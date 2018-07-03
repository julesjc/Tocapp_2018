package com.jules.tocapp;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ServerFacade {


    static String user;
    static String psw;

    public static void getFriends(final FriendsListActivity act)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public static void eventFriendExists(final String name, final NewEventActivity act)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> friends = new ArrayList();
                DataSnapshot friendsSnapshot = dataSnapshot.child("friends");
                Iterable<DataSnapshot> friendsChildren = friendsSnapshot.getChildren();
                for (DataSnapshot friend : friendsChildren) {
                    String s = friend.getValue(String.class);
                    if (s.equals(name))
                    {
                        act.friendExists(name);
                        return;
                    }
                }
                act.friendnotExists();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static void getEvent(final String name, final EventActivity act)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot eventsSnapshot = dataSnapshot.child("events");
                Iterable<DataSnapshot> eventsChildren = eventsSnapshot.getChildren();
                HashMap<String,Event> events = new HashMap<>();
                for (DataSnapshot event : eventsChildren) {
                    Event e = event.getValue(Event.class);
                    events.put(e.getName(),e);
                }
                act.fill(events.get(name));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static void getMessages(final String conversationWith, final WriteMessageActivity act)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> messages = new ArrayList();
                DataSnapshot messageSnapshot = dataSnapshot.child("Conversations").child(conversationWith).child("messages");
                Iterable<DataSnapshot> friendsChildren = messageSnapshot.getChildren();
                for (DataSnapshot message : friendsChildren) {
                    Message m = message.getValue(Message.class);
                    messages.add(m.from + " : " + m.text);
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

    public static void getConversationsUsers(final ConversationsListActivity act)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(user).child("Conversations");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String,Conversation> conversations = new HashMap<>();
                    Iterable<DataSnapshot> conversationsChildren = dataSnapshot.getChildren();
                for (DataSnapshot conversation : conversationsChildren) {
                    Conversation s = conversation.getValue(Conversation.class);
                    conversations.put(s.getWith(),s);
                }
                List<String> conversationsUsers = new ArrayList<>();
                for ( String key : conversations.keySet() ) {
                    conversationsUsers.add(key);
                }
                act.fillList(conversationsUsers);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static void getEventsName(final EventListActivity act)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> eventList = new ArrayList();
                DataSnapshot eventsSnapshot = dataSnapshot.child("events");
                Iterable<DataSnapshot> eventsChildren = eventsSnapshot.getChildren();
                for (DataSnapshot event : eventsChildren) {
                    Event e = event.getValue(Event.class);
                    eventList.add(e.getName());
                }
                act.fillList(eventList);
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

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public static void submitEvent(final Event event, final NewEventActivity act)
    {
        for (String userInvited:event.getInvited()) {

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference userfirebase = database.getReference(userInvited).child("events");
            userfirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List<Event> events = new ArrayList<>();
                    Iterable<DataSnapshot> eventsChildren = dataSnapshot.getChildren();
                    for (DataSnapshot conversation : eventsChildren) {
                        Event e = conversation.getValue(Event.class);
                        events.add(e);
                        if (e.getName().equals(event.getName()))
                        {
                            act.eventExists();
                            return;
                        }
                    }
                    events.add(event);
                    userfirebase.setValue(events);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }
        act.success();
    }

    public static void setLastKnownLocation(LatLng pos)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user).child("latitude");
        myRef.setValue(pos.latitude);
        myRef = database.getReference(user).child("longitude");
        myRef.setValue(pos.longitude);
    }

    /*public static void getFriendsMarkers(final MapsActivity act) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user).child("friends");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final HashMap<String,LatLng> fposList = new HashMap<>();
                Iterable<DataSnapshot> friendsChildren = dataSnapshot.getChildren();
                for (DataSnapshot friend : friendsChildren) {
                    final String f = friend.getValue(String.class);
                    DatabaseReference myRef = database.getReference(f);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            fposList.put(f,new LatLng(dataSnapshot.child("latitude").getValue(Double.class),dataSnapshot.child("longitude").getValue(Double.class)));
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                        });
                }
                act.placeFriendsMarkers(fposList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }*/

    public static void getEventsMarkers(final MapsActivity act)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Event> eventList = new ArrayList();
                DataSnapshot eventsSnapshot = dataSnapshot.child("events");
                Iterable<DataSnapshot> eventsChildren = eventsSnapshot.getChildren();
                for (DataSnapshot event : eventsChildren) {
                    Event e = event.getValue(Event.class);
                    eventList.add(e);
                }
                act.placeEventsMarkers(eventList);
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
    //todo friends requests
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
    //demo only
    public static void addFriend(final String name)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userInDB = database.getReference(user);
        final DatabaseReference friendInDB = database.getReference(name);
        friendInDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    List<String> friends = new ArrayList<>();
                    Iterable<DataSnapshot> friendsChildren = dataSnapshot.child("friends").getChildren();
                    for (DataSnapshot friend : friendsChildren) {
                        String f = friend.getValue(String.class);
                        friends.add(f);
                    }
                    friends.add(user);
                    friendInDB.child("friends").setValue(friends);
                    userInDB.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<String> friends = new ArrayList<>();
                            Iterable<DataSnapshot> friendsChildren = dataSnapshot.child("friends").getChildren();
                            for (DataSnapshot friend : friendsChildren) {
                                String f = friend.getValue(String.class);
                                friends.add(f);
                            }
                            friends.add(name);
                            userInDB.child("friends").setValue(friends);
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }



    public static void registerUser(final String name,final String mail,final String psw,final RegisterActivity act)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userInDB = database.getReference(name);
        userInDB.addListenerForSingleValueEvent(new ValueEventListener() {
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

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public static void sendMessage(final String to, String text)
    {
        final Message message = new Message(user,text);
        final Conversation convSender = new Conversation(to,message);
        final Conversation convReceiver = new Conversation(user,message);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference sender = database.getReference(user).child("Conversations");
        final DatabaseReference receiver = database.getReference(to);
        sender.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String,Conversation> conversations = new HashMap<>();
                Iterable<DataSnapshot> conversationsChildren = dataSnapshot.getChildren();
                for (DataSnapshot conversation : conversationsChildren) {
                    Conversation s = conversation.getValue(Conversation.class);
                    conversations.put(s.getWith(),s);
                }
                if (!conversations.containsKey(convSender.getWith()))
                    conversations.put(convSender.getWith(),convSender);
                else
                    conversations.get(convSender.getWith()).addMessageToList(message);
                sender.setValue(conversations);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        receiver.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    DataSnapshot conversationSnapshot = dataSnapshot.child("Conversations");
                    HashMap<String,Conversation> conversations = new HashMap<>();
                    Iterable<DataSnapshot> conversationsChildren = conversationSnapshot.getChildren();
                    for (DataSnapshot conversation : conversationsChildren) {
                        Conversation s = conversation.getValue(Conversation.class);
                        conversations.put(s.getWith(),s);
                    }
                    if (!conversations.containsKey(convReceiver.getWith()))
                        conversations.put(convReceiver.getWith(),convReceiver);
                    else
                        conversations.get(convReceiver.getWith()).addMessageToList(message);
                    receiver.child("Conversations").setValue(conversations);
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
