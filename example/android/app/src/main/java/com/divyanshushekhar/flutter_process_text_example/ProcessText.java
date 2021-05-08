package com.divyanshushekhar.flutter_process_text_example;

import com.divyanshushekhar.flutter_process_text.FlutterProcessTextPlugin;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import static java.lang.Thread.sleep;

public class ProcessText extends FlutterActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean issAppRunning = MainActivity.getIsAppRunning();
        FlutterProcessTextPlugin.listenProcessTextIntent(issAppRunning);
    }
}
