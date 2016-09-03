package com.gizwits.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.gizwits.framework.utils.DensityUtil;
import com.gizwits.framework.utils.StringUtils;
import com.uh.all.airpurifier.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import java.util.List;

public class MenuDeviceAdapter extends BaseAdapter {
    private int choosedPos = 0;
    private Context ctx;
    private List<XPGWifiDevice> devicelist;
    private LayoutInflater inflater;

    private static class ViewHolder {
        TextView deviceName_tv;
        ImageView device_checked_tv;

        private ViewHolder() {
        }
    }

    public MenuDeviceAdapter(Context context, List<XPGWifiDevice> list) {
        this.ctx = context;
        this.inflater = LayoutInflater.from(context);
        this.devicelist = list;
    }

    public int getChoosedPos() {
        return this.choosedPos;
    }

    public int getCount() {
        return this.devicelist.size();
    }

    public XPGWifiDevice getItem(int i) {
        return (XPGWifiDevice) this.devicelist.get(i);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        String macAddress;
        viewGroup.setLayoutParams(new LayoutParams(-2, DensityUtil.dip2px(this.ctx, (float) (getCount() * 50))));
        XPGWifiDevice item = getItem(i);
        if (view == null) {
            view = this.inflater.inflate(R.layout.slibbar_item, null);
            ViewHolder viewHolder2 = new ViewHolder();
            viewHolder2.device_checked_tv = (ImageView) view.findViewById(R.id.device_checked_tv);
            viewHolder2.deviceName_tv = (TextView) view.findViewById(R.id.deviceName_tv);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (StringUtils.isEmpty(item.getRemark())) {
            macAddress = item.getMacAddress();
            int length = macAddress.length();
            macAddress = item.getProductName() + macAddress.substring(length - 4, length);
        } else {
            macAddress = item.getRemark();
        }
        viewHolder.deviceName_tv.setText(StringUtils.getStrFomat(macAddress, 8, true));
        if (getChoosedPos() == i) {
            viewHolder.deviceName_tv.setSelected(true);
            viewHolder.device_checked_tv.setVisibility(0);
        } else {
            viewHolder.deviceName_tv.setSelected(false);
            viewHolder.device_checked_tv.setVisibility(4);
        }
        if (item.isOnline()) {
            viewHolder.deviceName_tv.setTextColor(this.ctx.getResources().getColor(R.color.text_blue));
        } else {
            viewHolder.deviceName_tv.setTextColor(this.ctx.getResources().getColor(R.color.text_gray));
        }
        return view;
    }

    public void setChoosedPos(int i) {
        this.choosedPos = i;
        notifyDataSetChanged();
    }
}
