package com.ferit.kotlingithubrestapi.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.ferit.kotlingithubrestapi.BuildConfig
import com.ferit.kotlingithubrestapi.ui.DataStateChangeListener
import com.ferit.kotlingithubrestapi.ui.UICommunicationListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.ClassCastException

@AndroidEntryPoint
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    //@Inject
    //lateinit var sharedPreferencesManager: SharedPreferencesManager

    lateinit var dataStateChangeListener: DataStateChangeListener
    lateinit var communicationListener: UICommunicationListener

    var uToken: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            //uToken = sharedPreferencesManager.getToken()
            if (BuildConfig.DEBUG) Timber.e("TOKEN $uToken")
        } catch (e: Exception) {
            Timber.e("$uToken error: ${e.localizedMessage}")
        }

        try {
            dataStateChangeListener = context as DataStateChangeListener
        } catch (e: ClassCastException) {
            Timber.e("$context must implement DataStateChangeListener")
        }

        try {
            communicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Timber.e("$context must implement UICommunicationListener")
        }
    }
}