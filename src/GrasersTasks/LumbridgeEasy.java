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
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

@Manifest(authors = ("Graser"), name = "Task", description = "AIO Achievement Diary Script", version = 0.1)
public class Task extends ActiveScript implements PaintListener,MessageListener {
	final int monsters[] = { 12362, 12364, 12363, 12365,12348,8828 };
	final int cowhide = 1739;
	public String status;
	final int softLeather = 1741;
	final int rawRatMeet = 2134;
	int minMilliSecond = 500;
	int maxMillisecond = 50000;
	int x=Random.nextInt(1,450);
	int y=Random.nextInt(1,450);
	final int rawPike=349;
	final int leatherGloves=1059;
	final int coalOre=453;
	int randomX= Random.nextInt(1,300);
	int randomY=Random.nextInt(1,300);
	int ironOreRock[] = { 37309, 37307 };
	final int ironOre=440;
	final int deadTree[] = {9388,9887,9366,9354,3300};
	final int log=1511;
	final int steelBarMaterial[] = {453,440};
	final int fishingSpot=329;
	final int Fire=70755;
	final int WaterTiara=5531;
	int LesserDemon=16207;
	final int GhostSpeakAmulet = 552;
	final int portal = 2467;
	final int WaterTemple = 2454;
	int Beam[] = {79568,12012,79758,79770,79769};
	int Essence[] = {556,2491};
	String Lastmessage;
	boolean left = false;
	int CombatAnimation = 18241;
	int TeleportationAnimation=18927;
	final int fishingSupplies[] = {313,309};
	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(0, 204, 255);
	private final Color color3 = new Color(0, 255, 0);
	private final Font font1 = new Font("Cambria", 1, 20);
	private final Font font2 = new Font("Cambria", 1, 17);
	public long startTime = System.currentTimeMillis();
	public long millis;
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),MOUSE_BORDER_COLOR = new Color(220, 220, 220),MOUSE_CENTER_COLOR = new Color(89, 255, 89);

	//Areas
	Area desertArea = new Area(new Tile[] { new Tile(3292, 3181, 0), new Tile(3292, 3189, 0), new Tile(3300, 3188, 0), 
			new Tile(3300, 3180, 0), new Tile(3292, 3181, 0) });
	Area cowArea = new Area(new Tile[] { new Tile(3252, 3269, 0), new Tile(3265, 3269, 0), new Tile(3265, 3261, 0), 
			new Tile(3265, 3255, 0), new Tile(3252, 3255, 0) });
	Area TanningStoreArea = new Area(new Tile[] { new Tile(3268, 3199, 0), new Tile(3276, 3199, 0), new Tile(3276, 3193, 0), 
			new Tile(3269, 3193, 0), new Tile(3268, 3199, 0) });
	Area DesertMineingSpotArea = new Area(new Tile[] { new Tile(3298, 3284, 0), new Tile(3298, 3289, 0), new Tile(3302, 3289, 0), 
			new Tile(3304, 3289, 0), new Tile(3305, 3285, 0), new Tile(3304, 3283, 0), 
			new Tile(3298, 3284, 0) });
	Area pikefishingArea = new Area(new Tile[] { new Tile(3245, 3257, 0), new Tile(3247, 3248, 0), new Tile(3241, 3238, 0), 
			new Tile(3235, 3247, 0), new Tile(3236, 3256, 0), new Tile(3245, 3257, 0) });
	Area smithingArea = new Area(new Tile[] { new Tile(3220, 3258, 0), new Tile(3220, 3252, 0), new Tile(3220, 3251, 0), 
			new Tile(3230, 3251, 0), new Tile(3229, 3258, 0) });
	Area lumbridgeSwampArea = new Area(new Tile[] { new Tile(3177, 3158, 0), new Tile(3177, 3163, 0), new Tile(3177, 3168, 0), 
			new Tile(3177, 3173, 0), new Tile(3181, 3176, 0), new Tile(3185, 3179, 0), 
			new Tile(3190, 3180, 0), new Tile(3195, 3180, 0), new Tile(3199, 3177, 0), 
			new Tile(3203, 3174, 0), new Tile(3206, 3170, 0), new Tile(3210, 3166, 0), 
			new Tile(3212, 3161, 0), new Tile(3213, 3156, 0), new Tile(3209, 3153, 0) });
	Area waterTempleArea = new Area(new Tile[] { new Tile(3178, 3159, 0), new Tile(3179, 3164, 0), new Tile(3185, 3167, 0), 
			new Tile(3190, 3163, 0), new Tile(3190, 3156, 0), new Tile(3181, 3155, 0), 
			new Tile(3178, 3159, 0) });
	Area insidewaterTempleArea = new Area(new Tile[] { new Tile(3492, 4834, 0), new Tile(3495,4833, 0), new Tile(3482, 4839, 0), 
			new Tile(3484, 4833, 0), new Tile(3490,4830, 0), new Tile(3495, 4830, 0)});
	Area priestArea = new Area(new Tile[] { new Tile(3198, 3146, 0), new Tile(3198, 3154, 0), new Tile(3210, 3154, 0), 
			new Tile(3214, 3146, 0), new Tile(3214, 3144, 0), new Tile(3208, 3143, 0) });
	Area wizardsTowerArea = new Area(new Tile[] { new Tile(3099, 3194, 0), new Tile(3092, 3192, 0), new Tile(3085, 3189, 0), 
			new Tile(3081, 3182, 0), new Tile(3072, 3186, 0), new Tile(3064, 3177, 0), 
			new Tile(3072, 3162, 0), new Tile(3070, 3153, 0), new Tile(3072, 3145, 0), 
			new Tile(3078, 3133, 0), new Tile(3091, 3126, 0), new Tile(3100, 3122, 0), 
			new Tile(3112, 3125, 0), new Tile(3120, 3129, 0), new Tile(3125, 3135, 0), 
			new Tile(3132, 3142, 0), new Tile(3134, 3149, 0), new Tile(3134, 3155, 0), 
			new Tile(3135, 3160, 0), new Tile(3125, 3176, 0), new Tile(3115, 3187, 0), 
			new Tile(3101, 3194, 0) });
	Area beamArea = new Area(new Tile[] { new Tile(3098, 3156, 0), new Tile(3101, 3159, 0), new Tile(3106, 3156, 0), 
			new Tile(3104, 3152, 0), new Tile(3100, 3151, 0) });

	//Walking
	Tile[] ToWizardsTower = new Tile[] { new Tile(3205, 3154, 0), new Tile(3198, 3153, 0), new Tile(3191, 3154, 0), 
			new Tile(3184, 3155, 0), new Tile(3178, 3156, 0), new Tile(3171, 3157, 0), 
			new Tile(3166, 3160, 0), new Tile(3159, 3164, 0), new Tile(3155, 3167, 0), 
			new Tile(3153, 3173, 0), new Tile(3149, 3178, 0), new Tile(3144, 3180, 0), 
			new Tile(3143, 3185, 0), new Tile(3140, 3188, 0), new Tile(3137, 3193, 0), 
			new Tile(3131, 3198, 0), new Tile(3126, 3198, 0), new Tile(3123, 3203, 0), 
			new Tile(3118, 3207, 0), new Tile(3113, 3208, 0), new Tile(3108, 3211, 0), 
			new Tile(3103, 3213, 0), new Tile(3102, 3208, 0), new Tile(3102, 3204, 0), 
			new Tile(3102, 3200, 0), new Tile(3102, 3194, 0), new Tile(3102, 3189, 0), 
			new Tile(3102, 3185, 0), new Tile(3102, 3181, 0), new Tile(3102, 3176, 0), 
			new Tile(3102, 3172, 0), new Tile(3102, 3167, 0), new Tile(3102, 3163, 0), 
			new Tile(3102, 3160, 0), new Tile(3102, 3158, 0) };
	public final Tile[] ToEllis = {new Tile(3280,3192, 0), new Tile(3274,3197, 0),};

	//People
	final int Ellis=2824;
	final int Wizard=15422;
	final int FatherUrhney=458;

	@SuppressWarnings("unused")
	private void Antiban() {
		sleep(Random.nextInt(minMilliSecond, maxMillisecond));
		status="move mouse";
		Mouse.move(x,y,randomX,randomY);
		int ii=Random.nextInt(1,20);
		switch (ii){
		case 1:
			status="Seting Angle";
			Camera.setAngle(Random.nextInt(1, 450));
			break;
		case 2:
			status="Seting Pitch";
			Camera.setPitch(Random.nextInt(1, 450));
			break;

		case 3:
			status="Seting Angle & Seting Pitch";
			Camera.setAngle(Random.nextInt(10, 500));
			Camera.setPitch(Random.nextInt(10, 500));
			break;
		case 4:
			status="Seting Angle";
			Camera.setAngle(Random.nextInt(20, 300));
			break;
		case 5:
			status="Moveing Mouse Randomly";
			Mouse.move(Random.nextInt(Mouse.getLocation().x - 150,
					Mouse.getLocation().x + 150),
					Random.nextInt(Mouse.getLocation().y - 150,
							Mouse.getLocation().y + 150));
			break;

		default:
			break;
		}

	}

	private void Ascend() {
		status="Ascending";
		Mouse.move(113,254);
		Mouse.click(true);
	}

	private boolean atCows() {
		return cowArea.contains(Players.getLocal().getLocation());
	}


	private boolean atDesertMineingSpot() {
		return DesertMineingSpotArea.contains(Players.getLocal().getLocation());
	}

	private boolean atFishingSpot() {
		return pikefishingArea.contains(Players.getLocal().getLocation());
	}

	private boolean atPriestHouse() {
		return priestArea.contains(Players.getLocal().getLocation());
	}

	private boolean atSmithingSpot() {
		return smithingArea.contains(Players.getLocal().getLocation());
	}

	public void Attack() {
		if (!Players.getLocal().isInCombat()) {
			status = "Looking for Monsters!";
			if (monsters != null);
			status = "We See the Monsters!";
			if (NPCs.getNearest(monsters).isOnScreen()) {
				status = "Checking to see if we are in Combat";
				if (!NPCs.getNearest(monsters).isInCombat()) {
					status = "We Should Be Attacking them Now";
					NPCs.getNearest(monsters).interact("Attack");
					sleep(Random.nextInt(2500, 0));
				} else if (!NPCs.getNearest(monsters).isOnScreen()) {
					Camera.setPitch(75);
				}
			}
		}
	}

	private boolean atTanningStore() {
		return TanningStoreArea.contains(Players.getLocal().getLocation());
	}

	private boolean atWaterTemple() {
		return waterTempleArea.contains(Players.getLocal().getLocation());
	}

	@SuppressWarnings("unused")
	private boolean atWizardsTower() {
		return wizardsTowerArea.contains(Players.getLocal().getLocation());
	}

	public void Chop() {
		SceneObject tree = SceneEntities.getNearest(deadTree);
		while (Players.getLocal().getAnimation() == -1) {
			if (tree != null) {
				if (tree.isOnScreen()) {
					tree.interact("Chop");
					sleep(2000, 2500);
					Camera.setPitch(99);
					Camera.setAngle(0);
				} else {
					Camera.setPitch(99);
					Camera.setAngle(0);
				}
			}
		}
	}

	private void Cook() {
		SceneObject fire = SceneEntities.getNearest(Fire);
		while (Players.getLocal().getAnimation() == -1) {
			if (fire != null) {
				if (fire.isOnScreen()) {
					fire.interact("Use");
					sleep(2000, 2500);
					status="Making Dinner";
					Mouse.move(204,243);
					sleep(2000);
					Mouse.click(true);
					Mouse.move(142,157);
					sleep(2000);
					Mouse.click(true);
					Mouse.move(293,365);
					sleep(2000);
					Mouse.click(true);

				} else {
					Camera.setPitch(99);
					Camera.setAngle(0);
				}
			}
		}

	}

	private void Craft() {
		status="Crafting Leather";
		Inventory.getItem(softLeather).getWidgetChild().interact("Craft");
		status="Chooseing a Tool";
		Mouse.move(275,230);
		sleep(2000);
		Mouse.click(true);
		status="Crafting Interface Clicking on Gloves";
		Mouse.move(50, 164);
		sleep(2000);
		Mouse.click(true);
		Mouse.move(313, 363);
		status="Creating Gloves";
		Mouse.click(true);

	}

	private void CraftRunes() {
		status="Checking if are Inventory Contains some Essence";
		if(Inventory.contains(Essence)) {
			Mouse.move(222, 192);
			if(Mouse.isReady()) 
				status="Crafting Runes";
			Mouse.click(true);
			status="Looks like we don't have any Essence";
			if(!Inventory.contains(Essence)) {
				status="Turning to Portal";
				Camera.turnTo(SceneEntities.getNearest(portal));
				if(SceneEntities.getNearest(portal).isOnScreen()) {
					status="Leaving Water Temple";
					SceneEntities.getNearest(portal).interact("Enter");
				}
			}
		}
	}

	private void drawMouse(Graphics g) {
		((Graphics2D) g).setRenderingHints(new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON));
		Point p = Mouse.getLocation();
		Graphics2D spinG = (Graphics2D) g.create();
		Graphics2D spinGRev = (Graphics2D) g.create();
		Graphics2D spinG2 = (Graphics2D) g.create();
		spinG.setColor(MOUSE_BORDER_COLOR);
		spinGRev.setColor(MOUSE_COLOR);
		spinG.rotate(System.currentTimeMillis() % 2000d / 2000d * (360d) * 2
				* Math.PI / 180.0, p.x, p.y);
		spinGRev.rotate(System.currentTimeMillis() % 2000d / 2000d * (-360d)
				* 2 * Math.PI / 180.0, p.x, p.y);
		final int outerSize = 20;
		final int innerSize = 12;
		spinG.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		spinGRev.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize,
				outerSize, 100, 75);
		spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize,
				outerSize, -100, 75);
		spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2),
				innerSize, innerSize, 100, 75);
		spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2),
				innerSize, innerSize, -100, 75);
		g.setColor(MOUSE_CENTER_COLOR);
		g.fillOval(p.x, p.y, 2, 2);
		spinG2.setColor(MOUSE_CENTER_COLOR);
		spinG2.rotate(System.currentTimeMillis() % 2000d / 2000d * 360d
				* Math.PI / 180.0, p.x, p.y);
		spinG2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		spinG2.drawLine(p.x - 5, p.y, p.x + 5, p.y);
		spinG2.drawLine(p.x, p.y - 5, p.x, p.y + 5);
	}

	private void Enterit() {
		SceneObject altar = SceneEntities.getNearest(WaterTemple);   
		if (altar.isOnScreen()) {
			status="Entering Temple";
			altar.interact("Enter");       
		}
	}

	private void Firemake() {
		status="Opening Interface";
		Inventory.getItem(log).getWidgetChild().interact("Craft");
		status="Making Fire";
		Mouse.move(158,250);
		sleep(2000);
		Mouse.click(true);
		sleep(5000);

	}

	private void Fish() {
		NPC fishingspot = NPCs.getNearest(fishingSpot);
		if (fishingspot != null) {
			if (fishingspot.isOnScreen()) {
				if(!(Players.getLocal().getInteracting() != null))
					fishingspot.interact("Bait");
				sleep(1000);
			}
		}
	}

	private boolean inLumbridgeSwamp() {
		return lumbridgeSwampArea.contains(Players.getLocal().getLocation());
	}

	@Override
	public int loop() {
		if (atCows()) {
			Attack();
		}
		sleep(2000);
		if(!(Players.getLocal().getAnimation()==CombatAnimation))
			if(!Inventory.contains(cowhide)) {
				loot();
			}
			else
				if(Inventory.contains(cowhide)) {
					TeleporttoDesert();
						if(atDesertLodestone())
							status="Walking to Ellis";
							Walking.newTilePath(ToEllis).traverse();
				}
		if(atTanningStore()) {
			TanHides();
			if(Inventory.contains(softLeather)) {
				Craft();
				if(Inventory.contains(leatherGloves)) {
					status="We got gloves doing next task.....";
				}
			}
			if(atDesertMineingSpot()) {
				status="Checking if those Fucking Scorpions aren't attacking us";
				if(!Players.getLocal().isInCombat())  {
					Mine();
					if(Inventory.contains(ironOre)) {
						status="We got the ore doing next task.....";
					}
				}
			}
			if(atFishingSpot()) {
				status="Checking if are Inventory Contains are fishing Supplies";
				if(Inventory.contains(fishingSupplies))
					Fish();
			}
			if(atSmithingSpot() && Inventory.contains(steelBarMaterial)) {
				Smith();
			}
			if(inLumbridgeSwamp()) {
				OpenDoor();
				Attack();
				status="Checking if are Inventory Contains some Raw Rat Meet";
				if(Inventory.contains(rawRatMeet)) {
					Chop();
					status="Checking if are Inventory Contains some Logs";
					if(Inventory.contains(log)) {
						Firemake();
						status="Checking if are Inventory does not contain any Logs";
						if(!Inventory.contains(log)) {
							Cook();
						}
					}
				}
			}
			if(atWaterTemple()) {
				status="Checking if are Equipment Contains a Water Tiara";
				if(Equipment.containsOneOf(WaterTiara)) {
					Enterit();
					status="Checking if we are in the Temple";
					if(Lastmessage.contains("You feel a powerful force take hold of you."))
						CraftRunes();
				}
			}
			if(atPriestHouse()) {
				TalktoPriest();
				if(Inventory.contains(GhostSpeakAmulet)) {
					Walking.newTilePath(ToWizardsTower).traverse();
				}
				Ascend();
				TauntDemon();
			}
			return Random.nextInt(25,50);
		}
		return 0;
	}

	private boolean atDesertLodestone() {
		return desertArea.contains(Players.getLocal().getLocation());
	}

	private void TeleporttoDesert() {
			status="Opening Ability Book";
			Tabs.ABILITY_BOOK.open();
			if(Tabs.ABILITY_BOOK.isOpen()) {
				status="Casting Home Teleport";
				Mouse.move(580, 359);
				Mouse.click(true);
				status="Sleeping for 2 Seconds";
				sleep(2000);
				status="Teleporting to Desert";
				Mouse.move(360,279);
				Mouse.click(true);
				sleep(10000);
			}
	}

	public void loot() {
		GroundItem loot = GroundItems.getNearest(cowhide);
		if (loot != null) {
			if (loot.isOnScreen()) {
				status = "Checking if we can reach it";
				if (loot.getLocation().canReach()) {
					status = "Turning to Loot";
					Camera.turnTo(loot);
					status = "Looting " + loot.getGroundItem().getName();
					loot.interact("Take", loot.getGroundItem().getName());
					Task.sleep(500, 650);
				}
			} else {
				Walking.walk(loot.getLocation());
				Camera.turnTo(loot.getLocation());
			}
		}
	}

	@Override
	public void messageReceived(MessageEvent arg0) {
		// TODO Auto-generated method stub

	}

	private void Mine() {
		SceneObject mine = SceneEntities.getNearest(ironOreRock);
		while (Players.getLocal().getAnimation() == -1) {
			if (mine != null) {
				if (mine.isOnScreen()) {
					status = "Mining Rock";
					mine.interact("Mine");
					sleep(2000, 2500);
					Camera.setPitch(99);
					Camera.setAngle(0);
				} else {
					Camera.setPitch(99);
					Camera.setAngle(0);
				}
			}
		}

	}

	@SuppressWarnings("unused")
	private boolean nearBeam() {
		return beamArea.contains(Players.getLocal().getLocation());
	}

	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		drawMouse(g);
		long millis = System.currentTimeMillis() - startTime;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;
		g.setColor(color1);
		g.fillRect(3, 314, 515, 160);
		g.setColor(color2);
		g.fillRect(20, 333, 478, 124);
		g.setFont(font1);
		g.setColor(color1);
		g.drawString("Skeleton by Graser", 29, 357);
		g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds,
				30, 402);
		g.drawString("Status: " + status, 33, 421);
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
		g.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER, 0.1f));
		g.fillRect(0, 0, p.x - 1, p.y - 1);
		g.fillRect(p.x + 1, 0, w - (p.x + 1), p.y - 1);
		g.fillRect(0, p.y + 1, p.x - 1, h - (p.y - 1));
		g.fillRect(p.x + 1, p.y + 1, w - (p.x + 1), h - (p.y - 1));
	}

	public void onSart() {
		if(Game.isLoggedIn())
			status="Hello World";
		else
			if(!Game.isLoggedIn()) {
				status="Not Logged in";
			}
	}

	public void onStop() {
		status="Thank you for using my script";
		stop();
	}

	private void OpenDoor() {
		status="Opening Door";
		Mouse.move(254,206);
		sleep(2000);
		Mouse.click(true);

	}

	private void Smith() {
		status="Useing Furnace";
		Mouse.move(298, 224);
		sleep(2000);
		Mouse.click(true);
		status="Chosing Bar to make";
		Mouse.move(57, 205);
		sleep(2000);
		Mouse.click(true);
		status="Smelting";
		Mouse.move(342, 368);
		sleep(2000);
		Mouse.click(true);

	}
	private void TalktoPriest() {
		NPC Priest= NPCs.getNearest(FatherUrhney);
		if(Priest.isOnScreen()) {
			Mouse.move(245,188);
			status="Opening Door";
			Mouse.click(true);
			sleep(1000);
			Priest.interact("Talk-To");
			status="Talking";
			Mouse.move(254,516);
			Mouse.click(true);
			sleep(1000);
			status="Option 1";
			Mouse.move(307,495);
			Mouse.click(true);
			sleep(1000);
			status="Clicking Option 2";
			Mouse.move(253,514);
			Mouse.click(true);
			sleep(1000);
			status="Clicking Option 3";
			Mouse.move(271,518);
			Mouse.click(true);
			sleep(1000);
			status="Clicking Option 4";
			Mouse.move(278,510);
			Mouse.click(true);
			sleep(1000);
			status="Clicking Option 5";
			Mouse.move(248,519);
			Mouse.click(true);
			sleep(1000);
			status="Clicking Option 6";
			Mouse.move(276,514);
			Mouse.click(true);
			sleep(1000);
		}
	}

	private void TanHides() {
		NPC Tanner = NPCs.getNearest(Ellis);
		if(Tanner.isOnScreen()) {
			status="Openning Store";
			Tanner.interact("Tan hides");
			status="Clicking on Soft Leather";
			Mouse.move(54, 159);
			sleep(2000);
			Mouse.click(true);
			sleep(5000);
			if(Mouse.isReady()) {
				status="Tanning";
				Mouse.move(275,363);
				Mouse.click(true);
				sleep(2000);
			}
		}
	}

	private void TauntDemon() {
		if(NPCs.getNearest(LesserDemon).isOnScreen()) {
			status="Taunting Lesser Demon";
			NPCs.getNearest(LesserDemon).interact("Taunt");
		}
	}
}
