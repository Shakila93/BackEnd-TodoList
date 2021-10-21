package com.shakila.backend_firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserManager{
    private User user;
    private static UserManager instance;
    private List<Runnable> onLoginEvents;
    private List<Runnable> onLogoutEvents;
    private List<Runnable> onFailureEvents;

    public static UserManager getInstance(){
        if(instance == null){
            instance = new UserManager();
        }
        return instance;
    }

    private  UserManager(){
        onLoginEvents = new ArrayList<>();
        onLogoutEvents = new ArrayList<>();
        onFailureEvents = new ArrayList<>();
    }
    public void logIn(String Email, String Password){
        FirebaseFirestore db = FirebaseFirestore.getInstance(); //gets the instance to the firestore object

        //a syncrnise action
        db.collection("users")
                .whereEqualTo("Email", Email)
                //.whereEqualTo("Password", Password)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.size() != 1){
                    //do sth,  informed the user of an error
                    user = null;
                    runFailure();
                    return;
                }
                user = queryDocumentSnapshots.toObjects(User.class).get(0);
                user.setID(queryDocumentSnapshots.getDocuments().get(0).getId());
                if(!user.getPassword().equals(Password)){
                    user = null;
                    runFailure();
                    return;
                }
                for(Runnable event: onLoginEvents){
                    event.run();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                user = null;
                runFailure();
            }
        });
    }

    private void runFailure(){
        for(Runnable event: onFailureEvents){
            event.run();
        }
    }
    public void logOut(){
        user = null;
        for(Runnable event: onLogoutEvents){
            event.run();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void addLoginEvent(Runnable event){
        onLoginEvents.add(event);
    }
    public void addLogoutEvent(Runnable event){
        onLogoutEvents.add(event);
    }
    public void addFailureEvent(Runnable event){
        onFailureEvents.add(event);
    }
}
