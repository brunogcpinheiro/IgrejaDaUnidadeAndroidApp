package com.brunogcpinheiro.igrejadaunidade;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private TextView header;
    private LinearLayout firstItem, secondItem, thirdItem, forthItem, fifthItem, sixthItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firstItem = (LinearLayout) findViewById(R.id.firstItem);
        secondItem = (LinearLayout) findViewById(R.id.secondItem);
        thirdItem = (LinearLayout) findViewById(R.id.thirdItem);
        forthItem = (LinearLayout) findViewById(R.id.forthItem);
        fifthItem = (LinearLayout) findViewById(R.id.fifthItem);
        sixthItem = (LinearLayout) findViewById(R.id.sixthItem);

        firstItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        secondItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, VersiculoActivity.class);
                startActivity(i);
            }
        });

        thirdItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, CelulaActivity.class);
                startActivity(i);
            }
        });

        forthItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, OracaoActivity.class);
                startActivity(i);
            }
        });

        fifthItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, VideoActivity.class);
                startActivity(i);
            }
        });

        sixthItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Serviço indisponível. Aguarde!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
