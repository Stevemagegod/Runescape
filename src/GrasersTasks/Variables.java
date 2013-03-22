package GrasersTasks;


import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;

public class Variables {

	/**
	 * @param args
	 */
	
		//itemn ids
		int[] Shrimp;
		int[] Coins = {995};
		int[] Bones = {526, 532, 530, 528, 3183, 2859, 534, 3125, 4834, 14793, 4812, 3123, 4832, 6729, 536};
		int[] PURE_ESS = {7936};
		int[] RUNE_ESS = {1436};
		int[] PORTAL = {39831};
		int[] ESSENCE = {2491};
		int[] Hatchets = { 1349, 1351, 1353, 1355, 1357, 1359, 1361, 6739, 13661 };
		int[] Cows = { 12363, 12362, 12365 };
		int CrayfishCage;
		int Tinderbox;
		int[] pickaxe = {1265, 1267, 1269, 1273, 1271, 1275, 15259, 14069, 16912};
		int Hammer;
		int Needle;
		int Thread=1734;
		int AirStaff=1381;
		int EmptyPot;
		int ratmeat;
		int BucketsofWater;
		int[] IronWeapons={1420};
		int[] IronArmour={1115,1191,1067,1153};
		int SteelWeapon;
		int[] Cowhide = { 1739 };
		int SteelArmour;
		int Arrows[]={};
		int UncutRuby;
		int Shortbow;
		int Longbow;
		int crossbow;
		int BronzeBars; // 2 bronze bars
		int FishingRod;
		int Fishingbait=313;
		int Rawherring;
		int SapphireJewellery;
		int UncookedBerryPie;
		int[] Rockid = { 0 };
		final static int[] nestIDs = { 5070, 5071, 5072, 5073, 5074, 5075, 5076,7413, 11966 };
		static int[] strangerockIDs = { 15542, 15543 };
		
		//Magic Runes Ids
		int AirRunes=556;
		int ConfuseRune;
		
		//Rock Ore ids
		int Clay=434;
		int Coal=453;
		int Tin=438;
		int Iron=440;
		int Copper=436;
		int Gold;
		int Silver=442;

		//Other Variables
		boolean mustContinue;
		boolean TaskDone = false;
		int adrenaline;
		int LumbridgeCastle=0;
		int hpThreshold = 12;
		int cooldowns[] = { 0, 36, 73, 77, 81, 85, 89, 93, 97, 101, 105,109, 113 };
		int logID;
		int Tasks;
		public long startTime = System.currentTimeMillis();
		String Lastmessage;
		String status;
		String version = "1";

		//People
		int[] Aubury={5913};
		int[] Horacio={741};
		int[] LumbridgeSage={};
		int[] FatherAerecks={};
		int EstateAgent;
		int HaigHalen;
		int[] Elsie={2824,808};
		int[] StrayDog={5917};
		int[] Doomsayer={3777};

		//Objects
		int[] DoortoHoraciosRoom={36845,36844,33819};
		int[] Horaciostairs={36777,36771,33795,36772};
		int[] LumbridgeBankBoothStairs={36772};
		int[] Flag={37335};
		int[] CopperRock={};
		int Fence;
		int FatherAereckselectionofgravestones;
		int OrganintheLumbridgeChurch;
		int BellintheLumbridgeChurch;
		int AlKharidGate;

		//NPCs
		public NPC nearestMonster;
		int[] Man={7876,13606};
		int[] Goblin={13613};
		int[] Zombie;

		//Paths
		Tile[] ReadAllAboutIt  = new Tile[] { new Tile(3210, 3420, 0), new Tile(3213, 3423, 0), new Tile(3216, 3427, 0), new Tile(3218, 3430, 0), new Tile(3217, 3432, 0) };
		TilePath ReadAllAboutIt1; //Walks To Benny from Thessalia
		//private TilePath TO_AUBURY = new TilePath( new Tile [] {new Tile(3260, 3412, 0),new Tile(3251, 3401, 0)});
		//private TilePath TO_BANK = new TilePath( new Tile [] {new Tile(3260, 3412, 0),new Tile(3251, 3401, 0)}).reverse(); //Walks to the Bank from Aubury
	
	/**
	 * @param args
    Widget-320/Child-01 : Attack skill tab
    Widget-320/Child-02 : Constitution skill tab
    Widget-320/Child-03 : Mining skill tab
    Widget-320/Child-04 : Strength skill tab
    Widget-320/Child-10 : Agility skill tab
    Widget-320/Child-16 : Mining skill tab
    Widget-320/Child-17 : Defense skill tab
    Widget-320/Child-23 : Herblore skill tab
    Widget-320/Child-29 : Fishing skill tab
    Widget-320/Child-35 : Range skill tab
    Widget-320/Child-41 : Thieving skill tab
    Widget-320/Child-47 : Cooking skill tab
    Widget-320/Child-53 : Prayer skill tab
    Widget-320/Child-59 : Crafting skill tab
    Widget-320/Child-65 : Firemaking skill tab
    Widget-320/Child-71 : Magic skill tab
    Widget-320/Child-77 : Fletching skill tab
    Widget-320/Child-78 : Woodcutting skill tab
    Widget-320/Child-79 : Runecrafting skill tab
    Widget-320/Child-85 : Slayer skill tab
    Widget-320/Child-91 : Farming skill tab
    Widget-320/Child-97 : Construction skill tab
    Widget-320/Child-103 : Hunter skill tab
    Widget-320/Child-109 : Summoning skill tab
    Widget-320/Child-115 : Dungeoneering skill tab
     
    Widget-548/Child-00 : Game window
    Widget-548/Child-05 : Entire game screen
    Widget-548/Child-07 : Small minigame window
    Widget-548/Child-08 : Large minigame window
    Widget-548/Child-09 : Wilderness levels?
    Widget-548/Child-14 : Money pouch
    Widget-548/Child-17 : XP button
    Widget-548/Child-24 : Wilderness levels?
    Widget-548/Child-25 : Tutorial island continue reminder?
    Widget-548/Child-38 : Chat buttons
    Widget-548/Child-39 : Bottom interface buttons
    Widget-548/Child-42 : Squeal interface button
    Widget-548/Child-43 : Private chat interface button
    Widget-548/Child-44 : Friends chat interface button
    Widget-548/Child-45 : Clan chat interface button
    Widget-548/Child-46 : Settings interface button
    Widget-548/Child-47 : Emotes interface button
    Widget-548/Child-48 : Music interface button
    Widget-548/Child-49 : Notes interface button
    Widget-548/Child-89 : Attack options interface button
    Widget-548/Child-90 : Achievement diary interface button
    Widget-548/Child-91 : Skills interface button
    Widget-548/Child-92 : Quest interface button
    Widget-548/Child-93 : Inventory interface button
    Widget-548/Child-94 : Equipment interface button
    Widget-548/Child-95 : Prayer interface button
    Widget-548/Child-96 : Magic book interface button
    Widget-548/Child-97 : Top interface buttons
    Widget-548/Child-144 : Minimap
    Widget-548/Child-145 : Compass
    Widget-548/Child-148 : World map
    Widget-548/Child-150 : X button for logout
    Widget-548/Child-151 : HP orb
    Widget-548/Child-152 : Prayer orb
    Widget-548/Child-153 : Run orb
    Widget-548/Child-154 : Summoning orb
    Widget-548/Child-194 : Extension from money pouch
*/

}
