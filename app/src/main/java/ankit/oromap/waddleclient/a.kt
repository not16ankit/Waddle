package ankit.oromap.waddleclient

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.webkit.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toFile
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ankit.oromap.waddleclient.faiza.biryani
import ankit.oromap.waddleclient.faiza.braintime
import ankit.oromap.waddleclient.faiza.lovehate
import ankit.oromap.waddleclient.faiza.paneer
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.single_file.view.*
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.*
import java.lang.StringBuilder
import java.math.BigDecimal
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.CipherOutputStream
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.HashMap

class a : AppCompatActivity(),paneer {
    private  var pass:String = ""
    private var email:String = ""
    private var laycontent:ViewGroup? =null
    private var alertd:AlertDialog.Builder? = null
    private var lay:ViewGroup? = null
    private var REQUEST_CODE = 8
    private var forback = 0
    private var filename:String = ""
    private var drawer:DrawerLayout? =null
    var exitcode:String = ""
    var size:Long = 0
    private lateinit var appBarConfiguration: AppBarConfiguration
   private var filecount = 0
    companion object
    {
        lateinit var crash:Handler
        lateinit var internet:Handler
    }

    override fun onDestroy() {
        super.onDestroy()
        val cache = cacheDir
        val appDir = File(cache.parent)
        if(cache!=null && cache.isDirectory)
        {
            deletedir(cache)
        }
    }
    fun deletedir(f:File):Boolean
    {
        var suc:Boolean = true
        if(f!=null && f.isDirectory)
        {
            val childer = f.list()
            var i=0
            while(i<childer.size)
            {
                suc = deletedir(File(f,childer.get(i)))
                i++
            }
        }
        return suc
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                var string:String = ""
                val uri = data!!.data as Uri
                if(uri.scheme.equals("content")) {
                    val cursor = contentResolver.query(uri, null, null, null, null)
                    try {
                        if (cursor != null && cursor.moveToFirst()) {
                            string = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            size = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE))
                        }
                    } finally {
                        cursor!!.close()
                    }
                }
                if(string.isEmpty() || string==null)
                {
                    string=uri.path.toString()
                    val cut:Int = string.lastIndexOf('/')
                    if(cut!=-1)
                    {
                        string = string.substring(cut+1)
                    }
                }
                filename =string
                lay = findViewById<DrawerLayout>(R.id.drawer_layout) as ViewGroup
                val view = layoutInflater.inflate(R.layout.encrypting_alert,null)
                lay!!.addView(view)
                drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                val inp = contentResolver.openInputStream(uri)
                val f=File(cacheDir,string)
                val pb2:ProgressBar = view.findViewById(R.id.process)
                pb2.max = 100
                pb2.progress = 0
                val h=object:Handler()
                {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        lay!!.removeViewAt(2)
                        drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    }
                }
                val h2=object:Handler()
                {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        pb2.setProgress(((msg.data.getLong("total")/msg.data.getLong("len").toDouble())*100).toInt())
                    }
                }
                val extrahand = object:Handler()
                {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        val d=msg.data
                        riptonystark(d.getString("hash")!!)
                    }
                }
                val ob:g = g(applicationContext,pass,h,h2,extrahand,filename,inp,f)
                ob.a=this
                ob.execute() 
            }
        }
        }
