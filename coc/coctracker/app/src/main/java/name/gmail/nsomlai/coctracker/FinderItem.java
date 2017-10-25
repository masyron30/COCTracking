package name.gmail.nsomlai.coctracker;

import android.util.Log;

public class FinderItem implements Comparable<FinderItem> {
    private Building building;
    private int distance;        // distance from current resource level
    private boolean allResourcesAvailable;

    private CostType costType;
    private int cost;
    private int time;

    public FinderItem(Building building, CostType costType, int cost, int time) {
        this.building = building;
        this.costType = costType;
        this.cost = cost;
        this.time = time;
    }

    public void calculateDistance(int currentGold, int currentEx, int currentDex) {
        int currentAmount = 0;
        switch (costType) {
            case GOLD:
                currentAmount = currentGold;
                break;
            case EX:
                currentAmount = currentEx;
                break;
            case DEX:
                currentAmount = currentDex;
                break;
        }
        allResourcesAvailable = (currentAmount >= cost);
        double diffPercent = cost != 0 ? (double)currentAmount / cost : 1.0;
        distance = (int)(diffPercent * 100);
    }

    @Override
    public int compareTo(FinderItem another) {
        return this.distance == another.distance ? 0 : this.distance < another.distance ? -1 : 1;
    }

    public Building getBuilding() {
        return building;
    }

    public int getCostDiff(int currentAmount) {
        return cost - currentAmount;
    }

    public int getTime() {
        return time;
    }

    public boolean isAllResourcesAvailable() {
        return allResourcesAvailable;
    }

    public int getDistance() {
        return distance;
    }

    public CostType getCostType() {
        return costType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof FinderItem)) {
            return false;
        }
        FinderItem fi = (FinderItem)o;
        if (fi.getBuilding().getType() == building.getType() && fi.getBuilding().getLevel() == building.getLevel()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31*hash + getBuilding().getType().hashCode();
        hash = 31*hash + getBuilding().getLevel();
        return hash;
    }
}
