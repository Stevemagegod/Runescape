import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;
import com.rarebot.event.listeners.PaintListener;
import com.rarebot.script.Script;
import com.rarebot.script.ScriptManifest;
import com.rarebot.script.methods.Skills;
import com.rarebot.script.util.Filter;
import com.rarebot.script.wrappers.RSArea;
import com.rarebot.script.wrappers.RSCharacter;
import com.rarebot.script.wrappers.RSGroundItem;
import com.rarebot.script.wrappers.RSItem;
import com.rarebot.script.wrappers.RSPlayer;
import com.rarebot.script.wrappers.RSTile;
import com.rarebot.event.events.MessageEvent;
import com.rarebot.event.listeners.MessageListener;
import com.rarebot.script.wrappers.*;
import com.rarebot.script.methods.Prayer;
import java.awt.*;
@ScriptManifest (authors = {"Goku123"}, name = "GrasersPker", description = "Kills People in the Wild!", version = 3)

public class GrasersPker extends Script implements PaintListener, MessageListener {
	//======= Misc variables and such. =======
	Image Report;
	int LastHp, NewHp, LastMage, NewMage,
		MyAtt, MyStr, MyDef, MyRange,
		MyPrayer, MyMagic, MyHp, EnemyAtt = 1,
		EnemyStr = 1, EnemyDef = 1, EnemyRange = 1,
		EnemyPrayer = 1, EnemyMagic = 1, EnemyHp = 10,
		VengTimer, VengTimerSec, Red, Green,
		DisplayFor, SpecTimer, SpecTimerSec, SpecCurr,
		SpecOld, Height, Y, EnemyHpPercent, EnemyFinalHp;
	double Display, Hit, XpGain;
	RSCharacter Enemy;
	String EnemyName = "", EnemyNameOld;
	Color C;
	double newXp;
	double oldXp;
	double hit;
	int myAtkXp = 0;
	int myStrXp = 0;
	int myDefXp = 0;
	int myRngXp = 0;
	int cbtlvl = 0;
	public int useSpecial = 0;
	public int spec = 0;
	private static final Color MOUSE_COLOR = new Color(0, 255, 255),
            MOUSE_BORDER_COLOR = new Color(220, 220, 220),
            MOUSE_CENTER_COLOR = new Color(89, 255, 89);
	double lpxp = 3.5;
	String hitS;
	//======= Options Menu =======
	boolean OptionMenuOpen, Clicked;
	Image Options, OptionsBackground, OnButton, OffButton, Confirm;
	//======= HitPredictor =======
	int HitPredX = 5, HitPredY = 309;
	boolean MoveHitPredictor, UseHitPredictor = true;
	Image HitPredictor;
	//======= StatGrabber =======
	int StatGrabberX = 6, StatGrabberY = 309;
	boolean MoveStatGrabber, UseStatGrabber = true;
	Image StatGrabber;
	//======= HpAboveHead =======
	boolean UseHpAboveHead = true;
	//======= VengeanceTimer =======
	boolean UseVengeance = true;
	Image Vengance, VenganceBg, VengPic, VengPicNotReady;
	//======= SpeicalAttackTimer =======
	boolean UseSpecialAttack = true;
	Image SpecialAttack, SpecialAttackBg;
	//======= FreezeTimer =======
	boolean UseFreeze = true, MoveFreeze;
	int FreezeTimer, FreezeTime = 200, FreezeX = 434, FreezeY = 190;
	Image BarragePicture;
	//Animation
	int[] FreeForAllAreaAnimation={-1};
	int[] CrossingWildernessDitchAnimation={6703};
	int[] DeathAnimation={836};
	final int healAt = 390;  ///Healing
	int[] BANKERS = {44, 45, 494, 495, 496, 497,498, 499, 553, 909, 958, 1036, 2271, 2354, 2355, 2718, 2759, 3198,3293, 3416, 3418, 3824, 4456, 4457, 4458, 4459, 5488, 5901, 5912, 6200,
            6362, 6532, 6533, 6534, 6535, 7605, 8948, 9710, 14367};
	int[] BANK_BOOTHS = {782, 2213, 2995, 5276,6084, 10517, 11402, 12759, 14367, 19230, 20325, 24914, 11338, 11758,
            25808, 26972, 29085, 52589, 34752, 35647, 36786, 2012, 2015, 2019,42217, 42377, 42378};
	int[] DEPOSIT_BOXES = {2045, 9398, 20228, 24995, 25937,26969, 32924, 32930, 32931, 34755, 36788, 39830, 45079};
	int[] BANK_CHESTS = {2693, 4483, 8981, 12308, 21301, 20607,21301, 27663, 42192};
	int[] Lootid={11732,4087,1149,5698,1187,1188,1215,1216,1231,
			1232,1249,1250,1263,1264,1305,1306,1377,1378,143, 141, 139, 2434, 15331, 15330, 15329, 15328, 23253, 23251, 23249, 23247, 23245, 23243, 23530, 23529, 23528, 23527, 23526, 23525
			,1435,1540,1541,3140,3141,3176,3204,3205,1123,1073,1331,
			4585,4586,4587,4588,5680,5681,5716,5717,5730,1163,1127,1303,1079,1333,1199
			,6739,6740,7158,7407,11227,11228,11229,11230,78,867,882,884,886,888,890,886,11212,
			11283,11284,11335,11336,11732,11733,13481,13490,1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980,
            7223, 6297, 6293, 6295, 6299, 7521, 9988, 7228, 2878, 7568, 2343,
            1861, 13433, 315, 325, 319, 3144, 347, 355, 333, 339, 351, 329, 3381, 361, 10136, 5003, 379, 365, 373, 7946, 385, 397, 391, 3369,
            3371, 3373, 2309, 2325, 2333, 2327, 2331, 2323, 2335, 7178, 7180,
            7188, 7190, 7198, 7200, 7208, 7210, 7218, 7220, 2003, 2011, 2289,2291, 2293, 2295, 2297, 2299, 2301, 2303, 1891, 1893, 1895, 1897,
            1899, 1901, 7072, 7062, 7078, 7064, 7084, 7082, 7066, 7068, 1942,3122,3134,13788,6568,6569,10636,13443,
            6701, 6703, 7054, 6705, 7056, 7060, 2130, 1985, 1993, 1989, 1978,3749,3750,12672,12673,13407,
            5763, 5765, 1913, 5747, 1905, 5739, 1909, 5743, 1907, 1911, 5745,10828,10843,12680,12681,
            2955, 5749, 5751, 5753, 5755, 5757, 5759, 5761, 2084, 2034, 2048,6524,6536, 1712, 1710, 1708, 1706,
            2036, 2217, 2213, 2205, 2209, 2054, 2040, 2080, 2277, 2225, 2255,3105,3106,2436, 145, 147, 149,
            2221, 2253, 2219, 2281, 2227, 2223, 2191, 2233, 2092, 2032, 2074,526, 532, 530, 528, 3183, 2859, 534, 3125, 4834, 14793, 4812, 3123, 4832, 6729, 536,
            2030, 2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971, 4608,2452, 2454, 2456, 2458,
            1883, 1885,1982, 3751,3752,12674,12675,13408, 2550,2551, 2570,2571,12029,99, 12790, 12825, 1119,96, 12093, 12435, 10818,95, 12822, 12828, 1115,93, 12796, 12827, 12161,
			92, 12089, 12437, 2859, 3226,89, 12786, 12833, 1444,12083, 12466, 2363,88, 12039, 12434, 237,85, 12776, 12832, 10149,83, 12017, 12456, 6155,
			12788, 12837, 12168,80, 12025, 12442, 571,79, 12802, 12824, 1442,12804, 12824, 1440,78, 12013, 12457, 5933,76, 12081, 12465, 2361,12782, 12841, 10020,
			74, 12069, 12449, 6979,73, 12792, 12826, 12168,71, 12057, 12451, 10117,70, 12820, 12830, 1933,69, 12033, 12423, 1963,67, 12031, 12439, 7939,
			66, 12079, 12464, 2359,12123, 12452, 2150,63, 12015, 12436, 6287,62, 12037, 12427, 12161,61, 12085, 12468, 9736,57, 12784, 12840, 10095,12810, 12835, 10099,
			12812, 12836, 10103,56, 12531, 12424, 311,52, 12007, 12441, 9978,49, 12061, 12444, 2132,34, 12818, 12443, 12164,10, 12059, 12428, 6291};
	
