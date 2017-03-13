package com.hkzy.imlz_h5native;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 分享管理
 * Created by linzenos on 16/9/19.
 */
public class ShareSDKManager {

    private static Context mcontext;
    private static String mtitle, murl, mcontent, mcomment, mimageUrl;


    private static boolean mshareing = false;

    public static boolean isMshareing() {
        return mshareing;
    }

    public static void setMshareing(boolean mshareing) {
        ShareSDKManager.mshareing = mshareing;
    }

    private static boolean mcancelShare = false;

    public static boolean isCancelShare() {
        return mcancelShare;
    }

    public static void setCancelShare(boolean cancelShare) {
        mcancelShare = cancelShare;
    }

    /**
     * 启动分享
     *
     * @param context
     * @param title
     * @param url
     * @param content
     * @param comment
     */
    public static void showShare(Context context, String title, String url, String imageUrl, String content, String comment) {
        mcontext = context;
        mtitle = title;
        murl = url;
        mcontent = content;
        mcomment = comment;
        mimageUrl = imageUrl;

        if (isMshareing()) {
            return;
        }
        //不用再下载通过本地获取图片,直接设置网络图片
        /*((BaseActivity) context).showLoadingDialog();

        setCancelShare(false);
        setMshareing(true);
        new DownLoadTask().execute(imageUrl);*/


        setMshareing(true);
        onekeyShare(context, title, url, imageUrl, content, comment);
    }


    public static class DownLoadTask extends AsyncTask<String, Integer, File> {
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected File doInBackground(String... params) {
            try {
                String mfilename = "shareimage.jpg";
                String mfilepath = Environment.getExternalStorageDirectory().toString() + File.separator + "XQJ" + File.separator;
                File file = downLoadFile(mfilepath, mfilename, params[0]);

                return file;
            } catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(File result) {
            super.onPostExecute(result);
            if (result != null) {
                if (isCancelShare()) {
                    return;
                }
                onekeyShare(mcontext, mtitle, murl, result.getAbsolutePath(), mcontent, mcomment);
            } else {
                Toast.makeText(mcontext, "图片操作异常", Toast.LENGTH_SHORT).show();
                setMshareing(false);
                ((BaseActivity) mcontext).stopLoadingDialog();
            }

        }
    }


    public static File getFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 下载图片
     *
     * @param filepath
     * @param filename
     * @param _urlStr
     * @return
     */
    private static File downLoadFile(String filepath, String filename, String _urlStr) {
        try {
            File filecache = getFilePath(filepath, filename);
            // 构造URL
            URL url = new URL(_urlStr);
            // 打开连接
            URLConnection con = url.openConnection();
            //获得文件的长度
            int contentLength = con.getContentLength();
            System.out.println("长度 :" + contentLength);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            OutputStream os = new FileOutputStream(filecache);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();

            return filecache;
        } catch (Exception e) {
            return null;
        }


    }

    private static void onekeyShare(Context context, String title, String url, final String imagePath, String content, String comment) {

        try {
            ShareSDK.initSDK(context);
            final OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();

            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
            oks.setTitle(title);
            // titleUrl是标题的网络链接，QQ和QQ空间等使用
            oks.setTitleUrl(url);
            // text是分享文本，所有平台都需要这个字段
            oks.setText(content);
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath(imagePath);//确保SDcard下面存在此张图片"/sdcard/test.jpg"//如果要用本地下载的图片,可以放开注释,但默认会覆盖imageurl
            oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                @Override
                public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                    if (platform.getName().equals(Wechat.NAME)) {
                        paramsToShare.setImageUrl(null);
                    } else {
                        paramsToShare.setImageUrl(imagePath);
                    }
                }
            });

            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(url);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment(comment);
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(context.getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(url);

            // 启动分享GUI
            oks.show(context);
            setMshareing(false);
            //((BaseActivity) context).stopLoadingDialog();
        } catch (Exception e) {
            Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
            setMshareing(false);
            //((BaseActivity) context).stopLoadingDialog();
        }
    }

}
