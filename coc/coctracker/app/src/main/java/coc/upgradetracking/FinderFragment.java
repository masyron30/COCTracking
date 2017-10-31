package coc.upgradetracking;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class FinderFragment extends Fragment {
    View rootView;
    boolean searchPressed = false;
    MainActivity mainActivity;

    public static final FinderFragment newInstance(String title) {
        FinderFragment fragment = new FinderFragment();
        Bundle b = new Bundle(1);
        b.putString("title", title);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            searchPressed = savedInstanceState.getBoolean("searchpressed", false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("searchpressed", searchPressed);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (searchPressed) {
            doSearch();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.finder, container, false);
        mainActivity = (MainActivity)getActivity();
        ((TextView)rootView.findViewById(R.id.finder_form_explanation)).setText(Html.fromHtml(getString(R.string.finder_explanation)));
        Bundle args = getArguments();
        Button btn = (Button)rootView.findViewById(R.id.finder_btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mainActivity.getApplication());
        ((EditText)rootView.findViewById(R.id.finder_edit_gold)).setText(Integer.valueOf(prefs.getInt("finder_gold", 0)).toString());
        ((EditText)rootView.findViewById(R.id.finder_edit_ex)).setText(Integer.valueOf(prefs.getInt("finder_ex", 0)).toString());
        ((EditText)rootView.findViewById(R.id.finder_edit_dex)).setText(Integer.valueOf(prefs.getInt("finder_dex", 0)).toString());

        ((EditText)rootView.findViewById(R.id.finder_edit_dex)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });
        return rootView;
    }

    public void doSearch() {
        searchPressed = true;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mainActivity.getApplication());
        int gold=0, ex=0, dex=0;
        try {
            gold = Integer.parseInt( ((EditText)rootView.findViewById(R.id.finder_edit_gold)).getText().toString());
            ex = Integer.parseInt( ((EditText)rootView.findViewById(R.id.finder_edit_ex)).getText().toString());
            dex = Integer.parseInt( ((EditText)rootView.findViewById(R.id.finder_edit_dex)).getText().toString());
        } catch (NumberFormatException e) {}
        prefs.edit()
                .putInt("finder_gold", gold)
                .putInt("finder_ex", ex)
                .putInt("finder_dex", dex)
                .apply();
        if (mainActivity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mainActivity.getCurrentFocus().getWindowToken(), 0);
        }
        clearTables();
        mainActivity.performSearch(gold, ex, dex);
    }

    void clearTables() {
        TableLayout table = (TableLayout)mainActivity.findViewById(R.id.finder_table1);
        while (table.getChildCount() > 2)
            table.removeView(table.getChildAt(table.getChildCount() - 1));
        table = (TableLayout)mainActivity.findViewById(R.id.finder_table2);
        while (table.getChildCount() > 2)
            table.removeView(table.getChildAt(table.getChildCount() - 1));
    }

    public void notifyChange() {
        clearTables();
    }
}
