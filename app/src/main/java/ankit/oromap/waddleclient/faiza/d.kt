package ankit.oromap.waddleclient.faiza

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ProgressBar
import androidx.viewpager.widget.ViewPager
import ankit.oromap.waddleclient.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

public class d(handler: Handler, progressBar: ProgressBar, con: Context, vp: ViewPager):
    AsyncTask<Int, Int, Int>()
{
    companion object
    {
        lateinit var v: ViewPager
        lateinit var c: Context
        lateinit var prog: ProgressBar
        lateinit var h: Handler
    }
    init {
        v = vp
        prog = progressBar
        h = handler
        c = con
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        prog.setProgress(values[0]!!,true)
    }

    override fun doInBackground(vararg params: Int?): Int {
        val u: URL = URL(c.resources.getString(R.string.url)+"/resources/"+params[0]+".png")
        val con: HttpURLConnection = u.openConnection() as HttpURLConnection
        val l = con.contentLength
        var b2 = ByteArray(10000)
        var len = 0
        var tot = 0
        val f= File(c.filesDir,"temp"+params[0]+".png")
        val fout = FileOutputStream(f)
        while(con.inputStream.read(b2).also { len = it }>-1)
        {
            fout.write(b2,0,len)
            tot = tot+len
            publishProgress(tot*100/l)
        }
        fout.flush()
        fout.close()
        val b: Bitmap = BitmapFactory.decodeStream(FileInputStream(f))
        when(params[0])
        {
            1 ->
            {
                frag1.bit = b
            }
            2 ->
            {
                frag2.bit = b
            }
            3 ->
            {
                frag3.bit = b
            }
            4 ->
            {
                frag4.bit = b
            }
            5 ->
            {
                frag5.bit = b
            }
            6 ->
            {
                frag6.bit = b
            }
        }
        h.sendEmptyMessage(0)
        return params[0]!!
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        prog.visibility = View.INVISIBLE
    }
}