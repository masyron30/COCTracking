package name.gmail.nsomlai.coctracker;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Norbert on 2015.02.01..
 */
public class StatsFragment extends Fragment {
    public static final StatsFragment newInstance(String title) {
        StatsFragment fragment = new StatsFragment();
        Bundle b = new Bundle(1);
        b.putString("title", title);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stats, container, false);
        Bundle args = getArguments();
        //
        // builder spinner
        //
        Spinner spin = (Spinner)rootView.findViewById(R.id.num_builders);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.builder_counts, R.layout.spinnerblue);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).setBuilderCount(position+2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ((MainActivity)getActivity()).setBuilderCount(MainActivity.MAX_BUILDER_COUNT);
            }
        });
        increaseHitRect(spin);
        //
        // heroes max level spinner
        //
        spin = (Spinner)rootView.findViewById(R.id.heroes_max_level);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.hero_levels, R.layout.spinnerred);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setSelection(((MainActivity)getActivity()).getHeroMaxLevel() / 5);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).setHeroMaxLevel(position*5);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ((MainActivity)getActivity()).setHeroMaxLevel(MainActivity.MAX_HERO_LEVEL);
            }
        });
        increaseHitRect(spin);
        //
        // walls max level spinner
        //
        spin = (Spinner)rootView.findViewById(R.id.walls_max_level);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.wall_levels, R.layout.spinnerred);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setSelection(((MainActivity) getActivity()).getWallMaxLevel());
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).setWallMaxLevel(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ((MainActivity) getActivity()).setBuilderCount(MainActivity.MAX_WALL_LEVEL);
            }
        });
        increaseHitRect(spin);
        setSpinnerValues(rootView);
        return rootView;
    }

    void setSpinnerValues(View rootView) {
        Spinner spin = (Spinner)rootView.findViewById(R.id.num_builders);
        spin.setSelection(((MainActivity)getActivity()).getBuilderCount()-2);
        spin = (Spinner)rootView.findViewById(R.id.heroes_max_level);
        spin.setSelection(((MainActivity) getActivity()).getHeroMaxLevel() / 5);
        spin = (Spinner)rootView.findViewById(R.id.walls_max_level);
        spin.setSelection(((MainActivity)getActivity()).getWallMaxLevel());
    }

    void increaseHitRect(Spinner spin) {
        Rect bounds = new Rect();
        spin.getHitRect(bounds);
        bounds.left -=200;
        bounds.right += 200;
        bounds.top -= 200;
        bounds.bottom += 200;
        TouchDelegate td = new TouchDelegate(bounds, spin);
        if (View.class.isInstance(spin.getParent())) {
            ((View)spin.getParent()).setTouchDelegate(td);
        }
    }

    public String getTitle() {
        return getArguments().getString("title", "x");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            MainActivity mainActivity = (MainActivity)getActivity();
            if (mainActivity != null) {
                mainActivity.updateStats();
                mainActivity.writeStats();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void notifyChange() {
        if (getView() != null) {
            setSpinnerValues(getView());
        }
    }
}
