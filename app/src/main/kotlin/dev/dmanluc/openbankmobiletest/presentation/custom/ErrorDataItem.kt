package dev.dmanluc.openbankmobiletest.presentation.custom

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class ErrorDataItem(
    @DrawableRes val imageResId: Int,
    @StringRes val textResId: Int,
    val onErrorAction: () -> Unit = {}
)