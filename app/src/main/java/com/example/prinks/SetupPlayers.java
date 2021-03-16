package com.example.prinks;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class SetupPlayers extends AppCompatActivity {

    private final List<EditText> names = new ArrayList<>();
    private static ArrayList<String> players;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_players);

        names.add(findViewById(R.id.editTextName));
        names.add(findViewById(R.id.editTextName2));
        names.add(findViewById(R.id.editTextName3));
        names.add(findViewById(R.id.editTextName4));
        names.add(findViewById(R.id.editTextName5));
        names.add(findViewById(R.id.editTextName6));

        button = findViewById(R.id.SetupPlayersButton);
        button.setOnClickListener(v -> {
            players = createPlayers();
            if (players.size() < 2) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Enter at least two players.",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Intent intent = new Intent( SetupPlayers.this, CategorySelect.class);
                intent.putStringArrayListExtra("players", players);
                startActivity(intent);
                finish();
            }
        });
    }

    private ArrayList<String> createPlayers() {
        ArrayList<String> players = new ArrayList<>();
        for (EditText name : names) {
            String player = name.getText().toString();
            if (!player.isEmpty()) {
                players.add(player);
            }
        }
        return players;
    }
}