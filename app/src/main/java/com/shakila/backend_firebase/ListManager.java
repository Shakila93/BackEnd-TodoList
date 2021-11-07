package com.shakila.backend_firebase;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
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

     public void createTodo(User user, String value, ITodoReceiver callBack){
        ListItem item = new ListItem();
        item.setUserID(user.getID());
        item.setName(value);
        item.setCreatedAt(Timestamp.now());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("list").add(item).addOnSuccessListener((qds)->{

            if(callBack != null){
                callBack.receive(null);
            }
        });
     }

     public void getTodoList(User user, ITodoReceiver callBack){
         FirebaseFirestore db = FirebaseFirestore.getInstance();
         db.collection("list")
                 .whereEqualTo("userID", user.getID())
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

    public void DeleteTodo(ListItem item,  ITodoReceiver callBack) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("list")
                .whereEqualTo("userID", item.getUserID())
                .whereEqualTo("name", item.getName())
                .whereEqualTo("createdAt", item.getCreatedAt())
        .get().addOnSuccessListener((qds)->{
            for(int i = 0; i < qds.size(); i++){
                qds.getDocuments().get(i).getReference().delete();
            }
            if(callBack != null){
                callBack.receive(null);
            }
        });
    }

    public void CompleteTodo(ListItem item, ITodoReceiver callBack) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("list")
                .whereEqualTo("userID", item.getUserID())
                .whereEqualTo("name", item.getName())
                .whereEqualTo("createdAt", item.getCreatedAt())
                .get().addOnSuccessListener((qds)->{
            for(int i = 0; i < qds.size(); i++){
                qds.getDocuments().get(i).getReference().update("completedAt", Timestamp.now());
            }
            if(callBack != null){
                callBack.receive(null);
            }
        });
    }
}
