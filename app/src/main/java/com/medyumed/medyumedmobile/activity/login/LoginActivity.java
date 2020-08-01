package com.medyumed.medyumedmobile.activity.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.medyumed.medyumedmobile.R;
import com.medyumed.medyumedmobile.activity.password.PasswordActivity;
import com.medyumed.medyumedmobile.data.Constants;
import com.medyumed.medyumedmobile.data.model.login.ControlState;
import com.medyumed.medyumedmobile.data.model.login.LoginFormState;
import com.medyumed.medyumedmobile.data.model.login.LoginResult;
import com.medyumed.medyumedmobile.data.viewmodel.login.LoginViewModel;
import com.medyumed.medyumedmobile.data.viewmodel.login.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences mySharedPreferences;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mySharedPreferences = getSharedPreferences(Constants.SharedPreferences.APP_PREFERENCES,
                Context.MODE_PRIVATE);

        if(mySharedPreferences != null)
            setPhoneNumberSharedPreference(null);

        loginViewModel =  new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText phoneNumberEditText = findViewById(R.id.phoneNumber);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getPhoneNumberError() != null) {
                    phoneNumberEditText.setError(getString(loginFormState.getPhoneNumberError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }

                loginViewModel.setControlState(ControlState.UNBLOCKED);

                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    showPasswordActivity(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);
            }
        });

        loginViewModel.getControlState().observe(this, new Observer<ControlState>() {
            @Override
            public void onChanged(ControlState controlState) {
                if(controlState == null){
                    return;
                }

                if(controlState == ControlState.LOCKED){
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    loginButton.setEnabled(false);
                }
                else {
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    loginButton.setEnabled(true);
                }
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
                loginViewModel.loginDataChanged(phoneNumberEditText.getText().toString());
            }
        };

        phoneNumberEditText.addTextChangedListener(afterTextChangedListener);
        phoneNumberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.setControlState(ControlState.LOCKED);
                    loginViewModel.login(phoneNumberEditText.getText().toString());
                }
                return false;
            }
        });
        phoneNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.setControlState(ControlState.LOCKED);
                loginViewModel.login(phoneNumberEditText.getText().toString());
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setPhoneNumberSharedPreference(String phoneNumber){
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(Constants.SharedPreferences.PHONE_NUMBER, phoneNumber);
        editor.apply();
    }

    private void showPasswordActivity(String phoneNumber) {
        setPhoneNumberSharedPreference(phoneNumber);

        Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
