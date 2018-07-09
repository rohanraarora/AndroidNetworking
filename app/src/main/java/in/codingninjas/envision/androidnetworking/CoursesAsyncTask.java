package in.codingninjas.envision.androidnetworking;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class CoursesAsyncTask extends AsyncTask<String,Void,ArrayList<String>> {


    CoursesDownloadListener listener;
    CoursesAsyncTask(CoursesDownloadListener listener){
        this.listener = listener;
    }


    //MainThread
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //Background Thread
    @Override
    protected ArrayList<String> doInBackground(String... strings) {

        ArrayList<String> titles = new ArrayList<>();
        String urlString = strings[0];
        try {
            URL url = new URL(urlString);

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            String result = "";
            while (scanner.hasNext()){
                result = result + scanner.next();
            }

            Log.d("MainActivity","Result: " + result);



            JSONObject rootObject = new JSONObject(result);
            JSONObject data = rootObject.getJSONObject("data");
            JSONArray courses = data.getJSONArray("courses");
            for(int i = 0;i<courses.length();i++){
                JSONObject courseObject = courses.getJSONObject(i);
                String title = courseObject.getString("title");
                titles.add(title);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("CoursesAsyncTask",titles.size() + "");
        return titles;
    }

    //Main Thread
    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        listener.onDownload(strings);
    }
}
