package com.example.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView textViewLast, textViewTry, textViewHint;
    private Button buttonConfirm;
    private EditText editTextGuess;

    boolean twoDigits, threeDigits, fourDigits;

    Random r = new Random();
    int random;
    int remainingTries = 10;

    ArrayList<Integer> guessesList = new ArrayList<>();
    int userAttempts = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewLast = findViewById(R.id.textViewLast);
        textViewTry = findViewById(R.id.textViewTry);
        textViewHint = findViewById(R.id.textViewHint);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        editTextGuess = findViewById(R.id.editTextGuess);

        twoDigits = getIntent().getBooleanExtra("two", false);
        threeDigits = getIntent().getBooleanExtra("three", false);
        fourDigits = getIntent().getBooleanExtra("four", false);

        if (twoDigits){
            random = r.nextInt(90)+10;
        }
        if (threeDigits){
            random = r.nextInt(900)+100;
        }
        if (fourDigits){
            random = r.nextInt(9000)+1000;
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = editTextGuess.getText().toString();
                if (guess.equals("")) {
                    Toast.makeText(GameActivity.this, "Please enter a guess.", Toast.LENGTH_LONG);
                } else {
                    textViewLast.setVisibility(View.VISIBLE);
                    textViewTry.setVisibility(View.VISIBLE);
                    textViewHint.setVisibility(View.VISIBLE);

                    userAttempts++;
                    remainingTries--;

                    int userGuess = Integer.parseInt(guess);
                    guessesList.add(userGuess);

                    textViewLast.setText("Your last guess is: " + guess);
                    textViewTry.setText("Your remainging tries: " + remainingTries);

                    if (random == userGuess) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guess Game");
                        builder.setCancelable(false);
                        builder.setMessage("Congrats! My number indeed was: " + userGuess +
                                "\n\nYou guessed my number in " + userAttempts + " attempts." +
                                "\n\nWould you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });

                        builder.create().show();

                    }
                    if (random > userGuess) {
                        textViewHint.setText("Increase your guess.");
                    }
                    if (random < userGuess) {
                        textViewHint.setText("Decrease your guess.");
                    }
                    if (remainingTries == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guess Game");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry,my number was: " + random +
                                "\n\nYou reached the maximum numer of attempts." +
                                "\n\nWould you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });

                        builder.create().show();

                    }

                    editTextGuess.setText("");
                }
            }
            });
    }
}