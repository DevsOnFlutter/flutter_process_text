import 'dart:async';

import 'package:flutter/services.dart';

class FlutterProcessText {
  static const MethodChannel _channel =
      const MethodChannel('flutter_process_text');

  static const EventChannel _eventChannel =
      const EventChannel('flutter_process_text_stream');

  static Stream<String>? _processTextStream;

  /// Initialize FlutterProcessText Plugins.
  /// showToast<bool> - if true, will show a toast message, else not.
  /// set ConfirmationMessage to show a confirmation message in the Toast.
  /// set refreshMessage to show a refresh message in the Toast.
  /// set errorMessage to show a error message in the Toast.
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

  /// Get the pending data by refreshing the process text
  static Future<String> get refreshProcessText async {
    var result = await _channel.invokeMethod('getRefreshProcessText');
    return result == null ? "" : result.toString();
  }

  /// Listen to the process text stream to get continuous data.
  static Stream<String> get getProcessTextStream {
    if (_processTextStream == null) {
      _processTextStream =
          _eventChannel.receiveBroadcastStream().map<String>((event) => event);
    }
    return _processTextStream!;
  }
}
