package com.training.christian.simplequiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GreetingActivity extends AppCompatActivity {

    private TextView mQuestion;
    private RadioGroup mAnswers;
    private RadioButton mAnswer1;
    private RadioButton mAnswer2;
    private RadioButton mAnswer3;
    private List<Question> mQuestions;
    private int mCurrentQuestion = 0;
    private Button mBackButton;
    private Button mNextButton;
    private int[] mChoices;
    private RadioButton[] radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        //1. Odczytanie przekazanego parametru
        String name = getIntent().getStringExtra("name");
        mQuestions = (List<Question>) getIntent().getSerializableExtra("questions");
        mChoices = new int[mQuestions.size()];

        //2. Wyświetlenie go na kontrolce TextView
        mQuestion = (TextView) findViewById(R.id.question);
        mAnswers = (RadioGroup) findViewById(R.id.answer_choice);
        mAnswer1 = (RadioButton) findViewById(R.id.answer_1);
        mAnswer2 = (RadioButton) findViewById(R.id.answer_2);
        mAnswer3 = (RadioButton) findViewById(R.id.answer_3);
        mBackButton = (Button) findViewById(R.id.back);
        mNextButton = (Button) findViewById(R.id.next);
        radioButtons = new RadioButton[]{mAnswer1, mAnswer2, mAnswer3};

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick();
            }
        });

        refreshView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mChoices[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();
        outState.putInt("currentQuestion", mCurrentQuestion);
        outState.putIntArray("choices", mChoices);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentQuestion = savedInstanceState.getInt("currentQuestion", 0);
        mChoices = savedInstanceState.getIntArray("choices");

        refreshView();
    }

    private void onBackClick() {
        mChoices[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();
        if (mCurrentQuestion - 1 < 0) {
            countResult();;
            return;
        }
        //zapisanie opdowiedzi na wybranej odpowiedzi
        mCurrentQuestion--;
        refreshView();
    }

    private void onNextClick() {
        if (mCurrentQuestion + 1 == mQuestions.size()) {
            //TODO
            return;
        }
        mChoices[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();
        mCurrentQuestion++;
        refreshView();
    }

    private void refreshView() {
        Question question = mQuestions.get(mCurrentQuestion);
        mQuestion.setText(question.getQuestion());
        //mQuestion.setText(name);
        int index = 0;
        for (RadioButton radioButton : new RadioButton[]{mAnswer1, mAnswer2, mAnswer3}) {
            radioButton.setText(question.getAnswers().get(index++));
        }

        mBackButton.setVisibility(mCurrentQuestion == 0 ? View.GONE : View.VISIBLE);
        mNextButton.setText(mCurrentQuestion == mQuestions.size() - 1 ? "Zakońz" : "Dalej");

        // Czyszczenie zaznaczenia przy przejsciach
        mAnswers.clearCheck();
        if (mChoices[mCurrentQuestion] > 0) {
            mAnswers.check(mChoices[mCurrentQuestion]);
        }

    }

    private void countResult() {
        int correctAnswers = 0;
        int questionsCount = mQuestions.size();
        for (int i = 0; i < questionsCount; i++) {
            int correctAnswerIndex = mQuestions.get(i).getTrueAnswer();
            int choiceRadioButtonId = mChoices[i];
            int choiceIndex = -1;
            for (int j = 0; j < radioButtons.length; j++) {
                if (radioButtons[j].getId() == choiceRadioButtonId) {
                    choiceIndex = j;
                    break;
                }
            }
            if (correctAnswerIndex == choiceIndex) {
                correctAnswers++;
            }
            Toast.makeText(this, String.format("Wynik: %d/%d", correctAnswers, questionsCount), Toast.LENGTH_LONG).show();
        }
    }
}
