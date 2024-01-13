package com.example.marketplace.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.marketplace.LoggedInActivity;
import com.example.marketplace.R;
import com.example.marketplace.dto.LoginDto;
import com.example.marketplace.response.JwtResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> performLogin(
                emailEditText.getText().toString(),
                passwordEditText.getText().toString()
        ));

        return view;
    }

    private void performLogin(String email, String password) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(email);
        loginDto.setPassword(password);

        Call<JwtResponse> call = apiService.loginUser(loginDto);
        call.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                Context context = getContext();
                if (context == null) return;

                if(response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    storeToken(context, token);

                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();

                    Intent loggedInIntent = new Intent(getActivity(), LoggedInActivity.class);
                    startActivity(loggedInIntent);
                    getActivity().finish();


                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                Context context = getContext();
                if (context == null) return;

                Toast.makeText(context, "Login Failed 2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("jwtToken", token);
        myEdit.apply();
    }
    public String getToken(Context context) {
        SharedPreferences sh = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sh.getString("jwtToken", "");
    }
}
