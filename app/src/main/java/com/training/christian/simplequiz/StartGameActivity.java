package com.training.christian.simplequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StartGameActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_QUESTIONS = "questions";

    private EditText mEditText;
    private Button mNextButton;

    private QuestionDatabase questionDatabase = new RandomQuestionDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        mEditText = (EditText) findViewById(R.id.name_field);
        mNextButton = (Button) findViewById(R.id.next_button);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextScreen();
            }
        });
    }

    private void openNextScreen() {
        // 1. Odczytać wpisany tekst
        String name = mEditText.getText().toString();
        // 2. Otworzyć nowe okno przekazując wpisany tekst
        Intent nameIntent = new Intent(this, GreetingActivity.class);
        nameIntent.putExtra("name", name);
        // Losowanie pytań
        List<Question> questions = questionDatabase.getQuestions();
        Random random = new Random();
        while (questions.size() > 5) {
            //Usuwa losowy elemnet z listy
            questions.remove(random.nextInt(questions.size()));

        }
        Collections.shuffle(questions);
        nameIntent.putExtra("questions", new ArrayList<>(questions));
        startActivity(nameIntent);
    }
}
