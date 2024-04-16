package vn.edu.ptit.planta.ui.myplant.myplantdetail.care;

import vn.edu.ptit.planta.model.care.CareCalendar;
import vn.edu.ptit.planta.model.myschedule.MySchedule;

public interface CareNavigator {

    public void handleEditNotification(MySchedule schedule);
    public void handleAddNotification();
    public void handleNotificationDetail(CareCalendar careCalendar);

}
