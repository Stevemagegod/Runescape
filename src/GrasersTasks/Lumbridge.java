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
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
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
	private final int cowdide = 1739;
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
	int fishingSpot=329;
	final int Fire=70755;
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

	//People
	final int Ellis=2824;

	@Override
	public void messageReceived(MessageEvent arg0) {
		// TODO Auto-generated method stub

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


	@Override
	public int loop() {
		if (atCows()) {
			Attack();
			sleep(2000);
			if (!(Players.getLocal().getInteracting() != null)) {
				LootCowhide();
				if(Inventory.getCount(cowdide)==1) {
					status="Go get these tanned";
				}
			}
		}
		if(atTanningStore()) {
			TanHides();
			if(Inventory.getCount(softLeather)==1) {
				Craft();
				if(Inventory.getCount(leatherGloves)==1) {
					status="We got gloves doing next task.....";
				}
			}
		}
		if(atDesertMineingSpot()) {
			if(!Players.getLocal().isInCombat())  {
				Mine();
				if(Inventory.getCount(leatherGloves)==1) {
					status="We got the ore doing next task.....";
				}
			}
		}
		if(atFishingSpot()) {
			if(Inventory.contains(fishingSupplies))
				Fish();
		}
		if(atSmithingSpot() && Inventory.contains(steelBarMaterial)) {
			Smith();
		}
		if(inLumbridgeSwamp()) {
			OpenDoor();
			if(inDoor()) {
				Attack();
				if(Inventory.contains(rawRatMeet)) {
					Chop();
					if(Inventory.contains(log)) {
						Firemake();
						if(!Inventory.contains(log)) {
							Cook();
						}
					}
				}
			}
		}
		if(Players.getLocal().getAnimation()==-1) {
			Antiban();
		}
		return 150;

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

	private void Firemake() {
		Inventory.getItem(log).getWidgetChild().interact("Craft");
		status="Making Fire";
		Mouse.move(158,250);
		sleep(2000);
		Mouse.click(true);
		sleep(5000);

	}

	private boolean inDoor() {
		// TODO Auto-generated method stub
		return false;
	}

	private void OpenDoor() {
		status="Opening Door";
		Mouse.move(254,206);
		sleep(2000);
		Mouse.click(true);

	}

	private boolean inLumbridgeSwamp() {
		return lumbridgeSwampArea.contains(Players.getLocal().getLocation());
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

	private boolean atSmithingSpot() {
		return smithingArea.contains(Players.getLocal().getLocation());
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

	private boolean atFishingSpot() {
		return pikefishingArea.contains(Players.getLocal().getLocation());
	}

	private void Mine() {
		SceneObject mine = SceneEntities.getNearest(ironOreRock);
		while (Players.getLocal().getAnimation() == -1) {
			if (mine != null) {
				if (mine.isOnScreen()) {
					status = "Mineing Rock";
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

	private boolean atDesertMineingSpot() {
		return DesertMineingSpotArea.contains(Players.getLocal().getLocation());
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

	private boolean atTanningStore() {
		return TanningStoreArea.contains(Players.getLocal().getLocation());
	}

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

	public void LootCowhide() {
		GroundItem loot = GroundItems.getNearest(cowdide);
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
				}
			} else {
				Walking.walk(loot.getLocation());
				Camera.turnTo(loot.getLocation());
			}
		}
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

	private boolean atCows() {
		return cowArea.contains(Players.getLocal().getLocation());
	}
}
