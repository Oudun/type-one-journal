package com.veve.shotsandsugar.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.veve.shotsandsugar.DaoAccess;
import com.veve.shotsandsugar.model.Record;
import com.veve.shotsandsugar.model.SugarLevel;

import com.veve.shotsandsugar.R;

public class DiagramActivity extends DatabaseActivity {

    static SimpleDateFormat sdf = new SimpleDateFormat("M dd, yyyy HH:mm");
    List<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton backButton = (FloatingActionButton) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOne = new Intent(getApplicationContext(), MainActivity.class);
                intentOne.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentOne);
            }
        });

        try {
            records = new DiagramTask(daoAccess).execute().get();
        } catch (Exception e) {
            Log.e(getClass().getName(), e.getLocalizedMessage(), e);
        }

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return records.size();
            }

            @Override
            public Object getItem(int position) {
                return records.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.records_list, parent, false);
                }
                TextView dateTextView = convertView.findViewById(R.id.date);
                TextView recordTextView = convertView.findViewById(R.id.record);
                Record record = records.get(position);
                Date resultdate = new Date(record.getTimestamp());
//                dateTextView.setText(String.format(Locale.getDefault(), "%s",
//                        sdf.format(resultdate)));
//                recordTextView.setText(String.valueOf(record.getValue()));
                return convertView;
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        try {
            records = new DiagramTask(daoAccess).execute().get();
        } catch (Exception e) {
            Log.e(getClass().getName(), e.getLocalizedMessage(), e);
        }
    }

    static class DiagramTask extends AsyncTask<Void, Void, List<Record>> {

        DaoAccess daoAccess;

        DiagramTask(DaoAccess daoAccess) {
            this.daoAccess = daoAccess;
        }

        @Override
        protected List<Record> doInBackground(Void... voids) {
            List<Record> records = new ArrayList<Record>();
            List<SugarLevel> sugarRecords = daoAccess.fetchSugarLevels();
            for (SugarLevel sugarRecord : sugarRecords) {
                String text = String.format("Glucose level is %f mmol", sugarRecord.getValue());
//                Date resultdate = new Date(record.getTimestamp());
//                Log.d(getClass().getName(),
//                        String.format("Date %S id:%d value:%f",
//                                sdf.format(resultdate), record.getId(), record.getValue()));
            }
            return records;
        }
    }

    class Record {

        public Object getOriginalRecord() {
            return originalRecord;
        }

        public void setOriginalRecord(Object originalRecord) {
            this.originalRecord = originalRecord;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        Object originalRecord;

        long timestamp;

        String text;

    }

}
