package com.flashy.server.core;

public class Flashcard {

    private String question;
    private String answer;
    private int cardID;

    public Flashcard(String question, String answer, int cardID) {
        this.question = question;
        this.answer = answer;
        this.cardID = cardID;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getCardID() {
        return cardID;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    // Override equals() to compare two flashcards
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true; // return true if comparing object to itself
        }

        if (!(obj instanceof Flashcard)) {
            return false; // return false if obj not an instance of Flashcard
        }

        // typecast obj to Flashcard so that we can compare data
        Flashcard card = (Flashcard) obj;

        return this.cardID == card.cardID;
    }

}
