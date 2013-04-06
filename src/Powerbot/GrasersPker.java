import java.awt.*;
import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.bot.event.listener.PaintListener;

@Manifest(name = "GrasersPker",authors = {"Graser"},description = "The Worlds Very First Pking Script.",version = 1.0D,website = "")
public class GrasersPker extends ActiveScript implements Task, Condition, PaintListener {

	 private Player[] players = null;
	 boolean nonCombat = false;
	 public boolean Activated = false;
	    public boolean enableSpecialAttack = false;
	    public boolean PlayersEnemy = false;
	    public double lastSpecial = -1;
	    public double currentSpecial = -1;
	    public double specialDamage = -1;
	    public String ActionString = "";
	    int wildernessLevel = 0;
	    int tick = 0;
	    boolean WildernessDitch1;
	    public boolean running = true;
	    int WildernessDitch[]={65093,65095,65094,65081,65083,65078,65088,65084,65092,65079,65082,65076,65085,65048};
	    
	  //items
	    int[] FOOD = {1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980,
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
	            1883, 1885,1982};
	    int[] Lootid={11732,4087,1149,5698,1187,1188,1215,1216,1231,1232,1249,1250,1263,1264,1305,1306,1377,1378,143, 141, 139, 2434, 15331, 15330, 15329, 15328, 23253, 23251, 23249, 23247, 23245, 23243, 23530, 23529, 23528, 23527, 23526, 23525,1435,1540,1541,3140,3141,3176,3204,3205,1123,1073,1331,4585,4586,4587,4588,5680,5681,5716,5717,5730,1163,1127,1303,1079,1333,1199,6739,6740,7158,7407,11227,11228,11229,11230,78,867,882,884,886,888,890,886,11212,11283,11284,11335,11336,11732,11733,13481,13490,1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980,
   7223, 6297, 6293, 6295, 6299, 7521, 9988, 7228, 2878, 7568, 2343,1861, 13433, 315, 325, 319, 3144, 347, 355, 333, 339, 351, 329, 3381, 361, 10136, 5003, 379, 365, 373, 7946, 385, 397, 391, 3369,
   3371, 3373, 2309, 2325, 2333, 2327, 2331, 2323, 2335, 7178, 7180,7188, 7190, 7198, 7200, 7208, 7210, 7218, 7220, 2003, 2011, 2289,2291, 2293, 2295, 2297, 2299, 2301, 2303, 1891, 1893, 1895, 1897,1899, 1901, 7072, 7062, 7078, 7064, 7084, 7082, 7066, 7068, 1942,3122,3134,13788,6568,6569,10636,13443,6701, 6703, 7054, 6705, 7056, 7060, 2130, 1985, 1993, 1989, 1978,3749,3750,12672,12673,13407,
   5763, 5765, 1913, 5747, 1905, 5739, 1909, 5743, 1907, 1911, 5745,10828,10843,12680,12681,2955, 5749, 5751, 5753, 5755, 5757, 5759, 5761, 2084, 2034, 2048,6524,6536, 1712, 1710, 1708, 1706,2036, 2217, 2213, 2205, 2209, 2054, 2040, 2080, 2277, 2225, 2255,3105,3106,2436, 145, 147, 149,
   2221, 2253, 2219, 2281, 2227, 2223, 2191, 2233, 2092, 2032, 2074,526, 532, 530, 528, 3183, 2859, 534, 3125, 4834, 14793, 4812, 3123, 4832, 6729, 536,
   2030, 2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971, 4608,2452, 2454, 2456, 2458,1883, 1885,1982, 3751,3752,12674,12675,13408, 2550,2551, 2570,2571,12029,99, 12790, 12825, 1119,96, 12093, 12435, 10818,95, 12822, 12828, 1115,93, 12796, 12827, 12161,92, 12089, 12437, 2859, 3226,89, 12786, 12833, 1444,12083, 12466, 2363,88, 12039, 12434, 237,85, 12776, 12832, 10149,83, 12017, 12456, 6155,
                12788, 12837, 12168,80, 12025, 12442, 571,79, 12802, 12824, 1442,12804, 12824, 1440,78, 12013, 12457, 5933,76, 12081, 12465, 2361,12782, 12841, 10020,
                74, 12069, 12449, 6979,73, 12792, 12826, 12168,71, 12057, 12451, 10117,70, 12820, 12830, 1933,69, 12033, 12423, 1963,67, 12031, 12439, 7939,66, 12079, 12464, 2359,12123, 12452, 2150,63, 12015, 12436, 6287,62, 12037, 12427, 12161,61, 12085, 12468, 9736,57, 12784, 12840, 10095,12810, 12835, 10099,12812, 12836, 10103,56, 12531, 12424, 311,52, 12007, 12441, 9978,49, 12061, 12444, 2132,34, 12818, 12443, 12164,10, 12059, 12428, 6291};
	    private int CurrentJunk = 0;
        private int estimatedProfits = 0;
        private int WildyWall = 65094;  // Check on this - dual int, unsure.    
        int Location = 0;
        int NearJunkPrice;
        int FarJunkPrice;
        public int loopTask = -1;
        int DeathAnimation[]={836};
        int healAt = 390;  ///Healing
        public static int WILDERNESS_Y = 3522;
        public static int EDGEVILLE_Y = 3520;
        public final Tile[] BankToWall     = { new Tile(3096, 3497, 0), new Tile(3100, 3501, 0), new Tile(3092, 3503, 0), new Tile(3087, 3503, 0), new Tile(3087, 3514, 0), new Tile(3083, 3520, 0),};
public final Tile[] WildyRetreat   = { new Tile(3097, 3527, 0), new Tile(3097, 3540, 0), new Tile(3092, 3552, 0), new Tile(3086, 3543, 0), new Tile(3075, 3535, 0), new Tile(3065, 3544, 0),new Tile(3054, 3544, 0), new Tile(3049, 3541, 0), new Tile(3046, 3534, 0), new Tile(3059, 3530, 0), new Tile(3064, 3536, 0), new Tile(3074, 3526, 0),new Tile(3083, 3523, 0),};
public final Tile[] VLodeStoneToGE = { new Tile(3211, 3377, 0), new Tile(3211, 3388, 0), new Tile(3211, 3399, 0), new Tile(3210, 3413, 0), new Tile(3208, 3425, 0), new Tile(3199, 3429, 0),new Tile(3199, 3429, 0), new Tile(3185, 3430, 0), new Tile(3186, 3443, 0), new Tile(3176, 3448, 0), new Tile(3168, 3457, 0), new Tile(3164, 3469, 0)};
public final Tile[] WildySearch    = { new Tile(3084, 3527, 0), new Tile(3098, 3531, 0), new Tile(3100, 3537, 0), new Tile(3095, 3544, 0), new Tile(3083, 3541, 0),};
Area areaWildyLevel = new Area(new Tile(3100, 3564, 0), new Tile(3043, 3521, 0));
Area areaEdgevilleBank = new Area(new Tile(3098, 3499, 0), new Tile (3094, 3493, 0));
Area areaGE = new Area(new Tile(3189, 3515, 0), new Tile(3138, 3469, 0));
Area areaRimmingtonMine = new Area(new Tile(2984, 3246, 0), new Tile(2963, 3225, 0));
//Walking
Tile[] ToEdgevilleWildDitch = new Tile[] {new Tile(3067, 3503, 0), new Tile(3073, 3506, 0),new Tile(3073, 3512, 0), new Tile(3076, 3518, 0),new Tile(3083, 3520, 0) };;
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
//NPCs
int[] GreenDragons={941,4677,4678,4679,4680,10604,10605,10606,10607,10608,10609};
Character Enemy;
String EnemyName = "", EnemyNameOld;
int cbtlvl = 0;
	    
