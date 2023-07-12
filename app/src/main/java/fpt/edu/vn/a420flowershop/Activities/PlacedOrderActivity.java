package fpt.edu.vn.a420flowershop.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fpt.edu.vn.a420flowershop.Models.MyCartModel;
import fpt.edu.vn.a420flowershop.Models.UserModel;
import fpt.edu.vn.a420flowershop.R;
import fpt.edu.vn.a420flowershop.ui.myCart.MyCartFragment;

public class PlacedOrderActivity extends AppCompatActivity {
    CircleImageView profileImg;
    EditText name, email, number, address;
    Button btn_back, btn_confirm;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        number = findViewById(R.id.profile_number);
        address = findViewById(R.id.profile_address);
        btn_back = findViewById(R.id.btn_back);
        btn_confirm = findViewById(R.id.btn_confirm);
        email.setEnabled(false);
        name.setEnabled(false);
        number.setEnabled(false);
        address.setEnabled(false);
        click_back();
        click_confirm();
        //hien thi profile
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserModel userModel = snapshot.getValue(UserModel.class);

                    name.setText(userModel.getUsername());
                    email.setText(userModel.getEmail());
                    number.setText(userModel.getPhone());
                    address.setText(userModel.getAddress());
                } else {
                    // Handle the case when the data does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
            }
        });
    }
    public void click_back(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlacedOrderActivity.this, MainActivity.class);
                Toast.makeText(PlacedOrderActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
    public void click_confirm(){
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MyCartModel> list = (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemList");
                if(list != null && list.size() > 0){
                    for (MyCartModel model : list){
                        final HashMap<String,Object> cartMap = new HashMap<>();
                        cartMap.put("productName",model.getProductName());
                        cartMap.put("productPrice",model.getProductPrice());
                        cartMap.put("currentDate",model.getCurrentDate());
                        cartMap.put("currentTime",model.getCurrentTime());
                        cartMap.put("totalQuantity",model.getTotalQuantity());
                        cartMap.put("totalPrice",model.getTotalPrice());
                        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        Toast.makeText(PlacedOrderActivity.this, "Your Order has been placed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        firestore.collection("AddToCart")
                                .document(auth.getCurrentUser().getUid())
                                .collection("CurrentUser")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                document.getReference().delete();
                                            }
                                        }
                                    }
                                });
                    }
                }
                Intent intent = new Intent(PlacedOrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}