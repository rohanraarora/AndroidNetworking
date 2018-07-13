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
    PostsClickListener listener;

    PostsAdapter(Context context,ArrayList<Post> posts,PostsClickListener postsClickListener){
        this.posts = posts;
        this.context = context;
        this.listener = postsClickListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.row_post,viewGroup,false);
        return new PostViewHolder(rowLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder postViewHolder, int i) {

        Post post = posts.get(i);
        postViewHolder.title.setText(post.title);
        postViewHolder.body.setText(post.body);
        postViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onPostClicked(view,postViewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
