package com.example.isibpmn.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isibpmn.MainActivity;
import com.example.isibpmn.R;
import com.example.isibpmn.cookies.ReceivedCookiesInterceptor;
import com.example.isibpmn.service.RetrofitServices;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginViewModel loginViewModel;
    private RetrofitServices loginApi;
    private Button loginButton;
    private  EditText passwordEditText;
    private EditText usernameEditText;
    private ProgressBar loadingProgressBar;
    private Context instance;
    private Retrofit retrofitLogin;
    private Intent I;
    private AlertDialog.Builder alert;
    private PrimeRun p;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);



        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });


    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {

        super.onStart();


        instance = this;
        p = new PrimeRun(143);
        loginButton.setOnClickListener(this);


    }
    private void init() {

        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();


        builder.addInterceptor(new ReceivedCookiesInterceptor(this));
        client=builder.build();

        retrofitLogin = new Retrofit.Builder()
                .baseUrl("http://digitalisi.tn:8080/bonita/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        loginApi = retrofitLogin.create(RetrofitServices.class);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login:

                if (!TextUtils.isEmpty(usernameEditText.getText()) && !TextUtils.isEmpty(passwordEditText.getText())) {

                    Log.i("login", "onClick: username：" + usernameEditText.getText().toString() + "password：" + passwordEditText.getText().toString());

                    new Thread(p).start();



                }
                else {
                    Toast.makeText(this, "champs vide", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }
    class PrimeRun implements Runnable {
        long minPrime;
        PrimeRun(long minPrime) {
            this.minPrime = minPrime;
        }
        @Override
        public void run() {
            // TODO add code to refresh in background
            try {
                init();
                Map<String, String> param = new HashMap<>();
                param.put("username", usernameEditText.getText().toString());
                param.put("password",passwordEditText.getText().toString());
                loginApi.isValidUser(param).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.i("success", "id  success");
                            Toast.makeText(instance, "id sucess", Toast.LENGTH_SHORT).show();
                            Intent inte = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(inte);

                        } else {
                            Log.i("failed", "login failed");
                            Toast.makeText(instance, "login failed" + response.code(), Toast.LENGTH_SHORT).show();

                        }
                    }


                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("fail", "on fail");
                        Toast.makeText(instance, "fail connect" + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }


                });

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    };
}