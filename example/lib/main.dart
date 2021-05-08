import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_process_text/flutter_process_text.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: HomePage(),
    );
  }
}

class HomePage extends StatefulWidget {
  const HomePage({
    Key key,
  }) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  String refreshedData = '';
  Stream<String> _processText;
  String text = '';

  @override
  void initState() {
    super.initState();
    FlutterProcessText.initialize(
      showToast: true,
      confirmationMessage: "Text Added",
      refreshMessage: "Got all Text",
      errorMessage: "Some Error",
    );
    _processText = FlutterProcessText.getProcessTextStream;
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
        actions: [
          refreshedData != null
              ? IconButton(
                  icon: Icon(Icons.refresh),
                  onPressed: () async {
                    String result = await FlutterProcessText.refreshProcessText;
                    setState(() {
                      refreshedData = result;
                    });
                  },
                )
              : SizedBox(),
        ],
      ),
      body: Column(
        children: [
          Text("\n\nRefreshed Data: $refreshedData\n\n"),
          StreamBuilder<String>(
            stream: _processText,
            builder: (context, snapshot) {
              return Text(snapshot.data);
            },
          ),
        ],
      ),
    );
  }
}
