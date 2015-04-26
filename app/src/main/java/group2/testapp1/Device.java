package group2.testapp1;

import android.bluetooth.*;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Device {

	private String name;
	private int deviceID;
    protected BluetoothSocket mmSocket;

    //constructor
    public Device(String name, int id, BluetoothSocket sock){
        this.deviceID = id;
        this.name = name;
        this.mmSocket = sock;
    }

	public String getName() {
		return this.name;
	}

	public int getID() {
		return deviceID;
	}

/*	public boolean refresh() {
		throw new UnsupportedOperationException();
	}*/

    public boolean connect(){
        try{Log.d("","Trying to connect inside Device");
            mmSocket.connect();
            return true;
        }
        catch(Exception e) {
            Log.d("", "Failed to Connect inside Device!");throw new RuntimeException("Failed to connect");}
    }

    public boolean disconnect() {
        try {
            Log.d("", "Trying to disconnect inside Device");
            mmSocket.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to disconnect");
        }
    }

    protected OutputStream getOS(){
        try{ return mmSocket.getOutputStream();}
        catch (Exception e){ e.printStackTrace();}
        return null;
    }

    protected InputStream getIS(){
        try{ return mmSocket.getInputStream();}
        catch (Exception e){ e.printStackTrace();}
        return null;
    }
}