package group2.testapp1;

import android.bluetooth.BluetoothDevice;

public class ThermostatDevice extends Device{ //if thermostat receives a '4', will send latest temperature

    float temperature = 0;

    //constructor
    public ThermostatDevice(String name, int id, BluetoothDevice device){
        super(name, id, device);
    }

    public void setTemperature(float t){
        this.temperature = t;
    }

    public float getTemperature(){
        return this.temperature;
    }
}
