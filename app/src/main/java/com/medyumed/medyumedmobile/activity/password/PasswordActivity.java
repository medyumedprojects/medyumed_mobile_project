package com.medyumed.medyumedmobile.activity.password;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.medyumed.medyumedmobile.R;
import com.medyumed.medyumedmobile.activity.profile.ProfileActivity;
import com.medyumed.medyumedmobile.data.Constants;
import com.medyumed.medyumedmobile.data.viewmodel.password.PasswordViewModel;
import com.medyumed.medyumedmobile.data.viewmodel.password.PasswordViewModelFactory;

public class PasswordActivity extends AppCompatActivity {

    private PasswordViewModel viewModel;
    private String sendAgainText;
    private SharedPreferences mySharedPreferences;

    EditText num1;
    EditText num2;
    EditText num3;
    EditText num4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        mySharedPreferences = getSharedPreferences(Constants.SharedPreferences.APP_PREFERENCES,
                Context.MODE_PRIVATE);

        final String phoneNumber = mySharedPreferences
                .getString(Constants.SharedPreferences.PHONE_NUMBER, "");

        final TextView sendAgainTextView = findViewById(R.id.sendAgainTextView);
        sendAgainText = getString(R.string.label_password_timer_finish);

        viewModel =  new ViewModelProvider(this, new PasswordViewModelFactory(phoneNumber))
                .get(PasswordViewModel.class);

        viewModel.getTimerLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                sendAgainTextView.setText(String.format(sendAgainText, phoneNumber, s));
            }
        });

        viewModel.startTimer();

        viewModel.getLoginResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });


        //editTextInit();
    }

    private void editTextInit(){
        try {
            num1 = findViewById(R.id.numberPass1);
            num2 = findViewById(R.id.numberPass2);
            num2 = findViewById(R.id.numberPass3);
            num3 = findViewById(R.id.numberPass4);

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
                    String pass = getFullPassword();
                    if(pass == null)
                        return;

                    viewModel.login(pass);
                }
            };

            num1.addTextChangedListener(afterTextChangedListener);
            num2.addTextChangedListener(afterTextChangedListener);
            num3.addTextChangedListener(afterTextChangedListener);
            num4.addTextChangedListener(afterTextChangedListener);
        } catch (Exception ex){
            String sdf = ex.getMessage();
        }
    }

    public String getFullPassword(){
        String fullPass = num1.getText().toString() + num2.getText().toString()
                + num3.getText().toString() + num4.getText().toString();

        if(fullPass.length() != 4)
            return null;

        return fullPass;
    }
}
