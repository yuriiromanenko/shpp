package yurii.romanenko.shpp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import yurii.romanenko.shpp.R
import yurii.romanenko.shpp.databinding.ItemContactBinding
import yurii.romanenko.shpp.ext.loadImage

interface ContactActionListener{
    fun onUserDelete(contact: Contact)
    fun onUserProfile(contact: Contact)
}

class ContactsAdapter(
    private val actionListener : ContactActionListener
): RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>(), View.OnClickListener {

    var contacts : List<Contact> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val contact = v.tag as Contact
        when(v.id){
            R.id.deleteButton -> {
            actionListener.onUserDelete(contact)
            }
            else ->{
               actionListener.onUserProfile(contact)
            }

        }
    }

    override fun getItemCount(): Int = contacts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.deleteButton.setOnClickListener(this)

        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val user = contacts[position]
        with(holder.binding){
            holder.itemView.tag = user
            deleteButton.tag = user
            userNameTextView.text = user.name
            userCompanyTextView.text = user.company
            if (user.photo.isNotBlank()){
                photoImageView.loadImage(user.photo)
            } else{
                photoImageView.setImageResource(R.drawable.ic_user_avatar)
            }
        }
    }


    class ContactsViewHolder(
        val binding: ItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root)

}