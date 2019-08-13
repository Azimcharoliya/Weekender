package com.weekender;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.util.Date;

public class specific extends AppCompatActivity {

    int row,fromwhole,flag=0,pos;
    Button book;
    ImageView imageView1;
    TextView title,details,price,time,venue,date;
    ImageButton bookmark;
    String price1,time1,date1,details1,venue1,title1,imageurl,status;
    String categories[] = {"plays", "clubs", "cafes", "parties"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);
        Intent i = getIntent();
        row = i.getIntExtra("row",-1);
        pos = i.getIntExtra("pos",-1);
        imageurl = i.getStringExtra("list");
        imageView1 = findViewById(R.id.simg);

        title=findViewById(R.id.specifics);
        details=findViewById(R.id.description);
        price=findViewById(R.id.amount);
        time=findViewById(R.id.time);
        venue=findViewById(R.id.place);
        date = findViewById(R.id.date);
        bookmark = findViewById(R.id.bookmark);
        book = findViewById(R.id.book);
        fromwhole = i.getIntExtra("whole",-1);

        if(i.getIntExtra("activity",-1)==2)
        {
            row=whole.row;
        }

        String category = "";
        if (row == 0)
            category = categories[0];
        else if (row == 1)
            category = categories[1];
        else if (row == 2)
            category = categories[2];
        else if (row == 3)
            category = categories[3];

        /*if(whole.search==1)
        {
            int l=row*2+pos;
            category=whole.cat.get(l);
        }*/

        DatabaseReference ref,refuser,refb;

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        refb = mDatabaseRef.child(category);


        if(fromwhole==1 && whole.search==1)
        {
            for(int j=0;j<4;j++) {
                DatabaseReference refc  = refb.child(categories[j]);
                Query query = refc.orderByChild("imageurl").equalTo(imageurl).limitToFirst(1);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                String url = postSnapshot.child("imageurl").getValue(String.class);
                                Picasso.with(getApplicationContext()).load(url).fit().into(imageView1);

                                price1 = postSnapshot.child("price").getValue(String.class);
                                time1 = postSnapshot.child("time").getValue(String.class);
                                date1 = postSnapshot.child("date").getValue(String.class);
                                details1 = postSnapshot.child("details").getValue(String.class);
                                venue1 = postSnapshot.child("venue").getValue(String.class);
                                title1 = postSnapshot.getKey();
                                status = postSnapshot.child("status").getValue(String.class);

                                if (fromwhole == 1 && whole.booking == 1) {
                                    book.setText("Status : " + status);
                                    book.setClickable(false);
                                } else
                                    book.setClickable(true);

                                //String time2 = time1.toString();
                                //String date2 = date1.toString();
                                price.setText("Amount : " + price1);
                                date.setText("Date : " + date1);
                                venue.setText("Venue : " + venue1);
                                details.setText("Description : " + details1);
                                title.setText(title1);
                                time.setText("Time : " + time1);
                            }
                            //    mProgressCircle.setVisibility(View.INVISIBLE);
                        }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(specific.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        //mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });
            }

        }
        else {
            if (fromwhole == 1 && (whole.bookmark == 1 || whole.booking == 1)) {
                ref = FirebaseDatabase.getInstance().getReference("users");
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                refuser = ref.child(userid);
                if (whole.bookmark == 1)
                    refb = refuser.child("bookmarks");
                else
                    refb = refuser.child("bookings");
            }

            Query query = refb.orderByChild("imageurl").equalTo(imageurl).limitToFirst(1);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String url = postSnapshot.child("imageurl").getValue(String.class);
                        Picasso.with(getApplicationContext()).load(url).fit().into(imageView1);

                        price1 = postSnapshot.child("price").getValue(String.class);
                        time1 = postSnapshot.child("time").getValue(String.class);
                        date1 = postSnapshot.child("date").getValue(String.class);
                        details1 = postSnapshot.child("details").getValue(String.class);
                        venue1 = postSnapshot.child("venue").getValue(String.class);
                        title1 = postSnapshot.getKey();
                        status = postSnapshot.child("status").getValue(String.class);

                        if (fromwhole == 1 && whole.booking == 1) {
                            book.setText("Status : " + status);
                            book.setClickable(false);
                        } else
                            book.setClickable(true);

                        //String time2 = time1.toString();
                        //String date2 = date1.toString();
                        price.setText("Amount : " + price1);
                        date.setText("Date : " + date1);
                        venue.setText("Venue : " + venue1);
                        details.setText("Description : " + details1);
                        title.setText(title1);
                        time.setText("Time : " + time1);
                    }
                    //    mProgressCircle.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(specific.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    //mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });
        }


        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference DatabaseRef = FirebaseDatabase.getInstance().getReference("users");
            DatabaseReference dref = DatabaseRef.child(userid);
            DatabaseReference check = dref.child("bookmarks");

            //DatabaseReference q = check.child(title1);
            check.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String key = data.getKey();
                        if (key.equals(title1)) {
                            flag = 1;
                            Drawable drawable = getResources().getDrawable(R.drawable.bookmark_final);
                            bookmark.setImageDrawable(drawable);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
             if(flag!=1) {
                flag = 0;
                Drawable drawable = getResources().getDrawable(R.drawable.bookmark_initial);
                bookmark.setImageDrawable(drawable);
            }
        }

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth fb = FirebaseAuth.getInstance();
                if(fb.getCurrentUser()!=null) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                    String userid = fb.getCurrentUser().getUid();
                    DatabaseReference refuid = databaseReference.child(userid);
                    DatabaseReference refusers = refuid.child("bookmarks");
                    if(flag==0) {
                        Drawable drawable = getResources().getDrawable(R.drawable.bookmark_final);
                        bookmark.setImageDrawable(drawable);
                        DatabaseReference ref = refusers.child(title1);
                        ref.child("price").setValue(price1);
                        ref.child("imageurl").setValue(imageurl);
                        ref.child("date").setValue(date1);
                        ref.child("time").setValue(time1);
                        ref.child("venue").setValue(venue1);
                        ref.child("details").setValue(details1);
                        flag=1;
                    }
                    else
                    {
                        Drawable drawable = getResources().getDrawable(R.drawable.bookmark_initial);
                        bookmark.setImageDrawable(drawable);
                        refusers.child(title1).setValue(null);
                        flag=0;
                    }
                }
            else
                Toast.makeText(getApplicationContext(),"Please Login to bookmark",Toast.LENGTH_LONG).show();
        }});

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth fb = FirebaseAuth.getInstance();
                if(fb.getCurrentUser()!=null) {
                    if(fb.getCurrentUser().isEmailVerified()) {
                        Intent i = new Intent(getApplicationContext(), payment.class);
                        i.putExtra("price", price1);
                        i.putExtra("imageurl", imageurl);
                        i.putExtra("date", date1);
                        i.putExtra("time", time1);
                        i.putExtra("title", title1);
                        i.putExtra("venue", venue1);
                        i.putExtra("details", details1);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"E-mail id verification is incomplete, kindly check your mailbox for the same.",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Please Login to book",Toast.LENGTH_LONG).show();
            }
        });
    }
}
