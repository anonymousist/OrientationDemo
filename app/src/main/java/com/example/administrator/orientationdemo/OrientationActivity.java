package com.example.administrator.orientationdemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrientationActivity extends ActionBarActivity {
    TextView textview=null;
    private TextView text2;
    private TextView text3;
    private TextView accx,accy,accz;
    private SensorManager sm=null;
    private Sensor aSensor=null;
    private Sensor mSensor=null;

    float[] accelerometerValues=new float[3];
    float[] magneticFieldValues=new float[3];
    float[] values=new float[3];
    float[] Rot=new float[9];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);
        textview=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);

        sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        aSensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor=sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sm.registerListener(myListener, aSensor, SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener(myListener, mSensor, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    //注意activity暂停的时候释放
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        sm.unregisterListener(myListener);
    }
    final SensorEventListener myListener=new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                accelerometerValues=event.values;
            }
            if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
                magneticFieldValues=event.values;
            }
            SensorManager.getRotationMatrix(Rot, null, accelerometerValues, magneticFieldValues);
            SensorManager.getOrientation(Rot, values);
            //经过SensorManager.getOrientation(R, values);得到的values值为弧度
            //转换为角度
            values[0]=(float)Math.toDegrees(values[0]);
            values[1]=(float)Math.toDegrees(values[1]);
            values[2]=(float)Math.toDegrees(values[2]);
            textview.setText("Azimut(Z)="+(values[0]));
            text2.setText("Pitch(X)="+(values[1]));
            text3.setText("Roll(Y)="+(values[2]));
        }};
}
