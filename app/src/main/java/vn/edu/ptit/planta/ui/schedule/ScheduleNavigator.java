package vn.edu.ptit.planta.ui.schedule;

import vn.edu.ptit.planta.model.myschedule.MySchedule;

public interface ScheduleNavigator {

    public void handleAddNotification();

    public void handleBlackNotification();

    public void handleEditNotification(MySchedule schedule);

}
