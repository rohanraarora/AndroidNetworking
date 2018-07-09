package in.codingninjas.envision.androidnetworking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    ArrayList<String> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,courses);
        listView.setAdapter(adapter);

        CoursesAsyncTask task= new CoursesAsyncTask(new CoursesDownloadListener() {
            @Override
            public void onDownload(ArrayList<String> titles) {
                courses.addAll(titles);
                adapter.notifyDataSetChanged();
            }
        });
        task.execute("https://codingninjas.in/api/v2/courses");
    }
}
