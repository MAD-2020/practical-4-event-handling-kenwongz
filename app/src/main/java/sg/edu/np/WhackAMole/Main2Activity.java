package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    private Button RightButton;
    private Button RightButton1;
    private Button RightButton2;
    private Button MiddleButton;
    private Button MiddleButton1;
    private Button MiddleButton2;
    private Button LeftButton;
    private Button LeftButton1;
    private Button LeftButton2;
    private TextView scoretext;
    private int Score = 0;
    CountDownTimer myCountDown;
    private static final String TAG = "Whack a Mole 2.0";
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

        //Toast will always update and deleting the previous so that the countdown timer and the toast will be synced
        final Toast[] mtoast = {null};
        myCountDown = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished / 1000);
                if (mtoast[0] != null) mtoast[0].cancel();
                mtoast[0] = Toast.makeText(getApplicationContext(), "Get Ready in " + millisUntilFinished / 1000 + " seconds", Toast.LENGTH_SHORT);
                mtoast[0].show();
            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete");
                if (mtoast[0] != null) mtoast[0].cancel();
                mtoast[0] = Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT);
                mtoast[0].show();
                placeMoleTimer();
            }
        };
        myCountDown.start();
    }

    private void placeMoleTimer() {
        myCountDown = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "Ready CountDown! " + l / 1000);
            }

            @Override
            public void onFinish() {
                setNewMole();
                myCountDown.start();

            }
        };
        myCountDown.start();
    }

    private static final int[] BUTTON_IDS = {
            R.id.RightButton, R.id.RightButton1, R.id.RightButton2, R.id.LeftButton, R.id.LeftButton1, R.id.LeftButton2, R.id.MiddleButton, R.id.MiddleButton1, R.id.MiddleButton2

        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        scoretext = (TextView) findViewById(R.id.scoretext);

        Intent receivingend = getIntent();
        String message = receivingend.getStringExtra("Key");
        Log.v(TAG, String.valueOf(message));
        scoretext.setText(String.valueOf(message));

        Log.v(TAG, "Finished Pre-Initialisation!");

        for(final int id : BUTTON_IDS){
            readyTimer();
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
        }

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    public void whenClicked(View v)
    {
        /* Checks for hit or miss and if user qualify for advanced page.
        Triggers nextLevelQuery().
         */
        switch(v.getId())
        {
            case R.id.RightButton:
                Log.v(TAG, "Right button clicked");
                break;

            case R.id.LeftButton:
                Log.v(TAG, "Left button clicked");
                break;

            case R.id.MiddleButton:
                Log.v(TAG, "Middle button clicked");

        }

        //downcast v to button
        Button button = (Button) v;
        if (checkmole((String) button.getText().toString()))
        {
            Score++;
            Log.v(TAG, "Hit, score added!");
        }
        else
        {
            Score--;
            Log.v(TAG, "Missed, score deducted!");
        }
        Updatescore();
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        int mole = BUTTON_IDS[randomLocation];
        for (final int id: BUTTON_IDS)
        {
            if (id == mole)
            {
                Button b = (Button) findViewById(id);
                b.setText("*");
            }
            else
            {
                Button b = (Button) findViewById(id);
                b.setText("O");
            }
        }
    }

    private boolean checkmole(String s)
    {
        if (s == "*")
        {
            return true;
        }
        return false;
    }

    private void Updatescore()
    {
        String count = String.valueOf(Score);
        scoretext.setText(count);
    }
}
