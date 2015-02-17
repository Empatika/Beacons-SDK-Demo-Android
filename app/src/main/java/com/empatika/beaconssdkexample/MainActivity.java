package com.empatika.beaconssdkexample;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.empatika.beaconsdk.app.BeaconSDKBaseActivity;
import com.empatika.beaconsdk.tracker.listener.BeaconEventsListener;
import com.empatika.beaconsdk.tracker.listener.RegionEventsListener;
import com.empatika.beaconsdk.tracker.model.Beacon;
import com.empatika.beaconsdk.tracker.model.Region;


public class MainActivity extends BeaconSDKBaseActivity {

    private ListView beaconsList;
    private ArrayAdapter<Beacon> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        beaconsList = (ListView) findViewById(R.id.beacons_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        beaconsList.setAdapter(adapter);
    }

    @Override
    public void onServiceConnected() {
        manager.addBeaconManager(this, new BeaconEventsListener() {
            @Override
            public void rangeBeacon(Beacon beacon) {
                Log.i(TAG, "range: " + beacon.toString());
                adapter.remove(beacon);
                adapter.add(beacon);
            }

            @Override
            public void newBeacon(Beacon beacon) {
                Log.d(TAG, "new: " + beacon.toString());
                adapter.add(beacon);
            }

            @Override
            public void deadBeacon(Beacon beacon) {
                Log.d(TAG, "dead: " + beacon.toString());
                adapter.remove(beacon);
            }
        });
        manager.addRegionManager(this, new RegionEventsListener() {
            @Override
            public void enterRegion(Region region) {
                Log.d(TAG, "enter: " + region.toString());
            }

            @Override
            public void exitRegion(Region region) {
                Log.d(TAG, "exit: " + region.toString());
            }
        });
    }
}
