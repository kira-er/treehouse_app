package de.ernstk.treehouseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener {

    public static DatabaseManager DatabaseManager;

    private Button _runButton;
    private ImageButton _helpButton;
    private ImageButton _historyButton;

    private SeekBar _amountPeopleSeekBar;
    private SeekBar _sizeSeekBar;

    private Spinner _typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager = new DatabaseManager(this);

        _runButton = findViewById(R.id.runButton);
        _runButton.setOnClickListener(this);
        _helpButton = findViewById(R.id.helpButton);
        _helpButton.setOnClickListener(this);
        _historyButton = findViewById(R.id.historyButton);
        _historyButton.setOnClickListener(this);

        _amountPeopleSeekBar = findViewById(R.id.amountPeopleSeekBar);
        _amountPeopleSeekBar.setOnSeekBarChangeListener(this);
        _sizeSeekBar = findViewById(R.id.sizeSeekBar);
        _sizeSeekBar.setOnSeekBarChangeListener(this);

        _typeSpinner = findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.treehousetypes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _typeSpinner.setAdapter(adapter);
        _typeSpinner.setOnItemSelectedListener(this);

        UpdateSeekbarMax();
    }

    @Override
    public void onClick(View view) {
        if(view == _runButton){
            Intent intent = new Intent(this, ResultActivity.class);

            EditText snowNumber = findViewById(R.id.snowNumber);
            EditText safetyFactorNumber = findViewById(R.id.safetyFactorNumber);

            intent.putExtra("size", GetSizeValue());
            intent.putExtra("type", _typeSpinner.getSelectedItemId());
            intent.putExtra("amountPeople", GetPeopleValue());

            try{
                intent.putExtra("snow", Double.parseDouble(snowNumber.getText().toString()));
            }catch (Exception e) {
                //todo errorpopup
                return;
            }

            try{
                intent.putExtra("safetyFactor", Double.parseDouble(safetyFactorNumber.getText().toString()));
            }catch (Exception e){
                //todo errorpopup
                return;
            }

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

    private void UpdateSeekbarMax(){
        int type = (int) _typeSpinner.getSelectedItemId();
        switch (type){
            case 0:
                SetSizeBounds(2, 15);
                break;
            case 1:
                SetSizeBounds(15, 25);
                break;
            case 2:
                SetSizeBounds(15, 40);
                break;
        }
        double size = GetSizeValue();
        SetPeopleBounds(1, (int)Math.round(size*1.5d));
        //https://thetreehouse.shop/baumhaus-bauen/baumhaus-gewicht-belastung-statik/
    }

    private int _sizeMin;
    private void SetSizeBounds(int min, int max){
        System.out.println("Size-   Bounds changed to "+min+"-"+max);
        _sizeMin = min;
        int delta = max - min;
        _sizeSeekBar.setMax(delta * 10);
    }

    private int _peopleMin;
    private void SetPeopleBounds(int min, int max){
        System.out.println("People-Bounds changed to "+min+"-"+max);
        _peopleMin = min;
        int delta = max - min;
        _amountPeopleSeekBar.setMax(delta);
    }

    private double GetSizeValue(){
        double value = _sizeSeekBar.getProgress() + _sizeMin;
        return value / 10d;
    }

    private int GetPeopleValue(){
        return _amountPeopleSeekBar.getProgress() + _peopleMin;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        System.out.println((seekBar == _sizeSeekBar ? "Size-   " : "People-") + "Value is "+i);
        if(seekBar == _sizeSeekBar) UpdateSeekbarMax();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        UpdateSeekbarMax();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}