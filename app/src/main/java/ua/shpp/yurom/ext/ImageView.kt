package ua.shpp.yurom.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import ua.shpp.yurrom.R


fun ImageView.loadImage(url: String){
    Glide.with(this)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.ic_user_avatar)
        .error(R.drawable.ic_user_avatar)
        .into(this)
}