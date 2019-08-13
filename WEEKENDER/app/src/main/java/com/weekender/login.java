package com.weekender;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {


    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;

    Button fp;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        fp = findViewById(R.id.forgotpassword);
        //if getCurrentUser does not returns null
        if (firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), home.class));
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.mailid);
        editTextPassword = (EditText) findViewById(R.id.pword);

        progressDialog = new ProgressDialog(this);

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), forgotpassword.class));

            }});
    }

    public void swit(View view) {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }

    public void skip(View view)
    {
        startActivity(new Intent(this,home.class));
    }
    public void loginuser(View view){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("logging Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            if(firebaseAuth.getCurrentUser().getUid().equals("dSYoU5cWogPgyhsgOkxIpjE1pZH2"))
                                startActivity(new Intent(getApplicationContext(),admin.class));
                            else
                                startActivity(new Intent(getApplicationContext(), home.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Login Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                    }
}
