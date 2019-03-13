package com.example.leeyo.naresha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import java.util.List;

public class AlramListViewAdapter_first extends BaseAdapter {

    private Context context;
    private List<Alram_first> alramList;

    AlramListViewAdapter_first(Context context, List<Alram_first> alramList) {
        this.context = context;
        this.alramList = alramList;

    }

    @Override
    public int getCount() {
        return alramList.size();

    }

    @Override
    public Object getItem(int position) {
        return alramList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @SuppressLint({"ViewHolder", "ResourceAsColor"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.alram_listview_item_first, parent, false);

        Alram_first alram = alramList.get(position);

        final TextView am_pm = convertView.findViewById(R.id.alram_am_pm);
        final TextView hour = convertView.findViewById(R.id.alram_hour);
        final TextView minute = convertView.findViewById(R.id.alram_minute);
        final TextView repeat = convertView.findViewById(R.id.alram_isFor);
        final TextView count = convertView.findViewById(R.id.medi_count);
        final Switch on_off = convertView.findViewById(R.id.alram_switch);

        hour.setText(alram.getHour());
        minute.setText(alram.getMinute());
        repeat.setText(alram.getIsFor());
        count.setText(alram.getCount());
        on_off.setChecked(alram.isOn());

        if(alram.isOn()) {
            am_pm.setTextColor(Color.BLACK);
            hour.setTextColor(Color.BLACK);
            minute.setTextColor(Color.BLACK);
            repeat.setTextColor(Color.BLACK);
            count.setTextColor(Color.BLACK);

        }
        else {
            am_pm.setTextColor(Color.GRAY);
            hour.setTextColor(Color.BLACK);
            minute.setTextColor(Color.BLACK);
            repeat.setTextColor(Color.GRAY);
            count.setTextColor(Color.GRAY);

        }

        on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    am_pm.setTextColor(Color.BLACK);
                    hour.setTextColor(Color.BLACK);
                    minute.setTextColor(Color.BLACK);
                    repeat.setTextColor(Color.BLACK);
                    count.setTextColor(Color.BLACK);

                }
                else {
                    am_pm.setTextColor(Color.GRAY);
                    hour.setTextColor(Color.BLACK);
                    minute.setTextColor(Color.BLACK);
                    repeat.setTextColor(Color.GRAY);
                    count.setTextColor(Color.GRAY);

                }

            }

        });

        AlramListViewAdapter_first.this.notifyDataSetChanged();

        return convertView;
    }

}
