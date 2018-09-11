package com.khgkjg12.actionbutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.khgkjg12.component.actionbutton.ActionButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionButton actionButton = findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButton.setText(R.string.wait);
                actionButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        actionButton.setText(R.string.click);
                        actionButton.setProgress(false);
                    }
                },3000);
                actionButton.setProgress(true);
            }
        });
    }
}
