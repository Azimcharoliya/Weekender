package com.weekender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class payment extends AppCompatActivity {

    Intent i;
    TextView amt,total;
    EditText quantity;
    int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        i = getIntent();

        amt=findViewById(R.id.amt);
        total=findViewById(R.id.total);
        quantity=findViewById(R.id.quantity);
        price=Integer.parseInt(i.getStringExtra("price"));
        amt.setText( price+ "   X   ");

        quantity.addTextChangedListener(testwatcher);



        Button b = findViewById(R.id.verf);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth fb = FirebaseAuth.getInstance();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                    String userid = fb.getCurrentUser().getUid();
                    DatabaseReference refuid = databaseReference.child(userid);
                    DatabaseReference refusers = refuid.child("bookings");
                    DatabaseReference ref = refusers.child(i.getStringExtra("title"));
                    ref.child("price").setValue(i.getStringExtra("price"));
                    ref.child("imageurl").setValue(i.getStringExtra("imageurl"));
                    ref.child("date").setValue(i.getStringExtra("date"));
                    ref.child("time").setValue(i.getStringExtra("time"));
                    ref.child("venue").setValue(i.getStringExtra("venue"));
                    ref.child("details").setValue(i.getStringExtra("details"));
                    ref.child("status").setValue("Pending");
                    startActivity(new Intent(payment.this, verificationmsg.class));
                }
        });
    }

    private TextWatcher testwatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(quantity.getText().toString()))
                total.setText("   =   0");
            else
            total.setText("   =   " + Integer.parseInt(quantity.getText().toString())*price);
        }
    };
}