	// Armor And Weapons 
	int[] MysticRobeArmorandWeaponsLoot={};
	int[] ArmadylArmorandWeaponsLoot={};
	int[] ToragArmorandWeaponsLoot={};
	int[] DharoksArmorandWeaponsLoot={};
	int[] DragonArmorandWeaponsLoot={11732,4087,1149,5698,1187,1188,1215,1216,1231,
			1232,1249,1250,1263,1264,1305,1306,1377,1378,143, 141, 139, 2434, 15331, 15330, 15329, 15328, 23253, 23251, 23249, 23247, 23245, 23243, 23530, 23529, 23528, 23527, 23526, 23525
			,1435,1540,1541,3140,3141,3176,3204,3205,
			4585,4586,4587,4588,5680,5681,5716,5717,5730,
			5731,6739,6740,7158,7407,11227,11228,11229,11230,
			11283,11284,11335,11336,11732,11733,13481,13490};
	int[] RuneArmorandWeaponsLoot={1163,1127,1303,1079,1333,1199 };
	int[] AdamantArmorandWeaponsLoot={1123,1073,1331,1317,};
	int[] MithrilArmorandWeaponsLoot={1071,1121,1159,1197,1343};
	int[] SteelArmorandWeaponsLoot={1105,1141,1069,1119,1157};
	int[] IronArmorandWeaponsLoot={1115, 1323,1067,1153,1420,1137,1279,1175,1335,1363,1101,1191,1309,1081};
	int[] BronzeArmorandWeaponsLoot={1139,1422,1277,1321,1291,1155,1173,1117};
	int[] BlackArmorandWeaponsLoot={1165,1195};
	int[] ScrollandPouchIDs={12029,99, 12790, 12825, 1119,96, 12093, 12435, 10818,95, 12822, 12828, 1115,93, 12796, 12827, 12161,
			92, 12089, 12437, 2859, 3226,89, 12786, 12833, 1444,12083, 12466, 2363,88, 12039, 12434, 237,85, 12776, 12832, 10149,83, 12017, 12456, 6155,
			12788, 12837, 12168,80, 12025, 12442, 571,79, 12802, 12824, 1442,12804, 12824, 1440,78, 12013, 12457, 5933,76, 12081, 12465, 2361,12782, 12841, 10020,
			74, 12069, 12449, 6979,73, 12792, 12826, 12168,71, 12057, 12451, 10117,70, 12820, 12830, 1933,69, 12033, 12423, 1963,67, 12031, 12439, 7939,
			66, 12079, 12464, 2359,12123, 12452, 2150,63, 12015, 12436, 6287,62, 12037, 12427, 12161,61, 12085, 12468, 9736,57, 12784, 12840, 10095,12810, 12835, 10099,
			12812, 12836, 10103,56, 12531, 12424, 311,52, 12007, 12441, 9978,49, 12061, 12444, 2132,34, 12818, 12443, 12164,10, 12059, 12428, 6291,};
	
	//Range Gear 
	int[] RedDHideArmorLoot={2495};
	int[] BlackDHideArmorLoot={};
	int[] GhostlyRobesLoot={6107,6108,6110,6106,6109,6111};
	int[] GreenDHideArmorLoot={1135,1065,1099};
	int[] Bows = {829, 837, 839, 841, 843, 845, 847, 849, 851, 853, 9705};
	int[] Knife={863,864,866,867};
	int[] Crossbowloot={};
	int[] DartsLoot={815,817};
	int[] ArrowsLoot={78,867,882,884,886,888,890,886,892,11212};
	int[] BoltsLoot={8882,10158,13083};
	int[] MagicRunes={562,554, 560, 557,558,560, 563,469, 561, 565,555,556,559,};
	int[] MagicStaffs={772,1381,1383,1385,1387,1393,1409,};
   	int[] Charms = {12158, 12159, 12160, 12161, 12162, 12163, 12164, 12165, 12166, 12167};
	int[] SPECIAL_WEAPONSloot={4151,7639,7640,7641,7642,7643,7644,7645,7646,7647,7648,805,1215,1216};
	
	//Potions
	@SuppressWarnings("unused")
	private int[] prayerPotion ={};
	public int AntiPot[]={};
	public int PrayerPot[] = {143, 141, 139, 2434, 15331, 15330, 15329, 15328, 23253, 23251, 23249, 23247, 23245, 23243, 23530, 23529, 23528, 23527, 23526, 23525};
	public int AttackPot[] = {149, 147, 145, 2436, 15311, 15310, 15309, 15308,  23265,  23263,  23261,  23259,  23257,  23255, 23500,  23499,  23498,  23497,  23496,  23495};
	public int StrengthPot[] = {161, 159, 157, 2440, 15315, 15314, 15313, 15312, 23289, 23287, 23285, 23283, 23281, 23279, 23506, 23505, 23504, 23503, 23502, 23501};
	public int DefencePot[] = {167, 165, 163, 2442, 15319, 15318, 15317, 15316, 23301, 23299, 23297, 23295, 23293, 23291, 23512, 23510, 23509, 23508, 23507};
	public int OverLoadPot[] = {15335, 15334, 15333, 15332, 23536, 23535, 23534, 23533, 23532, 23531};
	public int SummonPot[] = {12146, 12144, 12142, 12140};
    public int PrayerRenewalPot[] = {21636, 21634, 21632, 21630, 23619, 23617, 23615, 23613, 23611, 23609};
	
