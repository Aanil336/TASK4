package com.example.task4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listViewNotes;
    private EditText editTextNote;
    private Button buttonSaveNote;
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewNotes = findViewById(R.id.listViewNotes);
        editTextNote = findViewById(R.id.editTextNote);
        buttonSaveNote = findViewById(R.id.buttonSaveNote);
        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);
        notesList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);

        loadNotes();

        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }
    private void saveNote() {
        String noteText = editTextNote.getText().toString().trim();

        if (noteText.isEmpty()) {
            Toast.makeText(this, "Please enter a note.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Note_" + System.currentTimeMillis(), noteText); // Use a unique key
        editor.apply();

        editTextNote.setText("");

        loadNotes();
    }

    private void loadNotes() {
        notesList.clear();
        Map<String, ?> allNotes = sharedPreferences.getAll();

        if (allNotes.isEmpty()) {
            notesList.add("No notes found.");
        } else {
            for (Map.Entry<String, ?> entry : allNotes.entrySet()) {
                notesList.add(entry.getValue().toString());
            }
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_add) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        } else if (itemId == R.id.action_delete) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}