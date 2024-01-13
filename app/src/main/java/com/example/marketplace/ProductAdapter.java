package com.example.marketplace;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.marketplace.fragments.ProductDetailsFragment;
import com.example.marketplace.response.ProductResponse;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductResponse> productList;
    private boolean isDeleteEnabled;
    private ApiInterface apiService;

    public ProductAdapter(Context context, List<ProductResponse> productList, boolean isDeleteEnabled) {
        this.context = context;
        this.productList = productList;
        this.isDeleteEnabled = isDeleteEnabled;
        this.apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ProductResponse product = productList.get(position);
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewPrice.setText(String.format("Price: %s", product.getPrice()));

        if (product.getImage() != null && !product.getImage().isEmpty()) {
            byte[] decodedString = Base64.decode(product.getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imageViewProduct.setImageBitmap(decodedByte);
        }
        
        holder.buttonViewDetails.setVisibility(!isDeleteEnabled ? View.VISIBLE : View.GONE);
        holder.buttonViewDetails.setOnClickListener(v -> {
            ProductDetailsFragment detailsFragment = ProductDetailsFragment.newInstance(product.getId());
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Set visibility and click listener for the delete button
        holder.buttonDeleteProduct.setVisibility(isDeleteEnabled ? View.VISIBLE : View.GONE);
        holder.buttonDeleteProduct.setOnClickListener(v -> deleteProduct(product.getId(), position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void deleteProduct(Long productId, int position) {
        String token = "Bearer " + getToken(context);
        Call<Void> call = apiService.deleteProduct(productId, token);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Remove the product from the list and notify the adapter
                    productList.remove(position);
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private String getToken(Context context) {
        SharedPreferences sh = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sh.getString("jwtToken", "");
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduct;
        TextView textViewTitle, textViewPrice;
        Button buttonViewDetails, buttonDeleteProduct;

        ProductViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            buttonViewDetails = itemView.findViewById(R.id.buttonViewDetails);
            buttonDeleteProduct = itemView.findViewById(R.id.buttonDeleteProduct); // Initialize the delete button
        }
    }
}
