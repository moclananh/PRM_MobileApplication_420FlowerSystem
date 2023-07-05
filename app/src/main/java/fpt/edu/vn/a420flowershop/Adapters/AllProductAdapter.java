package fpt.edu.vn.a420flowershop.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.checkerframework.checker.nullness.qual.NonNull;

import fpt.edu.vn.a420flowershop.Activities.DetailedActivity;
import fpt.edu.vn.a420flowershop.Activities.ViewAllActivity;
import fpt.edu.vn.a420flowershop.Models.ProductModel;
import fpt.edu.vn.a420flowershop.R;

public class AllProductAdapter extends FirebaseRecyclerAdapter<ProductModel, AllProductAdapter.allProductViewHolder> {

   /**
    * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
    * {@link FirebaseRecyclerOptions} for configuration options.
    *
    * @param options
    */
int totalQuantity;
   public AllProductAdapter(@androidx.annotation.NonNull FirebaseRecyclerOptions<ProductModel> options) {
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

       holder.btn_view_detail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               detail_click(model, holder);
           }
       });
   }

   @androidx.annotation.NonNull
   @Override
   public allProductViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_product_item, parent,false);
       return new allProductViewHolder(view);
   }

   class allProductViewHolder extends RecyclerView.ViewHolder{
       Button btn_view_detail;
       TextView product_name, product_price, product_cat, product_stock;
       ImageView product_img;
       public allProductViewHolder(@NonNull View itemView){
         super(itemView);
          product_name = itemView.findViewById(R.id.text_id);
          product_price = itemView.findViewById(R.id.price_id);
          product_cat = itemView.findViewById(R.id.cat_id);
          product_stock = itemView.findViewById(R.id.stock_id);
          product_img = itemView.findViewById(R.id.img_id);
          btn_view_detail = itemView.findViewById(R.id.btn_view_detail);
       }
   }

   void detail_click(final ProductModel model , @androidx.annotation.NonNull allProductViewHolder holder){
       final DialogPlus dialogPlus = DialogPlus.newDialog(holder.product_img.getContext())
               .setContentHolder(new ViewHolder(R.layout.detail_product))
               .setExpanded(true, 1800)
               .create();
       dialogPlus.show();
       // read old data
       View view = dialogPlus.getHolderView();
       TextView name = view.findViewById(R.id.pro_name_id_detail);
       TextView category = view.findViewById(R.id.pro_cat_id_detail);
       ImageView img = view.findViewById(R.id.pro_img_id_detail);
       TextView stock = view.findViewById(R.id.pro_stock_id_detail);
       TextView price = view.findViewById(R.id.pro_price_id_detail);
       TextView des = view.findViewById(R.id.pro_des_id_detail);


       Button add_to_cart = view.findViewById(R.id.add_to_cart);
       ImageView incre = view.findViewById(R.id.add_item);
       ImageView decre = view.findViewById(R.id.remove_item);
       TextView quantity = view.findViewById(R.id.quantity);

       totalQuantity = 1;
       name.setText(model.getProduct_name());
       category.setText(model.getProduct_cat());

       Glide.with(img.getContext())
               .load(model.getProduct_img())
               .placeholder(R.drawable.ic_menu_camera)
               .error(R.drawable.ic_menu_camera)
               .into(img);

       stock.setText(model.getProduct_stock());
       price.setText(model.getProduct_price());
       des.setText(model.getProduct_des());
       add_to_cart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(holder.product_name.getContext(), "OK", Toast.LENGTH_SHORT).show();
           }
       });
       String stock1 = stock.getText().toString();
       int quantityInStock =  Integer.parseInt(stock1);
       incre.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              if (totalQuantity >= quantityInStock){
                  Toast.makeText(holder.product_name.getContext(), "Deo co du hang", Toast.LENGTH_SHORT).show();
              }
              else {
                  totalQuantity+=1;
                  Toast.makeText(holder.product_name.getContext(), "Add Successs"+totalQuantity, Toast.LENGTH_SHORT).show();
                  quantity.setText(String.valueOf(totalQuantity));
              }
           }
       });

       decre.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (totalQuantity >= 0){
                   Toast.makeText(holder.product_name.getContext(), "DEO DC SUB NUA", Toast.LENGTH_SHORT).show();

               }
               else {
                   totalQuantity-=1;
                   Toast.makeText(holder.product_name.getContext(), "Sub Successs"+totalQuantity, Toast.LENGTH_SHORT).show();
                   quantity.setText(String.valueOf(totalQuantity));
               }
           }
       });

       add_to_cart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });
   }
}
