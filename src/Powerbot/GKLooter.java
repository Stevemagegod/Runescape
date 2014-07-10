import java.awt.Graphics;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Random;
import org.powerbot.script.Script.Manifest;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GroundItem;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.Path;

/**
 * The algorithim of my script is very simple: We fish if there is no loot on
 * the ground.//TODO If there is loot on the ground then we take the fish loot. If are
 * inventory has items that aren't notes we will chop down a tree to make into a
 * fire. //TODO Then we will cook are food using the fire we just made. //TODO If we
 * burnt a fish then we will drop it. //TODO We will then repeat this loop until are
 * inventory is full and then go and exchange are stuff.
 * 
 * Current Algorithim is Loot till inventory is full then walk to Stiles Exchange then Walk Back. Repeat Process. 
 */

@Manifest(description = "Fishes, Cookes, and Firemakes", name = "GKLooter")
public class GFisher extends PollingScript<ClientContext> implements
PaintListener {
    public long startTime = System.currentTimeMillis();
    String status = "";
    public final static int stiles = 11267;
    public final static int lootid[] = { 377, 371, 359 };
    public final static int fishingSpot = 323;
    public int[] Monsters = {107,708,411,128, 132};

    @Override
    public void poll() {
	if (!(ctx.backpack.select().size() == 28)) {
	    loot();
	}
	else
	    if (ctx.backpack.select().size() == 28) {
		Exchange();
		if (ctx.backpack.select().size()<28) {
		    walkToDock().traverse();
		}
	    }
    }

    private void Exchange() {
	if (ctx.npcs.id(stiles).nearest() != null) {
	    status = "Walking to Stiles";
	    walkToStiles().traverse();
	    try {
		Thread.sleep(1500);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	Npc Man = ctx.npcs.select().id(stiles).peek();
	if(Man.valid()) {
	    Man.interact(false, "Exchange");
	    ctx.input.move(Random.nextInt(-20, 700), Random.nextInt(-20, 700));
	    status = "Exchanging";
	}
    }

    public Tile[] pathToDock() {
	Tile[] path = { new Tile(2852, 3143, 0), new Tile(2857, 3146, 0),
		new Tile(2864, 3150, 0), new Tile(2874, 3161, 0),
		new Tile(2891, 3169, 0), new Tile(2888, 3168, 0),
		new Tile(2898, 3171, 0), new Tile(2908, 3173, 0),
		new Tile(2918, 3172, 0), new Tile(2924, 3175, 0),
		new Tile(2923, 3175, 0) };
	return path;
    }

    public Path walkToDock() {
	return ctx.movement.newTilePath(pathToDock()).reverse();
    }

    public Path walkToStiles() {
	return ctx.movement.newTilePath(pathToDock()).reverse();
    }

    private void loot() {
	GroundItem Loot = ctx.groundItems.id(lootid).peek(); {
	    if(ctx.groundItems.nearest().id(lootid) != null) {
		if(ctx.groundItems.nearest().id(lootid).peek().inViewport()) 
		    status = "Looting "+Loot.name();
		Loot.interact("Take");
		try {
		    Thread.sleep(1200);
		} catch (InterruptedException e) {
		    Random.nextInt(2000, 1000);
		    e.printStackTrace();
		}
	    }
	    else
		if(ctx.groundItems.count() > 5) {
		    Loot.click(true);
		}
	}
    }

    @Override
    public void repaint(Graphics g) {
	long millis = System.currentTimeMillis() - startTime;
	long hours = millis / (1000 * 60 * 60);
	millis -= hours * (1000 * 60 * 60);
	long minutes = millis / (1000 * 60);
	millis -= minutes * (1000 * 60);
	long seconds = millis / 1000;
	g.drawString("GKLooter by: Xianb", 36, 412);
	g.drawString("Version: 1", 44, 384);
	g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds,
		34, 367);
	g.drawString("Status: " + status, 35, 444);
    }

}
