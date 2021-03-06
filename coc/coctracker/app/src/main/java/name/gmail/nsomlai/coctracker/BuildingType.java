package name.gmail.nsomlai.coctracker;

import static name.gmail.nsomlai.coctracker.CostType.*;
import static name.gmail.nsomlai.coctracker.BuilderType.*;
import static name.gmail.nsomlai.coctracker.BuildingCategory.*;
import static name.gmail.nsomlai.coctracker.MainActivity.H;
import static name.gmail.nsomlai.coctracker.MainActivity.D;

/**
 * Created by Norbert on 2015.01.29..
 */
public enum BuildingType {
    TOWNHALL(R.string.BN_TOWNHALL, BASIC, GOLD, BUILDER, 1,
            new int[] {1,1,1,1,1,1,1,1,1,1,1},  new int[] {11,11,11,11,11,11,11,11,11,11,11},
            new int[] {0,1000,4000,25000,150000,750000,1200000,2000000,3000000,5000000,7000000},
            new int[] {0,1,3*H,D,2*D,4*D,6*D,8*D,10*D,12*D,14*D}),
    CLANCASTLE(R.string.BN_CLANCASTLE, BASIC, GOLD, BUILDER, 0,
            new int[] {0,0,1,1,1,1,1,1,1,1,1}, new int[] {0,0,1,2,2,3,3,4,5,6,7},
            new int[] {10000,100000,800000,1800000,4000000,7000000,10000000},
            new int[] {0,6*H,D,2*D,6*D,10*D,14*D}),
    ARMYCAMP(R.string.BN_ARMYCAMP, BASIC, EX, BUILDER, 0,
            new int[] {1,1,2,2,3,3,4,4,4,4,4}, new int[] {1,2,3,4,5,6,6,6,7,8,8},
            new int[] {250,2500,10000,100000,250000,750000,2250000,6750000},
            new int[] {5,1*H,3*H,8*H,1*D,3*D,5*D,10*D}),
    BARRACKS(R.string.BN_BARRACKS, BASIC, EX, BUILDER, 0,
            new int[] {1,2,2,3,3,3,4,4,4,4,4}, new int[] {3,4,5,6,7,8,9,10,11,12,12},
            new int[] {200,1000,2500,5000,10000,80000,240000,700000,1500000,2000000,3000000,4000000},
            new int[] {1,15,2*H,4*H,10*H,16*H,1*D,2*D,4*D,6*D,8*D,10*D}),
    DARKBARRACKS(R.string.BN_DARKBARRACKS, BASIC, EX, BUILDER, 0,
            new int[] {0,0,0,0,0,0,1,2,2,2,2}, new int[] {0,0,0,0,0,0,2,4,6,7,7},
            new int[] {750000,1250000,1750000,2250000,2750000,3500000,6000000},
            new int[] {3*D,5*D,6*D,7*D,8*D,9*D,12*D}),
    LABORATORY(R.string.BN_LABORATORY, BASIC, EX, BUILDER, 0,
            new int[] {0,0,1,1,1,1,1,1,1,1,1}, new int[] {0,0,1,2,3,4,5,6,7,8,9},
            new int[] {25000,50000,90000,270000,500000,1000000,2500000,4000000,6000000},
            new int[] {30,5*H,12*H,D,2*D,4*D,5*D,6*D,7*D}),
    SPELLFACTORY(R.string.BN_SPELLFACTORY, BASIC, EX, BUILDER, 0,
            new int[] {0,0,0,0,1,1,1,1,1,1,1}, new int[] {0,0,0,0,1,2,3,3,4,5,5},
            new int[] {200000,400000,800000,1600000,3200000},
            new int[] {D,2*D,4*D,5*D,6*D}),
    DARKSPELLFACTORY(R.string.BN_DARKSPELLFACTORY, BASIC, EX, BUILDER, 0,
            new int[] {0,0,0,0,0,0,0,1,1,1,1}, new int[] {0,0,0,0,0,0,0,2,4,4,4},
            new int[] {1500000, 2500000, 3500000, 4500000},
            new int[] {4*D,6*D,8*D,10*D}),
    CANNON(R.string.BN_CANNON, DEFENSE, GOLD, BUILDER, 0,
            new int[] {2,2,2,2,3,3,5,5,5,6,7},  new int[] {2,3,4,5,6,7,8,10,11,13,14},
            new int[] {250,1000,4000,16000,50000,100000,200000,400000,800000,1600000,3200000,5000000,6500000,8500000},
            new int[] {1,15,45,2*H,6*H,12*H,D,2*D,3*D,4*D,5*D,6*D,7*D,8*D}),
    ARCHERTOWER(R.string.BN_ARCHERTOWER, DEFENSE, GOLD, BUILDER, 0,
            new int[] {0,1,1,2,3,3,4,5,6,7,8}, new int[] {0,2,3,4,6,7,8,10,11,13,14},
            new int[] {1000,2000,5000,20000,80000,180000,360000,720000,1500000,2500000,3800000,5500000,7000000,9000000},
            new int[] {1,30,45,4*H,12*H,D,2*D,3*D,4*D,5*D,6*D,7*D,8*D,9*D}),
    MORTAR(R.string.BN_MORTAR, DEFENSE, GOLD, BUILDER, 0,
            new int[] {0,0,1,1,1,2,3,4,4,4,4}, new int[] {0,0,1,2,3,4,5,6,7,8,10},
            new int[] {8000,32000,120000,400000,800000,1600000,3200000,5000000,7000000,9000000},
            new int[] {8*H,12*H,D,2*D,4*D,5*D,7*D,8*D,10*D,12*D}),
    AIRDEFENSE(R.string.BN_AIRDEFENSE, DEFENSE, GOLD, BUILDER, 0,
            new int[] {0,0,0,1,1,2,3,3,4,4,4}, new int[] {0,0,0,2,3,4,5,6,7,8,9},
            new int[] {22500,90000,270000,500000,1000000,2000000,4000000,6300000,8800000},
            new int[] {5*H,D,3*D,4*D,5*D,6*D,8*D,10*D,12*D}),
    WIZARDTOWER(R.string.BN_WIZARDTOWER, DEFENSE, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,1,2,2,3,4,4,5}, new int[] {0,0,0,0,2,3,4,6,7,9,10},
            new int[] {180000,360000,720000,1200000,1700000,2200000,3700000,5200000,7200000,9200000},
            new int[] {12*H,D,2*D,3*D,4*D,5*D,6*D,8*D,10*D,12*D}),
    AIRSWEEPER(R.string.BN_AIRSWEEPER, DEFENSE, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,0,1,1,1,2,2,2}, new int[] {0,0,0,0,0,2,3,4,5,6,6},
            new int[] {500000,750000,1250000,2400000,4800000,7200000},
            new int[] {D,3*D,5*D,7*D,8*D,9*D}),
    HIDDENTESLA(R.string.BN_HIDDENTESLA, DEFENSE, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,0,0,2,3,4,4,4}, new int[] {0,0,0,0,0,0,3,6,7,8,9},
            new int[] {1000000,1250000,1500000,2000000,2500000,3000000,3500000,5000000,8000000},
            new int[] {D,3*D,5*D,6*D,7*D,8*D,10*D,12*D,14*D}),
    BOMBTOWER(R.string.BN_BOMBTOWER, DEFENSE, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,0,0,0,1,1,2,2}, new int[] {0,0,0,0,0,0,0,2,3,4,5},
            new int[] {2000000,3500000,5000000,7000000,9000000},
            new int[] {4*D,6*D,8*D,10*D,14*D}),
    XBOW(R.string.BN_XBOW, DEFENSE, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,0,0,0,0,2,3,4}, new int[] {0,0,0,0,0,0,0,0,3,4,5},
            new int[] {3000000,4000000,5000000,7000000,9500000},
            new int[] {7*D,8*D,10*D,12*D,14*D}),
    INFERNOTOWER(R.string.BN_INFERNOTOWER, DEFENSE, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,0,0,0,0,0,2,2}, new int[] {0,0,0,0,0,0,0,0,0,3,4},
            new int[] {5000000,6500000,8000000,10000000},
            new int[] {7*D,10*D,14*D,14*D}),
    EAGLEARTILLERY(R.string.BN_EAGLEARTILLERY, DEFENSE, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,0,0,0,0,0,0,1}, new int[] {0,0,0,0,0,0,0,0,0,0,2},
            new int[] {8000000, 10000000},
            new int[] {10*D,14*D}),
    BOMB(R.string.BN_BOMB, TRAP, GOLD, BUILDER, 0,
            new int[] {0,0,2,2,4,4,6,6,6,6,6}, new int[] {0,0,2,2,3,3,4,5,6,6,6},
            new int[] {400,1000,10000,100000,1000000,1500000},
            new int[] {0,15,2*H,8*H,D,2*D}),
    SPRINGTRAP(R.string.BN_SPRINGTRAP, TRAP, GOLD, BUILDER, 0,
            new int[] {0,0,0,2,2,4,4,6,6,6,6}, new int[] {0,0,0,1,1,1,2,3,4,5,5},
            new int[] {2000,500000,1000000,1500000,2000000},
            new int[] {0,16*H,1*D,2*D,3*D}),
    GIANTBOMB(R.string.BN_GIANTBOMB, TRAP, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,0,1,2,3,4,5,5}, new int[] {0,0,0,0,0,2,2,3,3,4,4},
            new int[] {12500,75000,750000,2500000},
            new int[] {0,6*H,D,3*D}),
    AIRBOMB(R.string.BN_AIRBOMB, TRAP, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,2,2,2,4,4,5,5}, new int[] {0,0,0,0,2,2,3,3,4,4,4},
            new int[] {4000,20000,200000,1500000},
            new int[] {0,4*H,12*H,D}),
    SEEKINGAIRMINE(R.string.BN_SEEKINGAIRMINE, TRAP, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,0,0,1,2,4,5,5}, new int[] {0,0,0,0,0,0,1,1,2,3,3},
            new int[] {15000,2000000,4000000},
            new int[] {0,D,3*D}),
    SKELETONTRAP(R.string.BN_SKELETONTRAP, TRAP, GOLD, BUILDER, 0,
            new int[] {0,0,0,0,0,0,0,2,2,3,3}, new int[] {0,0,0,0,0,0,0,2,3,3,3},
            new int[] {6000,600000,1300000},
            new int[] {0,6*H,D}),
    GOLDMINE(R.string.BN_GOLDMINE, RESOURCE, EX, BUILDER, 0,
            new int[] {1,2,3,4,5,6,6,6,6,7,7}, new int[] {2,4,6,8,10,10,11,12,12,12,12},
            new int[] {150,300,700,1400,3000,7000,14000,28000,56000,84000,168000,336000},
            new int[] {1,1,15,H,2*H,6*H,12*H,D,2*D,3*D,4*D,5*D}),
    ELIXIRCOLLECTOR(R.string.BN_ELIXIRCOLLECTOR, RESOURCE, GOLD, BUILDER, 0,
            new int[] {1,2,3,4,5,6,6,6,6,7,7}, new int[] {2,4,6,8,10,10,11,12,12,12,12},
            new int[] {150,300,700,1400,3500,7000,14000,28000,56000,84000,168000,336000},
            new int[] {1,1,15,H,2*H,6*H,12*H,D,2*D,3*D,4*D,5*D}),
    DARKELIXIRDRILL(R.string.BN_DARKELIXIRDRILL, RESOURCE, EX, BUILDER, 0,
            new int[] {0,0,0,0,0,0,1,2,2,3,3}, new int[] {0,0,0,0,0,0,3,3,6,6,6},
            new int[] {1000000,1500000,2000000,3000000,4000000,5000000},
            new int[] {D,2*D,3*D,4*D,6*D,8*D}),
    GOLDSTORAGE(R.string.BN_GOLDSTORAGE, RESOURCE, EX, BUILDER, 0,
            new int[] {1,1,2,2,2,2,2,3,4,4,4}, new int[] {1,3,6,8,9,10,11,11,11,11,12},
            new int[] {300,750,1500,3000,6000,12000,25000,50000,100000,250000,500000,2500000},
            new int[] {1,30,H,2*H,3*H,4*H,6*H,8*H,12*H,D,2*D,7*D}),
    ELIXIRSTORAGE(R.string.BN_ELIXIRSTORAGE, RESOURCE, GOLD, BUILDER, 0,
            new int[] {1,1,2,2,2,2,2,3,4,4,4}, new int[] {1,3,6,8,9,10,11,11,11,11,12},
            new int[] {300,750,1500,3000,6000,12000,25000,50000,100000,250000,500000,2500000},
            new int[] {1,30,H,2*H,3*H,4*H,6*H,8*H,12*H,D,2*D,7*D}),
    DARKELIXIRSTORAGE(R.string.BN_DARKELIXIRSTORAGE, RESOURCE, EX, BUILDER, 0,
            new int[] {0,0,0,0,0,0,1,1,1,1,1}, new int[] {0,0,0,0,0,0,2,4,6,6,6},
            new int[] {600000,1200000,1800000,2400000,3000000,3600000},
            new int[] {D,2*D,3*D,4*D,5*D,6*D}),
    BARBKING(R.string.BN_BARBKING, HERO, DEX, BUILDER, 0,
            new int[] {0,0,0,0,0,0,1,1,1,1,1}, new int[] {0,0,0,0,0,0,5,10,30,40,45},
            new int[] {10000,12500,15000,17500,20000,22500,25000,30000,35000,40000,45000,50000,55000,60000,65000,70000,75000,80000,85000,90000,95000,100000,105000,110000,115000,120000,125000,130000,135000,140000,144000,148000,152000,156000,160000,164000,168000,172000,176000,180000,185000,188000,191000,194000,197000},
            new int[] {0,12*H,D,36*H,2*D,2*D+12*H,3*D,3*D+12*H,4*D,4*D+12*H,5*D,5*D+12*H,6*D,6*D+12*H,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D}),
    ARCHERQUEEN(R.string.BN_ARCHERQUEEN, HERO, DEX, BUILDER, 0,
            new int[] {0,0,0,0,0,0,0,0,1,1,1}, new int[] {0,0,0,0,0,0,0,0,30,40,45},
            new int[] {40000,22500,25000,27500,30000,32500,35000,40000,45000,50000,55000,60000,65000,70000,75000,80000,85000,90000,95000,100000,105000,110000,115000,120000,125000,129000,133000,137000,141000,145000,149000,153000,157000,161000,165000,169000,173000,177000,181000,185000,190000,192000,194000,196000,198000},
            new int[] {0,12*H,D,36*H,2*D,2*D+12*H,3*D,3*D+12*H,4*D,4*D+12*H,5*D,5*D+12*H,6*D,6*D+12*H,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D,7*D}),
    GRANDWARDEN(R.string.BN_GRANDWARDEN, HERO, EX, BUILDER, 0,
            new int[] {0,0,0,0,0,0,0,0,0,0,1}, new int[] {0,0,0,0,0,0,0,0,0,0,20},
            new int[] {6000000,2500000,3000000,3500000,4000000,4500000,5000000,5500000,6000000,6500000,7000000,7500000,8000000,8400000,8800000,9100000,9400000,9600000,9800000,10000000},
            new int[] {0,12*H,D,36*H,2*D,2*D+12*H,3*D,3*D+12*H,4*D,4*D+12*H,5*D,5*D+12*H,6*D,6*D+12*H,7*D,7*D,7*D,7*D,7*D,7*D}),
    LIGHTNING(R.string.BN_LIGHTNING, SPELL, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,1,1,1,1,1,1,1}, new int[] {0,0,0,0,4,4,4,5,6,7,7},
            new int[] {0,200000,500000,1000000,2000000,6000000,8000000}, new int[] {0,1*D,2*D,3*D,4*D,10*D,14*D}),
    HEALING(R.string.BN_HEALING, SPELL, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,1,1,1,1,1,1}, new int[] {0,0,0,0,0,3,4,5,6,6,6},
            new int[] {0,300000,600000,1200000,2400000,4800000},
            new int[] {0,D,2*D,3*D,5*D,7*D}),
    RAGE(R.string.BN_RAGE, SPELL, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,1,1,1,1,1}, new int[] {0,0,0,0,0,0,4,5,5,5,5},
            new int[] {0,450000,900000,1800000,3000000},
            new int[] {0,2*D,3*D,5*D,7*D}),
    JUMP(R.string.BN_JUMP, SPELL, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,0,1,1,1}, new int[] {0,0,0,0,0,0,0,0,2,3,3},
            new int[] {0,3000000,6000000}, new int[] {0,5*D,7*D}),
    FREEZE(R.string.BN_FREEZE, SPELL, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,0,1,1,1}, new int[] {0,0,0,0,0,0,0,0,1,5,5},
            new int[] {0,4000000,5000000,6500000,8000000},
            new int[] {0,5*D,7*D,10*D,14*D}),
    POISON(R.string.BN_POISON, SPELL, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,1,1,1,1}, new int[] {0,0,0,0,0,0,0,2,3,4,5},
            new int[] {0,25000,50000,75000,150000},
            new int[] {0,4*D,6*D,10*D,12*D}),
    EARTHQUAKE(R.string.BN_EARTHQUAKE, SPELL, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,1,1,1,1}, new int[] {0,0,0,0,0,0,0,2,3,4,4},
            new int[] {0,30000,60000,90000},
            new int[] {0,6*D,8*D,12*D}),
    CLONE(R.string.BN_CLONESPELL, SPELL, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,0,0,1,1}, new int[] {0,0,0,0,0,0,0,0,0,2,4},
            new int[] {0,5000000,7000000,9000000},
            new int[] {0,8*D,10*D,14*D}),
    HASTE(R.string.BN_HASTE, SPELL, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,0,1,1,1}, new int[] {0,0,0,0,0,0,0,0,2,4,4},
            new int[] {0,40000,80000,100000},
            new int[] {0,8*D,10*D,14*D}),
    SKELETON(R.string.BN_SKELETONSPELL, SPELL, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,0,1,1,1}, new int[] {0,0,0,0,0,0,0,0,1,3,4},
            new int[] {0,50000,75000,100000},
            new int[] {0,8*D,10*D,12*D}),
    BARBARIAN(R.string.BN_BARBARIAN, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,1,1,1,1,1,1,1,1,1}, new int [] {1,1,2,2,3,3,4,5,6,7,7},
            new int[] {0,50000,150000,500000,1500000,4500000,6000000}, new int[] {0,6*H,D,3*D,5*D,10*D,14*D}),
    ARCHER(R.string.BN_ARCHER, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,1,1,1,1,1,1,1,1,1}, new int [] {1,1,2,2,3,3,4,5,6,7,7},
            new int[] {0,50000,250000,750000,2250000,6000000,7500000}, new int[] {0,12*H,2*D,3*D,5*D,10*D,14*D}),
    GOBLIN(R.string.BN_GOBLIN, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,1,1,1,1,1,1,1,1,1}, new int [] {1,1,2,2,3,3,4,5,6,7,7},
            new int[] {0,50000,250000,750000,2250000,4500000,6750000}, new int[] {0,12*H,2*D,3*D,5*D,8*D,10*D}),
    GIANT(R.string.BN_GIANT, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,1,1,1,1,1,1,1,1}, new int [] {0,0,0,2,2,3,4,5,6,7,8},
            new int[] {0,100000,250000,750000,2250000,5000000,6000000,9500000}, new int[] {0,D,2*D,3*D,5*D,10*D,12*D,14*D}),
    WALLBREAKER(R.string.BN_WALLBREAKER, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,1,1,1,1,1,1,1,1}, new int [] {0,0,0,2,2,3,4,5,5,6,6},
            new int[] {0,100000,250000,750000,2250000,6750000}, new int[] {0,D,2*D,3*D,5*D,10*D}),
    BALLOON(R.string.BN_BALLOON, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,1,1,1,1,1,1,1,1}, new int [] {0,0,0,2,2,3,4,5,6,6,7},
            new int[] {0,150000,450000,1350000,2500000,6000000,9500000}, new int[] {0,D,2*D,3*D,5*D,10*D,14*D}),
    WIZARD(R.string.BN_WIZARD, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,1,1,1,1,1,1,1}, new int [] {0,0,0,0,2,3,4,5,6,7,7},
            new int[] {0,150000,450000,1350000,2500000,5000000,8500000}, new int[] {0,D,2*D,3*D,5*D,10*D,14*D}),
    HEALER(R.string.BN_HEALER, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,1,1,1,1,1,1}, new int [] {0,0,0,0,0,1,2,3,4,4,4},
            new int[] {0,750000,1500000,3000000}, new int[] {0,3*D,5*D,7*D}),
    DRAGON(R.string.BN_DRAGON, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,1,1,1,1,1}, new int [] {0,0,0,0,0,0,2,3,4,5,6},
            new int[] {0,2000000,3000000,5000000,7000000,9000000}, new int[] {0,7*D,8*D,10*D,12*D,14*D}),
    BABYDRAGON(R.string.BN_BABYDRAGON, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,0,1,1,1}, new int[] {0,0,0,0,0,0,0,0,2,4,5},
            new int[] {0,5000000,6000000,7000000,8000000}, new int[] {0,8*D,10*D,12*D,14*D}),
    MINER(R.string.BN_MINER, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,0,0,1,1}, new int[] {0,0,0,0,0,0,0,0,0,2,4},
            new int[] {0,7500000,8500000,9500000}, new int[] {0,10*D,12*D,14*D}),
    PEKKA(R.string.BN_PEKKA, TROOP, EX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,1,1,1,1}, new int [] {0,0,0,0,0,0,0,3,3,5,5},
            new int[] {0,3000000,6000000,7000000,8000000}, new int[] {0,10*D,12*D,14*D,14*D}),
    MINION(R.string.BN_MINION, TROOP, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,1,1,1,1,1}, new int[] {0,0,0,0,0,0,2,4,5,6,7},
            new int[] {0,10000,20000,30000,40000,100000,140000},
            new int[] {0,5*D,6*D,7*D,10*D,12*D,14*D}),
    HOGRIDER(R.string.BN_HOGRIDER, TROOP, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,1,1,1,1,1}, new int[] {0,0,0,0,0,0,2,4,5,6,7},
            new int[] {0,20000,30000,40000,50000,100000,150000},
            new int[] {0,5*D,6*D,8*D,10*D,12*D,14*D}),
    VALKYRIE(R.string.BN_VALKYRIE, TROOP, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,1,1,1,1}, new int[] {0,0,0,0,0,0,0,2,4,5,5},
            new int[] {0,50000,60000,70000,110000},
            new int[] {0,8*D,10*D,12*D,14*D}),
    GOLEM(R.string.BN_GOLEM, TROOP, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,1,1,1,1}, new int[] {0,0,0,0,0,0,0,2,4,5,6},
            new int[] {0,60000,70000,80000,90000,200000},
            new int[] {0,6*D,8*D,10*D,12*D,14*D}),
    WITCH(R.string.BN_WITCH, TROOP, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,0,1,1,1}, new int[] {0,0,0,0,0,0,0,0,2,2,3},
            new int[] {0,75000,160000},
            new int[] {0,10*D,14*D}),
    LAVAHOUND(R.string.BN_LAVAHOUND, TROOP, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,0,1,1,1}, new int[] {0,0,0,0,0,0,0,0,2,3,4},
            new int[] {0,60000,70000,150000},
            new int[] {0,10*D,12*D,14*D}),
    BOWLER(R.string.BN_BOWLER, TROOP, DEX, BuilderType.LABORATORY, 1,
            new int[] {0,0,0,0,0,0,0,0,0,1,1}, new int[] {0,0,0,0,0,0,0,0,0,2,3},
            new int[] {0,120000,200000},
            new int[] {0,10*D,14*D}),
    WALL(R.string.BN_WALL, BuildingCategory.WALL, GOLD, BuilderType.BUILDER, 1,
            new int[] {0,25,50,75,100,125,175,225,250,275,300}, new int[] {0,2,3,4,5,6,7,8,10,11,12},
            new int[] {50,1000,5000,10000,30000,75000,200000,500000,1000000,2000000,3000000,4000000},
            new int[] {0,0,0,0,0,0,0,0,0,0,0,0});

    private int name;
    private BuildingCategory category;
    private CostType costType;
    private BuilderType builderType;
    private int startingLevel;
    private int[] maxCountPerThLevel;
    private int[] maxLevelPerThLevel;
    private int[] costPerLevel;
    private int[] timePerLevel; // minutes

    BuildingType(int name, BuildingCategory category, CostType costType, BuilderType builderType, int startingLevel,
                 int[] maxCountPerThLevel, int[] maxLevelPerThLevel,
                 int[] costPerLevel, int[] timePerLevel) {
        this.name = name;
        this.category = category;
        this.costType = costType;
        this.builderType = builderType;
        this.startingLevel = startingLevel;
        this.maxLevelPerThLevel = maxLevelPerThLevel;
        this.maxCountPerThLevel = maxCountPerThLevel;
        this.costPerLevel = costPerLevel;
        this.timePerLevel = timePerLevel;
    }

    public BuildingCategory getCategory() {
        return category;
    }

    public int getMaxCountForThLevel(int level) {
        return maxCountPerThLevel[level-1];
    }

    public int getMaxLevelForThLevel(int level) {
        return maxLevelPerThLevel[level-1];
    }

    public int getCostForLevel(int level) {
        return costPerLevel[level-1];
    }

    public int getTimeForLevel(int level) {
        return timePerLevel[level-1];
    }

    public int getName() {
        return name;
    }

    public CostType getCostType() {
        return costType;
    }

    public BuilderType getBuilderType() {
        return builderType;
    }

    public int getStartingLevel() {
        return startingLevel;
    }
}
