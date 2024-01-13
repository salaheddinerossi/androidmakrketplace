package com.example.marketplace.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.marketplace.ApiClient;
import com.example.marketplace.ApiInterface;
import com.example.marketplace.ProductAdapter;
import com.example.marketplace.R;
import com.example.marketplace.response.ProductResponse;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<ProductResponse> productList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new ProductAdapter(getContext(), productList,false);
        recyclerView.setAdapter(productAdapter);

        loadProducts();

        return view;
    }

    private void loadProducts() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductResponse>> call = apiService.getAllProducts();

        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear(); // Clear existing items
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
