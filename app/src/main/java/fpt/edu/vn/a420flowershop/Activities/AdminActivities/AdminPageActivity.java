package fpt.edu.vn.a420flowershop.Activities.AdminActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fpt.edu.vn.a420flowershop.Activities.LoginActivity;
import fpt.edu.vn.a420flowershop.Activities.RegisterActivity;
import fpt.edu.vn.a420flowershop.R;

public class AdminPageActivity extends AppCompatActivity {
 private Button btn_manageProduct, btn_manageAccount, btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        init();
        click_manageProduct();
        click_manageAccount();
        click_logout();
    }

    private void init(){
        btn_manageProduct = findViewById(R.id.ad_manage_product);
        btn_manageAccount = findViewById(R.id.ad_manage_account);
        btn_logout = findViewById(R.id.ad_logout);
    }

    private void click_manageProduct(){
        btn_manageProduct.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(AdminPageActivity.this, ManageProductActivity.class));
                Toast.makeText(AdminPageActivity.this, "Welcome to Manage Product Page", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void click_manageAccount(){
        btn_manageAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(AdminPageActivity.this, ManageAccountActivity.class));
                Toast.makeText(AdminPageActivity.this, "Welcome to Manage Account Page", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void click_logout(){
        btn_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(AdminPageActivity.this, LoginActivity.class));
                Toast.makeText(AdminPageActivity.this, "Logout !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}