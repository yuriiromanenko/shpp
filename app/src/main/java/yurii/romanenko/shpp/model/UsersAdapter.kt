package yurii.romanenko.shpp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import yurii.romanenko.shpp.R
import yurii.romanenko.shpp.databinding.ItemUserBinding

interface UserActionListener{
    fun onUserDelete(user: User)
    fun onUserProfile(user: User)
}

class UsersAdapter(
    private val actionListener : UserActionListener
): RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener {

    var users : List<User> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val user = v.tag as User
        when(v.id){
            R.id.deleteButton -> {
            actionListener.onUserDelete(user)
            }
            else ->{
               actionListener.onUserProfile(user)
            }

        }
    }

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.deleteButton.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding){
            holder.itemView.tag = user
            deleteButton.tag = user
            userNameTextView.text = user.name
            userCompanyTextView.text = user.company
            if (user.photo.isNotBlank()){
                Glide.with(photoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .into(photoImageView)
            } else{
                photoImageView.setImageResource(R.drawable.ic_user_avatar)
            }
        }
    }


    class UsersViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)

}