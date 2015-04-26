package group2.testapp1;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
/**
 * Created by Christian on 4/26/2015.
 */
public class Thermostat extends Device{ //if thermostat receives a '4', will send latest temperature

    float temperature = 0;

    //constructor
    public Thermostat(String name, int id, BluetoothSocket sock){
        super(name, id, sock);
    }

    public void setTemperature(float t){
        this.temperature = t;
    }

    public float getTemperature(){
        return this.temperature;
    }
}
