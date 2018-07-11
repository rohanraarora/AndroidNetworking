package in.codingninjas.envision.androidnetworking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    ArrayAdapter<String> adapter;
    ProgressBar progressBar;
    ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        progressBar = findViewById(R.id.progressBar);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(adapter);

        Movie movie = new Movie();
        movie.title = "ABC";
        movie.description = "desc";


        Gson gson = new Gson();
        String jsonString = gson.toJson(movie);
        Toast.makeText(this,jsonString,Toast.LENGTH_LONG).show();

        String movieString = "{'descripdsadtion':'abcd','title':'Movie Title'}";
        Movie parsedMovie = gson.fromJson(movieString,Movie.class);



    }

    public void fetchData(View view){
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
//        CoursesAsyncTask task= new CoursesAsyncTask(new CoursesDownloadListener() {
////            @Override
////            public void onDownload(ArrayList<String> titles) {
////                courses.clear();
////                courses.addAll(titles);
////                adapter.notifyDataSetChanged();
////                progressBar.setVisibility(View.GONE);
////                listView.setVisibility(View.VISIBLE);
////            }
////        });
////        task.execute("https://codingninjas.in/api/v2/courses");
////        Toast.makeText(this,"abcsa",Toast.LENGTH_LONG).show();


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://codingninjas.in/api/v2/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        CoursesService service = retrofit.create(CoursesService.class);

        Call<CoursesResponse> call = service.getCourses();

        call.enqueue(new Callback<CoursesResponse>() {
            @Override
            public void onResponse(Call<CoursesResponse> call, Response<CoursesResponse> response) {

                CoursesResponse coursesResponse = response.body();
                ArrayList<Course> courses = coursesResponse.getData().courses;
                titles.clear();
                for(int i = 0;i<courses.size();i++){
                    titles.add(courses.get(i).courseName);
                }
                progressBar.setVisibility(View.GONE);
                   listView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<CoursesResponse> call, Throwable t) {

            }
        });

    }
}
