package group2.testapp1;

import android.bluetooth.BluetoothSocket;
import android.bluetooth.*;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Device is an abstract class that represents one particular model such as a light or thermostat.
 * Each Device has a name, id, and BluetoothSocket.
 */
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

    public boolean connect(){
        try{Log.d("","Trying to connect inside Device");
            mmSocket.connect();
            return true;
        }
        catch(Exception e) {
            Log.d("", "Failed to Connect inside Device!");
            throw new RuntimeException("Failed to connect");
        }
    }

    public boolean disconnect() {
        try {
            Log.d("", "Trying to disconnect inside Device");
            mmSocket.close();
            return true;
        } catch (Exception e) {
            Log.d("", "Failed to Disconnect inside Device!");
            throw new RuntimeException("Failed to disconnect");
        }
    }

    /**
     * Get the OutputStream for writing bytes to the Arduino.
     * @return
     */
    protected OutputStream getOS(){
        try{ return mmSocket.getOutputStream();}
        catch (Exception e){ e.printStackTrace();}
        return null;
    }

    /**
     * Get the InputStream for receiving bytes from the Arduino.
     * @return
     */
    protected InputStream getIS(){
        try{ return mmSocket.getInputStream();}
        catch (Exception e){ e.printStackTrace();}
        return null;
    }

}