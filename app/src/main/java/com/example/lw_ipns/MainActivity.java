package com.example.lw_ipns;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

        private TextView textView;
        private WifiManager wifiManager;
        WifiReceiver wifiReceiver;
        private List<ScanResult> wifiList;
        private StringBuilder sb = new StringBuilder();

    public class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("wifi", "");
            wifiList = wifiManager.getScanResults();
            Log.e("sb", "Wifi Connections are");
            sb.append("Wifi Connections are: ").append(wifiList.size()).append("\n\n");

            Log.e("sb", "StringBuilder me append ho rha hai");
            for(int i=0; i<wifiList.size(); i++){
                sb.append(Integer.toString(i + 1));
                //sb.append(i + 1);
                sb.append(wifiList.get(i).toString());
                sb.append("\n\n");
            }
            textView.setText(sb);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.mainText);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(!wifiManager.isWifiEnabled()){
            Toast.makeText(getApplicationContext(), "wifi is disabled. enable it", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

        //wifiReceiver = new wifiReceiver();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        Log.e("wifi", "Start scan ke peeche");
        wifiManager.startScan();
        Log.e("wifi", "Start scan ho rha hai bus display nhi ho rha");

        textView.setText("Starting scan...");
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(wifiReceiver);
        //unregisterReceiver(wifiReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }
}
