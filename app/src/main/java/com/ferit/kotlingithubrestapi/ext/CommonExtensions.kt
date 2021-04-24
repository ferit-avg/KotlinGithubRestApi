package com.ferit.kotlingithubrestapi.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ferit.kotlingithubrestapi.R
import com.ferit.kotlingithubrestapi.data.model.common.AppError
import com.ferit.kotlingithubrestapi.ui.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException

fun <A : Activity> Activity.startNewActivty(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun Exception.showError(context: Context?): String {
    when (this) {
        is HttpException -> {
            val response = this.response()
            val type = object : TypeToken<AppError>() {}.type
            try {
                val appError: AppError = Gson().fromJson(response?.errorBody()?.charStream(), type)
                return appError.convertServerResponse(context)
            } catch (e: Exception) {
                context?.let {
                    return context.getString(R.string.ERROR)
                } ?: return "Bir hata oluştu. Lütfen tekrar deneyin"

            }
        }
        else -> return "Bir hata oluştu. Lütfen tekrar deneyin"
    }
}

fun Exception.code(): String {
    return when (this) {
        is HttpException -> {
            val response = this.response()
            val type = object : TypeToken<AppError>() {}.type
            try {
                val appError: AppError =
                    Gson().fromJson(response?.errorBody()?.charStream(), type)
                appError.code
            } catch (e: Exception) {
                "Bir hata oluştu. Lütfen tekrar deneyin"
            }
        }
        else -> "Bir hata oluştu. Lütfen tekrar deneyin"
    }
}


fun AppError.convertServerResponse(context: Context?): String {
    return try {
        context!!.getString(context.resources.getIdentifier(this.code, "string", context.packageName))
    } catch (e: Exception) {
        this.message
    }
}


fun Fragment.safeNavigate(currentFragmentId: Int, direction: NavDirections) {
    val navController = findNavController()
    if (navController.currentDestination?.id == currentFragmentId) {
        navigate(direction)
    } else {
        Toast.makeText(context, "Navigation error", Toast.LENGTH_LONG).show()
    }
}

fun Fragment.navigate(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun Activity.navigateToMain() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
    this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    this.finish()
}

fun Fragment.isNetworkConnected(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }

    return result
}

