package vn.edu.ptit.planta.model.plant;

import java.util.List;

import vn.edu.ptit.planta.model.HarvestSeason;
import vn.edu.ptit.planta.model.Schedule;
import vn.edu.ptit.planta.model.Weather;

public class PlantDetail {
    private int id;
    private String name;
    private String typePlant;
    private String mainImage;
    private String secondaryImage;
    private String typeSoil;
    private String keySearch;
    private String matureSize;
    private String matureTime;
    private String description;
    private List<Schedule> schedules;
    private List<Weather> weathers;
    private List<HarvestSeason> harvestSeasons;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTypePlant() {
        return typePlant;
    }

    public String getMainImage() {
        return mainImage;
    }

    public String getSecondaryImage() {
        return secondaryImage;
    }

    public String getTypeSoil() {
        return typeSoil;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public String getMatureSize() {
        return matureSize;
    }

    public String getMatureTime() {
        return matureTime;
    }

    public String getDescription() {
        return description;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public List<HarvestSeason> getHarvestSeasons() {
        return harvestSeasons;
    }
}
