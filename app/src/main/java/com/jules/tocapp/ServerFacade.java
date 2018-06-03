package com.jules.tocapp;

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


    static String user;
    static String psw;

    public static void getFriends(final FriendsActivity act)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user);

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

    public static void getMessages(final String conversationWith, final ConversationsActivity act)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> messages = new ArrayList();
                DataSnapshot messageSnapshot = dataSnapshot.child("Conversations").child(conversationWith);
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

    public static void getConversations(final ConversationsActivity act)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> messages = new ArrayList();
                DataSnapshot messageSnapshot = dataSnapshot.child("Conversations");
                Iterable<DataSnapshot> friendsChildren = messageSnapshot.getChildren();
                for (DataSnapshot conversation : friendsChildren) {
                    String s = conversation.getValue(String.class);
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

    public static void getDescription(final ProfileActivity act)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user).child("description");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String desc = dataSnapshot.getValue(String.class);
                act.fillDescription(desc);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static void setDescription(String newDescription )
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user).child("description");
        myRef.setValue(newDescription);
    }

    public static void sendFriendRequest(String user)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Reqs = database.getReference(user).child("friendRequests");
        Reqs.setValue(user);
    }

    public static void requestToFriend(String user)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Reqs = database.getReference(ServerFacade.user).child("friendRequests");
        Reqs.child(user).removeValue();
        Reqs = database.getReference(ServerFacade.user).child("Friends");
        Reqs.setValue(user);
        Reqs = database.getReference(user).child("Friends");
        Reqs.setValue(user);
    }



    public static void registerUser(final String name,final String mail,final String psw,final RegisterActivity act)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userInDB = database.getReference(name);
        userInDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    DatabaseReference mailRef = userInDB.child("mail");
                    mailRef.setValue(mail);
                    DatabaseReference nameRef = userInDB.child("user");
                    nameRef.setValue(name);
                    DatabaseReference pswRef = userInDB.child("password");
                    pswRef.setValue(psw);
                    act.showModal(true);
                }
                else
                {
                    act.showModal(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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
                        ServerFacade.user = typedName;
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

    public void sendMessage(String to, String text)
    {
        Message messageReceiver = new Message(to,text);
        Message messageSender = new Message(user,text);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference sender = database.getReference(to).child("Conversations").child(user);
        DatabaseReference receiver = database.getReference(user).child("Conversations").child(to);
        sender.setValue(messageSender);
        receiver.setValue(messageReceiver);
    }
}
