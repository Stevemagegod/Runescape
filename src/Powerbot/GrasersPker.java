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
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

@Manifest(authors = { "Xianb" }, description = "Kills People like a boss.", name = "GrasersPker")
public class GrasersPker extends ActiveScript implements PaintListener,MessageListener {

	/**
	 * @author Xianb
	 * This Script will try and Mimic some of the Best Pkers out there. It will jump the wild wall, and try to find someone to kill. 
	 */
	
	// VARIABLES
	public static final Timer t = new Timer(0);
	String status;
	String version = "1";
	public long startTime = System.currentTimeMillis();
	public boolean crossedWall;
	public boolean needToPot;
	String Lastmessage;
	int Killed;
	int BeenKilled;
	int findsomeoneelse;
	public enum State {
		UNKNOWN, ANTIBAN, CROSS_WALL, ATTACK, TELE_TO_VARROCK, BANK, WALK_TO_DRAGS, WALK_TO_WALL, EAT, Enemy
	}
	
	//items,objects, and NPCs
	public int bankBooth = 782;
	public int[] greenDragon = {941, 4679, 4677, 4680, 4678}; // NPC Id
	public int[] antifire = {2452,2454,2456,2458};
	public int dragonBone = 536, dragonHide = 1753, teleTab = 8007, goldCharm = 12158;
	static int[] FOODID = {1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980,
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
        2030, 2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971, 4608,1883, 1885,1982}; //k
	public int[] supplies = {teleTab};
	int currentAction;
	int wildyWall[] = { 65093, 65095, 65094, 65081, 65083, 65078,65088, 65084, 65092, 65079, 65082, 65076, 65085, 65048 };
	
