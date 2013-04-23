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
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Character;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

@Manifest(authors = ("Graser"), name = "GrasersPker", description = "Kills People like a Boss", version = 3)
public class GrasersPker extends ActiveScript implements PaintListener {

	//Variables
	private String status;
	private final double VERSION = 1.00;
	private Timer runtime = new Timer(0);
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),
			MOUSE_BORDER_COLOR = new Color(220, 220, 220),
			MOUSE_CENTER_COLOR = new Color(89, 255, 89);
	private final int[] LOOT = { 1249, 536, 1213, 1753, 12158, 12159, 12160,
			12163, 18778 };
	private final int[] WILDY_WALL = { 65093, 65095, 65094, 65081, 65083,
			65078, 65088, 65084, 65092, 65079, 65082, 65076, 65085, 65048 };
	Character Enemy;
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
	private final int TUNA = 361;

	// Walking
	private final Tile[] TO_EDGE_BANK = new Tile[] { new Tile(3083, 3497, 0),
			new Tile(3093, 3493, 0) };
	private final Tile[] TO_GREEN_DRAGONS = new Tile[] {
			new Tile(3079, 3527, 0), new Tile(3073, 3531, 0),
			new Tile(3067, 3534, 0), new Tile(3061, 3537, 0),
			new Tile(3055, 3540, 0), new Tile(3048, 3542, 0),
			new Tile(3045, 3548, 0), new Tile(3038, 3549, 0),
			new Tile(3033, 3553, 0), new Tile(3029, 3558, 0),
			new Tile(3022, 3561, 0), new Tile(3015, 3564, 0),
			new Tile(3009, 3567, 0), new Tile(3005, 3571, 0),
			new Tile(2999, 3574, 0), new Tile(2993, 3577, 0),
			new Tile(2988, 3581, 0), new Tile(2986, 3587, 0),
			new Tile(2980, 3592, 0), new Tile(2981, 3599, 0),
			new Tile(2986, 3600, 0) };
	private final Tile[] TO_EDGE_DITCH = new Tile[] { new Tile(3067, 3503, 0),
			new Tile(3073, 3506, 0), new Tile(3073, 3512, 0),
			new Tile(3076, 3518, 0), new Tile(3083, 3520, 0) };

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
		g.setColor(color1);
		g.fillRect(3, 314, 515, 160);
		g.setColor(color2);
		g.fillRect(20, 333, 478, 124);
		g.setFont(font1);
		g.setColor(color1);
		g.drawString("Good Fight Green Dragons by: Xianb", 36, 412);
		g.drawString("Version: 3" + VERSION, 44, 384);
		g.drawString("Time running: " + runtime.toElapsedString(), 34, 367);
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

	public void onStart(){
		if(Game.isLoggedIn()) {
			status="Hello World";
		}
		else
			if(!Game.isLoggedIn()) {
				status="Not Logged in";
			}
	}

	public void onStop(){
		status="Thank You for using my Script";
	}

	@Override
	public int loop() {
			attack();
		return Random.nextInt(50, 100);
	}

	public boolean inCombat() {
		if (Players.getLocal().getInteracting() != null)
			return true;
		return false;
	}

	@SuppressWarnings("unused")
	private void eat() {
		if(Players.getLocal().getHealthPercent() < 30) {
			org.powerbot.game.api.wrappers.node.Item food = Inventory.getItem(FOOD);
			if ( food != null) {
				food.getWidgetChild().interact("Eat");
				status="Eating "+food.getName();
			} else {
				status="Fuck out of food";
				sleep(500);
				GetFoodFromBank();
				return;
			}
		}
	}

	private void GetFoodFromBank() {
		do {
			status="Walking Back to Ditch";
			Walking.newTilePath(TO_GREEN_DRAGONS).reverse();
		}while(!Inventory.contains(FOOD)); {
			hopWall();
			Walking.newTilePath(TO_EDGE_BANK).traverse();
			if(Bank.getNearest() != null); {
				status="Opening Bank";
				Bank.open();
			}
			if(Bank.isOpen()) {
				Bank.depositInventory();
			}
			status="Searching for Food";
			Bank.search("Tuna");
			status="Withdrawing Food";
			Bank.withdraw(TUNA, 14);
			status="Closing Bank";
			Bank.close();
			if(Bank.close()) {
				status="Walking Back to Wild Ditch";
				Walking.newTilePath(TO_EDGE_DITCH).traverse(); 
			}
			hopWall();
			status="Walking Back to Green Dragons";
			Walking.newTilePath(TO_GREEN_DRAGONS).traverse();
		}
	}

	private void attack() {
			Enemy.interact("Attack");
	}

	@SuppressWarnings("unused")
	private void loot() {
		GroundItem loot = GroundItems.getNearest(LOOT);
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
						if(Inventory.containsAll(LOOT))
							Bank();
					}
				}
			} else {
				Walking.walk(loot.getLocation());
				Camera.turnTo(loot.getLocation());
			}
		}
	}

	private void Bank() {
		do {
			status="Inventory Full of Loot Walking to Bank";
			Walking.newTilePath(TO_GREEN_DRAGONS).reverse();
		}while(Inventory.getCount(FOOD)<14 && Inventory.contains(LOOT));
		hopWall();
		Walking.newTilePath(TO_EDGE_BANK).traverse();
		if(Bank.getNearest() != null) {
			Bank.open();
			if(Bank.open()) {
				Bank.depositInventory();
			}
			status="Withdrawing Tuna";
			Bank.withdraw(TUNA, 14);
			Bank.close();
			if(Bank.close()) {
				Walking.newTilePath(TO_EDGE_DITCH).traverse();
			}
			do {
				hopWall();
			}while(Walking.newTilePath(TO_GREEN_DRAGONS).traverse());
		}
	}

	private void hopWall() {
		Timer wait = new Timer(3000); // Dynamic Sleep
		SceneObject wall = SceneEntities.getNearest(WILDY_WALL);
		status = "Jumping wall";
		wall.interact("Cross");
		while (wait.isRunning()) {
			Task.sleep(200, 400);
		}
		return;
	}
}
