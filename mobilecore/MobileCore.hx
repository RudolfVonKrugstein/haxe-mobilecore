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

  public static function showOfferWall(cb : Void -> Void = null, showToast : Bool = false) {
    initJNI();

    _showOfferWall(new CallbackHolder(cb), showToast);
  }

  public static function isOfferwallReady() : Bool {
    return _isOfferwallReady();
  }

  // Init all jni variables
  private static function initJNI() {
    var getMethod = JNI.createStaticMethod.bind("org/haxe/extension/HaxeMobileCore");
    if (_init == null) {
      _init = getMethod("init", "(Ljava/lang/String;II)V");
    }
    if (_showOfferWall == null) {
      _showOfferWall = getMethod("showOfferWall", "(Lorg/haxe/lime/HaxeObject;Z)V");
    }
    if (_isOfferwallReady == null) {
      _isOfferwallReady = getMethod("isOfferwallReady","()Z");
    }
  }

  private static var _init : Dynamic = null;
  private static var _showOfferWall : Dynamic = null;
  private static var _isOfferwallReady : Dynamic = null;
}

private class CallbackHolder {
  private var cb : Void -> Void;
  public function new(cb : Void -> Void) {
    this.cb = cb;
  }

  public function callback() {
    if (cb != null)
      cb();
  }
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
