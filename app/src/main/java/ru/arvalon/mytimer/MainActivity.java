package ru.arvalon.mytimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final long TIMEDELAY = 1000;

    private static final long PERIODDELAY = 2000;

    private CheckBox mSingleShotCheckBox;

    private Button mStartButton, mCancelButton;

    private TextView mCounterTextView;

    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        setValues();

        mStartButton.setOnClickListener(arg0 -> {

            if (mTimer != null) {
                mTimer.cancel();
            }

            // re-schedule timer here otherwise, IllegalStateException of "TimerTask is scheduled
            // already" will be thrown
            mTimer = new Timer();

            mMyTimerTask = new MyTimerTask();

            if (mSingleShotCheckBox.isChecked()) {
                // singleshot delay 1000 ms
                mTimer.schedule(mMyTimerTask, TIMEDELAY);
            } else {
                // delay 1000ms, repeat in 5000ms
                mTimer.schedule(mMyTimerTask, TIMEDELAY, PERIODDELAY);
            }
        });

        mCancelButton.setOnClickListener(v -> {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
        });
    }

    private void initViews() {

        mSingleShotCheckBox = findViewById(R.id.checkBoxSingleShot);
        mStartButton = findViewById(R.id.buttonStart);
        mCancelButton = findViewById(R.id.buttonCancel);
        mCounterTextView = findViewById(R.id.textViewCounter);
    }

    private void setValues() {

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                final float value = Utils.randInt(-10, 35);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Logs.info(this, "Value: " + value);
                    }
                });
            }
        }, 0, 3500);
    }

    class MyTimerTask extends TimerTask {

        private static final String DATETIMEFORMAT = "dd:MMMM:yyyy HH:mm:ss";

        @Override
        public void run() {

            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat(DATETIMEFORMAT, Locale.getDefault());

            final String strDate = simpleDateFormat.format(calendar.getTime());

            runOnUiThread(() -> mCounterTextView.setText(strDate));

            Logs.info(this,strDate);
        }
    }
}
