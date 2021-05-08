package com.divyanshushekhar.flutter_process_text;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MethodCallHandlerImplementation implements MethodChannel.MethodCallHandler {

    private static final String CHANNEL_ID = "flutter_process_text";
    private final Context context;
    private Activity activity;

    MethodCallHandlerImplementation(Context context, Activity activity) {
        System.out.println("Method Call Handler Implementation");
        this.context = context;
        this.activity = activity;
    }

    void setActivity(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if(call.method.equals("getRefreshProcessText")){
            try {
                String textIntent = FlutterProcessTextPlugin.getSavedProcessIntentText();
                result.success(textIntent);
                Toast.makeText(context, "Fetched All Data", Toast.LENGTH_LONG).show();
                FlutterProcessTextPlugin.setSavedProcessIntentText(null);
            } catch (Exception error) {
                Toast.makeText(context, "Unable to get text!", Toast.LENGTH_LONG).show();
            }

        }
        else {
            result.notImplemented();
        }
    }
}
