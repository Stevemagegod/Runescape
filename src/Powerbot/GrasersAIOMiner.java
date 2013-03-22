import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.URLConnection;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.client.RSObject;

        @Manifest(authors = { "Grasers" }, name = "Miner", description = "", version = 1.0)
        public class Miner extends ActiveScript {
                public boolean running = true;
                int xpPerHour;
                int currentXP;
                int gainedXP;
                int mouseSpeed;
                String rockName;
                boolean useAntiban;
                boolean useRun;
                public int MiningLevel, startXP;
                public int gems = 0;
                public int oresMined = 0;
                public int entrance = 52855, exit = 52864, depositBox = 25937,bankInterface = 11;
                int OreMine1[];
                SceneObject OreMine2;
                int OreMine3[];
                int OREMINED;
                int OREID;
                int EXP;
                int GambleInt;
                int ORES;
                int DropOres;
                boolean currentlyMining;
                boolean checkMiningStat;
                boolean scriptStart;
                int ShowOre;
                int ShowLocation;
                int ShowMethod;
                private String Mine;
                private String Location;
                public URLConnection url;
                public BufferedReader in;
                public BufferedWriter out;
                public String line;
                public String line2;
                public boolean scriptStarted;
           
                //Rock Ids
                int[] Rockids={ 11954, 11956, 11955, 9717, 9718, 9719, 9724, 37307, 37308, 37309, 31071, 31072, 3107, 31060, 31061,9714, 9716, 11933, 11934, 11935, 11957,11958, 11959, 31077, 31078, 31079,9708, 9709, 9710, 11936, 11937, 11938, 11960, 11961, 11962, 11963, 31080, 31082,11942, 11943, 11944, 32438, 32439, 31086, 31088,};
                int[] ironRockID = { 11954, 11956, 11955, 9717, 9718, 9719, 9724, 37307, 37308, 37309, 31071, 31072, 3107, 31060, 31061 };
                int[] tinRockID = {9714, 9716, 11933, 11934, 11935, 11957,11958, 11959, 31077, 31078, 31079}; //k
                int[] copperRockID ={9708, 9709, 9710, 11936, 11937, 11938, 11960, 11961, 11962, 11963, 31080, 31082}; //k
                int[] mithrilRockID = {11942, 11943, 11944, 32438, 32439, 31086, 31088}; //k
                int[] adamantRockID = {1939, 11940, 11941, 31083, 31084, 31085, 32435, 32436};
                int[] runiteRockID = {14895, 33078, 33079};
                int[] clayRockID = {711, 9713, 15503, 15504, 15505, 31062, 31063};
                int[] silverRockID = {2311, 9713, 9714, 9716, 11948, 11949, 11950, 37304, 37305, 37306};
                int[] goldRockID = {9720, 9722, 11183, 11184, 11185, 15503, 15505, 31065, 31066, 37310, 37312, 37313};
                int[] coalRockID = {2096, 2097, 11930, 11931, 11932, 11963, 11964, 14850, 14851, 14852, 31068, 31069, 31070, 32426};
                int[] essMine = {2491};
                int[] chosenRockID = {};
           
                //Objects
                int[] pickID = { 1265, 1267, 1269, 1271, 1273, 1275, 1623, 1621, 1619, 1617 };
                int[] BANKERS = {44, 45, 494, 495, 496, 497,498, 499, 553, 909, 958, 1036, 2271, 2354, 2355, 2718, 2759, 3198,3293, 3416, 3418, 3824, 4456, 4457, 4458, 4459, 5488, 5901, 5912, 6200,6362, 6532, 6533, 6534, 6535, 7605, 8948, 9710, 14367};
                int[] BANK_BOOTHS = {782, 2213, 2995, 5276,6084, 10517, 11402, 12759, 14367, 19230, 20325, 24914, 11338, 11758,25808, 26972, 29085, 52589, 34752, 35647, 36786, 2012, 2015, 2019,42217, 42377, 42378};
                int[] DEPOSIT_BOXES = {2045, 9398, 20228, 24995, 25937,26969, 32924, 32930, 32931, 34755, 36788, 39830, 45079};
                int[] BANK_CHESTS = {2693, 4483, 8981, 12308, 21301, 20607,21301, 27663, 42192};
                int WaterSkins[] = {1823, 1825, 1827, 1829};
                public RSObject rock, nextRock = null, lastRock = null;
                public long startTime = System.currentTimeMillis();
                public final Tile WESTBANK_TO_CLAYMINE[] = {new Tile(3189, 3435,0), new Tile(3185, 3431,0), new Tile(3180, 3431,0), new Tile(3175, 3428,0), new Tile(3172, 3424,0), new Tile(3170, 3419,0), new Tile(3171, 3414,0), new Tile(3171, 3409,0), new Tile(3168, 3405,0), new Tile(3169, 3400,0), new Tile(3170, 3395,0), new Tile(3171, 3390,0), new Tile(3175, 3386,0), new Tile(3179, 3383,0), new Tile(3182, 3379,0), new Tile(3183, 3374,0), new Tile(3180, 3370,0)};
                        public final Tile CLAYMINE_TO_WESTBANK[];
                        public final Tile WESTBANK_TO_IRONMINE[] = {new Tile(3189, 3435,0), new Tile(3185, 3431,0), new Tile(3180, 3431,0), new Tile(3175, 3428,0), new Tile(3172, 3424,0), new Tile(3170, 3419,0), new Tile(3171, 3414,0), new Tile(3171, 3409,0), new Tile(3168, 3405,0), new Tile(3169, 3400,0), new Tile(3170, 3395,0), new Tile(3171, 3390,0), new Tile(3175, 3386,0), new Tile(3179, 3383,0), new Tile(3182, 3379,0), new Tile(3183, 3374, 0), new Tile(3180, 3370,0), new Tile(3175, 3367,0)};
                        public final Tile IRONMINE_TO_WESTBANK[];
                        public final Tile WESTBANK_TO_TINMINE[] = {new Tile(3189, 3435,0), new Tile(3185, 3431,0), new Tile(3180, 3431,0), new Tile(3175, 3428,0), new Tile(3172, 3424,0), new Tile(3170, 3419,0), new Tile(3171, 3414,0), new Tile(3171, 3409,0), new Tile(3168, 3405,0), new Tile(3169, 3400,0), new Tile(3170, 3395,0), new Tile(3171, 3390,0), new Tile(3175, 3386,0), new Tile(3179, 3383,0), new Tile(3182, 3379,0), new Tile(3182, 3376,0)};
                        public Tile TINMINE_TO_WESTBANK[]={};
                        public final Tile WESTBANK_TO_SILVERMINE[] = {new Tile(3189, 3435,0), new Tile(3185, 3431,0), new Tile(3180, 3431,0), new Tile(3175, 3428,0), new Tile(3172, 3424,0), new Tile(3170, 3419,0), new Tile(3171, 3414,0), new Tile(3171, 3409, 0), new Tile(3168, 3405, 0), new Tile(3169, 3400, 0), new Tile(3170, 3395,0), new Tile(3171, 3390,0), new Tile(3175, 3386,0), new Tile(3179, 3383,0), new Tile(3182, 3379,0), new Tile(3183, 3374,0), new Tile(3180, 3370,0), new Tile(3177, 3369,0)};
                        public final Tile SILVERMINE_TO_WESTBANK[];
                        public final Tile EASTBANK_TO_IRONMINE[] = {new Tile(3253, 3420,0), new Tile(3253, 3425,0), new Tile(3257, 3428,0), new Tile(3262, 3428,0), new Tile(3267, 3428,0), new Tile(3272, 3428,0), new Tile(3277, 3428,0), new Tile(3282, 3429, 0), new Tile(3284, 3424, 0), new Tile(3285, 3419, 0), new Tile(3286, 3414,0), new Tile(3290, 3410,0), new Tile(3290, 3405,0), new Tile(3290, 3400,0), new Tile(3290, 3395,0), new Tile(3290, 3390,0), new Tile(3292, 3385,0), new Tile(3290, 3380,0), new Tile(3288, 3375, 0), new Tile(3284, 3371, 0), new Tile(3286, 3368,0)};
                        public final Tile IRONMINE_TO_EASTBANK[];
                        public final Tile EASTBANK_TO_COPPERMINE[] = {new Tile(3253, 3420,0), new Tile(3253, 3425, 0), new Tile(3257, 3428, 0), new Tile(3262, 3428, 0), new Tile(3267, 3428,0), new Tile(3272, 3428,0), new Tile(3277, 3428,0), new Tile(3282, 3429,0), new Tile(3284, 3424,0), new Tile(3285, 3419, 0), new Tile(3286, 3414, 0), new Tile(3290, 3410, 0), new Tile(3290, 3405, 0), new Tile(3290, 3400,0), new Tile(3290, 3395,0), new Tile(3290, 3390,0), new Tile(3292, 3385,0), new Tile(3290, 3380,0), new Tile(3288, 3375,0), new Tile(3284, 3371,0), new Tile(3287, 3363, 0)};
                        public final Tile COPPERMINE_TO_EASTBANK[];
                        public final Tile EASTBANK_TO_TINMINE[] = {new Tile(3253, 3420, 0), new Tile(3253, 3425,0), new Tile(3257, 3428,0), new Tile(3262, 3428,0), new Tile(3267, 3428,0), new Tile(3272, 3428, 0), new Tile(3277, 3428, 0), new Tile(3282, 3429,0), new Tile(3284, 3424,0), new Tile(3285, 3419, 0), new Tile(3286, 3414,0), new Tile(3290, 3410,0), new Tile(3290, 3405, 0), new Tile(3290, 3400,0), new Tile(3290, 3395,0), new Tile(3290, 3390,0), new Tile(3292, 3385,0), new Tile(3290, 3380,0), new Tile(3288, 3375,0), new Tile(3284, 3371,0), new Tile(3282, 3363,0)};
                        public final Tile TINMINE_TO_EASTBANK[];
                        public final Tile FALLYBANK_TO_IRONMINE[] = {new Tile(3012, 3355,0), new Tile(3009, 3359,0), new Tile(3008, 3354, 0), new Tile(3008, 3349, 0), new Tile(3008, 3344, 0), new Tile(3008, 3339, 0), new Tile(3008, 3334,0), new Tile(3008, 3329,0), new Tile(3008, 3324,0), new Tile(3008, 3319,0), new Tile(3007, 3314,0), new Tile(3007, 3309,0), new Tile(3007, 3304, 0), new Tile(3005, 3299,0), new Tile(3005, 3294, 0), new Tile(3004, 3289, 0), new Tile(3005, 3284,0), new Tile(3005, 3279,0), new Tile(3001, 3276,0), new Tile(2996, 3276,0), new Tile(2993, 3272,0), new Tile(2989, 3268,0), new Tile(2984, 3266,0), new Tile(2979, 3264,0), new Tile(2978, 3259, 0), new Tile(2978, 3254, 0), new Tile(2976, 3249,0), new Tile(2974, 3244,0), new Tile(2970, 3240,0)};
                        public final Tile IRONMINE_TO_FALLYBANK[];
                        public final Tile FALLYBANK_TO_CLAYMINE[] = {new Tile(3012, 3355,0), new Tile(3009, 3359,0), new Tile(3008, 3354,0), new Tile(3008, 3349,0), new Tile(3008, 3344,0), new Tile(3008, 3339,0), new Tile(3008, 3334,0), new Tile(3008, 3329,0), new Tile(3008, 3324,0), new Tile(3008, 3319,0), new Tile(3007, 3314,0), new Tile(3007, 3309,0), new Tile(3007, 3304,0), new Tile(3005, 3299,0), new Tile(3005, 3294,0), new Tile(3004, 3289,0), new Tile(3005, 3284,0), new Tile(3005, 3279,0), new Tile(3001, 3276,0), new Tile(2996, 3276,0), new Tile(2993, 3272,0), new Tile(2989, 3268,0), new Tile(2984, 3266,0), new Tile(2979, 3264,0), new Tile(2978, 3259,0), new Tile(2978, 3254,0), new Tile(2976, 3249,0), new Tile(2974, 3244,0), new Tile(2986, 3240,0)};
                        public final Tile CLAYMINE_TO_FALLYBANK[];
                        public final Tile FALLYBANK_TO_COPPERMINE[] = {new Tile(3012, 3355,0), new Tile(3009, 3359,0), new Tile(3008, 3354,0), new Tile(3008, 3349,0), new Tile(3008, 3344,0), new Tile(3008, 3339, 0), new Tile(3008, 3334,0), new Tile(3008, 3329,0), new Tile(3008, 3324,0), new Tile(3008, 3319,0), new Tile(3007, 3314,0), new Tile(3007, 3309,0), new Tile(3007, 3304,0), new Tile(3005, 3299,0), new Tile(3005, 3294,0), new Tile(3004, 3289, 0), new Tile(3005, 3284,0), new Tile(3005, 3279,0), new Tile(3001, 3276,0), new Tile(2996, 3276,0), new Tile(2993, 3272,0), new Tile(2989, 3268,0), new Tile(2984, 3266,0), new Tile(2979, 3264,0), new Tile(2978, 3259,0), new Tile(2978, 3254,0), new Tile(2977, 3248,0)};
                        public final Tile COPPERMINE_TO_FALLYBANK[];
                        public final Tile FALLYBANK_TO_TINMINE[] = {new Tile(3012, 3355,0), new Tile(3009, 3359,0), new Tile(3008, 3354,0), new Tile(3008, 3349,0), new Tile(3008, 3344,0), new Tile(3008, 3339,0), new Tile(3008, 3334,0), new Tile(3008, 3329,0), new Tile(3008, 3324,0), new Tile(3008, 3319,0), new Tile(3007, 3314,0), new Tile(3007, 3309,0), new Tile(3007, 3304,0), new Tile(3005, 3299,0), new Tile(3005, 3294,0), new Tile(3004, 3289,0), new Tile(3005, 3284,0), new Tile(3005, 3279,0), new Tile(3001, 3276,0), new Tile(2996, 3276,0), new Tile(2993, 3272,0), new Tile(2989, 3268,0), new Tile(2984, 3266,0), new Tile(2979, 3264,0), new Tile(2978, 3259,0), new Tile(2978, 3254,0), new Tile(2976, 3249,0), new Tile(2974, 3244,0), new Tile(2984, 3236,0)};
                        public final Tile TINMINE_TO_FALLYBANK[];
                        public final Tile FALLYBANK_TO_GOLDMINE[] = {new Tile(3012, 3355,0), new Tile(3009, 3359,0), new Tile(3008, 3354,0), new Tile(3008, 3349,0), new Tile(3008, 3344,0), new Tile(3008, 3339,0), new Tile(3008, 3334,0), new Tile(3008, 3329,0), new Tile(3008, 3324,0), new Tile(3008, 3319,0), new Tile(3007, 3314,0), new Tile(3007, 3309,0), new Tile(3007, 3304,0), new Tile(3005, 3299,0), new Tile(3005, 3294,0), new Tile(3004, 3289,0), new Tile(3005, 3284,0), new Tile(3005, 3279,0), new Tile(3001, 3276,0), new Tile(2996, 3276,0), new Tile(2993, 3272,0), new Tile(2989, 3268,0), new Tile(2984, 3266,0), new Tile(2979, 3264,0), new Tile(2978, 3259,0), new Tile(2978, 3254,0), new Tile(2976, 3249,0), new Tile(2974, 3244,0), new Tile(2976, 3234,0)};
                        public final Tile GOLDMINE_TO_FALLYBANK[];
                        public final Tile ALKBANK_TO_MINE[] = {new Tile(3269, 3168,0), new Tile(3274, 3167,0), new Tile(3275, 3172,0), new Tile(3277, 3177,0), new Tile(3280, 3181,0), new Tile(3281, 3186,0), new Tile(3281, 3191,0), new Tile(3281, 3196,0), new Tile(3280, 3201,0), new Tile(3280, 3206,0), new Tile(3284, 3210,0), new Tile(3284, 3215,0), new Tile(3282, 3220,0), new Tile(3280, 3225,0), new Tile(3281, 3230,0), new Tile(3284, 3234,0), new Tile(3289, 3237,0), new Tile(3292, 3241,0), new Tile(3292, 3246,0), new Tile(3292, 3251,0), new Tile(3292, 3256,0), new Tile(3292, 3261,0), new Tile(3293, 3266,0), new Tile(3293, 3271,0), new Tile(3294, 3276,0), new Tile(3298, 3280,0), new Tile(3298, 3285,0), new Tile(3298, 3290,0), new Tile(3298, 3295,0), new Tile(3298, 3300,0), new Tile(3300, 3305,0), new Tile(3300, 3310,0), new Tile(3299, 3315,0)};
                        public final Tile MINE_TO_ALKBANK[];
                        public final Tile ALKBANK_TO_IRONMINE[] = {new Tile(3269, 3168,0), new Tile(3274, 3167,0), new Tile(3275, 3172,0), new Tile(3277, 3177,0), new Tile(3280, 3181,0), new Tile(3281, 3186,0), new Tile(3281, 3191,0), new Tile(3281, 3196,0), new Tile(3280, 3201,0), new Tile(3280, 3206,0), new Tile(3284, 3210,0), new Tile(3284, 3215,0), new Tile(3282, 3220,0), new Tile(3280, 3225,0), new Tile(3281, 3230,0), new Tile(3284, 3234,0), new Tile(3289, 3237,0), new Tile(3292, 3241,0), new Tile(3292, 3246,0), new Tile(3292, 3251,0), new Tile(3292, 3256,0), new Tile(3292, 3261,0), new Tile(3293, 3266,0), new Tile(3293, 3271,0), new Tile(3294, 3276,0), new Tile(3298, 3280,0), new Tile(3298, 3285,0), new Tile(3298, 3290,0), new Tile(3298, 3295,0), new Tile(3298, 3300,0), new Tile(3300, 3305,0), new Tile(3298, 3311,0)};
                        public final Tile IRONMINE_TO_ALKBANK[];
                        public final Tile ALKBANK_TO_GOLDMINE[] = {new Tile(3269, 3168,0), new Tile(3274, 3167,0), new Tile(3275, 3172,0), new Tile(3277, 3177,0), new Tile(3280, 3181,0), new Tile(3281, 3186,0), new Tile(3281, 3191,0), new Tile(3281, 3196,0), new Tile(3280, 3201,0), new Tile(3280, 3206,0), new Tile(3284, 3210,0), new Tile(3284, 3215,0), new Tile(3282, 3220,0), new Tile(3280, 3225,0), new Tile(3281, 3230,0), new Tile(3284, 3234,0), new Tile(3289, 3237,0), new Tile(3292, 3241,0), new Tile(3292, 3246,0), new Tile(3292, 3251,0), new Tile(3292, 3256,0), new Tile(3292, 3261,0), new Tile(3293, 3266,0), new Tile(3293, 3271,0), new Tile(3294, 3276,0), new Tile(3298, 3280,0), new Tile(3297, 3287,0)};
                        public final Tile GOLDMINE_TO_ALKBANK[];
                        public final Tile ALKBANK_TO_SILVERMINE[] = {new Tile(3269, 3168,0), new Tile(3274, 3167,0), new Tile(3275, 3172,0), new Tile(3277, 3177,0), new Tile(3280, 3181,0), new Tile(3281, 3186,0), new Tile(3281, 3191,0), new Tile(3281, 3196,0), new Tile(3280, 3201,0), new Tile(3280, 3206,0), new Tile(3284, 3210,0), new Tile(3284, 3215,0), new Tile(3282, 3220,0), new Tile(3280, 3225,0), new Tile(3281, 3230,0), new Tile(3284, 3234,0), new Tile(3289, 3237,0), new Tile(3292, 3241,0), new Tile(3292, 3246,0), new Tile(3292, 3251,0), new Tile(3292, 3256,0), new Tile(3292, 3261,0), new Tile(3293, 3266,0), new Tile(3293, 3271,0), new Tile(3294, 3300,0)};
                        public final Tile SILVERMINE_TO_ALKBANK[];
                        public final Tile DRAYBANK_TO_DRAYMINE[] = {new Tile(3092, 3243,0), new Tile(3095, 3247,0), new Tile(3098, 3243,0), new Tile(3098, 3238,0), new Tile(3099, 3233,0), new Tile(3102, 3229,0), new Tile(3106, 3226,0), new Tile(3110, 3223,0), new Tile(3110, 3217,0), new Tile(3113, 3213,0), new Tile(3118, 3213,0), new Tile(3122, 3210,0), new Tile(3126, 3206,0), new Tile(3131, 3207,0), new Tile(3136, 3204,0), new Tile(3138, 3199,0), new Tile(3138, 3194,0), new Tile(3138, 3189,0), new Tile(3142, 3185,0), new Tile(3142, 3180,0), new Tile(3145, 3175,0), new Tile(3148, 3170,0), new Tile(3148, 3165,0), new Tile(3150, 3160,0), new Tile(3150, 3155,0), new Tile(3151, 3150,0), new Tile(3146, 3149,0)};
                        public final Tile DRAYMINE_TO_DRAYBANK[];

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
                                                                }
                                ));
                }
           
                public Miner(){
                        CLAYMINE_TO_WESTBANK = reversePath(WESTBANK_TO_CLAYMINE);
                        IRONMINE_TO_WESTBANK = reversePath(WESTBANK_TO_IRONMINE);
                        TINMINE_TO_WESTBANK = reversePath(WESTBANK_TO_TINMINE);
                        SILVERMINE_TO_WESTBANK = reversePath(WESTBANK_TO_SILVERMINE);
                        IRONMINE_TO_EASTBANK = reversePath(EASTBANK_TO_IRONMINE);
                        COPPERMINE_TO_EASTBANK = reversePath(EASTBANK_TO_COPPERMINE);
                        TINMINE_TO_EASTBANK = reversePath(EASTBANK_TO_TINMINE);
                        IRONMINE_TO_FALLYBANK = reversePath(FALLYBANK_TO_IRONMINE);
                        CLAYMINE_TO_FALLYBANK = reversePath(FALLYBANK_TO_CLAYMINE);
                        COPPERMINE_TO_FALLYBANK = reversePath(FALLYBANK_TO_COPPERMINE);
                        TINMINE_TO_FALLYBANK = reversePath(FALLYBANK_TO_TINMINE);
                        GOLDMINE_TO_FALLYBANK = reversePath(FALLYBANK_TO_GOLDMINE);
                        MINE_TO_ALKBANK = reversePath(ALKBANK_TO_MINE);
                        IRONMINE_TO_ALKBANK = reversePath(ALKBANK_TO_IRONMINE);
                        GOLDMINE_TO_ALKBANK = reversePath(ALKBANK_TO_GOLDMINE);
                        SILVERMINE_TO_ALKBANK = reversePath(ALKBANK_TO_SILVERMINE);
                        DRAYMINE_TO_DRAYBANK = reversePath(DRAYBANK_TO_DRAYMINE);
                        startTime = System.currentTimeMillis();
                        ORES = 0;
                        DropOres = 0;
                        currentlyMining = false;
                        checkMiningStat = true;
                        scriptStart = false;
                        ShowOre = 0;
                        ShowLocation = 0;
                        ShowMethod = 0;
                        url = null;
                        in = null;
                        out = null;
                        scriptStarted = false;
                }
           
                private Tile[] reversePath(Tile[] wESTBANK_TO_CLAYMINE2) {
                        // TODO Auto-generated method stub
                        return null;
                }

                public boolean onstart() {
                                return true;
                }
                public boolean GUISettings() {
                        if(Mine.equals("Essence")) {
                                EXP = 5;
                                OreMine1 = essMine;
                        }
                        if(Mine.equals("Clay")) {
                                EXP = 5;
                                OREID = 434;
                                OreMine1 = clayRockID;
                        }
                        if(Mine.equals("Copper")) {
                                EXP = 17;
                                OREID = 436;
                                OreMine1 = copperRockID;
                        }
                        if(Mine.equals("Tin")) {
                                EXP = 17;
                                OREID = 438;
                                OreMine1 = tinRockID;
                        }
                        if(Mine.equals("Iron")) {
                                EXP = 35;
                                OREID = 440;
                                OreMine1 = ironRockID;
                        }
                        if(Mine.equals("Silver")) {
                                EXP = 40;
                                OREID = 442;
                                OreMine1 = silverRockID;
                        }
                        if(Mine.equals("Gold")) {
                                EXP = 65;
                                OREID = 444;
                                OreMine1 = goldRockID;
                        }
                        if(Mine.equals("Coal")) {
                                EXP = 50;
                                OREID = 453;
                                OreMine1 = coalRockID;
                        }
                        if(Mine.equals("Mithril")) {
                                EXP = 80;
                                OREID = 447;
                                OreMine1 = mithrilRockID;
                        }
                        if(Mine.equals("Adamant")){
                                EXP = 95;
                                OREID = 449;
                                OreMine1 = adamantRockID;
                        }
                        return true;
                }
           
                public int loop() {
                                if(!Inventory.isFull()){
                                        Mine(); //Works
                                        if(Inventory.getCount() >= 28)
                                                WalkToBank();
                                        Bank(); { //Works
                                                WalktoMine();
                                                Antiban(); //Works
                                        }
                        }
                                return 0;
                        }
           
                private void Mine() {
                        SceneObject Rockid = SceneEntities.getNearest(ironRockID);
                           if(Rockid != null){
                                        if(Players.getLocal().getAnimation() == -1){
                                         if(Rockid.isOnScreen()){
                                                 Rockid.interact("Mine");
                                          Time.sleep(1500);
                                          while (Players.getLocal().isMoving()) {
                                           Time.sleep(800);
                                          }
                                         }else{
                                          Camera.turnTo(Rockid);
                                         }
                                        }
                           }
                          }

                private void Bank() {
                        SceneObject BankBooth = SceneEntities.getNearest(BANKERS);
                        if(BankBooth!=null){
                        Camera.turnTo(BankBooth);
                        if(BankBooth.isOnScreen()){
                        org.powerbot.game.api.methods.widget.Bank.open();
                        org.powerbot.game.api.methods.widget.Bank.depositInventory();
                        Time.sleep(1500,1700);
                        org.powerbot.game.api.methods.widget.Bank.close();
                        }
                        }
                }

                private void WalkToBank() {
                                if(Location.equals("Varrock-West Mine")) {
                                        if(Mine.equals("Clay")){
                                                Walking.newTilePath(CLAYMINE_TO_WESTBANK).traverse();
                                        }
                                        if(Mine.equals("Tin")) {
                                                Walking.newTilePath(TINMINE_TO_WESTBANK).traverse();
                                        }
                                        if(Mine.equals("Iron")){
                                                Walking.newTilePath(IRONMINE_TO_WESTBANK).traverse();
                                        }
                                        if(Mine.equals("Silver")){
                                                Walking.newTilePath(SILVERMINE_TO_WESTBANK).traverse();
                                        }
                                }
                                if(Location.equals("Varrock-East Mine")){
                                        if(Mine.equals("Iron")){
                                                Walking.newTilePath(IRONMINE_TO_EASTBANK).traverse();
                                        }
                                        if(Mine.equals("Tin")){
                                                Walking.newTilePath(TINMINE_TO_EASTBANK).traverse();
                                        }
                                        if(Mine.equals("Copper")){
                                                Walking.newTilePath(COPPERMINE_TO_EASTBANK).traverse();
                                        }
                                }
                                if(Location.equals("Rimmington Mine")){
                                        if(Mine.equals("Clay")){
                                                Walking.newTilePath(CLAYMINE_TO_FALLYBANK).traverse();
                                        }
                                        if(Mine.equals("Tin")){
                                                Walking.newTilePath(TINMINE_TO_FALLYBANK).traverse();
                                        }
                                        if(Mine.equals("Copper")){
                                                Walking.newTilePath(COPPERMINE_TO_FALLYBANK).traverse();
                                        }
                                        if(Mine.equals("Iron")){
                                                Walking.newTilePath(IRONMINE_TO_FALLYBANK).traverse();
                                        }
                                        if(Mine.equals("Gold")){
                                                Walking.newTilePath(GOLDMINE_TO_FALLYBANK).traverse();
                                        }
                                }
                                if(Location.equals("Al-Kharid Mine")){
                                        if(Mine.equals("Copper")){
                                                Walking.newTilePath(MINE_TO_ALKBANK).traverse();
                                        }
                                        if(Mine.equals("Mithril")){
                                                Walking.newTilePath(MINE_TO_ALKBANK).traverse();
                                        }
                                        if(Mine.equals("Adamant")){
                                                Walking.newTilePath(MINE_TO_ALKBANK).traverse();
                                        }
                                        if(Mine.equals("Coal")){
                                                Walking.newTilePath(MINE_TO_ALKBANK).traverse();
                                        }
                                        if(Mine.equals("Tin")){
                                                Walking.newTilePath(MINE_TO_ALKBANK).traverse();
                                        }
                                        if(Mine.equals("Silver")){
                                                Walking.newTilePath(SILVERMINE_TO_ALKBANK).traverse();
                                        }
                                        if(Mine.equals("Iron")){
                                                Walking.newTilePath(IRONMINE_TO_ALKBANK).traverse();
                                        }
                                        if(Mine.equals("Gold")){
                                                Walking.newTilePath(GOLDMINE_TO_ALKBANK).traverse();
                                        }
                                }
                                if(Location.equals("Draynor Mine")){
                                        Walking.newTilePath(DRAYMINE_TO_DRAYBANK).traverse();
                                }
                        }
           

                private void WalktoMine() {
                                if(Mine.equals("Clay")) {
                                        Walking.newTilePath(WESTBANK_TO_CLAYMINE).traverse();
                                }
                                if(Mine.equals("Tin")){
                                        Walking.newTilePath(WESTBANK_TO_TINMINE).traverse();
                                }
                                if(Mine.equals("Iron")) {
                                        Walking.newTilePath(WESTBANK_TO_IRONMINE).traverse();
                                }
                                if(Mine.equals("Silver")) {
                                        Walking.newTilePath(WESTBANK_TO_SILVERMINE).traverse();
                                }
                                if(Mine.equals("Iron")) {
                                        Walking.newTilePath(EASTBANK_TO_IRONMINE).traverse();
                                }
                                if(Mine.equals("Tin")) {
                                        Walking.newTilePath(EASTBANK_TO_TINMINE).traverse();
                                }
                                if(Mine.equals("Copper")) {
                                        Walking.newTilePath(EASTBANK_TO_COPPERMINE).traverse();
                                }
                                if(Location.equals("Rimmington Mine")) {
                                        if(Mine.equals("Clay")) {
                                                Walking.newTilePath(FALLYBANK_TO_CLAYMINE).traverse();
                                        }
                                        if(Mine.equals("Tin")) {
                                                Walking.newTilePath(FALLYBANK_TO_TINMINE).traverse();
                                        }
                                        if(Mine.equals("Copper")) {
                                                Walking.newTilePath(FALLYBANK_TO_COPPERMINE).traverse();
                                        }
                                        if(Mine.equals("Iron")) {
                                                Walking.newTilePath(FALLYBANK_TO_IRONMINE).traverse();
                                        }
                                        if(Mine.equals("Gold")) {
                                                Walking.newTilePath(FALLYBANK_TO_GOLDMINE).traverse();
                                        }
                                }
                                if(Location.equals("Al-Kharid Mine")) {
                                        if(Mine.equals("Copper")) {
                                                Walking.newTilePath(ALKBANK_TO_MINE).traverse();
                                        }
                                        if(Mine.equals("Mithril")){
                                                Walking.newTilePath(ALKBANK_TO_MINE).traverse();
                                        }
                                        if(Mine.equals("Adamant")){
                                                Walking.newTilePath(ALKBANK_TO_MINE).traverse();
                                        }
                                        if(Mine.equals("Coal")){
                                                Walking.newTilePath(ALKBANK_TO_MINE).traverse();
                                        }
                                        if(Mine.equals("Tin")){
                                                Walking.newTilePath(ALKBANK_TO_MINE).traverse();
                                        }
                                        if(Mine.equals("Silver")){
                                                Walking.newTilePath(ALKBANK_TO_SILVERMINE).traverse();
                                        }
                                        if(Mine.equals("Iron")){
                                                Walking.newTilePath(ALKBANK_TO_IRONMINE).traverse();
                                        }
                                        if(Mine.equals("Gold")){
                                                Walking.newTilePath(ALKBANK_TO_GOLDMINE).traverse();
                                        }
                                }
                                if(Location.equals("Draynor Mine")){
                                        Walking.newTilePath(DRAYBANK_TO_DRAYMINE).traverse();
                                }
                        }
           
                private void Antiban() {
                        int r = 0;
                        switch (r) {
                   
                        case 10:
                                Camera.setPitch(Random.nextInt(1, 86));
                                  Time.sleep(Random.nextInt(97, 186));
                                  break;
                                
                          case 5:
                                  Camera.setAngle(Random.nextInt(1, 196));
                                  Time.sleep(Random.nextInt(97, 186));
                                  break;
                        
                                        
                          case 50:
                                  Tabs.STATS.open();
                                  Time.sleep(Random.nextInt(1567, 2103));
                                
                                  Mouse.move(689, 217);
                                          Time.sleep(Random.nextInt(1031, 2098));
                                        
                                          Tabs.INVENTORY.open();
                                          Time.sleep(Random.nextInt(462, 796));
                                          break;
                                        
                                
                          case 7:
                                  Mouse.move(Random.nextInt(1, 783), Random.nextInt(1, 678));
                                  break;
                                
                          case 20:
                                  Tabs.STATS.open();
                                                                Time.sleep(Random.nextInt(500, 1500));
                                                                Tabs.INVENTORY.open();
                                                                break;
                                                          
                          case 3:
                                  Camera.setAngle(Random.nextInt(-45, 196));
                                  Time.sleep(Random.nextInt(97, 186));
                                  break;
                                
                                
                          case 28:
                                  SceneEntities.getNearest(ironRockID).interact("examine");
                          }
                          
                }
                }
   
  //MouseTrial//
        class MouseTrail {
                private final static int SIZE = 10;

                private Point[] points;
                private int index;

                public MouseTrail() {
                  points = new Point[SIZE];
                  index = 0;
                }

                public void add(Point p) {
                  points[index++] = p;
                  index %= SIZE;
                }

                public void draw(Graphics graphics) {
                  for (int i = index; i != (index == 0 ? SIZE - 1 : index - 1); i = (i + 1)
                          % SIZE) {
                        if (points[i] != null && points[(i + 1) % SIZE] != null) {
                          graphics.setColor(Color.WHITE);
                          graphics.drawLine(points[i].x, points[i].y, points[(i + 1)
                                  % SIZE].x, points[(i + 1) % SIZE].y);
                        }
                  }
                }
          }
