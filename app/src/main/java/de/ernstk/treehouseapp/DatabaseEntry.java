package de.ernstk.treehouseapp;

public class DatabaseEntry {
    public float Timestamp;
    public int TreehouseType;
    public int TreehouseSize;
    public int PersonCount;
    public boolean Snow;
    public int TreeSize;

    public DatabaseEntry(int treehouseType, int treehouseSize, int personCount, boolean snow, int treeSize){
        this(System.currentTimeMillis() / 1000, treehouseType, treehouseSize, personCount, snow, treeSize);
    }

    public DatabaseEntry(float timestamp, int treehouseType, int treehouseSize, int personCount, boolean snow, int treeSize){
        Timestamp = timestamp;
        TreehouseType = treehouseType;
        TreehouseSize = treehouseSize;
        PersonCount = personCount;
        Snow = snow;
        TreeSize = treeSize;
    }

    @Override
    public String toString() {
        return "DatabaseEntry{" +
                "Timestamp=" + Timestamp +
                ", TreehouseType=" + TreehouseType +
                ", TreehouseSize=" + TreehouseSize +
                ", PersonCount=" + PersonCount +
                ", Snow=" + Snow +
                ", TreeSize=" + TreeSize +
                '}';
    }
}
