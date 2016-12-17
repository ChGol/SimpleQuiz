package com.training.christian.simplequiz;


import java.util.List;

public class Question {

    private String question;
    private List<String> answers;
    private int trueAnswer;

    public Question() {
    }

    public Question(String question, List<String> answers, int trueAnswer) {
        this.question = question;
        this.answers = answers;
        this.trueAnswer = trueAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(int trueAnswer) {
        this.trueAnswer = trueAnswer;
    }
}
