# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\application\android\sdks/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep public class com.leedavid.adslib.comm.**
-dontwarn com.leedavid.**
-keepclassmembers public class com.leedavid.adslib.comm.** { public *** ***(...);}
-keepclassmembers public class com.leedavid.adslib.comm.** { public <fields> ;}
-keep class com.qq.e.** { public protected *;}
-keep class android.support.v4.app.NotificationCompat**{ public *;}
-keepclassmembers class * extends android.app.Activity { public void *(android.view.View);}
-keepclassmembers enum * { public static **[] values();
public static ** valueOf(java.lang.String);
}
-keep class com.baidu.mobads.*.** { *; }
-keep class com.afk.** {*;}
-keep class com.google.protobuf.** {*;}
-keepattributes *Annotation* -keepattributes *JavascriptInterface*
-keep public class * implements com.afk.client.ads.inf.BaseListener
-keep public class com.afk.client.ads.inf.BaseListener
-keep public class * extends android.app.Activity
-keep public class * extends android.webkit.WebChromeClient -keep public class com.anzhi.usercenter.sdk.AnzhiUserCenter

-keep class * implements com.anzhi.usercenter.sdk.BaseWebViewActivity$JsCallJavaInterface{*; }
-keep public class com.anzhi.**{*;}
-keepclassmembers class com.afk.client.ads.inf.BaseListener {
<fields>;
<methods>;
}
-keepclassmembers class * extends android.webkit.WebChromeClient{
public <fields>;
public <methods>;
}
-keepclassmembers class * implements com.afk.client.ads.inf.BannerAdListener{
public <fields>;
public <methods>;
}
