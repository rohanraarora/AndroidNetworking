package in.codingninjas.envision.androidnetworking;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import java.util.Collections;
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

        adapter = new PostsAdapter(this, mPosts, new PostsClickListener() {
            @Override
            public void onPostClicked(View view, int position) {
                mPosts.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder fromVH, @NonNull RecyclerView.ViewHolder toVH) {
                int from = fromVH.getAdapterPosition();
                int to = toVH.getAdapterPosition();

                Post post = mPosts.get(from);
                mPosts.remove(from);
                mPosts.add(to,post);

                adapter.notifyItemMoved(from,to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mPosts.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

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

        Call<ArrayList<Post>> call = ApiClient.getCoursesService().getPosts();
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {

                ArrayList<Post> posts = response.body();
                mPosts.clear();
                mPosts.addAll(posts);

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {

            }
        });

    }
}
