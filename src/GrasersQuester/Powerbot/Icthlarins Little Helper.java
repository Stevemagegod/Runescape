import java.awt.event.KeyEvent;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.client.RSItem;

@Manifest
(
               
                name = "IcthlarinsLittleHelperPowerbot",
                authors = "Graser",
                version = 1.0,
                description ="Does Icthlarin's Little Helper For You"
)

public class IcthlarinsLittleHelperPowerbot extends ActiveScript {
  int[] Tinderbox={590};
	int[] Willowlogs={1519};
	int[] Bucketofsap={4687,4688};
	int[] Bagofsalt={4161};
	int[] Ghostspeakamulet={552,4250};
	int[] WaterskinID={1823,1825,1831};
	int[] Coins={995};
	int[] Canopicjar={14807};
	int[] UnholySymbolIcthlarins={17384};
	int[] Sphinxstoken={4691};
	int[] LinenID={4684,4685};
	int[] FullBucketID={3722,4693};
	int[] PileofSaltID={4689,4690};
	int[] HolysymbolIcthlarins={};
	int[] FOODID = {1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980,
            7223, 6297, 6293, 6295, 6299, 7521, 9988, 7228, 2878, 7568, 2343,
            1861, 13433, 315, 325, 319, 3144, 347, 355, 333, 339, 351, 329,
            3381, 361, 10136, 5003, 379, 365, 373, 7946, 385, 397, 391, 3369,
            3371, 3373, 2309, 2325, 2333, 2327, 2331, 2323, 2335, 7178, 7180,
            7188, 7190, 7198, 7200, 7208, 7210, 7218, 7220, 2003, 2011, 2289,
            2291, 2293, 2295, 2297, 2299, 2301, 2303, 1891, 1893, 1895, 1897,
            1899, 1901, 7072, 7062, 7078, 7064, 7084, 7082, 7066, 7068, 1942,
            6701, 6703, 7054, 6705, 7056, 7060, 2130, 1985, 1993, 1989, 1978,
            5763, 5765, 1913, 5747, 1905, 5739, 1909, 5743, 1907, 1911, 5745,
            2955, 5749, 5751, 5753, 5755, 5757, 5759, 5761, 2084, 2034, 2048,
            2036, 2217, 2213, 2205, 2209, 2054, 2040, 2080, 2277, 2225, 2255,
            2221, 2253, 2219, 2281, 2227, 2223, 2191, 2233, 2092, 2032, 2074,
            2030, 2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971, 4608,
            1883, 1885,1982};
	final int healAt = 390;  ///Healing
	
	//People
	int[] Wanderer={2005,2006};
	int[] Klenter={2014};
	int[] Sphinx={1990,2637};
	int[] HighPriest={};
	
	//Objects
	int[] SophanemDoor={};
	
	/*Locations*/
	boolean playerLocationCamp = false;
	boolean playerLocationSophanem = false;
			
	public final Tile[] PathtoQuestStart = {
			new Tile(3350, 3002, 0), new Tile(3353, 2996, 0),
			new Tile(3355, 2989, 0), new Tile(3357, 2982, 0),
			new Tile(3358, 2976, 0), new Tile(3353, 2971, 0),
			new Tile(3347, 2966, 0), new Tile(3341, 2961, 0),
			new Tile(3337, 2957, 0), new Tile(3333, 2953, 0),
			new Tile(3328, 2950, 0), new Tile(3324, 2946, 0),
			new Tile(3319, 2942, 0), new Tile(3313, 2937, 0),
			new Tile(3306, 2934, 0), new Tile(3299, 2934, 0),
			new Tile(3292, 2935, 0), new Tile(3284, 2935, 0),
			new Tile(3278, 2934, 0), new Tile(3271, 2932, 0),
			new Tile(3264, 2932, 0), new Tile(3258, 2934, 0),
			new Tile(3252, 2932, 0), new Tile(3245, 2929, 0),
			new Tile(3238, 2931, 0), new Tile(3231, 2931, 0),
			new Tile(3225, 2929, 0), new Tile(3218, 2929, 0),
			new Tile(3212, 2929, 0), new Tile(3205, 2929, 0),
			new Tile(3198, 2930, 0), new Tile(3193, 2929, 0)
	};
 
