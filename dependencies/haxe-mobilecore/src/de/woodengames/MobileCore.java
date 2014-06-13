package de.woodengames;

import android.content.Context;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Map;
import java.util.HashMap;

import com.ironsource.mobilcore.MobileCore;

import org.haxe.extension.Extension;

public class MobileCore extends Extension
{
  private static boolean _showOnPause;
  private static Bundle _saveInstanceState;

  // These constants are also defined in haxe
  final private static AD_STICKEEZ = 1;
  final private static AD_SLIDER = 2;
  final private static AD_OVERWALL = 4;
  final private static AD_DIRECT_TO_MARKET = 8;
  final private static AD_ALL = 16;


  private static String tag = "HaxeMobileCore";

  public StartApp()
  {
    Log.d(tag, "Construct Haxe MobileCore");
    _showOnPause = false;
  }

  /**
   * Called when an activity you launched exits, giving you the requestCode
   * you started it with, the resultCode it returned, and any additional data
   * from it.
   */
  public boolean onActivityResult (int requestCode, int resultCode, Intent data)
  {
    return true;
  }


  /**
   * Called when the activity is starting.
   */
  public void onCreate(Bundle savedInstanceState)
  {
    _saveInstanceState = savedInstanceState;
  }


  /**
   * Perform any final cleanup before an activity is destroyed.
   */
  public void onDestroy()
  {
  }


  /**
   * Called as part of the activity lifecycle when an activity is going into
   * the background, but has not (yet) been killed.
   */
  public void onPause()
  {
    if(_showOnPause)
    {
    }
  }


  /**
   * Called after {@link #onStop} when the current activity is being
   * re-displayed to the user (the user has navigated back to it).
   */
  public void onRestart()
  {
  }


  /**
   * Called after {@link #onRestart}, or {@link #onPause}, for your activity
   * to start interacting with the user.
   */
  public void onResume()
  {
  }


  /**
   * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when
   * the activity had been stopped, but is now again being displayed to the
   * user.
   */
  public void onStart()
  {
  }


  /**
   * Called when the activity is no longer visible to the user, because
   * another activity has been resumed and is covering this one.
   */
  public void onStop()
  {
  }

  public static void init(String devHash, int adUnits)
  {
    // Build a list of wanted ad units
    int numAdUnits = 0;
    for (int i : {1,2,4,8,16}) {
      if (adUnits & i) ++numAdUnits;
    }
    AD_UNITS[] adArray = new AD_UNITS[numAdUnits];
    int c = 0;
    if (adUnits & AD_STICKEEZ) {
      AD_UNITS[c] = AD_UNITS.STICKEEZ; ++c;
    }
    if (adUnits & AD_SLIDER) {
      AD_UNITS[c] = AD_UNITS.SLIDER; ++c;
    }
    if (adUnits & AD_OVERWALL) {
      AD_UNITS[c] = AD_UNITS.OVERWALL; ++c;
    }
    if (adUnits & AD_DIRECT_TO_MARKET) {
      AD_UNITS[c] = AD_UNITS.DIRECT_TO_MARKET; ++c;
    }
    if (adUnits & AD_ALL) {
      AD_UNITS[c] = AD_UNITS.ALL; ++c;
    }
    MobileCore.init(mainActivity, devHash, adArray);

  }

  public static void showOverwall()
  {
  }
}
