package com.shakila.backend_firebase;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListView extends Fragment implements View.OnLongClickListener, View.OnClickListener {
    private TextView Name;
    private TextView Date;
    private  ListItem Item;
    private LinearLayout Parent;
    private MainActivity Activity;

    public ListView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ListView.
     */
    // TODO: Rename and change types and number of parameters
    public static ListView newInstance(ListItem item, MainActivity Activity) {
        ListView fragment = new ListView();
        Bundle args = new Bundle();
        //args.putSerializable("ListItem", item);
        fragment.setArguments(args);
        fragment.Activity = Activity;
        fragment.Item = item;
        return fragment;
    }

    public void setParent(LinearLayout layout){
        Parent = layout;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//          Item =(ListItem) getArguments().getSerializable("ListItem");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_view, container, false);
        Name = view.findViewById(R.id.Name);
        Date = view.findViewById(R.id.Date);
        Name.setText(Item.getName());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date itemDate = Item.getCreatedAt().toDate();
        Date.setText(stf.format(itemDate));
        if(Item.getCompletedAt()!= null){
            Name.setPaintFlags(Name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if(Parent != null){
            Parent.addView(view);
        }

        view.setOnLongClickListener(this);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean onLongClick(View view) {
        new AlertDialog.Builder(Activity).setTitle("Delete Todo List Item")
                .setMessage("Are you sure you want to delete this item?")
                .setIcon(R.drawable.ic_launcher_foreground).setPositiveButton("Yes", (dialog, button)->{
            ListManager.getInstance().DeleteTodo(Item, (list)->Activity.onLogin());
            Toast.makeText(Activity, "Todo Item Deleted", Toast.LENGTH_SHORT).show();
        }).setNegativeButton("NO", null).show();


        return true;
    }

    @Override
    public void onClick(View view) {
        ListManager.getInstance().CompleteTodo(Item, (list)->Name.setPaintFlags(Name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG));
    }
}