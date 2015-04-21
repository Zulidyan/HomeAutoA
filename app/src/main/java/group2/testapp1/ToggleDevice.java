package group2.testapp1;

import android.bluetooth.BluetoothSocket;

public class ToggleDevice extends Device {

	private boolean state = false; //default off
    public BluetoothSocket mmSocket;
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

    public void toggleState() {
		// TODO - implement ToggleDevice.setState

        state = !state;
		//throw new UnsupportedOperationException();
	}

}