	private class Walker extends Strategy implements Task, Condition {
		 
        @Override
        public boolean validate() {
                return !isRunning();
        }

        @Override
        public void run() {
                Tile destination;
				if(Calculations.distance(Players.getLocal().getLocation(), destination) > 2) {
                        if(!destination.canReach()) {
                                Tile nextTile = Walking.getClosestOnMap(destination);
                                Walking.findPath(nextTile).traverse();
                        } else {
                                Walking.findPath(destination).traverse();
                        }
                        checkEnergy();
                } else if(Calculations.distance(Players.getLocal().getLocation(), destination) < 2) {
                        stop();
                }
        }

		private void checkEnergy() {
			int lowEnergy= 20;
			if(!Walking.isRunEnabled() && Walking.getEnergy() >= lowEnergy) {
                Walking.setRun(true);
                Time.sleep(600, 1000);
        }
        if(Walking.isRunEnabled()) {
                lowEnergy = Random.nextInt(0, 20);
        }
}

}
	
	private class AntiBan extends Strategy implements Task, Condition {
		 
        @Override
        public boolean validate() {
                return !isRunning();
        }

        @Override
        public void run() {
                switch(Random.nextInt(0, 20)) {
                case 0:
                        Camera.setAngle(Random.nextInt(-100, 100));
                        break;
                case 5:
                        Camera.setPitch(Random.nextInt(42, 90));
                        break;
                }
        }

}
	
	private class Rest extends Strategy implements Task, Condition {
		 
        @Override
        public boolean validate() {
                boolean useRest= false;
				return useRest;
        }

        @Override
        public void run() {
                int lowEnergy= 20;;
				if(Walking.getEnergy() < lowEnergy) {
                        lowEnergy = Random.nextInt(0, 20);
                        int restAnim= 11786;
						if(Players.getLocal().getAnimation() != restAnim) {
                                Widgets.get(750).getChild(2).interact("Rest");
                                Time.sleep(2000, 3000);
                                int resumeEnergy = Random.nextInt(80, 100);
                                for(int i = 0 ; i < 10000 && Walking.getEnergy() < resumeEnergy ; i++) {
                                        Time.sleep(100);
                                }
                        }
                }
        }

}

private class eat implements Task, Condition {

    @Override
    public void run() {
    	final Item foods = Inventory.getItem(FOODID);
    	
				foods.getWidgetChild().interact("Eat");
				Time.sleep(60);
		
		
    }
    public boolean validate() {
    	int currentHP = Integer.parseInt(Widgets.get(748, 8).getText());
        int eatValue = 60 ;
        return (currentHP <= eatValue);
    }
}

	private Object getMyPlayer() {
	// TODO Auto-generated method stub
	return null;
}

	private void PathPastTraps() {
	// TODO Auto-generated method stub
	
}

	private void TouchDoor() {
		int Door = 0000;
        SceneObject SophanemDoor = SceneEntities.getNearest(SophanemDoor);     
        if(SophanemDoor.isOnScreen())
        {
                SophanemDoor.interact("ENTER");
        }
        Time.sleep(Random.nextInt(1200,1500));
        char enter = KeyEvent.VK_SPACE;
        Keyboard.pressKey(enter,0, 0);	
}

	private final class Talking extends Strategy implements Task {

		@Override
		public void run() {
			TalktoWanderer();
			SpeaktoKlenter();
		}

		private void SpeaktoKlenter() {
			// TODO Auto-generated method stub
			
		}

		private void TalktoWanderer() {
			// TODO Auto-generated method stub
			
		}
		
	}


@Override
protected void setup() {
	Walking.setRun(true);
	
}
}
