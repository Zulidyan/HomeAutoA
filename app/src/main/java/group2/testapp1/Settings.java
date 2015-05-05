package group2.testapp1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

/**
 * Settings for Bluetooth connectivity
 */
public class Settings extends ActionBarActivity {
    private BluetoothAdapter mBluetoothAdapter;
//    private Set<BluetoothDevice> pairedDevices;
//    private ArrayList<BluetoothDevice> btDeviceArray = new ArrayList<BluetoothDevice>();
//    private BluetoothSocket mmSocket[];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_my);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }
    public void startBT(View view){
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
            //Toast didn't make sense as it was displaying the same time as the confirmation window
            //Toast.makeText(getApplicationContext(),"Bluetooth Enabled",Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(getApplicationContext(), "Bluetooth Already Enabled", Toast.LENGTH_SHORT).show();
    }
    public void discoverableBT(View view){
        if (mBluetoothAdapter.isEnabled()) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
            startActivity(discoverableIntent);
        }
        //Toast didn't make sense as it was displayed before accepting discoverable
        //Toast.makeText(getApplicationContext(),"Discoverable for 30 Seconds",Toast.LENGTH_SHORT).show();
    }
    public void disableBT(View view){
        if(mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(),"Bluetooth Disabled",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(),"Bluetooth Already Disabled",Toast.LENGTH_SHORT).show();
    }

//    public void discoverBT(View view){
//        Toast.makeText(getApplicationContext(),"Not Implemented Yet",Toast.LENGTH_SHORT).show();
//    }
    public void listPairedDevices(View view){
        ListView lv = (ListView)findViewById(R.id.listView1);


        //Toast.makeText(getApplicationContext(),"Not Implemented Yet",Toast.LENGTH_SHORT).show();
        if(mBluetoothAdapter.isEnabled()) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            ArrayList tList = new ArrayList();
            //ArrayList<BluetoothDevice> tDevice = new ArrayList<>();

            for (BluetoothDevice bt : pairedDevices) {

                tList.add(bt.getName());
                //tDevice.add(bt);
                //Toast.makeText(getApplicationContext(), bt.getName(), Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
            @SuppressWarnings("unchecked")
            final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tList);
            lv.setAdapter(adapter);
            //btDeviceArray = tDevice;
        }
        else
            Toast.makeText(getApplicationContext(),"Please Enable Bluetooth First",Toast.LENGTH_SHORT).show();
    }

}
