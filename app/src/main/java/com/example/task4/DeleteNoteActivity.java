package com.example.task4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Map;

public class DeleteNoteActivity extends AppCompatActivity {

    private ListView listViewDeleteNotes;
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        listViewDeleteNotes = findViewById(R.id.listViewDeleteNotes);
        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);
        notesList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listViewDeleteNotes.setAdapter(adapter);

        loadNotes();

        listViewDeleteNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String noteName = notesList.get(position);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(noteName);
                editor.apply();
                finish();
            }
        });
    }

    private void loadNotes() {
        notesList.clear();
        Map<String, ?> allNotes = sharedPreferences.getAll();

        if (allNotes.isEmpty()) {
            notesList.add("No notes found.");
        } else {
            for (Map.Entry<String, ?> entry : allNotes.entrySet()) {
                notesList.add(entry.getKey());
            }
        }

        adapter.notifyDataSetChanged();
    }
}