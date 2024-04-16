package vn.edu.ptit.planta.ui.user;

import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    private UserNavigator userNavigator;

    public void setUserNavigator(UserNavigator userNavigator) {
        this.userNavigator = userNavigator;
    }

    public void onLogoutClick(){
        userNavigator.handleLogout();
    }

    public void onClick(){
        userNavigator.handle();
    }
}
