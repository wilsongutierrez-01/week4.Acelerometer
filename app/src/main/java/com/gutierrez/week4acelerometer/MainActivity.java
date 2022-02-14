package com.gutierrez.week4acelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Vars
    SensorManager sm;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    int whip = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        final TextView sound = (TextView) findViewById(R.id.tvSon);
        if (sensor == null) finish();


        sensorEventListener = new SensorEventListener(){

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                if(x < -5  && whip == 0){
                    whip ++;
                    sound.setText("Sound " + whip);
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    soundL();

                }else if (x > 5 && whip == 1){
                    whip ++;
                    sound.setText("Sound " + whip );
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
                if( whip == 2){
                    whip = 0;
                    sound();
                    sound.setText("Sound " + whip);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        start();
    }

    private void sound (){
        MediaPlayer media = MediaPlayer.create(this, R.raw.latigo);
        media.start();
    }
    private void soundL (){
        MediaPlayer media = MediaPlayer.create(this, R.raw.latigoo);
        media.start();
    }

    private void start (){
        sm.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop () {
        sm.unregisterListener(sensorEventListener);
    }


    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}