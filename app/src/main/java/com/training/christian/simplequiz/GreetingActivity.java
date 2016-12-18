package com.training.christian.simplequiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class GreetingActivity extends AppCompatActivity {

    public static final String CURRENT_QUESTION = "currentQuestion";
    public static final String CHOICES = "choices";
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
    private String mPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        //1. Odczytanie przekazanego parametru
        mPlayerName = getIntent().getStringExtra(StartGameActivity.EXTRA_NAME);
        mQuestions = (List<Question>) getIntent().getSerializableExtra(StartGameActivity.EXTRA_QUESTIONS);
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
        outState.putInt(CURRENT_QUESTION, mCurrentQuestion);
        outState.putIntArray(CHOICES, mChoices);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentQuestion = savedInstanceState.getInt(CURRENT_QUESTION, 0);
        mChoices = savedInstanceState.getIntArray(CHOICES);

        refreshView();
    }

    private void onBackClick() {
        mChoices[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();
        boolean isLastQuestion = mCurrentQuestion - 1 < 0;
        if (isLastQuestion) {
            countResult();
            ;
            return;
        }
        //zapisanie opdowiedzi na wybranej odpowiedzi
        mCurrentQuestion--;
        refreshView();
    }

    private void onNextClick() {
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
        }
        //Toast.makeText(this, String.format("Wynik: %d/%d", correctAnswers, questionsCount), Toast.LENGTH_LONG).show();
        //Szybkie powdaomienie typu toast

        QuizResultDialogFragment.createDialog(mPlayerName, correctAnswers, questionsCount)
                .show(getSupportFragmentManager(), "Tag1");
    }
}

//Test
//Test