package GrasersTasks;

import org.g;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class VarrockEasy extends ActiveScript {

	private Tree jobContainer = null;
	public int adrenaline;
	private SceneObject lastClicked = null;
	
	//itemn ids
	public int[] Bones = {526, 532, 530, 528, 3183, 2859, 534, 3125, 4834, 14793, 4812, 3123, 4832, 6729, 536};
	public int[] cooldowns = { 0, 36, 73, 77, 81, 85, 89, 93, 97, 101, 105,109, 113 };
	public int hpThreshold = 12;
	
	//People
	int Aubury;
	int EstateAgent;
	int HaigHalen;
	int Elsie;
	int[] StrayDog={5917};
	
	//Objects
	int Fence;
	public int treeID = 51843;
	
	//NPCs
	public NPC nearestMonster;
	
	//Paths
	Tile[] ReadAllAboutIt  = new Tile[] { new Tile(3210, 3420, 0), new Tile(3213, 3423, 0), new Tile(3216, 3427, 0), 
			new Tile(3218, 3430, 0), new Tile(3217, 3432, 0) };
	TilePath ReadAllAboutIt1; //Walks To Benny from Thessalia
	
	public boolean isAbilityActive(int index) {
		return Widgets.get(640).getChild(cooldowns[index]).getTextureId() == 14521;
		}
	    
	    public class KingoftheCastle extends Node {

			@Override
			public boolean activate() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				
			}
	    	
	    }
	    
	    public class TradeingThessalia extends Node {

			@Override
			public boolean activate() {
				
				return false;
			}

			@Override
			public void execute() {
				NPC Thessalia1 = NPCs.getNearest(548,819,808);
				if (Thessalia1 != null && Thessalia1.validate()) {
					if (Thessalia1.isOnScreen()) {
						Thessalia1.interact("Change-Clothes");
							Task.sleep(1000, 2000);
					}
				}
			}
	    	
	    }
	    
	    public class SticktheKnifeIn extends Node {

			@Override
			public boolean activate() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				
			}
	    	
	    }
	    
	    public class Enterthe2ndSOS extends Node {

			@Override
			public boolean activate() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				
			}
	    	
	    }
	    
	    public class EnterEdgevilleDungeon extends Node {

			@Override
			public boolean activate() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				
			}
	    	
	    }
	    
	    public class Dog extends Node {

			@Override
			public boolean activate() {
				
				return false;
			}

			@Override
			public void execute() {
				if(Players.getLocal().isIdle()) {
					if(NPCs.getLoaded().equals(StrayDog)) {
						 NPCs.getNearest (new Filter<NPC>() {

							@SuppressWarnings("deprecation")
							@Override
							public boolean accept(NPC StrayDog) {
								if(StrayDog.isOnScreen() && Inventory.containsOneOf(Bones)); //Determines if this entity is on screen and Inventory Contains Bones
								Inventory.getItem(Bones); //Gets the first inventory item whose id matches with any of the provided ids.
								Inventory.getSelectedItem().getId(); //Gets the inventory item that is currently selected.
								StrayDog.interact("Give Bone"); //Should be Interacting with the Dog. 
								return false;
							}
							 
						 });
					}
				}
				if(StrayDog != null);
				
			}
	    	
	    }

	    public class Fight extends Node {
	    	@Override
	    	public boolean activate() {
	    		return !Players.getLocal().isInCombat();
	    	}

	    	@Override
	    	public void execute() {
	    		if (!Players.getLocal().isInCombat() && Players.getLocal().isIdle()) {
	    			if (!Players.getLocal().isInCombat()) {
	    				if (nearestMonster != null) {
	    					nearestMonster.interact("Attack");
	    				}
	    				if (!nearestMonster.isOnScreen()) {
	    					Walking.walk(nearestMonster);
	    				}
	    			}
	    		}
	    	}
	
	public class Abilities extends Node {
		@Override
		public boolean activate() {
			return true;
		}

		@Override
		public void execute() {
			if(isAbilityActive(10) && adrenaline == 100 && Widgets.get(748).getChild(5).getHeight() >= 12 ) {
				Keyboard.sendKey("0".charAt(0));
				//log.info("healed");
			} else if(!Widgets.get(464).getChild(6).getText().equalsIgnoreCase("no target")){
				int rand = Random.nextInt(1, 7);
				if(isAbilityActive(rand)) {
					Keyboard.sendKey(Integer.toString(rand).charAt(0));
					//log.info("basic ability " + rand);
					Task.sleep(2500, 4000);
				} else {
					Task.sleep(250, 300);
				}
			}
		}
	}
	    
	    public class Woodcutting extends Node {

	    	@Override
	    	public boolean activate() {
	    		return !Inventory.isFull() && SceneEntities.getNearest(treeID) != null && !isChopping();
	    	}

	    	@Override
	    	public void execute() {
	    		SceneObject tree = SceneEntities.getNearest(treeID);
	    		if (tree != null) {
	    			if (tree.isOnScreen()) {
	    				if (tree.interact("Chop")) {
	    					lastClicked = tree;
	    					final Timer t = new Timer(1000);
	    					while (t.isRunning() && !isChopping()) {
	    						if (Players.getLocal().isMoving()) {
	    							t.reset();
	    						}
	    						Task.sleep(50);
	    					}
	    				}
	    			} else {
	    				Camera.turnTo(tree);
	    				if (!tree.isOnScreen()) {
	    					Walking.walk(tree);
	    				}
	    			}
	    		}
	    	}

	    }
	    
	    private boolean isChopping() {
 		if (lastClicked != null && !lastClicked.validate()) {
 			lastClicked = null;
 			return false;
 		}
 		return lastClicked != null && Players.getLocal().getAnimation() != -1;
 	}

}
	    
	    public class Fishing extends Node {

			@Override
			public boolean activate() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				
			}
	    	
	    }
	    
	    public boolean checkInv() {
			if(Players.getLocal().isIdle()) {
				if(Inventory.isFull());
				return true;
			}
			if(!Inventory.isFull());
			return false;
		}
	    
	    public void teleport() {
			int[] Aubury={5913};
			if(Players.getLocal().isIdle()) {
				NPC Aubury1 = NPCs.getNearest(Aubury);
				if(Aubury1.isOnScreen()) {
					if(Aubury1 !=null);
					Aubury1.interact("Teleport");
				}
				else if(!Aubury1.isOnScreen()) {
					Camera.turnTo(Aubury1);
				}
			}
		}
	    
	    public class Mine extends Node {

			@Override
			public boolean activate() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				
			}
	    	
	    }

	public int loop() {
		if (jobContainer != null) {
			final Node job = jobContainer.state();
			if (job != null) {
				jobContainer.set(job);
				//getContainer().submit(job);
				job.join();
			}
		}
		return 0;
	}

@SuppressWarnings("unused")
private void TalkToBenny() {
	NPC Benny = NPCs.getNearest(5925);
	if (Benny != null && Benny.validate()) {
		if (Benny.isOnScreen()) {
			Benny.interact("Talk-To");
				Task.sleep(1000, 2000);
				Widgets.getContinue().click(true);
		} else {
			if (Calculations.distanceTo(Benny) > 7) {
				Walking.walk(Benny);
			} else {
				Camera.turnTo(Benny);
			}
		}
	}
}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public boolean activate() {
		// TODO Auto-generated method stub
		return false;
	}

}
