package com.example.leeyo.naresha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class ChonListViewAdapter extends BaseAdapter{

    private Context context;
    private List<ChonItem> chonList;

    ChonListViewAdapter(Context context, List<ChonItem> chon_List) {
        this.context = context;
        this.chonList = chon_List;
    }

    @Override
    public int getCount() {
        return chonList.size();
    }

    @Override
    public Object getItem(int position) {
        return chonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChonItem chonItem = chonList.get(position);

        if(chonItem.getCheck_chon().contains("-")) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chon_listview_item_off, parent, false);
        }
        else if(chonItem.getCheck_chon().contains("+")) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chon_listview_item_on, parent, false);
        }
        else if(chonItem.getCheck_chon().equals("0.0")) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chon_listview_item_none, parent, false);
        }

        final TextView Chone = convertView.findViewById(R.id.Chon);
        final TextView dateChon = convertView.findViewById(R.id.date_chon);
        final TextView checkChon = convertView.findViewById(R.id.check_chon);

        Chone.setText(chonItem.getChon());
        dateChon.setText(chonItem.getDate_chon());
        checkChon.setText(chonItem.getCheck_chon());

        return convertView;
    }
}
