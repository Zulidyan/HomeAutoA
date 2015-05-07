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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;

/**
 * Opens up the Thermostat activity to show a menu that shows the Bluetooth-connected thermostat.
 * Upon pressing the "Refresh" button, the latest temperature reading will be displayed.
 */
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
                if(bt.getName().contains("LightA")){
                    try{
                        Log.d("","Trying to add a new ThermostatDevice " + bt.getName());
                        thermDeviceArray.add(new ThermostatDevice(bt.getName(), id, bt));
                        Log.d("","Device Added, trying to connect");
                        if(thermDeviceArray.get(id).connect())
                            Log.d("","Device Connected!");
                        else{
                            Log.d("","Failed to connect to device " + id + " with name " + bt.getName());
                        }
                        Button refreshButton = new Button(this);
                        final TextView tempDisplay = new TextView(this);
                        refreshButton.setText("Refresh");
                        refreshButton.setId(id);
                        updateTemperature(id);
                        tempDisplay.setText(bt.getName() + "\n" +
                                String.valueOf(thermDeviceArray.get(id).getTemperatureC())
                                + " Degrees Celsius\n"
                                + String.valueOf(thermDeviceArray.get(id).getTemperatureF())
                                + " Degrees Fahrenheit");
                        tempDisplay.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tempDisplay.setTextSize(24);

                        refreshButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateTemperature(v.getId());
                                tempDisplay.setText(thermDeviceArray.get(v.getId()).getName() + "\n" +
                                        String.valueOf(thermDeviceArray.get(v.getId()).getTemperatureC())
                                        + " Degrees Celsius\n"
                                        + String.valueOf(thermDeviceArray.get(v.getId()).getTemperatureF())
                                        + " Degrees Fahrenheit");
                                //updateTemperature(id);
                            }
                        });
                        linear.addView(tempDisplay);
                        linear.addView(refreshButton);
                        id++;
                    }catch (Exception e) {
                        Log.d("", "Failed to connect to device " + id + " with name " + bt.getName());
                        thermDeviceArray.remove(id);
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
        else {
            TextView error = new TextView(this);
            error.setText("Please Enable Bluetooth First");
            error.setTextSize(24);
            linear.addView(error);
        }
          //linear.addView(l1);
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
        for (int i = 0; i < thermDeviceArray.size(); i++){
            try{thermDeviceArray.get(i).disconnect();
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

    /**
     * Get the latest temperature reading and update the state of the device
     * @param i The id of the device
     */
    protected void updateTemperature(int i){
        Log.d("","Trying to update temperature of id"+i);
        try {
            char[] bufferOut = new char[1];
            bufferOut[0] = '4'; //get temp from arduino
            byte [] bufferIn1 = new byte[1];
            byte [] bufferIn2 = new byte[5];

            OutputStream mmOutStream = thermDeviceArray.get(i).getOS();
            InputStream mmInStream = thermDeviceArray.get(i).getIS();
            mmOutStream.write(bufferOut[0]);
            int numBytesRead = mmInStream.read(bufferIn1);
            Log.d(""," Read the following : '"+(char)bufferIn1[0]+"' and read a total of " + numBytesRead + " bytes.");
            numBytesRead = mmInStream.read(bufferIn2);
            Log.d(""," Read a total of " + numBytesRead + " bytes.");
            Log.d("",bufferIn2[0] + " " + bufferIn2[1] + " " + bufferIn2[2] + " " +
                     bufferIn2[3] + " " + bufferIn2[4]);
            String temp = "";
            for (int j = 0; j < 5; j++){
                temp = temp + (char)bufferIn2[j];
            }
            Log.d("","String value : "+ temp);
            thermDeviceArray.get(i).setTemperature(Float.valueOf(temp));
        } catch (Exception e) {
            Log.d("","Failed to get temperature");
            Toast.makeText(getApplicationContext(), "Error: Failed to Update Temperature",
                    Toast.LENGTH_SHORT).show();
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(!mBluetoothAdapter.isEnabled()){
                finish();
            }
        }
    }

}