	//other
    int[] OtherLoot={1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980,
            7223, 6297, 6293, 6295, 6299, 7521, 9988, 7228, 2878, 7568, 2343,
            1861, 13433, 315, 325, 319, 3144, 347, 355, 333, 339, 351, 329, 3381, 361, 10136, 5003, 379, 365, 373, 7946, 385, 397, 391, 3369,
            3371, 3373, 2309, 2325, 2333, 2327, 2331, 2323, 2335, 7178, 7180,
            7188, 7190, 7198, 7200, 7208, 7210, 7218, 7220, 2003, 2011, 2289,2291, 2293, 2295, 2297, 2299, 2301, 2303, 1891, 1893, 1895, 1897,
            1899, 1901, 7072, 7062, 7078, 7064, 7084, 7082, 7066, 7068, 1942,3122,3134,13788,6568,6569,10636,13443,
            6701, 6703, 7054, 6705, 7056, 7060, 2130, 1985, 1993, 1989, 1978,3749,3750,12672,12673,13407,
            5763, 5765, 1913, 5747, 1905, 5739, 1909, 5743, 1907, 1911, 5745,10828,10843,12680,12681,
            2955, 5749, 5751, 5753, 5755, 5757, 5759, 5761, 2084, 2034, 2048,6524,6536, 1712, 1710, 1708, 1706,
            2036, 2217, 2213, 2205, 2209, 2054, 2040, 2080, 2277, 2225, 2255,3105,3106,2436, 145, 147, 149,
            2221, 2253, 2219, 2281, 2227, 2223, 2191, 2233, 2092, 2032, 2074,526, 532, 530, 528, 3183, 2859, 534, 3125, 4834, 14793, 4812, 3123, 4832, 6729, 536,
            2030, 2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971, 4608,2452, 2454, 2456, 2458,
            1883, 1885,1982, 3751,3752,12674,12675,13408, 2550,2551, 2570,2571};
	int[] BerserkerHelm={3751,3752,12674,12675,13408}; //k
	int[] PhoenixNecklace={};
	int[] WarriorRing={};
	int[] RingofRecoil={2550,2551}; //k
	int[] CombatBracelet={};
	int[] RingofLife={2570,2571}; //k
	int[] Toktzketxilloot={6524,6536}; //k
	int[] RockClimbingBootsloot={3105,3106}; //k
	int[] HelmofNeitiznot={10828,10843,12680,12681}; //k
	int[] ArcherHelm={3749,3750,12672,12673,13407}; //k
	int[] GraniteShield={3122,3134,13788}; //k
	int[] ObsidianCape={6568,6569,10636,13443}; //k
	int[] AmuletofStrength={};
	int[] Bones = {526, 532, 530, 528, 3183, 2859, 534, 3125, 4834, 14793, 4812, 3123, 4832, 6729, 536}; //k
	int[] Antifire={2452, 2454, 2456, 2458}; //k
	int[] AttackPots = {2436, 145, 147, 149}; //k
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
	int[] GLORYS = {1712, 1710, 1708, 1706}; //k
	public String[][] SPECIAL_WEAPONS = {{"rod of ivandis", "ivandis flail", "rune thrownaxe"},
	        {"dragon dagger", "dragon longsword", "dragon mace", "dragon spear", "rune claws", "vesta's longsword"},{"dragon halberd"},{"statius's warhammer", "statius' warhammer", "magic longbow"},
	        {"dragon claws", "abyssal whip", "granite maul", "granite mace", "darklight", "penance trident", "vesta's spear", "armadyl godsword", "saradomin godsword", "zanik's crossbow", "morrigan's throwing axe", "morrigan's javelin", "hand cannon"},
	        {"dragon scimitar", "magic shortbow", "saradomin bow", "guthix bow", "zamorak bow"},{"dragon 2h sword", "abyssal vine whip", "korasi's sword", "zamorak godsword"},{"dark bow"},{"brackish blade", "bone dagger", "brine sabre", "dorgeshuun crossbow"},
	        {"dragon pickaxe", "dragon battleaxe", "dragon hatchet", "excalibur", "staff of light", "ancient mace", "saradomin sword", "dwarven army axe", "bandos godsword", "seercull", "enhanced excalibur"}
	    };
	int[] WildernessDitch={65093,65095,65094,65081,65083,65078,65088,65084,65092,65079,65082,65076,65085};
	int[] FreeForAlldangerousPortal={38699};
	int[] FreeForAllSafePortal={38698};
	// Player Positions
	RSTile[] EdgevilleLodeStoneSpawnPoint={new RSTile (3067, 3505)};
	RSTile[] FreeForAllSafePositionSpawnPoint={new RSTile (2990, 9683), new RSTile (2815, 5511)}; 
	RSTile[] TilesToDitchFromEdgevillBank={new RSTile(3094,3493), new RSTile(3088, 3511), new RSTile(3087,3520 ) };
	RSTile[] EdgevilleLodeStonetoWildWall={new RSTile(3074, 3520)};
	RSTile[] RespawnEdgevilleLocation={new RSTile(3103,3488)};
	@SuppressWarnings("unused")
	private static final RSArea BorderArea = new RSArea(3103, 3520, 3097, 3523);
	@SuppressWarnings("unused")
	private static final int ValidBorder[] = { 65094, 65082, 6926, 65093, 65084 };
	@SuppressWarnings("unused")
	private static final RSArea Edge = new RSArea(3082, 3483, 3104, 3503);
	public long startTime = System.currentTimeMillis();

	public boolean onStart(){
		log("Welcome to GrasersPker!");
		try {

			log("Loading PkBuddy images!");
			SpecialAttack = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/ZSysG.png"));
			BarragePicture = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/dZJiI.png"));
			log("Loading images - 10%");
			SpecialAttackBg = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/tG4Kf.png"));
			log("Loading images - 20%");
			Vengance = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/qSVRu.png"));
			VenganceBg = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/zQ1t3.png"));
			log("Loading images - 30%");
			VengPic = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/pgH05.png"));
			log("Loading images - 40%");
			VengPicNotReady = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/7chpl.gif"));
			log("Loading images - 50%");
			Report = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/mXTwn.png"));
			Options = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/kvnbP.png"));
			log("Loading images - 60%");
			OptionsBackground = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/NhdO9.png"));
			log("Loading images - 70%");
			OnButton = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/ke1Xn.png"));
			log("Loading images - 80%");
			OffButton = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/Ta2Tk.png"));
			Confirm = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/LxIR6.png"));
			log("Loading images - 90%");
			StatGrabber = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/B2XBi.png"));
			log("Loading images - 100%");
			HitPredictor = Toolkit.getDefaultToolkit().getImage(new URL ("http://i.imgur.com/Yfn8v.png"));
			log("Loading has finished - Good luck!");
		} catch (final IOException e) {
			log("Images have failed to load!");
			e.printStackTrace();
		}
		env.disableRandoms();
		return true;
	}

	public void Compare(int Int1, int Int2) {
		if (Int2 > Int1) {
			C = new Color(255, 0, 0);
		}
		if (Int2 == Int1) {
			C = new Color(0, 150, 255);
		}
		if (Int2 < Int1) {
			C = new Color(0, 255, 0);
		}
	}

