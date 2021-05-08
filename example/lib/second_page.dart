import 'package:flutter/material.dart';

class SecondPage extends StatefulWidget {
  SecondPage({
    @required this.text,
  });

  final String text;

  @override
  _SecondPageState createState() => _SecondPageState();
}

class _SecondPageState extends State<SecondPage> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Second Page"),
      ),
      body: Container(
        child: Center(
          child: Center(
            child: Text('Fetched Text: ${widget.text}\n'),
          ),
        ),
      ),
    );
  }
}
