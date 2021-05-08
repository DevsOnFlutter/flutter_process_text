import 'package:flutter/material.dart';
import 'dart:async';

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
      debugShowCheckedModeBanner: false,
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
  Stream<String> _processText;
  String refreshedData = '';

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

  void initialize() {
    FlutterProcessText.initialize(
      showToast: true,
      confirmationMessage: "Text Added",
      refreshMessage: "Got all Text",
      errorMessage: "Some Error",
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Flutter Process Text Plugin'),
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
          SizedBox(height: 100),
          Center(
            child: StreamBuilder<String>(
              stream: _processText,
              builder: (context, snapshot) {
                return Text('Fetched Data: ${snapshot.data}\n');
              },
            ),
          ),
          SizedBox(height: 150),
          Text("Refreshed Data: $refreshedData"),
        ],
      ),
    );
  }
}
