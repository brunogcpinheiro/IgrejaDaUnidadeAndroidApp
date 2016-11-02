package com.brunogcpinheiro.igrejadaunidade.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.brunogcpinheiro.igrejadaunidade.Evento;
import com.brunogcpinheiro.igrejadaunidade.R;
import com.brunogcpinheiro.igrejadaunidade.Video;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import nz.co.delacour.exposurevideoplayer.ExposureVideoPlayer;

import static com.brunogcpinheiro.igrejadaunidade.R.id.evento_title;

public class VideosFragment extends Fragment {

    private RecyclerView mVideosList;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    public VideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getContext());
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_videos, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Video");

        mVideosList = (RecyclerView) root.findViewById(R.id.videos_list);
        mVideosList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mVideosList.setLayoutManager(mLayoutManager);
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
        mVideosList.smoothScrollToPosition(0);

        FirebaseRecyclerAdapter<Video, VideosFragment.VideoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Video, VideosFragment.VideoViewHolder>(
                Video.class,
                R.layout.video_row,
                VideosFragment.VideoViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(VideosFragment.VideoViewHolder viewHolder, Video model, int position) {
                viewHolder.setVideo(model.getVideo());
            }
        };

        mVideosList.setAdapter(firebaseRecyclerAdapter);
        if(isOnline()){
            firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    progressDialog.dismiss();
                    mVideosList.smoothScrollToPosition(positionStart);
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

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public VideoViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setVideo(String video){
            ExposureVideoPlayer evp = null;
            evp = (ExposureVideoPlayer) mView.findViewById(R.id.evp);
            evp.setVideoSource(video);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
