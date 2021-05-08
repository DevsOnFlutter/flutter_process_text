#import "FlutterProcessTextPlugin.h"
#if __has_include(<flutter_process_text/flutter_process_text-Swift.h>)
#import <flutter_process_text/flutter_process_text-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_process_text-Swift.h"
#endif

@implementation FlutterProcessTextPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterProcessTextPlugin registerWithRegistrar:registrar];
}
@end
