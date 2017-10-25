package name.gmail.nsomlai.coctracker;

import java.io.Serializable;

/**
 * Created by Norbert on 2015.01.29..
 */
public class Wall implements Serializable {
    private static final long serialVersionUID = 1L;

    int count;
    int level;

    public Wall(int count, int level) {
        this.count = count;
        this.level = level;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLevel() {
        return level;
    }
}
