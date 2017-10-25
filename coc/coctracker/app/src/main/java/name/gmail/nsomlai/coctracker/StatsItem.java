package name.gmail.nsomlai.coctracker;

/**
 * Created by Norbert on 2015.02.01..
 */
public class StatsItem {
    BuildingCategory buildingCategory;
    int name;
    long goldCost;
    long elixirCost;
    long darkCost;
    int buildTime;
    int labTime;

    public BuildingCategory getBuildingCategory() {
        return buildingCategory;
    }

    public void setBuildingCategory(BuildingCategory buildingCategory) {
        this.buildingCategory = buildingCategory;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public long getGoldCost() {
        return goldCost;
    }

    public void setGoldCost(int goldCost) {
        this.goldCost = goldCost;
    }

    public long getElixirCost() {
        return elixirCost;
    }

    public void setElixirCost(int elixirCost) {
        this.elixirCost = elixirCost;
    }

    public long getDarkCost() {
        return darkCost;
    }

    public void setDarkCost(int darkCost) {
        this.darkCost = darkCost;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(int buildTime) {
        this.buildTime = buildTime;
    }

    public int getLabTime() {
        return labTime;
    }

    public void setLabTime(int labTime) {
        this.labTime = labTime;
    }

    public void addCost(CostType costType, long amount) {
        switch (costType) {
            case GOLD:
                goldCost += amount;
                break;
            case EX:
                elixirCost += amount;
                break;
            case DEX:
            default:
                darkCost += amount;
                break;
        }
    }

    public void addBuildTime(int amount) {
        buildTime += amount;
    }

    public void addLabTime(int amount) {
        labTime += amount;
    }

}
