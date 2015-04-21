package group2.testapp1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;


// Created by Matthew on 4/19/15.

public class Lights extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("", "Inside Lights.class");
        ScrollView sv1 = new ScrollView(this);
        ToggleButton l1 = new ToggleButton(this);
        l1.setText("light1");
        sv1.addView(l1);
        setContentView(sv1);

        //setContentView(R.layout.activity_display_message);
    }
}
