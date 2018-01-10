package com.example.test.toernooi.fragment;


import android.content.Context;
import android.hardware.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.toernooi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends Fragment implements SensorEventListener {
    private static final String TAG = "RecyclerViewFragment";

    private Button mWriteToFirebase;

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private TextView accX;
    private TextView accY;
    private TextView accZ;

    public SensorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sensor, container, false);
        rootView.setTag(TAG);

        mWriteToFirebase = (Button) rootView.findViewById(R.id.firebasebutton);
        mWriteToFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        senSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        accX = (TextView) rootView.findViewById(R.id.textX);
        accY= (TextView) rootView.findViewById(R.id.textY);
        accZ= (TextView) rootView.findViewById(R.id.textZ);

        return rootView;
    }

    public void onResume() {
        super.onResume();  // Always call the superclass method first
//        onSensorChanged();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float lastX = sensorEvent.values[0];
            float lastY= sensorEvent.values[1];
            float lastZ = sensorEvent.values[2];

            accX.setText(String.valueOf(lastX));
            accY.setText(String.valueOf(lastY));
            accZ.setText(String.valueOf(lastZ));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor event, int accuracy){

    }
}
