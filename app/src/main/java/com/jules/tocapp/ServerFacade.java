package com.jules.tocapp;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ServerFacade {


    static String name;
    static String psw;

    public static void setPsw(String psw) {
        ServerFacade.psw = psw;
    }

    public static void setName(String name) {
        ServerFacade.name = name;
    }

    public static void getFriends(final FriendsActivity act)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(name);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> friends = new ArrayList();
                DataSnapshot friendsSnapshot = dataSnapshot.child("friends");
                Iterable<DataSnapshot> friendsChildren = friendsSnapshot.getChildren();
                for (DataSnapshot friend : friendsChildren) {
                    String s = friend.getValue(String.class);
                    friends.add(s);
                }
                act.fillList(friends);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static void getMessages(final MessagesActivity act)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(name);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> messages = new ArrayList();
                DataSnapshot messageSnapshot = dataSnapshot.child("messages");
                Iterable<DataSnapshot> friendsChildren = messageSnapshot.getChildren();
                for (DataSnapshot message : friendsChildren) {
                    String s = message.getValue(String.class);
                    messages.add(s);
                }
                act.fillList(messages);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static void registerUser(String name, String mail, String psw)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userInDB = database.getReference(name);

        DatabaseReference mailRef = userInDB.child("mail");

        mailRef.setValue(mail);

        DatabaseReference nameRef = userInDB.child("name");

        nameRef.setValue(name);

        DatabaseReference pswRef = userInDB.child("password");

        pswRef.setValue(psw);
    }

    public static void connectUser(final String typedName, final String typedPsw, final MainActivity act)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(typedName);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    String realpsw = dataSnapshot.child("password").getValue(String.class);
                    if (typedPsw.equals(realpsw))
                    {
                        ServerFacade.name = typedName;
                        ServerFacade.psw = typedPsw;
                        act.loginSuccessful();
                    }

                }
                else
                {
                    act.loginFail();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
