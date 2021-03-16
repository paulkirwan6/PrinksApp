package com.example.prinks;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PromptBuilder {

    public static List<String> buildPrompts(Context context, List<String> players, String gameLength, List<PromptType> promptsOn) {

        List<Prompt> promptList = new ArrayList();
        List<String> builtPrompts = new ArrayList<>();
        boolean kingsSelected = false;

        // Add prompts from the selected categories
        for (PromptType category : promptsOn) {
            if (category.isOn()) {
                String json = loadData(context, category.getJson());
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Prompt>>(){}.getType();
                promptList.addAll(gson.fromJson(json, listType));
                if (category.getJson().toLowerCase().contains("kings"))
                    kingsSelected = true;
            }
        }

        //Remove prompts for 3 people if any less people are playing
        for(int i = 0; i < promptList.size(); i++) {
            if (players.size() < 3 && promptList.get(i).getNumPlayers() == 3) {
                promptList.remove(i--);
            }
        }

        //Randomise the order the prompts will appear
        Collections.shuffle(promptList);

        // Short, medium or long game
        List<Prompt> gameLengthPromptList;
        if (gameLength.toLowerCase().contains("short")) {
            gameLengthPromptList = promptList.stream().limit(30).collect(Collectors.toList());
        }
        else if (gameLength.toLowerCase().contains("medium")) {
            gameLengthPromptList = promptList.stream().limit(52).collect(Collectors.toList());
        }
        else { // All prompts
            gameLengthPromptList = promptList;
        }

        // If Kings is a selected category, add the final King at the very end.
        if (kingsSelected)
            gameLengthPromptList.add(new Prompt("Final King: %s must drink the King's cup.",1));

        //Add player names to prompts
        for (Prompt prompt: gameLengthPromptList) {

            String message = prompt.getMessage();
            int numPlayers = prompt.getNumPlayers();
            List<String> randomNames = randomNames(players, numPlayers);

            if (numPlayers == 1) {
                String newMessage = String.format(message, randomNames.get(0));
                builtPrompts.add(newMessage);
            } else if (numPlayers == 2) {
                String newMessage = String.format(message, randomNames.get(0), randomNames.get(1));
                builtPrompts.add(newMessage);
            } else if (numPlayers == 3) {
                String newMessage = String.format(message, randomNames.get(0), randomNames.get(1), randomNames.get(2));
                builtPrompts.add(newMessage);
            }else {
                builtPrompts.add(prompt.getMessage());
            }
        }
        builtPrompts.add("Game Over");
        return builtPrompts;
    }

    // Load json file to String
    private static String loadData(Context context, String file) {
        String tContents = "";
        try {
            InputStream stream = context.getAssets().open(file);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tContents;
    }

    //Return n random names without repeats from list of names
    public static List<String> randomNames(List<String> list, int n) {
        Collections.shuffle(list);
        List<String> answer = new ArrayList<>();
        for (int i = 0; i < n; i++)
            answer.add(list.get(i));
        return answer;
    }
}
