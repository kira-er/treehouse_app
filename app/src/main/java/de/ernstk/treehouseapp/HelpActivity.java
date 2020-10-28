package de.ernstk.treehouseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.slide_out_right);
    }

    private float x1,x2,y1,y2;
    static final int MIN_HORIZONTAL_DISTANCE = 250;
    static final int MAX_VERTICAL_DISTANCE = 100;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = ev.getX();
                y1 = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = ev.getX();
                y2 = ev.getY();
                float horizontalDistance = x2 - x1;
                float verticalDistance = Math.abs(y2 - y1);
                Log.println(Log.DEBUG, "A", String.valueOf(horizontalDistance));
                if (horizontalDistance > MIN_HORIZONTAL_DISTANCE && verticalDistance < MAX_VERTICAL_DISTANCE)
                {
                    finish();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}