package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
public class FragmentHome extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public FragmentHome() {

    }

    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        final Context context=getActivity();
        Intent home_Search=new Intent(context,home_Search.class);
        Intent home_Add=new Intent(context,home_Add.class);
        Intent home_Remove=new Intent(context,home_Remove.class);
        Intent home_help=new Intent(context, person_help.class);
        Intent home_inspection = new Intent(context, home_diet_inspection.class);

        ImageView imgAdd=view.findViewById(R.id.add);
        ImageView imgRemove=view.findViewById(R.id.remove);
        ImageView imgSearch=view.findViewById(R.id.search);
        ImageView imgInspection = view.findViewById(R.id.inspection);

        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(listener);
        imgInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home_inspection);
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home_Add);
            }
        });
        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home_Remove);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home_Search);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Uri uri  = Uri.parse("https://www.hpa.gov.tw/Pages/Detail.aspx?nodeid=543&pid=715");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    };

}