	    @SuppressWarnings("unused")
		private GroundItem getBestGroundItem() {
	         Filter<GroundItem> junkFilter = new Filter<GroundItem>() {
	 
	                 public boolean accept(GroundItem t) {
	                return getPrice(t.getId()) >= 500;//change the val
	                }

					private int getPrice(int id) {
						// TODO Auto-generated method stub
						return 0;
					}
	                };
	                        int highestPrice = 0;
	                GroundItem bestItem = null;
	              for (GroundItem gi : GroundItems.getLoaded(junkFilter)) {
	              if (!gi.validate()) {
	                continue;
	            }
	                int currPrice = getPrice(gi.getId());
	                  if (currPrice > highestPrice) {
	                highestPrice = currPrice;
	                bestItem = gi;
	                }
	            }
	                return bestItem;
	            }
	       
	       
	 
	        private int getPrice(int id) {
			// TODO Auto-generated method stub
			return 0;
		}



			public Tile getBest(Tile[] array) {
	 
	                for(int i = array.length - 1; i >= 0; i++) {
	                        if(array[i].isOnMap()) {
	                                return array[i];
	                        }
	                }
	                return null;
	        }
	       
	       
	        Filter<GroundItem> itemFilter = new Filter<GroundItem>() {
	        public boolean accept(GroundItem groundItem) {
	                return getPrice(groundItem.getId()) > 250;
	        }

			private int getPrice(int id) {
				// TODO Auto-generated method stub
				return 0;
			}
	};
	protected void setup() {
        log.info("starting....");
        if (!onstart()) {
                running = false;
                return;
        }
        provide(new Strategy(
                        new Condition() {
                                public boolean validate() {
                                        return true;
                                }
                        },
                        new Task() {
                                public void run() {
                                        int ret = -1;
                                        try {
                                                ret = loop();
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                        if (ret == -1) {
                                                running = false;
                                                return;
                                        }
                                        Time.sleep(ret);
                                }

								private int loop() {
									if (Game.isLoggedIn()) {
							        	HopBorder();
							        	if(CanAttack()) {
							        		UseSpecial();
							        	}
							            final Player botter = Players.getLocal();
							            players = Players.getLoaded(new Filter<Player>() {

							                public boolean accept(Player p) {
							                    return (p != null)
							                            && (Calculations.distanceTo(p.getLocation()) < 19.0D)
							                            && (botter.getLevel() - getWildernessLevel() <= p.getLevel())
							                            && (botter.getLevel() + getWildernessLevel() >= p.getLevel())
							                            && (!p.equals(botter));

							                }
							            });
							        }
							        tick++;
							        Time.sleep(50);
									return 0;
							    } {
							
								}
                        }
        ));
}	  

	    private boolean onstart() {
		// TODO Auto-generated method stub
		return false;
	}



		private void HopBorder() {
	    	SceneObject wall = SceneEntities.getNearest(WildyWall);
            if(!areaWildyLevel.contains(Players.getLocal().getLocation()) && wall != null && !Bank.isOpen()) {
                    if(!Inventory.isFull() && Inventory.getCount(true, Lootid) < 120 && wall.isOnScreen() && !Players.getLocal().isInCombat()) {
                            log.info("Crossing Wall into the Wilderness...");
                            wall.interact("Cross");
                            Time.sleep(3500, 3900);
                            Widgets.get(382, 19).interact("Proceed");
                            Time.sleep(2000, 3000);
                    } else
                            if(!Inventory.isFull() && Inventory.getCount(true, Lootid) < 120 && !wall.isOnScreen())  {
                            	log.info("Walking to the Wilderness Wall...");
                                    Walking.newTilePath(BankToWall).traverse();
                                    Time.sleep(500, 800);
                            }
            }
    } 

		private void UseSpecial() {
			// TODO Auto-generated method stub
			
		}

		private boolean CanAttack() {
			ActionString="Attack";
			Players.getLocal().getLevel();
			if(Players.getLocal().isInCombat())
				log.info("O Shit we are in Combat");
			Eating();
			if(Players.getLocal().getInteracting() != null)
				Looting();
			if(Players.getLoaded() != null) Players.getLocal().interact(ActionString);
			PlayersEnemy = Players.getLoaded(new Filter<Player>() {
				public boolean accept(Player player) {
					return player != null && Players.getLocal().getInteracting() == null
	                         && player.getSkullIcon() != Players.getLocal().getId()
	                         && player.getHpPercent() > 1;
				}
            }) != null;
            return false;
		}

		private void Looting() {
			GroundItem NearJunk = GroundItems.getNearest(GroundItems.ALL_FILTER);
            CurrentJunk = NearJunk.getId();
            if(!Inventory.isFull()) {
            if(NearJunk.isOnScreen() && areaGE.contains(NearJunk) && areaGE.contains(Players.getLocal().getLocation()) && Calculations.distanceTo(NearJunk) <= 8) {
                    if(Players.getLocal().getAnimation() == -1) {
                            NearJunk.interact("Take");
                            Time.sleep(200, 300);
            }
                   
    //              Status = "Grabbing Junk...";
    //              NearJunk.interact("Take");
                    estimatedProfits = estimatedProfits + getPrice(CurrentJunk);  
                    Time.sleep(2500, 4000);
            } else
                    if(!NearJunk.isOnScreen() && NearJunk != null && areaGE.contains(NearJunk) && areaGE.contains(Players.getLocal().getLocation()) && Calculations.distanceTo(NearJunk) < 8) {
                            log.info("Walking to nearby Junk...");
                            Walking.findPath(NearJunk.getLocation()).traverse();
                            Time.sleep(2000, 5000);
                    }
            for(GroundItem FarJunk : GroundItems.getLoaded())
                    if(!NearJunk.isOnScreen() && areaGE.contains(Players.getLocal().getLocation()) && Calculations.distanceTo(NearJunk) >= 8 && FarJunk != null && areaGE.contains(FarJunk)) {
                    	log.info("Walking to nearby Junk...");
                            Walking.findPath(FarJunk.getLocation()).traverse();
                            Time.sleep(3000, 5000);
                    }
                    }
                    }

		private void Eating() {
			 for (final Item item : Inventory.getItems()) {
                 for (final int ID : FOOD) {
                              if (item.getId() == ID) {
                                       item.getWidgetChild().interact("Eat");
                                       return;
                              }
                 }
        }
}
		
		@SuppressWarnings("unused")
		private int getHPPercent() {
			return (int) (((double) Integer.parseInt(Widgets.get(748).getChild(8)
			.getText()) / (double) (Skills.getLevel(Skills.CONSTITUTION) * 10)) * 100);
			}

@SuppressWarnings("unused")
private Item getFood() {
String[] actions;
for (Item i : Inventory.getAllItems(true)) {
if (i == null || !i.getWidgetChild().validate())
continue;
actions = i.getWidgetChild().getActions();
for (String s : actions) {
if (s != null) {
if (s.equalsIgnoreCase("eat")) {
return i;
}
}
}
}
return null;
}

@SuppressWarnings("unused")
private void WalkToGreenDragons() {
           Walking.setRun(true);
        Camera.setAngle(Random.nextInt(220, 235));
            Walking.newTilePath(ToGreenDragons).traverse();
       
}
 
        @SuppressWarnings("unused")
        private void WalkingtoWildDitch() {
                   Walking.setRun(true);
            Camera.setAngle(Random.nextInt(220, 235));
                        Walking.newTilePath(ToEdgevilleWildDitch).traverse();
                        Time.sleep(500, 1000);
       
}
         
@SuppressWarnings("unused")
private boolean AtGreenDragons() {
        if((NPCs.getLoaded(GreenDragons) != null));
        return false;
   }

		public void onRepaint(Graphics grphcs) {
	        if (Game.isLoggedIn()) {
	            Graphics2D g = (Graphics2D) grphcs;
	            g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
	                    RenderingHints.VALUE_ANTIALIAS_ON));

	            Player botter = Players.getLocal();
	            if ((players != null)
	                    && (inWilderness())) {
	                for (Player p : players) {
	                    if ((p != null) && (!p.equals(botter))) {
	                        if (p.getLocation().isOnScreen()) {
	                            drawTile(g, p.getLocation(), colorForLevel(p));
	                        }
	                        if (p.isOnScreen()) {
	                            drawPlayer(g, p, colorForLevel(p));
	                        }
	                        if (p.isOnScreen()) {
	                            drawPlayerInformation(g, p, colorForLevel(p));
	                        }
	                    }
	                }
	            }
	        }
	    }

	    public void drawTile(Graphics2D g, Tile tile, Color col) {
	        for (Polygon poly : tile.getBounds()) {
	            boolean drawThisOne = true;
	            for (int i = 0; i < poly.npoints; i++) {
	                Point p = new Point(poly.xpoints[i], poly.ypoints[i]);
	                if (!Calculations.isOnScreen(p)) {
	                    drawThisOne = false;
	                }
	            }
	            if (drawThisOne) {
	                Color col2 = new Color(col.getRed(), col.getGreen(), col.getBlue(), 80);
	                g.setColor(col2);
	                g.fillPolygon(poly);
	                g.setColor(col);
	                g.drawPolygon(poly);
	            }
	        }
	    }

	    public void drawPlayer(Graphics2D g, Player player, Color col) {
	        if (player.isOnScreen()) {
	            Rectangle rect = getBoundingBox(player.getBounds());
	            if ((Calculations.isOnScreen(new Point(rect.x, rect.y))) && (Calculations.isOnScreen(new Point(rect.x + rect.width, rect.y + rect.height)))) {
	                g.setColor(col);
	            }
	            g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
	        }
	    }

	    public void drawPlayerInformation(Graphics2D g, Player player, Color col) {
	        Rectangle boundingBox = getBoundingBox(player.getBounds());
	        String playerName = player.getName();
	        int level = player.getLevel();
	        int centerBoundPointX = (int) (boundingBox.getX() + boundingBox.getWidth() / 2);
	        double fullWidth = g.getFont().getStringBounds(playerName + "(" + level + ")",
	                g.getFontRenderContext()).getWidth(); //Left space out to look neater in-game.
	        double nameWidth = g.getFont().getStringBounds(playerName + " (",
	                g.getFontRenderContext()).getWidth();
	        double levelWidth = g.getFont().getStringBounds(level + "",
	                g.getFontRenderContext()).getWidth();
	        int stringStartX = centerBoundPointX - (int) (fullWidth / 2);
	        g.setColor(Color.WHITE);
	        g.drawString(playerName + " (", stringStartX,
	                (int) boundingBox.getY());
	        g.setColor(col);
	        g.drawString("" + level, stringStartX + (int) nameWidth, (int) boundingBox.getY());
	        g.setColor(Color.WHITE);
	        g.drawString(")", stringStartX + (int) nameWidth + (int) levelWidth, (int) boundingBox.getY());
	    }

	    public Color colorForLevel(Player p) {
	        Player botter = Players.getLocal();
	        float delta = getScale(p.getLevel() - botter.getLevel(), getWildernessLevel());

	        return Color.getHSBColor((60.0F - 60.0F * delta) / 360.0F, 1F, 1F);
	    }

	    public Rectangle getBoundingBox(Polygon[] polys) {
	        double minX = Double.MAX_VALUE;
	        double minY = Double.MAX_VALUE;
	        double maxX = Double.MIN_VALUE;
	        double maxY = Double.MIN_VALUE;
	        for (Polygon poly : polys) {
	            Rectangle rect = poly.getBounds();
	            if (minX > rect.getX()) {
	                minX = rect.getX();
	            }
	            if (minY > rect.getY()) {
	                minY = rect.getY();
	            }
	            if (maxX < rect.getX()) {
	                maxX = rect.getX();
	            }
	            if (maxY < rect.getY()) {
	                maxY = rect.getY();
	            }
	        }
	        return new Rectangle((int) minX - 5, (int) minY - 5, (int) (maxX - minX) + 5, (int) (maxY - minY) + 5);
	    }

	    public boolean inWilderness() {
	        return Widgets.get(381, 2).visible() ? true : false;
	    }

	    public int getWildernessLevel() {
	        return inWilderness() ? Integer.parseInt(
	                Widgets.get(381, 2).getText().replaceAll("Level: ", "")) : 0;
	    }

	    public float getScale(int difference, int max) {
	        if (difference == 0) {
	            return 0F;
	        }
	        float val = (float) Math.max(difference, -difference) / (float) max;
	        return difference > 0 ? val : -val;
	    }



		public void run() {
			// TODO Auto-generated method stub
			
		}



		public boolean validate() {
			// TODO Auto-generated method stub
			return false;
		}
}
