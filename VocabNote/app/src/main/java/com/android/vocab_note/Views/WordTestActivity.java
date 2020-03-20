package com.android.vocab_note.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vocab_note.R;
import com.android.vocab_note.ViewModels.RepositoryViewModelFactory;
import com.android.vocab_note.ViewModels.WordTestViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordTestActivity extends AppCompatActivity
{
    private TextView tvQuestion;
    private TextView tvAnswer1;
    private TextView tvAnswer2;
    private TextView tvAnswer3;
    private TextView tvAnswer4;

    private List<TextView> tvAnswerList;

    private TextView selectedAnswer;

    private WordTestViewModel viewModel;

    private Drawable defaultBackground;

    private boolean changedCorrectAnswerBackground = false;

    private static final int CORRECT_ANSWER_INDEX = 3;

    private Vibrator vibrator;

    private MenuItem itemScore;

    private int numOfQuiz = 1;
    private int numOfCorrectQuiz = 0;

    private boolean canChooseAnswer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);

        setUpView();

        viewModel = new ViewModelProvider(this, new RepositoryViewModelFactory(getApplication()))
                .get(WordTestViewModel.class);

        viewModel.getIsCorrect().observe(this, isCorrect ->
        {
            if (selectedAnswer == null)
                return;

            if (isCorrect)
            {
                selectedAnswer.setBackgroundColor(getColor(R.color.correct_answer));
                numOfCorrectQuiz++;
            }
            else
            {
                selectedAnswer.setBackgroundColor(getColor(R.color.incorrect_answer));
                vibrate();
                tvAnswerList.get(CORRECT_ANSWER_INDEX).setBackgroundColor(getColor(R.color.correct_answer));
                changedCorrectAnswerBackground = true;
            }

            itemScore.setTitle(numOfCorrectQuiz + "\\" + numOfQuiz);
        });

        viewModel.getOtherWords().observe(this, otherWordList ->
        {
            Collections.shuffle(tvAnswerList);

            for (int i = 0; i < otherWordList.size(); i++)
            {
                tvAnswerList.get(i).setText(otherWordList.get(i).getMeaning());
            }
        });

        viewModel.getWordToAsk().observe(this, wordToAsk ->
        {
            String question = "What is the meaning of \n" + wordToAsk.getWord() + " ?";
            tvQuestion.setText(question);

            tvAnswerList.get(3).setText(wordToAsk.getMeaning());
        });
    }

    private void setUpView()
    {
        tvQuestion = findViewById(R.id.tv_question);
        tvAnswer1 = findViewById(R.id.tv_answer1);
        tvAnswer2 = findViewById(R.id.tv_answer2);
        tvAnswer3 = findViewById(R.id.tv_answer3);
        tvAnswer4 = findViewById(R.id.tv_answer4);

        tvAnswer1.setOnClickListener(answerClickListener);
        tvAnswer2.setOnClickListener(answerClickListener);
        tvAnswer3.setOnClickListener(answerClickListener);
        tvAnswer4.setOnClickListener(answerClickListener);

        tvAnswerList = new ArrayList<>();
        tvAnswerList.add(tvAnswer1);
        tvAnswerList.add(tvAnswer2);
        tvAnswerList.add(tvAnswer3);
        tvAnswerList.add(tvAnswer4);

        defaultBackground = tvAnswer1.getBackground();
    }

    private void vibrate()
    {
        if (vibrator == null)
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        int interval = 50;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            vibrator.vibrate(VibrationEffect.createOneShot(interval, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else
        {
            //deprecated in API 26
            vibrator.vibrate(interval);
        }
    }

    private void nextQuestion()
    {
        selectedAnswer.setBackground(defaultBackground);
        if (changedCorrectAnswerBackground)
            tvAnswerList.get(CORRECT_ANSWER_INDEX).setBackground(defaultBackground);

        viewModel.createQuestion();
        numOfQuiz++;
        canChooseAnswer = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_word_test, menu);

        itemScore = menu.findItem(R.id.item_score);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_next_question:
                nextQuestion();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener answerClickListener = selectedView ->
    {
        if (selectedView instanceof TextView)
        {
            if (!canChooseAnswer)
                return;

            selectedAnswer = (TextView) selectedView;
            viewModel.submitResult(selectedAnswer.getText().toString());

            if (canChooseAnswer)
                canChooseAnswer = false;
        }
    };

}
