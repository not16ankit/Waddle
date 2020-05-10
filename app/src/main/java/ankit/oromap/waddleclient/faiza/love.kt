package ankit.oromap.waddleclient.faiza

import android.content.Context
import android.os.*
import android.util.Log
import android.widget.Toast
import ankit.oromap.waddleclient.OroCrypt
import ankit.oromap.waddleclient.TAR
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import javax.net.ssl.HttpsURLConnection

public class lovehate(h1: Handler,h2:Handler,h3:Handler,c:Context,size:String,end1:Handler):AsyncTask<String,Int,String>() {
    companion object
    {
        lateinit var end:Handler
        lateinit var sizesize:String
        lateinit var con1:Context;
        lateinit var handleforprogressdecrypt:Handler
        lateinit var handleforremove:Handler
        lateinit var handleforprogress:Handler
    }
    init {
        end = end1
        sizesize = size
        con1 = c
        handleforprogress = h1
        handleforremove = h2
         handleforprogressdecrypt = h3
    }
    override fun doInBackground(vararg params: String?): String {
        try {
            var u: URL
            var con: HttpsURLConnection
            var input: InputStream
            u = URL("https://ipfs.infura.io:5001/api/v0/get?arg=" + params[0]!!.trim())
            con = u.openConnection() as HttpsURLConnection
            val len: Long = sizesize.toLong()
            var total: Long = 0
            var l = 0
            var b = ByteArray(1024)
            val f = File(params[1])
            input = con.getInputStream()
            val fout = FileOutputStream(f)
            while (input.read(b).also { l = it } > -1) {
                fout.write(b, 0, l)
                total = total + l
                val m = Message()
                val bun = Bundle()
                bun.putLong("total", total)
                bun.putLong("len", len)
                m.data = bun
                handleforprogress.sendMessage(m)
            }
            fout.flush()
            fout.close()
            input.close()
            con.disconnect()
            handleforremove.sendEmptyMessage(0);
            val ob = OroCrypt()
            ob.decryptfile(
                File(con1.cacheDir, params[0]).absolutePath,
                params[2],
                params[3],
                handleforprogressdecrypt,
                end,
                params[1],
                con1
            )
        }
        catch (e:Exception)
        {
            Log.e("lip",e.message)
         ankit.oromap.waddleclient.a.internet.sendEmptyMessage(0)
        }
            return ""
    }
}
