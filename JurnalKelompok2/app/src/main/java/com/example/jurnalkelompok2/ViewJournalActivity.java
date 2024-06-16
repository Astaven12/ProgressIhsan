package com.example.jurnalkelompok2;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ViewJournalActivity extends AppCompatActivity {

    private static final String TAG = "ViewJournalActivity";
    private ListView journalListView;
    private TextView journalContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journal);

        journalListView = findViewById(R.id.journal_list_view);
        journalContentTextView = findViewById(R.id.journal_content);

        if (journalListView == null) {
            Log.e(TAG, "journalListView is null");
        }
        if (journalContentTextView == null) {
            Log.e(TAG, "journalContentTextView is null");
        }

        try {
            File journalDir = new File(getFilesDir().getPath());
            File[] journalFiles = journalDir.listFiles();

            if (journalFiles == null) {
                Log.e(TAG, "No journal files found in directory.");
                return;
            }

            ArrayList<String> journalTitles = new ArrayList<>();
            for (File file : journalFiles) {
                // Filter files to only include those with a specific extension (e.g., .txt)
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    journalTitles.add(file.getName());
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, journalTitles);
            journalListView.setAdapter(adapter);

            journalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fileName = (String) parent.getItemAtPosition(position);
                    loadJournalContent(fileName);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error loading journal files", e);
        }
    }

    private void loadJournalContent(String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            Log.e(TAG, "Error reading journal file: " + fileName, e);
        }
        journalContentTextView.setText(content.toString());
    }
}
