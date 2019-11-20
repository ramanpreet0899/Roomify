package com.example.findroommate.ui.send;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.findroommate.AboutUs;
import com.example.findroommate.ChangePassword;
import com.example.findroommate.ContactUs;
import com.example.findroommate.R;
import com.example.findroommate.SignInActivity;

public class SendFragment extends Fragment {
    private LinearLayout  linearLayoutChangePassword, linearLayoutPrivacy,layoutContactUs;

    SharedPreferences preferences;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_send, container, false);
        // final TextView textView = root.findViewById(R.id.text_send);

        root.findViewById(R.id.linearLayout_changePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ChangePassword.class);
                startActivity(i);
            }
        });
        root.findViewById(R.id.linearLayout_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AboutUs.class);
                startActivity(i);

            }
        });
        root.findViewById(R.id.linearLayout_signOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                final String username = preferences.getString("UserName", null);


                if (username != null) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(getContext(), SignInActivity.class);// New activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().finish();
                    startActivity(intent);

                }
            }
        });
        root.findViewById(R.id.linearLayout_contactUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ContactUs.class);
                startActivity(intent);
            }
        });

        return root;
    }
}