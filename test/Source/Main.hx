package;

import mobilecore.MobileCore;

class Main {
	
	static public function main () {
        MobileCore.init("48N7TAAGCTATXI3A9VKFJ12MIPGUA",DEBUG, [OFFERWALL]);

        MobileCore.showOfferWall(doneWithOfferwall, true);
	}

    static private function doneWithOfferwall() {
      trace("Done with offerwall");
      MobileCore.setStickeezReadyListener(onStickeezReady);
    }

    static private function onStickeezReady() {
      MobileCore.showStickee();
    }
}
