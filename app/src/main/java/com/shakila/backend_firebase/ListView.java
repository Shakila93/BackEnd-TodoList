package com.shakila.backend_firebase;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListView extends Fragment {
    private TextView Name;
    private TextView Date;
    private  ListItem Item;
    private LinearLayout Parent;

    public ListView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ListView.
     */
    // TODO: Rename and change types and number of parameters
    public static ListView newInstance(ListItem item) {
        ListView fragment = new ListView();
        Bundle args = new Bundle();
        args.putSerializable("ListItem", item);
        fragment.setArguments(args);
        return fragment;
    }

    public void setParent(LinearLayout layout){
        Parent = layout;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
          Item =(ListItem) getArguments().getSerializable("ListItem");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_view, container, false);
        Name = view.findViewById(R.id.Name);
        Date = view.findViewById(R.id.Date);
        Name.setText(Item.getName());

        int year = Item.getCreatedAt().toDate().getYear();
        int month = Item.getCreatedAt().toDate().getMonth();
        int date = Item.getCreatedAt().toDate().getDate();

        Date.setText(String.format("%4d-%02d-%02d", year, month, date));
        if(Item.getCompletedAt()!= null){
            Name.setPaintFlags(Name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if(Parent != null){
            Parent.addView(view);
        }
        return view;
    }
}