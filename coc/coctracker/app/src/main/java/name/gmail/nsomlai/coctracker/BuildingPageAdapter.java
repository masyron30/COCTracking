package name.gmail.nsomlai.coctracker;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Norbert on 2015.01.30..
 */
public class BuildingPageAdapter extends FragmentPagerAdapter {
    static final int PAGE_COUNT = BuildingCategory.values().length + 2;
    private MainActivity mainActivity;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public BuildingPageAdapter(FragmentManager fm, MainActivity mainActivity) {
        super(fm);
        this.mainActivity = mainActivity;
        for (int i=0; i<PAGE_COUNT;i++) {
            fragments.add(null);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == getCount() - 2) {
            return StatsFragment.newInstance(mainActivity.getResources().getString(R.string.BC_STATS));
        } else if (position == getCount() - 1) {
            return FinderFragment.newInstance(mainActivity.getResources().getString(R.string.finder_title));
        } else if (position == getCount() - 3) {
            return WallListFragment.newInstance(mainActivity.getResources().getString(R.string.BC_WALLS));
        } else {
            return BuildingListFragment.newInstance(position, mainActivity.getString(BuildingCategory.values()[position].getName()));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment created = (Fragment)super.instantiateItem(container, position);
        fragments.set(position, created);
        return created;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == getCount() - 2) {
            return mainActivity.getResources().getString(R.string.BC_STATS);
        } else if (position == getCount() - 1) {
            return mainActivity.getResources().getString(R.string.finder_title);
        } else {
            return mainActivity.getString(BuildingCategory.values()[position].getName());
        }
    }

    public void notifyAllFragments()
    {
        for (Fragment f : fragments) {
            if (f != null) {
                if (f instanceof BuildingListFragment) {
                    ((BuildingListFragment) f).notifyChange();
                } else if (f instanceof WallListFragment) {
                    ((WallListFragment) f).notifyChange();
                } else if (f instanceof StatsFragment) {
                    ((StatsFragment) f).notifyChange();
                } else if (f instanceof FinderFragment) {
                    ((FinderFragment) f).notifyChange();
                }
            }
        }
    }
}


