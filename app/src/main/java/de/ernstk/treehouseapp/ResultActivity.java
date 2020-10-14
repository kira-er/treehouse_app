package de.ernstk.treehouseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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

        try {
            double treeDiameter =  calculateDiameter(type, size, amountPeople, snowHeight, safetyFactor);
            MainActivity.DatabaseManager.InsertNewEntry(new DatabaseEntry(type, size, amountPeople, snowHeight, safetyFactor, treeDiameter));
        } catch (Exception e) {
            finish();
        }
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