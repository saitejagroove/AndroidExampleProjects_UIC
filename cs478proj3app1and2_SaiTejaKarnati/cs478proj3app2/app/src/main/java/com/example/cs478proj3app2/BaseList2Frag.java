package com.example.cs478proj3app2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseList2Frag extends ListFragment {

    private Namelistener2 namelistener2;
    public BaseList2Frag() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Restaurants)));
        setRetainInstance(true);

    }

    public  interface Namelistener2
    {
        public void onNameSelected(int index);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            namelistener2=(Namelistener2) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement interface NameListener");
        }
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //v.setSelected(true);
        namelistener2.onNameSelected(position);
    }
}
