import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_process_text/flutter_process_text.dart';
import 'package:flutter_process_text_example/second_page.dart';

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
  StreamSubscription _processText;
  String text = '';

  @override
  void initState() {
    super.initState();
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
          ElevatedButton(
            child: Text("Second Page"),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => SecondPage(text: text),
                ),
              );
            },
          ),
        ],
      ),
    );
  }
}
