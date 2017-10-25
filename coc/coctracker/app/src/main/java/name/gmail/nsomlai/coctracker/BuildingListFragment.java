package name.gmail.nsomlai.coctracker;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Norbert on 2015.01.30..
 */
public class BuildingListFragment extends Fragment {
    BuildListAdapter adapter = null;
    public GridView gridView;

    public static final BuildingListFragment newInstance(int categoryIndex, String title) {
        BuildingListFragment fragment = new BuildingListFragment();
        Bundle b = new Bundle(1);
        b.putInt("categoryIndex", categoryIndex);
        b.putString("title", title);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buildinglist, container, false);
        Bundle args = getArguments();
        adapter = new BuildListAdapter(getArguments().getInt("categoryIndex", -1));
        adapter.setMainActivity((MainActivity)getActivity());
        gridView = (GridView) rootView.findViewById(R.id.buildinglist);
        gridView.setAdapter(adapter);
        return rootView;
    }

    void notifyChange() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