    	private double getHit() {
    		RSCharacter interacting = getMyPlayer().getInteracting();
    		
		if(interacting != null){
			if(interacting instanceof RSNPC){
				return ((newXp - oldXp) * 2.5);
			} else {
				double pxp = 0.4;
				if (cbtlvl < 1) {
					cbtlvl = getMyPlayer().getCombatLevel();
				}	        	
				if (cbtlvl <= 138 && cbtlvl >= 120) {
					pxp = 0.46;	
				} else if (cbtlvl <= 119 && cbtlvl >= 100) {
					pxp = 0.45;
				} else if (cbtlvl <= 99 && cbtlvl >= 80) {
					pxp = 0.44;
				} else if (cbtlvl <= 79 && cbtlvl >= 60) {
					pxp = 0.43;
				} else if (cbtlvl <= 59 && cbtlvl >= 40) {
					pxp = 0.42;
				} else if (cbtlvl <= 39 && cbtlvl >= 20) {
					pxp = 0.41;
				} else {
					pxp = 0.4;
				}
				return ((newXp - oldXp) / pxp);
			}
		}
		return 0;
	}

	private boolean newHit() {
		myAtkXp = skills.getCurrentExp(Skills.ATTACK);
		myStrXp = skills.getCurrentExp(Skills.STRENGTH);
		myDefXp = skills.getCurrentExp(Skills.DEFENSE);
		myRngXp = skills.getCurrentExp(Skills.RANGE);
		newXp = (myAtkXp + myStrXp + myDefXp + myRngXp);
		if (newXp > oldXp) {
			return true;
		}
		return false;
	}

