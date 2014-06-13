package;


import openfl.display.Sprite;
import de.woodengames.mobilecore.MobileCore;

class Main extends Sprite {
	
	public function new () {
		super ();
        MobileCore.init("55",DEBUG, [ALL_UNITS]);
	}
	
	
}
