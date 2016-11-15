package com.brunogcpinheiro.igrejadaunidade;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.brunogcpinheiro.igrejadaunidade.R.drawable.verse;
import static com.brunogcpinheiro.igrejadaunidade.R.id.versDate;
import static com.brunogcpinheiro.igrejadaunidade.R.id.versRef;

public class CelulaActivity extends AppCompatActivity {

    private RecyclerView mCelulaList;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celula);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Celula");
        progressDialog = new ProgressDialog(this);

        mCelulaList = (RecyclerView)findViewById(R.id.celulas_list);
        mCelulaList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mCelulaList.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mCelulaList.smoothScrollToPosition(0);

        FirebaseRecyclerAdapter<Celula, CelulaActivity.CelulaViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Celula, CelulaViewHolder>(
                Celula.class,
                R.layout.celula_row,
                CelulaActivity.CelulaViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(CelulaActivity.CelulaViewHolder viewHolder, Celula model, int position) {
                viewHolder.setAnfitrioes(model.getAnfitrioes());
                viewHolder.setNome(model.getNome());
                viewHolder.setLocal(model.getLocal());
                viewHolder.setDiaehora(model.getDiaehora());
                viewHolder.setTelefone(model.getTelefone());
            }


        };

        mCelulaList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        if(isOnline()){
            firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    progressDialog.dismiss();
                    mCelulaList.smoothScrollToPosition(positionStart);
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


    public static class CelulaViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public CelulaViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setAnfitrioes(String anfitrioes){

            TextView celulaAnfitrioes = (TextView) mView.findViewById(R.id.celulaAnfitrioes);
            celulaAnfitrioes.setText(anfitrioes);

        }

        public void setNome(String nome){
            TextView nomeDaCelula = (TextView) mView.findViewById(R.id.celulaNome);
            nomeDaCelula.setText(nome);
        }

        public void setLocal(String local){
            TextView celulaLocal = (TextView) mView.findViewById(R.id.localCelula);
            celulaLocal.setText(local);
        }

        public void setDiaehora(String diaehora){
            TextView diaehoraCelula = (TextView) mView.findViewById(R.id.diaEHoraCelula);
            diaehoraCelula.setText(diaehora);
        }

        public void setTelefone(String telefone){
            TextView celulaTelefone = (TextView) mView.findViewById(R.id.telefoneCelula);
            celulaTelefone.setText(telefone);
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}