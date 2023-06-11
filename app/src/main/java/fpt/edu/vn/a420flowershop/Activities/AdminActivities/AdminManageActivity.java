package fpt.edu.vn.a420flowershop.Activities.AdminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import fpt.edu.vn.a420flowershop.Activities.LoginActivity;
import fpt.edu.vn.a420flowershop.Adapters.AllProductAdapter;
import fpt.edu.vn.a420flowershop.Adapters.ManageProductAdapter;
import fpt.edu.vn.a420flowershop.Models.ProductModel;
import fpt.edu.vn.a420flowershop.R;

public class AdminManageActivity extends AppCompatActivity {
    Button btn_logout, btn_add_new;
    RecyclerView recyclerView;
    ManageProductAdapter manageProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage);
        init_view();
        loadData();
        click_add();
        click_logout();
    }

    private void loadData(){
        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), ProductModel.class)
                .build();
        manageProductAdapter = new ManageProductAdapter(options);
        recyclerView.setAdapter(manageProductAdapter);
    }


    private void init_view() {
        // recyclerView
        recyclerView = findViewById(R.id.rec_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //button
        btn_logout = findViewById(R.id.btnLogout);
        btn_add_new = findViewById(R.id.btnAddNew);
    }

    private void click_add(){
        btn_add_new.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(AdminManageActivity.this, AddNewProductActivity.class));
            }
        });
    }

    private void click_logout(){
        btn_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(AdminManageActivity.this, LoginActivity.class));
                Toast.makeText(AdminManageActivity.this, "Logout!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        manageProductAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        manageProductAdapter.stopListening();
    }

    // search product
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_product, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //get product by keyword
    private void txtSearch( String txt){
        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("products").orderByChild("product_name").startAt(txt).endAt(txt+"~"), ProductModel.class)
                .build();
        manageProductAdapter = new ManageProductAdapter(options);
        manageProductAdapter.startListening();
        recyclerView.setAdapter(manageProductAdapter);
    }
}