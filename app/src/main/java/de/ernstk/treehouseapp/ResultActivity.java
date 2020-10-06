package de.ernstk.treehouseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    private double calculateTreehouseWeight ( int type, double size) throws Exception {
        double treehouseWeightPerSqm;
        double treehouseWeight;

            switch (type){
            case 1:
                treehouseWeightPerSqm = 60; break;
            case 2:
                treehouseWeightPerSqm = 125; break;
            case 3:
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


    protected double calculateTotalWeight() throws Exception {
        double totalWeight;
        double treehouseWeight;
        double peopleWeight;
        double snowWeight;

        int type = 2; // intent
        double size = 30.5; // intent
        int amountPeople = 5; // intent
        double snowHeight = 2.5; //intent

        treehouseWeight = calculateTreehouseWeight(type, size);
        peopleWeight = calculatePeopleWeight(amountPeople);
        snowWeight = calculateSnowWeight(size, snowHeight);

        totalWeight = treehouseWeight + peopleWeight + snowWeight;
        return totalWeight;
    }

    protected double calculateDiameter() throws Exception {
        double totalWeight;
        double treeSqcm;
        double treeRadius;
        double treeDiameter;

        double safetyFactor = 2; // intent
        
        totalWeight = calculateTotalWeight();
        treeSqcm = totalWeight/ 200;
        treeRadius = Math.sqrt(treeSqcm / Math.PI);
        treeDiameter = treeRadius * 2;
        treeDiameter = treeDiameter * safetyFactor;
        return treeDiameter;
    }
}