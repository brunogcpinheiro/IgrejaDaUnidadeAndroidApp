package com.brunogcpinheiro.igrejadaunidade;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OracaoPostActivity extends AppCompatActivity {

    private EditText edAutor, edTexto;
    private Button postButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oracao_post);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Oracoes");

        edAutor = (EditText)findViewById(R.id.edAutor);
        edTexto = (EditText)findViewById(R.id.edOracao);

        postButton = (Button)findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String autor = edAutor.getText().toString().trim();
                String oracao = edTexto.getText().toString().trim();
                if(TextUtils.isEmpty(autor) || TextUtils.isEmpty(oracao)){
                    Toast.makeText(OracaoPostActivity.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                } else {
                    startPosting();
                    startAnotherActv();
                    Toast.makeText(OracaoPostActivity.this, "Nova oração adicionada!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            startAnotherActv();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startAnotherActv(){
        Intent i = new Intent(OracaoPostActivity.this, OracaoActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void startPosting() {

        String autor = edAutor.getText().toString().trim();
        String oracao = edTexto.getText().toString().trim();

        if(!TextUtils.isEmpty(autor) && !TextUtils.isEmpty(oracao)){
            DatabaseReference newPost = mDatabase.push();
            newPost.child("autor").setValue(autor);
            newPost.child("oracao").setValue(oracao);
        }
    }
}
