import com.rarebot.script.Script;
import com.rarebot.script.ScriptManifest;
import com.rarebot.script.methods.Magic;
import com.rarebot.script.wrappers.RSNPC;
import com.rarebot.script.wrappers.RSTile;
import com.rarebot.script.wrappers.RSTilePath;
 
@ScriptManifest(authors = {"Goku123"},version = 1.0,keywords = ("Base, Antiban"),description = "Does FreeingtheMountainDwarf For you",name = "FreeingtheMountainDwarf" )
 
public class FreeingtheMountainDwarf extends Script {
  int[] Egg={1944};
	int[] BucketofMilk={1927};
	int[] BowlofWater={1921};
	int[] PotofFoul={1933};
	int[] FallyGate={84};
	int[] AsgoldianAles={};
	
	//People
	int[] OldDwarf={301,3401};
	int[] Kaylee={3217};
	
	public final RSTile[] TilesToHeroesGuild = {
			new RSTile(2964, 3382, 0), new RSTile(2964, 3389, 0),
			new RSTile(2965, 3396, 0), new RSTile(2969, 3401, 0),
			new RSTile(2970, 3407, 0), new RSTile(2965, 3409, 0),
			new RSTile(2958, 3410, 0), new RSTile(2952, 3414, 0),
			new RSTile(2950, 3421, 0), new RSTile(2948, 3427, 0),
			new RSTile(2949, 3433, 0), new RSTile(2948, 3439, 0),
			new RSTile(2941, 3440, 0), new RSTile(2933, 3440, 0),
			new RSTile(2926, 3439, 0), new RSTile(2919, 3437, 0),
			new RSTile(2915, 3432, 0), new RSTile(2916, 3426, 0),
			new RSTile(2911, 3421, 0), new RSTile(2905, 3419, 0),
			new RSTile(2899, 3417, 0), new RSTile(2893, 3414, 0),
			new RSTile(2887, 3417, 0), new RSTile(2881, 3421, 0),
			new RSTile(2881, 3427, 0), new RSTile(2883, 3434, 0),new RSTile(2883, 3440, 0), new RSTile(2885, 3446, 0),new RSTile(2886, 3452, 0), new RSTile(2888, 3456, 0),new RSTile(2883, 3458, 0), new RSTile(2877, 3462, 0)};
	RSTilePath TilesToHeroesGuild1;
	public final RSTile[] TilesToRisingSunTavern ={new RSTile(2964,3378), new RSTile(2955, 3375)};
	RSTilePath TilesToRisingSunTavern1;
	
	public boolean OnStart() {
  log("Welcome to FreeingtheMountainDwarf by Goku123");
  return false;
 }
 
 @Override
 public int loop() {
     PathToHeroesGuild(); //Walks to the Heros Guild From fally Teleport Spot 
     walkR();
     EnterCave();
     //Teleports to fally
     PathToRisingSunTavern();
     WalkR1();
	return 0;
}

@SuppressWarnings("unused")
private void TalktoKaylee() {
	RSNPC Kaylee = npcs.getNearest(3217);
	if(Kaylee != null); {
		Kaylee.interact("Talk-to");
	        if(interfaces.getComponent(232, 2).isValid());
	interfaces.getComponent(232, 2).doClick();
	sleep(500,1000);
	if(interfaces.canContinue());
	interfaces.clickContinue();
	sleep(500,1000);
	}
}

private void WalkR1() {
	TilesToRisingSunTavern1.traverse();
	
}

private void PathToRisingSunTavern() {
	TilesToRisingSunTavern1 = walking.newTilePath(TilesToRisingSunTavern);
	return;	
}

private void EnterCave() {
	// TODO Auto-generated method stub
	
}

@SuppressWarnings("unused")
private void TalkToAOldDwarf() {
	RSNPC OldDwarf = npcs.getNearest(301,3401);
	if(OldDwarf != null); {
		OldDwarf.interact("Talk-to");
	        if(interfaces.getComponent(232, 2).isValid());
	interfaces.getComponent(232, 2).doClick();
	sleep(500,1000);
	if(interfaces.canContinue());
	interfaces.clickContinue();
	sleep(500,1000);
	}
}

private void walkR() {
	TilesToHeroesGuild1.traverse();
	TilesToHeroesGuild1.reverse();
}

private void PathToHeroesGuild() {
	TilesToHeroesGuild1 = walking.newTilePath(TilesToHeroesGuild);
	return;	
}
    
@SuppressWarnings("unused")
private void TeleporttoFally() {
	magic.castSpell(Magic.SPELL_FALADOR_TELEPORT);
	sleep(50000,100000);
}

public boolean OnFinish() {
	  log("Thanks for using FreeingtheMountainDwarf by Goku123");
	  return false;
	 }
}
