package com.brunogcpinheiro.igrejadaunidade;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    // Timer da splash screen
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_intro);

            new Handler().postDelayed(new Runnable() {
                /*
                 * Exibindo splash com um timer.
                 */
                @Override
                public void run() {
                    // Esse método será executado sempre que o timer acabar
                    // E inicia a activity principal
                    Intent i = new Intent(IntroActivity.this, MenuActivity.class);
                    startActivity(i);

                    // Fecha esta activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
}
