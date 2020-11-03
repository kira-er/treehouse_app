package de.ernstk.treehouseapp;

public class DatabaseEntry {
    public int _id;
    public long _timestamp;
    public int _treehouseType;
    public double _treehouseSize;
    public int _personCount;
    public double _snowHeight;
    public double _safetyFactor;
    public double _treeSize;

    public DatabaseEntry(int treehouseType, double treehouseSize, int personCount, double snowHeight, double safetyFactor, double treeSize){
        this(-1, System.currentTimeMillis(), treehouseType, treehouseSize, personCount, snowHeight, safetyFactor, treeSize);
    }

    public DatabaseEntry(int id, long timestamp, int treehouseType, double treehouseSize, int personCount, double snowHeight, double safetyFactor, double treeSize){
        _id = id;
        _timestamp = timestamp;
        _treehouseType = treehouseType;
        _treehouseSize = treehouseSize;
        _personCount = personCount;
        _snowHeight = snowHeight;
        _safetyFactor = safetyFactor;
        _treeSize = treeSize;
    }
}
