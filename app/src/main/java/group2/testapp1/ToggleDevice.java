package group2.testapp1;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class ToggleDevice extends Device {

	private boolean state;

	public boolean getState() {
		return this.state;
	}
    public void setState(boolean state){
        this.state = state;
    }
    public int getId(){return super.getID();}

    public ToggleDevice(String name, int id, BluetoothDevice device){
        super(name, id, device);
        this.state = false;
    }

    public boolean connect(){
        try{
            Log.d("","Inside ToggleDevice, calling superclass.connect()");
            super.connect();
            return true;}
        catch(Exception e) {return false;}
    }

    public boolean disconnect(){
        try{
            Log.d("","Inside ToggleDevice, calling superclass.disconnect()");
            super.disconnect();
            return true;
        }
        catch(Exception e) {return false;}
    }
//    protected OutputStream getOS(){
//        try{ return mmSocket.getOutputStream();}
//        catch (Exception e){ e.printStackTrace();}
//        return null;
//    }
//
//    protected InputStream getIS(){
//        try{ return mmSocket.getInputStream();}
//        catch (Exception e){ e.printStackTrace();}
//        return null;
//    }




}