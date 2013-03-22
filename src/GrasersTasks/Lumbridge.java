package GrasersTasks;
import java.awt.Graphics;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

@Manifest(authors = ("Graser"), name = "Lumbridge", description = "AIO Achievement Diary Script", version = 0.1)
public class Lumbridge extends ActiveScript implements PaintListener,MessageListener {
	
	//itemn ids
	int Pickaxe;
	int Hatchet;
	int RawRatMeat=2134;
	int Coal;
	int FishingRod;
	int FishingBait;
	int SoftClay;
	int RingMould;
	int Tinderbox;
	int WaterTalisman;
	int Essence;
	int GhostspeakAmulet;

	//Other Variables
	private Area placeToMine;
	boolean atMine = false;
	boolean TaskDone = false;
	int LumbridgeCastle=0;
	int Tasks;
	String Lastmessage;
	String status;
	public int adrenaline;
	public long startTime = System.currentTimeMillis();

	//People
	int[] Aubury={5913};
	int[] FatherUrhney={458};
	int[] DraynorTownCrier;
	int[] Horacio={741};
	int[] ExplorerJack;
	int[] LumbridgeSage={};
	int[] FatherAerecks={};
	int[] EstateAgent;
	int[] HaigHalen;
	int[] Elsie={2824};
	int[] StrayDog={5917};
	int[] Doomsayer={3777};

	//Objects
	int[] DoortoHoraciosRoom={36845,36844,33819};
	int[] Horaciostairs={36777,36771,33795,36772};
	int[] LumbridgeBankBoothStairs={36772};
	int[] Flag={37335};
	int[] ZanarisDoor={4343};
	int[] WaterTemple={2454};
	int[] FishingSpot={85};
	int Fence;
	int FatherAereckselectionofgravestones;
	int OrganintheLumbridgeChurch;
	int BellintheLumbridgeChurch;
	int AlKharidGate;
	
	//Rock ids
	int ironoreRock[]={37309,37307};
	int CopperRock;
	
	//Trees
	int[] deadtree={9385,9387,9355};

	//NPCs
	int[] Man={7876,13606};
	int[] Goblin={13613};
	int[] GiantRat={8829,8828};
	int[] Zombie;

	// Paths
	Tile[] AndItWasTHISBig = new Tile[] { new Tile(3234, 3223, 0),
			new Tile(3239, 3225, 0), new Tile(3245, 3225, 0),
			new Tile(3251, 3225, 0), new Tile(3249, 3230, 0),
			new Tile(3247, 3235, 0), new Tile(3243, 3239, 0),
			new Tile(3240, 3241, 0) }; //Walks to the Herraring Fishing Spot from the Lodestone
	Tile[] IronOn = new Tile[] { new Tile(3240, 3241, 0),
			new Tile(3245, 3240, 0), new Tile(3251, 3240, 0),
			new Tile(3256, 3238, 0), new Tile(3260, 3234, 0),
			new Tile(3265, 3232, 0), new Tile(3270, 3229, 0),
			new Tile(3276, 3229, 0), new Tile(3277, 3229, 0),
			new Tile(3299, 3286, 0) }; //Walks to Al Kahid Minning Spot from the fishing spot
	Tile[] BelterOfaSmelter = new Tile[] { new Tile(3299, 3286, 0),
			new Tile(3296, 3281, 0), new Tile(3290, 3280, 0),
			new Tile(3285, 3277, 0), new Tile(3286, 3272, 0),
			new Tile(3285, 3267, 0), new Tile(3282, 3262, 0),
			new Tile(3279, 3257, 0), new Tile(3279, 3251, 0),
			new Tile(3284, 3250, 0), new Tile(3290, 3250, 0),
			new Tile(3291, 3245, 0), new Tile(3290, 3239, 0),
			new Tile(3286, 3235, 0), new Tile(3281, 3231, 0),
			new Tile(3276, 3229, 0), new Tile(3270, 3229, 0),
			new Tile(3264, 3229, 0), new Tile(3260, 3233, 0),
			new Tile(3258, 3234, 0), new Tile(3253, 3237, 0),
			new Tile(3248, 3240, 0), new Tile(3243, 3243, 0),
			new Tile(3242, 3248, 0), new Tile(3244, 3253, 0),
			new Tile(3244, 3259, 0), new Tile(3239, 3261, 0),
			new Tile(3233, 3261, 0), new Tile(3230, 3256, 0),
			new Tile(3229, 3251, 0), new Tile(3224, 3254, 0) };
	Tile[] NowToolLookAt = new Tile[] { new Tile(3224, 3254, 0),
			new Tile(3223, 3249, 0), new Tile(3223, 3243, 0),
			new Tile(3220, 3238, 0), new Tile(3225, 3235, 0),
			new Tile(3229, 3231, 0), new Tile(3231, 3226, 0),
			new Tile(3232, 3221, 0), new Tile(3227, 3219, 0),
			new Tile(3222, 3221, 0), new Tile(3227, 3220, 0),
			new Tile(3232, 3218, 0), new Tile(3232, 3218, 0),
			new Tile(3232, 3212, 0), new Tile(3228, 3208, 0),
			new Tile(3224, 3203, 0), new Tile(3229, 3200, 0),
			new Tile(3235, 3200, 0), new Tile(3239, 3196, 0),
			new Tile(3234, 3193, 0), new Tile(3229, 3192, 0),
			new Tile(3224, 3191, 0), new Tile(3219, 3190, 0),
			new Tile(3216, 3185, 0), new Tile(3216, 3179, 0),
			new Tile(3214, 3174, 0), new Tile(3210, 3170, 0),
			new Tile(3205, 3172, 0), new Tile(3200, 3169, 0) }; //Travels to the Lumbridge Shed from the furnace
	Tile[] ICantHearDeadPeople = new Tile[] { new Tile(3187, 3166, 0),
			new Tile(3192, 3167, 0), new Tile(3196, 3163, 0),
			new Tile(3200, 3159, 0), new Tile(3205, 3155, 0) }; //Walks to Father Urhney from the Water Temple.

