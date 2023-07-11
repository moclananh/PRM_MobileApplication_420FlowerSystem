package fpt.edu.vn.a420flowershop.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import fpt.edu.vn.a420flowershop.Adapters.CategoryAdapter;
import fpt.edu.vn.a420flowershop.Models.ProductModel;
import fpt.edu.vn.a420flowershop.R;
public class CategoryFragment extends Fragment {

    FirebaseDatabase database;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    Spinner search_cate;
    String text;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        search_cate = root.findViewById(R.id.spinner);
        text = search_cate.getSelectedItem().toString();

        database = FirebaseDatabase.getInstance();
        recyclerView = root.findViewById(R.id.rec_cat_all_product_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(categoryAdapter);

        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), ProductModel.class)
                .build();
        categoryAdapter = new CategoryAdapter(options);
        recyclerView.setAdapter(categoryAdapter);
        search_click();
        return root;

    }


    public void search_click() {
        search_cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String txt = search_cate.getSelectedItem().toString();

                FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products")
                                .orderByChild("product_cat").equalTo(txt), ProductModel.class)
                        .build();

                categoryAdapter = new CategoryAdapter(options);
                categoryAdapter.startListening();
                recyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        categoryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        categoryAdapter.stopListening();
    }
}