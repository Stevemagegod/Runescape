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
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

@Manifest(authors = ("Graser"), name = "Cow Killer", description = "Kills Cows", version = 0.1)
public class CowKiller extends ActiveScript implements PaintListener,
MessageListener {

	/**
	 * @param args
	 */

	// Variables
	final int BONES = 526;
	final int Lootid[] = { 1739,526 };
	final int LumbridgeCowsGate[] = { 45212, 45210 };
	int dieCount = 0;
	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(0, 204, 255);
	private final Color color3 = new Color(0, 255, 0);
	private final Font font1 = new Font("Cambria", 1, 20);
	private final Font font2 = new Font("Cambria", 1, 17);
	private final int[] Cow = { 12362, 12364, 12363, 12365 };
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),MOUSE_BORDER_COLOR = new Color(220, 220, 220),MOUSE_CENTER_COLOR = new Color(89, 255, 89);
	public long millis;
	public long startTime = System.currentTimeMillis();
	public String status;
	String Lastmessage;

	
	//Walking
	public final Tile[] Bankpath = {
			new Tile(3067, 3505, 0), new Tile(3070, 3500, 0),
			new Tile(3076, 3499, 0), new Tile(3081, 3497, 0),
			new Tile(3087, 3494, 0), new Tile(3094, 3493, 0)
	};
	public final Area Cowarea = new Area(new Tile[] { new Tile(3254, 3258, 0),
			new Tile(3253, 3264, 0), new Tile(3254, 3270, 0),
			new Tile(3253, 3276, 0), new Tile(3248, 3281, 0),
			new Tile(3242, 3287, 0), new Tile(3243, 3293, 0),
			new Tile(3246, 3298, 0), new Tile(3246, 3297, 0),
			new Tile(3251, 3297, 0), new Tile(3253, 3296, 0),
			new Tile(3258, 3295, 0), new Tile(3263, 3293, 0),
			new Tile(3268, 3291, 0), new Tile(3258, 3267, 0),
			new Tile(3262, 3264, 0), new Tile(3265, 3260, 0),
			new Tile(3258, 3267, 0), new Tile(3263, 3266, 0),
			new Tile(3266, 3261, 0), new Tile(3266, 3256, 0),
			new Tile(3258, 3267, 0), new Tile(3263, 3268, 0),
			new Tile(3266, 3272, 0), new Tile(3265, 3277, 0),
			new Tile(3265, 3282, 0), new Tile(3259, 3267, 0),
			new Tile(3260, 3262, 0), new Tile(3263, 3258, 0) });
	public final Tile[] Lodestonepathtocows = {
			new Tile(3233, 3221, 0), new Tile(3237, 3224, 0),
			new Tile(3243, 3227, 0), new Tile(3250, 3226, 0),
			new Tile(3255, 3228, 0), new Tile(3259, 3233, 0),
			new Tile(3261, 3239, 0), new Tile(3260, 3245, 0),
			new Tile(3255, 3249, 0), new Tile(3250, 3252, 0),
			new Tile(3251, 3258, 0), new Tile(3255, 3261, 0)
	};
	public final Area LumbridgeLodestonearea = new Area(new Tile[] {
			new Tile(3231, 3220, 0), new Tile(3233, 3220, 0),
			new Tile(3233, 3220, 0), new Tile(3233, 3220, 0),
			new Tile(3234, 3225, 0), new Tile(3230, 3223, 0),
			new Tile(3235, 3223, 0), new Tile(3238, 3219, 0),
			new Tile(3234, 3215, 0), new Tile(3229, 3217, 0),
			new Tile(3230, 3222, 0), new Tile(3234, 3225, 0),
			new Tile(3229, 3224, 0), new Tile(3233, 3227, 0),
			new Tile(3236, 3223, 0), new Tile(3234, 3228, 0),
			new Tile(3229, 3227, 0), new Tile(3229, 3222, 0),
			new Tile(3234, 3226, 0) 
	});
	Tile[] AreaLumbridgeCows = new Tile[] { new Tile(3253, 3265, 0),
			new Tile(3254, 3256, 0), new Tile(3264, 3259, 0),
			new Tile(3243, 3283, 0), new Tile(3241, 3295, 0),
			new Tile(3258, 3294, 0), new Tile(3262, 3279, 0) };
	Area edgevilleBankArea = new Area(new Tile[] { new Tile(3090, 3499, 0), new Tile(3097, 3499, 0), new Tile(3097, 3487, 0), 
			new Tile(3090, 3487, 0) });
	Area edgevilleLodestoneArea = new Area(new Tile[] { new Tile(3062, 3509, 0), new Tile(3067, 3509, 0), new Tile(3071, 3508, 0), 
			new Tile(3070, 3501, 0), new Tile(3064, 3499, 0), new Tile(3060, 3506, 0) });
	
	public void Antiban() {
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

	private boolean atBank() {
		return edgevilleBankArea.contains(Players.getLocal().getLocation());
	}

	private boolean atCows() {
		return Cowarea.contains(Players.getLocal().getLocation());
	}

	private boolean atEdge() {
		return edgevilleLodestoneArea.contains(Players.getLocal().getLocation());
	}

	private boolean atLumbridge() {
		return LumbridgeLodestonearea.contains(Players.getLocal().getLocation());
	}

	private void Attackcows() {
		if (!Players.getLocal().isInCombat()) {
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

	private void Bank() {
		if(atBank()) {
			status="Opening Bank";
			Bank.open();
			if(Bank.isOpen()) {
				status="Depositing Inventory";
				Bank.depositInventory();
				status="Closing Bank";
				Bank.close();
			}
		}
	}

	private void Bury() {
		for (Item i : Inventory.getItems()) // The for statement using 3
			// Variables Item, I, and
			// Inventory.getitems()
		{
			if (i.getId() == BONES) // if i equals Bone
			{
				status = "Burying Bones";
				i.getWidgetChild().interact("Bury"); // we will bury the bone
				sleep(Random.nextInt(500, 1000)); // then sleep.
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

	@Override
	public int loop() {
		if(atCows()) 
			Attackcows();
		sleep(2000);
		if (!(Players.getLocal().getInteracting() != null))
			if(atCows())
				Loot();
		sleep(3000);
		while (Inventory.getCount() == 28) {
			if (Inventory.contains(BONES))
				Bury();
			else if (!Inventory.contains(BONES) && Inventory.isFull()) {
				TeleporttoEdgeville();
				if(atEdge())
					status = "Walking to Edgeville Bank";
				Walking.newTilePath(Bankpath).traverse();
				Bank();
				if(!Inventory.isFull()) 
					while(atBank())
						TeleporttoLumbridge();
				status ="Walking Back to Cows";
				if(atLumbridge())
				Walking.newTilePath(Lodestonepathtocows).traverse();
			}
		}
		return Random.nextInt(150, 450);
	}

	private void Loot() {
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
				}
			} else {
				Walking.walk(loot.getLocation());
				Camera.turnTo(loot.getLocation());
				Random.nextInt(1000,9000);
			}
		}
	}

	@Override
	public void messageReceived(MessageEvent e) {
		if (Lastmessage.contains("Oh dear, you are dead")) {
			dieCount++;
		}

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
		g.drawString("Cow Killer by Graser", 29, 357);
		g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds,
				30, 402);
		g.drawString("Status: " + status, 33, 421);
		g.drawString("Deaths:"+dieCount, 39,377);
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

	public void onStart() {
		if (Game.isLoggedIn())
			status = "Hello World";
		else if (!Game.isLoggedIn()) {
			status = "Not Logged in";
		}
		return;
	}


	public void onStop() {
		status = "Thank you for using my script";
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

	//Credits to unown author http://pastebin.com/brShbn7i
	private void TeleporttoLumbridge() {
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
			status="Teleporting to Lumbridge";
		if (LODESTONE_BUTTON.interact("Cast")) {
			Task.sleep(800, 1200);
		}
		final WidgetChild DESTINATION_CHOOSE = Widgets.get(1092, 0);
		if (DESTINATION_CHOOSE.validate() && DESTINATION_CHOOSE.visible()) {
			final WidgetChild DESTINATION_BUTTON = Widgets.get(1092, 47); //Lumbridge 
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
