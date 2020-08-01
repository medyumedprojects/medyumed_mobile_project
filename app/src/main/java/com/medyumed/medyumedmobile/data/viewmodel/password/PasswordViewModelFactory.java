package com.medyumed.medyumedmobile.data.viewmodel.password;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PasswordViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    String phoneNumber;

    public PasswordViewModelFactory(String number){
        phoneNumber = number;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PasswordViewModel.class)) {
            return (T) new PasswordViewModel(phoneNumber);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
