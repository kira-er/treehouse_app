package de.ernstk.treehouseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class HistoryActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private RecyclerView _recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        _recyclerView = findViewById(R.id.historyRecyclerView);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _recyclerView.setAdapter(new HistoryListViewAdapter(this));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.slide_out_right);
    }

    private class HistoryListViewAdapter extends RecyclerView.Adapter<HistoryListViewAdapter.ViewHolder>{
        private DatabaseEntry[] _entries;
        private LayoutInflater _inflater;

        private HistoryListViewAdapter(Context context) {
            databaseManager = MainActivity.DatabaseManager;
            _entries = databaseManager.GetEntries();

            _inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = _inflater.inflate(R.layout.recycler_view_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            DatabaseEntry entry = _entries[position];
            Timestamp timestamp = new Timestamp(entry.Timestamp);
            holder.TimeStampView.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(timestamp));
            holder.SizeView.setText(df.format(entry.TreehouseSize));
            String[] types = getResources().getStringArray(R.array.treehousetypes_array);
            String typeText = "ERROR";
            try{
                typeText = types[entry.TreehouseType];
            }catch(Exception e){} //if the array access fails, text will be ERROR

            holder.TypeView.setText(typeText);
            holder.AmountPeopleView.setText(""+entry.PersonCount);
            holder.SnowView.setText(df.format(entry.SnowHeight));
            holder.SafetyFactorView.setText(df.format(entry.SafetyFactor));
            holder.TreeSizeView.setText(df.format(entry.TreeSize));
            holder.Index = entry.ID;
        }

        @Override
        public int getItemCount() {
            return _entries.length;
        }

        public void RefreshData(){
            databaseManager = MainActivity.DatabaseManager;
            _entries = databaseManager.GetEntries();

            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            int Index = -1;

            TextView TimeStampView;
            TextView SizeView;
            TextView TypeView;
            TextView AmountPeopleView;
            TextView SnowView;
            TextView SafetyFactorView;
            TextView TreeSizeView;

            ConstraintLayout DetailLayout;

            ImageButton deleteButton;

            View ItemView;

            ViewHolder(View itemView) {
                super(itemView);

                ItemView = itemView;
                ConstraintLayout layout = itemView.findViewById(R.id.itemLayout);
                layout.setOnClickListener(this);

                DetailLayout = itemView.findViewById(R.id.detailLayout);

                deleteButton = itemView.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(this);

                TimeStampView = itemView.findViewById(R.id.timeStampView);
                SizeView = itemView.findViewById(R.id.sizeView);
                TypeView = itemView.findViewById(R.id.typeView);
                AmountPeopleView = itemView.findViewById(R.id.amountPeopleView);
                SnowView = itemView.findViewById(R.id.snowView);
                SafetyFactorView = itemView.findViewById(R.id.safetyFactorView);
                TreeSizeView = itemView.findViewById(R.id.treeSizeView);
            }

            @Override
            public void onClick(View view) {
                if(view == deleteButton){
                    if(Index != -1){
                        MainActivity.DatabaseManager.RemoveEntry(Index);
                    }
                    RefreshData();
                    return;
                }

                if(DetailLayout.getVisibility() != View.VISIBLE){
                    DetailLayout.setVisibility(View.VISIBLE);
                }else{
                    DetailLayout.setVisibility(View.GONE);
                }
            }
        }
    }
}