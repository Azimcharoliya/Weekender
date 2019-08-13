package com.weekender;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzbfq.NULL;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    ProgressBar mProgressCircle;
    homecustomadapter myAdapter;
    ImageButton search;
    EditText srch;
    public ArrayList<homelistrow> list;
    String[] plays= {"0","0","0","0","0","0"};
    String[] clubs= {"0","0","0","0","0","0"};
    String[] cafes= {"0","0","0","0","0","0"};
    String[] parties= {"0","0","0","0","0","0"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        search = findViewById(R.id.searchbutton);
        srch = findViewById(R.id.searchtext);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        final TextView mfname = headerview.findViewById(R.id.mfname);

        firebaseAuth = FirebaseAuth.getInstance();
        mProgressCircle = findViewById(R.id.progress_circle);

        if(firebaseAuth.getCurrentUser()!=null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid()).child("firstname");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mfname.setText(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
        mfname.setVisibility(View.GONE);


         ListView listView = findViewById(R.id.listview);

         list = new ArrayList<>();
         myAdapter = new homecustomadapter(this,list);
         listView.setAdapter(myAdapter);

         DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
             Query refplays = mDatabaseRef.child("plays").limitToFirst(6);
             refplays.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     int i = 0;
                     for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                         String url = postSnapshot.child("imageurl").getValue(String.class);
                         plays[i] = url;
                         i++;
                     }
                     myAdapter.notifyDataSetChanged();
                     mProgressCircle.setVisibility(View.INVISIBLE);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {
                     Toast.makeText(home.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                     mProgressCircle.setVisibility(View.INVISIBLE);
                 }
             });

        Query refclubs= mDatabaseRef.child("clubs").limitToFirst(6);
        refclubs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String url=postSnapshot.child("imageurl").getValue(String.class);
                    clubs[i]=url;
                    i++;
                    }
                myAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(home.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


        Query refcafes= mDatabaseRef.child("cafes").limitToFirst(6);
        refcafes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String url=postSnapshot.child("imageurl").getValue(String.class);
                    cafes[i]=url;
                    i++;
                    }
                myAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressCircle.setVisibility(View.INVISIBLE);
                Toast.makeText(home.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        Query refparties= mDatabaseRef.child("parties").limitToFirst(6);
        refparties.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String url=postSnapshot.child("imageurl").getValue(String.class);
                    parties[i]=url;
                    i++;
                    }
                myAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(home.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        list.clear();
        list.add(new homelistrow(plays,"Plays"));
        list.add(new homelistrow(clubs,"Clubs"));
        list.add(new homelistrow(cafes,"Cafes"));
        list.add(new homelistrow(parties,"Parties"));

        srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_NULL && event.getAction()==event.ACTION_DOWN)
                {
                    if (!TextUtils.isEmpty(srch.getText().toString())) {
                        Intent i = new Intent(getApplicationContext(), whole.class);
                        i.putExtra("search", 1);
                        i.putExtra("searchtext", srch.getText().toString());
                        startActivity(i);
                    }
                return true;

                }
                return false;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(srch.getText().toString()))
                {
                Intent i = new Intent(getApplicationContext(), whole.class);
                i.putExtra("search",1);
                i.putExtra("searchtext",srch.getText().toString());
                startActivity(i);
            }}
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.home:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.mybookings:
                if(firebaseAuth.getCurrentUser()!=null) {
                    Intent ib = new Intent(this, whole.class);
                    ib.putExtra("booking", 1);
                    startActivity(ib);
                }
                else
                    Toast.makeText(this,"Please Login to see your booking",Toast.LENGTH_LONG).show();
                break;
            case R.id.bookmarks:
                if (firebaseAuth.getCurrentUser()!=null) {
                    Intent i = new Intent(this, whole.class);
                    i.putExtra("bookmark", 1);
                    startActivity(i);
                }
                else
                    Toast.makeText(this,"Please Login to see your bookmarks",Toast.LENGTH_LONG).show();
                break;
            case R.id.logout:
                if(firebaseAuth.getCurrentUser()!=null) {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(this,login.class));
                }
                else
                    startActivity(new Intent(this,login.class));
                break;

            }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
