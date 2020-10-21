package de.ernstk.treehouseapp;

public class DatabaseEntry {
    public int ID;
    public long Timestamp;
    public int TreehouseType;
    public double TreehouseSize;
    public int PersonCount;
    public double SnowHeight;
    public double SafetyFactor;
    public double TreeSize;

    public DatabaseEntry(int treehouseType, double treehouseSize, int personCount, double snowHeight, double safetyFactor, double treeSize){
        this(-1, System.currentTimeMillis(), treehouseType, treehouseSize, personCount, snowHeight, safetyFactor, treeSize);
    }

    public DatabaseEntry(int id, long timestamp, int treehouseType, double treehouseSize, int personCount, double snowHeight, double safetyFactor, double treeSize){
        ID = id;
        Timestamp = timestamp;
        TreehouseType = treehouseType;
        TreehouseSize = treehouseSize;
        PersonCount = personCount;
        SnowHeight = snowHeight;
        SafetyFactor = safetyFactor;
        TreeSize = treeSize;
    }
}
