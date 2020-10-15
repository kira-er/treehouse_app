package de.ernstk.treehouseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class HistoryActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private RecyclerView _recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        databaseManager = MainActivity.DatabaseManager;
        DatabaseEntry[] entries = databaseManager.GetEntries();

        _recyclerView = findViewById(R.id.historyRecyclerView);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _recyclerView.setAdapter(new HistoryListViewAdapter(this, entries));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.slide_out_right);
    }

    private class HistoryListViewAdapter extends RecyclerView.Adapter<HistoryListViewAdapter.ViewHolder>{
        private DatabaseEntry[] _entries;
        private LayoutInflater _inflater;

        private HistoryListViewAdapter(Context context, DatabaseEntry[] entries) {
            _entries = entries;
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
            holder.TimeStampView.setText("<b>Timestamp:</b>\n" + entry.Timestamp);
            holder.SizeView.setText("<b>Size:</b>\n" +df.format(entry.TreehouseSize));
            holder.TypeView.setText("<b>Type:</b>\n" +entry.TreehouseType);
            holder.AmountPeopleView.setText("<b>People:</b>\n" + entry.PersonCount);
            holder.SnowView.setText("<b>Snow:</b>\n" + df.format(entry.SnowHeight));
            holder.SafetyFactorView.setText("<b>Safetyfactor:</b>\n" + df.format(entry.SafetyFactor));
            holder.TreeSizeView.setText("<b>TreeSize:</b>\n" + df.format(entry.TreeSize));
        }

        @Override
        public int getItemCount() {
            return _entries.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView TimeStampView;
            TextView SizeView;
            TextView TypeView;
            TextView AmountPeopleView;
            TextView SnowView;
            TextView SafetyFactorView;
            TextView TreeSizeView;


            ViewHolder(View itemView) {
                super(itemView);
                TimeStampView = itemView.findViewById(R.id.timeStampView);
                SizeView = itemView.findViewById(R.id.sizeView);
                TypeView = itemView.findViewById(R.id.typeView);
                AmountPeopleView = itemView.findViewById(R.id.amountPeopleView);
                SnowView = itemView.findViewById(R.id.snowView);
                SafetyFactorView = itemView.findViewById(R.id.safetyFactorView);
                TreeSizeView = itemView.findViewById(R.id.treeSizeView);
            }
        }
    }
}