package com.shakila.backend_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
private EditText newNote;
private LinearLayout Notes;
private boolean debouncedNewTask = false;
private FloatingActionButton goToMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNote = findViewById(R.id.newNote);
        Notes = findViewById(R.id.Notes);
        goToMap = findViewById(R.id.goToMap);

        UserManager.getInstance().addLoginEvent(()-> onLogin());
        UserManager.getInstance().addFailureEvent(()-> onFailure());
        UserManager.getInstance().logIn("shakilamaqsoodi@outlook.com", "1234");

        newNote.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    newTaskCreate();
                }
                return true;
            }
        });
        goToMap.setOnClickListener(view -> {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        });
    }

    private void newTaskCreate(){
        if(debouncedNewTask){
            return;
        }
        debouncedNewTask = true;
        String value = String.valueOf(newNote.getText());
        ListManager.getInstance().createTodo(UserManager.getInstance().getUser(), value, (list)->{
            onLogin();
            newNote.setText("");
        });
    }

    //receive function
    public void onLogin(){
        System.out.println("login successful");
        MainActivity that = this;
        ListManager.getInstance().getTodoList(UserManager.getInstance().getUser(), new ITodoReceiver() {
            @Override
            public void receive(List<ListItem> TodoList) {
                System.out.println("retreived list");
                Notes.removeAllViews();
                for(ListItem item: TodoList){
                    ListView view = ListView.newInstance(item, that);
                    view.setParent(Notes);
                    getSupportFragmentManager().beginTransaction().add(view, item.getName()).commit();
                }
                debouncedNewTask = false;
            }
        });
    }
    public void onFailure(){
        System.out.println("login unsuccessful");
    }
}