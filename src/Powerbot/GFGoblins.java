import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;

@Manifest(authors = ("Graser"), name = "Good Fight Goblins", description = "Kills Goblins", version = 16)
public class GFGoblins extends ActiveScript implements PaintListener {

	/**
	 * @param args
	 */

	// Variables
	static String status;
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),MOUSE_BORDER_COLOR = new Color(220, 220, 220),MOUSE_CENTER_COLOR = new Color(89, 255, 89);
	public long startTime = System.currentTimeMillis();
	int[] Goblins = { 12353, 12355, 11236, 1769, 11240, 1770, 1771, 1772,12352, 1773, 1774, 1775, 1776, 445, 444, 6181, 6180, 1438 };

	//items
	int[] Lootid = { 995, 526, 555, 558, 559, 877, 554, 886 };
	int[] Junk = { 1439, 19830, 288, 2307, 1277, 1139, 1949, 1511, 25547, 9054,1351, 2132, 1438, 1917, 1987, 2138, 1009, 1173, 2138,1203 };
	int[] Food = { 1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980, 7223,
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
			2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971, 4608, 1883,1885, 1982 };
	int Bone = 526;

	public void onStart() {
		if (Game.isLoggedIn()) {
			status = "Hello";
		ClaimTicket();
		}
		if (!Game.isLoggedIn()) {
			log.info("NOT logged in");
			stop();
		}
	}

	private void ClaimTicket() {
		status="Scanning claim";
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
		Attack(); 
		Eating();
		if (Players.getLocal().getInteracting() != null) {	//Checks if we are intercting with anything at the moment
			Players.getLocal().validate(); //Verify that we aren't interacting with anything.
		}
		if (!Inventory.isFull()) {
			Looting();
			if (Inventory.isFull())
				BuryandDrop();
		}
		return 150;
	}

	private void Eating() {
		if (Inventory.contains(Food) && Inventory.getItem(Food).getWidgetChild().interact("Eat"))
			status = "Eating";
		sleep(750, 1000);
	}

	private void BuryandDrop() {
		for (Item i : Inventory.getItems()) {
			if (i.getId() == Bone) {
				status = "Burying Bones";
				i.getWidgetChild().interact("Bury");
				sleep(Random.nextInt(500, 1000));
			}
		}
		status = "Checking for Junk";
		for (Item item : Inventory.getItems()) {
			for (int id : Junk) {
				if (item.getId() == id || item.getId() == Bone) {
					status = "Droping";
					item.getWidgetChild().interact("Drop");
					sleep(Random.nextInt(300, 500));
				}
			}
		}
	}

	private void Attack() {
		if(!Players.getLocal().isInCombat()) {
			status = "Looking for Goblins!";
			if (NPCs.getNearest(Goblins) != null);
			status = "We See the Goblins!";
			if (NPCs.getNearest(Goblins).isOnScreen()) {
				status = "Checking to see if we are in Combat";
				if (!NPCs.getNearest(Goblins).isInCombat()) {
					status = "We Should Be Attacking them Now";
					NPCs.getNearest(Goblins).interact("Attack");
					sleep(Random.nextInt(2500, 0));
				} else if (!NPCs.getNearest(Goblins).isOnScreen()) {
					Camera.setPitch(75);
				}
			}
		}
	}

	private void Looting() {
		GroundItem loot = GroundItems.getNearest(Lootid);
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

	//Auto Generated Paint
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
		g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds,34, 367);
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
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
		g.fillRect(0, 0, p.x - 1, p.y - 1);
		g.fillRect(p.x + 1, 0, w - (p.x + 1), p.y - 1);
		g.fillRect(0, p.y + 1, p.x - 1, h - (p.y - 1));
		g.fillRect(p.x + 1, p.y + 1, w - (p.x + 1), h - (p.y - 1));
		g.translate(0, 50); 
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

}
