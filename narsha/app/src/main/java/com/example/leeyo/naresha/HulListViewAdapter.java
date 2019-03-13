package com.example.leeyo.naresha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class HulListViewAdapter extends BaseAdapter {

    private Context context;
    private List<HulItem> hulList;

    HulListViewAdapter(Context context, List<HulItem> hul_List) {
        this.context = context;
        this.hulList = hul_List;
    }

    @Override
    public int getCount() {
        return hulList.size();
    }

    @Override
    public Object getItem(int position) {
        return hulList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HulItem hulItem = hulList.get(position);

        if(getCheckString(hulItem) == -1) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hul_listview_item_off, parent, false);
        }
        else if(getCheckString(hulItem) == 1) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hul_listview_item_on, parent, false);
        }
        else if(getCheckString(hulItem) == 0) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hul_listview_item_none, parent, false);
        }

        final TextView MaxHul = convertView.findViewById(R.id.Max_hul);
        final TextView MinHul = convertView.findViewById(R.id.Min_hul);
        final TextView dateHul = convertView.findViewById(R.id.date_hul);
        final TextView checkHul = convertView.findViewById(R.id.check_hul);
        final TextView AverageHul = convertView.findViewById(R.id.Average_hul);

        MaxHul.setText(hulItem.getMax_hul());
        MinHul.setText(hulItem.getMin_hul());
        dateHul.setText(hulItem.getDate_hul());
        checkHul.setText(hulItem.getCheck_hul());
        AverageHul.setText(hulItem.getAverage_hul());

        return convertView;
    }

    public int getCheckString(HulItem hulItem) {
        if (hulItem.getCheck_hul().equals("0/0")) {
            return 0;
        }
        else if(hul.PreL + hul.PreB > 0) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
