package com.gizwits.airpurifier.activity.advanced;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gizwits.framework.adapter.AlarmListAdapter;
import com.gizwits.framework.entity.DeviceAlarm;
import com.marz.ac.v1.R;

import java.util.ArrayList;
import java.util.List;

public class AlarmFragment extends Fragment {
    private AlarmListAdapter adapter;
    private ListView alarms_lv;
    private List<DeviceAlarm> infos = new ArrayList();

    public AlarmFragment(AdvancedActivity advancedActivity) {
        this.adapter = new AlarmListAdapter(advancedActivity, this.infos);
    }

    public void addInfos(List<DeviceAlarm> list) {
        this.infos.clear();
        for (DeviceAlarm add : list) {
            this.infos.add(add);
        }
        this.adapter.notifyDataSetInvalidated();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_alarm_layout, null);
        this.alarms_lv = (ListView) inflate.findViewById(R.id.alarms_lv);
        this.alarms_lv.setAdapter(this.adapter);
        return inflate;
    }
}