	public void Update() {
    		RSCharacter interacting = getMyPlayer().getInteracting();
		if (UseFreeze == true && Enemy != null ) {
			if (Enemy.getAnimation() == 1979 && Enemy != null) {
				FreezeTime = 100;// ======= Burst =======
				if (EnemyMagic > 93) {
					FreezeTime = 200;
				}//======= Barrage! =======
			}

			if (Enemy.getAnimation() == 1978 && Enemy != null) {
				FreezeTime = 50;//Rush
				if (EnemyMagic > 82) {
					FreezeTime = 150;
				}//======= Blitz =======
			}

			if (Enemy.getAnimation() == 1161 && Enemy != null) {
				FreezeTime = 50;// ======= Bind =======
				if (EnemyMagic > 49) {
					FreezeTime=100;
				}//======= Snare =======
				if (EnemyMagic > 79) {
					FreezeTime=150;
				}//======= Entangle =======
			}
		}

		if (UseStatGrabber == false && UseHpAboveHead == false && UseVengeance == false && UseSpecialAttack == false && UseHitPredictor == false) {
			log.severe("Why use my script if you are just going to disable everything?!");
		}

		if (Enemy != null && !(EnemyName.equals(EnemyNameOld)) && UseStatGrabber == true && interacting instanceof RSPlayer) {

			EnemyNameOld = EnemyName;

			EnemyHp = 10;
			EnemyAtt = 1;
			EnemyStr = 1;
			EnemyDef = 1;
			EnemyMagic = 1;
			EnemyRange = 1;
			EnemyPrayer = 1;

			try {
				if (getMyPlayer().getInteracting() != null) {

					if (interacting instanceof RSPlayer) {

						@SuppressWarnings("deprecation")
						String encodedEnemyName = URLEncoder.encode(EnemyName);

						if (encodedEnemyName.contains("%3F")) {
							String fixed = encodedEnemyName.replaceAll("%3F", "%20");
							String Link = "http://hiscore.runescape.com/index_lite.ws?player=" + fixed;
							URL url = new URL(Link);
							BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
							String inputLine;
							int lineIndex = 0;
							while ((inputLine = in.readLine()) != null) {
								lineIndex++;
								if (lineIndex == 2) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyAtt = Integer.parseInt(inputLine);
									} else {
										EnemyAtt = 1;
									}
								}

								if (lineIndex == 3) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyDef = Integer.parseInt(inputLine);
									} else {
										EnemyDef = 1;
									}
								}

								if (lineIndex == 4) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyStr = Integer.parseInt(inputLine);
									} else {
										EnemyStr = 1;
									}
								}

								if (lineIndex == 5) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyHp = Integer.parseInt(inputLine);
										EnemyHp = EnemyHp*10;
									}  else {
										EnemyHp = 10;
									}
								}

								if (lineIndex == 6) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyRange = Integer.parseInt(inputLine);
									} else {
										EnemyRange = 1;
									}
								}

								if (lineIndex == 7) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyPrayer = Integer.parseInt(inputLine);
									} else {
										EnemyPrayer = 1;
									}
								}

								if (lineIndex == 8) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyMagic = Integer.parseInt(inputLine);
									} else {
										EnemyMagic = 1;
									}
								}
							}

							in.close();

						} else {

							String Link = "http://hiscore.runescape.com/index_lite.ws?player=" + EnemyName;
							URL url = new URL(Link);
							BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
							String inputLine;
							int lineIndex = 0;
							while ((inputLine = in.readLine()) != null) {
								lineIndex++;
								if (lineIndex == 2) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyAtt = Integer.parseInt(inputLine);
									} else {
										EnemyAtt = 1;
									}
								}

								if (lineIndex == 3) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyDef = Integer.parseInt(inputLine);
									} else {
										EnemyDef = 1;
									}
								}

								if (lineIndex == 4) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyStr = Integer.parseInt(inputLine);
									} else {
										EnemyStr = 1;
									}
								}

								if (lineIndex == 5) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyHp = Integer.parseInt(inputLine);
										EnemyHp = EnemyHp*10;
									}  else {
										EnemyHp = 10;
									}
								}

								if (lineIndex == 6) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyRange = Integer.parseInt(inputLine);
									} else {
										EnemyRange = 1;
									}
								}

								if (lineIndex == 7) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyPrayer = Integer.parseInt(inputLine);
									} else {
										EnemyPrayer = 1;
									}
								}

								if (lineIndex == 8) {
									StringTokenizer st = new StringTokenizer(inputLine, ",");
									st.nextToken();
									inputLine = st.nextToken();
									if (Integer.parseInt(inputLine) != -1) {
										EnemyMagic = Integer.parseInt(inputLine);
									} else {
										EnemyMagic = 1;
									}
								}
							}

							in.close();
						}
					}
				}
			} catch (Exception e) {
			}
		}

		if (getMyPlayer().getInteracting() !=null) {
			if (interacting instanceof RSPlayer) {
				Enemy = getMyPlayer().getInteracting();
				EnemyName = Enemy.getName();
				if (EnemyHp != 10) {
					EnemyHpPercent = Enemy.getHPPercent();
					EnemyFinalHp = Enemy.getHPPercent() * EnemyHp / 100;
				} else {
					EnemyFinalHp = Enemy.getHPPercent();
				}
			} else if (interacting instanceof RSNPC) {
				Enemy = getMyPlayer().getInteracting();
				EnemyFinalHp = getMyPlayer().getInteracting().getHPPercent();
			}
		}

		if (UseHitPredictor == true) {
			LastHp = NewHp;
			NewHp = skills.getCurrentExp(Skills.CONSTITUTION);
			LastMage = NewMage;
			NewMage = skills.getCurrentExp(Skills.MAGIC);
			XpGain=NewHp - LastHp;
			Hit=XpGain/0.133333333333;
			DisplayFor--;
			if (Hit > 0) {
				Display = Math.round(Hit);
				DisplayFor = 20;
			}

		}

		if (UseSpecialAttack == true) {

			SpecOld = SpecCurr;
			SpecCurr = settings.getSetting(300) / 10;
			if (SpecOld != SpecCurr) {
				if (SpecCurr != 100) {
					SpecTimer = 300;
				}
			}

			if (SpecTimer > -1) {
				SpecTimer--;
			}
		}

		if (UseVengeance == true) {
			if (NewMage > LastMage) {
				VengTimer = 300;
			}
			if (VengTimer > -1) {
				VengTimer--;
			}
		}

		if (UseFreeze == true) {
			FreezeTimer--;
			if (FreezeTimer < 1) {
				FreezeTimer = 0;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public int loop() {
		if (game.isLoggedIn()) {

			MyAtt = skills.getRealLevel(Skills.ATTACK);
			if (MyAtt > 99) {
				MyAtt = 99;
			}
			MyStr = skills.getRealLevel(Skills.STRENGTH);
			if (MyStr > 99) {
				MyStr = 99;
			}

			MyDef = skills.getRealLevel(Skills.DEFENSE);
			if (MyDef > 99) {
				MyDef = 99;
			}

			MyRange = skills.getRealLevel(Skills.RANGE);
			if (MyRange > 99) {
				MyRange = 99;
			}

			MyPrayer = skills.getRealLevel(Skills.PRAYER);
			if (MyPrayer > 99) {
				MyPrayer = 99;
			}

			MyMagic = skills.getRealLevel(Skills.MAGIC);
			if (MyMagic > 99) {
				MyMagic = 99;
			}

			MyHp = skills.getRealLevel(Skills.CONSTITUTION) * 10;
			if (MyHp > 990) {
				MyHp = 990;
			}

			Update();

			if (UseHitPredictor == true) {
				if (newHit()) {
					hit = getHit();
					oldXp = newXp;
				}
			}
		}
		Antiban1();
		Antiban2();	
		if(isinWild())
		if (!summoning.isFamiliarSummoned() && skills.getCurrentLevel(Skills.SUMMONING) >= 10) {
	          if (inventory.containsOneOf(ScrollandPouchIDs)) {
	          inventory.getItem(ScrollandPouchIDs).doAction("Summon");
	          sleep(random(2000, 3400));
	        }
	         } else if (!summoning.isFamiliarSummoned() && inventory.containsOneOf(SummonPot) && inventory.containsOneOf(ScrollandPouchIDs)
	          && skills.getCurrentLevel(Skills.SUMMONING) <= 10) {
	          inventory.getItem(SummonPot).doAction("Drink");
	          sleep(random(1000, 1500));
	        }
        if(Looting());
        Attacking();
        Activate();
        Eating();
		return 95;
	}
	
	private boolean isinWild() {
            RSTile loc = getMyPlayer().getLocation();
            int x = loc.getX();
            int y = loc.getY();
            if (x <= 0000 && x >= 0000) {
                    if (y <= 0000 && y >= 0000)
                            return true;
            }

            return false;
    }


	@SuppressWarnings("static-access")
	private void Activate() {
		if (useSpecial > 0) {
            if (spec == 0) {
                log("Special attack");
                game.openTab(game.getTab().ATTACK);
                sleep(500, 1000);
                interfaces.getComponent(884, 30).doClick();
                spec++;
                sleep(333, 666);
            }
		}
        }

	private void Antiban2() {
		int rr = random(0, 20);
        switch(rr) {
            case 1:
camera.setAngle(random(0, 350));
camera.setPitch(random(500, 5000));
break;
            case 2:
                break;
            case 3:
camera.setAngle(random(145,360));
camera.setPitch(random(100, 4099));
break;
            case 4:
                break;
            case 5:
camera.setAngle(random(0, 145));
camera.setPitch(random(4099, 8000));
break;
            case 6:
                break;
            case 7:
                break;
            case 8:
camera.setAngle(random(0, 360));
camera.setPitch(random(8000, 12000));
break;
            case 9:
                break;
            case 10:
                break;
            case 11:
camera.setAngle(random(100, 300));
camera.setPitch(random(12000, 16000));
break;
            case 12:
                break;
            case 13:
                break;
            case 15:
camera.moveRandomly (1200);
sleep(random(300, 500));
break;
            case 16:
                break;
            case 17:
                break;
            case 19:
camera.moveRandomly(random(750, 1250));
sleep(random(300, 500));
break;
        }
    }

	private void Antiban1() {
		  int r = random(0, 20);
          switch (r) {
          case 1:
                  break;
          case 3:
                  mouse.moveSlightly();
                  sleep(300, 600);
                  break;
          case 5:
                  break;
          case 6:
                  break;
          case 7:
                  mouse.moveRandomly(10, 50);
                  break;
          case 8:
                  break;
          case 9:
                  break;
          case 10:
                  mouse.moveOffScreen();
                  sleep (random(2000, 3000));
                  break;
          case 12:
                  break;
          case 15:
                  break;
          case 16:
                  mouse.moveSlightly();
                  sleep(300, 600);
                  break;
          case 17:
                  break;
          }
      }

	@SuppressWarnings("deprecation")
	private void Eating() {
		 if(getMyPlayer().getHPPercent() < 50){
		     RSItem food = inventory.getItem(FOODID);
		     if ( food != null) {
		          food.doAction("Eat");
		          log.severe("Eating...");
		     } else {
		    	 prayer.setPrayer(Prayer.Normal.RETRIBUTION, true);
                 log("Fuck out of food");
				                  sleep(500);
				                  return;
		     }
		 }
	}

	private void Attacking() {
		RSPlayer p = opponent();
        if (p != null) {
            if (getMyPlayer().getInteracting() != null) {
                walking.walkTileMM(p.getLocation());}
            if (p.isOnScreen()) {
                if (getMyPlayer().getInteracting() == null) {
                     {
                        p.interact("Attack");
                        prayer.setPrayer(Prayer.Normal.PROTECT_FROM_MELEE, true);
                        prayer.setPrayer(Prayer.Normal.ULTIMATE_STRENGTH, true);
                    }
                }
            } else {
                camera.turnTo(p);
                if (calc.distanceTo(p) >= 10) {
                    walking.walkTileMM(p.getLocation());
                }
            }
        }
        }

	private RSPlayer opponent() {
		 return players.getNearest(new Filter<RSPlayer>() {
             public boolean accept(RSPlayer player) {
                 return player != null && getMyPlayer().getInteracting() == null
                         && player.getTeam() != getMyPlayer().getTeam()
                         && player.getHPPercent() > 1;
             }
         });
	}

	private boolean Looting() {
		if(getMyPlayer().isInCombat())
		if (!inventory.isFull()) {
		RSGroundItem LOOT = groundItems.getNearest(Lootid);
        if (LOOT != null) {
            if (LOOT.isOnScreen()) {
                if (!LOOT.interact("Take " + LOOT.getItem().getName())) {
                }    
                }
            }
        return true;
		}
		return false;
	}
	
	public static int getWildernessLevel(final int y) {
        if (y < 3525) {
                return 0;
        } else if (y < 3528) {
                return 1;
        } else {
                int i = 2;
                int temp = y;
                while ((temp -= 8) >= 3528) {
                        i++;
                } return i;
        }
}
public static int getWildernessLevel() {
        return getWildernessLevel();
}

	public void messageReceived(MessageEvent e) {
		String M = e.getMessage();
		if (M.contains("A magical force stops you from moving.")) {
			prayer.setPrayer(Prayer.Normal.PROTECT_FROM_MAGIC, true);
			FreezeTimer = FreezeTime;
			if (prayer.isPrayerOn(Prayer.Normal.PROTECT_FROM_MAGIC) == true || prayer.isPrayerOn(Prayer.Curses.DEFLECT_MAGIC) == true ) {
				FreezeTimer = FreezeTimer / 2;
			}
		}
	}
	
	public void messageReceived1(MessageEvent e) {
		String M = e.getMessage();
		if (M.contains("Oh dear, you are poisoned.")) {
			prayer.setPrayer(Prayer.Normal.PROTECT_FROM_MELEE, true);
		}
	}
	
	public void messageReceived2(MessageEvent e) { // Clan Wars Safe Arena Message
		String M = e.getMessage();
		if (M.contains("Players can fight each other in hear, but your items will be SAFE if you die.")) {
			WalktoCombatZone();
		}
		}
	
	public void messageReceived3(MessageEvent e) {
		String M = e.getMessage();
		getMyPlayer().getAnimation();
		if (M.contains("Oh dear, you are dead!.")) {		
			log("Fuck we died");
		}
	}
	
	public void messageReceived4(MessageEvent e) {
		String M = e.getMessage();
		if (M.contains("Well done! You Have defeated ...")) {
			Enemy.getName();
			log("Hahaha we just killed that nub");
		}
	}

	private void WalktoCombatZone() {
		RSTile toCombatZone=new RSTile(2815, 5513);
		walking.walkTileOnScreen(toCombatZone);
		return;
	}

	public void onFinish(){
		env.enableRandoms();
		env.saveScreenshot(true);
		log.severe("Thanks for using GrasersPker!");
		log("A Special Thanks to Anarki99 from Powerbot for his PkBuddy. ");
	}
	
	private void drawMouse(Graphics g) {
        ((Graphics2D) g).setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        Point p = mouse.getLocation();
        Graphics2D spinG = (Graphics2D) g.create();
        Graphics2D spinGRev = (Graphics2D) g.create();
        Graphics2D spinG2 = (Graphics2D) g.create();
        spinG.setColor(MOUSE_BORDER_COLOR);
        spinGRev.setColor(MOUSE_COLOR);
        spinG.rotate(System.currentTimeMillis() % 2000d / 2000d * (360d) * 2
                * Math.PI / 180.0, p.x, p.y);
        spinGRev.rotate(System.currentTimeMillis() % 2000d / 2000d * (-360d)
                * 2 * Math.PI / 180.0, p.x, p.y);
        final int outerSize = 20;
        final int innerSize = 12;
        spinG.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        spinGRev.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize,
                outerSize, 100, 75);
        spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize,
                outerSize, -100, 75);
        spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2),
                innerSize, innerSize, 100, 75);
        spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2),
                innerSize, innerSize, -100, 75);
        g.setColor(MOUSE_CENTER_COLOR);
        g.fillOval(p.x, p.y, 2, 2);
        spinG2.setColor(MOUSE_CENTER_COLOR);
        spinG2.rotate(System.currentTimeMillis() % 2000d / 2000d * 360d
                * Math.PI / 180.0, p.x, p.y);
        spinG2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        spinG2.drawLine(p.x - 5, p.y, p.x + 5, p.y);
        spinG2.drawLine(p.x, p.y - 5, p.x, p.y + 5);
    }

	@SuppressWarnings("deprecation")
	public void onRepaint (Graphics g) {
		drawMouse(g);
		if (game.isLoggedIn()) {
			int CurrX=mouse.getLocation().x;
			int CurrY=mouse.getLocation().y;
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(255, 100, 100, 150));
			if (UseSpecialAttack == true) {
				g.drawImage(SpecialAttackBg, 526, 124, null);
				SpecTimerSec = SpecTimer / 10;
				Height = -2 + ((SpecCurr/3) + 3 - (SpecTimerSec / 10));
				Y = 158 - Height;
				g.fillRect(526,Y,34,Height);
			}
			if (UseSpecialAttack == true) {
				g.drawImage(SpecialAttack,526,124,null);
				g.setFont(new Font("Arial", 0, 16));
				if (SpecCurr > 90) {
					g.setColor(new Color(0, 0, 0));
					g.drawString("" + SpecCurr, 529, 144);
					g.setColor(new Color(255, 255, 255));
					g.drawString("" + SpecCurr, 528, 143);
				} else {
					g.setColor(new Color(0, 0, 0));
					g.drawString(""+SpecCurr,535,144);
					g.setColor(new Color(255,255,255));
					g.drawString(""+SpecCurr,534,143);
				}
				if (SpecCurr < 99) {
					g.setFont(new Font("Arial", 0, 12));
					g.setColor(new Color(0, 0, 0));
					g.drawString(""+SpecTimerSec,539,155);
					g.setColor(new Color(255,255,255));
					g.drawString(""+SpecTimerSec,538,154);
				}

			}
			if (UseVengeance == true) {
				g.drawImage(VenganceBg,690,127,null);
				Height = 30 - VengTimer / 10;
				Y = 157 - Height;
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(690,Y,30,Height);
				g.setFont(new Font("Arial", 0, 12));
				g.drawImage(Vengance,690,127,null);
				VengTimerSec = VengTimer / 10;
				g.setFont(new Font("Arial", 0, 12));
				g.setColor(new Color(0, 0, 0));
				g.drawString(""+VengTimerSec,728,153);
				int Red = VengTimer;
				int Green = 300 - VengTimer;
				if ( Red > 254 ) {
					Red = 254;
				}
				if ( Red < 1 ) {
					Red = 0;
				}
				if ( Green > 254 ) {
					Green = 254;
				}
				if ( Green < 1 ) {
					Green = 0;
				}
				g.setColor(new Color(Red, Green, 0));
				g.drawString(""+VengTimerSec, 727, 152);
				g.drawImage(VengPic, 700, 134, null);
				if (VengTimerSec > 0) {
					g.drawImage(VengPicNotReady, 700, 134, null);
				}
			}
			if (FreezeTimer > 0 || OptionMenuOpen == true) {
				if (UseFreeze == true) {
					g.drawImage(BarragePicture, FreezeX, FreezeY, null);
					g.setFont(new Font("Arial", 0, 24));
					g.setColor(new Color(0, 0, 50));
					g.drawString("" + (FreezeTimer / 10), FreezeX + 30, FreezeY + 71);
				}
			}
			if (game.getCurrentTab() == 12) {
				g.drawImage(Options, 611, 297, null);
			}
			if (mouse.isPressed() && CurrX > 611 && CurrX < 683 && CurrY < 327  && CurrY > 297 && game.getCurrentTab() == 12) {
				OptionMenuOpen = true;
			}
			if (mouse.isPressed() && CurrX > 216 && CurrX < 316 && CurrY > 433 && CurrY < 465) {
				OptionMenuOpen = false;
			}
			if (OptionMenuOpen == true) {
				g.drawImage(OptionsBackground, 0, 338, null);
				g.drawImage(Confirm, 217, 435, null);

				if (!mouse.isPressed()) {
					Clicked = false;
					MoveHitPredictor = false;
					MoveStatGrabber = false;
					MoveFreeze = false;
				}
				//======= HitPredictor, Move and Toggle. =======
				if (UseHitPredictor == true) {
					g.drawImage(OnButton, 287, 352, null);
				} else {
					g.drawImage(OffButton, 287, 352, null);
				}
				if (UseHitPredictor == false && Clicked == false && mouse.isPressed() && CurrX > 287 && CurrX < 358 && CurrY > 352 && CurrY < 372) {
					UseHitPredictor = true;
					Clicked = true;
				}
				if (UseHitPredictor == true && Clicked == false && mouse.isPressed() && CurrX > 287 && CurrX < 358 && CurrY > 352 && CurrY < 372) {
					UseHitPredictor = false;
					Clicked = true;
				}
				if (UseHitPredictor == true && MoveStatGrabber == false && MoveFreeze == false && mouse.isPressed() && CurrX > HitPredX && CurrX < HitPredX + 88 && CurrY > HitPredY && CurrY < HitPredY + 29) {
					MoveHitPredictor = true;
				}
				if (MoveHitPredictor == true) {
					HitPredX = CurrX - 44;
					HitPredY = CurrY - 14;
				}
				//======= StatGrabber, Move and Toggle. =======
				if (UseStatGrabber == true) {
					g.drawImage(OnButton,287, 382, null);
				} else {
					g.drawImage(OffButton, 287, 382, null);
				}
				if (UseStatGrabber == false && Clicked == false && mouse.isPressed() && CurrX > 287 && CurrX < 358 && CurrY > 382 && CurrY < 402) {
					UseStatGrabber = true;
					Clicked = true;
				}
				if (UseStatGrabber == true && Clicked == false && mouse.isPressed() && CurrX > 287 && CurrX < 358 && CurrY > 382 && CurrY < 402) {
					UseStatGrabber = false;
					Clicked = true;
				}
				if (UseStatGrabber == true && MoveHitPredictor == false && MoveFreeze == false && mouse.isPressed() && CurrX > StatGrabberX + 80 && CurrX < StatGrabberX + 550 && CurrY > StatGrabberY && CurrY < StatGrabberY + 29) {
					MoveStatGrabber = true;
				}
				if (MoveStatGrabber == true) {
					StatGrabberX = CurrX - 275;
					StatGrabberY = CurrY - 14;
				}
				//======= HpAboveHead, Toggle. =======
				if (UseHpAboveHead == true) {
					g.drawImage(OnButton, 287, 410, null);
				} else {
					g.drawImage(OffButton, 287, 410, null);
				}
				if (UseHpAboveHead == false && Clicked == false && mouse.isPressed() && CurrX > 287 && CurrX < 358 && CurrY > 410 && CurrY < 430) {
					UseHpAboveHead = true;
					Clicked = true;
				}
				if (UseHpAboveHead == true && Clicked == false && mouse.isPressed() && CurrX > 287 && CurrX < 358 && CurrY > 410 && CurrY < 430) {
					UseHpAboveHead = false;
					Clicked = true;
				}
				//======= Freeze Timer - NOT ADDED YET! =======
				if (UseFreeze == true) {
					g.drawImage(OnButton, 179, 352, null);
				} else {
					g.drawImage(OffButton, 179, 352, null);
				}
				if (UseFreeze == false && Clicked == false && mouse.isPressed() && CurrX > 179 && CurrX < 250 && CurrY > 352 && CurrY < 372) {
					UseFreeze = true;
					Clicked = true;
				}
				if (UseFreeze == true && Clicked == false && mouse.isPressed() && CurrX > 179 && CurrX < 250 && CurrY > 352 && CurrY < 372) {
					UseFreeze = false;
					Clicked = true;
				} 
				if (UseFreeze == true && MoveHitPredictor == false && MoveStatGrabber == false && mouse.isPressed() && CurrX > FreezeX && CurrX < FreezeX + 81 && CurrY > FreezeY && CurrY < FreezeX + 117) {
					MoveFreeze = true;
				}
				if (MoveFreeze == true) {
					FreezeX = CurrX - 41;
					FreezeY = CurrY - 60;
				}
				//======= VenganceTimer, Toggle. =======
				if (UseVengeance == true) {
					g.drawImage(OnButton, 179, 382, null);
				} else {
					g.drawImage(OffButton, 179, 382, null);
				}
				if (UseVengeance == false && Clicked == false && mouse.isPressed() && CurrX > 179 && CurrX < 250 && CurrY > 382 && CurrY < 402) {
					UseVengeance = true;
					Clicked = true;
				}
				if (UseVengeance == true && Clicked == false && mouse.isPressed() && CurrX > 179 && CurrX < 250 && CurrY > 382 && CurrY < 402) {
					UseVengeance = false;
					Clicked = true;
				}
				//======= SpecialAttackTimer, Toggle. =======
				if (UseSpecialAttack == true) {
					g.drawImage(OnButton, 179, 410, null);
				} else {
					g.drawImage(OffButton, 179, 410, null);
				}
				if (UseSpecialAttack == false && Clicked == false && mouse.isPressed() && CurrX > 179 && CurrX < 250 && CurrY > 410 && CurrY < 430) {
					UseSpecialAttack = true;
					Clicked = true;
				}
				if (UseSpecialAttack == true && Clicked == false && mouse.isPressed() && CurrX > 179 && CurrX < 250 && CurrY > 410 && CurrY < 430) {
					UseSpecialAttack = false;
					Clicked = true;
				}

			}

			g.setFont(new Font("Arial", 0, 20));
			if (Enemy != null ) {
				Point M = calc.tileToScreen(Enemy.getLocation(), Enemy.getHeight());
				if (calc.pointOnScreen(M) && UseHpAboveHead == true) {
    					RSCharacter interacting = getMyPlayer().getInteracting();
					if(interacting instanceof RSNPC){
						g.setColor(new Color(0, 0, 0));
						g.drawString("Hp: " + EnemyFinalHp + "%", M.x - 35, M.y - 35);
						if (EnemyFinalHp > 50) {
							g.setColor(new Color(0, 255, 0));
						}
						if (EnemyFinalHp < 51) {
							g.setColor(new Color(255, 0, 0));
						}
						g.drawString("Hp: " + EnemyFinalHp + "%", M.x - 35, M.y - 35);
					} else {
						if (EnemyFinalHp > 100) {
							g.setColor(new Color(0, 0, 0));
							g.drawString("Hp: " + EnemyFinalHp + " / " + EnemyHp, M.x - 50, M.y - 40);
								if (EnemyFinalHp / EnemyHp * 100 > 50) {
								g.setColor(new Color(0, 255, 0));
							}
							if (EnemyFinalHp / EnemyHp * 100  < 51) {
								g.setColor(new Color(255, 0, 0));
								}
							g.drawString("Hp: " + EnemyFinalHp + " / " + EnemyHp, M.x - 50, M.y - 40);
						}
						if (EnemyHp == 10) {
							g.setColor(new Color(0, 0, 0));
							g.drawString("Hp: " + EnemyFinalHp + "%", M.x - 35, M.y - 40);
							if (EnemyFinalHp > 50) {
								g.setColor(new Color(0, 255, 0));
							}
							if (EnemyFinalHp < 51) {
								g.setColor(new Color(255, 0, 0));
							}
							g.drawString("Hp: " + EnemyFinalHp + "%", M.x - 35, M.y - 40);
						}
					}
				}

			}
			g.setFont(new Font("Arial", 0, 15));
			g.drawImage(Report, 404, 481, null);
			g.setColor(new Color(255, 255, 255));
			g.drawString(""+ EnemyName , 411, 497);
			if (UseHitPredictor == true) {
				g.drawImage(HitPredictor, HitPredX, HitPredY, null);
				if (DisplayFor > 0 && Display < 2000) {
					if (hit > 1000) {
						hitS = "";
					} else {
						hitS = Integer.toString((int) hit);
						g.setFont(new Font("Arial", 0, 25));
						g.setColor(new Color(0, 0, 0, DisplayFor*12));
						g.drawString("" + hitS, HitPredX + 14, HitPredY + 24);
						g.setColor(new Color(255, 0, 0, DisplayFor*12));
						g.drawString("" + hitS, HitPredX + 13, HitPredY + 23);
				}
				}
			}
			if (!(UseStatGrabber == false)) {
				g.drawImage(StatGrabber, StatGrabberX + 88, StatGrabberY, null);
				g.setFont(new Font("Arial", 0, 12));
				g.setColor(new Color(0, 0, 0));
				g.drawString("" + MyAtt, StatGrabberX + 122, StatGrabberY + 13);
				Compare(MyAtt, EnemyAtt);
				g.setColor(C);
				g.drawString("" + MyAtt, StatGrabberX+121, StatGrabberY + 12);
				if (EnemyAtt !=1) {
					g.setColor(new Color(0, 0, 0));
					g.drawString("" + EnemyAtt, StatGrabberX + 133, StatGrabberY + 26);
					Compare(EnemyAtt,MyAtt);
					g.setColor(C);
					g.drawString(""+EnemyAtt, StatGrabberX + 132, StatGrabberY + 25);
				}
				g.setColor(new Color(0, 0, 0));
				g.drawString("" + MyStr, StatGrabberX + 183, StatGrabberY + 13);
				Compare(MyStr, EnemyStr);
				g.setColor(C);
				g.drawString("" + MyStr, StatGrabberX + 182, StatGrabberY + 12);
				if (EnemyStr !=1) {
					g.setColor(new Color(0, 0, 0));
					g.drawString("" + EnemyStr, StatGrabberX + 193, StatGrabberY + 26);
					Compare(EnemyStr,MyStr);
					g.setColor(C);
					g.drawString("" + EnemyStr, StatGrabberX + 192, StatGrabberY + 25);
				}
				g.setColor(new Color(0, 0, 0));
				g.drawString("" + MyDef, StatGrabberX + 243, StatGrabberY + 13);
				Compare(MyDef, EnemyDef);
				g.setColor(C);
				g.drawString("" + MyDef, StatGrabberX + 242, StatGrabberY +12);
				if (EnemyDef !=1) {
					g.setColor(new Color(0, 0, 0));
					g.drawString("" + EnemyDef, StatGrabberX + 252, StatGrabberY + 26);
					Compare(EnemyDef, MyDef);
					g.setColor(C);
					g.drawString("" + EnemyDef, StatGrabberX + 251, StatGrabberY + 25);
				}
				g.setColor(new Color(0, 0, 0));
				g.drawString("" + MyHp, StatGrabberX + 298, StatGrabberY + 13);
				Compare(MyHp, EnemyHp);
				g.setColor(C);
				g.drawString("" + MyHp, StatGrabberX + 297, StatGrabberY + 12);
				if (EnemyHp > 10) {
					g.setColor(new Color(0, 0, 0));
					g.drawString("" + EnemyHp, StatGrabberX + 308, StatGrabberY + 26);
					Compare(EnemyHp, MyHp);
					g.setColor(C);
					g.drawString("" + EnemyHp, StatGrabberX+307, StatGrabberY + 25);
				}
				g.setColor(new Color(0, 0, 0));
				g.drawString("" + MyMagic, StatGrabberX + 363, StatGrabberY + 13);
				Compare(MyMagic, EnemyMagic);
				g.setColor(C);
				g.drawString("" + MyMagic, StatGrabberX + 362, StatGrabberY + 12);
				if (EnemyMagic !=1) {
					g.setColor(new Color(0, 0, 0));
					g.drawString("" + EnemyMagic, StatGrabberX + 373, StatGrabberY + 26);
					Compare(EnemyMagic, MyMagic);
					g.setColor(C);
					g.drawString("" + EnemyMagic, StatGrabberX + 372, StatGrabberY + 25);
				}
				g.setColor(new Color(0, 0, 0));
				g.drawString("" + MyRange, StatGrabberX + 423, StatGrabberY + 13);
				Compare(MyRange, EnemyRange);
				g.setColor(C);
				g.drawString("" + MyRange, StatGrabberX + 422, StatGrabberY + 12);
				if (EnemyRange !=1) {
					g.setColor(new Color(0, 0, 0));
					g.drawString("" + EnemyRange, StatGrabberX + 433, StatGrabberY + 26);
					Compare(EnemyRange, MyRange);
					g.setColor(C);
					g.drawString("" + EnemyRange, StatGrabberX + 432, StatGrabberY + 25);
				}
				g.setColor(new Color(0, 0, 0));
				g.drawString("" + MyPrayer, StatGrabberX + 483,StatGrabberY + 13);
				Compare(MyPrayer, EnemyPrayer);
				g.setColor(C);
				g.drawString("" + MyPrayer, StatGrabberX + 482, StatGrabberY + 12);
				if (EnemyPrayer !=1) {
					g.setColor(new Color(0, 0, 0));
					g.drawString("" + EnemyPrayer, StatGrabberX + 493, StatGrabberY + 26);
					Compare(EnemyPrayer, MyPrayer);
					g.setColor(C);
					g.drawString("" + EnemyPrayer, StatGrabberX + 492, StatGrabberY + 25);
				}
			}
		}
	}
}
