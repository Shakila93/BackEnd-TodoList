package com.shakila.backend_firebase;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ListManager {
    private static ListManager instance;
    public static ListManager getInstance(){
         if (instance == null){
             instance = new ListManager();
         }
         return instance;
     }
     private ListManager(){

     }

     public void getTodoList(User user, ITodoReceiver callBack){
         FirebaseFirestore db = FirebaseFirestore.getInstance();
         db.collection("list")
                 .whereEqualTo("UserID", user.getID())
                 .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
             @Override
             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 List<ListItem> TodoList = queryDocumentSnapshots.toObjects(ListItem.class);
                 if(callBack != null){
                     callBack.receive(TodoList);
                 }
             }
         });
     }
}
