package com.example.marketplace.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.marketplace.ApiClient;
import com.example.marketplace.ApiInterface;
import com.example.marketplace.R;
import com.example.marketplace.response.ProductUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsFragment extends Fragment {

    private Long productId;
    private ImageView imageViewProduct;
    private TextView textViewTitle, textViewDescription, textViewPrice, textViewSellerInfo;

    public static ProductDetailsFragment newInstance(Long productId) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putLong("productId", productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getLong("productId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        imageViewProduct = view.findViewById(R.id.imageViewProduct);
        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewDescription = view.findViewById(R.id.textViewDescription);
        textViewPrice = view.findViewById(R.id.textViewPrice);
        textViewSellerInfo = view.findViewById(R.id.textViewSellerInfo);

        loadProductDetails();

        return view;
    }

    private void loadProductDetails() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductUser> call = apiService.getProduct(productId);

        call.enqueue(new Callback<ProductUser>() {
            @Override
            public void onResponse(Call<ProductUser> call, Response<ProductUser> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductUser product = response.body();

                    textViewTitle.setText(product.getTitle());
                    textViewDescription.setText(product.getDescription());
                    textViewPrice.setText(String.format("Price: %s", product.getPrice()));
                    textViewSellerInfo.setText(String.format("Seller: %s\nEmail: %s\nPhone: %s", product.getName(), product.getEmail(), product.getPhone()));

                    if (product.getImage() != null && !product.getImage().isEmpty()) {
                        byte[] decodedString = Base64.decode(product.getImage(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageViewProduct.setImageBitmap(decodedByte);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductUser> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
