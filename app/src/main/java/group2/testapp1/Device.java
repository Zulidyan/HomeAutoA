package group2.testapp1;

import android.bluetooth.BluetoothSocket;

public abstract class Device {

	private String name;
	private int deviceID;
    private BluetoothSocket mmSocket;

    public BluetoothSocket getmmSocket(){
        return mmSocket;
    }

    public Device(BluetoothSocket socket, int id, String name){
        this.name = name;
        this.deviceID = id;
        this.mmSocket = socket;
    }

	//private DeviceController dController;

	public String getName() {
		return this.name;
	}

	public int getID() {
		// TODO - implement Device.getID
		throw new UnsupportedOperationException();
	}

	public boolean refresh() {
		// TODO - implement Device.refresh
		throw new UnsupportedOperationException();
	}

}