package de.ernstk.treehouseapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Calendar;


public class DatabaseManager extends SQLiteOpenHelper {

    private SQLiteStatement _insertStatement;

    public DatabaseManager(Context context) {
        super(context, "history.db", null, 1);

        _insertStatement = getReadableDatabase().compileStatement(
                "INSERT INTO history (timestamp, treehouse_type, treehouse_size, person_count, snow, safety_factor, tree_size) VALUES ( ?, ?, ?, ?, ?, ?, ? )"
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
        _insertStatement.bindDouble(1, entry.Timestamp); //unix timestamp
        _insertStatement.bindLong(2, entry.TreehouseType);
        _insertStatement.bindDouble(3, entry.TreehouseSize);
        _insertStatement.bindLong(4, entry.PersonCount);
        _insertStatement.bindDouble(5, entry.SnowHeight);
        _insertStatement.bindDouble(6, entry.SafetyFactor);
        _insertStatement.bindDouble(7, entry.TreeSize);

        long idOfNewRow = _insertStatement.executeInsert();
        _insertStatement.clearBindings();
        if (idOfNewRow == -1) {
            throw new SQLException("Insertion failed");
        }
    }

    public DatabaseEntry[] GetEntries(){
        Cursor c = getReadableDatabase().rawQuery("SELECT timestamp, treehouse_type, treehouse_size, person_count, snow, safety_factor, tree_size FROM history", null);

        int resultCount = c.getCount();
        if(resultCount == 0) return new DatabaseEntry[0];

        DatabaseEntry[] entries = new DatabaseEntry[resultCount];
        int i = 0;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            float timestamp = c.getFloat(0);
            int treehouseType = c.getInt(1);
            double treehouseSize = c.getDouble(2);
            int personCount = c.getInt(3);
            double snow = c.getDouble(4);
            double safetyFactor = c.getDouble(5);
            double treeSize = c.getDouble(6);
            entries[i++] = new DatabaseEntry(timestamp, treehouseType, treehouseSize, personCount, snow, safetyFactor, treeSize);
        }
        return entries;
    }
}
