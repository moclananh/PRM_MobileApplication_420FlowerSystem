package fpt.edu.vn.a420flowershop.Activities.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import fpt.edu.vn.a420flowershop.R;

public class AddNewProductActivity extends AppCompatActivity {

    EditText product_name, product_price, product_cat, product_img, product_stock, product_des, product_status;
    Button btnAddNew, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        init_view();
        clickAddNew();
        clickBack();
    }
    private void init_view() {
        //btn
        btnAddNew = findViewById(R.id.btnAddNew);
        btnBack = findViewById(R.id.btnBack);
        // edt
        product_name = findViewById(R.id.pro_name_id);
        product_price = findViewById(R.id.pro_price_id);
        product_cat = findViewById(R.id.pro_cat_id);
        product_img = findViewById(R.id.pro_img_id);
        product_stock = findViewById(R.id.pro_stock_id);
        product_des = findViewById(R.id.pro_des_id);
        product_status = findViewById(R.id.pro_status_id);
    }

    private void clickAddNew(){
        btnAddNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                insertData();
                clearContent();
            }
        });
    }

    private void clickBack(){
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(AddNewProductActivity.this, AdminManageActivity.class));
            }
        });
    }

    private void insertData(){
        Map<String, Object> map = new HashMap<>();
        map.put("product_name", product_name.getText().toString());
        map.put("product_price", product_price.getText().toString());
        map.put("product_cat", product_cat.getText().toString());
        map.put("product_img", product_img.getText().toString());
        map.put("product_stock", product_stock.getText().toString());
        map.put("product_des", product_des.getText().toString());
        map.put("product_status", product_status.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("products")
                .push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddNewProductActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewProductActivity.this, "Insert error!! ", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearContent(){
        product_name.setText("");
        product_price.setText("");
        product_cat.setText("");
        product_img.setText("");
        product_stock.setText("");
        product_des.setText("");
        product_status.setText("");
    }
}