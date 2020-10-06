package de.ernstk.treehouseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button _runButton;
    private ImageButton _helpButton;
    private ImageButton _historyButton;
    private DatabaseManager _databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _databaseManager = new DatabaseManager(this);

        _runButton = findViewById(R.id.runButton);
        _runButton.setOnClickListener(this);
        _helpButton = findViewById(R.id.helpButton);
        _helpButton.setOnClickListener(this);
        _historyButton = findViewById(R.id.historyButton);
        _historyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == _runButton){
            Intent intent = new Intent(this, ResultActivity.class);
            //add data here
            //intent.putExtra()
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.do_nothing);
        }else if(view == _helpButton){
            startActivity(new Intent(this, HelpActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.do_nothing);
        }else if(view == _historyButton){
            startActivity(new Intent(this, HistoryActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.do_nothing);
        }
    }
}