package ua.shpp.yurom.model


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import ua.shpp.yurrom.R

class CustomGoogleButton (context: Context, attrs: AttributeSet?)   : ConstraintLayout(context, attrs)  {

init {
    val inflater = LayoutInflater.from(context)
    inflater.inflate(R.layout.button_google, this,true)
}

}