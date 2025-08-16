import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.omersenturk.apiflowposts.R
import com.omersenturk.apiflowposts.data.models.PostUiModel
import com.omersenturk.apiflowposts.databinding.ItemPostsBinding

class PostAdapter(private var posts: List<PostUiModel>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(private val binding : ItemPostsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post : PostUiModel){
            binding.textViewPostTitle.text = post.title
            binding.textViewPostBody.text = post.body
            binding.imageViewPosts.load(post.image){
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostsBinding.inflate(LayoutInflater.from(parent.context),parent,false,)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    fun updatePosts(newPosts: List<PostUiModel>) {
        posts = newPosts
        notifyDataSetChanged()
    }

}
