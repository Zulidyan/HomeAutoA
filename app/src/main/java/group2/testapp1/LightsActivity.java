package group2.testapp1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;


// Created by Matthew on 4/19/15.

public class LightsActivity extends ActionBarActivity {

    private ArrayList<ToggleDevice> tdDeviceArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("", "Inside LightsActivity.onCreate()");

        BluetoothAdapter mBluetoothAdapter;
        Set<BluetoothDevice> pairedDevices;

        ScrollView sv1 = new ScrollView(this);
        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter.isEnabled()) {
            pairedDevices = mBluetoothAdapter.getBondedDevices();
            Log.d("","pairedDevices.size() = " + pairedDevices.size());
            int id = 0;
            for (BluetoothDevice bt : pairedDevices) {
                if(bt.getName().contains("Light")){
                    try {
                        Log.d("", "Trying to add a new ToggleDevice " + bt.getName());
                        tdDeviceArray.add(new ToggleDevice(bt.getName(), id, bt));
//                        tdDeviceArray.add(new ToggleDevice(bt.getName(), id,
//                                bt.createInsecureRfcommSocketToServiceRecord(
//                                        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))));
                        Log.d("", "Device Added, trying to connect");
                        if (tdDeviceArray.get(id).connect()) {
                            Log.d("", "Device Connected!");
                        }
                        else {
                            Log.d("", "Failed to connect to device " + id + " with name " + bt.getName());
                        }

                        final ToggleButton button = new ToggleButton(this);
                        button.setText(bt.getName());
                        button.setId(id);
                        button.setTextOn(bt.getName() + " On");
                        button.setTextOff(bt.getName() + " Off");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toggleLight(v.getId(), button);
                            }
                        });

                        char[] bufOut = new char[1];
                        bufOut[0] = '3';
                        byte[] bufIn = new byte[1];
                        OutputStream mmOutStream = tdDeviceArray.get(id).getOS();
                        InputStream mmInStream = tdDeviceArray.get(id).getIS();
                        mmOutStream.write(bufOut[0]);
                        int numBytesRead = mmInStream.read(bufIn);
                        Log.d("", " Read the following : '" + (char) bufIn[0] + "' and read a total of "
                                + numBytesRead + " bytes while initializing.");
                        numBytesRead = mmInStream.read(bufIn);
                        Log.d("", " Read the following : '" + (char) bufIn[0] + "' and read a total of "
                                + numBytesRead + " bytes while initializing.");

                        if (bufIn[0] == '1'){
                            tdDeviceArray.get(id).setState(true);
                            button.setChecked(true);
                        }
                        else if (bufIn[0] == '0') {
                            tdDeviceArray.get(id).setState(false);
                            button.setChecked(false);
                        }
                        linear.addView(button);
                        id++;
                    }catch (Exception e) {
                        Log.d("", "Failed to connect to device " + id + " with name " + bt.getName());
                        tdDeviceArray.remove(id);
                    }

                }
            }
            if (id == 0){
                TextView error = new TextView(this);
                error.setText("No devices found");
                error.setTextSize(24);
                linear.addView(error);
            }
        }

        //create intent
        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);*/
        //create textView
//        TextView textView = new TextView(this);
//        textView.setTextSize(20);
//        textView.setText("test");
        //set the view




//        ToggleButton l1 = new ToggleButton(this);
//        l1.setText("Light 1");
//        l1.setTextOff("Light 1 Off");
//        l1.setTextOn("Light 1 On");
//        l1.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                toggleLight(1);
//            }
//        });
//        linear.addView(l1);
//        ToggleButton l2 = new ToggleButton(this);
//        l2.setText("Light 2");
//        l2.setTextOff("Light 2 Off");
//        l2.setTextOn("Light 2 On");
//        l2.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                toggleLight(2);
//            }
//        });
//        linear.addView(l2);
        sv1.addView(linear);
        setContentView(sv1);

        //setContentView(R.layout.activity_display_message);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        for (int i = 0; i < tdDeviceArray.size(); i++){
            try{tdDeviceArray.get(i).disconnect();
                Log.d("","Closed socket '"+i+"' onDestroy()");
            }
            catch (Exception e){
                Log.d("", "Failed to close socket "+ i);
            }
        }
    }

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

    protected void toggleLight(int i, ToggleButton b){
        Log.d("","Trying to turn on/off with ID "+i);
        try {
            char[] bufferOut = new char[1];
            if(tdDeviceArray.get(i).getState()) //check to see if light is on
                    bufferOut[0] = '2'; //if yes, prepare to send off signal
            else
                    bufferOut[0] = '1'; //if no, prepare to send on signal
            byte [] bufferIn = new byte[1];
            Log.d(""," Sending the following : '"+bufferOut[0]+"'");
            OutputStream mmOutStream = tdDeviceArray.get(i).getOS();
            InputStream mmInStream = tdDeviceArray.get(i).getIS();
            mmOutStream.write(bufferOut[0]);
            int numBytesRead = mmInStream.read(bufferIn);

            Log.d(""," Read the following : '"+(char)bufferIn[0]+"' and read a total of " + numBytesRead + " bytes.");

            if(bufferIn[0] == '1')
                tdDeviceArray.get(i).setState(true);
            else
                tdDeviceArray.get(i).setState(false);

            Log.d("","Current state is :"+ tdDeviceArray.get(i).getState());
        } catch (Exception e) {
            Log.d("","Failed to toggle light");
            b.setChecked(tdDeviceArray.get(i).getState());
            Toast.makeText(getApplicationContext(), "Failed To Toggle " +
                    tdDeviceArray.get(i).getName(), Toast.LENGTH_SHORT).show();
            //reconnect();
        }
    }

}
