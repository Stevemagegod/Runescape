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
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

@Manifest(authors = ("Graser"), name = "XBoner", description = "Buries Bones", version = 1)
public class Boner extends org.powerbot.core.script.ActiveScript implements PaintListener, MessageListener {
  
	/**
	 * @param Author Xianb aka Graser
	 */
	
	public long startTime = System.currentTimeMillis();
	public String status;
	public long millis;
	int BonesBuried;
	int Bone = 526; //Change this id to what ever you want to Bury. 
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),MOUSE_BORDER_COLOR = new Color(220, 220, 220),MOUSE_CENTER_COLOR = new Color(89, 255, 89);

	@Override
	public void messageReceived(MessageEvent e) {
		String Lastmessage = e.getMessage();
        if (Lastmessage.contains("You bury the bones.")) //Returns true if and only if this string contains the specified sequence of char values. 
        {
                BonesBuried++;
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
			g.drawString("XBoner by Graser", 29, 357);
			g.drawString("Bones Burried: "+BonesBuried, 44, 384);
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
	
	public void onStart() {
		if (Game.isLoggedIn()) // Checks if are Player is logged in
		{
			status = "Hello World"; //Says Hello World if we are logged in
			//TODO Make GUI 
		}
		if (!Game.isLoggedIn()) //If we are not logged in
		{
			status="NOT logged in"; //Status update to
			stop(); //Kill the Script
		}
	}

	public void onStop() {
		
		        status="Thank You for using my Script";
		    }
	
	/**
	 * @param Simple nested if Statement in loop
	 */

	@Override
	public int loop() {
		if(!Inventory.isFull()) //Checks if the inventory is not full.
			Bank(); //If Inventory is not full it will open the Bank.
			if(Inventory.contains(Bone)) //Checks if are Inventory contains Bones and if it does contain Bones.
				Bury(); //It will Bury the bones using a simple for statement. 
		return 150;
	}

	private void Bury() {
		for (Item i : Inventory.getItems()) //The for statement using 3 Variables Item, I, and Inventory.getitems()
		{
			if (i.getId() == Bone) //if i equals Bone
			{
				status = "Burying Bones"; 
				i.getWidgetChild().interact("Bury"); //we will bury the bone
				sleep(Random.nextInt(500, 1000)); //then sleep. 
			}
		}
	}
		
	private void Bank() {
		status = "Opening Bank";
		Bank.open(); //Opens Bank
		status = "Checking if Bank is Open";
		if(Bank.isOpen()) //if Bank is Open
			status = "Searching for Bones......";
			Bank.search("Bone"); //we will search for the Bones
			status = "Withdrawing";
		Bank.withdraw(Bone, 28); //Once found we will withdraw 28 Bones.
		if(Inventory.getCount(Bone)<=28) //If are Inventory contains the Bones and is less than or equal to 28 items
			status = "Closing Bank";
			Bank.close(); //we will close the bank.
		if(Bank.getItemCount(Bone)==0) //This is to prevent Spam Clicking if are Bank no longer has any Bones
			status = "Bye Bye";
			stop(); //Kills the Script. 
	}
}
