package com.brunogcpinheiro.igrejadaunidade.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brunogcpinheiro.igrejadaunidade.Jejum;
import com.brunogcpinheiro.igrejadaunidade.R;
import com.brunogcpinheiro.igrejadaunidade.Video;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JejunsFragment extends Fragment {


    private RecyclerView mJejunsList;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;


    public JejunsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getContext());
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_jejuns, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Jejum");

        mJejunsList = (RecyclerView) root.findViewById(R.id.jejuns_list);
        mJejunsList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mJejunsList.setLayoutManager(mLayoutManager);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        mJejunsList.smoothScrollToPosition(0);

        FirebaseRecyclerAdapter<Jejum, JejunsFragment.JejumViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Jejum, JejunsFragment.JejumViewHolder>(
                Jejum.class,
                R.layout.jejum_row,
                JejunsFragment.JejumViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(JejunsFragment.JejumViewHolder viewHolder, Jejum model, int position) {
                viewHolder.setInicio(model.getInicio());
                viewHolder.setTermino(model.getTermino());
                viewHolder.setMotivos(model.getMotivos());
            }
        };

        mJejunsList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        if(isOnline()){
            firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    progressDialog.dismiss();
                    mJejunsList.smoothScrollToPosition(positionStart);
                }
            });
        } else {
            progressDialog.dismiss();
            firebaseRecyclerAdapter.cleanup();
            LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.error, null);

            // fill in any details dynamically here
            TextView textView = (TextView) v.findViewById(R.id.error);
            textView.setText("Não foi possível cerregar. Sem conexão com a Internet :(");

            // insert into main view
            ViewGroup insertPoint = (ViewGroup) getView().findViewById(R.id.insert_point);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public static class JejumViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public JejumViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setMotivos(String motivos){
            TextView motivos_text = (TextView) mView.findViewById(R.id.motivos);
            motivos_text.setText(motivos);
        }

        public void setInicio(String inicio){
            TextView inicio_text = (TextView) mView.findViewById(R.id.inicio);
            inicio_text.setText(inicio);
        }

        public void setTermino(String termino){
            TextView termino_text = (TextView) mView.findViewById(R.id.termino);
            termino_text.setText(termino);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
