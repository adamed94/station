package com.station.bangoura.stationnew.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.tapadoo.alerter.Alerter;

public class JaugeFragment extends Fragment {
    CardView cardV ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_jauge, container, false);
        cardV = (CardView)view.findViewById(R.id.cardV);
        cardV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Button Clicked",Toast.LENGTH_LONG).show();

            }
        });
        return view ;
    }


}
