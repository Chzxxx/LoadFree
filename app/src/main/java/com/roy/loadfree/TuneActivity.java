package com.roy.loadfree;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.*;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.InputStream;
import java.net.URL;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

public class TuneActivity extends AppCompatActivity  {

    private TextView userEmail;
    private EditText setWeight,setReps,setSets;
    private Button myProfile,Confirm;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;////////////////////////////////////


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tune);


        setupTuneViews();
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

       if(firebaseAuth.getCurrentUser() == null){
           finish();
           startActivity(new Intent(TuneActivity.this, MainActivity.class));
       }

        databaseReference = FirebaseDatabase.getInstance().getReference();//////////////////////////////////
        userEmail.setText("Welcom  user : \n" + user.getEmail());

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weight = setWeight.getText().toString().trim();
                String reps = setReps.getText().toString().trim();
                String sets = setSets.getText().toString().trim();

                UserProfile userProfile = new UserProfile(reps, sets, weight);
                databaseReference.child(user.getUid()).setValue(userProfile);///////////////////////////////

//                String setReps = "https://api.thingspeak.com/update?api_key=1Z4S2VFG0GV7F87K&field1="+reps;
//                String setSets = "https://api.thingspeak.com/update?api_key=1Z4S2VFG0GV7F87K&field2="+sets;
//                String setWeight = "https://api.thingspeak.com/update?api_key=1Z4S2VFG0GV7F87K&field3="+weight;

//                String setReps="1="+reps;
//                String setSets="2="+sets;
//                String setWeight="3="+weight;
                String all="&field1="+weight+"&field2="+reps+"&field3="+sets;
                ThingSpeak.get(all,null,new JsonHttpResponseHandler());




               Toast.makeText(TuneActivity.this, "Information Saved...", Toast.LENGTH_SHORT).show();
                }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                firebaseAuth.signOut();
//                finish();
//                startActivity(new Intent(TuneActivity.this, MainActivity.class));
                String url = "https://thingspeak.com/channels/561554";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(TuneActivity.this, MainActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupTuneViews(){
        userEmail=(TextView)findViewById(R.id.eduserEmail) ;

        setWeight = (EditText)findViewById(R.id.edWeight);
        setReps = (EditText)findViewById(R.id.edReps);
        setSets = (EditText)findViewById(R.id.edSets);

        myProfile = (Button)findViewById(R.id.btnMyProfile);
        Confirm = (Button)findViewById(R.id.btnConfirm);
    }

//    private void sendUserData(){
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid()); //no user in same name get same data
//        UserProfile userProfile = new UserProfile(weight,reps,sets); // under Uid
//        myRef.setValue(userProfile);
//    }

//    public void download(String url) {
//        URL myFileURL = null;
//        InputStream is = null;
//        try {
//            myFileURL = new URL(url);
//            is = myFileURL.openStream();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
