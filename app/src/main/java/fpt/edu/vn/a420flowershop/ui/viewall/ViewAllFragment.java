package fpt.edu.vn.a420flowershop.ui.viewall;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import fpt.edu.vn.a420flowershop.Adapters.AllProductAdapter;
import fpt.edu.vn.a420flowershop.Adapters.NavCategoryAdapter;
import fpt.edu.vn.a420flowershop.Models.NavCategoryModel;
import fpt.edu.vn.a420flowershop.Models.ProductModel;
import fpt.edu.vn.a420flowershop.Models.UserModel;
import fpt.edu.vn.a420flowershop.R;


import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.vn.a420flowershop.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ViewAllFragment extends Fragment {

    FirebaseDatabase database;
    RecyclerView recyclerView;
    AllProductAdapter allProductAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nav_view_all, container, false);


        database = FirebaseDatabase.getInstance();
        recyclerView = root.findViewById(R.id.rec_all_product_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(allProductAdapter);

        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), ProductModel.class)
                .build();
        allProductAdapter = new AllProductAdapter(options);
        recyclerView.setAdapter(allProductAdapter);

        return root;
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
}
