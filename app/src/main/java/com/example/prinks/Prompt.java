package com.example.prinks;

public class Prompt {

    private String message;
    private int numPlayers;

    public Prompt(String message, int numPlayers) {
        this.message = message;
        this.numPlayers = numPlayers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public String toString() {
        return "Prompt{" +
                "message='" + message + '\'' +
                ", numPlayers=" + numPlayers +
                '}';
    }
}
