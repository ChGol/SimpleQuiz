package com.training.christian.simplequiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class GreetingActivity extends AppCompatActivity {

    private TextView mQuestion;
    private RadioButton mAnswer1;
    private RadioButton mAnswer2;
    private RadioButton mAnswer3;
    private List<Question> mQuestions;
    private int mCurrentQuestion = 0;
    private Button mBackButton;
    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        //1. Odczytanie przekazanego parametru
        String name = getIntent().getStringExtra("name");
        mQuestions = (List<Question>) getIntent().getSerializableExtra("questions");
        //2. Wyświetlenie go na kontrolce TextView
        mQuestion = (TextView) findViewById(R.id.question);
        mAnswer1 = (RadioButton) findViewById(R.id.answer_1);
        mAnswer2 = (RadioButton) findViewById(R.id.answer_2);
        mAnswer3 = (RadioButton) findViewById(R.id.answer_3);
        mBackButton = (Button) findViewById(R.id.back);
        mNextButton = (Button) findViewById(R.id.next);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick();
            }
        });

        refreshView();
    }


    private void onBackClick() {
        if (mCurrentQuestion - 1 < 0) {
            //TODO
            return;
        }
        mCurrentQuestion--;
        refreshView();
    }

    private void onNextClick() {
        if (mCurrentQuestion + 1 == mQuestions.size()) {
            //TODO
            return;
        }
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


    }
}
