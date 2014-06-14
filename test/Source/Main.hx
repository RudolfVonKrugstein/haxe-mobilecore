package;

import mobilecore.MobileCore;

class Main {
	
	static public function main () {
        MobileCore.init("55",DEBUG, [OFFERWALL]);
        MobileCore.showOfferWall();
	}
}
