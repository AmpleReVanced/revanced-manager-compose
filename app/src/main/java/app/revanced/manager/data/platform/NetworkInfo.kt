package app.revanced.manager.data.platform

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import app.revanced.manager.domain.manager.PreferencesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NetworkInfo(app: Application) : KoinComponent {
    private val connectivityManager = app.getSystemService<ConnectivityManager>()!!
    private val prefs: PreferencesManager by inject()

    private fun getCapabilities() = connectivityManager.activeNetwork?.let { connectivityManager.getNetworkCapabilities(it) }
    fun isConnected() = connectivityManager.activeNetwork != null
    fun isUnmetered() = getCapabilities()?.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED) ?: true

    /**
     * Returns true if it is safe to download large files.
     * If "allow download on metered network" is enabled in settings, always returns true when connected.
     */
    fun isSafe() = isConnected() && (isUnmetered() || prefs.allowDownloadOnMeteredNetwork.getBlocking())
}