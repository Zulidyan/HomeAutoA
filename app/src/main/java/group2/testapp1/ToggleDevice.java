package group2.testapp1;

import android.bluetooth.BluetoothSocket;

public class ToggleDevice extends Device {

	private boolean state = false; //default off

	public boolean getState() {
		return this.state;
	}

    public void setState(boolean state){
        this.state = state;
    }

    public BluetoothSocket mmSocket[];

    char[] buffer = new char[1];


    public ToggleDevice(BluetoothSocket mmSocket[]){
        this.mmSocket = mmSocket;
    }

    public void toggleState() {
		// TODO - implement ToggleDevice.setState

        state = !state;
		throw new UnsupportedOperationException();
	}

}