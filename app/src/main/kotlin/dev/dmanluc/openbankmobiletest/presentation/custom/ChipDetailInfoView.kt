package dev.dmanluc.openbankmobiletest.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.databinding.ViewChipDetailInfoBinding

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Definition of a custom view to show character stats in a better user-friendly way
 *
 */
class ChipDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ViewChipDetailInfoBinding =
        ViewChipDetailInfoBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        if (isInEditMode) {
            val data = ChipDetailInfoDataItem(R.string.character_detail_featured_comics_text, "50")
            setData(data)
        }
    }

    fun setData(item: ChipDetailInfoDataItem) {
        with(binding) {
            title.text = context.getString(item.titleResId)
            infoNumber.text = item.info
        }
    }

}