	@Override
	public void messageReceived(MessageEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void onStart() {
		log.info("Hello");
	}
	
	public class LumbridgeEasy extends Node {

		public boolean activate() {
			return false;
		}

		public void execute() {
			
		}
	
	public int loop() {
		Walking.newTilePath(AndItWasTHISBig).traverse();
		Fish();
		if(TaskDone);
			Walking.newTilePath(IronOn).traverse();
			if(atMine())
			Mine();
			if(Players.getLocal().isInCombat()) {
				status="o shit we in combat";
				if(TaskDone);
				Walking.newTilePath(BelterOfaSmelter).traverse();
					Smithing();
					if(TaskDone);
					Walking.newTilePath(NowToolLookAt).traverse();
					OpenDoor();
					if(Game.getPlane()==0);
					status="Good we didnt get sent to another Dimension";
						if(Game.getPlane()==1);
						status="Shit we got sent to another Dimension stoping Script";
						stop(); //Stops the Script
							if(TaskDone);
							AttackRat();
							loot();
							Task.sleep(200, 500);
							if(Lastmessage.contains("You have completed the Task")) {
								Chop();
								Task.sleep(200, 500);
								Burn();
								Cooking();
								EnterTemple(); //Enters the Water temple and Crafts the Water rune
								if(Lastmessage.contains("You have completed the Task")) {
									Walking.newTilePath(ICantHearDeadPeople).traverse();
									TalktoPriest();
								}
							}
			}
		return 0;
	}
	
	private void TalktoPriest() {
		// TODO Auto-generated method stub
		
	}

	private void EnterTemple() {
		// TODO Auto-generated method stub
		
	}

	private void Cooking() {
		
	}

	private void Burn() {
		// TODO Auto-generated method stub
		
	}

	private void loot() {
		org.powerbot.game.api.wrappers.node.GroundItem loot = GroundItems.getNearest(RawRatMeat);
		if (loot != null) {
			if (loot.isOnScreen()) {
				if (loot.getLocation().canReach()) {
					Camera.turnTo(loot);
					String name = loot.getGroundItem().getName();
					loot.interact("Take", name);
					Task.sleep(500, 650);
					if (Players.getLocal().isMoving()) {
						while (Players.getLocal().isMoving()) {
							Task.sleep(10, 30);
						}
					}
				}
			} else {
				Walking.walk(loot.getLocation());
				Camera.turnTo(loot.getLocation());
			}
		}
	}

	private void AttackRat() {
		// TODO Auto-generated method stub
		
	}

	private boolean OpenDoor() {
		final SceneObject door = SceneEntities.getNearest(ZanarisDoor);
		SceneEntities.getNearest(ZanarisDoor);
		if (door != null) {
			if (door.isOnScreen()) {
				door.interact("open");
			}
		}
		return false;
	}

	private boolean atMine() {
		if(placeToMine.contains(Players.getLocal().getLocation())){
			return true;
		} else {
			return false;
		}
	}

	private void Smithing() {
		// TODO Auto-generated method stub
		
	}

	private void Mine() {
		if(Players.getLocal().isIdle()) {
			SceneObject Mine = SceneEntities.getNearest(ironoreRock);
			if(Mine.isOnScreen()) {
				Mine.interact("Mine");
			}
			else if(!Mine.isOnScreen()) {
				Camera.turnTo(Mine);
			}
		}
		
	}

	private void Fish() {
		org.powerbot.game.api.wrappers.interactive.NPC FISHSPOT = NPCs.getNearest(FishingSpot);
		if (FISHSPOT != null && FISHSPOT.validate()) {
			if (FISHSPOT.isOnScreen()) {
				if (Players.getLocal().getAnimation() == -1) {
					FISHSPOT.interact("Bait");
					Task.sleep(1000, 2000);
				}else{
					Task.sleep(50,100);
				}
			} else {
				if (Calculations.distanceTo(FISHSPOT) > 7) {
					Walking.walk(FISHSPOT);
				} else {
					Camera.turnTo(FISHSPOT);
				}
			}
		}
		
	}

	public void Chop() {
		if(Players.getLocal().isIdle()) {
			SceneObject Tree = SceneEntities.getNearest(deadtree);
			if(Tree.isOnScreen()) {
				Tree.interact("Chop");
			}
			else if(!Tree.isOnScreen()) {
				Camera.turnTo(Tree);
			}
		}
	}
}

	@Override
	public int loop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onRepaint(Graphics arg0) {
		// TODO Auto-generated method stub
		
	}
}