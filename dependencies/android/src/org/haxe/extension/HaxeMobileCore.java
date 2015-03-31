package org.haxe.extension;


import android.app.Activity;
import android.content.res.AssetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.util.Log;

import org.haxe.lime.HaxeObject;

import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.CallbackResponse;
import com.ironsource.mobilcore.OnReadyListener;
import com.ironsource.mobilcore.AdUnitEventListener;

/* 
   You can use the Android Extension class in order to hook
   into the Android activity lifecycle. This is not required
   for standard Java code, this is designed for when you need
   deeper integration.

   You can access additional references from the Extension class,
   depending on your needs:

   - Extension.assetManager (android.content.res.AssetManager)
   - Extension.callbackHandler (android.os.Handler)
   - Extension.mainActivity (android.app.Activity)
   - Extension.mainContext (android.content.Context)
   - Extension.mainView (android.view.View)

   You can also make references to static or instance methods
   and properties on Java classes. These classes can be included 
   as single files using <java path="to/File.java" /> within your
   project, or use the full Android Library Project format (such
   as this example) in order to include your own AndroidManifest
   data, additional dependencies, etc.

   These are also optional, though this example shows a static
   function for performing a single task, like returning a value
   back to Haxe from Java.
   */
public class HaxeMobileCore extends Extension {

  private static boolean _showOnPause;
  private static Bundle _saveInstanceState;

  /**
   * Called when an activity you launched exits, giving you the requestCode 
   * you started it with, the resultCode it returned, and any additional data 
   * from it.
   */
  public boolean onActivityResult (int requestCode, int resultCode, Intent data) {

    return true;

  }


  /**
   * Called when the activity is starting.
   */
  public void onCreate (Bundle savedInstanceState) {
    _saveInstanceState = savedInstanceState;
  }


  /**
   * Perform any final cleanup before an activity is destroyed.
   */
  public void onDestroy () {



  }


  /**
   * Called as part of the activity lifecycle when an activity is going into
   * the background, but has not (yet) been killed.
   */
  public void onPause () {



  }


  /**
   * Called after {@link #onStop} when the current activity is being 
   * re-displayed to the user (the user has navigated back to it).
   */
  public void onRestart () {



  }


  /**
   * Called after {@link #onRestart}, or {@link #onPause}, for your activity 
   * to start interacting with the user.
   */
  public void onResume () {
    MobileCore.refreshOffers();
  }


  /**
   * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when  
   * the activity had been stopped, but is now again being displayed to the 
   * user.
   */
  public void onStart () {



  }


  /**
   * Called when the activity is no longer visible to the user, because 
   * another activity has been resumed and is covering this one. 
   */
  public void onStop () {



  }

  // These constants are also defined in haxe
  final private static int AD_STICKEEZ = 1;
  final private static int AD_NATIVE_ADS = 2;
  final private static int AD_INTERSTITIAL = 4;
  final private static int AD_DIRECT_TO_MARKET = 8;
  final private static int AD_ALL = 16;

  // Position of an interstitial
  final private static int IS_APP_START = 1;
  final private static int IS_APP_EXIT  = 2;
  final private static int IS_APP_BUTTON_CLICK = 3;
  final private static int IS_GAME_LEVEL_END_WIN = 4;
  final private static int IS_GAME_LEVEL_END_LOSE = 5;
  final private static int IS_CUSTOM = 6;
  final private static int IS_NOT_SET = 7;


