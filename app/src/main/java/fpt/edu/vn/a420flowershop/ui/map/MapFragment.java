package fpt.edu.vn.a420flowershop.ui.map;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fpt.edu.vn.a420flowershop.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {


    private EditText etFromLocation;
    private Button btnGetDirection;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        etFromLocation = root.findViewById(R.id.edtFromLocation);

        btnGetDirection = root.findViewById(R.id.btnGetDirection);

        btnGetDirection.setOnClickListener(view ->{
            String userLocation = etFromLocation.getText().toString();
            String userDestination = "Vincom Xuân Khánh";

            if(userLocation.equals("")){
                Toast.makeText(getContext(), "Please Enter location", Toast.LENGTH_SHORT).show();
            }else{
                getDirection(userLocation, userDestination);
            }
        });



        return root;

    }

    private void getDirection(String from, String to ){
        try{
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" + from +"/" +to);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch(ActivityNotFoundException exception){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=vi&gl=US");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}