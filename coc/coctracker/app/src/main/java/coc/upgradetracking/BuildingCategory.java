package coc.upgradetracking;

/**
 * Created by Norbert on 2015.01.29..
 */
public enum BuildingCategory {
    BASIC(R.string.BC_BASIC,0, R.id.stats_row_basic),
    DEFENSE(R.string.BC_DEFENSE,1, R.id.stats_row_defenses),
    TRAP(R.string.BC_TRAP,2, R.id.stats_row_traps),
    RESOURCE(R.string.BC_RESOURCE,3, R.id.stats_row_resources),
    TROOP(R.string.BC_TROOP, 4, R.id.stats_row_troops),
    HERO(R.string.BC_HERO, 5, R.id.stats_row_heroes),
    SPELL(R.string.BC_SPELL, 6, R.id.stats_row_spells),
    WALL(R.string.BC_WALLS, 7, R.id.stats_row_walls);

    private int name;
    private int index;
    private int tableRowId;

    BuildingCategory(int name, int index, int tableRowId) {
        this.name = name;
        this.index = index;
        this.tableRowId = tableRowId;
    }

    public int getName() {
        return name;
    }

    public int getIndex() { return index; }

    public int getTableRowId() {
        return tableRowId;
    }
}
