import com.rarebot.script.Script;
import com.rarebot.script.ScriptManifest;
import com.rarebot.script.wrappers.RSCharacter;
import com.rarebot.script.wrappers.RSNPC;

//Fairy Tale Part III - Battle at Orks Rift
//Recipe for Disaster
//Grim Tales
//Icthlarin's Little Helper
//Regicide

@ScriptManifest(authors = {"Goku123"},version = 1.0,keywords = ("Quests"), description = "Does Quests for you",name = "AIOQuester")

public class AIOQuester extends Script {
  
	private static final RSCharacter Chest = null;
	/*VARIABLES FOR Fairy Tale Part III - Battle at Orks Rift*/
int[] FairyNuff={3303, 4434};
int[] FairyVeryWise={4442, 11435};
int[] QueensSecatuers={7410, 9020};
int[] FairyGodfather={3304, 4433, 11323, 11324, 11325, 11326, 11327, 11328, 11346, 11347};
int[] FairyRing={12003,12094,12128,	14058,	14061,	14064,	14067,	14070,	 14073,	 14076,	 14079,	 14082,	 14085,	 14088,	 14091,	 14094,	 14097,	 14100,	 14103,	 14106,	 14109,	 14112,	 
14115,	 14118,	 14121,	 14127,	 14130,	 14133,	 14136,	 14142,	 14145,	 14148,	 14151,	 14154,	 14160,	 16184,	 17012,	 23047,	 27325,	 37727,	 52609,	 52613,	 52666,	 52673,	 52679,	 61498};
int[] MagicEssencePotion={};
int[] FairyQueen={653,4437,11380,11381,11436};
int[] StarFlower={9017};
int[] GorakClawPowder={9018}; 
int[] Goraklevel145={};
int[] GorakClaws={9016};
int[] VialofWater={};
int[] MagicEssenceUnf={9019};
int[] PestleAndMortar={233, 234, 13587};
int[] NuffsCertificate={9025};
int[] FairyChef={3322};
int[] MartinTheMasterGardener={3299};
		
 public boolean OnStart() {   
  log("Welcome to AIOQuester by Goku123");
  return false;
 }
 
 @Override
 public int loop() {
     antiban1();
     antiban2();
     if (getMyPlayer().getAnimation() != -1) {
    	 TalkToMartinTheMasterGardener();
         return random(500, 1000);
     }
	return 0;
     }
 

	private void TalkToMartinTheMasterGardener() {
RSNPC MartinTheMasterGardener = npcs.getNearest(3299);
		
		if (MartinTheMasterGardener != null) {
			if (MartinTheMasterGardener.isOnScreen()) {
				Chest.interact("Talk");
				sleep(1000, 1200);
				return;
			}
			else if (!MartinTheMasterGardener.isOnScreen()) {
				camera.turnTo(MartinTheMasterGardener);
				return;
			}
		}
		
     
  return;
	
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
}
