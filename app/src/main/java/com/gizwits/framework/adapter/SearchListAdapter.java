package com.gizwits.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gizwits.framework.sdk.SettingManager;
import com.gizwits.framework.utils.StringUtils;
import com.uh.all.airpurifier.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends BaseAdapter {
    private Context context;
    private List<XPGWifiDevice> currentDevices;
    private int i;
    private LayoutInflater inflater;
    private SettingManager setManager;

    private static class ViewHolder {
        TextView tvName;

        private ViewHolder() {
        }
    }

    public SearchListAdapter(Context context, List<XPGWifiDevice> list) {
        this.i = 0;
        this.i = 0;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.setManager = new SettingManager(context);
        changedatas(list);
    }

    private void changedatas(List<XPGWifiDevice> list) {
        if (this.currentDevices == null || this.currentDevices.size() <= 0) {
            this.currentDevices = new ArrayList();
        } else {
            this.currentDevices.clear();
        }
        this.currentDevices = list;
    }

    public int getCount() {
        return this.currentDevices.size();
    }

    public XPGWifiDevice getDevice(int i) {
        return (XPGWifiDevice) this.currentDevices.get(i);
    }

    public Object getItem(int i) {
        return this.currentDevices.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = this.inflater.inflate(R.layout.search_list_item, null);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        XPGWifiDevice xPGWifiDevice = (XPGWifiDevice) this.currentDevices.get(i);
        String macAddress = xPGWifiDevice.getMacAddress();
        int length = macAddress.length();
        viewHolder.tvName.setText(StringUtils.getStrFomat(xPGWifiDevice.getProductName() + macAddress.substring(length - 4, length), 8, true));
        return view;
    }
}
