package in.codingninjas.envision.androidnetworking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostViewHolder> {

    ArrayList<Post> posts;
    Context context;

    PostsAdapter(Context context,ArrayList<Post> posts){
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.row_post,viewGroup,false);
        return new PostViewHolder(rowLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {

        Post post = posts.get(i);
        postViewHolder.title.setText(post.title);
        postViewHolder.body.setText(post.body);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
