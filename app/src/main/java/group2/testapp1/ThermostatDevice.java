package group2.testapp1;

import android.bluetooth.BluetoothDevice;

/**
 * A thermostat module that holds temperature information, default Celsius.
 */
public class ThermostatDevice extends Device{ //if thermostat receives a '4', will send latest temperature

    float temperature = 0;

    //constructor
    public ThermostatDevice(String name, int id, BluetoothDevice device){
        super(name, id, device);
    }

    public void setTemperature(float t){
        this.temperature = t;
    }

    public float getTemperatureC(){
        return this.temperature;
    }

    public float getTemperatureF() { return this.temperature * 9 / 5 + 32;}
}
