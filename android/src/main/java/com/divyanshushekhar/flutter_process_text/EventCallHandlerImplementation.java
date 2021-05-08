package com.divyanshushekhar.flutter_process_text;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import io.flutter.Log;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.EventChannel.StreamHandler;
import io.flutter.plugin.common.EventChannel.EventSink;

public class EventCallHandlerImplementation implements StreamHandler {

    private static final String TAG = FlutterProcessTextPlugin.getPluginTag();

    private static EventSink mEventSink = null;
    @SuppressLint("StaticFieldLeak")
    private static Activity activity = null;
    private static Context context = null;

    EventCallHandlerImplementation(Context context,Activity activity) {
        EventCallHandlerImplementation.activity = activity;
        EventCallHandlerImplementation.context = context;
    }

    public static void setActivity(Activity activity) {
        EventCallHandlerImplementation.activity = activity;
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        mEventSink = events;
        Log.w(TAG,"Listening Stream...");
    }

    @Override
    public void onCancel(Object arguments) {
        mEventSink = null;
        Log.w(TAG,"Closing Stream...");
    }

    public static void onProcessTextChanged() {
        String textIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textIntent = activity.getIntent().getStringExtra(Intent.EXTRA_PROCESS_TEXT);
        } else {
            textIntent = null;
            Log.e(TAG,"Compatibility Issue:");
            Log.i(TAG,"Make sure device android version >= M (Marshmallow)");
        }
        if(mEventSink != null) {
            mEventSink.success(textIntent);
            FlutterProcessTextPlugin.showConfirmationToast();
            Log.w(TAG,"Text Fetch Successful.");
        } else {
            FlutterProcessTextPlugin.showErrorToast();
            Log.e(TAG, "Event Sink was called on null.");
            Log.i(TAG,"Make sure to navigate where the stream is listening.");
        }
    }
}
