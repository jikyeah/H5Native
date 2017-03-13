## 项目说明
> 项目主要提供内嵌网页的功能，同时提供js和本地native交互的能力，内置了ShareSDk一键分享和友盟数据统计。

## 准备工作
- 安装android studio;
- 下载并配置好SDK环境；

## 打开工程
- 修改工程目录下local.properties文件中的sdk.dir路径为本机SDK路径；
- 根据本机配置环境情况修改工程目录下的两个build.gradle文件（可选）
- 启动android studio通过Open的方式选中根目录下的build.gralde文件，然后逐步确定并启动项目；


## 项目配置
- 修改程序图标：准备不同规格的图片（见下表格）命名为ic_launcher.png拷贝覆盖到相应目录下，没有对应目录可新建；

屏幕密度 | 图标尺寸
---|---
mdpi | 48x48px
hdpi | 72x72px
xhdpi | 96x96px
xxhdpi | 144x144px
xxxhdpi | 192x192px
- 修改程序名称：打开工程../H5Native/app/src/main/res/values/strings.xml文件，修改第一行app_name的值APP为需要的值，如"天猫"；
- 修改启动图片：将需要修改的图片命名为splash_.jpg,拷贝覆盖到项目../H5Native/app/src/main/res/drawable-xhdpi/目录下；
- 修改跳转网页地址：打开../H5Native/app/src/main/java/com/hkzy/imlz_h5native/AppConfig.java文件，设置Web_url的值为需要的值，如"http://www.baidu.com"
- 友盟配置：打开../H5Native/app/src/main/java/com/hkzy/imlz_h5native/AppConfig.java文件，设置UM_APP_KEY和UM_CHANNEL_ID为需要的值，具体请参见友盟官网：http://www.umeng.com/
- 分享配置：打开../H5Native/app/src/main/assets/ShareSDK.xml，根据需要和ShareSDK集成规范配置相关参数，ShareSDK官网：http://sharesdk.mob.com/

## 内置本地接口
- 显示提示消息: HN.showToast(String message);
- 一键分享： HN.share(String title, String url, String imageUrl, String content, String comment);

注：具体使用请参考../H5Native/app/src/main/assets/test.html文件；






