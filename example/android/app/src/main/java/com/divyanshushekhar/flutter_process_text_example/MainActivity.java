package com.divyanshushekhar.flutter_process_text_example;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.divyanshushekhar.flutter_process_text.FlutterProcessTextPlugin;

import java.util.List;

public class MainActivity extends FlutterActivity {
   private static boolean isAppRunning;

   public static boolean getIsAppRunning() {
     return isAppRunning;
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        isAppRunning = isAppRunning(this);
   }

    public static boolean isAppRunning(Context context) {
        final String packageName = context.getPackageName();
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> processInfo = activityManager.getRunningAppProcesses();
        if (processInfo != null)
        {
            for (final ActivityManager.RunningAppProcessInfo info : processInfo) {
                if (info.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
