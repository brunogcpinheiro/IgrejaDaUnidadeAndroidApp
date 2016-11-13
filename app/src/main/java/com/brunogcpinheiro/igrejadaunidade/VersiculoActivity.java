package com.brunogcpinheiro.igrejadaunidade;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class VersiculoActivity extends AppCompatActivity {

    private RecyclerView mVersiculoList;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versiculo);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Versiculo");
        progressDialog = new ProgressDialog(this);

        mVersiculoList = (RecyclerView)findViewById(R.id.versiculo_list);
        mVersiculoList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mVersiculoList.setLayoutManager(mLayoutManager);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mVersiculoList.smoothScrollToPosition(0);

        FirebaseRecyclerAdapter<Versiculo, VersiculoActivity.VersiculoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Versiculo, VersiculoViewHolder>(
                Versiculo.class,
                R.layout.versiculo_row,
                VersiculoViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(VersiculoViewHolder viewHolder, Versiculo model, int position) {
                viewHolder.setDate(model.getDate());
                viewHolder.setRef(model.getRef());
                viewHolder.setVerse(model.getVerse());
            }


        };

        mVersiculoList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();

        if(isOnline()){
            firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(0, itemCount);
                    progressDialog.dismiss();
                    mVersiculoList.smoothScrollToPosition(positionStart);
                }
            });
        } else {
            progressDialog.dismiss();
            firebaseRecyclerAdapter.cleanup();
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.error, null);

            // fill in any details dynamically here
            TextView textView = (TextView) v.findViewById(R.id.error);
            textView.setText("Não foi possível cerregar. Sem conexão com a Internet :(");

            // insert into main view
            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.insert_point);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }


    public static class VersiculoViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public VersiculoViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDate(String date){

            TextView versDate = (TextView) mView.findViewById(R.id.versDate);
            versDate.setText(date);

        }

        public void setRef(String ref){
            TextView versRef = (TextView) mView.findViewById(R.id.versRef);
            versRef.setText(ref);
        }

        public void setVerse(String verse){
            TextView versVerse = (TextView) mView.findViewById(R.id.versTexto);
            versVerse.setText(verse);
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
