package com.example.prinks;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;

public class CategorySelect extends AppCompatActivity {

    Button next, back;
    ChipGroup categories;
    RadioGroup gameLengthRadioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);

        // Hold on to players selected
        ArrayList<String> players = (ArrayList<String>) getIntent().getSerializableExtra("players");

        // Add all json prompts as PromptType objects
        ArrayList<PromptType> promptTypes = new ArrayList<>();
        promptTypes.add(new PromptType("StandardPrompts.json",false));
        promptTypes.add(new PromptType("Virtual.json",false));
        promptTypes.add(new PromptType("UwuPrompts.json", false));
        promptTypes.add(new PromptType("Kings.json", false));

        categories = findViewById(R.id.categoryGroup);
        next = findViewById(R.id.selectCategoryButton);
        next.setOnClickListener(v -> {
            // Include PromptType if their button is selected,
            for (int i=0; i<categories.getChildCount(); i++){
                Chip chip = (Chip) categories.getChildAt(i);
                if (chip.isChecked()){
                    promptTypes.get(i).setOn(true);
                }
            }

            // get selected game length from gameLengthRadioGroup
            gameLengthRadioGroup = findViewById(R.id.lengthOfGameGroup);
            int selectedId = gameLengthRadioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);
            CharSequence gameLength = radioButton.getText();

            //Start the game
            Intent intent = new Intent( CategorySelect.this, MainActivity.class);
            intent.putStringArrayListExtra("players", players);
            intent.putExtra("promptTypes", promptTypes);
            intent.putExtra("gameLength", gameLength);
            startActivity(intent);
            finish();
        });

        // On back pressed
        back = findViewById(R.id.backFromCategorySelct);
        back.setOnClickListener(v -> {
            Intent intent = new Intent( CategorySelect.this, SetupPlayers.class);
            startActivity(intent);
        });
    }

}