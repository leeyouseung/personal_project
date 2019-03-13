package com.example.leeyo.naresha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class AlramListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Alram> alramList;

    AlramListViewAdapter(Context context, List<Alram> alramList) {
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.alram_listview_item, parent, false);

        final Alram alram = alramList.get(position);

        final TextView am_pm = convertView.findViewById(R.id.alram_am_pm);
        final TextView hour = convertView.findViewById(R.id.alram_hour);
        final TextView minute = convertView.findViewById(R.id.alram_minute);
        final TextView repeat = convertView.findViewById(R.id.alram_isFor);
        final TextView count = convertView.findViewById(R.id.medi_count);

        am_pm.setText(alram.getam_pm());
        hour.setText(alram.getHour());
        minute.setText(alram.getMinute());
        repeat.setText(alram.getIsFor());
        count.setText(alram.getCount());

        AlertDialog.Builder time_delete = new AlertDialog.Builder(context);
        time_delete.setMessage(alram.getIsFor() + " 알람을 삭제하시겠습니까?")
                .setCancelable(false).setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Action for 'Cancel' Button;

                    }
                }).setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Action for 'OK' Button;

                        alramList.remove(position);
                        AlramListViewAdapter.this.notifyDataSetChanged();

                    }
                });

        final AlertDialog delete_time = time_delete.create();

        ImageButton trash_button = convertView.findViewById(R.id.alram_trash_button);
        trash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_time.show();

            }
        });

        return convertView;

    }

}
