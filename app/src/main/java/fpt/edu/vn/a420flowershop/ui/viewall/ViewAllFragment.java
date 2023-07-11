package fpt.edu.vn.a420flowershop.ui.viewall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import fpt.edu.vn.a420flowershop.Adapters.AllProductAdapter;
import fpt.edu.vn.a420flowershop.Models.ProductModel;
import fpt.edu.vn.a420flowershop.R;


public class ViewAllFragment extends Fragment {

    FirebaseDatabase database;
    RecyclerView recyclerView;
    AllProductAdapter allProductAdapter;
    EditText search_product;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nav_view_all, container, false);

        search_product = root.findViewById(R.id.search_product_user);
        database = FirebaseDatabase.getInstance();
        recyclerView = root.findViewById(R.id.rec_all_product_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(allProductAdapter);

        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), ProductModel.class)
                .build();
        allProductAdapter = new AllProductAdapter(options);
        recyclerView.setAdapter(allProductAdapter);
        search_click();
        return root;
    }

    public void search_click(){
        search_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = search_product.getText().toString();

                FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").orderByChild("product_name").startAt(txt).endAt(txt + "\uf8ff"), ProductModel.class) //startAt("%"+txt+"%").endAt(txt+"~")
                        .build();
                allProductAdapter = new AllProductAdapter(options);
                allProductAdapter.startListening();
                recyclerView.setAdapter(allProductAdapter);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        allProductAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        allProductAdapter.stopListening();
    }
   /* //menu search nhma dang bi loi menu setting ghi de
    @Override
    public final void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.search_product, menu);
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    //get product by keyword
    private void txtSearch( String txt){
        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("products").orderByChild("product_name").startAt(txt).endAt(txt+"~"), ProductModel.class)
                .build();
        allProductAdapter = new AllProductAdapter(options);
        allProductAdapter.startListening();
        recyclerView.setAdapter(allProductAdapter);
    }*/
}
