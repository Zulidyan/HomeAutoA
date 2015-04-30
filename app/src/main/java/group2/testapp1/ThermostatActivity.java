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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
                        Button button = new Button(this);
                        TextView tempDisplay = new TextView(this);
                        getTemperature(id);
                        tempDisplay.setText(bt.getName() + ": " +
                                String.valueOf(thermDeviceArray.get(id).getTemperatureC())
                                + " Degrees Celsius | "
                                + String.valueOf(thermDeviceArray.get(id).getTemperatureF())
                                + " Degrees Fahrenheit");
                        tempDisplay.setTextSize(24);
//                        button.setText(bt.getName());
//                        button.setId(id);
//                        button.setTextOn(bt.getName()+ " On");
//                        button.setTextOff(bt.getName()+ " Off");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                getTemperature(v.getId());
//                            }
                        //});
                        linear.addView(tempDisplay);
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
    protected void onDestroy(){
        super.onDestroy();
        for (int i = 0; i < thermDeviceArray.size(); i++){
            try{thermDeviceArray.get(i).disconnect();
                Log.d("","Closed socket '"+i+"' onDestroy()");
            }
            catch (Exception e){
                Log.d("", "Failed to close socket "+ i +" onDestroy()");
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
        Log.d("","Trying to update temperature of id"+i);
        try {
            char[] bufferOut = new char[1];
            bufferOut[0] = '4'; //get temp from arduino
            byte [] bufferIn = new byte[5];

            OutputStream mmOutStream = thermDeviceArray.get(i).getOS();
            InputStream mmInStream = thermDeviceArray.get(i).getIS();
            mmOutStream.write(bufferOut[0]);
            int numBytesRead = mmInStream.read(bufferIn);
            Log.d(""," Read the following : '"+(char)bufferIn[0]+"' and read a total of " + numBytesRead + " bytes.");
            numBytesRead = mmInStream.read(bufferIn);
            Log.d(""," Read a total of " + numBytesRead + " bytes.");
            Log.d("",bufferIn[0] + " " + bufferIn[1] + " " + bufferIn[2] + " " +
                     bufferIn[3] + " " + bufferIn[4]);
            String temp = "";
            for (int j = 0; j < 5; j++){
                temp = temp + (char)bufferIn[j];
            }
            Log.d("","String value : "+ temp);
            thermDeviceArray.get(i).setTemperature(Float.valueOf(temp));
        } catch (Exception e) {
            Log.d("","Failed to get temperature");
        }
    }

}
