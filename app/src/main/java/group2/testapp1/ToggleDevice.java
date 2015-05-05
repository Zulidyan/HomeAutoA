package group2.testapp1;

import android.bluetooth.BluetoothDevice;

/**
 * ToggleDevice is a type of Device that simply holds a boolean state and can represent lights
 * turning on and off, a door locking and unlocking etc.
 */
public class ToggleDevice extends Device {

	private boolean state;

	public boolean getState() {
		return this.state;
	}
    public void setState(boolean state){
        this.state = state;
    }

    public ToggleDevice(String name, int id, BluetoothDevice device){
        super(name, id, device);
        this.state = false;
    }
}