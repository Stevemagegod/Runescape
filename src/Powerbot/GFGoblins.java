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
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.powerbot.game.api.methods.widget.Bank;

@Manifest(authors = ("Graser"), name = "Good Fight Goblins", description = "Kills Goblins", version = 18)
public class GFGoblins extends ActiveScript implements PaintListener,
		MessageListener {

	/**
	 * @param Author Xianb
	 * This Script is easily convertable to any other monster in runescape. It doesn't have to be just Goblins.
	 */

	// Variables
	private Tile[] goblinArea = new Tile[] { new Tile(3256, 3241, 0),
			new Tile(3251, 3244, 0) };
	private final Tile[] walkToEdgevilleBank = new Tile[] {
			new Tile(3082, 3501, 0), new Tile(3095, 3496, 0) };
	private final Tile[] walktoGoblins = new Tile[] { new Tile(3234, 3222, 0),
			new Tile(3250, 3226, 0), new Tile(3259, 3236, 0),
			new Tile(3252, 3251, 0), new Tile(3249, 3266, 0) };
	String lastMessage = "", status = "";
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),
			MOUSE_BORDER_COLOR = new Color(220, 220, 220),
			MOUSE_CENTER_COLOR = new Color(89, 255, 89);
	public long startTime = System.currentTimeMillis();
	int dead;
	int[] goblins = { 12353, 12355, 11236, 1769, 11240, 1770, 1771, 1772,
			12352, 1773, 1774, 1775, 1776, 445, 444, 6181, 6180, 1438 };

	// items
	int[] lootid = { 995, 526, 555, 558, 559, 877, 554, 886 };
	int[] junk = { 1439, 19830, 288, 2307, 1277, 1139, 1949, 1511, 25547, 9054,
			1351, 2132, 1438, 1917, 1987, 2138, 1009, 1173, 2138, 1203 };
	int[] food = { 1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980, 7223,
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
	int BONE = 526;

	public void onStart() {
		if (Game.isLoggedIn()) {
			status = "Hello";
			claimTicket();
		}
		if (!Game.isLoggedIn()) {
			status = "NOT logged in";
		}
	}

	private void claimTicket() {
		status = "Scanning for claim";
		final Item item = Inventory.getItem(24154);
		if (item != null && item.getWidgetChild().click(true)) {
			final Timer t = new Timer(2500);
			while (t.isRunning() && Inventory.getCount(24154) > 0) {
				sleep(50);
			}
		}
	}

	public void onStop() {
		status = "Thank You for using my Script";
	}

	@Override
	public int loop() {
		Antiban();
		//Stops the Script if the scripts gets out of area perfectly just the way i want
		if (goblinArea.equals(goblinArea)) {
			Attack();
			sleep(Random.nextInt(500, 1000));
		}
		Eating();
		if (!Inventory.isFull()) {
			Looting();
		} else if (Inventory.isFull())
			buryandDrop();
		return Random.nextInt(50, 100);
	}

	private void Antiban() {
		//Max and Min Time
		int minMilliSecond = 500;
		int maxMillisecond = 50000;
		sleep(Random.nextInt(minMilliSecond, maxMillisecond));
		status="move mouse";
		//randomly generated numbers for mouse
		int x=Random.nextInt(1,450);
		int y=Random.nextInt(1,450);
		int randomX= Random.nextInt(1,300);
		int randomY=Random.nextInt(1,300);
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

	private void Eating() {
		if (Players.getLocal().isInCombat()) {
			status = "Getting Health " + Players.getLocal().getHealthPercent();
			if (Players.getLocal().getHealthPercent() <= 20
					&& Inventory.contains(food)) {
				status = "Eating "
						+ Inventory.getItem(food).getWidgetChild()
								.interact("Eat");
				if (!Inventory.contains(food)) {
					status = "Fuck we out of Food";
				}
			}
		}
	}

	private void buryandDrop() {
		for (Item i : Inventory.getItems()) {
			if (i.getId() == BONE) {
				status = "Burying Bones";
				i.getWidgetChild().interact("Bury");
				sleep(Random.nextInt(500, 1000));
			}
		}
		status = "Checking for Junk";
		for (Item item : Inventory.getItems()) {
			for (int id : junk) {
				if (item.getId() == id || item.getId() == BONE) {
					status = "Droping";
					item.getWidgetChild().interact("Drop");
					sleep(Random.nextInt(300, 500));
				}
			}
		}
	}

	//phlow showed me how to do the following without NPCs.getNearest Goblins multiple times but then it wouldn't be my own work if i used his.
	private void Attack() {
		if (!Players.getLocal().isInCombat()) {
			status = "Looking for Goblins!";
			if (goblins != null);
			status = "We See the Goblins!";
			if (NPCs.getNearest(goblins).isOnScreen()) {
				status = "Checking to see if we are in Combat";
				if (!NPCs.getNearest(goblins).isInCombat()) {
					status = "We Should Be Attacking them Now";
					NPCs.getNearest(goblins).interact("Attack");
					sleep(Random.nextInt(2500, 0));
				} else if (!NPCs.getNearest(goblins).isOnScreen()) {
					Camera.setPitch(75);
				}
			}
		}
	}

	private void Looting() {
		GroundItem loot = GroundItems.getNearest(lootid);
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

	// Auto Generated Paint
	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(0, 204, 255);
	private final Color color3 = new Color(0, 255, 0);
	private final Font font1 = new Font("Cambria", 1, 20);
	private final Font font2 = new Font("Cambria", 1, 17);

	@Override
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
		g.drawString("Good Fight Goblins by: Xianb", 36, 412);
		g.drawString("Version: 15", 44, 384);
		g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds,
				34, 367);
		g.drawString("Status: " + status, 35, 444);
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

	@Override
	public void messageReceived(MessageEvent e) {
		lastMessage = e.getMessage();
		if (lastMessage.contains("Oh dear, you are dead")) {
			dead++;
			status = "Death Walk"
					+ Walking.newTilePath(walktoGoblins).traverse();
		}
		if (Inventory.isFull() && !Inventory.contains(BONE)) {
			Bank();
		}

	}

	private void Bank() {
		TeleporttoEdgeville();
		Walking.newTilePath(walkToEdgevilleBank).traverse();
		Task.sleep(500, 750);
		Bank.open();
		if (Bank.isOpen()) {
			Bank.depositInventory();
			if (!Inventory.contains(lootid)) {
				Bank.close();
			}
			TeleporttoLumbridge();
			sleep(Random.nextInt(500, 1000));
		}

	}

	// Credits to unown author http://pastebin.com/brShbn7i
	private void TeleporttoLumbridge() {
		final WidgetChild SKILL_TAB = Widgets.get(275, 16);
		if (SKILL_TAB.validate() && !SKILL_TAB.visible()) {
			final WidgetChild OPEN_SKILL_TAB = Widgets.get(548, 114);
			status = "Interacting with Ability Book";
			if (OPEN_SKILL_TAB.validate()
					&& OPEN_SKILL_TAB.interact("Ability Book")) {
				Task.sleep(300, 600);
			}
		}
		final WidgetChild MAGIC_TAB = Widgets.get(275, 62);
		if (MAGIC_TAB.validate() && !MAGIC_TAB.visible()) {
			final WidgetChild OPEN_MAGIC_TAB = Widgets.get(275, 40);
			status = "Interacting with the Magic Button";
			if (OPEN_MAGIC_TAB.validate() && OPEN_MAGIC_TAB.interact("Magic")) {
				Task.sleep(300, 600);
			}
		}
		final WidgetChild TELEPORT_TAB = Widgets.get(275, 38);
		if (TELEPORT_TAB.validate() && !TELEPORT_TAB.visible()) {
			final WidgetChild OPEN_TELEPORT_TAB = Widgets.get(275, 46);
			status = "Validating Spells";
			if (OPEN_TELEPORT_TAB.validate()
					&& OPEN_TELEPORT_TAB.interact("Teleport-spells")) {
				Task.sleep(300, 600);
			}
		}
		final WidgetChild LODESTONE_BUTTON = Widgets.get(275, 16).getChild(155);
		if (LODESTONE_BUTTON.validate() && LODESTONE_BUTTON.visible())
			status = "Teleporting to Lumbridge";
		if (LODESTONE_BUTTON.interact("Cast")) {
			Task.sleep(800, 1200);
		}
		final WidgetChild DESTINATION_CHOOSE = Widgets.get(1092, 0);
		if (DESTINATION_CHOOSE.validate() && DESTINATION_CHOOSE.visible()) {
			final WidgetChild DESTINATION_BUTTON = Widgets.get(1092, 47); // Lumbridge
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

	// Credits to unown author http://pastebin.com/brShbn7i
	private void TeleporttoEdgeville() {
		final WidgetChild SKILL_TAB = Widgets.get(275, 16);
		if (SKILL_TAB.validate() && !SKILL_TAB.visible()) {
			final WidgetChild OPEN_SKILL_TAB = Widgets.get(548, 114);
			status = "Interacting with Ability Book";
			if (OPEN_SKILL_TAB.validate()
					&& OPEN_SKILL_TAB.interact("Ability Book")) {
				Task.sleep(300, 600);
			}
		}
		final WidgetChild MAGIC_TAB = Widgets.get(275, 62);
		if (MAGIC_TAB.validate() && !MAGIC_TAB.visible()) {
			final WidgetChild OPEN_MAGIC_TAB = Widgets.get(275, 40);
			status = "Interacting with the Magic Button";
			if (OPEN_MAGIC_TAB.validate() && OPEN_MAGIC_TAB.interact("Magic")) {
				Task.sleep(300, 600);
			}
		}
		final WidgetChild TELEPORT_TAB = Widgets.get(275, 38);
		if (TELEPORT_TAB.validate() && !TELEPORT_TAB.visible()) {
			final WidgetChild OPEN_TELEPORT_TAB = Widgets.get(275, 46);
			status = "Validating Spells";
			if (OPEN_TELEPORT_TAB.validate()
					&& OPEN_TELEPORT_TAB.interact("Teleport-spells")) {
				Task.sleep(300, 600);
			}
		}
		final WidgetChild LODESTONE_BUTTON = Widgets.get(275, 16).getChild(155);
		if (LODESTONE_BUTTON.validate() && LODESTONE_BUTTON.visible())
			status = "Teleporting to Edgeville";
		if (LODESTONE_BUTTON.interact("Cast")) {
			Task.sleep(800, 1200);
		}
		final WidgetChild DESTINATION_CHOOSE = Widgets.get(1092, 0);
		if (DESTINATION_CHOOSE.validate() && DESTINATION_CHOOSE.visible()) {
			final WidgetChild DESTINATION_BUTTON = Widgets.get(1092, 45); // Edgeville
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
