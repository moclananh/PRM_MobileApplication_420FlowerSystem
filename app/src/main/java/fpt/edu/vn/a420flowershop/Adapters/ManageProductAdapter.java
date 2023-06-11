package fpt.edu.vn.a420flowershop.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

import fpt.edu.vn.a420flowershop.Models.ProductModel;
import fpt.edu.vn.a420flowershop.R;

public class ManageProductAdapter extends FirebaseRecyclerAdapter<ProductModel, ManageProductAdapter.allProductViewHolder> {

   /**
    * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
    * {@link FirebaseRecyclerOptions} for configuration options.
    *
    * @param options
    */
   public ManageProductAdapter(@androidx.annotation.NonNull FirebaseRecyclerOptions<ProductModel> options) {
      super(options);
   }

   @Override
   protected void onBindViewHolder(@androidx.annotation.NonNull allProductViewHolder holder, int position, @androidx.annotation.NonNull ProductModel model) {
       holder.product_name.setText(model.getProduct_name());
       holder.product_price.setText(model.getProduct_price());
       holder.product_cat.setText(model.getProduct_cat());
       holder.product_stock.setText(model.getProduct_stock());
       Glide.with(holder.product_img.getContext())
               .load(model.getProduct_img())
               .placeholder(R.drawable.ic_menu_camera)
               .error(R.drawable.ic_menu_camera)
               .into(holder.product_img);

       //update
       holder.btn_edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final DialogPlus dialogPlus = DialogPlus.newDialog(holder.product_img.getContext())
                       .setContentHolder(new ViewHolder(R.layout.update_product))
                       .setExpanded(true, 1800)
                       .create();
               dialogPlus.show();
               // read old data
               View view = dialogPlus.getHolderView();
               EditText name = view.findViewById(R.id.pro_name_id_update);
               EditText category = view.findViewById(R.id.pro_cat_id_update);
               EditText img = view.findViewById(R.id.pro_img_id_update);
               EditText stock = view.findViewById(R.id.pro_stock_id_update);
               EditText price = view.findViewById(R.id.pro_price_id_update);
               EditText des = view.findViewById(R.id.pro_des_id_update);
               EditText status = view.findViewById(R.id.pro_status_id_update);

               Button btn_update = view.findViewById(R.id.btnUpdate_confirm);

               name.setText(model.getProduct_name());
               category.setText(model.getProduct_cat());
               img.setText(model.getProduct_img());
               stock.setText(model.getProduct_stock());
               price.setText(model.getProduct_price());
               des.setText(model.getProduct_des());
               status.setText(model.getProduct_status());

               btn_update.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Map<String, Object> map = new HashMap<>();
                       map.put("product_name", name.getText().toString());
                       map.put("product_cat", category.getText().toString());
                       map.put("product_stock", stock.getText().toString());
                       map.put("product_price", price.getText().toString());
                       map.put("product_des", des.getText().toString());
                       map.put("product_status", status.getText().toString());

                       FirebaseDatabase.getInstance().getReference().child("products")
                               .child(getRef(position).getKey()).updateChildren(map)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       Toast.makeText(holder.product_name.getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
                                       dialogPlus.dismiss(); // after update close table update detail
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure( Exception e) {
                                       Toast.makeText(holder.product_name.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                                       dialogPlus.dismiss();
                                   }
                               });
                   }
               });
           }
       });
   }

   @androidx.annotation.NonNull
   @Override
   public allProductViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_product_item, parent,false);
       return new allProductViewHolder(view);
   }

   class allProductViewHolder extends RecyclerView.ViewHolder{
       TextView product_name, product_price, product_cat, product_stock;
       ImageView product_img;
       Button btn_edit, btn_delete;
       public allProductViewHolder(@NonNull View itemView){
         super(itemView);
          product_name = itemView.findViewById(R.id.manage_text_id);
          product_price = itemView.findViewById(R.id.manage_price_id);
          product_cat = itemView.findViewById(R.id.manage_cat_id);
          product_stock = itemView.findViewById(R.id.manage_stock_id);
          product_img = itemView.findViewById(R.id.manage_img_id);
          btn_edit = itemView.findViewById(R.id.btn_update);
          btn_delete = itemView.findViewById(R.id.btn_delete);
       }
   }
}
