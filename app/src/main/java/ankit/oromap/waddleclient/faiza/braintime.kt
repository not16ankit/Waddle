package ankit.oromap.waddleclient.faiza

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Handler
import ankit.oromap.waddleclient.error
import io.ipfs.kotlin.defaults.InfuraIPFS
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class braintime(c:Context,h1:Handler):AsyncTask<String,String,String>() {
    var biryani:biryani? = null
    var err:error? = null
    companion object
    {
        lateinit var handler: Handler
        lateinit var con:Context
    }
    init {
        handler = h1
        con = c
    }
    override fun doInBackground(vararg params: String?): String {
        try {
            var hash = ""
            val ipfs = InfuraIPFS()
            hash = ipfs.add.file(File(params[0])).Hash
            while (true) {
                if (hash.trim().isEmpty()) {
                    continue;
                } else {
                    break;
                }
            }
            biryani!!.biryani_paneer_interface(handler, hash)
        }catch (e:Exception)
        {
            err!!.err()
        }
        return ""
    }
}