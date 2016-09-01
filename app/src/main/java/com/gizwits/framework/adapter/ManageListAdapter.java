package com.gizwits.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gizwits.framework.utils.StringUtils;
import com.uh.all.airpurifier.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import java.util.List;

public class ManageListAdapter extends BaseAdapter {
    private Context context;
    private List<XPGWifiDevice> devicelist;
    private LayoutInflater inflater = LayoutInflater.from(this.context);

    private static class ViewHolder {
        ImageView ivArrow;
        ImageView ivType;
        TextView tvName;

        private ViewHolder() {
        }
    }

    public ManageListAdapter(Context context, List<XPGWifiDevice> list) {
        this.devicelist = list;
        this.context = context;
    }

    public int getCount() {
        return this.devicelist.size();
    }

    public Object getItem(int i) {
        return this.devicelist.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        String macAddress;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = this.inflater.inflate(R.layout.search_list_item, null);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            viewHolder.ivType = (ImageView) view.findViewById(R.id.icon);
            viewHolder.ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        XPGWifiDevice xPGWifiDevice = (XPGWifiDevice) this.devicelist.get(i);
        if (StringUtils.isEmpty(xPGWifiDevice.getRemark())) {
            macAddress = xPGWifiDevice.getMacAddress();
            int length = macAddress.length();
            macAddress = xPGWifiDevice.getProductName() + macAddress.substring(length - 4, length);
        } else {
            macAddress = xPGWifiDevice.getRemark();
        }
        viewHolder.tvName.setText(StringUtils.getStrFomat(macAddress, 8, true));
        if (xPGWifiDevice.isLAN() || xPGWifiDevice.isOnline()) {
            viewHolder.tvName.setTextColor(this.context.getResources().getColor(R.color.text_blue));
        } else {
            viewHolder.tvName.setTextColor(this.context.getResources().getColor(R.color.text_gray));
        }
        return view;
    }
}
