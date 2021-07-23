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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Caterer_Register extends AppCompatActivity {
    private TextView btn;

    private EditText username,email,password,conPassword,Address,Phone,service_name;
    private Button btn_register;

    private FirebaseAuth myAuth;
    private ProgressDialog loading;
    private FirebaseDatabase rootNode;
    private DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer__register);

        btn = (TextView)findViewById(R.id.calreadyhaveaccount);

        username = (EditText)findViewById(R.id.cusername);
        email = (EditText)findViewById(R.id.cemailid);
        password = (EditText)findViewById(R.id.cPassword);
        conPassword = (EditText)findViewById(R.id.cconfirmPassword);
        Address = (EditText)findViewById(R.id.cAddress);
        Phone = (EditText)findViewById(R.id.cPhoneno);
        service_name = (EditText)findViewById(R.id.sevicename);

        btn_register = (Button)findViewById(R.id.cRegister);

        myAuth = FirebaseAuth.getInstance();
        loading = new ProgressDialog(Caterer_Register.this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Caterer_Register.this,Login_Page.class);
                startActivity(i);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkCatererDetails();
            }
        });
    }

    private void checkCatererDetails() {
        String name = username.getText().toString();
        String emailId = email.getText().toString();
        String phoneNo = Phone.getText().toString();
        String address = Address.getText().toString();
        String conPwd = conPassword.getText().toString();
        String serviceName = service_name.getText().toString();
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
            showError(Phone,"Invalid Phone number");
            err = true;
        }
        if(conPwd.isEmpty() || !conPwd.equals(pwd)){
            showError(conPassword,"Password does not match");
            err = true;
        }
        if(address.isEmpty()){
            showError(Address,"Invalid address");
            err = true;
        }
        if(serviceName.isEmpty() || serviceName.length()>20){
            showError(service_name,"Maximum 20 Characters");
        }
        if(err == false){
            loading.setTitle("Verification");
            loading.setMessage("Please wait");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            myAuth.createUserWithEmailAndPassword(emailId,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Caterer_Register.this,"Credentials are verified",Toast.LENGTH_SHORT).show();
                        addUser(name,phoneNo,emailId,pwd,address,serviceName);
                        Intent i = new Intent(Caterer_Register.this,MainActivity.class);
                        i.setFlags(i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        loading.dismiss();
                    }else {
                        Toast.makeText(Caterer_Register.this,"Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loading.dismiss();
                    Toast.makeText(Caterer_Register.this,"Error : "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            rootNode = FirebaseDatabase.getInstance();
            dbRef = rootNode.getReference("caterer");
        }
    }

    private void addUser(String name,String phoneNo,String emailId,String pwd,String Address,String serviceName) {
        //dbRef = ref.child("caterer");
        String key = dbRef.push().getKey();

        HashMap<String , String> user  = new HashMap<>();
        user.put("Key",key);
        user.put("Username",name);
        user.put("Phone",phoneNo);
        user.put("Email",emailId);
        user.put("Address",Address);
        user.put("Password",pwd);
        user.put("ServiceName",serviceName);

        dbRef.child(key).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Caterer_Register.this,"Registered Successfully",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(Caterer_Register.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Caterer_Register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showError(EditText input, String s) {
        Drawable error = getResources().getDrawable(R.drawable.ic_baseline_error_24);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        input.setError(s,error);
        input.requestFocus();
    }
}