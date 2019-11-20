package com.example.findroommate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Objects;

public class CreatePassword extends AppCompatActivity {
    TextInputEditText textInputEditTextPassword, textInputEditTextConfirmPassword;
    DatabaseReference databaseReference;
    User user;
    private FirebaseAuth mAuth;
    String pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        mAuth = FirebaseAuth.getInstance();
        textInputEditTextPassword = findViewById(R.id.editText_enterNewPassword);
        textInputEditTextConfirmPassword = findViewById(R.id.edittext_reEnterPassword);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        user = new User();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void signUpFinal(View view) {
        /*final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("processing...");
        progressDialog.show();*/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pass1 = textInputEditTextPassword.getText().toString().trim();
        pass2 = textInputEditTextConfirmPassword.getText().toString().trim();
        if (pass1.length() < 8) {
            textInputEditTextPassword.setError("password should consists atleast 8 chars");

        } else if (!pass1.equals(pass2)) {
            textInputEditTextConfirmPassword.setError("Password Mismatch");

        } else {
            //progressDialog.dismiss();
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("UserName",getIntent().getStringExtra("UserName"));
            editor.apply();
            user.setFirstName(getIntent().getStringExtra("FirstName"));
            user.setLastName(getIntent().getStringExtra("LastName"));
            user.setUserName(getIntent().getStringExtra("UserName"));
            user.setPhoneNumber(Long.parseLong(Objects.requireNonNull(getIntent().getStringExtra("Phone"))));
            user.setEmailId(getIntent().getStringExtra("Email"));
            user.setPassword(pass2);
            user.setDob("dd/mm/yyyy");
            user.setGender("Male");

            databaseReference.child(user.getUserName()).setValue(user);
            //progressDialog.dismiss();

           // Toast.makeText(CreatePassword.this, "User has been successfully registered", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
