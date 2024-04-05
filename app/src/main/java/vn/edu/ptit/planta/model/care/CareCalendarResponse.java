package vn.edu.ptit.planta.model.care;

import androidx.annotation.NonNull;

import com.google.gson.annotations.JsonAdapter;

import java.sql.Date;
import java.util.List;

import vn.edu.ptit.planta.config.SqlDateTypeAdapter;

public class CareCalendarResponse implements Comparable<CareCalendarResponse> {
    @JsonAdapter(SqlDateTypeAdapter.class)
    private Date dateCare;
    private List<CareCalendar> careCalendars;

    public CareCalendarResponse() {
    }

    public CareCalendarResponse(Date date, List<CareCalendar> careCalendars) {
        this.dateCare = date;
        this.careCalendars = careCalendars;
    }

    public Date getDateCare() {
        return dateCare;
    }

    public List<CareCalendar> getCareCalendars() {
        return careCalendars;
    }

    public void setDateCare(Date dateCare) {
        this.dateCare = dateCare;
    }

    public void setCareCalendars(List<CareCalendar> careCalendars) {
        this.careCalendars = careCalendars;
    }

    @Override
    public int compareTo(@NonNull CareCalendarResponse o) {
        return this.dateCare.compareTo(o.dateCare);
    }
}
