import com.rarebot.script.Script;
import com.rarebot.script.ScriptManifest;
import com.rarebot.script.wrappers.RSNPC;
import com.rarebot.script.wrappers.RSTile;

@ScriptManifest(authors = {"Goku123"},version = 1.0,keywords = ("Quests"), description = "Does Deadliest Catch for you",name = "DeadliestCatch")

public class DeadliestCatch extends Script {
  // People
	int[] MasterFisher={308};
	int[] Jones={9693};
	int[] Linza={6530};
	int[] TheGuns={11247};
	int[] Bonafido={5580};
	
	//Items
	int[] HarpoonPlans={20890};
	int[] SeaChart={20884};
	int[] GiantHarpoon={20886};
	int[] LowlandHeather={};
	int[] BuildersTea={};
	int[] SeaLegsPotion={20889};
	
	//Paths
	public final RSTile[] PathtoLinza = {new RSTile(2590, 3418, 0), new RSTile(2593, 3412, 0),new RSTile(2595, 3406, 0), new RSTile(2596, 3399, 0),new RSTile(2598, 3394, 0), new RSTile(2604, 3391, 0),new RSTile(2610, 3390, 0), new RSTile(2614, 3385, 0),new RSTile(2620, 3385, 0), new RSTile(2625, 3389, 0),new RSTile(2629, 3394, 0), new RSTile(2630, 3400, 0),new RSTile(2633, 3406, 0), new RSTile(2636, 3412, 0),new RSTile(2638, 3418, 0) };
	public final RSTile[] PathtoMasterFisher = {new RSTile(2589, 3416, 0), new RSTile(2592, 3410, 0),new RSTile(2596, 3407, 0), new RSTile(2600, 3401, 0),new RSTile(2604, 3396, 0), new RSTile(2606, 3392, 0)};
	
	//Object Paths
	int[] WreckageObjectPath={5726,5645,5628,5594,5593,5602,5629,5630,5708,5725};
	int[] WreckageObjectPath1={5726,5632,5653,5690,5708,5603,5590,5602,5593,5585};
	int[] WreckageObjectPath2={5726,5632,5647,5690,5708,5630,5629,5603};
	int[] WreckageObjectPath3={5726,5632,5653,5690,5630,5629,5602,5593,5596,5644};
	int[] WreckageObjectPath4={5726,5632,5653,5690,5708,5603,5592,5589,5583};
	int[] WreckageObjectPath5={5726,5645,5596,5628,5602,5629,5603,5708,5690,5653};
	int[] WreckageObjectPath6={5726,5632,5653,5690,5708,5603,5592,5589};

	@Override
	public int loop() {
		SpeaktoJones();
		if(QuestStarted())
		WalktoMasterFisher();
		PickPocketMasterFisher();
	    SpeaktoTheGuns();{
		PickLowlandHeather();
		CombineHeatherandTea();
		return 0;
		}}

	private boolean QuestStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	private void WalktoMasterFisher() {
		walking.newTilePath(PathtoMasterFisher);
		
	}

	private void SpeaktoJones() {
		{
			RSNPC Jones = npcs.getNearest(9693);
			if(Jones != null); {
				Jones.interact("Talk-to");
			        if(interfaces.getComponent(232, 2).isValid());
			interfaces.getComponent(232, 2).doClick();
			sleep(500,1000);
			if(interfaces.canContinue());
			interfaces.clickContinue();
			sleep(500,1000);
			
			        return;
			        }
			}	    
			
			}

	private void CombineHeatherandTea() {
		// TODO Auto-generated method stub
		
	}

	private void PickLowlandHeather() {
		// TODO Auto-generated method stub
		
	}

	private void SpeaktoTheGuns() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings({ "deprecation" })
	private void PickPocketMasterFisher() {
		RSNPC MasterFisher = npcs.getNearest((308));
        log("Thieving from Fisher!");
        if (MasterFisher != null) {
                if (MasterFisher.doAction("Pickpocket")) {
                        sleep(1800, 2000);
                }
                sleep(random(100, 250));
        }
}

	
}
