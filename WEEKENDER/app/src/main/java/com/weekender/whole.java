package com.weekender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class whole extends AppCompatActivity {

    ProgressBar mProgressCircle;
    wholecustomadapter myAdapter;
    static int row,search;
    static int bookmark,booking;
    String stext;
    String categories[] = {"plays", "clubs", "cafes", "parties"};
    List<wholelistrow> list = new ArrayList<>();
    static List<String> cat = new ArrayList<>();
    TextView topic;
    String imageid[]={"0","0"};
    int k[]={1};
    int j=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole);
        Intent i = getIntent();
        row = i.getIntExtra("row", -1);
        bookmark = i.getIntExtra("bookmark", -1);
        booking = i.getIntExtra("booking", -1);
        search = i.getIntExtra("search", -1);
        stext = i.getStringExtra("searchtext");

        list = new ArrayList<>();
        mProgressCircle = findViewById(R.id.progress_bar);
        topic = findViewById(R.id.topic);
        ListView listView = findViewById(R.id.wlistview);

        myAdapter = new wholecustomadapter(this, list);
        listView.setAdapter(myAdapter);

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        if (search != 1)
        {
            String category = "";
            if (row == 0)
                category = categories[0];
            else if (row == 1)
                category = categories[1];
            else if (row == 2)
                category = categories[2];
            else if (row == 3)
                category = categories[3];

            topic.setText(category);
            DatabaseReference ref, refuser, refb;

            refb = mDatabaseRef.child(category);

            if (bookmark == 1 || booking == 1) {
                ref = FirebaseDatabase.getInstance().getReference("users");
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                refuser = ref.child(userid);
                if (bookmark == 1) {
                    refb = refuser.child("bookmarks");
                    topic.setText("Bookmarks");
                } else if (booking == 1) {
                    refb = refuser.child("bookings");
                    topic.setText("Bookings");
                }
            }
            ValueEventListener mDBListener = refb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 1;
                    list.clear();
                    String imageid1 = "0", imageid2 = "0";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (i % 2 == 1)
                            imageid1 = postSnapshot.child("imageurl").getValue(String.class);
                        else
                            imageid2 = postSnapshot.child("imageurl").getValue(String.class);
                        if (i % 2 == 0)
                            list.add(new wholelistrow(imageid1, imageid2));
                        i++;
                    }
                    if (i % 2 == 0)
                        list.add(new wholelistrow(imageid1, "0"));
                    myAdapter.notifyDataSetChanged();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(whole.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });

        }
        else if(search==1)
        {
            topic.setText("Results for "+stext);
            list.clear();
            for(j=0;j<4;j++)
            {
                DatabaseReference ref = mDatabaseRef.child(categories[j]);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if (postSnapshot.getKey().toLowerCase().contains(stext.toLowerCase())) {
                                if (k[0] % 2 == 1)
                                    imageid[0] = postSnapshot.child("imageurl").getValue(String.class);
                                else
                                    imageid[1] = postSnapshot.child("imageurl").getValue(String.class);
                                if (k[0] % 2 == 0)
                                    list.add(new wholelistrow(imageid[0], imageid[1]));
                                k[0]++;
                            }
                        }
                        if (k[0] % 2 == 0) {
                            list.add(new wholelistrow(imageid[0], "0"));
                            k[0]++;
                        }
                        myAdapter.notifyDataSetChanged();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(whole.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });

            }
        }
    }
}