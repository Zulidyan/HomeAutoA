package group2.testapp1;

import android.bluetooth.*;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

//import java.util.ArrayList;
//import java.util.Set;


public class MyActivity extends ActionBarActivity {


    private BluetoothAdapter mBluetoothAdapter;
//    public final static String EXTRA_MESSAGE = "wat";
//    private ListView lv;
//    private Set<BluetoothDevice> pairedDevices;
//    private ArrayList<BluetoothDevice> btDeviceArray = new ArrayList<BluetoothDevice>();
//    private BluetoothSocket mmSocket[];
//    private ToggleDevice[] tD;
//
//    public BluetoothSocket getmmSocket(){
//        return mmSocket[0];
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
//        if(mBluetoothAdapter.isEnabled()) {
//            pairedDevices = mBluetoothAdapter.getBondedDevices();
//
//            ArrayList tList = new ArrayList();
//            ArrayList<BluetoothDevice> tDevice = new ArrayList<>();
//            for (BluetoothDevice bt : pairedDevices) {
//                tList.add(bt.getName());
//                Log.d("","Name of Device: "+bt.getName());
//                if (bt.getName().contains("Light"))
//                    Log.d("","Contains HC");
//                tDevice.add(bt);
//                Log.d("","Device name"+bt.getName()+": toString "+bt.toString());
//            }
//            btDeviceArray = tDevice;
//        }
//        tD = new ToggleDevice[btDeviceArray.size()];
//
//        mBluetoothAdapter.cancelDiscovery();
//        Log.d("", "Size of bdDeviceArray = " + btDeviceArray.size());
//        mmSocket = new BluetoothSocket[btDeviceArray.size()];
//        for (int device = 0; device < btDeviceArray.size(); device++) {
//            BluetoothDevice mmDevice = btDeviceArray.get(device);
//            //try {
//                String mmUUID = "00001101-0000-1000-8000-00805F9B34FB";
//            try{
//                mmSocket[device] = mmDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString(mmUUID));
//            } catch (Exception e) {e.printStackTrace(); Log.d("","Failed to create rfcomm socket");}
//                tD[device] = new ToggleDevice(mmSocket[device], device);
//                if(tD[device].connect() == true)
//                //mmSocket[device].connect();
//                    Log.d("","Connected to device "+device);
//                else
//                    Log.d("","Failed to connect to device "+device);
//        }
    }

//    protected void reconnect(){
//        for (int device = 0; device < btDeviceArray.size(); device++) {
//            BluetoothDevice mmDevice = btDeviceArray.get(device);
//            BluetoothSocket tSocket;
//            try {
//                String mmUUID = "00001101-0000-1000-8000-00805F9B34FB";
//                tSocket = mmDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString(mmUUID));
//                tSocket.connect();
//                mmSocket[device] = tSocket;
//                Log.d("","Connected to device "+device);
//            } catch (Exception e) {
//                Log.d("","Failed to connect to device "+device);
//            }
//        }
//    }
//    @Override
//    protected void onPause(){
//        super.onPause();
//        for (int i = 0; i < btDeviceArray.size(); i++){
//            try{mmSocket[i].close();
//                Log.d("","Closed socket '"+i+"' onPause()");
//            }
//            catch (Exception e){
//                Log.d("", "Failed to close socket "+ i);
//            }
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the LightsActivity button */
    public void openLights(View view) {
        Intent intent = new Intent(this, LightsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void openThermostat(View view){
        Intent intent = new Intent(this, ThermostatActivity.class);
        startActivity(intent);
    }
//    public void toggleLight(View view){
//        pairedDevices = mBluetoothAdapter.getBondedDevices();
//        BluetoothSocket mmSocket;
//        BluetoothDevice mmDevice = btDeviceArray.get(0);*//*
//        try {
//        	char[] buffer = new char[1];
//        	switch(view.getId()){
//                case R.id.Button1: //light on
//                    buffer[0] = '1';
//                    tD[0].setState(true);
//                    break;
//                case R.id.Button2: //light off
//                    buffer[0] = '2';
//                    tD[0].setState(false);
//                    break;
//                case R.id.ToggleButton1:
//                    if(tD[0].getState()){buffer[0] = '2';}
//                    else {buffer[0] = '1';}
//                    tD[0].toggleState();
//                    break;
//        	}
//            String mmUUID = "00001101-0000-1000-8000-00805F9B34FB";
//            mmSocket = mmDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString(mmUUID));
//            mmSocket.connect();*//*
//            byte [] buffer2 = new byte[1];
//            Toast.makeText(getApplicationContext(),String.valueOf(buffer[0]),Toast.LENGTH_SHORT).show();
//            OutputStream mmOutStream = mmSocket[0].getOutputStream();
//            InputStream mmInStream = mmSocket[0].getInputStream();
//            mmOutStream.write(buffer[0]);
//            mmInStream.read(buffer2);
//
//            Log.d(""," Read the following : '"+(char)buffer2[0]+"'");
//            mmSocket.close();*//*
//        } catch (Exception e) {
//            Log.d("","Failed to toggle light");
//            reconnect();
//            Toast.makeText(getApplicationContext(),"Error: Device not connected",Toast.LENGTH_SHORT).show();
//        }
//    }
}
