package com.brunogcpinheiro.igrejadaunidade.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.brunogcpinheiro.igrejadaunidade.fragment.AvisosFragment;
import com.brunogcpinheiro.igrejadaunidade.fragment.EventosFragment;
import com.brunogcpinheiro.igrejadaunidade.fragment.VideosFragment;

public class TabAdapter extends FragmentStatePagerAdapter{

    private String[] mTituloAbas = {"AVISOS", "EVENTOS", "VÍDEOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new AvisosFragment();
                break;
            case 1:
                fragment = new EventosFragment();
                break;
            case 2:
                fragment = new VideosFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mTituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTituloAbas[position];
    }
}