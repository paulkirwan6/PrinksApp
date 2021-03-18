package com.example.prinks;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView promptText;
    private Button nextPromptButton;
    private Button previousPromptButton;
    private int promptIndex = 0;

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        // Transfer players and selected categories to this activity
        List<String> players = (ArrayList<String>) getIntent().getSerializableExtra("players");
        List<PromptType> promptTypes = (ArrayList<PromptType>) getIntent().getSerializableExtra("promptTypes");
        String gameLength = getIntent().getCharSequenceExtra("gameLength").toString();

        // Build the prompts
        List<String> prompts = PromptBuilder.buildPrompts(context, players, gameLength, promptTypes);
        promptText = findViewById(R.id.PromptText);
        promptText.setText(prompts.get(0));

        // Move to next prompt when button is tapped until we run out of prompts
        nextPromptButton = findViewById(R.id.NextPromptButton);
        nextPromptButton.setOnClickListener(v -> {
            if (promptIndex >= prompts.size() - 1) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Game Over",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else {
                promptIndex++;
                promptText.setText(prompts.get(promptIndex));
            }
        });

        //Move to previous prompt when button is tapped until we reach the start
        previousPromptButton = findViewById(R.id.PreviousPromptButton);
        previousPromptButton.setOnClickListener(v -> {
            if (promptIndex == 0) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "This is the first prompt.",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else {
                promptIndex--;
                promptText.setText(prompts.get(promptIndex));
            }
        });
    }
}