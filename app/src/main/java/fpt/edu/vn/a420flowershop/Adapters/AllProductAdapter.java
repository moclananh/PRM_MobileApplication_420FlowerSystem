package fpt.edu.vn.a420flowershop.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

import fpt.edu.vn.a420flowershop.Models.ProductModel;
import fpt.edu.vn.a420flowershop.R;

public class AllProductAdapter extends FirebaseRecyclerAdapter<ProductModel, AllProductAdapter.allProductViewHolder> {

   /**
    * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
    * {@link FirebaseRecyclerOptions} for configuration options.
    *
    * @param options
    */
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
   }

   @androidx.annotation.NonNull
   @Override
   public allProductViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_product_item, parent,false);
       return new allProductViewHolder(view);
   }

   class allProductViewHolder extends RecyclerView.ViewHolder{
       RecyclerView recyclerView;
       TextView product_name, product_price, product_cat, product_stock;
       ImageView product_img;
       EditText search;
       public allProductViewHolder(@NonNull View itemView){
         super(itemView);
          product_name = itemView.findViewById(R.id.text_id);
          product_price = itemView.findViewById(R.id.price_id);
          product_cat = itemView.findViewById(R.id.cat_id);
          product_stock = itemView.findViewById(R.id.stock_id);
          product_img = itemView.findViewById(R.id.img_id);
          search = itemView.findViewById(R.id.search_product_user);
       }
   }
}
