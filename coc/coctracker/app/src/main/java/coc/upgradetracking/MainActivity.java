package coc.upgradetracking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class MainActivity extends Activity {
    public static final int H = 60;
    public static final int D = 1440;
    public static final int MAX_CATEGORIES = BuildingCategory.values().length;
    public static final int ELIXIR_WALL_LEVEL = 9;      // walls can be built from elixir from this level
    public static final int MAX_BUILDER_COUNT = 5;
    public static final int MAX_HERO_LEVEL = 45;
    public static final int MAX_WALL_LEVEL = 12;
    public static final int MAX_VILLAGENAME = 15;
    public static final int MAX_NUMOFMAXWALLS = 100;

    protected int builderCount = MAX_BUILDER_COUNT;
    protected int heroMaxLevel = MAX_HERO_LEVEL;
    protected int wallMaxLevel = MAX_WALL_LEVEL;
    Menu mainMenu = null;
    ArrayList<Building>[] buildingData = new ArrayList[10];  // room for some extra categories
    ArrayList<Wall> wallData = new ArrayList<Wall>();
    ArrayList<StatsItem> statsData = new ArrayList<StatsItem>();
    BuildingPageAdapter pageAdapter;
    GridView view;
    int thLevel;
    boolean firstRunThisVersion = false;
    int currentVillage = 1;

    public MainActivity() {
        initData();
    }

    void initData() {
        for (int i=0; i< MAX_CATEGORIES; i++) {
            buildingData[i] = new ArrayList<Building>();
        }
        buildingData[0].add(new Building(BuildingType.TOWNHALL, BuildingType.TOWNHALL.getStartingLevel()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ViewConfiguration config = ViewConfiguration.get((Context)this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean((Object)config, false);
            }
        }
        catch (Exception e) {}
        setContentView(R.layout.activity_main);

        pageAdapter = new BuildingPageAdapter(getFragmentManager(), this);
        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setOffscreenPageLimit(12);
        pager.setAdapter(pageAdapter);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabs.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
        tabs.setViewPager(pager);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        currentVillage = prefs.getInt("currentVillage", 1);
        int versionCode = 0;
        try {
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {}
        int versionRun = prefs.getInt("versionRun", 0);
        if (versionRun < versionCode) {
            firstRunThisVersion = true;
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.new_version_title));
            alertDialog.setMessage(Html.fromHtml(getString(R.string.new_version_msg)));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }
        prefs.edit().putInt("versionRun", versionCode).commit();
        if (!loadData()) {
            initData();
            saveData();
        }
        updateBuildingLists();
        pageAdapter.notifyAllFragments();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    int findThLevel() {
        for (int i = 0; i < this.buildingData[0].size(); ++i) {
            if (this.buildingData[0].get(i).getType() != BuildingType.TOWNHALL) continue;
            return this.buildingData[0].get(i).getLevel();
        }
        return -1;
    }

    public void clickBuildingButton(int categoryIndex, Integer position, int direction) {
        thLevel = findThLevel();
        Building b = buildingData[categoryIndex].get(position);
        if (direction == 1) {
            if (b.getLevel() < b.getType().getMaxLevelForThLevel(thLevel)) {
                b.setLevel(b.getLevel() + 1);
            }
        } else if (direction == -1) {
            if (b.getLevel() > b.getType().getStartingLevel()) {
                b.setLevel(b.getLevel() - 1);
            }
        } else if (direction == 1000) {
            b.setLevel(b.getType().getMaxLevelForThLevel(thLevel));
        } else if (direction == -1000) {
            b.setLevel(b.getType().getStartingLevel());
        }
        if (b.getType() == BuildingType.TOWNHALL) {
            updateBuildingLists();
            pageAdapter.notifyAllFragments();
        }
    }

    public void clickWallButton(Integer position, int direction) {
        thLevel = findThLevel();
        Wall w = getWallData().get(position);
        int newCount = w.getCount();
        if (direction > 0) {
            newCount += direction;
            if (getWallCount(position) + newCount > BuildingType.WALL.getMaxCountForThLevel(thLevel)) {
                newCount = BuildingType.WALL.getMaxCountForThLevel(thLevel) - getWallCount(position);
            }
        } else if (direction < 0) {
            newCount += direction;
            if (newCount < 0) {
                newCount = 0;
            }
        }
        w.setCount(newCount);
    }

    void updateBuildingLists() {
        thLevel = findThLevel();
        for (BuildingCategory bc : BuildingCategory.values()) {
            if (bc != BuildingCategory.WALL) {
                for (BuildingType bt : BuildingType.values()) {
                    if (bt != BuildingType.TOWNHALL && bt.getCategory() == bc) {
                        updateBuildingTypeCount(bc.getIndex(), thLevel, bt);
                    }
                }
                for (BuildingType bt : BuildingType.values()) {
                    if (bt != BuildingType.TOWNHALL && bt.getCategory() == bc) {
                        updateBuildingTypeMaxLevel(bc.getIndex(), thLevel, bt);
                    }
                }
            } else {
                updateWalls(thLevel);
            }
        }
    }

    void updateWalls(int thLevel) {
        // add/remove wall levels
        int maxWallLevels = BuildingType.WALL.getMaxLevelForThLevel(thLevel);
        int maxWallCount = BuildingType.WALL.getMaxCountForThLevel(thLevel);
        if (wallData.size() > maxWallLevels) {
            wallData.subList(maxWallLevels, wallData.size()).clear();
        } else if (wallData.size() < maxWallLevels) {
            int wallLevel = wallData.size() + 1;
            while (wallLevel <= maxWallLevels) {
                if(wallLevel == MAX_WALL_LEVEL){
                    maxWallCount = MAX_NUMOFMAXWALLS;
                }
                wallData.add(new Wall(0, wallLevel, maxWallCount));
                wallLevel++;
            }
        }
        // fix total wall count if too many
        if (wallData.size() > 0) {
            //int maxWallCount = BuildingType.WALL.getMaxCountForThLevel(thLevel);
            int wallCount = getWallCount();
            if (wallCount > maxWallCount) {
                int diff = wallCount - maxWallCount;
                for (int i = wallData.size() - 1; i >= 0; i--) {
                    Wall w = wallData.get(i);
                    int count = w.getCount();
                    if (count >= diff) {
                        w.setCount(count - diff);
                        diff = 0;
                    } else {
                        w.setCount(0);
                        diff -= count;
                    }
                    if (diff == 0) break;
                }
            }
        }
    }

    int getWallCount() {
        int count = 0;
        for (Wall w : wallData) {
            count += w.getCount();
        }
        return count;
    }
    int getMaxLevelWallCount() {
        int count = 0;
        for (Wall w : wallData) {
            if(w.getLevel() == MAX_WALL_LEVEL) {
                count += w.getCount();
            }
        }
        return count;
    }
    int getWallCount(int excludePosition) {
        return getWallCount() - wallData.get(excludePosition).getCount();
    }

    void updateBuildingTypeCount(int categoryIndex, int thLevel, BuildingType bt) {
        int count = countBuildingType(categoryIndex, bt);
        int addCount = bt.getMaxCountForThLevel(thLevel) - count;
        if (addCount > 0) {
            int lastIndex = findSmallestLevel(categoryIndex, bt);
            lastIndex = lastIndex != -1 ? lastIndex : buildingData[categoryIndex].size() - 1;
            for (int i=0;i<addCount;i++) {
                buildingData[categoryIndex].add(lastIndex + 1, new Building(bt, bt.getStartingLevel()));
            }
        } else if (addCount < 0) { // remove
            while (addCount != 0) {
                int lastIndex = findSmallestLevel(categoryIndex, bt);
                buildingData[categoryIndex].remove(lastIndex);
                addCount++;
            }
        }
    }

    void updateBuildingTypeMaxLevel(int categoryIndex, int thLevel, BuildingType bt) {
        for (Building b : buildingData[categoryIndex]) {
            if (b.getType() == bt && b.getLevel() > bt.getMaxLevelForThLevel(thLevel)) {
                b.setLevel(bt.getMaxLevelForThLevel(thLevel));
            }
        }
    }

    int countBuildingType(int categoryIndex, BuildingType bt) {
        int count = 0;
        for (Building b : buildingData[categoryIndex]) {
            if( b.getType() == bt) count++;
        }
        return count;
    }

    int findSmallestLevel(int categoryIndex, BuildingType bt) {
        int retval = -1;
        int level = 100;
        int idx = 0;
        for (Building b : this.buildingData[categoryIndex]) {
            if (b.getType() == bt && b.getLevel() < level) {
                retval = idx;
                level = b.getLevel();
            }
            ++idx;
        }
        return retval;
    }

    public int getThLevel() {
        return thLevel;
    }

    int[] builderBucket;

    public void updateStats() {
        StatsItem si;
        statsData.clear();
        List<Integer> buildTimes = new ArrayList<Integer>();
        builderBucket = new int[builderCount];
        for (BuildingCategory bc : BuildingCategory.values()) {
            si = new StatsItem();
            si.setBuildingCategory(bc);
            si.setName(bc.getName());
            if (bc != BuildingCategory.WALL) {
                for (Building b : buildingData[bc.getIndex()]) {
                    int maxLevel = b.getType().getMaxLevelForThLevel(thLevel);
                    if (bc == BuildingCategory.HERO) {
                        maxLevel = Math.min(maxLevel, getHeroMaxLevel());
                    }
                    if (b.getType() != BuildingType.TOWNHALL) {
                        int lvl = b.getLevel();
                        int buildTime = 0;
                        while (lvl < maxLevel) {
                            si.addCost(b.getType().getCostType(), b.getType().getCostForLevel(lvl + 1));
                            if (b.getType().getBuilderType() == BuilderType.BUILDER) {
                                si.addBuildTime(b.getType().getTimeForLevel(lvl + 1));
                                buildTime += b.getType().getTimeForLevel(lvl + 1);
                            } else {
                                si.addLabTime(b.getType().getTimeForLevel(lvl + 1));
                            }
                            lvl++;
                        }
                        if (buildTime > 0) {
                            buildTimes.add(buildTime);
                        }
                    }
                }
            } else {
                si = new StatsItem();
                si.setBuildingCategory(BuildingCategory.WALL);
                si.setName(BuildingCategory.WALL.getName());
                int unBuiltWallCount = BuildingType.WALL.getMaxCountForThLevel(thLevel) - getWallCount();
                addWallCost(si, unBuiltWallCount, 0);
                for (Wall w : wallData) {
                    addWallCost(si, w.getCount(), w.getLevel());
                }
            }
            statsData.add(si);
        }
        // divide build tasks between builders, starting with longest
        Collections.sort(buildTimes, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs.compareTo(lhs);
            }
        });
        for (Integer time : buildTimes) {
            int minIdx = 0, minVal = Integer.MAX_VALUE;
            for (int i = 0; i < builderCount; i++) {
                if (builderBucket[i] < minVal) {
                    minIdx = i;
                    minVal = builderBucket[i];
                }
            }
            builderBucket[minIdx] += time;
        }
    }

    public void addWallCost(StatsItem si, int count, int startingLevel) {
        int maxLevel = Math.min(getWallMaxLevel(), BuildingType.WALL.getMaxLevelForThLevel(thLevel));
        for (int i=startingLevel+1; i<=maxLevel;i++) {
            if (i < ELIXIR_WALL_LEVEL) {
                si.addCost(CostType.GOLD, BuildingType.WALL.getCostForLevel(i) * count);
            } else {
                si.addCost(CostType.EX, BuildingType.WALL.getCostForLevel(i) * count);
            }
        }
    }

    public void writeStats() {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(',');

        long goldTotal = 0, exTotal = 0, dexTotal = 0;
        int buildTimeTotal = 0, labTimeTotal = 0;
        for (StatsItem si : statsData) {
            BuildingCategory bc = si.getBuildingCategory();
            TableRow tableRow = (TableRow) findViewById(bc.getTableRowId());
            ((TextView) tableRow.findViewById(R.id.stats_value_type)).setText(bc.getName());
            ((TextView) tableRow.findViewById(R.id.stats_value_g)).setText(formatWithSuffix(formatter, si.getGoldCost(),""));
            ((TextView) tableRow.findViewById(R.id.stats_value_ex)).setText(formatWithSuffix(formatter, si.getElixirCost(),""));
            ((TextView) tableRow.findViewById(R.id.stats_value_dex)).setText(formatWithSuffix(formatter, si.getDarkCost(),""));
            ((TextView) tableRow.findViewById(R.id.stats_value_btime)).setText(formatBuildTime(si.getBuildTime(),""));
            ((TextView) tableRow.findViewById(R.id.stats_value_ltime)).setText(formatBuildTime(si.getLabTime(),""));
            goldTotal += si.getGoldCost();
            exTotal += si.getElixirCost();
            dexTotal += si.getDarkCost();
            buildTimeTotal += si.getBuildTime();
            labTimeTotal += si.getLabTime();
        }
        int maxVal = 0;
        for (int i=0;i<builderCount;i++) {
            if (builderBucket[i] > maxVal) maxVal = builderBucket[i];
        }

        TableRow tableRow = (TableRow) findViewById(R.id.stats_row_total);
        ((TextView) tableRow.findViewById(R.id.stats_value_g)).setText(formatWithSuffix(formatter, goldTotal,""));
        ((TextView) tableRow.findViewById(R.id.stats_value_ex)).setText(formatWithSuffix(formatter, exTotal,""));
        ((TextView) tableRow.findViewById(R.id.stats_value_dex)).setText(formatWithSuffix(formatter, dexTotal,""));
        ((TextView) tableRow.findViewById(R.id.stats_value_btime)).setText(formatBuildTime(buildTimeTotal, ""));
        ((TextView) tableRow.findViewById(R.id.stats_value_ltime)).setText(formatBuildTime(labTimeTotal,""));
        tableRow = (TableRow) findViewById(R.id.stats_row_buildtime);
        ((TextView) tableRow.findViewById(R.id.stats_value_time_for_builders)).setText(formatBuildTime(maxVal,""));
        TableLayout table = (TableLayout)findViewById(R.id.stats_table);
        table.requestLayout();
    }

    public String formatWithSuffix(DecimalFormat formatter, long number, String defaultForZero) {
        if (number == 0) return defaultForZero;
        else if (number < 10000) return formatter.format(number);
        else if (number < 1000000) return String.format("%.0fK", number / 1000.0);
        else return String.format("%.1fM", number / 1000000.0);
    }

    public String formatBuildTime(int minutes, String defaultForZero) {
        String result = defaultForZero;

        if (minutes > 0) {
            result = "";
            int days = minutes / (24 * 60);
            minutes -= days * 24 * 60;
            int hours = minutes / 60;
            minutes -= hours * 60;

            if (days > 0) {
                result += "" + days + getString(R.string.day_short) + " ";
            }
            if (hours > 0) {
                result += "" + hours + getString(R.string.hour_short) + " ";
            }
            if (minutes > 0 && days == 0) {
                result += "" + minutes + getString(R.string.minute_short) + " ";
            }
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mainMenu = menu;
        updateVillageMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.help_title));
            alertDialog.setMessage(Html.fromHtml(getString(R.string.help_text)));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return true;
        } else if (id == R.id.action_sort) {
            this.sortBuildings();
        } else if (id == R.id.action_maxall) {
            AlertDialog.Builder bld = new AlertDialog.Builder((Context)this);
            bld.setTitle(getString(R.string.DIALOG_MAXALL_TITLE))
                    .setMessage(getString(R.string.DIALOG_MAXALL_MESSAGE))
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            maxAll();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(true);
            AlertDialog d = bld.create();
            d.setCanceledOnTouchOutside(true);
            if (!this.isFinishing()) {
                d.show();
            }
        } else if (id == R.id.action_rename) {
            final EditText txt = new EditText(this);
            txt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(MAX_VILLAGENAME)});
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
            txt.setText(prefs.getString("villagename" + currentVillage, "Village " + currentVillage));

            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.DLG_RENAME_VILLAGE))
                    .setMessage(getString(R.string.DLG_ENTER_NEW_NAME))
                    .setView(txt)
                    .setPositiveButton(getString(R.string.DLG_BTN_RENAME), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String newname = txt.getText().toString().trim();
                            if (newname.length() > 0) {
                                if (newname.length() > MAX_VILLAGENAME) {
                                    newname = newname.substring(0, MAX_VILLAGENAME);
                                }
                                prefs.edit().putString("villagename" + currentVillage, newname).commit();
                                updateVillageMenu();
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Building> getBuildingData(int categoryIndex) {
        return buildingData[categoryIndex];
    }

    public int getBuilderCount() {
        return builderCount;
    }

    public void setBuilderCount(int builderCount) {
        this.builderCount = builderCount;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        prefs.edit().putInt("builderCount" + currentVillage, builderCount).commit();
        updateStats();
        writeStats();
    }

    public int getHeroMaxLevel() {
        return heroMaxLevel;
    }

    public void setHeroMaxLevel(int heroMaxLevel) {
        this.heroMaxLevel = heroMaxLevel;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        prefs.edit().putInt("heroMaxLevel" + currentVillage, heroMaxLevel).commit();
        updateStats();
        writeStats();
    }

    public int getWallMaxLevel() {
        return wallMaxLevel;
    }

    public void setWallMaxLevel(int wallMaxLevel) {
        this.wallMaxLevel = wallMaxLevel;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        prefs.edit().putInt("wallMaxLevel" + currentVillage, wallMaxLevel).commit();
        updateStats();
        writeStats();
    }

    public ArrayList<Wall> getWallData() {
        return wallData;
    }

    public ArrayList<StatsItem> getStatsData() {
        return statsData;
    }

    void maxAll() {
        for (BuildingCategory bc : BuildingCategory.values()) {
            if (bc == BuildingCategory.WALL) continue;
            for (Building b : buildingData[bc.getIndex()]) {
                if (b.getType() == BuildingType.TOWNHALL) continue;
                b.setLevel(b.getType().getMaxLevelForThLevel(this.thLevel));
            }
        }
        pageAdapter.notifyAllFragments();
        updateStats();
        writeStats();
    }

    public void saveData() {
        if (buildingData != null) {
            FileOutputStream fos;
            ObjectOutputStream oos;

            try {
                fos = getApplication().openFileOutput("savedata" + currentVillage, Context.MODE_PRIVATE);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(buildingData);
                oos.writeObject(wallData);
                oos.close();
                fos.close();
            } catch (Exception e) {
                Log.e("COCT", "saving game failed: ", e);
            }
        }
    }

    public boolean loadData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        builderCount = prefs.getInt("builderCount" + currentVillage, MAX_BUILDER_COUNT);
        heroMaxLevel = prefs.getInt("heroMaxLevel" + currentVillage, MAX_HERO_LEVEL);
        wallMaxLevel = prefs.getInt("wallMaxLevel" + currentVillage, MAX_WALL_LEVEL);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = getApplication().openFileInput("savedata" + currentVillage);
            ois = new ObjectInputStream(fis);
            buildingData = (ArrayList<Building>[]) ois.readObject();
            wallData = (ArrayList<Wall>) ois.readObject();
//            if (firstRunThisVersion) {   // add new stuff here so loading code does not die
//            }
            ois.close();
            fis.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.e("COCT", "restoring game failed: ", e);
            return false;
        } catch (InvalidClassException e) {
            Log.e("COCT", "restoring game failed: ", e);
            return false;
        } catch (Exception e) {
            Log.e("COCT", "restoring game failed: ", e);
            return false;
        }
    }

    public void onSelectVillage(MenuItem item) {
        int village = 1;
        switch(item.getItemId()) {
            case R.id.village1:
                village = 1;
                break;
            case R.id.village2:
                village = 2;
                break;
            case R.id.village3:
                village = 3;
                break;
            case R.id.village4:
                village = 4;
                break;
            case R.id.village5:
                village = 5;
                break;
        }
        saveData();
        currentVillage = village;
        if (!loadData()) {
            initData();
        }
        updateBuildingLists();
        pageAdapter.notifyAllFragments();
        updateStats();
        writeStats();
        updateVillageMenu();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        prefs.edit().putInt("currentVillage", currentVillage).commit();
    }

    void updateVillageMenu() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        MenuItem villageMenu = mainMenu.findItem(R.id.action_village);
        SubMenu villageSubMenu = villageMenu.getSubMenu();
        int[] menuids = new int[] {R.id.village1, R.id.village2, R.id.village3, R.id.village4, R.id.village5};
        for (int i=1; i<=5; i++) {
            String menuitemname = prefs.getString("villagename" + i, getString(R.string.MENU_VILLAGE) + " " + i);
            villageSubMenu.findItem(menuids[i-1]).setTitle(menuitemname);
            if (i == currentVillage) {
                villageSubMenu.findItem(menuids[i-1]).setChecked(true);
                villageMenu.setTitle(menuitemname);
            }
        }
    }

    void sortBuildings() {
        for (BuildingCategory bc : BuildingCategory.values()) {
            if (bc == BuildingCategory.WALL) continue;
            ArrayList<Building> newList1 = new ArrayList<Building>();
            ArrayList<Building> newList2 = new ArrayList<Building>();
            int size = buildingData[bc.getIndex()].size();
            for (BuildingType bt : BuildingType.values()) {
                Building b;
                int i;
                for (i = 0; i < size; ++i) {
                    b = buildingData[bc.getIndex()].get(i);
                    if (b.getType() != bt || b.getLevel() >= b.getType().getMaxLevelForThLevel(thLevel)) continue;
                    newList1.add(b);
                }
                for (i = 0; i < size; ++i) {
                    b = buildingData[bc.getIndex()].get(i);
                    if (b.getType() != bt || b.getLevel() != b.getType().getMaxLevelForThLevel(thLevel)) continue;
                    newList2.add(b);
                }
            }
            buildingData[bc.getIndex()].clear();
            buildingData[bc.getIndex()].addAll(newList1);
            buildingData[bc.getIndex()].addAll(newList2);
        }
        pageAdapter.notifyAllFragments();
        Toast.makeText((Context)this.getApplicationContext(), R.string.TOAST_SORT, Toast.LENGTH_LONG).show();
    }

    void performSearch(int currentGold, int currentEx, int currentDex) {
        Set<FinderItem> buildingGoldBelow = new HashSet<>();
        Set<FinderItem> buildingExBelow = new HashSet<>();
        Set<FinderItem> buildingDexBelow = new HashSet<>();
        Set<FinderItem> buildingUpgradesAboveSet = new HashSet<>();
        Set<FinderItem> labExBelow = new HashSet<>();
        Set<FinderItem> labDexBelow = new HashSet<>();
        ArrayList<FinderItem> labUpgradesAbove = new ArrayList<>();

        // collect and separate possibilities
        for (BuildingCategory bc : BuildingCategory.values()) {
            if (bc != BuildingCategory.WALL) {
                for (Building b : buildingData[bc.getIndex()]) {
                    BuildingType t = b.getType();
                    int lvl = b.getLevel();
                    int maxLevel = t.getMaxLevelForThLevel(thLevel);
                    if (lvl < maxLevel) {
                        FinderItem fi = new FinderItem(b, t.getCostType(), t.getCostForLevel(lvl + 1), t.getTimeForLevel(lvl + 1));
                        fi.calculateDistance(currentGold, currentEx, currentDex);
                        if (t.getBuilderType() == BuilderType.BUILDER) {
                            if (fi.isAllResourcesAvailable()) {
                                switch (fi.getCostType()) {
                                    case GOLD:
                                        buildingGoldBelow.add(fi);
                                        break;
                                    case EX:
                                        buildingExBelow.add(fi);
                                        break;
                                    case DEX:
                                        buildingDexBelow.add(fi);
                                        break;
                                }
                            } else {
                                buildingUpgradesAboveSet.add(fi);
                            }
                        } else {
                            if (fi.isAllResourcesAvailable()) {
                                switch (fi.getCostType()) {
                                    case EX:
                                        labExBelow.add(fi);
                                        break;
                                    case DEX:
                                        labDexBelow.add(fi);
                                        break;
                                }
                            } else {
                                labUpgradesAbove.add(fi);
                            }
                        }
                    }
                }
            }
        }

        // create up to 12 building and lab upgrades below resource level
        ArrayList<FinderItem> temp;
        ArrayList<FinderItem> buildingUpgradesBelow = new ArrayList<>();
        temp = new ArrayList<FinderItem>(buildingGoldBelow);
        if (temp.size() > 0) {
            Collections.sort(temp);
            buildingUpgradesBelow.addAll(temp.subList(0,Math.min(4,temp.size())));
        }
        temp = new ArrayList<FinderItem>(buildingExBelow);
        if (temp.size() > 0) {
            Collections.sort(temp);
            buildingUpgradesBelow.addAll(temp.subList(0,Math.min(4,temp.size())));
        }
        temp = new ArrayList<FinderItem>(buildingDexBelow);
        if (temp.size() > 0) {
            Collections.sort(temp);
            buildingUpgradesBelow.addAll(temp.subList(0,Math.min(4,temp.size())));
        }
        Collections.sort(buildingUpgradesBelow);
        Collections.reverse(buildingUpgradesBelow);

        ArrayList<FinderItem> buildingUpgradesAbove = new ArrayList<>(buildingUpgradesAboveSet);
        Collections.sort(buildingUpgradesAbove);
        Collections.reverse(buildingUpgradesAbove);

        ArrayList<FinderItem> labUpgradesBelow = new ArrayList<>();
        temp = new ArrayList<FinderItem>(labExBelow);
        if (temp.size() > 0) {
            Collections.sort(temp);
            labUpgradesBelow.addAll(temp.subList(0,Math.min(4,temp.size())));
        }
        temp = new ArrayList<FinderItem>(labDexBelow);
        if (temp.size() > 0) {
            Collections.sort(temp);
            labUpgradesBelow.addAll(temp.subList(0,Math.min(4,temp.size())));
        }
        Collections.sort(labUpgradesBelow);
        Collections.reverse(labUpgradesBelow);

        Collections.sort(labUpgradesAbove);
        Collections.reverse(labUpgradesAbove);

        // Add to tables
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        LayoutInflater inflater = getLayoutInflater();

        TableLayout table = (TableLayout)findViewById(R.id.finder_table1);
        int minDistance = Integer.MAX_VALUE;
        TableRow minRow = null;
        int idx1 = 0, idx2 = 0;
        for (int i=0;i<24;i++) {
            FinderItem fi;
            if (idx1 < buildingUpgradesBelow.size()) {
                fi = buildingUpgradesBelow.get(idx1++);
            } else if (idx2 < buildingUpgradesAbove.size()){
                fi = buildingUpgradesAbove.get(idx2++);
            } else {
                break;
            }
            TableRow row = (TableRow)inflater.inflate(R.layout.finder_table_builder_row, null);
            ((TextView)row.findViewById(R.id.finder_row_item)).setText(getString(fi.getBuilding().getType().getName()));
            ((TextView)row.findViewById(R.id.finder_row_lvl)).setText(getString(R.string.finder_row_lvl, fi.getBuilding().getLevel()));
            if (fi.getCostType() == CostType.GOLD) {
                ((TextView) row.findViewById(R.id.finder_row_gold)).setText(formatWithSuffix(formatter, Math.abs(fi.getCostDiff(currentGold)), "0"));
                ((TextView)row.findViewById(R.id.finder_row_gold)).setTextColor(fi.isAllResourcesAvailable() ? Color.rgb(0,192,0) : Color.RED);
            } else if (fi.getCostType() == CostType.EX) {
                ((TextView) row.findViewById(R.id.finder_row_ex)).setText(formatWithSuffix(formatter, Math.abs(fi.getCostDiff(currentEx)), "0"));
                ((TextView)row.findViewById(R.id.finder_row_ex)).setTextColor(fi.isAllResourcesAvailable() ? Color.rgb(0,192,0) : Color.RED);
            } else if (fi.getCostType() == CostType.DEX) {
                ((TextView) row.findViewById(R.id.finder_row_dex)).setText(formatWithSuffix(formatter, Math.abs(fi.getCostDiff(currentDex)), "0"));
                ((TextView)row.findViewById(R.id.finder_row_dex)).setTextColor(fi.isAllResourcesAvailable() ? Color.rgb(0,192,0) : Color.RED);
            }
            ((TextView)row.findViewById(R.id.finder_row_time)).setText(formatBuildTime(fi.getTime(), ""));
//            ((TextView)row.findViewById(R.id.finder_row_time)).setText(getString(R.string.finder_row_lvl,fi.getDistance()));
            table.addView(row);
            if (Math.abs(fi.getDistance()-100) < minDistance) {
                minDistance = Math.abs(fi.getDistance()-100);
                minRow = row;
            }
        }
        if (minRow != null) {
            minRow.findViewById(R.id.finder_row_item).setBackgroundColor(Color.rgb(255, 210, 50));
        }

        table = (TableLayout)findViewById(R.id.finder_table2);
        minDistance = Integer.MAX_VALUE;
        minRow = null;
        idx1 = 0; idx2 = 0;
        for (int i=0;i<16;i++) {
            FinderItem fi;
            if (idx1 < labUpgradesBelow.size()) {
                fi = labUpgradesBelow.get(idx1++);
            } else if (idx2 < labUpgradesAbove.size()){
                fi = labUpgradesAbove.get(idx2++);
            } else {
                break;
            }
            TableRow row = (TableRow)inflater.inflate(R.layout.finder_table_lab_row, null);
            ((TextView)row.findViewById(R.id.finder_row_item)).setText(getString(fi.getBuilding().getType().getName()));
            ((TextView)row.findViewById(R.id.finder_row_lvl)).setText(getString(R.string.finder_row_lvl, fi.getBuilding().getLevel()));
            if (fi.getCostType() == CostType.EX) {
                ((TextView) row.findViewById(R.id.finder_row_ex)).setText(formatWithSuffix(formatter, Math.abs(fi.getCostDiff(currentEx)), "0"));
                ((TextView)row.findViewById(R.id.finder_row_ex)).setTextColor(fi.isAllResourcesAvailable() ? Color.rgb(0,192,0) : Color.RED);
            } else if (fi.getCostType() == CostType.DEX) {
                ((TextView) row.findViewById(R.id.finder_row_dex)).setText(formatWithSuffix(formatter, Math.abs(fi.getCostDiff(currentDex)), "0"));
                ((TextView)row.findViewById(R.id.finder_row_dex)).setTextColor(fi.isAllResourcesAvailable() ? Color.rgb(0,192,0) : Color.RED);
            }
            ((TextView)row.findViewById(R.id.finder_row_time)).setText(formatBuildTime(fi.getTime(), ""));
            table.addView(row);
            if (Math.abs(fi.getDistance()-100) < minDistance) {
                minDistance = Math.abs(fi.getDistance()-100);
                minRow = row;
            }
        }
        if (minRow != null) {
            minRow.findViewById(R.id.finder_row_item).setBackgroundColor(Color.rgb(255, 214, 50));
        }
    }
}
