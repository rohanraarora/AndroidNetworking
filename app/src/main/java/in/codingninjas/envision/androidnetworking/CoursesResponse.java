package in.codingninjas.envision.androidnetworking;

import android.util.Log;

public class CoursesResponse {

    String message;
    int status;
    private CourseData data;

    public CourseData getData() {
        return data;
    }

    public CoursesResponse(String message,int status,CourseData data){
        this.data = data;
        this.message = message;
        this.status = status;
        Log.d("CoursesResponse","Constructor called");
    }
}
