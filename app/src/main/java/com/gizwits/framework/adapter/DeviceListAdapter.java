package com.gizwits.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gizwits.framework.sdk.SettingManager;
import com.gizwits.framework.utils.StringUtils;
import com.marz.ac.v1.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import java.util.ArrayList;
import java.util.List;

public class DeviceListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 6;
    public static final int VIEW_TYPE_EMPTY = 5;
    public static final int VIEW_TYPE_HEADER = 4;
    public static final int VIEW_TYPE_LAN = 0;
    public static final int VIEW_TYPE_OFFLINE = 2;
    public static final int VIEW_TYPE_UNBIND = 3;
    public static final int VIEW_TYPE_WAN = 1;
    private Context context;
    private List<TypeItem> items;
    List<XPGWifiDevice> lanDevices = new ArrayList();
    private LayoutInflater mInflater;
    List<XPGWifiDevice> offlineDevices = new ArrayList();
    private SettingManager setManager;
    List<XPGWifiDevice> unBindDevices = new ArrayList();
    List<XPGWifiDevice> wanDevices = new ArrayList();

    class ViewHolder {
        View itemView;

        public ViewHolder(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView can not be null!");
            }
            this.itemView = view;
        }
    }

    class DeviceEmptyHolder extends ViewHolder {
        public DeviceEmptyHolder(View view) {
            super(view);
        }
    }

    class TypeItem {
        int itemType;

        private TypeItem(int i) {
            this.itemType = i;
        }
    }

    class DeviceTypeItem extends TypeItem {
        XPGWifiDevice xpgWifiDevice;

        public DeviceTypeItem(int i, XPGWifiDevice xPGWifiDevice) {
            super(i);
            this.xpgWifiDevice = xPGWifiDevice;
        }
    }

    class DeviceViewHolder extends ViewHolder {
        ImageView arrow;
        LinearLayout background;
        ImageView icon;
        TextView name;
        TextView statue;

        public DeviceViewHolder(View view) {
            super(view);
            this.background = (LinearLayout) view.findViewById(R.id.bg);
            this.icon = (ImageView) view.findViewById(R.id.icon);
            this.arrow = (ImageView) view.findViewById(R.id.arrow);
            this.name = (TextView) view.findViewById(R.id.name);
            this.statue = (TextView) view.findViewById(R.id.statue);
        }
    }

    class EmptyTypeItem extends TypeItem {
        public EmptyTypeItem(int i) {
            super(i);
        }
    }

    class HeaderTypeItem extends TypeItem {
        String label;

        public HeaderTypeItem(String str) {
            super(4);
            this.label = str;
        }
    }

    class HeaderViewHolder extends ViewHolder {
        TextView label;

        public HeaderViewHolder(View view) {
            super(view);
            this.label = (TextView) view.findViewById(R.id.label);
        }
    }

    public DeviceListAdapter(Context context, List<XPGWifiDevice> list) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.setManager = new SettingManager(context);
        changeDatas(new ArrayList());
    }

    private List<TypeItem> generateItems() {
        List<TypeItem> arrayList = new ArrayList();
        arrayList.add(new HeaderTypeItem("在线设备"));
        if (this.lanDevices.size() > 0 || this.wanDevices.size() > 0) {
            for (XPGWifiDevice deviceTypeItem : this.lanDevices) {
                arrayList.add(new DeviceTypeItem(0, deviceTypeItem));
            }
            for (XPGWifiDevice deviceTypeItem2 : this.wanDevices) {
                arrayList.add(new DeviceTypeItem(1, deviceTypeItem2));
            }
        } else {
            arrayList.add(new EmptyTypeItem(5));
        }
        arrayList.add(new HeaderTypeItem("离线设备"));
        if (this.offlineDevices.size() > 0) {
            for (XPGWifiDevice deviceTypeItem22 : this.offlineDevices) {
                arrayList.add(new DeviceTypeItem(2, deviceTypeItem22));
            }
        } else {
            arrayList.add(new EmptyTypeItem(5));
        }
        arrayList.add(new HeaderTypeItem("未绑定设备"));
        if (this.unBindDevices.size() > 0) {
            for (XPGWifiDevice deviceTypeItem222 : this.unBindDevices) {
                arrayList.add(new DeviceTypeItem(3, deviceTypeItem222));
            }
            return arrayList;
        }
        arrayList.add(new EmptyTypeItem(5));
        return arrayList;
    }

    private void onBindDeviceItem(DeviceViewHolder deviceViewHolder, XPGWifiDevice xPGWifiDevice) {
        String macAddress;
        if (StringUtils.isEmpty(xPGWifiDevice.getRemark())) {
            macAddress = xPGWifiDevice.getMacAddress();
            int length = macAddress.length();
            macAddress = xPGWifiDevice.getProductName() + macAddress.substring(length - 4, length);
        } else {
            macAddress = xPGWifiDevice.getRemark();
        }
        deviceViewHolder.name.setText(StringUtils.getStrFomat(macAddress, 8, true));
        if (xPGWifiDevice.isLAN()) {
            if (xPGWifiDevice.isBind(this.setManager.getUid())) {
                deviceViewHolder.icon.setImageResource(R.drawable.device_icon_blue);
                deviceViewHolder.name.setTextColor(this.context.getResources().getColor(R.color.main_green));
                deviceViewHolder.statue.setText("局域网在线");
                deviceViewHolder.arrow.setVisibility(0);
                deviceViewHolder.arrow.setImageResource(R.drawable.arrow_right_blue);
                return;
            }
            deviceViewHolder.icon.setImageResource(R.drawable.device_icon_gray);
            deviceViewHolder.name.setTextColor(this.context.getResources().getColor(R.color.text_gray));
            deviceViewHolder.statue.setText("未绑定");
            deviceViewHolder.arrow.setVisibility(0);
            deviceViewHolder.arrow.setImageResource(R.drawable.arrow_right_gray);
        } else if (xPGWifiDevice.isOnline()) {
            deviceViewHolder.icon.setImageResource(R.drawable.device_icon_blue);
            deviceViewHolder.name.setTextColor(this.context.getResources().getColor(R.color.main_green));
            deviceViewHolder.statue.setText("远程在线");
            deviceViewHolder.arrow.setVisibility(0);
            deviceViewHolder.arrow.setImageResource(R.drawable.arrow_right_blue);
        } else {
            deviceViewHolder.icon.setImageResource(R.drawable.device_icon_gray);
            deviceViewHolder.name.setTextColor(this.context.getResources().getColor(R.color.text_gray));
            deviceViewHolder.statue.setText("离线");
            deviceViewHolder.arrow.setVisibility(8);
            deviceViewHolder.background.setBackgroundResource(R.color.transparent);
            deviceViewHolder.arrow.setImageResource(R.drawable.arrow_right_gray);
        }
    }

    public void changeDatas(List<XPGWifiDevice> list) {
        this.lanDevices.clear();
        this.wanDevices.clear();
        this.offlineDevices.clear();
        this.unBindDevices.clear();
        for (XPGWifiDevice xPGWifiDevice : list) {
            if (xPGWifiDevice.isLAN()) {
                if (xPGWifiDevice.isBind(this.setManager.getUid())) {
                    this.lanDevices.add(xPGWifiDevice);
                } else {
                    this.unBindDevices.add(xPGWifiDevice);
                }
            } else if (xPGWifiDevice.isOnline()) {
                this.wanDevices.add(xPGWifiDevice);
            } else {
                this.offlineDevices.add(xPGWifiDevice);
            }
        }
        this.items = generateItems();
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.items != null ? this.items.size() : 0;
    }

    public XPGWifiDevice getDeviceByPosition(int i) {
        return (((TypeItem) this.items.get(i)).itemType == 4 || ((TypeItem) this.items.get(i)).itemType == 5) ? null : ((DeviceTypeItem) this.items.get(i)).xpgWifiDevice;
    }

    public Object getItem(int i) {
        return (this.items == null || i <= 0 || i >= this.items.size()) ? null : this.items.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return this.items.get(i) != null ? ((TypeItem) this.items.get(i)).itemType : super.getItemViewType(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder deviceViewHolder;
        TypeItem typeItem = (TypeItem) this.items.get(i);
        if (view == null) {
            switch (getItemViewType(i)) {
                case 0:
                case 1:
                case 2:
                case 3:
                    deviceViewHolder = new DeviceViewHolder(this.mInflater.inflate(R.layout.device_list_item, null));
                    break;
                case 4:
                    deviceViewHolder = new HeaderViewHolder(this.mInflater.inflate(R.layout.device_list_item_header, null));
                    break;
                case 5:
                    deviceViewHolder = new DeviceEmptyHolder(this.mInflater.inflate(R.layout.device_list_item_empty, null));
                    break;
                default:
                    throw new IllegalArgumentException("invalid view type : " + getItemViewType(i));
            }
            view = deviceViewHolder.itemView;
            view.setTag(deviceViewHolder);
        } else {
            deviceViewHolder = (ViewHolder) view.getTag();
        }
        if (deviceViewHolder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) deviceViewHolder).label.setText(String.valueOf(((HeaderTypeItem) typeItem).label));
        } else if (deviceViewHolder instanceof DeviceViewHolder) {
            onBindDeviceItem((DeviceViewHolder) deviceViewHolder, ((DeviceTypeItem) typeItem).xpgWifiDevice);
        }
        return view;
    }

    public int getViewTypeCount() {
        return 6;
    }
}
