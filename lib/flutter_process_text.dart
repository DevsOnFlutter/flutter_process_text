import 'dart:async';

import 'package:flutter/services.dart';

class FlutterProcessText {
  static const MethodChannel _channel =
      const MethodChannel('flutter_process_text');

  static const EventChannel _eventChannel =
      const EventChannel('flutter_process_text_stream');

  static Stream<String> _processTextStream;

  static Future<void> initialize({
    bool showToast = true,
    String confirmationMessage = "Text Fetched",
    String refreshMessage = "Refreshed",
    String errorMessage = "Unable to fetch text!",
  }) async {
    return await _channel.invokeMethod('initialize', {
      'showToast': showToast.toString(),
      'confirmationMessage': confirmationMessage,
      'refreshMessage': refreshMessage,
      'errorMessage': errorMessage,
    });
  }

  static Future<String> get refreshProcessText async {
    return await _channel.invokeMethod('getRefreshProcessText');
  }

  static Stream<String> get getProcessTextStream {
    if (_processTextStream == null) {
      _processTextStream =
          _eventChannel.receiveBroadcastStream().map<String>((event) => event);
    }
    return _processTextStream;
  }
}
