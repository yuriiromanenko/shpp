package yurii.romanenko.shpp.model


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import yurii.romanenko.shpp.R

class CustomGoogleButton (context: Context, attrs: AttributeSet?)   : ConstraintLayout(context, attrs)  {

init {
    val inflater = LayoutInflater.from(context)
    inflater.inflate(R.layout.button_google, this,true)
}

}