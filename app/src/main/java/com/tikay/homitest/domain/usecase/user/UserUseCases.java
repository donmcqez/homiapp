package com.tikay.homitest.domain.usecase.user;

import android.content.Context;

import com.tikay.homitest.data.repository.UserRepositoryImpl;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;

public class UserUseCases {
    public FetchUsersUseCase fetchUsersUseCase;
    public GetAuthUserUseCase getAuthUserUseCase;
    public LoginUseCase loginUseCase;
    public SaveUserUseCase saveUserUseCase;
    public SignOutUseCase signOutUseCase;
    public UpgradeUserUseCase upgradeUserUseCase;

    public UserUseCases(Context context){
        fetchUsersUseCase = new FetchUsersUseCase(context);
        getAuthUserUseCase = new GetAuthUserUseCase(context);
        loginUseCase = new LoginUseCase(context);
        saveUserUseCase = new SaveUserUseCase(context);
        signOutUseCase = new SignOutUseCase(context);
        upgradeUserUseCase = new UpgradeUserUseCase(context);
    }

}
