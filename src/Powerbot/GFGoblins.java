import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;

@Manifest(authors = ("Graser"), name = "Good Fight Goblins", description = "Kills Goblins", version = 2)
public class GFGoblins extends ActiveScript implements PaintListener {

	/**
	 * @param args
	 */

	//Variables
	String status;
	public static final Timer t = new Timer(0);
	public long startTime = System.currentTimeMillis();
	int[] Goblins = {12353,12355,11236,1769,11240, 1770, 1771, 1772, 1773, 1774, 1775, 1776, 445, 444,6181,6180 };
	int[] Lootid = {995,526,555,559};
	int[] Junk = {1439,19830,288,2307,1277,1139,1949};
	int[] Food = {1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980,
			7223, 6297, 6293, 6295, 6299, 7521, 9988, 7228, 2878, 7568, 2343,
			1861, 13433, 315, 325, 319, 3144, 347, 355, 333, 339, 351, 329,
			3381, 361, 10136, 5003, 379, 365, 373, 7946, 385, 397, 391, 3369,
			3371, 3373, 2309, 2325, 2333, 2327, 2331, 2323, 2335, 7178, 7180,
			7188, 7190, 7198, 7200, 7208, 7210, 7218, 7220, 2003, 2011, 2289,
			2291, 2293, 2295, 2297, 2299, 2301, 2303, 1891, 1893, 1895, 1897,
			1899, 1901, 7072, 7062, 7078, 7064, 7084, 7082, 7066, 7068, 1942,
			6701, 6703, 7054, 6705, 7056, 7060, 2130, 1985, 1993, 1989, 1978,
			5763, 5765, 1913, 5747, 1905, 5739, 1909, 5743, 1907, 1911, 5745,
			2955, 5749, 5751, 5753, 5755, 5757, 5759, 5761, 2084, 2034, 2048,
			2036, 2217, 2213, 2205, 2209, 2054, 2040, 2080, 2277, 2225, 2255,
			2221, 2253, 2219, 2281, 2227, 2223, 2191, 2233, 2092, 2032, 2074,
			2030, 2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971, 4608,1883, 1885,1982};
	int Bone = 526;
	boolean DeathAnimation = false;
	
	//Walking Variables
	Tile[] path = { new Tile(3220, 3218, 0), new Tile(3228, 3219, 0), new Tile(3234, 3224, 0),new Tile(3240, 3226, 0), new Tile(3248, 3226, 0), new Tile(3247, 3232, 0),new Tile(3249, 3238, 0) };
	Area goblins = new Area(new Tile[] { new Tile(3241, 3231, 0), new Tile(3241, 3248, 0), new Tile(3262, 3248, 0),new Tile(3262, 3231, 0) });

	public void onStart() {
		if(Game.isLoggedIn()) //true if this client instance is logged in; otherwise false.
			status="Hello";
	}

	public void onFinish() {
		status="Thank You for using my Script";
	}

	@Override
	public int loop() {
		if(!Game.isLoggedIn()) {
			log.info("NOT logged in...waiting 15 seconds.");
			return 15000;
		}
		Antiban();
		if(weareinArea()) //if we are in the Goblin area
			Attack();
		if(NPCs.getNearest(Goblins).isInCombat()) //if the Goblin is in Combat
			do
				if(NPCs.getNearest(Goblins).getInteracting() != null); //checks if goblins are interacting
			while(Players.getLocal().isInCombat());  //while we are in combat
		Eating();
		if(Players.getLocal().isIdle() && !Players.getLocal().isInCombat()) //If we are idle and we are not in Combat
			Looting(); //We should be Looting
		else
			if(wearedead())
				Walking.newTilePath(path).traverse();
		return 0;
	}

	private boolean wearedead() {
		return DeathAnimation;
	}

	private void Eating() {
		Players.getLocal().getHealthPercent(); //we should be checking are health and 
		if(Players.getLocal().getHealthPercent() <= 60) //if are health is less than 60 we need to eat
			Inventory.getItem(Food).getWidgetChild().click(true); //We should be Eating now
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

	private void Attack() {
		NPCs.getNearest(Goblins);//we should get The nearest Goblin with one of these Ids if any present, else null.
			status="We see the Goblins!";
		if(NPCs.getNearest(Goblins).isOnScreen()) //Determines if the Goblin is on screen.
			status="Were attacking them Now";
		NPCs.getNearest(Goblins).interact("Attack"); //Attacks the Goblin.
		sleep(700,800);
		
	}

	private boolean weareinArea() {
		return goblins.contains(Players.getLocal().getLocation()); //Determines whether at least one of the given tiles is contained in this area.
	}

	private void Looting() {
		org.powerbot.game.api.wrappers.node.GroundItem Loot = GroundItems.getNearest(Lootid);
           if(Loot != null) {
              if(Loot.isOnScreen()){
                 status ="Looting";
                 Loot.interact("Take");
                sleep(800,1000);
				if(Inventory.isFull() //Checks whether the inventory is full. 
						&& Inventory.getItem(Junk).getWidgetChild().interact("Drop")) //And if Inventory contains junk we need to drop it 
					BuryBones();
			}
		}
	}

	private void BuryBones() 
	{
		for (Item i : Inventory.getItems()) {
            if (i.getId() == Bone) {
                status ="Burying Bones";
                i.getWidgetChild().interact("Bury");
                sleep(850, 1550);
            }
        }
	}

	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(0, 204, 255);
	private final Color color3 = new Color(0, 255, 0);
	private final Font font1 = new Font("Cambria", 1, 20);
	private final Font font2 = new Font("Cambria", 1, 17);

	@Override
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
		g.drawString("Good Fight Goblins by: Xianb", 36, 412);
		g.drawString("Time running: " +hours + ":" +minutes + ":" +seconds , 34, 367);
		g.drawString("Status: "+status , 35, 444);
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

}
