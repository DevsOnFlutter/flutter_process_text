package com.divyanshushekhar.flutter_process_text;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;

import io.flutter.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MethodCallHandlerImplementation implements MethodChannel.MethodCallHandler {

    private static final String TAG = FlutterProcessTextPlugin.getPluginTag();

    public MethodCallHandlerImplementation() {}

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("initialize")) {
            Map<String, String> arguments = call.arguments();
            // Setting User Initialization coming from flutter side
            FlutterProcessTextPlugin.setUserInitialization(
                    Boolean.parseBoolean(arguments.get("showConfirmationToast")),
                    Boolean.parseBoolean(arguments.get("showRefreshToast")),
                    Boolean.parseBoolean(arguments.get("showErrorToast")),
                    arguments.get("confirmationMessage"),
                    arguments.get("refreshMessage"),
                    arguments.get("errorMessage"));
        } else if (call.method.equals("getRefreshProcessText")) {
            try {
                String textIntent = FlutterProcessTextPlugin.getSavedProcessIntentText();
                result.success(textIntent);
                FlutterProcessTextPlugin.setSavedProcessIntentText(null);
                FlutterProcessTextPlugin.showRefreshToast();
            } catch (Exception error) {
                FlutterProcessTextPlugin.showErrorToast();
                Log.e(TAG, "Method Call Failed");
                Log.i(TAG, "Make sure you are calling the correct method.");
            }
        } else {
            result.notImplemented();
        }
    }
}
