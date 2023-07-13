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
import fpt.edu.vn.a420flowershop.Adapters.ManageAccountAdapter;
import fpt.edu.vn.a420flowershop.Adapters.ManageProductAdapter;
import fpt.edu.vn.a420flowershop.Models.ProductModel;
import fpt.edu.vn.a420flowershop.Models.UserModel;
import fpt.edu.vn.a420flowershop.R;

public class AdminManageAccountActivity extends AppCompatActivity {

    Button btn_logout, btn_add_new, btn_manage_pro;
    RecyclerView recyclerView;
    ManageAccountAdapter manageAccountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_account);
        init_view();
        loadData();
        click_add();
        click_logout();
        click_Manage_Product();
    }

    private void loadData(){
        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), UserModel.class)
                .build();
        manageAccountAdapter = new ManageAccountAdapter(options);
        recyclerView.setAdapter(manageAccountAdapter);
    }

    private void init_view() {
        // recyclerView
        recyclerView = findViewById(R.id.recAccount_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //button
        btn_logout = findViewById(R.id.btnLogout);
        btn_add_new = findViewById(R.id.btnAddNew);
        btn_manage_pro = findViewById(R.id.btnManageProduct);
    }

    private void click_add(){
        btn_add_new.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(AdminManageAccountActivity.this, AddNewProductActivity.class));
            }
        });
    }

    private void click_logout(){
        btn_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(AdminManageAccountActivity.this, LoginActivity.class));
                Toast.makeText(AdminManageAccountActivity.this, "Logout!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void click_Manage_Product(){
        btn_manage_pro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(AdminManageAccountActivity.this, AdminManageActivity.class));
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        manageAccountAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        manageAccountAdapter.stopListening();
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

    private void txtSearch( String txt){
        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").startAt(txt).endAt(txt+"~"), UserModel.class)
                .build();
        manageAccountAdapter = new ManageAccountAdapter(options);
        manageAccountAdapter.startListening();
        recyclerView.setAdapter(manageAccountAdapter);
    }
}