  public static void init(final String devHash, int logType, int adUnits)
  {
    // Build a list of wanted ad units
    int numAdUnits = 0;

    if ( (adUnits & AD_ALL) == AD_ALL) {
      // Add all possible units
      adUnits = AD_STICKEEZ | AD_NATIVE_ADS | AD_INTERSTITIAL | AD_DIRECT_TO_MARKET;
    }

    int[] allFlags = {AD_STICKEEZ, AD_NATIVE_ADS, AD_INTERSTITIAL, AD_DIRECT_TO_MARKET};
    for (int i : allFlags) {
      if ( (adUnits & i) == i) ++numAdUnits;
    }
    MobileCore.AD_UNITS[] adArray = new MobileCore.AD_UNITS[numAdUnits];
    int c = 0;
    if ( (adUnits & AD_STICKEEZ) == AD_STICKEEZ) {
      adArray[c] = MobileCore.AD_UNITS.STICKEEZ; ++c;
    }
    if ( (adUnits & AD_NATIVE_ADS) == AD_NATIVE_ADS) {
      adArray[c] = MobileCore.AD_UNITS.NATIVE_ADS; ++c;
    }
    if ( (adUnits & AD_INTERSTITIAL) == AD_INTERSTITIAL) {
      adArray[c] = MobileCore.AD_UNITS.INTERSTITIAL; ++c;
    }
    if ( (adUnits & AD_DIRECT_TO_MARKET) == AD_DIRECT_TO_MARKET ) {
      adArray[c] = MobileCore.AD_UNITS.DIRECT_TO_MARKET; ++c;
    }

    final MobileCore.LOG_TYPE lt = (logType == 2)?MobileCore.LOG_TYPE.PRODUCTION:MobileCore.LOG_TYPE.DEBUG;
    final MobileCore.AD_UNITS[] finalAdArray = adArray;

    mainActivity.runOnUiThread(new Runnable() {
      public void run() {
        //MobileCore.init(mainActivity, devHash, lt, finalAdArray);
        switch(finalAdArray.length) {
          case 1:
            MobileCore.init(mainActivity, devHash, lt, finalAdArray[0]);
            break;
          case 2:
            MobileCore.init(mainActivity, devHash, lt, finalAdArray[0], finalAdArray[1]);
            break;
          case 3:
            MobileCore.init(mainActivity, devHash, lt, finalAdArray[0], finalAdArray[1], finalAdArray[2]);
            break;
          case 4:
            MobileCore.init(mainActivity, devHash, lt, finalAdArray[0], finalAdArray[1], finalAdArray[2], finalAdArray[3]);
            break;
          case 5:
            MobileCore.init(mainActivity, devHash, lt, finalAdArray[0], finalAdArray[1], finalAdArray[2], finalAdArray[3], finalAdArray[4]);
            break;
          default:
            MobileCore.init(mainActivity, devHash, lt, MobileCore.AD_UNITS.DIRECT_TO_MARKET, MobileCore.AD_UNITS.INTERSTITIAL, MobileCore.AD_UNITS.NATIVE_ADS, MobileCore.AD_UNITS.STICKEEZ);
            break;
        }
        // Add response function
        MobileCore.setAdUnitEventListener(new AdUnitEventListener() {
              public void onAdUnitEvent(MobileCore.AD_UNITS adUnit, EVENT_TYPE eventType) {
              }
            });
      }
    });
  }

  public static void showInterstitial(final HaxeObject callback, final int position) {
    final CallbackResponse callbackResponse = new CallbackResponse() {
      @Override
      public void onConfirmation(TYPE confirmationType) {
        callback.call0("callback");
      }
    };
    mainActivity.runOnUiThread(new Runnable() {
      public void run() {
        // Set the correct position value
        MobileCore.AD_UNIT_SHOW_TRIGGER trigger;
        switch(position) {
          case IS_APP_START:          trigger = MobileCore.AD_UNIT_SHOW_TRIGGER.APP_START; break;
          case IS_APP_EXIT:           trigger = MobileCore.AD_UNIT_SHOW_TRIGGER.APP_EXIT;  break;
          case IS_APP_BUTTON_CLICK:   trigger = MobileCore.AD_UNIT_SHOW_TRIGGER.BUTTON_CLICK; break;
          case IS_GAME_LEVEL_END_WIN: trigger = MobileCore.AD_UNIT_SHOW_TRIGGER.GAME_LEVEL_END_WIN; break;
          case IS_GAME_LEVEL_END_LOSE:trigger = MobileCore.AD_UNIT_SHOW_TRIGGER.GAME_LEVEL_END_LOSE;  break;
          case IS_CUSTOM:             trigger = MobileCore.AD_UNIT_SHOW_TRIGGER.CUSTOM; break;
          default:                    trigger = MobileCore.AD_UNIT_SHOW_TRIGGER.NOT_SET; break;
        }
        MobileCore.showInterstitial(mainActivity, trigger, callbackResponse);
      }
    });
  }

  public static boolean isInterstitialReady() {
    return MobileCore.isInterstitialReady();
  }

  public static void showStickee() {
    mainActivity.runOnUiThread(new Runnable() {
      public void run() {
        MobileCore.showStickee(mainActivity);
      }
    });
  }

  public static void hideStickee() {
    mainActivity.runOnUiThread(new Runnable() {
      public void run() {
        MobileCore.hideStickee();
      }
    });
  }

  public static boolean isStickeeShowing() {
    return MobileCore.isStickeeShowing();
  }

  public static boolean isStickeeReady() {
    return MobileCore.isStickeeReady();
  }

  public static void setStickeezReadyListener(final HaxeObject callback) {
    final OnReadyListener readyListener = new OnReadyListener() {
      @Override
      public void onReady(MobileCore.AD_UNITS adUnit) {
        if (adUnit.equals(MobileCore.AD_UNITS.STICKEEZ)){
          callback.call0("callback");
        }
      }
    };
    mainActivity.runOnUiThread(new Runnable() {
      public void run() {
        MobileCore.setStickeezReadyListener(readyListener);
      }
    });
  }
}
