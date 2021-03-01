package dev.dmanluc.openbankmobiletest.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import dev.dmanluc.openbankmobiletest.databinding.ViewErrorBinding

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewErrorBinding =
        ViewErrorBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(item: ErrorDataItem) {
        with(binding) {
            errorText.text = context.getString(item.textResId)
            errorImage.setImageResource(item.imageResId)
            retryButton.setOnClickListener {
                item.onErrorAction()
            }
        }
    }

}