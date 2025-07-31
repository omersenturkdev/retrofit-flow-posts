import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.omersenturk.apiflowposts.R
import com.omersenturk.apiflowposts.data.PostUiModel

class PostAdapter(private val posts: List<PostUiModel>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvPostTitle)
        val tvBody = itemView.findViewById<TextView>(R.id.tvPostBody)
        val image = itemView.findViewById<ImageView>(R.id.ivPosts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_posts, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.tvTitle.text = post.title
        holder.tvBody.text = post.body
        holder.image.load(post.image) {
            transformations(CircleCropTransformation())
        }
    }

    override fun getItemCount(): Int = posts.size


}
