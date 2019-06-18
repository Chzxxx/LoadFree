package com.roy.loadfree;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText regName, regPassword, regEmail;
    private Button regBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
// Name+Email+Password  and  Register
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //upload to the database
                    String user_email = regEmail.getText().toString().trim();
                    String user_password = regPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registration Successful !", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent (RegisterActivity.this, MainActivity.class));
                            }
                        else{
                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void setupUIViews(){
        regPassword = (EditText)findViewById(R.id.regPassword);
        regEmail = (EditText)findViewById(R.id.regEmail);
        regBtn = (Button)findViewById(R.id.regRegister);
    }

    private Boolean validate(){
        String name, password, email;
        Boolean result = false;
        password = regPassword.getText().toString();
        email = regEmail.getText().toString();

        if(password.isEmpty() || email.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }


}
