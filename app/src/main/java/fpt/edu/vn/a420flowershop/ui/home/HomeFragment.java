package fpt.edu.vn.a420flowershop.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;


import java.util.ArrayList;
import java.util.List;
import fpt.edu.vn.a420flowershop.Adapters.HomeAdapter;
import fpt.edu.vn.a420flowershop.Adapters.PopularAdapters;
import fpt.edu.vn.a420flowershop.Adapters.RecommendedAdapter;
import fpt.edu.vn.a420flowershop.Adapters.ViewAllAdapter;
import fpt.edu.vn.a420flowershop.Models.HomeCategory;
import fpt.edu.vn.a420flowershop.Models.PopularModel;
import fpt.edu.vn.a420flowershop.Models.RecommendedModel;
import fpt.edu.vn.a420flowershop.Models.ViewAllModel;
import fpt.edu.vn.a420flowershop.R;
import fpt.edu.vn.a420flowershop.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    ScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView popularRec, homeCatRec, recommendedRec;
    FirebaseFirestore db;
    //popular item
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;

    //////////////////////Search
    EditText search_box;
    private List<ViewAllModel> viewAllModelList;
    private RecyclerView recyclerViewSearch;
    private ViewAllAdapter viewAllAdapter;

    //Home category
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;
    //Recommended
    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        popularRec = root.findViewById(R.id.pop_rec);
        homeCatRec = root.findViewById(R.id.explore_rec);
        recommendedRec = root.findViewById(R.id.recommened_rec);
        scrollView = root.findViewById(R.id.scroll_view);
        progressBar = root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        //popular item
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularModelList = new ArrayList<>();
        popularAdapters =  new PopularAdapters(getActivity(), popularModelList);
        popularRec.setAdapter(popularAdapters);

        db.collection("popular products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapters.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }else {
                            Toast.makeText(getActivity(), "Error: "+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //home category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        homeAdapter =  new HomeAdapter(getActivity(), categoryList);
        homeCatRec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(getActivity(), "Error: "+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //recommended
        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapter =  new RecommendedAdapter(getActivity(), recommendedModelList);
        recommendedRec.setAdapter(recommendedAdapter);

        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(getActivity(), "Error: "+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        /////////////////////Search view
        recyclerViewSearch = root.findViewById(R.id.search_rec);
        search_box = root.findViewById(R.id.search_box);
        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(), viewAllModelList);

        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().isEmpty()){
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }else{
                    searchProduct(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().isEmpty()){

                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }else{
                    Log.d("myTag", "This is my message");
                    searchProduct(s.toString());
                }
            }
        });
        return root;
    }

    private void searchProduct(String type){
        if(!type.isEmpty()){
            db.collection("AllProducts").whereEqualTo("type", type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful() && task.getResult() != null){
                                viewAllModelList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for(DocumentSnapshot doc : task.getResult().getDocuments()){
                                    ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    Log.d("Haha", "Finish");
                                    viewAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }
}