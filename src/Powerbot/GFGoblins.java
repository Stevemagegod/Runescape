import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;

@Manifest(authors = ("Graser"), name = "Good Fight Goblins", description = "Kills Goblins", version = 2)
public class GFGoblins extends ActiveScript {

	/**
	 * @param args Untested
	 */
	
	boolean weareinArea = false;
	boolean wearedead = false;
	int Goblins = 0; //TODO
	Tile[] DeathWalk = null; //TODO

	@Override
	public int loop() {
		if(weareinArea) //if we are in the Goblin area
			NPCs.getNearest(Goblins);//we should get The nearest Goblin with one of these Ids if any present, else null.
		if(NPCs.getNearest(Goblins).isOnScreen()) //Determines if this entity is on screen.
			NPCs.getNearest(Goblins).click(true); //Clicks this entity.
		if(NPCs.getNearest(Goblins).isInCombat()) //if the Goblin is in Combat
			try {
				Players.getLocal().wait(); //we should try to wait until the Goblin is dead
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		do
			if(NPCs.getNearest(Goblins).getInteracting() != null); //checks if goblins are interacting
		while(Players.getLocal().isInCombat());  //while we are in combat
		Players.getLocal().getHealthPercent(); //we should be checking are health and 
				if(Players.getLocal().getHealthPercent() <= 60) //if are health is less than 60 we need to eat
			Eating(); //We should be Eating now
		if(Players.getLocal().isIdle() && !Players.getLocal().isInCombat()) //If we are idle and we are not in Combat
			Looting(); //We should be Looting
		else
			if(wearedead) //if we are dead
				Walking.newTilePath(DeathWalk); //Walks back to Goblins from respawn point
		return 0;
	}

	private void Eating() {
		// TODO Auto-generated method stub
		
	}

	private void Looting() {
		// TODO Auto-generated method stub
		
	}

}
