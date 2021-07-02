# Flutter Process Text Plugin

<img src="https://i.imgur.com/6rTQCDc.png" title="Flutter_Process_Text" />

![GitHub](https://img.shields.io/github/license/DevsOnFlutter/flutter_process_text?label=License&style=plastic) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/DevsOnFlutter/flutter_process_text?style=plastic) ![GitHub language count](https://img.shields.io/github/languages/count/DevsOnFlutter/flutter_process_text?style=plastic) ![GitHub last commit](https://img.shields.io/github/last-commit/DevsOnFlutter/flutter_process_text?style=plastic)

##  Compatibility

✅ &nbsp; Android </br>
❌ &nbsp; iOS (active issue: [iOS support](https://github.com/DevsOnFlutter/flutter_process_text/issues/2))

## Show some :heart: and :star: the repo

[![GitHub followers](https://img.shields.io/github/followers/divshekhar.svg?style=social&label=Follow)](https://github.com/divshekhar/)

## Why use Flutter Process Text?

Flutter Process Text Plugin is known for :

| Flutter Process Text                                 | Flutter Process Text                          |
| :--------------------------------- | :-------------------------- |
| Fast, performant & compatible | Free & Open-source     |
| Production ready          | Make App Reactive |

---

## Features

✅ &nbsp; Listen process text stream </br>
✅ &nbsp; Open app from process text intent activity </br>
✅ &nbsp; Get pending intent text </br>

---

## Demo

App Running | App Not Running
|---|---|


![App Running](https://media.giphy.com/media/omJKH5bAvrnlPB1nRd/giphy.gif) | ![AppNotRunning](https://media.giphy.com/media/6UFvIvZUxdn9oniTAH/giphy.gif)
|---|---|

## Quick start

### Step 1: Include plugin to your project

```yaml
dependencies:
  flutter_process_text: <latest version>
```

Run pub get and get packages.

### Step 2: Create a new activity

Add the below code to your `AndroidManifest.xml` in the `android\app\src\main\` folder.

```xml
  <activity 
        android:name=".ProcessTextActivity" 
        android:label="Process_Text"
        android:theme="@android:style/Theme.NoDisplay">
          <intent-filter>
              <action android:name="android.intent.action.PROCESS_TEXT" />
              <data android:mimeType="text/plain"/>
              <category android:name="android.intent.category.DEFAULT" />
          </intent-filter>
  </activity>
```

**Note:** You may change the `android:name` from `ProcessTextActivity` to anything you want. also change the `android:label` from `Process_Text` to the process action text that you want to display.

### Step 3: Create new activity class

Create a new Java/Kotlin file with the same name as  `android:name` in step 2.

Copy the below code and paste in the newly created file.

```java
package com.divyanshushekhar.flutter_process_text_example;

import com.divyanshushekhar.flutter_process_text.FlutterProcessTextPlugin;
import io.flutter.embedding.android.FlutterActivity;
import android.os.Bundle;

public class ProcessTextActivity extends FlutterActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean issAppRunning = MainActivity.getIsAppRunning();
        FlutterProcessTextPlugin.listenProcessTextIntent(issAppRunning);
    }
}
```

**Note:** Don't forget to change the package name in the above code.

### Step 4: Changes in MainActivity

Make the necessary changes in the MainActivity class.

```java
package com.divyanshushekhar.flutter_process_text_example;

import io.flutter.embedding.android.FlutterActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
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
```

### Step 5: Connect to flutter

First thing you need to do is to call the initialize method from the `FlutterProcessText` class in the `initState() {}` of the page.

```dart
FlutterProcessText.initialize();
```

OR

```dart
FlutterProcessText.initialize(
    showConfirmationToast: true,
    showRefreshToast: true,
    showErrorToast: true,
    confirmationMessage: "Text Added",
    refreshMessage: "Got all Text",
    errorMessage: "Some Error",
);
```

### Step 6: Working with stream

There are two ways to work with stream, either create a `StreamSubscription` to listen for the incoming data or store the `Stream` and use it in `StreamBuilder`.

```dart
  late final StreamSubscription _processText;
  String text? = '';

  @override
  void initState() {
    super.initState();
    FlutterProcessText.initialize(
      showToast: true,
      confirmationMessage: "Text Added",
      refreshMessage: "Got all Text",
      errorMessage: "Some Error",
    );

    _processText = FlutterProcessText.getProcessTextStream.listen((event) {
      setState(() {
        text = event;
      });
    });
  }

  @override
  void dispose() {
    super.dispose();
    _processText.cancel();
  }
```

OR

```dart
late final Stream<String> _processText;
_processText = FlutterProcessText.getProcessTextStream;
```

Now use the stream in the `StreamBuilder`.

```dart
StreamBuilder<String?>(
  stream: _processText,
  builder: (context, snapshot) {
    return Text('Fetched Data: ${snapshot.data}');
  },
),
```

#### Get pending data

Get the pending data by calling the `refreshProcessText` method in `FlutterProcessText` class.

```dart
String? text = await FlutterProcessText.refreshProcessText;
```

## Project Created & Maintained By

### Divyanshu Shekhar

<a href="https://twitter.com/dshekhar17"><img src="https://github.com/aritraroy/social-icons/blob/master/twitter-icon.png?raw=true" width="60"></a> <a href="https://in.linkedin.com/in/divyanshu-shekhar-a8a04a162"><img src="https://github.com/aritraroy/social-icons/blob/master/linkedin-icon.png?raw=true" width="60"></a> <a href="https://instagram.com/dshekhar17"><img src="https://github.com/aritraroy/social-icons/blob/master/instagram-icon.png?raw=true" width="60"></a>

## Copyright & License

Code and documentation Copyright (c) 2021 [Divyanshu Shekhar](https://divyanshushekhar.com). Code released under the [BSD 3-Clause License](./LICENSE).
