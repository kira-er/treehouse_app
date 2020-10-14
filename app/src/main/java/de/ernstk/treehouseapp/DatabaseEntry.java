package de.ernstk.treehouseapp;

public class DatabaseEntry {
    public float Timestamp;
    public int TreehouseType;
    public double TreehouseSize;
    public int PersonCount;
    public double SnowHeight;
    public double SafetyFactor;
    public double TreeSize;

    public DatabaseEntry(int treehouseType, double treehouseSize, int personCount, double snowHeight, double safetyFactor, double treeSize){
        this(System.currentTimeMillis() / 1000, treehouseType, treehouseSize, personCount, snowHeight, safetyFactor, treeSize);
    }

    public DatabaseEntry(float timestamp, int treehouseType, double treehouseSize, int personCount, double snowHeight, double safetyFactor, double treeSize){
        Timestamp = timestamp;
        TreehouseType = treehouseType;
        TreehouseSize = treehouseSize;
        PersonCount = personCount;
        SnowHeight = snowHeight;
        SafetyFactor = safetyFactor;
        TreeSize = treeSize;
    }
}
