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
import android.util.Log;
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
    private EditText phoneNumberEditText;
    private static final String TAG = "Login Activity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumberEditText = findViewById(R.id.phoneNumber);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        try {
            mySharedPreferences = getSharedPreferences(Constants.SharedPreferences.APP_PREFERENCES,
                    Context.MODE_PRIVATE);

            if (mySharedPreferences != null)
                setPhoneNumberSharedPreference(null);
        } catch (Exception ex){
            Log.e(TAG, "SharedPreferences exceptions", ex);
        }

        loginViewModel =  new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

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

        phoneNumberEditText.addTextChangedListener(new CustomEditTextView(phoneNumberEditText));
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

    private class CustomEditTextView implements TextWatcher{

        private EditText editText;

        public CustomEditTextView(EditText e){
            editText = e;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            loginViewModel.loginDataChanged(editText.getText().toString());
        }
    }

    private void hideKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch(Exception ex){
            Log.e(TAG, "hideKeyboard exceptions", ex);
        }
    }

    private void setPhoneNumberSharedPreference(String phoneNumber){
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(Constants.SharedPreferences.PHONE_NUMBER, phoneNumber);
        editor.apply();
    }

    private void showPasswordActivity(String phoneNumber) {
        try {
            setPhoneNumberSharedPreference(phoneNumber);

            Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
            startActivity(intent);
        } catch (Exception ex){
            Log.e(TAG, "ShowPasswordActivity exceptions", ex);
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
