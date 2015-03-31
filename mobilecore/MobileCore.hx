package mobilecore;

#if android
import openfl.utils.JNI;

class MobileCore {
  public static function init(hash : String, logType : LogType, adUnits : Array<AdUnit>) {
    initJNI();

    var adUnitsFlags : Int = 0;
    for (a in adUnits) {
      adUnitsFlags = adUnitsFlags | a;
    }
    _init(hash, logType, adUnitsFlags);
  }

  public static function showInterstitial(cb : Void -> Void = null, position : InterstitialPosition = NOT_SET) {
    initJNI();

    _showInterstitial(new CallbackHolder(cb), position);
  }

  public static function isInterstitialReady() : Bool {
    initJNI();
    return _isInterstitialReady();
  }

  public static function showStickee() {
    initJNI();
    _showStickee();
  }

  public static function hideStickee() { initJNI();
    _hideStickee();
  }

  public static function isStickeeShowing() : Bool {
    initJNI();
    return _isStickeeShowing();
  }

  public static function isStickeeReady() : Bool {
    initJNI();
    return _isStickeeReady();
  }

  public static function setStickeezReadyListener(cb : Void -> Void) {
    initJNI();
    _setStickeezReadyListener(new CallbackHolder(cb));
  }

  // Init all jni variables
  private static function initJNI() {
    var getMethod = JNI.createStaticMethod.bind("org/haxe/extension/HaxeMobileCore");
    if (_init == null) {
      _init = getMethod("init", "(Ljava/lang/String;II)V");
    }
    if (_showInterstitial == null) {
      _showInterstitial = getMethod("showInterstitial", "(Lorg/haxe/lime/HaxeObject;I)V");
    }
    if (_isInterstitialReady == null) {
      _isInterstitialReady = getMethod("isInterstitialReady","()Z");
    }
    if (_showStickee == null) {
      _showStickee = getMethod("showStickee", "()V");
    }
    if (_hideStickee == null) {
      _hideStickee = getMethod("hideStickee", "()V");
    }
    if (_isStickeeShowing == null) {
      _isStickeeShowing = getMethod("isStickeeShowing", "()Z");
    }
    if (_isStickeeReady == null) {
      _isStickeeReady = getMethod("isStickeeReady", "()Z");
    }
    if (_setStickeezReadyListener == null) {
      _setStickeezReadyListener = getMethod("setStickeezReadyListener", "(Lorg/haxe/lime/HaxeObject;)V");
    }
  }

  private static var _init : Dynamic = null;
  private static var _showInterstitial : Dynamic = null;
  private static var _isInterstitialReady : Dynamic = null;
  private static var _showStickee : Dynamic = null;
  private static var _hideStickee : Dynamic = null;
  private static var _isStickeeShowing : Dynamic = null;
  private static var _isStickeeReady : Dynamic = null;
  private static var _setStickeezReadyListener : Dynamic = null;
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

#else

// Alternative implementation doing nothing
class MobileCore {
  public static function init(hash : String, logType : LogType, adUnits : Array<AdUnit>) {
  }

  public static function showInterstitial(cb : Void -> Void = null, showToast : Bool = false) {
  }

  public static function isInterstitialReady() : Bool {
    return false;
  }

  public static function showStickee() {
  }

  public static function hideStickee() {
  }

  public static function isStickeeShowing() : Bool {
    return false;
  }

  public static function isStickeeReady() : Bool {
    return false;
  }

  public static function setStickeezReadyListener(cb : Void -> Void) {
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
  var NATIVE_ADS    = 2;
  var INTERSTITIAL = 4;
  var DIRECT_TO_MARKET = 8;
  var ALL_UNITS = 16;
}

@:enum
abstract InterstitialPosition(Int) to Int {
  var APP_START = 1;
  var APP_EXIT  = 2;
  var APP_BUTTON_CLICK = 3;
  var GAME_LEVEL_END_WIN = 4;
  var GAME_LEVEL_END_LOSE = 5;
  var CUSTOM = 6;
  var NOT_SET = 7;
}
