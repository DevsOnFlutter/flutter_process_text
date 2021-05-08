package com.divyanshushekhar.flutter_process_text;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import io.flutter.Log;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.EventChannel.StreamHandler;
import io.flutter.plugin.common.EventChannel.EventSink;

public class EventCallHandlerImplementation implements StreamHandler {

    private static final String TAG = "[Flutter Process Text]";

    private static EventSink mEventSink = null;
    @SuppressLint("StaticFieldLeak")
    private static Activity activity = null;

    EventCallHandlerImplementation(Activity activity) {
        Log.w(TAG,"Event Call Handler Implementation");
        EventCallHandlerImplementation.activity = activity;
    }

    public static void setActivity(Activity activity) {
        EventCallHandlerImplementation.activity = activity;
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        Log.w(TAG,"Listen Added");
        mEventSink = events;
    }

    @Override
    public void onCancel(Object arguments) {
        Log.w(TAG,"Cancel Called");
        mEventSink = null;
    }

    public static void onProcessTextChanged() {
        Log.w(TAG,"Intent Start");
        String textIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textIntent = activity.getIntent().getStringExtra(Intent.EXTRA_PROCESS_TEXT);
        } else {
            textIntent = "No Data Found";
            Toast.makeText(activity, "Not Compatible with this Android Version", Toast.LENGTH_SHORT).show();
        }
        if(mEventSink != null) {
            mEventSink.success(textIntent);
            Toast.makeText(activity, "Added to Queue", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "Event Sink called on null", Toast.LENGTH_SHORT).show();
        }
        Log.w(TAG,"Intent Leave");
    }
}
