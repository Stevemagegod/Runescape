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
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

@Manifest(authors = ("Graser"), name = "GrasersPker", description = "Kills People in the Wild", version = 0.5)
public class GrasersPker extends ActiveScript implements PaintListener, MessageListener {

	Tile[] deathWalkingTiles = new Tile[] { new Tile(3103, 3490, 0),
			new Tile(3102, 3494, 0), new Tile(3100, 3497, 0),
			new Tile(3100, 3500, 0), new Tile(3101, 3503, 0),
			new Tile(3102, 3506, 0), new Tile(3102, 3510, 0),
			new Tile(3101, 3513, 0), new Tile(3101, 3516, 0),
			new Tile(3100, 3519, 0), new Tile(3097, 3520, 0) };

	Tile[] pathtowallfromBank = new Tile[] { new Tile(3094, 3495, 0), new Tile(3096, 3499, 0), new Tile(3100, 3506, 0), 
			new Tile(3104, 3510, 0), new Tile(3104, 3517, 0), new Tile(3103, 3520, 0) };

	Area deathArea = new Area(new Tile[] { new Tile(3101, 3489, 0),
			new Tile(3101, 3493, 0), new Tile(3105, 3493, 0),
			new Tile(3108, 3491, 0), new Tile(3106, 3488, 0),
			new Tile(3099, 3487, 0), new Tile(3099, 3491, 0),
			new Tile(3099, 3493, 0), new Tile(3100, 3494, 0),
			new Tile(3102, 3495, 0), new Tile(3103, 3496, 0),
			new Tile(3106, 3495, 0), new Tile(3108, 3494, 0),
			new Tile(3108, 3491, 0) });

	Area edgevilleLodestoneArea = new Area(new Tile[] { new Tile(3062, 3509, 0), new Tile(3067, 3509, 0), new Tile(3071, 3508, 0), 
			new Tile(3070, 3501, 0), new Tile(3064, 3499, 0), new Tile(3060, 3506, 0) });

	public final Tile[] Bankpath = {
			new Tile(3067, 3505, 0), new Tile(3070, 3500, 0),
			new Tile(3076, 3499, 0), new Tile(3081, 3497, 0),
			new Tile(3087, 3494, 0), new Tile(3094, 3493, 0)
	};

	Area wildArea = new Area(new Tile[] { new Tile(3116, 3525, 0),
			new Tile(3095, 3525, 0), new Tile(3077, 3525, 0),
			new Tile(3065, 3525, 0), new Tile(3056, 3526, 0),
			new Tile(3045, 3527, 0), new Tile(3044, 3534, 0),
			new Tile(3051, 3541, 0), new Tile(3060, 3544, 0),
			new Tile(3069, 3546, 0), new Tile(3075, 3548, 0),
			new Tile(3081, 3548, 0) });

	final int[] FOOD_IDS = { 1895, 1893, 1891, 4293, 2142, 291, 2140, 3228,
			9980, 7223, 6297, 6293, 6295, 6299, 7521, 9988, 7228, 2878, 7568,
			2343, 1861, 13433, 315, 325, 319, 3144, 347, 355, 333, 339, 351,
			329, 3381, 361, 10136, 5003, 379, 365, 373, 7946, 385, 397, 391,
			3369, 3371, 3373, 2309, 2325, 2333, 2327, 2331, 2323, 2335, 7178,
			7180, 7188, 7190, 7198, 7200, 7208, 7210, 7218, 7220, 2003, 2011,
			2289, 2291, 2293, 2295, 2297, 2299, 2301, 2303, 1891, 1893, 1895,
			1897, 1899, 1901, 7072, 7062, 7078, 7064, 7084, 7082, 7066, 7068,
			1942, 6701, 6703, 7054, 6705, 7056, 7060, 2130, 1985, 1993, 1989,
			1978, 5763, 5765, 1913, 5747, 1905, 5739, 1909, 5743, 1907, 1911,
			5745, 2955, 5749, 5751, 5753, 5755, 5757, 5759, 5761, 2084, 2034,
			2048, 2036, 2217, 2213, 2205, 2209, 2054, 2040, 2080, 2277, 2225,
			2255, 2221, 2253, 2219, 2281, 2227, 2223, 2191, 2233, 2092, 2032,
			2074, 2030, 2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971,
			4608, 1883, 1885, 1982 };

