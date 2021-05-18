package com.divyanshushekhar.flutter_process_text;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;

/** FlutterProcessTextPlugin */
public class FlutterProcessTextPlugin implements FlutterPlugin, ActivityAware {

  // Plugin Constructor
  public FlutterProcessTextPlugin() {}


  /* ------------- Method Channel -------------- */
  private static final String CHANNEL_ID = "flutter_process_text";

  public static String getChannelId() {
    return CHANNEL_ID;
  }
  /* ------------- Method Channel -------------- */


  /* ------------- Event Channel -------------- */
  private static final String STREAM_ID = "flutter_process_text_stream";

  public static String getStreamId() {
    return STREAM_ID;
  }
  /* ------------- Event Channel -------------- */


  /* ------------- Plugin Logging TAG -------------- */
  private static final String TAG = "[Flutter Process Text Plugin]";

  public static String getPluginTag() {
    return TAG;
  }
  /* ------------- Plugin Logging TAG -------------- */


  /* ------------- User Initialization -------------- */
  private static boolean showConfirmationToast = true;
  private static boolean showRefreshToast = true;
  private static boolean showErrorToast = true;
  private static String confirmationMessage = null;
  private static String refreshMessage = null;
  private static String errorMessage = null;

  public static void setUserInitialization(boolean showConfirmationToast, boolean showRefreshToast,
                                           boolean showErrorToast, String confirmationMessage,
                                           String refreshMessage, String errorMessage) {

    FlutterProcessTextPlugin.showConfirmationToast = showConfirmationToast;
    FlutterProcessTextPlugin.showRefreshToast = showRefreshToast;
    FlutterProcessTextPlugin.showErrorToast = showErrorToast;
    FlutterProcessTextPlugin.confirmationMessage = confirmationMessage;
    FlutterProcessTextPlugin.refreshMessage = refreshMessage;
    FlutterProcessTextPlugin.errorMessage = errorMessage;
  }
  /* ------------- User Initialization -------------- */

  /* ------------- Toast Messages -------------- */
  public static void showConfirmationToast() {
    if(showConfirmationToast) {
      Toast.makeText(context, confirmationMessage, Toast.LENGTH_LONG).show();
    }
  }

  public static void showRefreshToast() {
    if(showRefreshToast) {
      Toast.makeText(context, refreshMessage, Toast.LENGTH_LONG).show();
    }
  }

  public static void showErrorToast() {
    if(showErrorToast) {
      Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
    }
  }
  /* ------------- Toast Messages -------------- */


  /* ------------- Saved Process Text -------------- */
  private static String savedProcessIntentText = null;
  public static String getSavedProcessIntentText() {
    return savedProcessIntentText;
  }
  public static void setSavedProcessIntentText(String savedProcessIntentText) {
    FlutterProcessTextPlugin.savedProcessIntentText = savedProcessIntentText;
  }
  /* ------------- Saved Process Text -------------- */


  /* ------------- Native Variables -------------- */
  @SuppressLint("StaticFieldLeak")
  private static Activity activity = null;
  @SuppressLint("StaticFieldLeak")
  private static Context context = null;

  private BinaryMessenger binaryMessenger = null;

  private MethodChannel methodChannel;
  private MethodCallHandlerImplementation methodHandler;

  @SuppressLint("StaticFieldLeak")
  private static EventCallHandlerImplementation eventHandler;

  private EventChannel eventChannel;
  /* ------------- Native Variables -------------- */


  private void setupChannel(BinaryMessenger messenger, Context context, Activity activity) {
    FlutterProcessTextPlugin.activity = activity;
    methodChannel = new MethodChannel(binaryMessenger, CHANNEL_ID);
    eventChannel = new EventChannel(binaryMessenger,STREAM_ID);

    methodHandler = new MethodCallHandlerImplementation();
    eventHandler = new EventCallHandlerImplementation(context, activity);

    methodChannel.setMethodCallHandler(methodHandler);
    eventChannel.setStreamHandler(eventHandler);

  }

  private void teardownChannel() {
    methodChannel.setMethodCallHandler(null);
    eventChannel.setStreamHandler(null);
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
    binaryMessenger = flutterPluginBinding.getBinaryMessenger();
    context = flutterPluginBinding.getApplicationContext();
    setupChannel(binaryMessenger, context, null);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    teardownChannel();
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
    EventCallHandlerImplementation.setActivity(activity);
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
    EventCallHandlerImplementation.setActivity(null);
  }

  public static void saveProcessIntentText() {
    String text = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
      text = activity.getIntent().getStringExtra(Intent.EXTRA_PROCESS_TEXT);
    } else {
      Log.e(TAG,"Compatibility Issue:");
      Log.i(TAG,"Make sure device android version >= M (Marshmallow)");
    }
    savedProcessIntentText = text;
  }

  public static void listenProcessTextIntent(boolean isAppRunning) {
    if (!isAppRunning) {
      // Open app when its not running
      openApp();
    } else {
      // Fetch process text when the app is running.
      EventCallHandlerImplementation.onProcessTextChanged();
    }
    // Activity launch Theme.NoDisplay
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
