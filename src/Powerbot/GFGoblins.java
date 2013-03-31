import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JFrame;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
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
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

@Manifest(authors = ("Graser"), name = "Good Fight Goblins", description = "Kills Goblins", version = 6)
public class GFGoblins extends ActiveScript implements PaintListener {

	/**
	 * @param args
	 */

	//Variables
	public String[] NPC_ARRAY;
    public String[] LOOT_ARRAY;
    String curBotStatus;
	static String status;
	boolean useFood = false;
	boolean DroppingJunk = false;
	Timer wait = new Timer(3000); //Dynamic Sleep
	Random randomGen = new Random();
	public static final Timer t = new Timer(0);
	public long startTime = System.currentTimeMillis();
	int[] Goblins = {12353,12355,11236,1769,11240, 1770, 1771, 1772,12352, 1773, 1774, 1775, 1776, 445, 444,6181,6180,1438 };
	int[] Lootid = {995,526,555,558,559,877,554,886};
	private int[] Junk = {1439,19830,288,2307,1277,1139,1949,1511,25547,9054,1351,2132,1438,1917,1987,2138,1009,1173,2138};
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
	public MainPanel main;
	//Walking Variables
	Tile[] path = { new Tile(3220, 3218, 0), new Tile(3228, 3219, 0), new Tile(3234, 3224, 0),new Tile(3240, 3226, 0), new Tile(3248, 3226, 0), new Tile(3247, 3232, 0),new Tile(3249, 3238, 0) };
	Area goblins = new Area(new Tile[] { new Tile(3241, 3231, 0), new Tile(3241, 3248, 0), new Tile(3262, 3248, 0),new Tile(3262, 3231, 0) });

	public void onStart() {
		main = new MainPanel();
        main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        main.setLocation(null);
        main.setTitle("GFGoblins");
        main.pack();
        main.setVisible(true);
        curBotStatus = ("Starting...");  
		if(Game.isLoggedIn()) //true if this client instance is logged in; otherwise false.
			status="Hello";
		getCurrentLifepoints();
			if(!Game.isLoggedIn()) {
				log.info("NOT logged in");
				stop();
			}
	}

	public void onFinish() {
		status="Thank You for using my Script";
		Environment.saveScreenCapture();
        sleep(Random.nextInt(600, 1000));
}

