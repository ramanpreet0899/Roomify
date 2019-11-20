package com.example.findroommate.ui.tools;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findroommate.ChatHistory;
import com.example.findroommate.ChatHistoryAdapter;
import com.example.findroommate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToolsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReferenceChatHistory;
    private String username, chatNode;
    private ArrayList<ChatHistory> chatHistories = new ArrayList<>();
    private ChatHistoryAdapter chatHistoryAdapter;
    private long totalChats;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        username = preferences.getString("UserName", null);
        //chatHistories = new ArrayList<>();
        chatHistories.clear();
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        recyclerView = root.findViewById(R.id.recycler_View_chat_list);
        new ItemTouchHelper(iteSimpleCallback).attachToRecyclerView(recyclerView);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }


    ItemTouchHelper.SimpleCallback iteSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // Toast.makeText(getContext(),"Coming Soon",Toast.LENGTH_LONG).show();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("Messages").child(chatNode);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        snapshot.getRef().child("deleted").setValue(username);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    };


    private void getData() {
        databaseReferenceChatHistory = FirebaseDatabase.getInstance().getReference()
                .child("ChatHistory");
        chatHistories.clear();

        databaseReferenceChatHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey() != null) {
                        chatNode = snapshot.getKey();
                        if (chatNode.contains(username)) {
                            ChatHistory chatHistory = snapshot.getValue(ChatHistory.class);
                            chatHistories.add(chatHistory);

                        }
                    }

                }
                chatHistoryAdapter = new ChatHistoryAdapter(getContext(), chatHistories);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                chatHistoryAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(chatHistoryAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}