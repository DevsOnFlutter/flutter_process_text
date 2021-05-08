import 'dart:async';

import 'package:flutter/services.dart';

class FlutterProcessText {
  static const MethodChannel _channel =
      const MethodChannel('flutter_process_text');

  static const EventChannel _eventChannel =
      const EventChannel('flutter_process_text_stream');

  static Stream<String> _processTextStream;

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
