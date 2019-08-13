package com.weekender;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class signup extends AppCompatActivity
{

    private EditText editTextEmail,editTextPassword,firstname,lastname,confirm;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if (firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), home.class));
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.eid);
        editTextPassword = (EditText) findViewById(R.id.passwrd);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        confirm = findViewById(R.id.cpasswrd);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
    }

    public void registerUser(View view){

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        final String password  = editTextPassword.getText().toString().trim();

         final String first  = firstname.getText().toString().trim();
        final String last  = lastname.getText().toString().trim();
        String cpassword  = confirm.getText().toString().trim();
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(first)){
            Toast.makeText(this,"Please enter First Name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(last)){
            Toast.makeText(this,"Please enter Last Name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
    FirebaseDatabase database = FirebaseDatabase.getInstance();
            Toast.makeText(this,"Please enter Email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)){
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_LONG).show();
            return;
        }

        if(!password.equals(cpassword)){
            Toast.makeText(this,"Please check password fields",Toast.LENGTH_LONG).show();
            return;
        }
        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();


        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            String userid = firebaseAuth.getUid();
                            User user = new User(first,last,email);
                        // pushing user to 'users' node using the userId
                            myRef.child(userid).setValue(user);
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            finish();
                            startActivity(new Intent(getApplicationContext(), home.class));
                        }else{
                            //display some message here
                            Toast.makeText(signup.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }

}
