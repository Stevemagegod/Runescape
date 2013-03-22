import com.rarebot.script.Script;
import com.rarebot.script.ScriptManifest;
import com.rarebot.script.wrappers.RSComponent;
import com.rarebot.script.wrappers.RSNPC;

@ScriptManifest(authors = {"Goku123"},version = 1.0,keywords = ("Quests"), description = "Does Quests for you",name = "Quester")

public class FairyTalePartIII extends Script {
  
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
int[] VialofWater={227};
int[] MagicEssenceUnf={9019};
int[] PestleAndMortar={233, 234, 13587};
int[] NuffsCertificate={9025};
int[] FairyChef={3322};
int[] MartinTheMasterGardener={3299};
int[] ZanarisDoor={}; 
int talkage;		
 public boolean OnStart() {   
  log("Welcome to AIOQuester by Goku123");
  return false;
 }
 
 @Override
 public int loop() {
    	 TalkToMartinTheMasterGardener();
		return 0;
 }
 
	@SuppressWarnings("deprecation")
	private void TalkToMartinTheMasterGardener() {
	 RSComponent talkage = interfaces.getComponent(137, 163); 
     camera.setPitch(true);
     RSNPC MartinTheMasterGardener = npcs.getNearest("MartinTheMasterGardener");
     MartinTheMasterGardener.doAction("Talk");
     sleep(1500);         
     if(talkage.isValid()){
            while(interfaces.canContinue()){
                    interfaces.clickContinue();
            }
             mouse.move((int) random(122, 410), (int) random(239,467));
             mouse.click(true);
             sleep(1500);
             while(interfaces.canContinue()){
                            interfaces.clickContinue();
                    }
                                  }        
     }
}
