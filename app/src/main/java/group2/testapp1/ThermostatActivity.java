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
import android.widget.ToggleButton;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;


public class ThermostatActivity extends ActionBarActivity {

    private ArrayList<ThermostatDevice> thermDeviceArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("", "Inside LightsActivity.class");

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
                        thermDeviceArray.add(new ThermostatDevice(bt.getName(), id, bt));
                        Log.d("","Device Added, trying to connect");
                        if(thermDeviceArray.get(id).connect())
                            Log.d("","Device Connected!");
                        else{
                            Log.d("","Failed to connect to device " + id + " with name " + bt.getName());
                        }
                        ToggleButton button = new ToggleButton(this);
                        TextView tempDisplay;
                        button.setText(bt.getName());
                        button.setId(id);
                        button.setTextOn(bt.getName()+ " On");
                        button.setTextOff(bt.getName()+ " Off");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getTemperature(v.getId());
                            }
                        });
                        linear.addView(button);
                        id++;
                    }catch (Exception e) {e.printStackTrace(); Log.d("","Shit broke making the socket");}

                }
            }
        }
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
        for (int i = 0; i < thermDeviceArray.size(); i++){
            try{thermDeviceArray.get(i).disconnect();
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

    protected void getTemperature(int i){
        Log.d("","Trying to update temperature "+i);
        try {
            char[] bufferOut = new char[1];
            bufferOut[0] = '4'; //get temp from arduino
            byte [] bufferIn = new byte[4];

            OutputStream mmOutStream = thermDeviceArray.get(i).getOS();
            InputStream mmInStream = thermDeviceArray.get(i).getIS();
            mmOutStream.write(bufferOut[0]);
            int numBytesRead = mmInStream.read(bufferIn);

            Log.d(""," Read the following : '"+(float)bufferIn[0]+"' and read a total of " + numBytesRead + " bytes.");

            thermDeviceArray.get(i).setTemperature((float)bufferIn[0]);
        } catch (Exception e) {
            Log.d("","Failed to get temperature");
        }
    }

}
