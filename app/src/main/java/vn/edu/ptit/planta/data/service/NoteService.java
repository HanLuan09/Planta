package vn.edu.ptit.planta.data.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.care.CareCalendarResponse;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.model.myschedule.MyScheduleRequest;
import vn.edu.ptit.planta.ui.note.Note;
import vn.edu.ptit.planta.ui.note.NoteResponse;
import vn.edu.ptit.planta.ui.note.SampleResponse;

public interface NoteService {
    @GET("notes")
    Call<ApiResponse<List<Note>>> getAllNotes();

    @GET("notes/{id}")
    Call<ApiResponse<Note>> getNoteById(@Path("id") int id);

    @POST("notes")
    Call<ApiResponse<NoteResponse>> createNote(@Body NoteResponse note);

    @PUT("notes/{id}")
    Call<ApiResponse<Note>> updateNote(@Path("id") int id, @Body Note note);

    @DELETE("notes/{id}")
    Call<ApiResponse<Void>> deleteNoteById(@Path("id") int id);

    @GET("notes/by-plant/{idMyPlant}")
    Call<ApiResponse<List<Note>>> getAllNotesByMyPlantId(@Path("idMyPlant") int idMyPlant);

    @GET("notes/sample/{idMyPlant}")
    Call<ApiResponse<SampleResponse>> getSampleByMyPlantId(@Path("idMyPlant") int idMyPlant);

}

