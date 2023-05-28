package fpt.edu.vn.a420flowershop.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import fpt.edu.vn.a420flowershop.Models.UserModel;
import fpt.edu.vn.a420flowershop.R;

public class RegisterActivity extends AppCompatActivity {
    Button login, register;
    EditText username, password, email, phone, re_password;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init_view();
        click_login();
        click_register();
    }

    private void click_login() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void init_view() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressBar = findViewById(R.id.idprogressbar);
        progressBar.setVisibility(View.GONE);
        //button
        login = findViewById(R.id.bt_login);
        register = findViewById(R.id.bt_register);
        //edt text
        username = findViewById(R.id.txt_username);
        phone = findViewById(R.id.txt_phone);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.edt_password);
        re_password = findViewById(R.id.edt_password2);
    }

    private void click_register() {

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }


    private void createUser() {
        String userName = username.getText().toString();
        String userPhone = phone.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userRePassword = re_password.getText().toString();

        if (TextUtils.isEmpty((userName))) {
            Toast.makeText(this, "Username is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty((userEmail))) {
            Toast.makeText(this, "Email Number is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty((userPassword))) {
            Toast.makeText(this, "Password is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty((userRePassword))) {
            Toast.makeText(this, "Re-password is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }
       /* if(!TextUtils.equals(userPassword, userRePassword)){
            Toast.makeText(this, "Password does not match!", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password length must > 6!", Toast.LENGTH_SHORT).show();
            return;
        }

        // create user
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    UserModel userModel = new UserModel(userName, userEmail, userPhone, userPassword, userRePassword);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(userModel);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Register successfull", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Register fail, error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}