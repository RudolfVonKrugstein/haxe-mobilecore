package;

import openfl.display.Sprite;
import mobilecore.MobileCore;

class Main extends Sprite {
	
	public function new () {
		super ();
        MobileCore.init("55",DEBUG, [ALL_UNITS]);
	}
}
