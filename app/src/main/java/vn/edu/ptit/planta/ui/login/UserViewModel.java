package vn.edu.ptit.planta.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.edu.ptit.planta.model.UserResponse;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    public void setUserResponse(UserResponse user){
        userResponse.setValue(user);
    }
    public LiveData<UserResponse> getUserResponse(){
        return userResponse;
    }
}
