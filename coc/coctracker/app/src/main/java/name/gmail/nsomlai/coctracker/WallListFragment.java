package name.gmail.nsomlai.coctracker;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Norbert on 2015.02.10..
 */
public class WallListFragment extends Fragment {
    WallListAdapter adapter = null;
    public GridView gridView;

    public static final WallListFragment newInstance(String title) {
        WallListFragment fragment = new WallListFragment();
        Bundle b = new Bundle(1);
        b.putString("title", title);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wallist, container, false);
        Bundle args = getArguments();
        adapter = new WallListAdapter();
        adapter.setMainActivity((MainActivity)getActivity());
        gridView = (GridView) rootView.findViewById(R.id.walllist);
        gridView.setAdapter(adapter);
        return rootView;
    }

    void notifyChange() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

}
