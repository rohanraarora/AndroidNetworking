package in.codingninjas.envision.androidnetworking;

import com.google.gson.annotations.SerializedName;

public class Course {

    int id;
    String title;
    @SerializedName("name")
    String courseName;
    String overview;

}
