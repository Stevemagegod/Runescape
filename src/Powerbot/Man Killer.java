import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Prayer;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;

@Manifest(authors = { "Xianb" }, description = "Kills men like a boss.", name = "Man Killer")
public class Mankiller extends ActiveScript implements PaintListener {

  /**
	 * @author Xianb
	 */
	
	// VARIABLES
	public static final Timer t = new Timer(0);
	public static final int[] MEN = { 819, 808 }; // NPC Id
	int Fighting; //Adding the Server Message Later 
	String status;
	String version = "1";
	public long startTime = System.currentTimeMillis();

	int[] FOODID = {1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980,
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
            2030, 2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971, 4608,
            1883, 1885,1982}; //k

	@Override
	public int loop() {
		if(Game.isLoggedIn()) //Checks to make sure we are Logged in
		antiban(); //Basic Antiban 
		fight();
		if(!Players.getLocal().isInCombat()) //if we are not in Combat we should be Looting
			Looting(); //Loots Shit
		else {
			if(Players.getLocal().isInCombat()); //if we are in Combat we should be Eating if we need to
			Eating(); //Eats
		}
		return 50;
	}

	private void Eating() {
		if(Players.getLocal().getHealthPercent() < 50) //I May need to change that Number
			{
			Item food = Inventory.getItem(FOODID);
			if ( food != null) {
				food.getWidgetChild().click(true);
				log.severe("Eating...");
				status="Eating...";
			} else {
				Prayer.togglePrayer(Prayer.Normal.RETRIBUTION, true);
				status="Fuck out of food";
				sleep(500);
				return;
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void Looting() {
        int Lootid = 995; //Loots Coins
        GroundItem Loot = GroundItems.getNearest(Lootid);
        if(!Inventory.isFull())
           if(Loot != null) {
              if(Loot.isOnScreen()){
                 status ="Looting";
                 Loot.interact("Take");
                 Time.sleep(800,1000);
              }
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
		g.drawString("Mankiller by Jesus Christ", 29, 357);
		g.drawString("Time running: " +hours + ":" +minutes + ":" +seconds , 6, 405);
		g.drawString("Killed: " +Fighting , 25, 420);
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

	// Fight the men
	public static void fight() {
		final NPC MAN = NPCs.getNearest(MEN); //Gets the Nearest Man
		if (MAN != null) {
			MAN.interact("Attack"); //Attacks Him 
			Task.sleep(Random.nextInt(1000, 2000)); //Sleeps
		}
	}
	
	private void antiban() {
        if (Random.nextInt(1, 100) >= 99) {
                int num = Random.nextInt(1, 200);
                int degree = Random.nextInt(1, 360);
                int pitch = Random.nextInt(1, 360);
                if (num <= 25) {
                        Camera.setNorth(); //Turns North
                } else if (num <= 50) {
                        Camera.setAngle(degree); //Sets the Camera Angle 
                } else if (num <= 75) {
                        Camera.setPitch(pitch);//Sets the Camera Pitch
                } else if (num <= 100){
                        Tabs.STATS.open(false); //Opens the Stats Tab
                } else if (num <= 125) {
                        Tabs.EQUIPMENT.open(false); //Opens the Equipment Tab
                } else if (num <= 150) {
                        Tabs.FRIENDS.open(false); //Opens the Friends Tab
                } else if (num <= 175) {
                        Tabs.PRAYER.open(false); //Opens the Prayer Icon
                } else {
                        Camera.setAngle(degree); //Sets the Camera Angle
                        Camera.setPitch(pitch); //Sets the Camera Pitch
                }
        }
}
}
