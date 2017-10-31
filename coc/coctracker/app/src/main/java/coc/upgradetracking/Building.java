package coc.upgradetracking;

import java.io.Serializable;

/**
 * Created by Norbert on 2015.01.29..
 */
public class Building implements Serializable {
    private static final long serialVersionUID = 1L;

    BuildingType type;
    int level;
    int color = 0x00ffffff;

    public Building(BuildingType type, int level) {
        this.type = type;
        this.level = level;
    }

    public BuildingType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
