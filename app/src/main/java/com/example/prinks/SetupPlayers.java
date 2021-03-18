package com.example.prinks;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class SetupPlayers extends AppCompatActivity {

    private EditText editPlayerName;
    private final ArrayList<String> players = new ArrayList<>();
    private Button setPlayersButton, addPlayerButton, resetPlayersButton;
    private TextView addedPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_players);

        editPlayerName = findViewById(R.id.editTextName);
        addedPlayers = findViewById(R.id.addedPlayersText);

        // Add a player to the players list from the edit text when "+" is pressed
        addPlayerButton = findViewById(R.id.addPlayerButton);
        addPlayerButton.setOnClickListener(v -> {
            String name = editPlayerName.getText().toString();

            if (name.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Enter a player's name before clicking \"+\".",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                editPlayerName.setText("");
                players.add(name);
                String displayText = addedPlayers.getText().toString();
                if (players.size() < 2)
                    addedPlayers.setText(String.format("%s%s", displayText, name));
                else
                    addedPlayers.setText(String.format("%s, %s", displayText, name));
            }
        });

        // Finish setting up players when "next" is pressed
        setPlayersButton = findViewById(R.id.SetupPlayersButton);
        setPlayersButton.setOnClickListener(v -> {
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

        // Reset added players when "reset" is pressed
        resetPlayersButton = findViewById(R.id.resetPlayersButton);
        resetPlayersButton.setOnClickListener(v -> {
            players.clear();
            addedPlayers.setText("Selected Players:\n");
        });
    }
}