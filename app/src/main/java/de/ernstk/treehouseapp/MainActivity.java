package de.ernstk.treehouseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import android.widget.TextView;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener {

    public static DatabaseManager DatabaseManager;

    private Button _runButton;
    private ImageButton _helpButton;
    private ImageButton _historyButton;

    private Button _typeHelpButton;
    private Button _sizeHelpButton;
    private Button _amountPeopleHelpButton;
    private Button _snowHelpButton;
    private Button _safetyFactorHelpButton;

    private SeekBar _sizeSeekBar;
    private TextView _sizeValueIndicator;

    private SeekBar _amountPeopleSeekBar;
    private TextView _amountPeopleValueIndicator;

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

        _typeHelpButton = findViewById(R.id.typeHelpButton);
        _typeHelpButton.setOnClickListener(this);

        _sizeHelpButton = findViewById(R.id.sizeHelpButton);
        _sizeHelpButton.setOnClickListener(this);

        _amountPeopleHelpButton = findViewById(R.id.amountPeopleHelpButton);
        _amountPeopleHelpButton.setOnClickListener(this);

        _snowHelpButton = findViewById(R.id.snowNumberHelpButton);
        _snowHelpButton.setOnClickListener(this);

        _safetyFactorHelpButton = findViewById(R.id.safetyFactorHelpButton);
        _safetyFactorHelpButton.setOnClickListener(this);

        _sizeValueIndicator = findViewById(R.id.sizeValueIndicator);

        _amountPeopleValueIndicator = findViewById(R.id.amountPeopleValueIndicator);

        UpdateSeekbarMax();
        UpdateSizeValueIndicator();
        UpdatePeopleValueIndicator();
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
        }else if(view == _typeHelpButton){
            TextView desc = findViewById(R.id.typeDescription);
            if(desc.getVisibility() == View.VISIBLE){
                desc.setVisibility(GONE);
            }else{
                desc.setVisibility(View.VISIBLE);
            }
        }else if(view == _sizeHelpButton){
            TextView desc = findViewById(R.id.sizeDescription);
            if(desc.getVisibility() == View.VISIBLE){
                desc.setVisibility(GONE);
            }else{
                desc.setVisibility(View.VISIBLE);
            }
        }else if(view == _amountPeopleHelpButton){
            TextView desc = findViewById(R.id.amountPeopleDescription);
            if(desc.getVisibility() == View.VISIBLE){
                desc.setVisibility(GONE);
            }else{
                desc.setVisibility(View.VISIBLE);
            }
        }else if(view == _snowHelpButton){
            TextView desc = findViewById(R.id.snowNumberDescription);
            if(desc.getVisibility() == View.VISIBLE){
                desc.setVisibility(GONE);
            }else{
                desc.setVisibility(View.VISIBLE);
            }
        }else if(view == _safetyFactorHelpButton){
            TextView desc = findViewById(R.id.safetyFactorDescription);
            if(desc.getVisibility() == View.VISIBLE){
                desc.setVisibility(GONE);
            }else{
                desc.setVisibility(View.VISIBLE);
            }
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
    private int _sizeMax;
    private void SetSizeBounds(int min, int max){
        _sizeMin = min;
        _sizeMax = (max - min) * 10;
        _sizeSeekBar.setMax(_sizeMax);
    }

    private int _peopleMin;
    private int _peopleMax;
    private void SetPeopleBounds(int min, int max){
        _peopleMin = min;
        _peopleMax = max - min;
        _amountPeopleSeekBar.setMax(_peopleMax);
    }

    private double GetSizeValue(){
        double value = _sizeSeekBar.getProgress();
        return value / 10d + _sizeMin;
    }

    private int GetPeopleValue(){
        return _amountPeopleSeekBar.getProgress() + _peopleMin;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(seekBar == _sizeSeekBar) {
            UpdateSeekbarMax();

            UpdateSizeValueIndicator();
        }else if(seekBar == _amountPeopleSeekBar){
            UpdatePeopleValueIndicator();
        }
    }

    private void UpdateSizeValueIndicator(){
        String value = String.valueOf(GetSizeValue());
        _sizeValueIndicator.setText(value);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) _sizeValueIndicator.getLayoutParams();
        params.horizontalBias = (float)_sizeSeekBar.getProgress()/_sizeMax;
        _sizeValueIndicator.setLayoutParams(params);
    }

    private void UpdatePeopleValueIndicator(){
        String value = String.valueOf(GetPeopleValue());
        _amountPeopleValueIndicator.setText(value);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) _amountPeopleValueIndicator.getLayoutParams();
        params.horizontalBias = (float)_amountPeopleSeekBar.getProgress()/_peopleMax;
        _amountPeopleValueIndicator.setLayoutParams(params);
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