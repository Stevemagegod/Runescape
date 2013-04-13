import java.awt.Graphics;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.wrappers.Tile;

/**
 * @author Xianb
 */
@Manifest(authors = ("Graser"), name = "Lumbridge Easy Task", description = "AIO Achievement Diary Script", version = 0.1)
public class LumbridgeEasyTask extends ActiveScript implements PaintListener,MessageListener {

	/**
	 * @param args Don't Bitch at me about Conventions
	 * I Realize i have empty ids.
	 */

	//items
	int RuneAxe;
	int Coins;
	int CoalOre;
	int Essence;
	int Clay;
	int Net;
	int Bucket;
	int Tinderbox;
	int IronOreRock[]={37309,37307};
	int FishingRod;
	int FishingBait;
	int Feathers;
	int Hammer;
	int CookedLobster;
	int Cowhide;
	int Needle;
	int Thread;
	int Gloves;
	int RawPike;
	int RawRatMeat=2134;
	int Logs;
	int WaterTalisman;

	//People
	int Ellis;
	int Dommik;
	int FatherUrhney;
	int WiseOldMan;
	int Sedridors;

	//Objects
	int TrapDoor;
	int LumbridgeCowsGate;
	int WiseOldManDoor;
	int WiseOldManStairCase;
	int Telescope;
	int Railing;
	int WizardsTowerDoors;

	//Monsters
	int Zombie;
	int[] Rat={8829,8828};

	//Walking
	Tile[] AndItWasTHISBig = new Tile[] { new Tile(3234, 3223, 0),
			new Tile(3239, 3225, 0), new Tile(3245, 3225, 0),
			new Tile(3251, 3225, 0), new Tile(3249, 3230, 0),
			new Tile(3247, 3235, 0), new Tile(3243, 3239, 0),
			new Tile(3240, 3241, 0) }; //Walks to the Herraring Fishing Spot from the Lodestone
	Tile[] IronOn = new Tile[] { new Tile(3240, 3241, 0),
			new Tile(3245, 3240, 0), new Tile(3251, 3240, 0),
			new Tile(3256, 3238, 0), new Tile(3260, 3234, 0),
			new Tile(3265, 3232, 0), new Tile(3270, 3229, 0),
			new Tile(3276, 3229, 0), new Tile(3277, 3229, 0),
			new Tile(3299, 3286, 0) }; //Walks to Al Kahid Minning Spot from the fishing spot
	Tile[] BelterOfaSmelter = new Tile[] { new Tile(3299, 3286, 0),
			new Tile(3296, 3281, 0), new Tile(3290, 3280, 0),
			new Tile(3285, 3277, 0), new Tile(3286, 3272, 0),
			new Tile(3285, 3267, 0), new Tile(3282, 3262, 0),
			new Tile(3279, 3257, 0), new Tile(3279, 3251, 0),
			new Tile(3284, 3250, 0), new Tile(3290, 3250, 0),
			new Tile(3291, 3245, 0), new Tile(3290, 3239, 0),
			new Tile(3286, 3235, 0), new Tile(3281, 3231, 0),
			new Tile(3276, 3229, 0), new Tile(3270, 3229, 0),
			new Tile(3264, 3229, 0), new Tile(3260, 3233, 0),
			new Tile(3258, 3234, 0), new Tile(3253, 3237, 0),
			new Tile(3248, 3240, 0), new Tile(3243, 3243, 0),
			new Tile(3242, 3248, 0), new Tile(3244, 3253, 0),
			new Tile(3244, 3259, 0), new Tile(3239, 3261, 0),
			new Tile(3233, 3261, 0), new Tile(3230, 3256, 0),
			new Tile(3229, 3251, 0), new Tile(3224, 3254, 0) };
	Tile[] NowToolLookAt = new Tile[] { new Tile(3224, 3254, 0),
			new Tile(3223, 3249, 0), new Tile(3223, 3243, 0),
			new Tile(3220, 3238, 0), new Tile(3225, 3235, 0),
			new Tile(3229, 3231, 0), new Tile(3231, 3226, 0),
			new Tile(3232, 3221, 0), new Tile(3227, 3219, 0),
			new Tile(3222, 3221, 0), new Tile(3227, 3220, 0),
			new Tile(3232, 3218, 0), new Tile(3232, 3218, 0),
			new Tile(3232, 3212, 0), new Tile(3228, 3208, 0),
			new Tile(3224, 3203, 0), new Tile(3229, 3200, 0),
			new Tile(3235, 3200, 0), new Tile(3239, 3196, 0),
			new Tile(3234, 3193, 0), new Tile(3229, 3192, 0),
			new Tile(3224, 3191, 0), new Tile(3219, 3190, 0),
			new Tile(3216, 3185, 0), new Tile(3216, 3179, 0),
			new Tile(3214, 3174, 0), new Tile(3210, 3170, 0),
			new Tile(3205, 3172, 0), new Tile(3200, 3169, 0) }; //Travels to the Lumbridge Shed from the furnace
	Tile[] ICantHearDeadPeople = new Tile[] { new Tile(3187, 3166, 0),
			new Tile(3192, 3167, 0), new Tile(3196, 3163, 0),
			new Tile(3200, 3159, 0), new Tile(3205, 3155, 0) }; //Walks to Father Urhney from the Water Temple.

