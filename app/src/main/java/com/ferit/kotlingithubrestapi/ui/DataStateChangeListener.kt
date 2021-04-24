package com.ferit.kotlingithubrestapi.ui


interface DataStateChangeListener{
    fun showAlert(statusType: Int, message: String, shouldShowSuccessAlert: Boolean = false, shouldShowErrorAlert: Boolean = true)
    fun showProgressBar(display: Boolean)
    fun hideSoftKeyboard()
    fun hideBottomBar()
    fun showBottomBar()
    fun setBottomBarBgColor(color: Int)
}