package com.veve.typeone.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.veve.typeone.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryActivity extends DatabaseActivity {

    static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());

    Cursor cursor;

    ListView diaryRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        diaryRecords = findViewById(R.id.diaryRecords);

        FloatingActionButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryActivity.this, DiaryRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        RecordsGetterTask recordsGetterTask = new RecordsGetterTask();
        recordsGetterTask.execute();

    }

    class RecordsGetterTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return daoAccess.fetchRawThreeColRecords();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            diaryRecords.setAdapter(new CursorAdapter(getApplicationContext(), cursor, true) {
                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    View view = getLayoutInflater().inflate(R.layout.three_cols_record, null);
                    ((TextView)view.findViewById(R.id.time))
                            .setText(String.format(Locale.getDefault(), "%s",
                                    sdf.format(new Date(cursor.getLong(1)))));
                    ((TextView)view.findViewById(R.id.glucose))
                            .setText(String.valueOf(cursor.getFloat(2)));
                    ((TextView)view.findViewById(R.id.insulin))
                            .setText(String.valueOf(cursor.getFloat(3)));
                    ((TextView)view.findViewById(R.id.meal))
                            .setText(String.valueOf(cursor.getLong(4)));
                    return view;
                }

                @Override
                public void bindView(View view, Context context, Cursor cursor) {

                }
            });
        }
    }

}