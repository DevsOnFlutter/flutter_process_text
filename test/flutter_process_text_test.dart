import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_process_text/flutter_process_text.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_process_text');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return 'Text';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getRefreshProcessText', () async {
    expect(await FlutterProcessText.refreshProcessText, 'Text');
  });
}
