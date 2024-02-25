package vn.edu.ptit.planta.ui.mygarden.notification;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Date;
import java.sql.Time;

import vn.edu.ptit.planta.BR;

public class AddNotificationViewModel extends ViewModel {

    private MutableLiveData<String> exercise;
    private MutableLiveData<String> time;
    private MutableLiveData<String> date;

    public MutableLiveData<String> getExercise() {
        if(exercise == null) {
            exercise = new MutableLiveData<>();
            exercise.setValue("Tưới nuớc");
        }
        return exercise;
    }

    public MutableLiveData<String> getTime() {
        if(time == null) {
            time = new MutableLiveData<>();
            time.setValue("08:00");
        }
        return time;
    }

    public MutableLiveData<String> getDate() {
        if(date == null) {
            date = new MutableLiveData<>();
        }
        return date;
    }
}
