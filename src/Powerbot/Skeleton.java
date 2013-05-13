package grasersTasks;
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
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;

@Manifest(authors = ("Graser"), name = " ", description = " ", version = 1)
public class Main extends org.powerbot.core.script.ActiveScript implements PaintListener, MessageListener {
	public long startTime = System.currentTimeMillis();
	public String status;
	public long millis;
	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(0, 204, 255);
	private final Color color3 = new Color(0, 255, 0);
	private final Font font1 = new Font("Cambria", 1, 20);
	private final Font font2 = new Font("Cambria", 1, 17);
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),MOUSE_BORDER_COLOR = new Color(220, 220, 220),MOUSE_CENTER_COLOR = new Color(89, 255, 89);

	@Override
	public void messageReceived(MessageEvent e) {
		//TODO
	}

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
			g.drawString("Skeleton by Graser", 29, 357);
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

	public void onStart(){
		if (Game.isLoggedIn()) {
			status = "Hello "+Environment.getDisplayName();
		}
	}

	public void onStop(){
		        status="Thank You for using my Script";
		    }

	@Override
	public int loop() {
		// TODO Auto-generated method stub
		return 0;
	}
}
