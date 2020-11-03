package de.ernstk.treehouseapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class DatabaseManager extends SQLiteOpenHelper {

    private SQLiteStatement _insertStatement;
    private SQLiteStatement _deleteStatement;

    public DatabaseManager(Context context) {
        super(context, "history.db", null, 1);

        SQLiteDatabase db = getReadableDatabase();

        _insertStatement = db.compileStatement(
                "INSERT INTO history (timestamp, treehouse_type, treehouse_size, person_count, snow, safety_factor, tree_size) VALUES ( ?, ?, ?, ?, ?, ?, ? )"
        );

        _deleteStatement = db.compileStatement(
                "DELETE FROM history WHERE entry_id=?"
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE history ( entry_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp INTEGER NOT NULL, treehouse_type INTEGER, treehouse_size FLOAT NOT NULL, person_count INTEGER NOT NULL, snow FLOAT, safety_factor FLOAT NOT NULL, tree_size FLOAT )");
            //absichtlich die erstellung eines indexes ausgelassen, da wir keine filter-select-statements ausführen, nur solche, die die gesamte datenbank abfragen
        }catch (SQLException ex){
            //todo logging
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void InsertNewEntry(DatabaseEntry entry) throws SQLException {
        _insertStatement.bindLong(1, entry._timestamp); //unix timestamp
        _insertStatement.bindLong(2, entry._treehouseType);
        _insertStatement.bindDouble(3, entry._treehouseSize);
        _insertStatement.bindLong(4, entry._personCount);
        _insertStatement.bindDouble(5, entry._snowHeight);
        _insertStatement.bindDouble(6, entry._safetyFactor);
        _insertStatement.bindDouble(7, entry._treeSize);

        long idOfNewRow = _insertStatement.executeInsert();
        _insertStatement.clearBindings();
        if (idOfNewRow == -1) {
            throw new SQLException("Insertion failed"); //todo logs
        }
    }

    public void RemoveEntry(int index){
        _deleteStatement.bindLong(1, index);
        _deleteStatement.executeInsert();
        _deleteStatement.clearBindings();
    }

    public DatabaseEntry[] GetEntries(){
        Cursor c = getReadableDatabase().rawQuery("SELECT entry_id, timestamp, treehouse_type, treehouse_size, person_count, snow, safety_factor, tree_size FROM history", null);

        int resultCount = c.getCount();
        if(resultCount == 0) return new DatabaseEntry[0];

        DatabaseEntry[] entries = new DatabaseEntry[resultCount];
        int i = 0;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            int index = c.getInt(0);
            long timestamp = c.getLong(1);
            int treehouseType = c.getInt(2);
            double treehouseSize = c.getDouble(3);
            int personCount = c.getInt(4);
            double snow = c.getDouble(5);
            double safetyFactor = c.getDouble(6);
            double treeSize = c.getDouble(7);
            entries[resultCount-(1+i++)] = new DatabaseEntry(index, timestamp, treehouseType, treehouseSize, personCount, snow, safetyFactor, treeSize);
        }

        return entries;
    }
}
