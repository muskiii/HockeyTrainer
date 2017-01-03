package com.dev.fabianos.hockeyt;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public final String SPINNER_VALUE = "com.dev.fabianos.hockeyt.SPINNER_VALUE";
    public final String SPINNER2_VALUE = "com.dev.fabianos.hockeyt.SPINNER2_VALUE";

    //    ---LOGGING---
    private static final String TAG = "MyActivity";

    Button start;
    Spinner spinner, spinner2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.bntStartTrainning);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        Float[] spinnerValue = new Float[]{0.5F, 1F, 1.5F, 2F, 2.5F};
        ArrayAdapter<Float> adapter = new ArrayAdapter<Float>(this, android.R.layout.simple_spinner_item,
                spinnerValue);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        Integer[] spinner2Value = new Integer[]{3, 4, 5};
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,
                spinner2Value);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

    }

    public void startTrainning(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        float value = (float) spinner.getSelectedItem();
        int value2 = (int) spinner2.getSelectedItem();
        intent.putExtra(SPINNER_VALUE, value);
        intent.putExtra(SPINNER2_VALUE,value2 );
        startActivity(intent);
    }

    ;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        start.setEnabled(false);
    }
}