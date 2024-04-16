package vn.edu.ptit.planta.ui.user;

public interface UserNavigator {

    public void handleError(Throwable throwable);

    public void handleLogout();

    public void handle();

}
