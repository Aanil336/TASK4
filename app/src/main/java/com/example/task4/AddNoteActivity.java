package com.example.task4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextName, editTextContent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextName = findViewById(R.id.editTextName);
        editTextContent = findViewById(R.id.editTextContent);
        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);
    }

    public void saveNote(View view) {
        String name = editTextName.getText().toString();
        String content = editTextContent.getText().toString();

        if (name.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, R.string.empty_warning, Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, content);
        editor.apply();

        finish();
    }
}