package grasersTasks;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * @author Xianb
 *
 */
@Manifest(authors = ("Graser"), name = "Lumbridge Easy Task", description = "AIO Achievement Diary Script", version = 0.1)
public class LumbridgeEasyTask extends ActiveScript implements PaintListener,MessageListener {

	/**
	 * @param args
	 * I Realize i have empty ids.
	 */

	//items
	int RuneAxe;
	int Coins;
	int CoalOre;
	int Essence;
	int Clay;
	int Net;
	int Bucket=1925;
	int Tinderbox;
	int IronOreRock[]={37309,37307};
	int FishingRod;
	int FishingBait;
	int Feathers;
	int Hammer;
	int CookedLobster;
	int Cowhide=1739;
	int Needle;
	int Thread;
	int Gloves;
	int RawPike;
	int RawRatMeat=2134;
	int Logs=1511;
	int WaterTalisman;

	//People
	int Ellis;
	int Dommik;
	int FatherUrhney;
	int WiseOldMan;
	int Sedridors;

	//Objects
	int TrapDoor;
	int LumbridgeCowsGate;
	int WiseOldManDoor;
	int WiseOldManStairCase;
	int Telescope;
	int Railing;
	int WizardsTowerDoors;
	int DeadTree;

	//Monsters
	int Zombie;
	int Rat[]={8829,8828};
	int Cow[] = {12362, 12364, 12363, 12365};

	//Walking
	public static Area LL= new Area(new Tile[] {new Tile(3233,3221,0), new Tile(3234, 3222, 0), new Tile(3233,3223,0), new Tile(3232,3222,0)}); //Lumbridge Lodestone
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
	Tile[] WalktoLumbridgeCows = new Tile[] {new Tile(3234, 3222, 0), new Tile(3250, 3226,0), new Tile(3259,3242,0), new Tile(3249, 3257,0)};
	Tile[] ICantHearDeadPeople = new Tile[] { new Tile(3187, 3166, 0),new Tile(3192, 3167, 0), new Tile(3196, 3163, 0),new Tile(3200, 3159, 0), new Tile(3205, 3155, 0) }; //Walks to Father Urhney from the Water Temple.

