package dev.dmanluc.openbankmobiletest.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.dmanluc.openbankmobiletest.presentation.navigation.NavigationCommand
import dev.dmanluc.openbankmobiletest.utils.setupSnackbarWithStringLiteral
import dev.dmanluc.openbankmobiletest.utils.setupSnackbarWithStringResId

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Base fragment template which handles snackbar messages and navigation operations
 *
 */
abstract class BaseFragment(@LayoutRes val layoutResId: Int) : Fragment(layoutResId) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeNavigation(getViewModel())

        setupSnackbarWithStringResId(
            this,
            getViewModel().snackbarErrorWithStringResId,
            Snackbar.LENGTH_LONG
        )
        setupSnackbarWithStringLiteral(
            this,
            getViewModel().snackbarErrorWithStringLiteral,
            Snackbar.LENGTH_LONG
        )
    }

    abstract fun getViewModel(): BaseViewModel

    private fun observeNavigation(viewModel: BaseViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { command ->
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(
                        command.directions, getExtras()
                    )
                    is NavigationCommand.Back -> findNavController().navigateUp()
                }
            }
        })
    }

    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()

}