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

import fpt.edu.vn.a420flowershop.Activities.AdminActivities.AdminPageActivity;
import fpt.edu.vn.a420flowershop.Activities.AdminActivities.ManageProductActivity;
import fpt.edu.vn.a420flowershop.R;

public class LoginActivity extends AppCompatActivity {

    Button register, login;
    EditText email, password;
    FirebaseAuth auth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init_view();
        click_register();
        click_login();
    }

    private void init_view() {
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.idprogressbar);
        progressBar.setVisibility(View.GONE);
        // button
        login = findViewById(R.id.bt_login);
        register = findViewById(R.id.bt_register);
        // edt text
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
    }
    private void click_register(){
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void click_login(){
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty((userEmail))) {
            Toast.makeText(this, "Email is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty((userPassword))) {
            Toast.makeText(this, "Password is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        //login
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    if (auth.getCurrentUser().getEmail().equals("admin@admin.com")){
                        startActivity(new Intent(LoginActivity.this, AdminPageActivity.class));
                    }
                    else startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login fail, error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}