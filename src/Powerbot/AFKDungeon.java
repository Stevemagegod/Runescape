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
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

@Manifest(authors = ("Graser"), name = "AFKDungeon", description = "Keeps you logged in a dungeon while your team does all the work", version = 0.5)
public class AFKDungeon extends org.powerbot.core.script.ActiveScript implements PaintListener, MessageListener {

	public String status;
	public long startTime = System.currentTimeMillis();
	public long millis;
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),MOUSE_BORDER_COLOR = new Color(220, 220, 220),MOUSE_CENTER_COLOR = new Color(89, 255, 89);
	private boolean s = false;
	int n = 0;
	int Died;
	int Smuggler = 11226;
	int[] Food = {18159, 20842, 20861, 18161, 18162, 18163, 18164,
			18165, 18166, 18167, 18168, 18169, 18170, 18171, 18172, 18173,
			18174, 18175, 18176, 18177, 18178 };
	int[] Lootid = {18314, 18316, 18318, 18320, 18322, 18324, 18326,18159,18201,
			18328, 18298, 18300, 18302, 18304, 18306, 18308, 18310, 18312,
			18282, 18284, 18286, 18288, 18290, 18292, 18294, 18296, 18218,
			18220, 18222, 18224, 18226, 18228, 18230, 18232, 18202, 18204,
			18206, 18208, 18210, 18212, 18214, 18216, 18266, 18268, 18270,
			18272, 18274, 18276, 18278, 18280, 18250, 18252, 18254, 18256,
			18258, 18260, 18262, 18264, 18234, 18236, 18238, 18240, 18242,
			18244, 18246, 18248,18159, 20842, 20861, 18161, 18162, 18163, 18164,
			18165, 18166, 18167, 18168, 18169, 18170, 18171, 18172, 18173,
			18174, 18175, 18176, 18177, 18178,18234,18236,18238,18240,18242,18244,18246,18248,18250,18252,18254,18256,18258,18260,18262,18264,18266,18268,18270,18272,18274,18276,18278,18280,18202,18204,18206,18208,18210,18212,18214,18216,18218,18220,18222,18224,18226,18228,18230,18232,18282,18284,18286,18288,18290,18292,18294,18296,18298,18300,18302,18304,18306,18308,18310,18312,18314,18316,18318,18320,18322,18324,18326,18328};
	int Monsters[]={10196, 10197, 10198, 10199, 10200, 10201, 10202, 10203, 10204, 10205, 10206,10250, 10251, 10252, 10256, 10258, 10259, 10262, 10263, 10265, 10266, 10270, 10271, 
10272, 10273, 10277, 10284, 10286, 10292, 10293, 10298, 10299, 10300, 10307, 10402, 10403, 10409, 10416, 10417, 10430, 10445, 10450, 
10451, 10452, 10457, 10458, 10517, 10526, 10546, 10548, 10847, 10848, 10849, 10854, 10855, 10856, 10860, 10861, 10868, 10877, 10881, 
10883, 10884, 10888, 10889, 10897,10246, 10247, 10248, 10253, 10254, 10255, 10260, 10261, 10267, 10274, 10289, 10295,5572,10480,10236,
10296, 10297, 10302, 10303, 10397, 10398, 10399, 10401, 10404, 10405, 10411, 10412, 10413, 10419, 10426, 10439, 10448, 10455, 10509, 
10542, 10845, 10851, 10858, 10866, 10873, 10885, 10886, 10899, 10893,10694, 10695, 10696, 10697, 10698, 10699, 10700, 10701, 10702, 10704, 10705,};
	
	public int loop() {					
		Antiban();
		if (Game.getPlane() == 1) {
			clickStairs();
		}
		if (Game.getPlane() == 0 && outSide() && s == false) {
			ClickDoor();
			FormParty();
			SetDifficulty();
			SetSize();	
		}
		Looting();
		if(checking()) {
		Attacking();
		}
		return Random.nextInt(100, 300);
	}

	private boolean checking() {
		return Players.getLocal().getInteracting() != null;
	}

	private void Attacking() {
		if(!Players.getLocal().isInCombat()) {
			status = "Looking for Monsters!";
			if (Monsters != null);
			status = "We See the Monsters!";
			if (NPCs.getNearest(Monsters).isOnScreen()) {
				status = "Checking to see if we are in Combat";
				if (!NPCs.getNearest(Monsters).isInCombat()) {
					status = "We Should Be Attacking the "+NPCs.getNearest(Monsters).getName();
					NPCs.getNearest(Monsters).interact("Attack");
					sleep(Random.nextInt(2500, 0));
				} else if (!NPCs.getNearest(Monsters).isOnScreen()) {
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

	private boolean outSide() {
		Area area = new Area(new Tile(3445, 3718, 0), new Tile(3472, 3729, 0));
		return area.contains(Players.getLocal().getLocation().getX(), Players.getLocal().getLocation().getY());
	}

	public void log(Object message) {
		log.info(message.toString());
	}

	private void SetSize() {
		if (Widgets.get(1188).getChild(3).validate()) {
			Point Click = Widgets.get(1188).getChild(3).getAbsoluteLocation();
			Mouse.hop(Click.x, Click.y);
			Widgets.get(1188).getChild(3).click(true);
			sleep(1200, 2000);
		}

	}

	private void SetDifficulty() {
		if (Widgets.get(947).getChild(761).validate()) {
			Point Click = Widgets.get(947).getChild(761).getAbsoluteLocation();
			Mouse.hop(Click.x, Click.y);
			Widgets.get(947).getChild(761).click(true);
			sleep(1000, 1200);
		}

		if (Widgets.get(938).getChild(39).validate()) {
			Point Click = Widgets.get(938).getChild(39).getAbsoluteLocation();
			Mouse.hop(Click.x, Click.y);
			Widgets.get(938).getChild(39).click(true);
			sleep(1000, 1200);
		}

	}

	private void FormParty() {
		if (Widgets.get(1186).getChild(8).validate()) {
			Point Click = Widgets.get(1186).getChild(8).getAbsoluteLocation();
			Mouse.hop(Click.x, Click.y);
			Widgets.get(1186).getChild(8).click(true);
			sleep(1200, 2000);
		}

		if (Widgets.get(1188).getChild(3).validate()) {
			Point Click = Widgets.get(1188).getChild(3).getAbsoluteLocation();
			Mouse.hop(Click.x, Click.y);
			Widgets.get(1188).getChild(3).click(true);
			sleep(1200, 2000);
		}

	}

	private void ClickDoor() {
		if (SceneEntities.getNearest(48496) !=null) {
			if (!Widgets.get(1188).getChild(3).validate() && !Widgets.get(938).getChild(39).validate() && !Widgets.get(1186).getChild(8).validate() && !Widgets.get(947).getChild(761).validate()) {
				SceneObject dungDoor = SceneEntities.getNearest(48496);
				if (!dungDoor.isOnScreen() && !Players.getLocal().isMoving()) {
					Walking.walk(dungDoor.getLocation());
					sleep(200, 300);
				}

				if (dungDoor.isOnScreen()) {
					dungDoor.interact("Climb-down");
					sleep(1200, 1400);
				}
			}
		}

	}

	private void clickStairs() {
		if (SceneEntities.getNearest(50552) != null && Players.getLocal().getAnimation() != 13760) {
			SceneObject dungStairs = SceneEntities.getNearest(50552);
			if (!dungStairs.isOnScreen() && !Players.getLocal().isMoving()) {
				Walking.walk(dungStairs.getLocation());
			}

			if (dungStairs.isOnScreen() && Players.getLocal().getAnimation() != 13760) {
				dungStairs.interact("Jump-down");
				sleep(800, 1000);
			}
		}

	}

	public void onStart(){
		if (Game.isLoggedIn()) {
			status = "Hello";
		}
		if (!Game.isLoggedIn()) {
			log.info("NOT logged in");
		}
	}

	public void onStop(){
		        status="Thank You for using my Script";
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

	public void messageReceived(MessageEvent e) {
		String Lastmessage = e.getMessage();
		if(Lastmessage.contains("Oh dear,you are dead!"))
			Died++;
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
		g.drawString("AFKDungeon by Graser", 29, 357);
		g.drawString("Died: "+ Died, 36, 375);
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
}
