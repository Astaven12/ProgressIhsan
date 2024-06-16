package com.example.jurnalkelompok2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button jurnal;
    private Button viewJournals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        jurnal = findViewById(R.id.jurnal);
        viewJournals = findViewById(R.id.view_journals);

        jurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaJurnal = new Intent(getApplicationContext(), WritingJournal.class);
                startActivity(bukaJurnal); // Use startActivity for single intent
            }
        });

        viewJournals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewJournalIntent = new Intent(getApplicationContext(), ViewJournalActivity.class);
                startActivity(viewJournalIntent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
