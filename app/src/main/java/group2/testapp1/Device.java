package group2.testapp1;

import android.bluetooth.BluetoothSocket;
import android.bluetooth.*;
import android.util.Log;

import java.util.UUID;

public abstract class Device {

	private String name;
	private int deviceID;
    protected BluetoothSocket mmSocket;
    private BluetoothDevice device;

    //constructor
    public Device(String name, int id, BluetoothDevice device){
        this.deviceID = id;
        this.name = name;
        this.device = device;
        try {
            this.mmSocket = device.createInsecureRfcommSocketToServiceRecord(
                    UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        }
        catch (Exception e){
        Log.d("","Failed to Connect");
        }

    }

	public String getName() {
		return this.name;
	}

	public int getID() {
		return deviceID;
	}

    public void reconnect(){
        Log.d("","Trying to reconnect in Device.java");
        try {
            this.mmSocket = device.createInsecureRfcommSocketToServiceRecord(
                    UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        }
        catch (Exception e){
            Log.d("","Failed to Reconnect in Device.java");
            throw new RuntimeException("Failed to Reconnect");
        }
    }

    public boolean connect(){
        try{Log.d("","Trying to connect inside Device");
            mmSocket.connect();
            Log.d("","mmSocket.connect() Succeeded");
            return true;
        }
        catch(Exception e) {
            Log.d("", "Failed to Connect inside Device!");
            return false;
        }
    }

    public boolean disconnect() {
        try {
            Log.d("", "Trying to disconnect inside Device");
            mmSocket.close();
            mmSocket = null;
            return true;
        } catch (Exception e) {
            Log.d("", "Failed to Disconnect inside Device!");
            throw new RuntimeException("Failed to disconnect");
        }
    }
    public void debug(){
        Log.d("","name: " + name + "\ndeviceID: " + deviceID +
                 "\nmmSocket: " + mmSocket + "\ndevice: "+device);
    }
}