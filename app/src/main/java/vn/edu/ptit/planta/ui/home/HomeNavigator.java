package vn.edu.ptit.planta.ui.home;

public interface HomeNavigator {
    public void handleError(Throwable throwable);
    public void handleMyPlantFragment();
    public void handleCalendarMyPlant();
    public void handleAddMyGarden();
}
