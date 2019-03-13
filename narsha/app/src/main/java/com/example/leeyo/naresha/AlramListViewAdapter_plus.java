package com.example.leeyo.naresha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class AlramListViewAdapter_plus extends BaseAdapter {

    private Context context;
    private List<medi_plus_Alram> plusList;

    AlramListViewAdapter_plus(Context context, List<medi_plus_Alram> plusList) {
        this.context = context;
        this.plusList = plusList;

    }

    @Override
    public int getCount() {
        return plusList.size();

    }

    @Override
    public Object getItem(int position) {
        return plusList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.medi_plus_listview_item, parent, false);

        final medi_plus_Alram plusAlram = plusList.get(position);

        final TextView am_pm = convertView.findViewById(R.id.plus_am_pm);
        final TextView hour = convertView.findViewById(R.id.plus_hour);
        final TextView minute = convertView.findViewById(R.id.plus_minute);

        am_pm.setText(plusAlram.getAm_pm());
        hour.setText(plusAlram.getHour());
        minute.setText(plusAlram.getMinute());

        ImageButton trash_button = convertView.findViewById(R.id.alram_trash_button);
        trash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusList.remove(position);
                AlramListViewAdapter_plus.this.notifyDataSetChanged();

            }
        });

        return convertView;
    }

}
