package com.training.christian.simplequiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class QuizResultDialogFragment extends DialogFragment {

    public static final String PLAYER_NAME = "playerName";
    public static final String CORRECT_ANSWERS = "correctAnswers";
    public static final String QUESTIONS_COUNT = "questionsCount";

    public static QuizResultDialogFragment createDialog(String playerName,
                                                        int correctAnswers,
                                                        int questionsCount) {
        QuizResultDialogFragment fragment = new QuizResultDialogFragment();
        Bundle args = new Bundle();
        args.putString(PLAYER_NAME, playerName);
        args.putInt(CORRECT_ANSWERS, correctAnswers);
        args.putInt(QUESTIONS_COUNT, questionsCount);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String mPlayerName = getArguments().getString(PLAYER_NAME);
        int correctAnswers = getArguments().getInt(CORRECT_ANSWERS);
        int questionsCount = getArguments().getInt(QUESTIONS_COUNT);

        setCancelable(false);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Wynik Quizu")
                .setMessage(String.format("Witaj %s ! Twój wynik to %d/%d !",
                        mPlayerName, correctAnswers, questionsCount))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .create();

        return super.onCreateDialog(savedInstanceState);
    }
}
