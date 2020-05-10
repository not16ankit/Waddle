package ankit.oromap.waddleclient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;
import java.io.InputStream;

import ankit.oromap.waddleclient.faiza.*;
public class g extends AsyncTask {
    String pass;
    paneer a;
    Handler h1;
    File forward;
    InputStream forwardinp;
    Handler ji;
    Handler extra;
    Context c;
    String name1;
    public g(Context con, String pss, Handler h2, Handler h,Handler extrahand, String name, InputStream is, File f)
    {
        this.name1 = name;
        this.ji=h;
        this.forward = f;
        this.forwardinp = is;
        this.c=con;
        this.extra = extrahand;
        this.h1 = h2;
        this.pass = pss;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            OroCrypt ob = new OroCrypt();
            ob.encryptfile(pass,c,ji,name1,forward,forwardinp);
        }
        catch(Exception e){
            Log.e("encrypt_error",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        a.paneer(h1,extra);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }
}
