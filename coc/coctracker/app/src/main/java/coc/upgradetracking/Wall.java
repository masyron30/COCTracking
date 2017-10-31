package coc.upgradetracking;

import java.io.Serializable;

/**
 * Created by Norbert on 2015.01.29..
 */
public class Wall implements Serializable {
    private static final long serialVersionUID = 1L;

    int count;
    int level;
    int maxPerTHLevel;

    public Wall(int count, int level, int maxPerTHLevel) {
        this.count = count;
        this.level = level;
        this.maxPerTHLevel = maxPerTHLevel;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        if(count > maxPerTHLevel) {
            count = maxPerTHLevel;
        }
        this.count = count;
    }

    public int getLevel() {
        return level;
    }
}
