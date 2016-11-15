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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brunogcpinheiro.igrejadaunidade.Aviso;
import com.brunogcpinheiro.igrejadaunidade.Evento;
import com.brunogcpinheiro.igrejadaunidade.R;
import com.brunogcpinheiro.igrejadaunidade.VersiculoActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.brunogcpinheiro.igrejadaunidade.R.id.aviso_date;
import static com.brunogcpinheiro.igrejadaunidade.R.id.aviso_texto;
import static com.brunogcpinheiro.igrejadaunidade.R.id.evento_desc;
import static com.brunogcpinheiro.igrejadaunidade.R.id.evento_title;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvisosFragment extends Fragment {

    private RecyclerView mAvisosList;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    public AvisosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getContext());

        View root = inflater.inflate(R.layout.fragment_avisos, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Aviso");

        mAvisosList = (RecyclerView) root.findViewById(R.id.avisos_list);
        mAvisosList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mAvisosList.setLayoutManager(mLayoutManager);
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
        mAvisosList.smoothScrollToPosition(0);

        FirebaseRecyclerAdapter<Aviso, AvisosFragment.AvisoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Aviso, AvisosFragment.AvisoViewHolder>(
                Aviso.class,
                R.layout.aviso_row,
                AvisosFragment.AvisoViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(AvisosFragment.AvisoViewHolder viewHolder, Aviso model, int position) {
                viewHolder.setDate(model.getDate());
                viewHolder.setAviso(model.getAviso());
            }
        };

        mAvisosList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        if(isOnline()){
            firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    progressDialog.dismiss();
                    mAvisosList.smoothScrollToPosition(positionStart);
                }
            });
        } else {
            progressDialog.dismiss();
            firebaseRecyclerAdapter.cleanup();
            LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.error, null);

            // fill in any details dynamically here
            TextView textView = (TextView) v.findViewById(R.id.error);
            textView.setText("Não foi possível cerregar. Sem conexão com a Internet. Conecte-se e entre novamente :)");

            // insert into main view
            ViewGroup insertPoint = (ViewGroup) getView().findViewById(R.id.insert_point);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

    }

    public static class AvisoViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public AvisoViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDate(String date){

            TextView aviso_date = (TextView) mView.findViewById(R.id.aviso_date);
            aviso_date.setText(date);

        }

        public void setAviso(String aviso){
            TextView aviso_texto = (TextView) mView.findViewById(R.id.aviso_texto);
            aviso_texto.setText(aviso);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
