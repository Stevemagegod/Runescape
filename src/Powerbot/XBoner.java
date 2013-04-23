import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.core.script.ActiveScript;

@Manifest(authors = ("Graser"), name = "XBoner", description = "Buries Bones", version = 1)
public class Boner extends ActiveScript implements PaintListener, MessageListener {

	/**
	 * @param Author Xianb 
	 */

	int BONES=526;		
	int BAT_BONES=530;
	int SMALL_MONKEY_BONES=3179;
	int MEDIUM_MONKEY_BONES=3180;
	int LARGE_MONKEY_BONES=3181;
	int SMELLY_MONKEY_BONES=3185;
	int SHAKE_QUEST_BONES=3187;
	int BURNT_BONES=528;
	int WOLF_BONES=2859;
	int BIG_BONES=532;
	int JOGRE_BONES=3125;
	int BURNT_JOGRE_BONES=3127;
	int BURNT_PASTRY_JOGRE_BONES=3128;
	int BURNT_MARINATED_JOGRE_BONES=3130;
	int COOKED_MARINATED_JOGRE_BONES=3132;
	int RAW_MARINATED_JOGRE_BONES=3131;
	int ZOGRE_BONES=4812;
	int BABY_DRAGON_BONES=534;
	int SHAIKAHAN_BONES=3123;
	int WYVERN_BONES=6812;             
	int DRAGON_BONES=536;
	int FAYRG_BONES=4830;
	int RAURG_BONES=4832;
	int OURG_BONES=4834;
	int DAGANNOTH_BONES=6729;
	int FROST_BONES=18830;
	int ANCIENT_BONES=15410;
	int bonesBuried;
	public int whatToDo = 0;
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),MOUSE_BORDER_COLOR = new Color(220, 220, 220),MOUSE_CENTER_COLOR = new Color(89, 255, 89);
	public long millis;
	public long startTime = System.currentTimeMillis();
	public String status;
	public boneBuryGui g = new boneBuryGui();
	private int boneToUse;
	public boolean guiWait = true;
	int startExp;
	public boolean quit = false;

	@Override
	public void messageReceived(MessageEvent e) {
		String Lastmessage = e.getMessage();
		if (Lastmessage.contains("You bury the bones.")) //Returns true if and only if this string contains the specified sequence of char values. 
		{
			bonesBuried++;
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
		g.drawString("Bones Burried: "+bonesBuried, 44, 384);
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
		System.out.println("Sarting...");      
		startTime = System.currentTimeMillis();
		startExp = Skills.getExperience(Skills.PRAYER);
		g.setVisible(true);
		while(guiWait == true) {
			sleep(500);
		}
		status="Bone ID chosen: " + boneToUse;
		return;
	}

	public void onStop() {
		status="Thank You for using my Script";
	}

	@Override
	public int loop() {
		if(quit == true)
			return -1;
		if(whatToDo == 1) {
			bury();
		}
		if(!Inventory.isFull()) //Checks if the inventory is not full.
			bank(); //If Inventory is not full it will open the Bank.
		else
			if(Inventory.contains(boneToUse)) //Checks if are Inventory contains Bones and if it does contain Bones.
				bury(); //It will Bury the bones using a simple for statement. 
		return 150;
	}

	private void bury() {
			for (Item i : Inventory.getItems()) //The for statement using 3 Variables Item, I, and Inventory.getitems()
			{
				if (i.getId() == boneToUse) //if i equals Bone
				{
					status = "Burying Bones"; 
					i.getWidgetChild().interact("Bury"); //we will bury the bone
					sleep(Random.nextInt(500, 1000)); //then sleep. 
				}
			}
	}

	private void bank() {
		status = "Opening Bank";
		Bank.open(); //Opens Bank
		status = "Checking if Bank is Open";
		if(Bank.isOpen()) //if Bank is Open
			status = "Searching for Bones......";
		Bank.search(" "); //we will search for the Bones
		status = "Withdrawing";
		Bank.withdraw(boneToUse, 28); //Once found we will withdraw 28 Bones.
		sleep(800,1200);
		if(Inventory.getCount(boneToUse)<=28) //If are Inventory contains the Bones and is less than or equal to 28 items
			status = "Closing Bank";
		Bank.close(); //we will close the bank.  
	}

	public class boneBuryGui extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;

		public boneBuryGui() {
			initComponents();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void initComponents() {
			setTitle("Boner");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 246, 198);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			JLabel lblPboneburier = new JLabel("Boner");
			lblPboneburier.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
			lblPboneburier.setBounds(62, 11, 112, 37);
			contentPane.add(lblPboneburier);

			JLabel lblWhatBoneTo = new JLabel("What bone to bury?:");
			lblWhatBoneTo.setBounds(57, 59, 117, 14);
			contentPane.add(lblWhatBoneTo);
			final JComboBox comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {
					"Normal Bones", "Ancient Bones", "Dragon Bones",
					"Bat Bones", "Baby Dragon Bones", "Big Bones",
					"Fayrg Bones", "Curved Bones", "Dagannoth Bones",
					"Jogre Bones", "Long Bones", "Frost Dragon Bones",
					"Burnt Bones", "Ourg Bones", "Wolf Bones", "Zogre Bones",
					"Wyvern bones", "Shaikahan bones", "Raurg bones" }));
			comboBox.setBounds(44, 82, 125, 20);
			contentPane.add(comboBox);
			JButton btnStart = new JButton("Start");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					whatToDo = 1;
					if (comboBox.getSelectedItem().toString()
							.equals("Normal Bones")) {
						boneToUse = 526;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Ancient Bones")) {
						boneToUse = 15410;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Bat Bones")) {
						boneToUse = 530;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Baby Dragon Bones")) {
						boneToUse = 7839;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Big Bones")) {
						boneToUse = 532;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Fayrg Bones")) {
						boneToUse = 4830;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Curved Bones")) {
						boneToUse = 10977;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Dagannoth Bones")) {
						boneToUse = 6729;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Jogre Bones")) {
						boneToUse = 3125;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Long Bones")) {
						boneToUse = 10976;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Frost Dragon Bones")) {
						boneToUse = 18832;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Burnt Bones")) {
						boneToUse = 528;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Ourg Bones")) {
						boneToUse = 14793;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Wolf Bones")) {
						boneToUse = 2859;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Zogre Bones")) {
						boneToUse = 4812;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Wyvern Bones")) {
						boneToUse = 6812;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Shaikahan Bones")) {
						boneToUse = 3123;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Raurg Bones")) {
						boneToUse = 4832;
					} else if (comboBox.getSelectedItem().toString()
							.equals("Dragon Bones")) {
						boneToUse = 536;
					}
					g.dispose();
					guiWait = false;
				}

			});

			btnStart.setBounds(10, 126, 210, 23);
			contentPane.add(btnStart);
		}
	}
}
