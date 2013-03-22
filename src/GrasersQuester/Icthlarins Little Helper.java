import com.rarebot.script.Script;
import com.rarebot.script.ScriptManifest;
import com.rarebot.script.wrappers.RSArea;
import com.rarebot.script.wrappers.RSItem;
import com.rarebot.script.wrappers.RSNPC;
import com.rarebot.script.wrappers.RSTile;
import com.rarebot.script.wrappers.RSTilePath;

@ScriptManifest(authors = {"Goku123"},version = 1.0,keywords = ("Quest"),description = "Does Icthlarin's Little Helper For You.",name = "IcthlarinsLittleHelper" )

public class IcthlarinsLittleHelper extends Script {
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
	
	//Areas
	RSArea[] CampArea={new RSArea(3197, 2927, 0, 0), new RSArea(3192, 2924, 0, 0),
			new RSArea(3186, 2925, 0, 0), new RSArea(3185, 2930, 0, 0),
			new RSArea(3190, 2934, 0, 0), new RSArea(3196, 2931, 0, 0),
			new RSArea(3200, 2927, 0, 0) };
	RSArea[] SophanemArea={new RSArea(3294, 2785, 0, 0), new RSArea(3292, 2790, 0, 0),
			new RSArea(3286, 2787, 0, 0), new RSArea(3291, 2782, 0, 0),
			new RSArea(3295, 2781, 0, 0)};
	
	/*Locations*/
	boolean playerLocationCamp = false;
	boolean playerLocationSophanem = false;
			
	public final RSTile[] PathtoQuestStart = {
			new RSTile(3216, 2955, 0), new RSTile(3220, 2951, 0),
			new RSTile(3226, 2947, 0), new RSTile(3231, 2941, 0),
			new RSTile(3235, 2935, 0), new RSTile(3240, 2930, 0),
			new RSTile(3244, 2925, 0), new RSTile(3249, 2922, 0),
			new RSTile(3256, 2920, 0), new RSTile(3262, 2917, 0),
			new RSTile(3268, 2914, 0), new RSTile(3273, 2909, 0),
			new RSTile(3279, 2906, 0), new RSTile(3284, 2901, 0),
			new RSTile(3289, 2896, 0), new RSTile(3292, 2891, 0),
			new RSTile(3296, 2885, 0), new RSTile(3299, 2878, 0),
			new RSTile(3301, 2872, 0), new RSTile(3304, 2866, 0),
			new RSTile(3308, 2860, 0), new RSTile(3309, 2854, 0),
			new RSTile(3311, 2847, 0), new RSTile(3315, 2844, 0)
	};
	RSTilePath TilesToQuestStart1;
	
	public boolean OnStart() {   
  log("Welcome to Icthlarin's Little Helper Quest Solver by Goku123");
  return false;
 }
 
 @Override
 public int loop() {
     antiban1();
     antiban2();
     PathtoQuestStart();
     WalkR();
     if(playerIsInCamp())
     TalktoWanderer();
     if(playerIsInSophanem())
    	 SpeaktoKlenter();
     TouchDoor();
     Eating();
     PathPastTraps();
  return random(300, 500);

    }
 
 private void WalkR() {
	 TilesToQuestStart1.traverse();
	 TilesToQuestStart1.reverse();
}

@SuppressWarnings("deprecation")
	private void Eating() {
		 if(getMyPlayer().getHPPercent() < 50){
		     RSItem food = inventory.getItem(FOODID);
		     if ( food != null) {
		          food.doAction("Eat");
		          log.severe("Eating...");
		     } else {
				                  sleep(500);
				                  return;
		     }
		 }
	}

	private void PathPastTraps() {
	// TODO Auto-generated method stub
	
}

	private void PathtoQuestStart() {
		TilesToQuestStart1= walking.newTilePath(PathtoQuestStart);
	}

	private void TouchDoor() {
	// TODO Auto-generated method stub
	
}

	private void SpeaktoKlenter() {
		RSNPC Klenter = npcs.getNearest(2014);
		if(Klenter != null); {
			Klenter.interact("Talk-to");
		        if(interfaces.getComponent(232, 2).isValid());
		interfaces.getComponent(232, 2).doClick();
		sleep(500,1000);
		if(interfaces.canContinue());
		interfaces.clickContinue();
		sleep(500,1000);
		}
	}

	private boolean playerIsInSophanem() {
		if (SophanemArea.equals(getMyPlayer().getLocation())){
            return true;
    }
    return false;
}

	private boolean playerIsInCamp() {
		if (CampArea.equals(getMyPlayer().getLocation())){
            return true;
    }
    return false;
}

	private void TalktoWanderer() {
		RSNPC Wanderer = npcs.getNearest(2005,2006);
		if(Wanderer != null); {
			Wanderer.interact("Talk-to");
		        if(interfaces.getComponent(232, 2).isValid());
		interfaces.getComponent(232, 2).doClick();
		sleep(500,1000);
		if(interfaces.canContinue());
		interfaces.clickContinue();
		sleep(500,1000);
		if(interfaces.getComponent(1188, 13).isValid());
		interfaces.getComponent(1188, 13).doClick();
		if(interfaces.getComponent(1188, 2).isValid());
		interfaces.getComponent(1188, 2).doClick();
		//Click Option 4
		}
}

	//**********Antiban********\\
    private void antiban1() {
    	int r = random(0, 20);
    	switch (r) {
    	case 1:
    		break;
    	case 3:
    		mouse.moveSlightly();
    		sleep(300, 600);
    		break;
    	case 5:
    		break;
    	case 6:
    		break;
    	case 7:
    		mouse.moveRandomly(10, 50);
    		break;
    	case 8:
    		break;
    	case 9:
    		break;
    	case 10:
    		mouse.moveOffScreen();
    		sleep (random(2000, 3000));
    		break;
    	case 12:
    		break;
    	case 15:
    		break;
    	case 16:
    		mouse.moveSlightly();
    		sleep(300, 600);
    		break;
    	case 17:
    		break;
    	}
    }
    		
    public final void antiban2() {
        int rr = random(0, 20);
        switch(rr) {
            case 1:
camera.setAngle(random(0, 350));
camera.setPitch(random(500, 5000));
break;
            case 2:
            	break;
            case 3:
camera.setAngle(random(145,360));
camera.setPitch(random(100, 4099));
break;
            case 4:
            	break;
            case 5:
camera.setAngle(random(0, 145));
camera.setPitch(random(4099, 8000));
break;
            case 6:
            	break;
            case 7:
            	break;
            case 8:
camera.setAngle(random(0, 360));
camera.setPitch(random(8000, 12000));
break;
            case 9:
            	break;
            case 10: 
            	break;
            case 11:
camera.setAngle(random(100, 300));
camera.setPitch(random(12000, 16000));
break;
            case 12:
            	break;
            case 13:
            	break;
            case 15:
camera.moveRandomly (1200);
sleep(random(300, 500));
break;
            case 16:
            	break;
            case 17:
            	break;
            case 19:
camera.moveRandomly(random(750, 1250));
sleep(random(300, 500));
break;
        }
    }

public void onFinish() {
	  log("Thanks for using my script");
	}
}
