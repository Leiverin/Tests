# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

-printmapping mapping.txt
-verbose
-dontoptimize
-dontpreverify
-dontshrink
-dontskipnonpubliclibraryclassmembers
-dontusemixedcaseclassnames
-keepparameternames
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-keep class com.triversoft.diary.data.models.**{ *; }

-keep class com.admob.**{
   <fields>;
}
-keep class com.triversoft.diary.data.mmkv.**{ *; }

-keep class androidx.appcompat.widget.** { *; }

-keep class com.google.ads.**
-dontwarn com.google.ads.**

-keep class **.R
-keep class **.R$* {
    <fields>;
}

-assumenosideeffects class android.util.Log {
    public static  d(...);
    public static  v(...);
    public static  e(...);
    public static  w(...);
    public static *** i(...);
}


-keep class * extends android.app.Activity

-keep class org.json.**

-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-keep class com.google.android.gms.internal.consent_sdk.** { <fields>; }
#-keep class com.google.android.gms.internal.** { *; }

-keepclassmembers,includedescriptorclasses class org.json.** { *; }
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keep class com.facebook.** { *; }
-keep class com.androidquery.** { *; }
-keep class com.google.** { *; }
-keep class org.acra.** { *; }
-keep class org.apache.** { *; }
-keep class com.mobileapptracker.** { *; }
-keep class com.nostra13.** { *; }
-keep class net.simonvt.** { *; }
-keep class android.support.** { *; }
-keep class com.nnacres.app.model.** { *; }
-keep class com.facebook.** { *; }
-keep class com.astuetz.** { *; }
-keep class twitter4j.** { *; }
-keep class com.actionbarsherlock.** { *; }
-keep class com.dg.libs.** { *; }
-keep class android.support.v4.** { *; }
-keep class com.bluetapestudio.templateproject.** { *; }
-keep class com.yourideatoreality.model.** { *; }
-keep interface com.yourideatoreality.model.** { *; }
-keep class com.bluetapestudio.** { *; }
-keep interface com.bluetapestudio.** { *; }

# Suppress warnings if you are NOT using IAP:
-dontwarn com.nnacres.app.**
-dontwarn com.androidquery.**
-dontwarn com.google.**
-dontwarn org.acra.**
-dontwarn org.apache.**
-dontwarn com.mobileapptracker.**
-dontwarn com.nostra13.**
-dontwarn net.simonvt.**
-dontwarn android.support.**
-dontwarn com.facebook.**
-dontwarn twitter4j.**
-dontwarn com.astuetz.**
-dontwarn com.actionbarsherlock.**
-dontwarn com.dg.libs.**
-dontwarn  com.bluetapestudio.templateproject.**

-keepattributes Signature

-keepattributes *Annotation*

-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }

-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
#Keep native
-keepclasseswithmembernames class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn com.squareup.okhttp.**

-keep public class com.google.android.gms.ads.**{
   public *;
}

# For old ads classes
-keep public class com.google.ads.**{
   public *;
}

# For mediation
-keepattributes *Annotation*

# Other required classes for Google Play Services
# Read more at http://developer.android.com/google/play-services/setup.html
-keep class * extends java.util.ListResourceBundle {
   protected java.lang.Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
   public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
   @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
   public static final ** CREATOR;
}

-keep class androidx.appcompat.widget.** { *; }