	//Areas
	Tile wallTile = new Tile(3142, 3520, 0), dragonTile = new Tile(3339, 3684, 0), teleTile = new Tile(3339, 3674, 0),bankTile = new Tile(3185, 3436, 0);
	Area areaWildyLevel = new Area(new Tile(3100, 3564, 0), new Tile(3043, 3521, 0));
	Area areaEdgevilleBank = new Area(new Tile(3098, 3499, 0), new Tile (3094, 3493, 0));
	Area areaGE = new Area(new Tile(3189, 3515, 0), new Tile(3138, 3469, 0));
	Area Respawn = new Area(new Tile(3104, 3488, 0));

public final Area bankArea = new Area(new Tile[] {new Tile(3178, 3449, 0), new Tile(3178, 3448, 0),new Tile(3183, 3448, 0), new Tile(3188, 3448, 0),
            new Tile(3192, 3445, 0), new Tile(3194, 3440, 0),
            new Tile(3194, 3435, 0), new Tile(3191, 3431, 0),
            new Tile(3186, 3431, 0), new Tile(3180, 3431, 0)});

public final Area dragonArea = new Area(new Tile[] {new Tile(3325, 3717, 0), new Tile(3326, 3717, 0),
            new Tile(3332, 3718, 0), new Tile(3337, 3718, 0),
            new Tile(3345, 3718, 0), new Tile(3350, 3718, 0),
            new Tile(3355, 3718, 0), new Tile(3359, 3715, 0),
            new Tile(3361, 3710, 0), new Tile(3361, 3705, 0),
            new Tile(3361, 3698, 0), new Tile(3363, 3693, 0),
            new Tile(3362, 3686, 0), new Tile(3362, 3681, 0),
            new Tile(3362, 3676, 0), new Tile(3362, 3671, 0),
            new Tile(3358, 3668, 0), new Tile(3353, 3667, 0),
            new Tile(3348, 3667, 0), new Tile(3343, 3668, 0),
            new Tile(3339, 3671, 0), new Tile(3334, 3671, 0),new Tile(3329, 3669, 0)});

public final Area wallArea = new Area(new Tile[] {new Tile(3136, 3527, 0), new Tile(3135, 3527, 0),
            new Tile(3141, 3527, 0), new Tile(3147, 3525, 0),
            new Tile(3144, 3519, 0), new Tile(3140, 3516, 0),new Tile(3134, 3516, 0)});

//Waklking
Tile[] ToGreenDragons = new Tile[] {new Tile(3079, 3527, 0), new Tile(3073, 3531, 0),
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
Tile[] ToEdgevilleWildDitch = new Tile[] {new Tile(3067, 3503, 0), new Tile(3073, 3506, 0),new Tile(3073, 3512, 0), new Tile(3076, 3518, 0),new Tile(3083, 3520, 0) };
	
	 @Override
     public void onStart() {
             System.out.println("Welcome to Script");
     }

     public void onStop() {
             System.out.println("Thank you for using Script");
     }

	@Override
	public int loop() {
		if(!Game.isLoggedIn()) {
			log.info("NOT logged in...waiting 15 seconds.");
			return 15000;
		}
		HopBorder();
		if(inWilderness())
		getWildernessLevel();
		Attacking();
		Eating();
		return 1000;
	}
	
	public void Looting() {
        	int Lootid = 995; //Loots Coins
            GroundItem Loot = GroundItems.getNearest(Lootid);
            if(!Inventory.isFull())
               if(Loot != null) {
                  if(Loot.isOnScreen()){
                     //status ="Looting";
                     Loot.interact("Take");
                    sleep(800,1000);
                  }
               }
               }
	
	private void HopBorder() {
		Timer wait = new Timer(3000); //Dynamic Sleep
		SceneObject wall = SceneEntities.getNearest(wildyWall);
		status="Jumping wall";
		wall.interact("Cross");
		while(wait.isRunning()) {
            Task.sleep(200, 400);
		}
	}

	@SuppressWarnings("deprecation")
	private void Eating() {
		if(Players.getLocal().getHpPercent() < 600){
		for (final org.powerbot.game.api.wrappers.node.Item item : Inventory.getItems()) {
			for (final int ID : FOODID) {
				if (item.getId() == ID) {
					status="Eating";
					item.getWidgetChild().interact("Eat");
					return;
				}
			}
		}
		}
	}

private void Attacking() {
	status="Attacking";
	Players.getLoaded(new Filter<Player>() //loads all current players
  			 {
	           public boolean accept(Player p) {
	              if (p != null) {
	                if (!p.isInCombat() && !Players.getLocal().isInCombat() //if enemy is not in combat and we are not in combat
	                          && !Players.getLocal().isMoving()) {
	                  if(p.isOnScreen()) //if enemy is on screen
	                	  if(p.getLevel() <= Players.getLocal().getLevel()); //if Enemys Combat Level is less than or equal to are level
	                        p.interact("Attack"); //we will attack them
	                    sleep(800,1000);
	                }
	              }
	               return false;
	              }
	      }
	       );
		
	}

public int getWildernessLevel() {
        return inWilderness() ? Integer.parseInt(
                Widgets.get(381, 2).getText().replaceAll("Level: ", "")) : 0; //Gets the Current Wild Level 
    }

	private boolean inWilderness() {
		return Widgets.get(381, 2).visible() ? true : false; //Determines if we are in the wild
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
		g.drawString("GrasersPker by Xianb", 24, 121);
		g.drawString("Time running: " +hours + ":" +minutes + ":" +seconds , 34, 367);
		g.drawString("Killed: " +Killed , 33, 390);
		g.drawString("Been Killed: " +BeenKilled , 43, 422);
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

	@Override
	public void messageReceived(MessageEvent e) {
		String txt = e.getMessage();
		if (txt.contains("Well done! You have defeated")) {
			Killed++;
		}
		if (txt.contains("Oh dear, you are dead!")) {
			BeenKilled++;
		}
		if(txt.contains("He needs to move deeper into the Wilderness before you can attack him "));
		if (txt.contains("The difference between your Combat level and the Combat level of"));
}
}