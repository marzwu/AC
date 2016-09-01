package com.gizwits.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gizwits.framework.entity.DeviceAlarm;
import com.uh.all.airpurifier.R;
import java.util.List;

public class AlarmListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater = LayoutInflater.from(this.context);
    private List<DeviceAlarm> list;

    private static class ViewHolder {
        TextView tvDesc;
        TextView tvTime;

        private ViewHolder() {
        }
    }

    public AlarmListAdapter(Context context, List<DeviceAlarm> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int i) {
        return this.list.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        DeviceAlarm deviceAlarm = (DeviceAlarm) this.list.get(i);
        if (view == null) {
            ViewHolder viewHolder2 = new ViewHolder();
            view = this.inflater.inflate(R.layout.alarm_list_item, null);
            viewHolder2.tvTime = (TextView) view.findViewById(R.id.tvTime);
            viewHolder2.tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvTime.setText(deviceAlarm.getTime());
        viewHolder.tvDesc.setText(deviceAlarm.getDesc());
        return view;
    }
}
