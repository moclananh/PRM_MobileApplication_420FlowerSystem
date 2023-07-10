package fpt.edu.vn.a420flowershop.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fpt.edu.vn.a420flowershop.Models.UserModel;
import fpt.edu.vn.a420flowershop.R;

public class RegisterActivity extends AppCompatActivity {
    Button login, register;
    EditText username, password, email, phone, re_password;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;
    boolean isAllFieldsChecked = false;

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
        database = FirebaseDatabase.getInstance("https://flowershop-339a3-default-rtdb.firebaseio.com/");
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
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    createUser();
                    progressBar.setVisibility(View.VISIBLE);
                }

            }
        });

    }

   Boolean isAdmin = false;
    private void createUser() {
        String userName = username.getText().toString().trim();
        String userPhone = phone.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String userRePassword = re_password.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Boolean userIsAdmin = isAdmin;

        // create user
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    UserModel user = new UserModel(userName, userEmail, userPhone, userPassword, userRePassword,"https://firebasestorage.googleapis.com/v0/b/flowershop-339a3.appspot.com/o/profile_picture%2Fa2AYaOvpzoRO6z17M739OOjVUR33?alt=media&token=950381f3-2529-4772-9cdf-0ea64cf0d0e6","", isAdmin);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(user);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Register fail, error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        // Define the regex pattern for a phone number
        String regexPattern = "^[+]?[0-9]{10,13}$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regexPattern);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(phoneNumber);

        // Perform the matching and return the result
        return matcher.matches();
    }

    private boolean CheckAllFields() {
        String userName = username.getText().toString().trim();
        String userPhone = phone.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String userRePassword = re_password.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String phonePattern = "/^0[1-9]\\d{8,10}$/";

        if (userName.isEmpty()) {
            username.setError("This field is required");
            return false;
        }

        if (userPhone.isEmpty()) {
            phone.setError("This field is required");
            return false;
        } else if (!userPhone.isEmpty() && !isValidPhoneNumber(userPhone)) {
            phone.setError("Only phone number input allowed");
            return false;
        }

        if (userEmail.isEmpty()) {
            email.setError("This field is required");
            return false;
        } else if (!userEmail.isEmpty() && !userEmail.matches(emailPattern)) {
            email.setError("Only email input allowed");
            return false;
        }

        if (userPassword.isEmpty()) {
            password.setError("This field is required");
            return false;
        } else if (userPassword.length() <= 6){
            password.setError("Password length must > 6");
        }

        if (userRePassword.isEmpty()){
            re_password.setError("This field is required");
            return false;
        } else if (!TextUtils.equals(userPassword, userRePassword)) {
            re_password.setError("Re-Password does not match above password");
            return false;
        }

        return true;
    }
}