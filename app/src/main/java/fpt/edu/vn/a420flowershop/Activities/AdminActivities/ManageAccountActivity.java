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
import fpt.edu.vn.a420flowershop.Models.UserModel;
import fpt.edu.vn.a420flowershop.R;

public class ManageAccountActivity extends AppCompatActivity {

    Button btn_back;
    RecyclerView recyclerView;
    ManageAccountAdapter manageAccountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        init_view();
        loadData();
        click_back();
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
        btn_back = findViewById(R.id.btnAccountBack);
    }

    private void click_back(){
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ManageAccountActivity.this, AdminPageActivity.class));
                Toast.makeText(ManageAccountActivity.this, "Back to admin page!!!", Toast.LENGTH_SHORT).show();
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

    // search
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