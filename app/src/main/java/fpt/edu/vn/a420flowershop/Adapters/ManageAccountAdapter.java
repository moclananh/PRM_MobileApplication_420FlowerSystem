package fpt.edu.vn.a420flowershop.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import fpt.edu.vn.a420flowershop.Models.UserModel;
import fpt.edu.vn.a420flowershop.R;

public class ManageAccountAdapter extends FirebaseRecyclerAdapter<UserModel, ManageAccountAdapter.allAccountViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    FirebaseAuth auth;
    public ManageAccountAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ManageAccountAdapter.allAccountViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull UserModel model) {
        if (!model.getEmail().equals("admin@admin.com")){
            holder.username.setText(model.getUsername());
            holder.email.setText(model.getEmail());
            holder.address.setText(model.getAddress());
            holder.phone.setText(model.getPhone());
            Glide.with(holder.account_img.getContext())
                    .load(model.getProfileImg())
                    .placeholder(R.drawable.ic_menu_camera)
                    .error(R.drawable.ic_menu_camera)
                    .into(holder.account_img);

            holder.btn_inactive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.username.getContext());
                    builder.setTitle("Are you sure ?");
                    builder.setMessage("Deleted account can not to Undo.");

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(getRef(position).getKey()).removeValue();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(holder.username.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            });
        }
    }

    @NonNull
    @Override
    public allAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_account_item, parent,false);
        auth = FirebaseAuth.getInstance();
        return new ManageAccountAdapter.allAccountViewHolder(view);
    }

    class allAccountViewHolder extends RecyclerView.ViewHolder{
        TextView username, email, address, phone;
        ImageView account_img;
        Button btn_inactive;
        public allAccountViewHolder(@org.checkerframework.checker.nullness.qual.NonNull View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.manage_username);
            email = itemView.findViewById(R.id.manage_email);
            address = itemView.findViewById(R.id.manage_address);
            phone = itemView.findViewById(R.id.manage_phone);
            account_img = itemView.findViewById(R.id.manage_account_img_id);
            btn_inactive = itemView.findViewById(R.id.btn_inactive_account);
        }
    }
}