	//Other Variables
	int Tasks;
	String Lastmessage;
	String status;

	/**Lumbridge Easy Tasks Algorithim
	 * 
	 * Start at Lumbridge Lodestone.
	 * Walk to Lumbridge Cows.
	 * If Gate To Lumbridge Cows is Closed Open it.
	 * Kill Cow and Take Cowhide.
	 * 
	 * Walk to Al Kahrid
	 * Open Al Kahrid Gate
	 * Continue walking to Tanning Store
	 * If at Tanning Store Trade Ellis
	 * Tan 1 Soft Leather
	 * Walk North East and Buy a "Needle, and Thread" from Dommik's Crafting Store
	 * Use Needle on Thread to make Leather Gloves
	 * 
	 * Walk to Al Kahid Mine
	 * Mine Iron Rocks
	 * 
	 * Walk Back to Al Kahrid Gate
	 * Open Gate
	 * Walk to Goblin Fishing Spot
	 * Bait Fishing Spot
	 * 
	 * Walk to Lumbridge Furnace
	 * If At Lumbridge Furnace
	 * Use Iron Ore on Furnace
	 * 
	 * Walk to Zanaris Shed
	 * If at Zanaris Location
	 * Open Shed
	 * If we Got Teleported to other Dimension Log out.
	 * If we didn't Open Door and Kill a Rat
	 * Take Raw Rat Meet
	 * Find Dead Tree 
	 * If found Chop it down
	 * 
	 * Use Tinderbox on Logs
	 * Use Raw Rat Meat on Fire
	 * Walk to Water Alter
	 * If At Water Alter Enter it
	 * Use Essence on Alter to Craft Water Rune
	 * 
	 * Walk to Priest House
	 * If at House, open door
	 * Talk to Father Urhney
	 * Choose Option 3
	 * 
	 * Lumbridge Home Teleport Dynar
	 * Walk to DJail
	 * If at Jail open TrapDoor
	 * Climb-Down Trapdoor
	 * If Task Compleat Climb Back up
	 * 
	 * Walk to Wise Old Man
	 * If Wise Old Man Door Closed Open it
	 * Talk To Wise Old Man
	 * Choose Option 2
	 * 
	 * Climb up Wise Old Man Stair Case;
	 * Observe Telescope
	 * WaitforCutawayScene
	 * 
	 * OpenBank
	 * if(Bankisopen());
	 * BankClose
	 * 
	 * Walk to Wizards Tower
	 * if at wizards tower open door if closed
	 * Climbupstaircase
	 * if at top floor
	 * open door if closed
	 * if Demon is on screen
	 * Tount Through Railing
	 * Climb Down Stair Case till Basement
	 * if at Basement
	 * OpenSedridorsDoor
	 * if Sedridor is on screen
	 * right click Teleport
	 * 
	 */

	@Override
	public void messageReceived(MessageEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRepaint(Graphics arg0) {
		// TODO Auto-generated method stub

	}

	public void onStart() {
		if(Game.isLoggedIn())
			status="Hello World";
		else
			if(!Game.isLoggedIn())
				status="Not Logged in";
		return;
	}

	@Override
	public int loop() {
		// TODO Auto-generated method stub
		return 150;
	}

	public void onStop() {
		status="Thank you for using my script";
	}

}
