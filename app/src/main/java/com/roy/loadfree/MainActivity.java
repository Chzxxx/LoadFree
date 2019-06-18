package com.roy.loadfree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private EditText userEmail, userPassword;
    private Button userLogin;
    private Button regButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;//
    private static final String PREF_NAME = "prefsfile";//

    private CheckBox checkBox;
    int weight,reps,sets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);//
        setupUIViews();
        getPreferenceData();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

//        if(firebaseAuth.getCurrentUser() !=null){
//            finish();
//            startActivity(new Intent(MainActivity.this, TuneActivity.class));
//        }

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(userEmail.getText().toString(), userPassword.getText().toString());
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        }


    private void setupUIViews(){
        userEmail = (EditText)findViewById(R.id.logUserEmail);
        userPassword = (EditText)findViewById(R.id.logUserPassword);
        userLogin = (Button)findViewById(R.id.btnLogin);
        regButton = (Button)findViewById(R.id.btnRegisterNow);
        checkBox=(CheckBox)findViewById(R.id.rememberMe);
    }
    private void getPreferenceData(){
        SharedPreferences sp = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        if(sp.contains("pref_mail")){
            String u = sp.getString("pref_mail","not found.");
            userEmail.setText(u.toString());
        }
        if(sp.contains("pref_pass")){
            String p = sp.getString("pref_pass","not found.");
            userPassword.setText(p.toString());
        }
        if(sp.contains("pref_check")){
            Boolean b = sp.getBoolean("pref_check", false);
            checkBox.setChecked(b);
        }
    }
// if validate
    private void validate(String Email, String Password) {
        progressDialog.setMessage("Connecting to your profile ");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    if(checkBox.isChecked()){
                        Boolean boolIsCheck = checkBox.isChecked();
                        SharedPreferences.Editor editor = sharedPreferences.edit();//
                        editor.putString("pref_mail",userEmail.getText().toString());
                        editor.putString("pref_pass",userPassword.getText().toString());
                        editor.putBoolean("pref_check",boolIsCheck);
                        editor.apply();
                    }else{
                        sharedPreferences.edit().clear().apply();
                    }
                    userEmail.getText().clear();
                    userPassword.getText().clear();
                    finish();
                    startActivity(new Intent(MainActivity.this, TuneActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
