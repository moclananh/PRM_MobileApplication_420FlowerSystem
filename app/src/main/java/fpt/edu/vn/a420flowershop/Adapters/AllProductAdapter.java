package fpt.edu.vn.a420flowershop.Adapters;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import fpt.edu.vn.a420flowershop.Activities.PlacedOrderActivity;
import fpt.edu.vn.a420flowershop.Models.ProductModel;
import fpt.edu.vn.a420flowershop.MyApplication;
import fpt.edu.vn.a420flowershop.R;

public class AllProductAdapter extends FirebaseRecyclerAdapter<ProductModel, AllProductAdapter.allProductViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    int totalQuantity;

    int totalPrice;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_product_item, parent, false);
        return new allProductViewHolder(view);
    }

    class allProductViewHolder extends RecyclerView.ViewHolder {
        Button btn_view_detail;
        TextView product_name, product_price, product_cat, product_stock;
        ImageView product_img;

        public allProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.text_id);
            product_price = itemView.findViewById(R.id.price_id);
            product_cat = itemView.findViewById(R.id.cat_id);
            product_stock = itemView.findViewById(R.id.stock_id);
            product_img = itemView.findViewById(R.id.img_id);
            btn_view_detail = itemView.findViewById(R.id.btn_view_detail);
        }
    }

    void detail_click(final ProductModel model, @androidx.annotation.NonNull allProductViewHolder holder) {
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
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

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
      /* add_to_cart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sendNotification(v.getContext());
           }
       });*/
        String stock1 = stock.getText().toString();
        int quantityInStock = Integer.parseInt(stock1);
        incre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity >= quantityInStock) {
                    Toast.makeText(holder.product_name.getContext(), "Exceed products in stock", Toast.LENGTH_SHORT).show();
                } else {
                    totalQuantity += 1;
                    quantity.setText(String.valueOf(totalQuantity));
                    int productPrice = Integer.parseInt(model.getProduct_price());
                    totalPrice = productPrice * totalQuantity;
                }
            }
        });

        decre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity < 0) {
                    Toast.makeText(holder.product_name.getContext(), "Quantity must higher 0", Toast.LENGTH_SHORT).show();

                } else {
                    totalQuantity -= 1;
                    quantity.setText(String.valueOf(totalQuantity));
                    int productPrice = Integer.parseInt(model.getProduct_price());
                    totalPrice = productPrice * totalQuantity;
                }
            }
        });
        int productPrice = Integer.parseInt(model.getProduct_price());
        totalPrice = productPrice * totalQuantity;

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saveCurrentDate, saveCurrentTime;
                Calendar calForDate = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat();
                saveCurrentDate = currentDate.format(calForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat();
                saveCurrentTime = currentTime.format(calForDate.getTime());

                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("productName", model.getProduct_name());
                cartMap.put("productPrice", price.getText().toString());
                cartMap.put("currentDate", saveCurrentDate);
                cartMap.put("currentTime", saveCurrentTime);
                cartMap.put("totalQuantity", quantity.getText().toString());
                cartMap.put("totalPrice", totalPrice);
                sendNotification(v.getContext());

                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@androidx.annotation.NonNull Task<DocumentReference> task) {
                                Toast.makeText(holder.product_name.getContext(), "Add success", Toast.LENGTH_SHORT).show();
                            }
                        });
//               FirebaseDatabase.getInstance().getReference().child("carts")
//                       .push()
//                       .setValue(cartMap)
//                       .addOnSuccessListener(new OnSuccessListener<Void>() {
//                           @Override
//                           public void onSuccess(Void unused) {
//                               Toast.makeText(holder.product_name.getContext(), "Data inserted", Toast.LENGTH_SHORT).show();
//                           }
//                       })
//                       .addOnFailureListener(new OnFailureListener() {
//                           @Override
//                           public void onFailure(@androidx.annotation.NonNull Exception e) {
//                               Toast.makeText(holder.product_name.getContext(), "Insert error!! ", Toast.LENGTH_SHORT).show();
//                           }
//                       });
            }
        });

    }


    private void sendNotification(Context context) {

        Notification notification = new NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                .setContentTitle("420 Notification")
                .setContentText("The product has been successfully added to the cart")
                .setSmallIcon(R.drawable.logo)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(getNotificationId(), notification);
    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }


}
