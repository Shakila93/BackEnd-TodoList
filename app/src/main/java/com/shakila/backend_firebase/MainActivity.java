package com.shakila.backend_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
private EditText newNote;
private LinearLayout Notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNote = findViewById(R.id.newNote);
        Notes = findViewById(R.id.Notes);

        UserManager.getInstance().addLoginEvent(()-> onLogin());
        UserManager.getInstance().addFailureEvent(()-> onFailure());
        UserManager.getInstance().logIn("shakilamaqsoodi@outlook.com", "1234");
    }

    //receive function
    public void onLogin(){
        System.out.println("login successful");
        ListManager.getInstance().getTodoList(UserManager.getInstance().getUser(), new ITodoReceiver() {
            @Override
            public void receive(List<ListItem> TodoList) {
                System.out.println("retreived list");
                Notes.removeAllViews();
                for(ListItem item: TodoList){
                    ListView view = ListView.newInstance(item);
                    view.setParent(Notes);
                    getSupportFragmentManager().beginTransaction().add(view, item.getName()).commit();
                }
            }
        });
    }
    public void onFailure(){
        System.out.println("login unsuccessful");
    }
}