fun riptonystark(hash:String)
{
    lay!!.getChildAt(2).findViewById<TextView>(R.id.processname).setText("Indexing...")
    val ququ=Volley.newRequestQueue(this)
    val sr = object:StringRequest(Request.Method.POST,"http://waddle.oromap.in/storehashs.php",Response.Listener {
        var json:String = ""
        val sr = object : StringRequest(Request.Method.POST, "http://waddle.oromap.in/gethashs.php", Response.Listener {
            json = it
            overkip(json.trim(),2)
        }, Response.ErrorListener {
            internet.sendEmptyMessage(0)
            lay!!.removeViewAt(2)
        }) {
            override fun getParams(): MutableMap<String, String> {
                val hash = HashMap<String, String>()
                hash.put("email",email)
                hash.put("password", pass)
                return hash
            }
        }
        ququ.add(sr)
    },Response.ErrorListener {
        Toast.makeText(applicationContext,"Server InputStream Error",Toast.LENGTH_LONG).show()
    })
    {
        override fun getParams(): MutableMap<String, String> {
            val hashs = HashMap<String,String>()
            hashs.put("hash",hash)
            hashs.put("email",email)
            hashs.put("filename",filename)
            hashs.put("size",size.toString())
            hashs.put("password",pass)
            return hashs
        }
    }
    ququ.add(sr)
}
    override fun paneer(h:Handler,extra:Handler) {
        h.sendEmptyMessage(0)
        val errorHandler = object:Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                lay!!.removeViewAt(2)
                drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                Toast.makeText(applicationContext,"Upload failed due to a timeout error. Please try again.",Toast.LENGTH_LONG).show()
            }
        }
        val ob:braintime = braintime(applicationContext,extra)
        ob.err = object : error
        {
            override fun err() {
                errorHandler.sendEmptyMessage(0)
            }
        }
        ob.biryani = object : biryani
        {
            override fun biryani_paneer_interface(h1:Handler,s:String) {
                val m = Message()
                val bun = Bundle()
                bun.putString("hash",s)
                m.data  = bun
                h1.sendMessage(m)
            }
        }
        ob.execute(File(cacheDir,filename+".gpg").absolutePath)
        val vh : View = layoutInflater.inflate(R.layout.load2,null)
        lay!!.addView(vh)
        drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
    fun web()
    {
        drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        forback = 1
        val view = layoutInflater.inflate(R.layout.libp2p,lay,false)
        lay!!.addView(view)
        val webview = view.findViewById<WebView>(R.id.webview)
        val pb=view.findViewById<ProgressBar>(R.id.pb)
        val client = object:WebChromeClient()
        {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                pb.setProgress(newProgress)

            }
        }
        webview.webChromeClient = client
        webview.loadUrl(resources.getString(R.string.libp2p))
        view.findViewById<TextView>(R.id.link).setText(resources.getString(R.string.libp2p))
        view.findViewById<ImageView>(R.id.cancelimg).setOnClickListener(View.OnClickListener {
            drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            lay!!.removeViewAt(2)
        })
    }
    public fun overkip(json:String,time:Int) {
        crash = object:Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val quq = Volley.newRequestQueue(applicationContext)
                var json:String = ""
                val sr = object : StringRequest(Request.Method.POST, "http://waddle.oromap.in/gethashs.php", Response.Listener {
                    json = it
                    overkip(json.trim(),9)
                }, Response.ErrorListener {
                    internet.sendEmptyMessage(0)
                }) {
                    override fun getParams(): MutableMap<String, String> {
                        val hash = HashMap<String, String>()
                        hash.put("email",email)
                        hash.put("password", pass)
                        return hash
                    }
                }
                quq.add(sr)
            }
        }
        val ob = JSONObject(json)
        val num: Int = Integer.parseInt(ob.getString("filecount"))
        filecount = num
        val list: ListView = findViewById(R.id.files)
        if (num == 0) {
            findViewById<TextView>(R.id.mantext).visibility = View.VISIBLE
            list.visibility = View.INVISIBLE
            if(time==2) {
                lay!!.removeViewAt(2)
            }
            drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            findViewById<TextView>(R.id.mantext).visibility = View.INVISIBLE
            list.visibility = View.VISIBLE
            var hashs = arrayOfNulls<String>(num)
            var ii = num
            var uu = 0
            var filenames = arrayOfNulls<String>(num)
            var i = num
            var u = 0
            var iii=num
            var uuu = 0
            var sizes = arrayOfNulls<String>(num)
            if (num % 2 == 0) {
                while (ii >= 1) {
                    hashs[uu] = ob.getString(ii.toString() + "hash")
                    ii--
                    uu++
                }
                while (i >= 1) {
                    filenames[u] = ob.getString(i.toString())
                    i--
                    u++
                }
                while (iii >= 1) {
                    sizes[uuu] = ob.getString(iii.toString() + "size")
                    iii--
                    uuu++
                }
                var blocks = arrayOfNulls<String>(num / 2).toList() as List<String>
                val filenameslist: List<String> = filenames.toList() as List<String>
                val hash: List<String> = hashs.toList() as List<String>
                val sizelist:List<String> = sizes.toList() as List<String>
                val list: ListView = findViewById(R.id.files)
                val adapt: adapter2 = adapter2(
                    alertd!!,
                    lay!!,
                    applicationContext,
                    Integer(num),
                    filenameslist,
                    hash,
                    blocks,
                    email,
                    pass,sizelist,drawer as DrawerLayout,crash
                )
                list.adapter = adapt
            } else {
                while (ii >= 1) {
                    hashs[uu] = ob.getString(ii.toString() + "hash")
                    ii--
                    uu++
                }
                while (i >= 1) {
                    filenames[u] = ob.getString(i.toString())
                    i--
                    u++
                }
                while (iii >= 1) {
                    sizes[uuu] = ob.getString(iii.toString() + "size")
                    iii--
                    uuu++
                }
                var blocks = arrayOfNulls<String>(((num - 1) / 2) + 1).toList() as List<String>
                val filenameslist: List<String> = filenames.toList() as List<String>
                val hash: List<String> = hashs.toList() as List<String>
                val sizelist:List<String> = sizes.toList() as List<String>
                val adapt: adapter2 = adapter2(
                    alertd!!,
                    lay!!,
                    applicationContext,
                    Integer(num),
                    filenameslist,
                    hash,
                    blocks,
                    email,
                    pass,sizelist,drawer as DrawerLayout,crash
                )
                list.adapter = adapt
            }
            if(time==2) {
                lay!!.removeViewAt(2)
            }
            drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        internet =object: Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Toast.makeText(applicationContext,"App couldn't detect internet connection. Restart the app.",Toast.LENGTH_LONG).show()
            }
        }
        window.statusBarColor = resources.getColor(R.color.blue)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        alertd = AlertDialog.Builder(this,R.style.slert)
        val nac = findViewById<NavigationView>(R.id.nav_view)
        nac.setNavigationItemSelectedListener(object :NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                drawerLayout.closeDrawers()
                when(item.itemId) {
                    R.id.account -> {
                        account()
                    }
                    R.id.keys -> {
                        keys()
                    }
                    R.id.logout -> {
                        logout()
                    }
                    R.id.libp2p -> {
                        web()
                    }
                    R.id.about -> {
                        about()
                    }
                    R.id.discord -> {
                        discord()
                    }
                    R.id.email -> {
                        email()
                    }
                }
                return true
            }
        })
       val code = intent.extras?.getString("code")?.toInt()
        lateinit var json: String
        lay = findViewById<DrawerLayout>(R.id.drawer_layout) as ViewGroup
        drawer = findViewById(R.id.drawer_layout)
        laycontent = findViewById<RelativeLayout>(R.id.container23) as ViewGroup
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (code == 0) {
            email = intent.extras?.getString("email").toString()
            pass = intent.extras?.getString("password").toString()
            val load: View = layoutInflater.inflate(
                R.layout.loading,
                lay,
                false
            )
            lay!!.addView(load)
            drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            val queue = Volley.newRequestQueue(this)
            val sr = object : StringRequest(Request.Method.POST, "http://waddle.oromap.in/gethashs.php", Response.Listener {
                json = it
                if(!File(filesDir,"jpg.png").exists())
                {
                    val ob = geticons()
                    ob.a = object:afternece
                    {
                        override fun after() {
                            overkip(json.trim(),2)
                        }
                    }
                    ob.execute(applicationContext)
                }
                else{
                    overkip(json.trim(),2)
                }
            }, Response.ErrorListener {
                internet.sendEmptyMessage(0)
                lay!!.removeViewAt(2)
            }) {
                override fun getParams(): MutableMap<String, String> {
                    val hash = HashMap<String, String>()
                    hash.put("email",email)
                    hash.put("password",pass)
                    return hash
                }
            }
            queue.add(sr)
        } else if (code == 1) {
            var fin = FileInputStream(File(filesDir,"creds.txt"))
            var len = 0
            while(fin.read().also { len = it } > 0)
            {
                email = email + len.toChar()
            }
            fin.close()
            len = 0
            fin = FileInputStream(File(filesDir,"pass.txt"))
            while(fin.read().also { len = it } > 0)
            {
                pass = pass + len.toChar()
            }
            fin.close()
            lay = findViewById<DrawerLayout>(R.id.drawer_layout) as ViewGroup
            val load: View = layoutInflater.inflate(
                R.layout.loading,
                lay,
                false
            )
            lay!!.addView(load)
            drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            val queue = Volley.newRequestQueue(this)
            val sr = object : StringRequest(Request.Method.POST, "http://waddle.oromap.in/gethashs.php", Response.Listener {
                json = it
                if(!File(filesDir,"jpg.png").exists())
                {
                    val ob = geticons()
                    ob.a = object:afternece
                    {
                        override fun after() {
                            overkip(json.trim(),2)
                        }
                    }
                    ob.execute(applicationContext)
                }
                else {
                    overkip(json.trim(), 2)
                }
            }, Response.ErrorListener {
              internet.sendEmptyMessage(0)
                lay!!.removeViewAt(2)
            }) {
                override fun getParams(): MutableMap<String, String> {
                    val hash = HashMap<String, String>()
                    hash.put("email",email)
                    hash.put("password",pass)
                    return hash
                }
            }
            queue.add(sr)
        }
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            val i = Intent.createChooser(intent, "Choose a File for Encryption")
            forback=1
            startActivityForResult(i, REQUEST_CODE)
        }
        val swip = findViewById<SwipeRefreshLayout>(R.id.swipe)
        swip.setColorSchemeColors(resources.getColor(R.color.blue))
        swip.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                val ququ =Volley.newRequestQueue(applicationContext)
                val sr = object : StringRequest(Request.Method.POST, "http://waddle.oromap.in/gethashs.php", Response.Listener {
                    swip.isRefreshing  = false
                    json = it
                    overkip(json.trim(),0)
                }, Response.ErrorListener {
                  internet.sendEmptyMessage(0)
                }) {
                    override fun getParams(): MutableMap<String, String> {
                        val hash = HashMap<String, String>()
                        hash.put("email",email)
                        hash.put("password", pass)
                        return hash
                    }
                }
                ququ.add(sr)
            }
        })
    }

    fun keys()
    {
        forback=2
        alertd!!.setCancelable(false)
        alertd!!.setMessage("AES encryption is used to encrypt your files as the developer was too lazy to get around PGP. "+resources.getString(R.string.youtube))
        alertd!!.setNegativeButton("OK", object:DialogInterface.OnClickListener
        {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog!!.dismiss()
                dialog.cancel()
                forback = 0
            }
        })
        alertd!!.show()
    }
   private  fun account()
    {
        drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        val k = layoutInflater.inflate(R.layout.account,lay,false)
        val uploadcount = k.findViewById<TextView>(R.id.filecount)
        val indexcount = k.findViewById<TextView>(R.id.indexcount)
        val email1:TextInputEditText = k.findViewById(R.id.email)
        val password:TextInputEditText = k.findViewById(R.id.password)
        email1.setText(email)
        password.setText(pass)
        uploadcount.setText(filecount.toString())
        indexcount.setText(filecount.toString())
        forback = 1
        lay!!.addView(k)
        drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
  private  fun logout()
    {
        val a= File(filesDir,"creds.txt").delete()
        val b=File(filesDir,"pass.txt").delete()
        if(a && b) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        else{
            Toast.makeText(applicationContext,"Cannot logout. Contact the developer on discord.",Toast.LENGTH_LONG).show()
        }
    }
    private fun discord()
    {
        val i = Intent(Intent.ACTION_VIEW,Uri.parse(resources.getString(R.string.discord)))
        exitcode = "discord"
        startActivity(i)
    }
   private fun email()
    {
        val emailintent = Intent(Intent.ACTION_SEND)
        emailintent.putExtra(Intent.EXTRA_EMAIL,resources.getString(R.string.email))
        emailintent.putExtra(Intent.EXTRA_SUBJECT,"Waddle Query")
        startActivity(Intent.createChooser(emailintent,"Mail Query"))
    }
    fun about()
    {
        drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        forback = 1
        val view = layoutInflater.inflate(R.layout.libp2p,lay,false)
        lay!!.addView(view)
        val webview = view.findViewById<WebView>(R.id.webview)
        val pb=view.findViewById<ProgressBar>(R.id.pb)
        val client = object:WebChromeClient()
        {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                pb.setProgress(newProgress)
            }
        }
        webview.webChromeClient = client
        webview.loadUrl("http://waddle.oromap.in/about.php")
        view.findViewById<TextView>(R.id.link).setText("http://waddle.oromap.in/about.php")
        view.findViewById<ImageView>(R.id.cancelimg).setOnClickListener(View.OnClickListener {
            drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            lay!!.removeViewAt(2)
        })
    }
    fun update()
    {
        val quq = Volley.newRequestQueue(this)
        var json:String = ""
        val sr = object : StringRequest(Request.Method.POST, "http://waddle.oromap.in/gethashs.php", Response.Listener {
            json = it
            overkip(json.trim(),9)
        }, Response.ErrorListener {
            internet.sendEmptyMessage(0)
        }) {
            override fun getParams(): MutableMap<String, String> {
                val hash = HashMap<String, String>()
                hash.put("email",email)
                hash.put("password", pass)
                return hash
            }
        }
        quq.add(sr)
    }
    override fun onBackPressed() {
        if(forback==1)
        {
            lay!!.removeViewAt(2)
            drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            forback=0
        }
        else{
            finish()
        }
    }
}
interface error
{
    fun err()
}
interface kipp
{
    fun kip(json:String)
}
interface pub
{
    fun pubg()
}
class adapter2(alert:AlertDialog.Builder,l:ViewGroup,c:Context,num:Integer,list:List<String>,hashs:List<String>,leng:List<String>,email:String,password:String,sizelen:List<String>,draw:DrawerLayout,j:Handler):ArrayAdapter<String>(c,R.layout.single_file,leng),pub
{
    var globalposition = 0
    companion object
    {
        lateinit var crashhandler:Handler
        lateinit var sizelist:List<String>
         var op = 0
        lateinit var drawer:DrawerLayout
        lateinit var al:AlertDialog.Builder
        lateinit var lay:ViewGroup
        lateinit var hash:List<String>
        lateinit var delete1:ImageView;
        lateinit var download1:ImageView
        lateinit var delete2:ImageView;
        lateinit var download2:ImageView
        lateinit var processname:TextView
        lateinit var lis:List<String>;
        lateinit var nu:Integer;
        lateinit var con:Context
        lateinit var pb:ProgressBar
        lateinit var emai:String
        lateinit var passwor:String
        var u = 0;
    }
    init {
        sizelist = sizelen
        u=0;
        crashhandler = j
        drawer = draw
        al = alert
        lay = l
        emai = email
        passwor = password
        hash = hashs
        lis = list
        con = c
        nu=num
    }

