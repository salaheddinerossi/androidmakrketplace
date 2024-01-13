package com.example.marketplace.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.marketplace.ApiClient;
import com.example.marketplace.ApiInterface;
import com.example.marketplace.R;
import com.example.marketplace.dto.UserDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText nameEditText, addressEditText, emailEditText, passwordEditText, phoneEditText;
    private Button registerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        nameEditText = view.findViewById(R.id.nameEditText);
        addressEditText = view.findViewById(R.id.addressEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        registerButton = view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> performRegistration());

        return view;
    }

    private void performRegistration() {
        String name = nameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Long phone = Long.parseLong(phoneEditText.getText().toString());

        UserDto userDto = new UserDto(name, address, email, password, phone);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.registerUser(userDto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Registration Done", Toast.LENGTH_SHORT).show();
                    // Clear input fields
                    nameEditText.setText("");
                    addressEditText.setText("");
                    emailEditText.setText("");
                    passwordEditText.setText("");
                    phoneEditText.setText("");
                } else {
                    Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
