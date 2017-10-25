package name.gmail.nsomlai.coctracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
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

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;

/**
 * Created by Norbert on 2015.01.29..
 */
public class BuildListAdapter extends BaseAdapter implements ListAdapter {
    int categoryIndex;
    MainActivity mainActivity;
    DecimalFormat formatter;

    static class ViewHolder {
        public TextView titleTextView;
        public TextView infoTextView;
        public Button plusBtn;
        public Button minusBtn;
        public View parentView;
        public View colorMarker;
    }

    public BuildListAdapter(int categoryIndex) {
        this.categoryIndex = categoryIndex;
        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return mainActivity.getBuildingData(categoryIndex).size();
    }

    @Override
    public Object getItem(int pos) {
        return mainActivity.getBuildingData(categoryIndex).get(pos);
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
            view = inflater.inflate(R.layout.buildinglistitem, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView)view.findViewById(R.id.buildingTitle);
            viewHolder.infoTextView = (TextView)view.findViewById(R.id.buildingInfo);
            viewHolder.plusBtn = (Button)view.findViewById(R.id.buttonPlus);
            viewHolder.minusBtn = (Button)view.findViewById(R.id.buttonMinus);
            viewHolder.parentView = view;
            viewHolder.colorMarker = view.findViewById(R.id.color_marker);
            view.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder)view.getTag();
        Building b = mainActivity.getBuildingData(categoryIndex).get(position);
        holder.titleTextView.setText(mainActivity.getResources().getString(b.getType().getName()));
        holder.infoTextView.setText(getInfoText(b));
        setColor(holder.parentView,b);
        holder.colorMarker.setBackgroundColor(b.getColor());
        updateButtons(holder, b);

        //Handle buttons and add onClickListeners
        holder.plusBtn.setTag(holder);
        holder.minusBtn.setTag(holder);

        holder.plusBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ViewHolder h = (ViewHolder)v.getTag();
                mainActivity.clickBuildingButton(categoryIndex, position, 1);
                Building b = mainActivity.getBuildingData(categoryIndex).get(position);
                h.infoTextView.setText(getInfoText(b));
                setColor(h.parentView, b);
                updateButtons(h,b);
            }
        });
        holder.plusBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ViewHolder h = (ViewHolder)v.getTag();
                mainActivity.clickBuildingButton(categoryIndex, position, 1000);
                Building b = mainActivity.getBuildingData(categoryIndex).get(position);
                h.infoTextView.setText(getInfoText(b));
                setColor(h.parentView, b);
                updateButtons(h, b);
                return false;
            }
        });
        holder.minusBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final ViewHolder h = (ViewHolder)v.getTag();
                final Building b = mainActivity.getBuildingData(categoryIndex).get(position);
                if (b.getType() == BuildingType.TOWNHALL) {
                    AlertDialog.Builder bld = new AlertDialog.Builder(mainActivity);
                    bld.setTitle(R.string.townhall_reduce_dialog_title)
                        .setMessage(R.string.townhall_reduce_dialog_message)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mainActivity.clickBuildingButton(categoryIndex, position, -1);
                                h.infoTextView.setText(getInfoText(b));
                                dialog.dismiss();
                            }})
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }})
                        .setCancelable(true);
                        AlertDialog d = bld.create();
                        d.setCanceledOnTouchOutside(true);
                    if (!mainActivity.isFinishing()) d.show();
                } else {
                    mainActivity.clickBuildingButton(categoryIndex, position, -1);
                    h.infoTextView.setText(getInfoText(b));
                    setColor(h.parentView, b);
                    updateButtons(h,b);
                }
            }
        });
        holder.minusBtn.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                final ViewHolder h = (ViewHolder)v.getTag();
                final Building b = mainActivity.getBuildingData(categoryIndex).get(position);
                if (b.getType() == BuildingType.TOWNHALL) {
                    return true;
                } else {
                    mainActivity.clickBuildingButton(categoryIndex, position, -1000);
                    h.infoTextView.setText(getInfoText(b));
                    setColor(h.parentView, b);
                    updateButtons(h,b);
                }
                return false;
            }
        });
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ViewHolder h = (ViewHolder) v.getTag();
                final Building b = mainActivity.getBuildingData(categoryIndex).get(position);
                int[] mColor = colorChoice(mainActivity);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColor, b.getColor(),4,isTablet(mainActivity) ? ColorPickerDialog.SIZE_LARGE : ColorPickerDialog.SIZE_SMALL);
                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        b.setColor(color);
                        h.colorMarker.setBackgroundColor(color);
                    }
                });
                dialog.show(mainActivity.getFragmentManager(),"col");
            }
        });
        return view;
    }

    void updateButtons(ViewHolder holder, Building b) {
        holder.plusBtn.setEnabled(b.getLevel() < b.getType().getMaxLevelForThLevel(mainActivity.getThLevel()));
        holder.minusBtn.setEnabled(b.getLevel() > b.getType().getStartingLevel());
    }

    void setColor(View view, Building b) {
        if (b.getLevel() == b.getType().getMaxLevelForThLevel(mainActivity.getThLevel())) {
            view.setBackgroundResource(R.drawable.grid_cell_max);
        } else {
            view.setBackgroundResource(R.drawable.grid_cell);
        }
    }

    android.text.Spanned getInfoText(Building b) {
        String infoText = mainActivity.getResources().getString(R.string.buildinginfo);
        String secondLine, levelText;
        String levelColor = "#000000";
        if (b.getLevel() == b.getType().getMaxLevelForThLevel(mainActivity.getThLevel())) {
            secondLine = String.format(mainActivity.getResources().getString(R.string.maxed),"#00A303");
            levelColor = "#00A303";
        } else {
            int cost = b.getType().getCostForLevel(b.getLevel() + 1);
            String formattedCost = formatter.format(cost);

            String mu, color;
            switch (b.getType().getCostType()) {
                case GOLD:
                    mu = mainActivity.getResources().getString(R.string.goldshort);
                    color = "#CCB225";
                    break;
                case EX:
                    mu = mainActivity.getResources().getString(R.string.exshort);
                    color = "#EA00FF";
                    break;
                case DEX:
                default:
                    mu = mainActivity.getResources().getString(R.string.dexshort);
                    color = "#000000";
                    break;
            }
            String buildTime = mainActivity.formatBuildTime(b.getType().getTimeForLevel(b.getLevel() + 1), mainActivity.getResources().getString(R.string.buildtime_instant));
            secondLine = String.format(mainActivity.getResources().getString(R.string.next), color, formattedCost, mu, buildTime);
        }

        if (b.getLevel() == 0) {
            levelColor = "#C00000";
            levelText = mainActivity.getResources().getString(R.string.newbuilding);
        } else {
            levelText = String.format(mainActivity.getResources().getString(R.string.level), b.getLevel(),
                    b.getType().getMaxLevelForThLevel(mainActivity.getThLevel()));
        }
        return Html.fromHtml(String.format(infoText, levelColor, levelText, secondLine));
    }

    public int[] colorChoice(Context context) {
        int[] mColorChoices=null;
        String[] color_array = context.getResources().getStringArray(R.array.default_color_choice_values);

        if (color_array!=null && color_array.length>0) {
            mColorChoices = new int[color_array.length];
            for (int i = 0; i < color_array.length; i++) {
                mColorChoices[i] = Color.parseColor(color_array[i]);
            }
        }
        return mColorChoices;
    }

    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
