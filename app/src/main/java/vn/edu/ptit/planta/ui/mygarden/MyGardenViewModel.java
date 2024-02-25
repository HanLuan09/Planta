package vn.edu.ptit.planta.ui.mygarden;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyGardenViewModel extends ViewModel {

    private MutableLiveData<String> namePlant;
    private MutableLiveData<Boolean> busy;
    private MutableLiveData<Boolean> validateGarden;
    private MyGardenNavigator myGardenNavigator;

    public void setMyGardenNavigator(MyGardenNavigator myGardenNavigator) {
        this.myGardenNavigator = myGardenNavigator;
    }

    public MutableLiveData<String> getNamePlant() {
        if (namePlant == null) namePlant = new MutableLiveData<>();
        return namePlant;
    }

    public MutableLiveData<Boolean> getValidateGarden () {
        if(validateGarden == null) {
            validateGarden = new MutableLiveData<>();
            validateGarden.setValue(false);
        }
        return validateGarden;
    }

    public MutableLiveData<Boolean> getBusy() {

        if (busy == null) {
            busy = new MutableLiveData<>();
            busy.setValue(false);
        }

        return busy;
    }

    public void onAddNotificationClick(){
        if(myGardenNavigator != null) myGardenNavigator.handleAddNotification();
    }

    public void onBlackMyPlantClick() {
        if(myGardenNavigator != null) myGardenNavigator.handleBlackMyPlant();
    }

    public void handleSubmit() {
        getBusy().setValue(true); //View.VISIBLE
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                busy.setValue(false); // == View.GONE

            }
        }, 3000);
    }
}