	@Override
	public int loop() {
		Walking.getEnergy();
		if(weareinArea() && Walking.getEnergy() >= 10)
			Walking.setRun(true);
			Attack();
			getAdrenaline();
		Eating();
		if(Players.getLocal().getInteracting() != null)
			Players.getLocal().validate();
		if(!Inventory.isFull()) //If Inventory is not full 
			Looting();
		else			
			if(Inventory.isFull())
				BuryandDrop(); 
		Antiban();
		return 150;
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

	@SuppressWarnings({ "deprecation" })
	private void Eating() {
		if (Players.getLocal().getHpPercent() < 40)
            if (useFood) {
                    if (Inventory.contains(Food)) {
                    	status="Eating";
                            Inventory.getItem(Food).getWidgetChild().interact("Eat");
                            Task.sleep(1000, 1500);
                    } else {
                            status="Fuck out of Food";
                            shutdown();
                    }
            } else {
                    status="Need food but not using food!";
                    shutdown();
            }
}

	private void BuryandDrop() {
            for (Item i : Inventory.getItems()) {
                    if (i.getId() == Bone) {
                            status = "Burying Bones";
                            i.getWidgetChild().interact("Bury");
                            sleep(Random.nextInt(500, 1000));
                    }
            }
            status="Checking for Junk";
            for(Item item : Inventory.getItems()){
				for(int id : Junk){
                        if(item.getId()==id || item.getId()==Bone){
                        	status="Droping";
                                item.getWidgetChild().interact("Drop");
                        sleep(Random.nextInt(300, 500));
                        }
				}
            }
                }

	private void Attack() {
		if (!Players.getLocal().isInCombat()) {
			status="Looking for Goblins!";
			if(NPCs.getNearest(Goblins) != null);
			status="We See the Goblins!";
			if(NPCs.getNearest(Goblins).isOnScreen()) {
				status="Checking to see if we are in Combat";
				if(!NPCs.getNearest(Goblins).isInCombat() & !Players.getLocal().isInCombat()){
					status="We Should Be Attacking them Now";
					NPCs.getNearest(Goblins).interact("Attack"); //Attacks the Goblin.
					sleep(Random.nextInt(2500, 0));
				}
				else if(!NPCs.getNearest(Goblins).isOnScreen()) {
							Camera.setPitch(75);
						}
				}
			}
		}

	private void Looting() {
		GroundItem loot = GroundItems.getNearest(Lootid);
		if (loot != null) {
			status="We see the Loot";
			if (loot.isOnScreen()) {
				status="Checking if we can reach it";
				if (loot.getLocation().canReach()) {
					status="Turning to Loot";
					Camera.turnTo(loot);
					status="Looting";
					Mouse.click(false); //if we right clicked 
							loot.interact("Take"); //we need to loot the item by getting its item
							if(loot.getGroundItem() != null) //Checking if its the correct Ground item
								loot.validate();
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

		private boolean weareinArea() {
		status="We are in the Goblins Area";
		return goblins.contains(Players.getLocal().getLocation()); //Determines whether at least one of the given tiles is contained in this area.
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
		g.translate(0,50); //From Kirinsoul
}
	
	public static int getAdrenaline() {
		status="Getting Adrenaline";
        return Settings.get(679) / 10;
}
	
	private int getCurrentLifepoints() {
        sleep(Random.nextInt(500, 1000));
        status="Getting Current Life Points";
        Widget gamePane = Widgets.get(748);
        WidgetChild lifepointsBar = gamePane.getChild(8);
        if (gamePane.validate()) {
                if (lifepointsBar.visible()) {
                        String txt = lifepointsBar.getText();
                        if (txt != null) {
                                return Integer.parseInt(txt);
                        }
                }
        }
        return -1;
}
	
	public static boolean isMomentumEnabled(){
        return Settings.get(1040) != 0;
}
	
	public boolean isActionbarUsable() {
        WidgetChild chat = Widgets.get(137, 56);
        if(chat.validate()) {
                return chat.getText().toLowerCase().contains("enter to chat");
        }
        return false;
}

	/**
	 *
	 * @author Stephen
	 */
	public class MainPanel extends javax.swing.JPanel {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
	     * Creates new form GFGoblin
	     */
	    public MainPanel() {
	        initComponents();
	    }

	    public void pack() {
			// TODO Auto-generated method stub
			
		}

		public void setTitle(String string) {
			// TODO Auto-generated method stub
			
		}

		public void setDefaultCloseOperation(int disposeOnClose) {
			// TODO Auto-generated method stub
			
		}

		/**
	     * This method is called from within the constructor to initialize the form.
	     * WARNING: Do NOT modify this code. The content of this method is always
	     * regenerated by the Form Editor.
	     */
	    
	    // <editor-fold defaultstate="collapsed" desc="Generated Code">
	    private void initComponents() {

	        jFileChooser1 = new javax.swing.JFileChooser();
	        jToggleButton1 = new javax.swing.JToggleButton();
	        jCheckBox1 = new javax.swing.JCheckBox();
	        jCheckBox2 = new javax.swing.JCheckBox();
	        jCheckBox3 = new javax.swing.JCheckBox();
	        jCheckBox4 = new javax.swing.JCheckBox();
	        jTextField1 = new javax.swing.JTextField();
	        jCheckBox5 = new javax.swing.JCheckBox();
	        jScrollPane1 = new javax.swing.JScrollPane();
	        jEditorPane1 = new javax.swing.JEditorPane();
	        jButton1 = new javax.swing.JButton();

	        jToggleButton1.setText("jToggleButton1");

	        jCheckBox1.setText("Loot");
	        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jCheckBox1ActionPerformed(evt);
	            }
	        });

	        jCheckBox2.setText("Fight");

	        jCheckBox3.setText("Summoning");

	        jCheckBox4.setText("Prayer");

	        jTextField1.setText("AIO Fighter ");
	        jTextField1.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jTextField1ActionPerformed(evt);
	            }
	        });

	        jCheckBox5.setText("Drop");

	        jScrollPane1.setViewportView(jEditorPane1);

	        jButton1.setText("Start");

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jCheckBox5)
	                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jCheckBox3)
	                    .addComponent(jCheckBox4)
	                    .addComponent(jCheckBox1)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(jCheckBox2)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(jButton1)))
	                .addContainerGap(22, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jCheckBox5)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jCheckBox3)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jCheckBox4)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                        .addGroup(layout.createSequentialGroup()
	                            .addComponent(jCheckBox1)
	                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                            .addComponent(jCheckBox2))
	                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(27, 27, 27)
	                        .addComponent(jButton1)))
	                .addContainerGap(37, Short.MAX_VALUE))
	        );
	    }// </editor-fold>

	    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {
	        // TODO add your handling code here:
	    }

	    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
	        // TODO add your handling code here:
	    }

	    // Variables declaration - do not modify
	    private javax.swing.JButton jButton1;
	    private javax.swing.JCheckBox jCheckBox1;
	    private javax.swing.JCheckBox jCheckBox2;
	    private javax.swing.JCheckBox jCheckBox3;
	    private javax.swing.JCheckBox jCheckBox4;
	    private javax.swing.JCheckBox jCheckBox5;
	    private javax.swing.JEditorPane jEditorPane1;
	    @SuppressWarnings("unused")
		private javax.swing.JFileChooser jFileChooser1;
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JTextField jTextField1;
	    private javax.swing.JToggleButton jToggleButton1;
	    // End of variables declaration
	}


}
