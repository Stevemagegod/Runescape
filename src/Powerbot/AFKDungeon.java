package Powerbot;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

@Manifest(authors = ("Graser"), name = "AFKDungeon", description = "Keeps you logged in a dungeon while your team does all the work", version = 0.5)
public class AFKDungeon extends org.powerbot.core.script.ActiveScript implements PaintListener, MessageListener {

	public String ScreenModels;
	public String status;
	public String Lastmessage;
	String Dungeon;
	String Avatar;
	public long startTime = System.currentTimeMillis();
	public long millis;
	private boolean s = false;
	int n = 0;
	int Died;
	int[] Food = { 18159, 18161, 18163, 18165, 18167, 18169, 18171, 18173, 18175, 18177 };

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
			if(shouldEat());
			Eating();
		}
		return 0;
	}

	@SuppressWarnings("deprecation")
	public boolean shouldEat()	{
		if(Players.getLocal().getHpPercent() < 600 && Inventory.contains(Food))
			return true;
		else return false;
	}

	@SuppressWarnings("unused")
	private void leaveDung() {
		clickLadder();
		if (Widgets.get(1186).getChild(8).validate()) {
			n = 0;
			Point Click = Widgets.get(1186).getChild(8).getAbsoluteLocation();
			Mouse.hop(Click.x, Click.y);
			Widgets.get(1186).getChild(8).click(true);
			sleep(1000, 1200);
		}

		Point Click = Widgets.get(1188).getChild(3).getAbsoluteLocation();
		if (Widgets.get(1188).getChild(3).validate()) {
			Mouse.hop(Click.x, Click.y);
			Widgets.get(1188).getChild(3).click(true);
		}
	}

	private void clickLadder() {
		// TODO Auto-generated method stub

	}

	private void Eating() {
		if(shouldEat()) {
			for(final Item i : Inventory.getItems()) {
				if(i != null && i.equals(Food)) {
					status= "Eating";
					i.getWidgetChild().interact("Eat");
					break;
				}
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

	public static boolean isRunEnabled() {
		return Settings.get(463) == 1;
	}

	//On start of script.. Obviously.
	public void onStart(){
		status="Hello";
	}

	//On stop of script.. Obviously.
	public void onStop(){
		Environment.captureScreen();
		Environment.saveScreenCapture("java.io.tmpdir");
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

	//START: Code generated using Enfilade's Easel

	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(0, 204, 255);
	private final Color color3 = new Color(0, 255, 0);

	private final Font font1 = new Font("Cambria", 1, 20);
	private final Font font2 = new Font("Cambria", 1, 17);

	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
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
	
	public void messageReceived(MessageEvent e) {
		String Lastmessage = e.getMessage();
		if(Lastmessage.contains("Oh dear,you are dead!"))
			Died++;
	}
}