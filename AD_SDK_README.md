#辉煌明天广告sdk接入文档
##1.SDK嵌入
###1.1.添加sdk到工程中
####请在工程文件根目录下创建一个名为 libs 的子目录，并将广点通 SDK 的 JAR 包拷贝到 libs 目录下。并在libs目录下拷贝wire的JAR包。
####wire GitHub地址：https://github.com/square/wire
####wire 导入as：compile 'com.squareup.wire:wire-runtime:2.x.x'
###1.2.在AndroidManifest.xml文件中添加权限
####

	<uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />


如果您的Android版本大于等于6.0，请您动态申请权限。
###1.3.初始化sdk。
在BaseApplication的onCreate()方法中调用：  

	ADManager.getInstance().init(context,"appid");  

用来初始化sdk。
###1.4.配置下载路径
7.0以上需要配置文件下载路径，在资源目录下新建XML，详情见demo。
###1.5申明provider、service以及activity 
在主项目中的AndroidManifest.xml中，申明下面内容： 
 
	<service 
		android:name="com.hhmt.comm.download.DownloadService"/>  
	<provider
		android:authorities="com.caesar.adsdkdemo.download"  
		android:name="com.hhmt.comm.download.DownloadTaskProvider"/>  
	<activity
		android:name="com.hhmt.ad.AdActivit"
        android:configChanges="keyboard|keyboardHidden|orientation|screenSize"
		android:screenOrientation="landscape"/>
	<meta-data
		android:name="InstallChannel"
		android:value="ad" />
##2.接入代码
###2.1.Banner广告
####2.1.1.主要代码示例
	private void initBanner() {
        BannerView bv = new BannerView(this, "appId", "bannerId");
        bv.setAdListener(new AbsBannerListener() {

            @Override
            public void onNoAD(AdError error) {
                //加载失败。
            }

            @Override
            public void onADReceiv() {
                //加载成功。
            }
        });
        bannerContainer.addView(bv);
		this.bv.loadAD(); //开始加载广告
    }

####2.1.2.Banner广告主要API
- **com.hhmt.ad.banner.BannerView**  
loadAD()：加载广告  
setAdListener(AbsBannerListener listener)：设置监听回调

- **com.hhmt.ad.banner.BannerADListener**  
onADReceiv() 	广告加载成功回调，表示广告相关的资源已经加载完毕。  
onNoAD(AdError error) 	广告加载失败，error对象包含了错误码和错误信息。  
###2.2.插屏广告
####2.2.1.主要代码示例
	final InnerAD innerAD = new InnerAD(InnerActivity.this, "13433242342", "2143212314e1");

        innerAD.setADListener(new AbsInnerAdListener() {

            /**
             * 加载成功。
             */
            @Override
            public void onADReceive() {
                innerAD.showAsPopupWindow();
                Log.i("InnerActivity: ", "onADReceive");
            }

            /**
             * 加载错误。
             * @param var1
             */
            @Override
            public void onNoAD(AdError var1) {
                Log.i("InnerActivity: ", var1.getErrorMsg());
            }
        });
        innerAD.loadAD();  //开始加载
####2.2.2.主要API
- **com.hhmt.ad.banner.InnerAD**  
loadAD()：加载广告  
setADListener(AbsInnerAdListener listener)：设置监听回调

