package group2.testapp1;

import android.bluetooth.BluetoothSocket;

public class ToggleDevice extends Device {

	private boolean state = false; //default off
    private BluetoothSocket mmSocket;
    private int ID;

	public boolean getState() {
		return this.state;
	}
    public void setState(boolean state){
        this.state = state;
    }

    public ToggleDevice(BluetoothSocket mmSocket, int ID){
        this.mmSocket = mmSocket;
        this.ID = ID;
    }
    public int getID(){
        return ID;
    }
    public boolean connect(){
        try{mmSocket.connect();
            return true;}
        catch(Exception e) {return false;}
    }


    public void toggleState() {
		// TODO - implement ToggleDevice.setState

        state = !state;
		//throw new UnsupportedOperationException();
	}

}