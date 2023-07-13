package fpt.edu.vn.a420flowershop.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import fpt.edu.vn.a420flowershop.R;

import fpt.edu.vn.a420flowershop.ui.viewall.ViewAllFragment;



public class HomeFragment extends Fragment {
    TextView btnShop;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnShop = root.findViewById(R.id.btn_shop);

        click_Shop(); // Call the method to set up the click listener

        return root;
    }

    public void click_Shop() {
        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ViewAllFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}