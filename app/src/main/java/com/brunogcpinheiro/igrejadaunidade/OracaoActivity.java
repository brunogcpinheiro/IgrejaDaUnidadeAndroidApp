package com.brunogcpinheiro.igrejadaunidade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.brunogcpinheiro.igrejadaunidade.R.id.celulaLocal;
import static com.brunogcpinheiro.igrejadaunidade.R.id.nomeDaCelula;

public class OracaoActivity extends AppCompatActivity {

    private RecyclerView mOracoesList;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oracao);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Oracoes");
        progressDialog = new ProgressDialog(this);
        mOracoesList = (RecyclerView)findViewById(R.id.oracao_list);
        mOracoesList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mOracoesList.setLayoutManager(mLayoutManager);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);


        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OracaoActivity.this, OracaoPostActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mOracoesList.smoothScrollToPosition(0);

        FirebaseRecyclerAdapter<Oracao, OracaoActivity.OracaoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Oracao, OracaoViewHolder>(
                Oracao.class,
                R.layout.oracao_row,
                OracaoActivity.OracaoViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(OracaoActivity.OracaoViewHolder viewHolder, Oracao model, int position) {
                viewHolder.setAutor(model.getAutor());
                viewHolder.setOracao(model.getOracao());
            }


        };

        mOracoesList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        if(isOnline()){
            firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    progressDialog.dismiss();
                    mOracoesList.smoothScrollToPosition(positionStart);
                }
            });
        } else {
            progressDialog.dismiss();
            firebaseRecyclerAdapter.cleanup();
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.error, null);

            // fill in any details dynamically here
            TextView textView = (TextView) v.findViewById(R.id.error);
            textView.setText("Não foi possível cerregar. Sem conexão com a Internet. Conecte-se e entre novamente :)");

            // insert into main view
            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.insert_point);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

    }

    public static class OracaoViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public OracaoViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setAnfitrioes(String anfitrioes){

            TextView celulaAnfitrioes = (TextView) mView.findViewById(R.id.celulaAnfitrioes);
            celulaAnfitrioes.setText(anfitrioes);

        }

        public void setAutor(String autor){
            TextView autorOracao = (TextView) mView.findViewById(R.id.autorOracao);
            autorOracao.setText(autor);
        }

        public void setOracao(String oracao){
            TextView oracaoOracao = (TextView) mView.findViewById(R.id.textoOracao);
            oracaoOracao.setText(oracao);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
