package in.codingninjas.envision.androidnetworking;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoursesService {

    @GET("posts")
    Call<ArrayList<Post>> getPosts();


}
