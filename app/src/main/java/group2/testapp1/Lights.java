package group2.testapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;


// Created by Matthew on 4/19/15.

public class Lights extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("", "Inside Lights.class");
        //create intent
        Intent intent = getIntent();
        //String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);*/
        //create textView
        /*TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText("test");*/
        //set the view
        ScrollView sv1 = new ScrollView(this);
        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);
        ToggleButton l1 = new ToggleButton(this);
        l1.setText("Light 1"); //setText("Light " + [light id])
        l1.setTextOff("Light 1 Off");
        l1.setTextOn("Light 1 On");
        l1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                toggleLight(1);
            }
        });
        linear.addView(l1);
        ToggleButton l2 = new ToggleButton(this);
        l2.setText("Light 2");
        l2.setTextOff("Light 2 Off");
        l2.setTextOn("Light 2 On");
        l2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                toggleLight(2);
            }
        });
        linear.addView(l2);
        sv1.addView(linear);
        setContentView(sv1);
        //setContentView(R.layout.activity_display_message);
    }
    protected void toggleLight(int i){
        //TODO implement turning light on and off
        Log.d("","Trying to turn on/off "+i);
    }
}
