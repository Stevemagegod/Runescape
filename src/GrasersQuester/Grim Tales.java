import com.rarebot.script.Script;
import com.rarebot.script.ScriptManifest;

@ScriptManifest(authors = {"Goku123"},version = 1.0,keywords = ("Quests"), description = "Does Quests for you",name = "Quester")

public class GrimTales extends Script {
  /*VARIABLES FOR Grim Tales*/
	int[] Cleantarromin={253, 254};
	int[] Vialsofwater={227}; 
	int[] Seeddibber={5343, 5344};
	int[] Wateringcan={5331, 5332, 5333, 5334, 5335, 5336, 5337, 5338, 5339, 5340, 6797, 18682};
	int[] Leathergloves={1059, 1060};
	int[] Griffinfeather={11196};
	int[] Shrunkogleroots={11205};
	int[] Shrinkingpotionrecipe={};
	int[] Todolist={11203};
	int[] Shrinkmequickpotions={11204};
	int[] pendant={14682, 14683};
	int[] Dwarvenhelmet={11200, 11201};
	int[] Goldengoblin={11210};
	
	//Paths
	
	@Override
	public int loop() {
		WalktoWhiteWolfMountain();
		return 0;
	}

	private void WalktoWhiteWolfMountain() {
		// TODO Auto-generated method stub
		
	}
}
