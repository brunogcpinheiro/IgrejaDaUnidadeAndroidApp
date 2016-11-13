package com.brunogcpinheiro.igrejadaunidade.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brunogcpinheiro.igrejadaunidade.Evento;
import com.brunogcpinheiro.igrejadaunidade.R;
import com.brunogcpinheiro.igrejadaunidade.VersiculoActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.brunogcpinheiro.igrejadaunidade.R.id.evento_desc;

public class EventosFragment extends Fragment {

    private RecyclerView mEventosList;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;


    public EventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getContext());

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_eventos, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Evento");

        mEventosList = (RecyclerView) root.findViewById(R.id.eventos_list);
        mEventosList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mEventosList.setLayoutManager(mLayoutManager);
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
        mEventosList.smoothScrollToPosition(0);

        FirebaseRecyclerAdapter<Evento, EventoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Evento, EventoViewHolder>(
                Evento.class,
                R.layout.evento_row,
                EventoViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(EventoViewHolder viewHolder, Evento model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setDate(model.getDate());
                viewHolder.setImage(getContext(), model.getImage());
            }
        };

        mEventosList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        if(isOnline()){
            firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    progressDialog.dismiss();
                    mEventosList.smoothScrollToPosition(positionStart);
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

    public static class EventoViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public EventoViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title){

            TextView evento_title = (TextView) mView.findViewById(R.id.evento_title);
            evento_title.setText(title);

        }

        public void setDesc(String desc){
            TextView evento_desc = (TextView) mView.findViewById(R.id.evento_desc);
            evento_desc.setText(desc);
        }

        public void setDate(String date){
            TextView evento_date = (TextView) mView.findViewById(R.id.evento_date);
            evento_date.setText(date);
        }

        public void setImage(Context ctx, String image){
            ImageView imageView = (ImageView)mView.findViewById(R.id.evento_image);
            Picasso.with(ctx).load(image).into(imageView);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}