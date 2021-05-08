package com.divyanshushekhar.flutter_process_text;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.List;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.EventChannel.EventSink;
import io.flutter.plugin.common.MethodChannel;

/** FlutterProcessTextPlugin */
public class FlutterProcessTextPlugin implements FlutterPlugin, ActivityAware {

  private static final String CHANNEL_ID = "flutter_process_text";
  private static final String STREAM_ID = "flutter_process_text_stream";
  private static final String TAG = "[Flutter Process Text]";

  public FlutterProcessTextPlugin() {}

  private static String savedProcessIntentText = null;
  private static boolean isAppInForeground = false;

  public static boolean getIsAppInForeground() {
    return isAppInForeground;
  }

  @SuppressLint("StaticFieldLeak")
  private static Activity activity = null;
  private static Context context = null;
  private BinaryMessenger binaryMessenger = null;

  private MethodChannel methodChannel;
  private MethodCallHandlerImplementation methodHandler;
  @SuppressLint("StaticFieldLeak")
  private static EventCallHandlerImplementation eventHandler;
  private EventChannel eventChannel;

  public static String getSavedProcessIntentText() {
    return savedProcessIntentText;
  }

  public static void setSavedProcessIntentText(String savedProcessIntentText) {
    FlutterProcessTextPlugin.savedProcessIntentText = savedProcessIntentText;
  }

  private void setupChannel(BinaryMessenger messenger, Context context, Activity activity) {
    FlutterProcessTextPlugin.activity = activity;
    methodChannel = new MethodChannel(binaryMessenger, CHANNEL_ID);
    eventChannel = new EventChannel(binaryMessenger,STREAM_ID);

    methodHandler = new MethodCallHandlerImplementation(context, activity);
    eventHandler = new EventCallHandlerImplementation(activity);

    methodChannel.setMethodCallHandler(methodHandler);
    eventChannel.setStreamHandler(eventHandler);

    ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
    ActivityManager.getMyMemoryState(myProcess);
    isAppInForeground = myProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
  }

  private void teardownChannel() {
    methodChannel.setMethodCallHandler(null);
    eventChannel.setStreamHandler(null);
    isAppInForeground = false;
    binaryMessenger = null;
    methodChannel = null;
    methodHandler = null;
    eventChannel = null;
    eventHandler = null;
    context = null;
  }


  @SuppressWarnings("deprecation")
  public static void registerWith(io.flutter.plugin.common.PluginRegistry.Registrar registrar) {
    final FlutterProcessTextPlugin plugin = new FlutterProcessTextPlugin();
    plugin.setupChannel(registrar.messenger(), registrar.context(), registrar.activity());
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    System.out.println("Attaching Engine");
    binaryMessenger = flutterPluginBinding.getBinaryMessenger();
    context = flutterPluginBinding.getApplicationContext();
    setupChannel(binaryMessenger, context, null);
    System.out.println("Attaching Engine OVER");
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    teardownChannel();
  }



  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    Log.w(TAG,"Activity Attached");
    activity = binding.getActivity();
    methodHandler.setActivity(activity);
    EventCallHandlerImplementation.setActivity(activity);


    Log.w(TAG,"Activity Attached Function Over");
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    onDetachedFromActivity();
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    onAttachedToActivity(binding);
  }

  @Override
  public void onDetachedFromActivity() {
    methodHandler.setActivity(null);
    EventCallHandlerImplementation.setActivity(null);
  }

  public static void saveProcessIntentText() {
    String text = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
      text = activity.getIntent().getStringExtra(Intent.EXTRA_PROCESS_TEXT);
    }
    savedProcessIntentText = text;
  }

  public static void listenProcessTextIntent(boolean isAppRunning) {
    if (!isAppRunning) {
      openApp();
    } else {
      EventCallHandlerImplementation.onProcessTextChanged();
    }
    activity.finish();
  }

  public static void openApp() {
    FlutterProcessTextPlugin.saveProcessIntentText();
    Intent intent = getIntentToOpenMainActivity();
    ContextCompat.startActivity(context,intent, null);
  }

  private static Intent getIntentToOpenMainActivity() {
    final String packageName = context.getPackageName();

    return context
            .getPackageManager()
            .getLaunchIntentForPackage(packageName)
            .setAction(Intent.ACTION_RUN)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
  }


}
