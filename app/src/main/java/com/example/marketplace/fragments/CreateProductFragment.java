package com.example.marketplace.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.marketplace.ApiClient;
import com.example.marketplace.ApiInterface;
import com.example.marketplace.R;
import com.example.marketplace.dto.ProductDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProductFragment extends Fragment {

    // UI components
    private EditText titleEditText, descriptionEditText, priceEditText;
    private ImageView productImageView;
    private Button selectImageButton, submitProductButton;
    private String encodedImage;

    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_product, container, false);

        titleEditText = view.findViewById(R.id.titleEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        priceEditText = view.findViewById(R.id.priceEditText);
        productImageView = view.findViewById(R.id.productImageView);

        productImageView = view.findViewById(R.id.productImageView);
        selectImageButton = view.findViewById(R.id.selectImageButton);
        submitProductButton = view.findViewById(R.id.submitProductButton);

        selectImageButton.setOnClickListener(v -> selectImage());
        submitProductButton.setOnClickListener(v -> performProductCreation());



        return view;
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    productImageView.setImageBitmap(bitmap);
                    encodeBitmapImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
    }



    private void performProductCreation() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        Double price = Double.parseDouble(priceEditText.getText().toString());

        ProductDto productDto = new ProductDto();
        productDto.setTitle(title);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setImage(encodedImage); // Base64 encoded image

        String token = "Bearer " + getToken(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.createProduct(token, productDto);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Product Created", Toast.LENGTH_SHORT).show();
                    // Clear input fields
                    titleEditText.setText("");
                    descriptionEditText.setText("");
                    priceEditText.setText("");
                    productImageView.setImageBitmap(null);
                    encodedImage = null; // Clear the encoded image
                } else {
                    Toast.makeText(getContext(), "Product Creation Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getToken(Context context) {
        SharedPreferences sh = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sh.getString("jwtToken", "");
    }

}
