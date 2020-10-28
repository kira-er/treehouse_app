package de.ernstk.treehouseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        double size = intent.getDoubleExtra("size", 0);
        int type = intent.getIntExtra("type", 0);
        int amountPeople = intent.getIntExtra("amountPeople", 0);
        double snowHeight = intent.getDoubleExtra("snow", 0);
        double safetyFactor = intent.getDoubleExtra("safetyFactor", 1);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        try {
            double treeDiameter =  calculateDiameter(type, size, amountPeople, snowHeight, safetyFactor);
            DatabaseEntry dbEntry = new DatabaseEntry(type, size, amountPeople, snowHeight, safetyFactor, treeDiameter);
            MainActivity.DatabaseManager.InsertNewEntry(dbEntry);

            Timestamp timestamp = new Timestamp(dbEntry.Timestamp);
            TextView timeStampView = findViewById(R.id.timeStampView);
            timeStampView.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(timestamp));
            TextView sizeView = findViewById(R.id.sizeView);
            sizeView.setText(df.format(dbEntry.TreehouseSize) + " " + getResources().getString(R.string.Meters_Squared_Unit));

            String[] types = getResources().getStringArray(R.array.treehousetypes_array);
            String typeText = "ERROR";
            try{
                typeText = types[dbEntry.TreehouseType];
            }catch(Exception e){} //if the array access fails, text will be ERROR
            TextView typeView = findViewById(R.id.typeView);
            typeView.setText(typeText);

            TextView amountPeopleView = findViewById(R.id.amountPeopleView);
            amountPeopleView.setText(""+dbEntry.PersonCount+" "+getResources().getString(R.string.People_unit));
            TextView snowView = findViewById(R.id.snowView);
            snowView.setText(df.format(dbEntry.SnowHeight)+" "+getResources().getString(R.string.Centimeter_unit));
            TextView safetyFactorView = findViewById(R.id.safetyFactorView);
            safetyFactorView.setText(df.format(dbEntry.SafetyFactor));
            TextView treeSizeView = findViewById(R.id.treeSizeView);
            treeSizeView.setText(df.format(dbEntry.TreeSize)+" "+getResources().getString(R.string.Centimeter_unit));
        } catch (Exception e) {
            finish();
        }
    }

    private float x1,x2,y1,y2;
    static final int MIN_HORIZONTAL_DISTANCE = 250;
    static final int MAX_VERTICAL_DISTANCE = 100;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = ev.getX();
                y1 = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = ev.getX();
                y2 = ev.getY();
                float horizontalDistance = x2 - x1;
                float verticalDistance = Math.abs(y2 - y1);
                Log.println(Log.DEBUG, "A", String.valueOf(horizontalDistance));
                if (horizontalDistance > MIN_HORIZONTAL_DISTANCE && verticalDistance < MAX_VERTICAL_DISTANCE)
                {
                    finish();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private double calculateTreehouseWeight ( int type, double size) throws Exception {
        double treehouseWeightPerSqm;
        double treehouseWeight;

        switch (type){
            case 0:
                treehouseWeightPerSqm = 60; break;
            case 1:
                treehouseWeightPerSqm = 125; break;
            case 2:
                treehouseWeightPerSqm = 155; break;
            default:
                throw new Exception("treehouse type is not valid");
        }
        treehouseWeight = treehouseWeightPerSqm * size;
        return treehouseWeight;
    }


    private double calculatePeopleWeight (int amountPeople) {
        double weightPerson = 85; //average weight of a man in Germany
        double weightPeople;

        weightPeople = amountPeople * weightPerson;
        return weightPeople;
    }


    private double calculateSnowWeight(double size, double snowHeight) {
        double snowWeight;
        int snowWeightPerSqm = 4; // weight of 1cm snow per 1m^2

        snowWeight = snowHeight * snowWeightPerSqm * size;
        return snowWeight;
    }


    protected double calculateTotalWeight(int type, double size, int amountPeople, double snowHeight) throws Exception {
        double totalWeight;
        double treehouseWeight;
        double peopleWeight;
        double snowWeight;

        treehouseWeight = calculateTreehouseWeight(type, size);
        peopleWeight = calculatePeopleWeight(amountPeople);
        snowWeight = calculateSnowWeight(size, snowHeight);

        totalWeight = treehouseWeight + peopleWeight + snowWeight;
        return totalWeight;
    }

    protected double calculateDiameter(int type, double size, int amountPeople, double snowHeight, double safetyFactor) throws Exception {
        double totalWeight;
        double treeSqcm;
        double treeRadius;
        double treeDiameter;

        totalWeight = calculateTotalWeight(type, size, amountPeople, snowHeight);
        treeSqcm = totalWeight/ 200;
        treeRadius = Math.sqrt(treeSqcm / Math.PI);
        treeDiameter = treeRadius * 2;
        treeDiameter = treeDiameter * safetyFactor;
        return treeDiameter;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.slide_out_right);
    }
}