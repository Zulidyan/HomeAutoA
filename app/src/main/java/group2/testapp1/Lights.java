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
import android.widget.ToggleButton;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


// Created by Matthew on 4/19/15.

public class Lights extends ActionBarActivity {

    private ArrayList<ToggleDevice> tdDeviceArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("", "Inside Lights.class");

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
                    try{
                        Log.d("","Trying to add a new ToggleDevice " + bt.getName());
                        tdDeviceArray.add(new ToggleDevice(bt.getName(), id,
                                bt.createInsecureRfcommSocketToServiceRecord(
                                        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))));
                        Log.d("","Device Added, trying to connect");
                        if(tdDeviceArray.get(id).connect())
                            Log.d("","Device Connected!");
                        else{
                            Log.d("","Failed to connect to device " + id + " with name " + bt.getName());
                        }
                        ToggleButton button = new ToggleButton(this);
                        button.setText(bt.getName());
                        button.setId(id);
                        button.setTextOn(bt.getName()+ " On");
                        button.setTextOff(bt.getName()+ " Off");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toggleLight(v.getId());
                            }
                        });
                        linear.addView(button);
                        id++;
                    }catch (Exception e) {e.printStackTrace(); Log.d("","Shit broke making the socket");}

                }
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
    protected void onPause(){
        super.onPause();
        for (int i = 0; i < tdDeviceArray.size(); i++){
            try{tdDeviceArray.get(i).disconnect();
                Log.d("","Closed socket '"+i+"' onPause()");
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
        inflater.inflate(R.menu.menu_reconnect, menu);
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

        if (id == R.id.reconnect){
            reconnect();
        }


        return super.onOptionsItemSelected(item);
    }

      protected void reconnect(){
        for (int device = 0; device < tdDeviceArray.size(); device++) {
            //TODO Figure out why this isn't working properly
            Log.d("","Trying to reconnect to lost devices");
            try {
                tdDeviceArray.get(device).connect();
                Log.d("","Reconnected to device "+device);
            } catch (Exception e) {
                Log.d("","Failed to reconnect to device "+device);
            }
        }
    }
    protected void toggleLight(int i){
        Log.d("","Trying to turn on/off with ID "+i);
        try {
            char[] bufferOut = new char[1];
            if(tdDeviceArray.get(i).getState()) //check to see if light is on
                    bufferOut[0] = '2'; //if yes, prepare to send off signal
            else
                    bufferOut[0] = '1'; //if no, prepare to send on signal
            byte [] bufferIn = new byte[1];

            OutputStream mmOutStream = tdDeviceArray.get(i).getOS();
            InputStream mmInStream = tdDeviceArray.get(i).getIS();
            mmOutStream.write(bufferOut[0]);
            int numBytesRead = mmInStream.read(bufferIn);

            Log.d(""," Read the following : '"+(char)bufferIn[0]+"' and read a total of " + numBytesRead + " bytes.");

            if(bufferIn[0] == '1')
                tdDeviceArray.get(i).setState(true);
            else
                tdDeviceArray.get(i).setState(false);
        } catch (Exception e) {
            Log.d("","Failed to toggle light");
            //reconnect();
        }
    }

}
