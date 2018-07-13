package in.codingninjas.envision.androidnetworking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;

    private static CoursesService service;

    static Retrofit getInstance(){
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = builder.build();
        }
        return retrofit;
    }

    static CoursesService getCoursesService(){
        if(service == null){
            service = getInstance().create(CoursesService.class);
        }
        return service;
    }
}