	// Variables
	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(0, 204, 255);
	private final Color color3 = new Color(0, 255, 0);
	private final Font font1 = new Font("Cambria", 1, 20);
	private final Font font2 = new Font("Cambria", 1, 17);
	final int DeathAnimation = 17984;
	int dieCount;
	String Lastmessage;
	private String status;
	private Timer runtime = new Timer(0);
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),
			MOUSE_BORDER_COLOR = new Color(220, 220, 220),
			MOUSE_CENTER_COLOR = new Color(89, 255, 89);
	private final static int[] WILDY_WALL = { 65093, 65095, 65094, 65081,
		65083, 65078, 65088, 65084, 65092, 65079, 65082, 65076, 65085,
		65048 };
	final int Skeleton[] = {5334,90,5333};

	/**
	 * Loots All: Arrows Special Weapons Food Charms Bows Ghostly Robes Magic
	 * Runes and Staffs Dragon, Rune Armor and Weapons
	 */
	private final int[] LOOTID = { 526,1249, 536, 995, 1213, 1753, 12158, 12159,
			12160, 12163, 18778, 1895, 1893, 1891, 4293, 2142, 291, 2140, 3228,
			9980, 7223, 995, 6297, 6293, 6295, 6299, 7521, 9988, 7228, 2878,
			7568, 2343, 1861, 13433, 315, 325, 319, 3144, 347, 355, 333, 339,
			351, 329, 3381, 12029, 99, 12790, 12825, 1119, 96, 12093, 12435,
			10818, 95, 12822, 12828, 1115, 93, 12796, 12827, 12161, 92, 12089,
			12437, 2859, 3226, 89, 12786, 12833, 1444, 12083, 12466, 2363, 88,
			12039, 12434, 237, 85, 12776, 12832, 10149, 83, 12017, 12456, 6155,
			12788, 12837, 12168, 80, 12025, 12442, 571, 79, 12802, 12824, 1442,
			12804, 12824, 1440, 78, 12013, 12457, 5933, 76, 12081, 12465, 2361,
			12782, 12841, 10020, 74, 12069, 12449, 6979, 73, 12792, 12826,
			12168, 71, 12057, 12451, 10117, 70, 12820, 12830, 1933, 69, 12033,
			12423, 1963, 67, 12031, 12439, 7939, 66, 12079, 12464, 2359, 12123,
			12452, 2150, 63, 12015, 12436, 6287, 62, 12037, 12427, 12161, 61,
			12085, 12468, 9736, 57, 12784, 12840, 10095, 12810, 12835, 10099,
			12812, 12836, 10103, 56, 12531, 12424, 311, 52, 12007, 12441, 9978,
			49, 12061, 12444, 2132, 34, 12818, 12443, 12164, 10, 12059, 12428,
			6291, 361, 10136, 5003, 379, 365, 373, 7946, 385, 397, 391, 3369,
			3371, 562, 554, 560, 557, 558, 560, 563, 469, 561, 565, 555, 556,
			559, 3373, 2309, 2325, 2333, 2327, 2331, 2323, 2335, 7178, 7180,
			7188, 772, 1381, 1383, 1385, 1387, 1393, 1409, 12158, 12159, 12160,
			12161, 12162, 12163, 12164, 12165, 12166, 12167, 7190, 7198, 7200,
			7208, 7210, 7218, 7220, 2003, 2011, 2289, 2291, 2495, 6107, 6108,
			6110, 6106, 6109, 6111, 1135, 1065, 1099, 78, 867, 882, 884, 886,
			888, 890, 886, 892, 11212, 2293, 2295, 2297, 2299, 2301, 2303,
			1891, 1893, 1895, 1897, 1899, 4151, 7639, 7640, 7641, 7642, 7643,
			7644, 7645, 7646, 7647, 7648, 805, 1215, 1216, 1901, 7072, 7062,
			7078, 7064, 7084, 7082, 7066, 7068, 1942, 6701, 6703, 7054, 6705,
			7056, 7060, 2130, 1985, 1993, 1989, 1978, 5763, 5765, 1913, 5747,
			1905, 5739, 1909, 5743, 1907, 1911, 5745, 2955, 5749, 5751, 5753,
			5755, 5757, 5759, 5761, 2084, 2034, 2048, 2036, 2217, 2213, 2205,
			2209, 2054, 2040, 2080, 2277, 2225, 2255, 2221, 2253, 2219, 2281,
			2227, 2223, 2191, 2233, 2092, 2032, 2074, 2030, 2281, 2235, 2064,
			2028, 2187, 2185, 2229, 6883, 1971, 4608, 1883, 1885, 1982, 11732,
			4087, 1149, 5698, 1187, 1188, 1215, 1216, 1231, 1232, 1249, 1250,
			1263, 1264, 1305, 1306, 1377, 1378, 143, 141, 139, 2434, 15331,
			15330, 15329, 15328, 23253, 23251, 23249, 23247, 23245, 23243,
			23530, 23529, 23528, 23527, 23526, 23525, 1435, 1540, 1541, 3140,
			3141, 3176, 3204, 3205, 4585, 4586, 4587, 4588, 5680, 5681, 5716,
			5717, 5730, 5731, 6739, 6740, 7158, 7407, 11227, 11228, 11229,
			11230, 11283, 11284, 11335, 11336, 11732, 11733, 13481, 13490,
			1163, 1127, 1303, 1079, 1333, 1199, 1123, 1073, 1331, 1317, };

	int[] FOOD = { 1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980, 7223,
			6297, 6293, 6295, 6299, 7521, 9988, 7228, 2878, 7568, 2343, 1861,
			13433, 315, 325, 319, 3144, 347, 355, 333, 339, 351, 329, 3381,
			361, 10136, 5003, 379, 365, 373, 7946, 385, 397, 391, 3369, 3371,
			3373, 2309, 2325, 2333, 2327, 2331, 2323, 2335, 7178, 7180, 7188,
			7190, 7198, 7200, 7208, 7210, 7218, 7220, 2003, 2011, 2289, 2291,
			2293, 2295, 2297, 2299, 2301, 2303, 1891, 1893, 1895, 1897, 1899,
			1901, 7072, 7062, 7078, 7064, 7084, 7082, 7066, 7068, 1942, 6701,
			6703, 7054, 6705, 7056, 7060, 2130, 1985, 1993, 1989, 1978, 5763,
			5765, 1913, 5747, 1905, 5739, 1909, 5743, 1907, 1911, 5745, 2955,
			5749, 5751, 5753, 5755, 5757, 5759, 5761, 2084, 2034, 2048, 2036,
			2217, 2213, 2205, 2209, 2054, 2040, 2080, 2277, 2225, 2255, 2221,
			2253, 2219, 2281, 2227, 2223, 2191, 2233, 2092, 2032, 2074, 2030,
			2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971, 4608, 1883,
			1885, 1982 };

	private void Antiban() {
		// Max and Min Time
		int minMilliSecond = 500;
		int maxMillisecond = 50000;
		sleep(Random.nextInt(minMilliSecond, maxMillisecond));
		status = "move mouse";
		// randomly generated numbers for mouse
		int x = Random.nextInt(1, 450);
		int y = Random.nextInt(1, 450);
		int randomX = Random.nextInt(1, 300);
		int randomY = Random.nextInt(1, 300);
		Mouse.move(x, y, randomX, randomY);

		int ii = Random.nextInt(1, 20);
		switch (ii) {
		case 1:
			status = "Seting Angle";
			Camera.setAngle(Random.nextInt(1, 450));
			break;
		case 2:
			status = "Seting Pitch";
			Camera.setPitch(Random.nextInt(1, 450));
			break;

		case 3:
			status = "Seting Angle & Seting Pitch";
			Camera.setAngle(Random.nextInt(10, 500));
			Camera.setPitch(Random.nextInt(10, 500));
			break;
		case 4:
			status = "Seting Angle";
			Camera.setAngle(Random.nextInt(20, 300));
			break;
		case 5:
			status = "Moveing Mouse Randomly";
			Mouse.move(
					Random.nextInt(Mouse.getLocation().x - 150,
							Mouse.getLocation().x + 150),
							Random.nextInt(Mouse.getLocation().y - 150,
									Mouse.getLocation().y + 150));
			break;

		default:
			break;
		}

	}

	private boolean atEdge() {
		return edgevilleLodestoneArea.contains(Players.getLocal().getLocation());
	}

	public void AttackPlayers() {
		Players.getLoaded(new Filter<Player>() {
			@Override
			public boolean accept(Player p) {
				if (!Players.getLocal().isInCombat()) {
					status = "We See the Enemy!";
					if (p.isOnScreen()) {
						status = "Checking to see if we are in Combat";
						if (!p.isInCombat()) {
							if (p.getLevel() <= Players.getLocal().getLevel())
								status = "We Should Be Attacking "
										+ p.getName();
							p.interact("Attack");
							sleep(Random.nextInt(2500, 0));
						} else if (!p.isOnScreen()) {
							Camera.setPitch(75);
						}
					}
				}
				return p.getLocation() != null && p.validate();
			}
		});
	}

	private void Bank() {
		Bank.open();
		if(Bank.isOpen()) {
			Bank.depositInventory();
			Bank.close();
			if(Bank.close()) {
				Walking.newTilePath(pathtowallfromBank).traverse();
			}
		}
	}

	// Credits to Member Magic from Rarebot
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

	private void hopWall() {
		Timer wait = new Timer(3000); // Dynamic Sleep
		SceneObject wall = SceneEntities.getNearest(WILDY_WALL);
		wall.interact("Cross");
		while (wait.isRunning()) {
			Task.sleep(200, 400);
		}
	}

	private boolean inDeathArea() {
		return deathArea.contains(Players.getLocal().getLocation());
	}

	private boolean inWild() {
		return wildArea.contains(Players.getLocal().getLocation());
	}


	@Override
	public int loop() {
		hopWall();
		if(inWild())
			AttackPlayers();
		sleep(2000);
		if(!(Players.getLocal().getInteracting() != null))
			if(!Inventory.isFull()) 
				loot();
			else
				if(Inventory.getCount() ==28 && !Players.getLocal().isInCombat())
					TeleporttoEdgeville();
		if(atEdge())
			Walking.newTilePath(Bankpath).traverse();
		Bank();
		if (Players.getLocal().getAnimation()==-1)
			Antiban();
		if (Players.getLocal().getAnimation() == DeathAnimation)
			if(inDeathArea())
				Walking.newTilePath(deathWalkingTiles).traverse();
		return Random.nextInt(1000, 3000);
	}

	private void loot() {
		GroundItem loot = GroundItems.getNearest(LOOTID);
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

	@Override
	public void messageReceived(MessageEvent e) {
		if (Lastmessage.contains("Oh dear, you are dead")) {
			dieCount++;
		}
	}

	@Override
	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		drawMouse(g);
		g.setColor(color1);
		g.fillRect(3, 314, 515, 160);
		g.setColor(color2);
		g.fillRect(20, 333, 478, 124);
		g.setFont(font1);
		g.setColor(color1);
		g.drawString("GrasersPker by Xianb", 29, 357);
		g.drawString("Time running: " + runtime.toElapsedString(), 44, 396);
		g.drawString("Status: " + status, 33, 421);
		g.drawString("Deaths: " + dieCount, 39, 377);
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
		g.translate(0, 50);
	}

	//Credits to unown author http://pastebin.com/brShbn7i
	private void TeleporttoEdgeville() {
		final WidgetChild SKILL_TAB = Widgets.get(275, 16);
		if (SKILL_TAB.validate() && !SKILL_TAB.visible()) {
			final WidgetChild OPEN_SKILL_TAB = Widgets.get(548, 114);
			status="Interacting with Ability Book";
			if (OPEN_SKILL_TAB.validate()
					&& OPEN_SKILL_TAB.interact("Ability Book")) {
				Task.sleep(300, 600);
			}
		}
		final WidgetChild MAGIC_TAB = Widgets.get(275, 62);
		if (MAGIC_TAB.validate() && !MAGIC_TAB.visible()) {
			final WidgetChild OPEN_MAGIC_TAB = Widgets.get(275, 40);
			status="Interacting with the Magic Button";
			if (OPEN_MAGIC_TAB.validate() && OPEN_MAGIC_TAB.interact("Magic")) {
				Task.sleep(300, 600);
			}
		}
		final WidgetChild TELEPORT_TAB = Widgets.get(275, 38);
		if (TELEPORT_TAB.validate() && !TELEPORT_TAB.visible()) {
			final WidgetChild OPEN_TELEPORT_TAB = Widgets.get(275, 46);
			status="Validating Spells";
			if (OPEN_TELEPORT_TAB.validate()
					&& OPEN_TELEPORT_TAB.interact("Teleport-spells")) {
				Task.sleep(300, 600);
			}
		}
		final WidgetChild LODESTONE_BUTTON = Widgets.get(275, 16).getChild(155);
		if (LODESTONE_BUTTON.validate() && LODESTONE_BUTTON.visible())
			status="Teleporting to Edgeville";
		if (LODESTONE_BUTTON.interact("Cast")) {
			Task.sleep(800, 1200);
		}
		final WidgetChild DESTINATION_CHOOSE = Widgets.get(1092, 0);
		if (DESTINATION_CHOOSE.validate() && DESTINATION_CHOOSE.visible()) {
			final WidgetChild DESTINATION_BUTTON = Widgets.get(1092, 45); //Edgeville 
			if (DESTINATION_BUTTON.validate() && DESTINATION_BUTTON.visible()) {
				if (DESTINATION_BUTTON.interact("Teleport")) {
					Task.sleep(2000, 3000);
					final Timer TIMEOUT = new Timer(15000);
					while (TIMEOUT.isRunning() && !Players.getLocal().isIdle()) {
						Task.sleep(50, 100);
					}
				}
			}
		}
	}

}
