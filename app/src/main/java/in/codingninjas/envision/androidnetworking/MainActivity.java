package in.codingninjas.envision.androidnetworking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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


    RecyclerView recyclerView;
    PostsAdapter adapter;
    ProgressBar progressBar;
    ArrayList<Post> mPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

//        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titles);
//        listView.setAdapter(adapter);

        adapter = new PostsAdapter(this,mPosts);
        recyclerView.setAdapter(adapter);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

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
        recyclerView.setVisibility(View.GONE);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        CoursesService service = retrofit.create(CoursesService.class);
        Call<ArrayList<Post>> call = service.getPosts();
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {

                ArrayList<Post> posts = response.body();
                mPosts.clear();
                mPosts.addAll(posts);

                mPosts = posts;
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {

            }
        });

    }
}
