package com.example.jurnalkelompok2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class WritingJournal extends AppCompatActivity {

    private EditText judul, isi;
    private Button saveButton, exitButton;
    private TextView time;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Predefined format

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_writing_jurnal);

        judul = findViewById(R.id.judul);
        isi = findViewById(R.id.isi);
        saveButton = findViewById(R.id.save);
        exitButton = findViewById(R.id.exitButton);
        time = findViewById(R.id.time);

        time.setText(today());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String journalTitle = judul.getText().toString();
                String journalContent = isi.getText().toString();

                if (journalTitle.isEmpty() || journalContent.isEmpty()) {
                    Toast.makeText(WritingJournal.this, "Please fill both title and content", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean errorOccurred = false;

                try {
                    String filename = generateUniqueFilename(journalTitle);
                    File fileDir = getApplicationContext().getFilesDir();
                    FileWriter writer = new FileWriter(getApplicationContext().getFilesDir() + "/" + filename);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write(journalTitle + "\n" + journalContent);
                    bufferedWriter.close();
                    Toast.makeText(WritingJournal.this, "Journal saved successfully!", Toast.LENGTH_SHORT).show();
                    judul.setText("");
                    isi.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("WritingJournal", "Error saving journal data: " + e.getMessage());
                    Toast.makeText(WritingJournal.this, "Error saving journal!", Toast.LENGTH_SHORT).show();
                    errorOccurred = true;
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity (WritingJournal)
            }
        });
    }

    private String today() {
        LocalDate today = LocalDate.now(); // Get current date
        return today.format(DATE_FORMAT); // Use predefined format
    }

    private String generateUniqueFilename(String title) {
        LocalDate today = LocalDate.now(); // Get current date
        LocalTime time = LocalTime.now(); // Get current time

        // Format date and time strings using predefined formatters
        String dateString = today.format(DATE_FORMAT);
        String timeString = time.format(DateTimeFormatter.ofPattern("HHmmss"));

        return String.format("%s_%s.txt", title.replaceAll("[^a-zA-Z0-9\\s]", ""), dateString.replace("/", "-") + "_" + timeString); // Sanitize title and combine
    }
}