- **com.hhmt.ad.banner.BannerADListener**  
onADReceiv() 	广告加载成功回调，表示广告相关的资源已经加载完毕。  
onNoAD(AdError error) 	广告加载失败，error对象包含了错误码和错误信息。  
###2.3.闪屏广告
####2.3.1.主要代码示例
	/**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity      展示广告的activity
     * @param adContainer   展示广告的大容器
     * @param skipContainer 自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * @param appId         应用ID
     * @param posId         广告位ID
     * @param adListener    广告状态监听器
     * @param fetchDelay    拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        splashAD = new SplashAD(activity, adContainer, skipContainer, appId, posId, adListener, fetchDelay);
    }

####2.2.2.主要API
-  **com.hhmt.ad.splash**  

-  **SplashADListener**  
onADDismissed()  广告关闭时调用，两种可能，倒计时结束或者用户主动跳过广告。  
onNoAD(AdError error) 	广告加载失败，error对象包含了错误码和错误信息。  
onADPresent()  广告展示成功的时候调用。  
onADClicked()  广告被点击的时候调用。
onADTick(long time)  广告倒计时回调，返回剩余的时间。  

  
###2.4.原生广告
####2.4.1.主要代码示例
	if (ad == null) {
        ad = new NativeAD(NativeActivity.this, "2132432142", "432142314");
        ad.setNativeAdListener(this);
    }
    ad.loadAd(1);  //参数为每次拉去的广告数量。
####2.4.2主要API
- **com.hhmt.ad.nativ.NativeAD**  
loadAd() 开始加载广告  
setNativeAdListener(NativeAdListener nativeAdListener) 设置回调监听    

- **com.hhmt.ad.nativ.NativeAdListener**  
void onADLoaded(List<NativeADDataRef> list) 加载成功。  
onNoAD(AdError adError)  加载失败。  
onADStatusChanged(NativeADDataRef nativeADDataRef)  广告状态发生改变。  
onADError(NativeADDataRef nativeADDataRef, AdError adError)  广告调用曝光、点击接口时发生的错误  
- **com.hhmt.ad.nativ.NativeADDataRef**  
原生广告包含的一些数据，通过get方法来获取。


###2.5.原生视频
####2.5.1.主要代码示例
	//listener为广告回调接口
	NativeMediaAD ad = new NativeMediaAD(Context, "appId", "posId", NativeMediaADListener);
    ad.loadAd(10);
####2.5.2.主要API
- **com.hhmt.ad.nativ.NativeMediaAD**  
NativeMediaAD(final Context context, final String appID, final String posID, final NativeMediaADListener listener) 	原生视频广告构造函数，listener指定广告状态回调接口  
loadAD(int count) 	加载广告，count指定期望加载的广告数量，根据广告填充情况不同，返回不大于count数量的广告。建议开发者不要一次加载过多广告，需要展示几个，就请求几个，count最大限制和原生广告相同。  
setBrowserType(BrowserType bt) 	指定普链广告点击后用于展示落地页的浏览器类型，可选项包括：InnerBrowser（APP内置浏览器），Sys（系统浏览器），Default（默认，SDK按照默认逻辑选择)  
setDownAPPConfirmPolicy(DownAPPConfirmPolicy policy) 	指定点击APP广告后是否展示二次确认对话框，可选项包括Default（wifi不展示，非wifi展示），NoConfirm（所有情况不展示）  
- **com.hhmt.ad.nativ.NativeMediaAD.NativeMediaADListener**  
onADLoaded(List adList) 	广告数据获取成功时回调  
onNoAD(AdError error) 	广告数据获取失败时回调  
onADStatusChanged(NativeMediaADData adData) 	广告数据更新状态时的回调  
onADError(NativeMediaADData adData, AdError error) 	广告在曝光、点击、加载视频素材的接口被调用发生错误时的回调  
onADVideoLoaded(NativeMediaADData adData) 	广告在成功加载加载视频素材时的回调  
onADExposure(NativeMediaADData adData) 	广告曝光的回调  
onADClicked(NativeMediaADData adData) 	广告点击的回调  

###2.6浮窗
####2.6.1.使用方法
#####第一步，新建对象以及参数意义。  
	/**
     * 
     * @param context   上下文对象
     * @param appId		Appid
     * @param posId		广告位id
     * @param x			显示浮窗的横坐标
     * @param y			显示浮窗的纵坐标
     */
	FloatAd(Context context, String appId, String posId, int x, int y)
#####第二步，设置监听回调
	floatAd.setFloatAdListener(FloatAdListener floatAdListener)
####2.6.2.回调FloatAdListener
	void onADReceive();				//加载成功
	void onNoAD(AdError adError);		//加载失败
	void onADClicked();				//点击
	void onADClosed();				//关闭

##3.混淆
	-keep class com.hhmt.lib.impl.** {*;}  
	-keep class com.hhmt.lib.net.** {*;}  
	-keep class com.hhmt.lib.media.MediaResponse {*;}  
	-keep class com.hhmt.lib.view.** {*;}  
	-keep class com.hhmt.lib.nativ.AdRsponse {*;}  
	-keep class com.hhmt.ad.** {*;}  
	-keep class com.hhmt.comm.constants.** {*;}  
	-keep class com.hhmt.comm.download.** {*;}  
	-keep class com.hhmt.comm.event.** {*;}  
	-keep class com.hhmt.comm.listener.** {*;}  
	-keep class com.hhmt.comm.manager.** {*;}  
	-keep class com.hhmt.comm.net.** {*;}  
	-keep class com.hhmt.comm.pi.** {*;}  
	-keep class com.hhmt.comm.util.** {*;}  
	-keep class com.hhmt.comm.** {*;}  
##4.SDK相关问题排查
如果根据正常的注册流程仍然无法在嵌入广告sdk的app中看到广告，可以尝试使用logcat排查问题。广告SDK默认设置下会对广告流程中的关键步骤打印日志，日志格式如下：

	LogTag ：ad_sdk
##5.SDK错误码
	0    正常获取到广告  
	-1   获取广告失败  
	404  没有获取到广告   


##6.注意事项
##7.demo  
<https://github.com/yangshaopeng/AdSdkDemo>  

##8.附录上报数据



|节点  |上报的参数|是否必填|字段类型|描述|
|:-:  | :-:     |:-:|    :-:|    :-:|
| 公共字段|guid| 是|String|标识唯一用户|
| |adid| 是|String|广告位id|
| |cid| 是|String|广告id|
| |adProvider| 是|String|广告提供方|
| |ip| 是|String|用户ip地址|
| |statEvent| 是|String|上报事件（曝光、点击或者交互事件）|
| |trackingKey|否|String|交互事件，只有在上报事件为交互事件的时候才上报此字段，<br>包括下载开始、下载结束、安装完成以及激活。<br/>|
| 曝光|duration　| 是|String|曝光时长（仅开屏才有）|
| |lon　| 是|String|经度|
| |lat　| 是|String|纬度|
| 点击/关闭|downx　　| 是|String|手指按下时横坐标|
||downy　　　| 是|String|手指按下时纵坐标|
||upx　　　| 是|String|手指抬起时横坐标|
||upy　　　| 是|String|手指抬起时纵坐标|
| |lon　| 是|String|经度|
| |lat　| 是|String|纬度|
|开始下载|||||
|结束下载|||||
|安装完成|||||
|激活|||||
