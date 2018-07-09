package in.codingninjas.envision.androidnetworking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    ArrayAdapter<String> adapter;
    ProgressBar progressBar;
    ArrayList<String> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        progressBar = findViewById(R.id.progressBar);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,courses);
        listView.setAdapter(adapter);


    }

    public void fetchData(View view){
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        CoursesAsyncTask task= new CoursesAsyncTask(new CoursesDownloadListener() {
            @Override
            public void onDownload(ArrayList<String> titles) {
                courses.clear();
                courses.addAll(titles);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        });
        task.execute("https://codingninjas.in/api/v2/courses");
        Toast.makeText(this,"abcsa",Toast.LENGTH_LONG).show();
    }
}
