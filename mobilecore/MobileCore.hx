package mobilecore;

#if android
import openfl.utils.JNI;

class MobileCore {
  public static function init(hash : String, logType : LogType, adUnits : Array<AdUnit>) {
    initJNI();

    var adUnitsFlags : Int = 0;
    for (a in adUnits) {
      adUnitsFlags = adUnitsFlags & a;
    }
    _init(hash, logType, adUnitsFlags);
  }

  // Init all jni variables
  private static function initJNI() {
    var getMethod = JNI.createStaticMethod.bind("org/haxe/extension/HaxeMobileCore");
    if (_init == null) {
      _init = getMethod("init", "(Ljava/lang/String;I;I)V;");
    }
  }

  private static var _init : Dynamic = null;
}

#end

@:enum
abstract LogType(Int) to Int {
  var DEBUG = 1;
  var PRODUCTION = 2;
}

@:enum
abstract AdUnit(Int) to Int {
  var STICKEEZ  = 1;
  var SLIDER    = 2;
  var OFFERWALL = 4;
  var DIRECT_TO_MARKET = 8;
  var ALL_UNITS = 16;
}
