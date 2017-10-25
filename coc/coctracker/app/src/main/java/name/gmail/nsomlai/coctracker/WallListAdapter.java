package name.gmail.nsomlai.coctracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Norbert on 2015.01.29..
 */
public class WallListAdapter extends BaseAdapter implements ListAdapter {
    MainActivity mainActivity;
    DecimalFormat formatter;

    static class ViewHolder {
        public TextView titleTextView;
        public TextView infoTextView;
        public Button plusBtn;
        public Button plusBtn10;
        public Button minusBtn;
        public View parentView;
    }

    public WallListAdapter() {
        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return mainActivity.getWallData().size() + 1;
    }

    @Override
    public Object getItem(int pos) {
        return null;
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.walllistitem, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView)view.findViewById(R.id.wallTitle);
            viewHolder.infoTextView = (TextView)view.findViewById(R.id.wallInfo);
            viewHolder.plusBtn = (Button)view.findViewById(R.id.buttonPlus);
            viewHolder.plusBtn10 = (Button)view.findViewById(R.id.buttonPlus10);
            viewHolder.minusBtn = (Button)view.findViewById(R.id.buttonMinus);
            viewHolder.parentView = view;
            view.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder)view.getTag();
        if (position > 0) {
            Wall w = mainActivity.getWallData().get(position - 1);
            holder.titleTextView.setText(String.format(mainActivity.getResources().getString(R.string.levelwall), w.getLevel()));
            holder.infoTextView.setText(getInfoText(w));
            updateButtons(holder, w);

            //Handle buttons and add onClickListeners
            holder.plusBtn.setVisibility(View.VISIBLE);
            holder.plusBtn10.setVisibility(View.VISIBLE);
            holder.minusBtn.setVisibility(View.VISIBLE);
            holder.plusBtn.setTag(holder);
            holder.plusBtn10.setTag(holder);
            holder.minusBtn.setTag(holder);
        } else {
            holder.titleTextView.setText(String.format(mainActivity.getResources().getString(R.string.built_walls),
                    mainActivity.getWallCount()));
            holder.infoTextView.setText(String.format(mainActivity.getResources().getString(R.string.max_walls),
                    BuildingType.WALL.getMaxCountForThLevel(mainActivity.getThLevel())));
            holder.plusBtn.setVisibility(View.INVISIBLE);
            holder.plusBtn10.setVisibility(View.INVISIBLE);
            holder.minusBtn.setVisibility(View.INVISIBLE);
        }

        holder.plusBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ViewHolder h = (ViewHolder)v.getTag();
                mainActivity.clickWallButton(position-1, 1);
                Wall w = mainActivity.getWallData().get(position-1);
                h.infoTextView.setText(String.format(mainActivity.getResources().getString(R.string.count), w.getCount()));
                notifyDataSetChanged();
                updateButtons(h,w);
            }
        });
        holder.plusBtn.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                ViewHolder h = (ViewHolder)v.getTag();
                mainActivity.clickWallButton(position-1, 1000);
                Wall w = mainActivity.getWallData().get(position-1);
                h.infoTextView.setText(String.format(mainActivity.getResources().getString(R.string.count), w.getCount()));
                notifyDataSetChanged();
                updateButtons(h,w);
                return false;
            }
        });
        holder.plusBtn10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ViewHolder h = (ViewHolder)v.getTag();
                mainActivity.clickWallButton(position-1, 10);
                Wall w = mainActivity.getWallData().get(position-1);
                h.infoTextView.setText(String.format(mainActivity.getResources().getString(R.string.count), w.getCount()));
                notifyDataSetChanged();
                updateButtons(h,w);
            }
        });
        holder.minusBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ViewHolder h = (ViewHolder)v.getTag();
                mainActivity.clickWallButton(position-1, -1);
                Wall w = mainActivity.getWallData().get(position-1);
                h.infoTextView.setText(String.format(mainActivity.getResources().getString(R.string.count), w.getCount()));
                updateButtons(h,w);
                notifyDataSetChanged();
            }
        });
        holder.minusBtn.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                ViewHolder h = (ViewHolder)v.getTag();
                mainActivity.clickWallButton(position-1, -1000);
                Wall w = mainActivity.getWallData().get(position-1);
                h.infoTextView.setText(String.format(mainActivity.getResources().getString(R.string.count), w.getCount()));
                notifyDataSetChanged();
                updateButtons(h,w);
                return false;
            }
        });

        return view;
    }

    void updateButtons(ViewHolder holder, Wall w) {
        holder.plusBtn.setEnabled(mainActivity.getWallCount() < BuildingType.WALL.getMaxCountForThLevel(mainActivity.getThLevel()));
        holder.plusBtn10.setEnabled(mainActivity.getWallCount() <= BuildingType.WALL.getMaxCountForThLevel(mainActivity.getThLevel())-10);
        holder.minusBtn.setEnabled(mainActivity.getWallCount() > 0 && w.getCount() > 0);
    }

    android.text.Spanned getInfoText(Wall w) {
        String line1 = String.format(mainActivity.getResources().getString(R.string.count), w.getCount());
        String infoText = mainActivity.getResources().getString(R.string.buildinginfo);

        String line2 = "";
        int cost = BuildingType.WALL.getCostForLevel(w.getLevel());
        String formattedCost = formatter.format(cost);
        line2 = String.format(mainActivity.getResources().getString(R.string.wall_cost), "#CCB225", formattedCost);
        if (w.getLevel() >= mainActivity.ELIXIR_WALL_LEVEL) {
            line2 += String.format(mainActivity.getResources().getString(R.string.wall_cost_e), "#EA00FF");
        }

        return Html.fromHtml(line1 + line2);
    }
}
