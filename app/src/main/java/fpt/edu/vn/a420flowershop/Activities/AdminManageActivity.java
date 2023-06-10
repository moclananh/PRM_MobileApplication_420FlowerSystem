package fpt.edu.vn.a420flowershop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import fpt.edu.vn.a420flowershop.Adapters.AllProductAdapter;
import fpt.edu.vn.a420flowershop.Models.ProductModel;
import fpt.edu.vn.a420flowershop.R;

public class AdminManageActivity extends AppCompatActivity {
    Button btn_back;
    RecyclerView recyclerView;
    AllProductAdapter allProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage);
        init_view();
        loadData();
    }

    private void loadData(){
        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), ProductModel.class)
                .build();
        allProductAdapter = new AllProductAdapter(options);
        recyclerView.setAdapter(allProductAdapter);
    }


    private void init_view() {
        // recyclerView
        recyclerView = findViewById(R.id.rec_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //button
        btn_back = findViewById(R.id.btnBack);
    }

    @Override
    protected void onStart(){
        super.onStart();
        allProductAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        allProductAdapter.stopListening();
    }
}