	//Other Variables
	int Tasks;
	String Lastmessage;
	public long startTime = System.currentTimeMillis();
	public String status;
	public long millis;
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),MOUSE_BORDER_COLOR = new Color(220, 220, 220),MOUSE_CENTER_COLOR = new Color(89, 255, 89);

	/**Lumbridge Easy Tasks Algorithim
	 * 
	 * Start at Lumbridge Lodestone.
	 * Walk to Lumbridge Cows.
	 * If Gate To Lumbridge Cows is Closed Open it.
	 * Kill Cow and Take Cowhide.
	 * 
	 * Walk to Al Kahrid
	 * Open Al Kahrid Gate
	 * Continue walking to Tanning Store
	 * If at Tanning Store Trade Ellis
	 * Tan 1 Soft Leather
	 * Walk North East and Buy a "Needle, and Thread" from Dommik's Crafting Store
	 * Use Needle on Thread to make Leather Gloves
	 * 
	 * Walk to Al Kahid Mine
	 * Mine Iron Rocks
	 * 
	 * Walk Back to Al Kahrid Gate
	 * Open Gate
	 * Walk to Goblin Fishing Spot
	 * Bait Fishing Spot
	 * 
	 * Walk to Lumbridge Furnace
	 * If At Lumbridge Furnace
	 * Use Iron Ore on Furnace
	 * 
	 * Walk to Zanaris Shed
	 * If at Zanaris Location
	 * Open Shed
	 * If we Got Teleported to other Dimension Log out.
	 * If we didn't Open Door and Kill a Rat
	 * Take Raw Rat Meet
	 * Find Dead Tree 
	 * If found Chop it down
	 * 
	 * Use Tinderbox on Logs
	 * Use Raw Rat Meat on Fire
	 * Walk to Water Alter
	 * If At Water Alter Enter it
	 * Use Essence on Alter to Craft Water Rune
	 * 
	 * Walk to Priest House
	 * If at House, open door
	 * Talk to Father Urhney
	 * Choose Option 3
	 * 
	 * Lumbridge Home Teleport Dynar
	 * Walk to DJail
	 * If at Jail open TrapDoor
	 * Climb-Down Trapdoor
	 * If Task Compleat Climb Back up
	 * 
	 * Walk to Wise Old Man
	 * If Wise Old Man Door Closed Open it
	 * Talk To Wise Old Man
	 * Choose Option 2
	 * 
	 * Climb up Wise Old Man Stair Case;
	 * Observe Telescope
	 * WaitforCutawayScene
	 * 
	 * OpenBank
	 * if(Bankisopen());
	 * BankClose
	 * 
	 * Walk to Wizards Tower
	 * if at wizards tower open door if closed
	 * Climbupstaircase
	 * if at top floor
	 * open door if closed
	 * if Demon is on screen
	 * Tount Through Railing
	 * Climb Down Stair Case till Basement
	 * if at Basement
	 * OpenSedridorsDoor
	 * if Sedridor is on screen
	 * right click Teleport
	 * 
	 */

	@Override
	public void messageReceived(MessageEvent arg0) {
		// TODO Auto-generated method stub

	}

	//START: Code generated using Enfilade's Easel
	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(0, 204, 255);
	private final Color color3 = new Color(0, 255, 0);

	private final Font font1 = new Font("Cambria", 1, 20);
	private final Font font2 = new Font("Cambria", 1, 17);

	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		drawMouse(g);
		long millis = System.currentTimeMillis() - startTime;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60); long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60); long seconds = millis / 1000;
		g.setColor(color1);
		g.fillRect(3, 314, 515, 160);
		g.setColor(color2);
		g.fillRect(20, 333, 478, 124);
		g.setFont(font1);
		g.setColor(color1);
		g.drawString("Skeleton by Graser", 29, 357);
		g.drawString("Time running: " +hours + ":" +minutes + ":" +seconds , 30, 402);
		g.drawString("Status: " +status, 33, 421);
		g.setFont(font2);
		g.setColor(color3);
		g.fillRoundRect(19, 323, 491, 3, 16, 16);
		g.fillRoundRect(9, 331, 4, 125, 16, 16);
		g.fillRoundRect(21, 462, 478, 6, 16, 16);
		g.fillRoundRect(503, 340, 7, 120, 16, 16);
		g.setColor(color1);
		Point p = Mouse.getLocation();
		Dimension d = Game.getDimensions();
		int w = (int) d.getWidth(), h = (int) d.getHeight();
		g.setColor(Color.lightGray);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.1f));
		g.fillRect(0, 0, p.x - 1, p.y - 1);
		g.fillRect(p.x + 1, 0, w - (p.x + 1), p.y - 1);
		g.fillRect(0, p.y + 1, p.x - 1, h - (p.y - 1));
		g.fillRect(p.x + 1, p.y + 1, w - (p.x + 1), h - (p.y - 1));
	}

	//Credits to Member Magic from Rarebot
	private void drawMouse(Graphics g) {
		((Graphics2D) g).setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
		Point p = Mouse.getLocation();
		Graphics2D spinG = (Graphics2D) g.create();
		Graphics2D spinGRev = (Graphics2D) g.create();
		Graphics2D spinG2 = (Graphics2D) g.create();
		spinG.setColor(MOUSE_BORDER_COLOR);
		spinGRev.setColor(MOUSE_COLOR);
		spinG.rotate(System.currentTimeMillis() % 2000d / 2000d * (360d) * 2* Math.PI / 180.0, p.x, p.y);
		spinGRev.rotate(System.currentTimeMillis() % 2000d / 2000d * (-360d)* 2 * Math.PI / 180.0, p.x, p.y);
		final int outerSize = 20;
		final int innerSize = 12;
		spinG.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		spinGRev.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize,outerSize, 100, 75);
		spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize,outerSize, -100, 75);
		spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2),innerSize, innerSize, 100, 75);
		spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2),innerSize, innerSize, -100, 75);
		g.setColor(MOUSE_CENTER_COLOR);
		g.fillOval(p.x, p.y, 2, 2);
		spinG2.setColor(MOUSE_CENTER_COLOR);
		spinG2.rotate(System.currentTimeMillis() % 2000d / 2000d * 360d* Math.PI / 180.0, p.x, p.y);
		spinG2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		spinG2.drawLine(p.x - 5, p.y, p.x + 5, p.y);
		spinG2.drawLine(p.x, p.y - 5, p.x, p.y + 5);
	}

	public void onStart() {
		if(Game.isLoggedIn())
			status="Hello World";
		else
			if(!Game.isLoggedIn())
				status="Not Logged in";
		return;
	}

	@Override
	public int loop() {
		WalkToCows();
		//if(atGate()) 
			//Attackcows();
			//LootCowhide();
		return 150;
	}

	@SuppressWarnings("unused")
	private static boolean atLodestone() {
		LL.contains(new Tile(3233,3221,0), new Tile(3234, 3222, 0), new Tile(3233,3223,0), new Tile(3232,3222,0));
		return false;
	}

	@SuppressWarnings("unused")
	private void LootCowhide() {
		GroundItem loot = GroundItems.getNearest(Cowhide);
		if (loot != null) {
			status = "We see the Loot";
			if (loot.isOnScreen()) {
				status = "Checking if we can reach it";
				if (loot.getLocation().canReach()) {
					status = "Turning to Loot";
					Camera.turnTo(loot);
					status = "Looting " + loot.getGroundItem().getName();
					loot.interact("Take", loot.getGroundItem().getName());
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

	@SuppressWarnings("unused")
	private void Attackcows() {
		if(!Players.getLocal().isInCombat()) {
			status = "Looking for Cow!";
			if (Cow != null);
			status = "We See the Cow!";
			if (NPCs.getNearest(Cow).isOnScreen()) {
				status = "Checking to see if we are in Combat";
				if (!NPCs.getNearest(Cow).isInCombat()) {
					status = "We Should Be Attacking them Now";
					NPCs.getNearest(Cow).interact("Attack");
					sleep(Random.nextInt(2500, 0));
				} else if (!NPCs.getNearest(Cow).isOnScreen()) {
					Camera.setPitch(75);
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private boolean atGate() {
		SceneObject Gate = SceneEntities.getNearest(LumbridgeCowsGate);
		Gate.interact("Open");
		return false;
	}

	@SuppressWarnings("unused")
	private void Chop() {
		SceneObject tree = SceneEntities.getNearest(DeadTree);
		while (Players.getLocal().getAnimation() == -1) {
			if (tree != null) {
				if (tree.isOnScreen()) {
					tree.interact("Chop");
					sleep(2000,2500);
					Camera.setPitch(99);
					Camera.setAngle(0);
				} else {
					Camera.setPitch(99);
					Camera.setAngle(0);
				}
			}
		}
	}

	public void WalkToCows() {
		status="Walking to Cows";
		Walking.newTilePath(WalktoLumbridgeCows).traverse();
	}

	public void onStop() {
		status="Thank you for using my script";
	}

}
