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

    /**
     * Used to get the temperature in Celsius (default)
     * @return The temperature in Celsius
     */
    public float getTemperatureC(){
        return this.temperature;
    }

    /**
     * Used to convert a temperature reading into Fahrenheit
     * @return The temperature in Fahrenheit
     */
    public float getTemperatureF() { return this.temperature * 9 / 5 + 32;}
}
