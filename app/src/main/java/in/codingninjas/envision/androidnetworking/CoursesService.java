package in.codingninjas.envision.androidnetworking;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoursesService {

    @GET("courses")
    Call<CoursesResponse> getCourses();


}
