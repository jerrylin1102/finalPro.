package com.example.demo;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPerson#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPerson extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String NAMEFILE = "namefile" ;
    private static final String KEY_NAME = "keyname" ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView icon;
    private TextView personname;
    public FragmentPerson() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPerson.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPerson newInstance(String param1, String param2) {
        FragmentPerson fragment = new FragmentPerson();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    /*public static FragmentPerson newInstance(String username) {//fix
        FragmentPerson fragment = new FragmentPerson();
        Bundle args = new Bundle();
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }*/

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

        View view = inflater.inflate(R.layout.fragment_person, container, false);
        final Context context=getActivity();
        Intent material=new Intent(context,material.class);
        Intent setting=new Intent(context,setting.class);
        personname = view.findViewById(R.id.personname);
        icon = view.findViewById(R.id.icon);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    for (DataSnapshot userSnapshot : snapshot.getChildren())
                    {
                        userData userData = com.example.demo.userData.getInstance();
                        String account = userSnapshot.child("name").getValue(String.class);;
                        if(readUsername().equals(account)){
                            String Username = userSnapshot.child("username").getValue(String.class);
                            personname.setText(Username);
                            Log.e("name:",Username);
                        }
                    }
                }
                catch (NullPointerException n){
                    n.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //


        ImageView imgSitting=view.findViewById(R.id.setting);
        ImageView imgmaterial=view.findViewById(R.id.material);
        imgmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(material);
            }
        });
        imgSitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(setting);
                getActivity().finish();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    @Override//f
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         //TextView personname= (TextView) view.findViewById(R.id.personname);
    }
    private String readUsername() {
        // 從 SharedPreferences 中檢索資料
        SharedPreferences preferences = getActivity().getSharedPreferences(NAMEFILE, Context.MODE_PRIVATE);
        return preferences.getString(KEY_NAME, null);
    }

}
