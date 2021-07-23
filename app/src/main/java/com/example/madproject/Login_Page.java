package com.example.madproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login_Page extends AppCompatActivity {
    private TextView btn,btnBack;
    private EditText email,password;
    private ProgressDialog loading;
    private Button btn_Login;

    private FirebaseAuth myAuth;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login__page);

        //email and password input
        email = (EditText)findViewById(R.id.lemailId);
        password = (EditText)findViewById(R.id.lPassword);

        //forgot password
        btn = (TextView)findViewById(R.id.forgotPassword);
        //this will lead to selection page
        btnBack = (TextView)findViewById(R.id.lDoNotHaveAccount);
        //button - login
        btn_Login = (Button)findViewById(R.id.lButton);

        //entry point of the Firebase Authentication SDK
        //instance of the class
        myAuth = FirebaseAuth.getInstance();
        //loading icon
        loading = new ProgressDialog(Login_Page.this);

        //forgot password-on click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Page.this,ForgotPassword.class);
                startActivity(intent);
            }
        });

        //selection page - on click
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_Page.this,SelectionPage.class);
                startActivity(i);
            }
        });

        //on clicking login it will check the details whether it is valid or not
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });
    }


    private void checkDetails() {
        //extracting email and password from editText
        String lEmail = email.getText().toString();
        String pwd =password.getText().toString();
        //set error = false intially
        Boolean err = false;

        //if password or email field is empty in during login show an error
        if(lEmail.isEmpty()){
            showError(email,"Enter the Email Id ");
            err = true;
        }
        if(pwd.isEmpty()){
            showError(password,"Enter the password");
            err = true;
        }
        // if no errors proceed with login
        if(err == false){
            loginUser(lEmail,pwd);
        }
    }

    private void loginUser(String emailid, String pwd) {
        loading.setTitle("Loading");
        loading.setMessage("Please wait");
        // avoiding dismissing of the progress bar when the user touches the screen
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        //to check whether the given login data is of a caterer
        //getting an instance of caterer database

        dbRef = FirebaseDatabase.getInstance().getReference("caterer");
        // checking whether the entered email id  is equal to the email id present in database
        Query checkUser = dbRef.orderByChild("Email").equalTo(emailid);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //if the data exists
                if (snapshot.exists()) {
                    String key;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        // get the key password, caterer name and service from the database
                        key = ds.getKey();
                        String pwdFromDb = snapshot.child(key).child("Password").getValue(String.class);
                        String name = snapshot.child(key).child("Username").getValue(String.class);
                        String service = snapshot.child(key).child("ServiceName").getValue(String.class);

                        if (pwdFromDb != null && pwdFromDb.equals(pwd)) {
                            Intent intent = new Intent(Login_Page.this, caterer_occasion.class);
                            intent.putExtra("Key", key);
                            intent.putExtra("Name",name);
                            intent.putExtra("Service",service);
                            startActivity(intent);
                            finish();
                            loading.dismiss();
                        } else {
                            showError(password, "Wrong Password");
                            loading.dismiss();
                        }
                    }
                } else {
                    dbRef = FirebaseDatabase.getInstance().getReference("users");

                    Query checkUser = dbRef.orderByChild("Email").equalTo(emailid);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String key;
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    key = ds.getKey();
                                    String pwdFromDb = snapshot.child(key).child("Password").getValue(String.class);

                                    if (pwdFromDb != null && pwdFromDb.equals(pwd)) {
                                        Intent intent = new Intent(Login_Page.this, occasion.class);
                                        intent.putExtra("Uniqid","From_Login");
                                        intent.putExtra("Key", key);
                                        intent.putExtra("Email",emailid);
                                        startActivity(intent);
                                        finish();
                                        loading.dismiss();
                                    } else {
                                        showError(password, "Wrong Password");
                                        loading.dismiss();
                                    }
                                }
                            }else{
                                loading.dismiss();
                                Toast.makeText(Login_Page.this,"Email Id is not registered",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // error
    private void showError(EditText input, String s) {
        Drawable error = getResources().getDrawable(R.drawable.ic_baseline_error_24);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        // setting the error
        input.setError(s,error);
        //focus on edittext
        input.requestFocus();
    }
}
