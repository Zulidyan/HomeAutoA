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

    public ToggleDevice(BluetoothSocket socket, int ID, String name){
        super(socket, ID, name);
    }

    public void toggleState() {
		// TODO - implement ToggleDevice.setState

        state = !state;
		//throw new UnsupportedOperationException();
	}

}