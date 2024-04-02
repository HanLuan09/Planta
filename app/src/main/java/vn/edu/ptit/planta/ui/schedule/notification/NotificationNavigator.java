package vn.edu.ptit.planta.ui.schedule.notification;

public interface NotificationNavigator {

    public void handleError(Throwable throwable);
    public void handleCloseNotification();
    public void handleSummitNotification();
    public void handleEditSummitNotification();
    public void handleStartDatePickerDialog();
    public void handleEndDatePickerDialog();
    public void handleTimePickerDialog();
    public void handleFrequencyDialog();
    public void handleExerciseDialog();
    public void handleDeleteNotification();
    public void handleDialogScheduleSuccess(String message);
    public void handleDialogScheduleFail(String message);
}
