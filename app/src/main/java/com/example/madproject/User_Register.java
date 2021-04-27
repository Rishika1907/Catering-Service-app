package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;

public class User_Register extends AppCompatActivity {
      private TextView btn;
      private EditText username,email,password,confirm_password,phone,address;
      private Button btn_register;

     private FirebaseAuth myAuth;
     private ProgressDialog loading;
      private FirebaseDatabase rootNode;
      private DatabaseReference ref,dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register);

        btn = (TextView)findViewById(R.id.alreadyhaveaccount);

        username = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.emailId);
        password = (EditText)findViewById(R.id.Password);
        confirm_password = (EditText)findViewById(R.id.confirmPassword);
        phone = (EditText)findViewById(R.id.Phoneno);
        address = (EditText)findViewById(R.id.Address);

        myAuth = FirebaseAuth.getInstance();
        loading = new ProgressDialog(User_Register.this);

        //ref = rootNode.getInstance().getReference();

        btn_register = (Button)findViewById(R.id.register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCredentials();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Register.this,Login_Page.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(myAuth.getCurrentUser() != null){
            openMain();
        }
    }

    private void openMain() {
        startActivity( new Intent(this,Login_Page.class));
        finish();
    }

    private void verifyCredentials() {
        String name = username.getText().toString();
        String emailId = email.getText().toString();
        String phoneNo = phone.getText().toString();
        String Address = address.getText().toString();
        String conPwd = confirm_password.getText().toString();
        String pwd = password.getText().toString();
        Boolean err = false;


        if (name.isEmpty()){
            showError(username,"Invalid username");
            err = true;
        }
        if(name.length()>20){
            showError(username,"Maximum 20 characters");
            err = true;
        }
        if(emailId.isEmpty() || !emailId.contains("@")){
            showError(email,"Invalid email Id");
            err = true;
        }
        if(pwd.isEmpty() || pwd.length()>30){
            showError(password,"Maximum 30 characters");
            err = true;
        }
        if(phoneNo.isEmpty() || phoneNo.length()!=10 ){
            showError(phone,"Invalid Phone number");
            err = true;
        }
        if(conPwd.isEmpty() || !conPwd.equals(pwd)){
            showError(confirm_password,"Password does not match");
            err = true;
        }
        if(Address.isEmpty()){
            showError(address,"Invalid address");
            err = true;
        }
        if(err == false){
              loading.setTitle("Verification");
              loading.setMessage("Please wait,while verifying your credentials");
              loading.setCanceledOnTouchOutside(false);
              loading.show();

              myAuth.createUserWithEmailAndPassword(emailId,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                          Toast.makeText(User_Register.this,"Credentials are verified",Toast.LENGTH_SHORT).show();
                           addUser(name,phoneNo,emailId,pwd,Address);
                          Intent i = new Intent(User_Register.this,MainActivity.class);
                          i.setFlags(i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                          startActivity(i);
                      }else {
                         Toast.makeText(User_Register.this,"Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                     }
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(User_Register.this,"Error : "+e.getMessage(),Toast.LENGTH_SHORT).show();
                  }
              });
            rootNode = FirebaseDatabase.getInstance();
            ref = rootNode.getReference("users");
        }
    }

    private void addUser(String name,String phoneNo,String emailId,String pwd,String Address) {
        dbRef = ref.child("users");
        String key = dbRef.push().getKey();

        HashMap<String , String> user  = new HashMap<>();
        user.put("Key",key);
        user.put("Username",name);
        user.put("Phone",phoneNo);
        user.put("Email",emailId);
        user.put("Address",Address);
        user.put("Password",pwd);

        dbRef.child(key).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                      Toast.makeText(User_Register.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                      openMain();
                }else{
                    Toast.makeText(User_Register.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(User_Register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showError(EditText input, String s){
        Drawable error = getResources().getDrawable(R.drawable.ic_baseline_error_24);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        input.setError(s,error);
        input.requestFocus();
    }
}