    override fun pubg() {
        lay.removeViewAt(2)
        drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        globalposition = position
        val li:LayoutInflater = LayoutInflater.from(con)
        al.setTitle("Alert")
        al.setMessage("Do you want to delete this file from our indexes?")
        var view:View = li.inflate(R.layout.intro,null);
        val qu = Volley.newRequestQueue(con)
        if(nu.toInt()%2==0)
        {
            view = li.inflate(R.layout.double_files,null)
            val name1:TextView = view.findViewById<TextView>(R.id.name1)
            val name2:TextView = view.findViewById<TextView>(R.id.name2)
            val date1:TextView = view.findViewById<TextView>(R.id.date1)
            val date2:TextView = view.findViewById<TextView>(R.id.date2)
            val im1:ImageView = view.findViewById<ImageView>(R.id.fileicon1)
            val im2:ImageView = view.findViewById<ImageView>(R.id.fileicon2)
            var sr:StringRequest
            delete1 = view.findViewById(R.id.delete1)
            download1 = view.findViewById(R.id.download1)
            delete2 = view.findViewById(R.id.delete2)
            download2 = view.findViewById(R.id.download2)
                if (position == 0) {
                    try {
                    val hash1 = hash.get(position)
                    val hash2 = hash.get(position + 1)
                    val text1 = lis.get(position)
                    val text2 = lis.get(position + 1)
                    name1.text = text1
                    name2.text = text2
                        date1.text="Size :"+(sizelist.get(position).toDouble()/1024).toInt() +" Kb"
                        date2.setText("Size :"+(sizelist.get(position+1).toDouble()/1024).toInt() + " Kb")
                    delete1.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            delete(qu, hash1)
                        }
                    })
                    delete2.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            delete(qu, hash2)
                        }
                    })
                    download1.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            download(hash1)
                        }
                    })
                    download2.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            download(hash2)
                        }
                    })
                        var ex1: String = ""
                        var ex2: String = ""
                        var lastindex: Int = text1.lastIndexOf('.')
                        while (lastindex < text1.length - 1) {
                            ex1 = ex1 + text1[lastindex + 1]
                            lastindex++
                        }
                        lastindex = text2.lastIndexOf('.')
                        while (lastindex < text2.length - 1) {
                            ex2 = ex2 + text2[lastindex + 1]
                            lastindex++
                        }
                        val list:List<String> = con.resources.getString(R.string.files).split(",").toList()
                        var i=0
                        var f1 = File(con.filesDir, "fileicon.png")
                        var f2 = File(con.filesDir, "fileicon.png")
                        while(i<24)
                        {
                            if(list.get(i).equals(ex1))
                            {
                                f1 = File(con.filesDir,ex1+".png")
                            }
                            if(list.get(i).equals(ex2))
                            {
                                f2 = File(con.filesDir,ex2+".png")
                            }
                            i++
                        }
                    im1.setImageBitmap(BitmapFactory.decodeFile(f1.absolutePath))
                    im2.setImageBitmap(BitmapFactory.decodeFile(f2.absolutePath))
                } catch (e:Exception)
                {
                    Log.e("dile",e.message)
                    val f1 = File(con.filesDir, "fileicon.png")
                    val f2 = File(con.filesDir, "fileicon.png")
                    im1.setImageBitmap(BitmapFactory.decodeFile(f1.absolutePath))
                    im2.setImageBitmap(BitmapFactory.decodeFile(f2.absolutePath))
                }
            }
            else {
                    try {
                        u = u + 1
                        val hash1 = hash.get(position + u)
                        val hash2 = hash.get(position + (u + 1))
                        val text1: String = lis.get(position + (u))
                        val text2: String = lis.get(position + (u + 1))
                        name1.text = text1
                        name2.text = text2
                        date1.setText("Size :"+(sizelist.get(position+u).toDouble()/1024).toInt() + " Kb")
                        date2.setText("Size :"+(sizelist.get(position+(u+1)).toDouble()/1024).toInt() + " Kb")
                        delete1.setOnClickListener(object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                delete(qu, hash1)
                            }
                        })
                        delete2.setOnClickListener(object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                delete(qu, hash2)
                            }
                        })
                        download1.setOnClickListener(object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                download(hash1)
                            }
                        })
                        download2.setOnClickListener(object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                download(hash2)
                            }
                        })
                        var ex1: String = ""
                        var ex2: String = ""
                        var lastindex: Int = text1.lastIndexOf('.')
                        while (lastindex < text1.length - 1) {
                            ex1 = ex1 + text1[lastindex + 1]
                            lastindex++
                        }
                        lastindex = text2.lastIndexOf('.')
                        while (lastindex < text2.length - 1) {
                            ex2 = ex2 + text2[lastindex + 1]
                            lastindex++
                        }
                        val list:List<String> = con.resources.getString(R.string.files).split(",").toList()
                        var i=0
                        var f1 = File(con.filesDir, "fileicon.png")
                        var f2 = File(con.filesDir, "fileicon.png")
                        while(i<24)
                        {
                            if(list.get(i).equals(ex1))
                            {
                                f1 = File(con.filesDir,ex1+".png")
                            }
                            if(list.get(i).equals(ex2))
                            {
                                f2 = File(con.filesDir,ex2+".png")
                            }
                            i++
                        }
                        im1.setImageBitmap(BitmapFactory.decodeFile(f1.absolutePath))
                        im2.setImageBitmap(BitmapFactory.decodeFile(f2.absolutePath))
                    }
                    catch (e:Exception)
                    {
                        Log.e("dile",e.message)
                    }
            }
        }
        else
        {
            if (position==0)
            {
                view = li.inflate(R.layout.single_file, null)
                val im: ImageView = view.findViewById<ImageView>(R.id.fileicon)
                try {
                    delete1 = view.findViewById(R.id.delete)
                    download1 = view.findViewById(R.id.download)
                    val date : TextView = view.findViewById(R.id.date)
                    val name: TextView = view.findViewById<TextView>(R.id.name)
                    val filename = lis.get(0)
                    val hash = hash.get(0)
                    name.text = filename
                    date.text = "Size :"+(sizelist.get(0).toDouble()/1024).toInt()+" Kb"
                    delete1.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            delete(qu, hash)
                        }
                    })
                    download1.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            download(hash)
                        }
                    })
                    var ex1: String = ""
                    var i = filename.lastIndexOf('.')
                    while (i < filename.length - 1) {
                        ex1 = ex1 + filename[i + 1]
                        i++
                    }
                    val list:List<String> = con.resources.getString(R.string.files).split(",").toList()
                    var ii=0
                    var f1 = File(con.filesDir, "fileicon.png")
                    while(ii<24)
                    {
                        if(list.get(ii).equals(ex1))
                        {
                            f1 = File(con.filesDir,ex1+".png")
                        }
                        ii++
                    }
                    im.setImageBitmap(
                        BitmapFactory.decodeFile(f1.absolutePath
                        )
                    )
                }
                catch (e:Exception)
                {
                    Log.e("dile",e.message)
                }
            }
            else
            {
                view = li.inflate(R.layout.double_files, null)
                val name1: TextView = view.findViewById<TextView>(R.id.name1)
                val name2: TextView = view.findViewById<TextView>(R.id.name2)
                val im1: ImageView = view.findViewById<ImageView>(R.id.fileicon1)
                val date1:TextView = view.findViewById<TextView>(R.id.date1)
                val date2:TextView = view.findViewById<TextView>(R.id.date2)
                val im2: ImageView = view.findViewById<ImageView>(R.id.fileicon2)
                try {
                    delete1 = view.findViewById(R.id.delete1)
                    delete2 = view.findViewById(R.id.delete2)
                    download1 = view.findViewById(R.id.download1)
                    download2 = view.findViewById(R.id.download2)
                    val hash1 = hash.get(position + u)
                    val hash2 = hash.get(position + (u + 1))
                    val text1 = lis.get(position + u)
                    val text2 = lis.get(position + (u + 1))
                    date1.setText("Size :"+ (sizelist.get(position+u).toDouble()/1024).toInt()+" Kb")
                    date2.setText("Size :"+ (sizelist.get(position+(u+1)).toDouble()/1024).toInt()+" Kb")
                    u = u + 1
                    name1.text = text1
                    name2.text = text2
                    delete1.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            delete(qu, hash1)
                        }
                    })
                    delete2.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            delete(qu, hash2)
                        }
                    })
                    download1.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            download(hash1)
                        }
                    })
                    download2.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            download(hash2)
                        }
                    })
                    var ex1: String = ""
                    var ex2: String = ""
                    var lastindex: Int = text1.lastIndexOf('.')
                    while (lastindex < text1.length - 1) {
                        ex1 = ex1 + text1[lastindex + 1]
                        lastindex++
                    }
                    lastindex = text2.lastIndexOf('.')
                    while (lastindex < text2.length - 1) {
                        ex2 = ex2 + text2[lastindex + 1]
                        lastindex++
                    }
                    val list:List<String> = con.resources.getString(R.string.files).split(",").toList()
                    var i=0
                    var f1 = File(con.filesDir, "fileicon.png")
                    var f2 = File(con.filesDir, "fileicon.png")
                    while(i<24)
                    {
                        if(list.get(i).equals(ex1))
                        {
                            f1 = File(con.filesDir,ex1+".png")
                        }
                        if(list.get(i).equals(ex2))
                        {
                            f2 = File(con.filesDir,ex2+".png")
                        }
                        i++
                    }
                    im1.setImageBitmap(BitmapFactory.decodeFile(f1.absolutePath))
                    im2.setImageBitmap(BitmapFactory.decodeFile(f2.absolutePath))
                }
                catch (e:Exception)
                {
                    Log.e("dile",e.message)
                }
            }
        }
        return view
    }
    fun delete(queue:RequestQueue,hash2:String)
    {
        al.setCancelable(false)
        al.setTitle("Sorry")
        al.setMessage("Delete feature is experiencing some issues and will soon be available.")
        al.setNegativeButton("OK",object:DialogInterface.OnClickListener
        {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog!!.dismiss()
                dialog.cancel()
            }
        })
        al.show()
        /*   al.setTitle("Alert")
                            al.setMessage("Do you want to delete this file from our indexes?")
                            al.setNegativeButton("NO", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    dialog!!.dismiss()
                                    dialog!!.cancel()
                                }
                            })
                            al.setPositiveButton("YES", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    dialog!!.dismiss()
                                    dialog!!.cancel()
                                }
                            })
                            al.show() */
        /* val v = LayoutInflater.from(con).inflate(R.layout.load1,lay,false)
        lay.addView(v)
        drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        val sr =object : StringRequest(Request.Method.POST,"http://waddle.oromap.in/deletehash.php",Response.Listener {
            if(it.toInt()==200)
            {
                lay.removeViewAt(2)
                drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                crashhandler.sendEmptyMessage(0)
            }
            else
            {
                lay.removeViewAt(2)
                drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                Toast.makeText(con,"Cannot Remove file",Toast.LENGTH_LONG).show()
            }
        },
            Response.ErrorListener {
               lay.removeViewAt(2)
                a.internet.sendEmptyMessage(0)
            })
        {
            override fun getParams(): MutableMap<String, String> {
                val hash = HashMap<String,String>()
                hash.put("email",emai)
                hash.put("password", passwor)
                hash.put("hash",hash2)
                return hash
            }
        }
        queue.add(sr) */
    }
    fun download(hash:String) {
        if (op == 1) {
            Toast.makeText(con,"We don't use multi-threading due to handler overloading. One download at a time please :)",Toast.LENGTH_LONG).show()
        } else if (op == 0) {
            op=1
            val v=LayoutInflater.from(con).inflate(R.layout.encrypting_alert, lay,false)
            pb = v.findViewById<ProgressBar>(R.id.process)
            processname = v.findViewById(R.id.processname)
            lay.addView(v)
            drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            processname.setText("Downloading....")
            pb.max = 100
            pb.setProgress(0)
            val h = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    pb.setProgress(
                        ((msg.data.getLong("total") / msg.data.getLong("len")
                            .toDouble()) * 100).toInt()
                    )
                }
            }
            val h2 = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    pb.setProgress(0)
                    processname.setText("Decrypting.....")
                }
            }
            val h3 = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    pb.setProgress(((msg.data.getLong("total")/msg.data.getLong("len").toDouble())*100).toInt())
                    }
            }
            val h4=object:Handler()
            {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    op = 0
                    lay.removeViewAt(2)
                    drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    Toast.makeText(con,"File Saved to /Downloads",Toast.LENGTH_LONG).show()
                }
            }
            val ob: lovehate = lovehate(h, h2,h3, con, sizelist.get(globalposition),h4)
            ob.execute(
                hash, File(con.cacheDir, "temp.tar").absolutePath,
                passwor, File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),lis.get(globalposition)).absolutePath
            )
        }
    }
}