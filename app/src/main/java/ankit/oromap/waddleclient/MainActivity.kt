package ankit.oromap.waddleclient
import ankit.oromap.waddleclient.faiza.*
import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.wang.avi.AVLoadingIndicatorView
import com.wang.avi.Indicator
import com.wang.avi.indicators.BallClipRotateIndicator
import kotlinx.android.synthetic.main.content_a.*
import kotlinx.android.synthetic.main.login_fragment.*
import net.kibotu.pgp.Pgp
import org.spongycastle.jce.provider.BouncyCastleProvider
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.Security
private var y:ViewGroup? = null
private var v:View? = null
private var emailet:TextInputEditText? = null
private var passet:TextInputEditText? = null
class MainActivity : AppCompatActivity(){
    companion object
    {
        lateinit var internet:Handler
        lateinit var code:Integer
        var oo = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        internet = object:Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Toast.makeText(applicationContext,"App couldn't detect internet connection. Restart the app.",Toast.LENGTH_LONG).show()
            }
        }
        code= Integer(0)
        window.statusBarColor = resources.getColor(R.color.back)
        val emailet: TextInputEditText = findViewById(R.id.email)
        val passet: TextInputEditText = findViewById(R.id.password)
        supportActionBar?.hide()
        emailet.isEnabled = false
        passet.isEnabled = false
        code1()
    }
fun code1()
{
    val f1= File(filesDir, "creds.txt")
    val f2 = File(filesDir,"pass.txt")
    if(f1.exists() && f2.exists())
    {
          anim()
    }
    else {
        after()
    }
}
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode)
        {
            8 ->
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(oo==1)
                    {
                        afterper()
                    }
                    if(oo==2)
                    {
                        afterper2()
                    }
                }
                else{
                     AlertDialog.Builder(applicationContext).setMessage("File read/write permissions required.").setNegativeButton("OK",
                        object: DialogInterface.OnClickListener
                        {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                dialog!!.dismiss()
                                dialog.cancel()
                                per(oo)
                            }
                        }).create().show()
                }
            }
        }
    }
    fun per(o:Int)
    {
        oo = o
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),8)
        }
        else
        {
         if(o==2)
         {
             afterper2()
         }
            else if(o==1)
         {
             afterper()
         }
        }
    }
    fun anim()
    {
        val li: LayoutInflater = layoutInflater
        y = findViewById(R.id.table) as ViewGroup
        val vi: View = li.inflate(R.layout.intro,y, false)
        val im:ImageView = vi.findViewById(R.id.introim)
        val anim = AnimationUtils.loadAnimation(this, R.anim.intro)
        anim.setAnimationListener(object:Animation.AnimationListener
        {
            override fun onAnimationStart(animation: Animation?) {

            }
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    afterper2()
                }
                else {
                    per(2)
                }
            }
        })
        y!!.addView(vi)
        im.startAnimation(anim)
    }
    fun afterper2()
    {
        val i = Intent(this,a::class.java)
        i.putExtra("code","1")
        code= Integer(1)
        startActivity(i)
    }
    fun after()
    {
        emailet = findViewById(R.id.email)
        passet = findViewById(R.id.password)
        val li: LayoutInflater = layoutInflater
        y = findViewById(R.id.table) as ViewGroup
        val vi: View = li.inflate(R.layout.intro,y, false)
        val im:FrameLayout = vi.findViewById(R.id.frame43)
        val anim2= AnimationUtils.loadAnimation(this,R.anim.move_img)
        val anim = AnimationUtils.loadAnimation(this, R.anim.intro)
        val anim3 = AnimationUtils.loadAnimation(this,R.anim.out_intro)
        val load = vi.findViewById<AVLoadingIndicatorView>(R.id.avi)
        anim3.setAnimationListener(object :Animation.AnimationListener
        {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                load.visibility = View.INVISIBLE
                im.startAnimation(anim2)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        anim2.setAnimationListener(object :Animation.AnimationListener
        {
            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    afterper()
                }
                else {
                    per(1)
                }
            }
        })
        anim.setAnimationListener(object:Animation.AnimationListener
        {
            override fun onAnimationStart(animation: Animation?) {

            }
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                val ob:geticons = geticons()
                load.visibility = View.VISIBLE
                load.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.in_intro))
                val h= object:Handler()
                {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        load.startAnimation(anim3)
                    }
                }
                ob.a = object:afternece
                {
                    override fun after() {
                        h.sendEmptyMessage(0)
                    }
                }
                ob.execute(applicationContext)
            }
        })
        y!!.addView(vi)
        val alertd = AlertDialog.Builder(this,R.style.slert)
        alertd.setMessage("Username and password do not match.")
        alertd.setTitle("Error")
        alertd.setCancelable(false)
        alertd.setNegativeButton("OK", object:DialogInterface.OnClickListener
        {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog!!.dismiss()
                dialog.cancel()
            }
        })
        im.animation = anim
        v = layoutInflater.inflate(R.layout.load1,y,false)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val login: Button = findViewById(R.id.login);
        Security.insertProviderAt(BouncyCastleProvider(), 1)
        login.setOnClickListener {
            val email: String = emailet!!.text.toString().trim()
            val password: String = passet!!.text.toString().trim()
            if (email.isEmpty() || password.isEmpty() || password.trim().length<6) {
                if (email.trim().isEmpty()) {
                    emailet!!.setError("Required Field")
                }
                if (password.trim().isEmpty()||password.trim().length<6) {
                    if (password.trim().isEmpty()) {
                        passet!!.setError("Required Field")
                    } else if (password.trim().length<6) {
                      passet!!.setError("Password should be more than 6 characters")
                }
                }
            } else {
                y!!.addView(v)
                val input:InputMethodManager  = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
               input.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0)
                val sr: StringRequest = object : StringRequest(
                    Request.Method.POST,
                    "http://waddle.oromap.in/signin.php",
                    Response.Listener {
                        if (it.equals("200", true)) {
                            ankit(email, password)
                           y!!.removeViewAt(5)
                        } else {
                            alertd.setMessage("Username and password do not match")
                            alertd.setNegativeButton("OK",object: DialogInterface.OnClickListener
                            {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    dialog!!.dismiss()
                                    dialog.cancel()
                                    input.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0)
                                }
                            })
                            alertd.setTitle("Error")
                            alertd.setCancelable(false)
                            alertd.show()
                            y!!.removeViewAt(5)

                        }
                    },
                    Response.ErrorListener {
                        y!!.removeViewAt(5)
                        internet.sendEmptyMessage(0)
                    }) {
                    override fun getParams(): Map<String, String> {
                        val hash: MutableMap<String, String> = HashMap<String, String>();
                        hash["email"] = email
                        hash["password"] = password
                        return hash;
                    }
                }
                queue.add(sr)
            }
        }
    }
    fun afterper()
    {
        y!!.removeViewAt(5)
        emailet!!.isEnabled = true
        findViewById<Button>(R.id.login).isClickable = true
        passet!!.isEnabled = true
        viewpagefun()
    }
    fun viewpagefun()
    {
        var links = arrayOfNulls<String>(6).toList()
        val qu= Volley.newRequestQueue(this)
        val sr = StringRequest(Request.Method.POST,"http://waddle.oromap.in/links.php",Response.Listener {
            links = it.toString().split(",")
            val vp:ViewPager = findViewById(R.id.vp)
            val vpadapt = pageradapter(supportFragmentManager,this,vp,links)
            vp.offscreenPageLimit = 6
            vp.adapter = vpadapt
        },Response.ErrorListener {
            internet.sendEmptyMessage(0)
        })
        qu.add(sr)
        qu.start()
    }

    override fun onPause() {
        super.onPause()
        if(code.toInt()==1) {
            finish()
        }
    }
    fun ankit(email:String,pass:String)
    {
        var fi:File = File(filesDir,"creds.txt")
        fi.createNewFile()
        var fin = FileOutputStream(fi)
        fin.write(email.toByteArray())
        fin.flush()
        fin.close()
        fi =File(filesDir,"pass.txt")
        fi.createNewFile()
        fin = FileOutputStream(fi)
        fin.write(pass.toByteArray())
        fin.flush()
        fin.close()
        val i=Intent(this,a::class.java)
        i.putExtra("email",email)
        i.putExtra("password",pass)
        i.putExtra("code","0")
        code = Integer(1)
        startActivity(i)
    }
}
class geticons:AsyncTask<Context, Context,String>()
{
   var a:afternece? = null
    override fun doInBackground(vararg params: Context?): String {
        try {
            val files: String = params[0]?.resources!!.getString(R.string.files)
            val list: List<String> = files.split(",")
            var i = 0
            lateinit var url: URL
            lateinit var con: HttpURLConnection
            lateinit var input: InputStream
            lateinit var fileto: File
            var b = ByteArray(1024)
            var l = 0
            lateinit var filestream: FileOutputStream
            while (i < 25) {
                url = URL("http://waddle.oromap.in/resources/icons/" + list.get(i).trim() + ".png")
                con = url.openConnection() as HttpURLConnection
                con.addRequestProperty("Referer", "http://waddle.oromap.in")
                input = con.getInputStream()
                fileto = File(params[0]!!.filesDir, list.get(i) + ".png")
                filestream = FileOutputStream(fileto)
                while (input.read(b).also { l = it } != -1) {
                    filestream.write(b, 0, l)
                }
                filestream.flush()
                filestream.close()
                i++
            }
        }catch (e:Exception)
        {
            MainActivity.internet.sendEmptyMessage(0)
        }
        return ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        a!!.after()
    }
}
interface k
{
    fun kl(im:ImageView,stream:HttpURLConnection)
}
class pageradapter(fm:FragmentManager,c:Context,v:ViewPager,link:List<String?>):FragmentPagerAdapter(fm)
{
    init {
        vp = v
        con = c
        links = link
    }
    companion object
    {
        lateinit var links:List<String?>
        lateinit var vp:ViewPager
     lateinit var con:Context
    }
    override fun getItem(position: Int): Fragment {
     var frag:Fragment = frag1(con,vp,links)
        when(position)
        {
            0 ->
            {
                frag = frag1(con,vp,links)
            }
            1 ->
            {
                frag = frag2(con,vp,links)
            }
            2 ->
            {
                frag = frag3(con,vp,links)
            }
            3 ->
            {
                frag = frag4(con,vp,links)
            }
            4 ->
            {
                frag = frag5(con,vp,links)
            }
            5 ->
            {
                frag = frag6(con,vp,links)
            }
        }
        return frag
    }

    override fun getCount(): Int {
        return 6
    }
}
interface afternece
{
    fun after()
}
class frag1(c:Context,v:ViewPager,link:List<String?>):Fragment()
{
    lateinit var im:ImageView
    companion object
    {
        lateinit var links:List<String?>
        lateinit var vp:ViewPager
        lateinit var con: Context
        lateinit var bit:Bitmap
    }
    init {
       links = link
        vp=v
        con = c
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val h1:Handler = object : Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                im.setImageBitmap(bit)
                im.setOnClickListener(View.OnClickListener {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(links.get(0)))
                    startActivity(i)
                })
            }
        }
        val view:View = inflater.inflate(R.layout.global_cardview,null)
         im = view.findViewById(R.id.im)
        val progressBar = view.findViewById<ProgressBar>(R.id.pd34)
        progressBar.max = 100
        progressBar.progress = 1
        val ob = a(h1,progressBar,con,vp)
        ob.execute(1)
        return view
    }
}
class frag2(c:Context,v:ViewPager,link:List<String?>):Fragment()
{
    lateinit var im:ImageView
    companion object
    {
        lateinit var links:List<String?>
        lateinit var vp:ViewPager
        lateinit var con: Context
        lateinit var bit:Bitmap
    }
    init {
        links = link
        vp=v
        con = c
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val h2:Handler = object : Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                im.setImageBitmap(bit)
                im.setOnClickListener(View.OnClickListener {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(links.get(1)))
                    startActivity(i)
                })
            }
        }
        val view:View = inflater.inflate(R.layout.global_cardview,null)
        im = view.findViewById(R.id.im)
        val progressBar = view.findViewById<ProgressBar>(R.id.pd34)
        val ob = b(h2,progressBar,con,vp)
        ob.execute(2)
        return view
    }
}
class frag3(c:Context,v:ViewPager,link:List<String?>):Fragment()
{
    lateinit var im:ImageView
    companion object
    {
        lateinit var links:List<String?>
        lateinit var vp:ViewPager
        lateinit var con: Context
        lateinit var bit:Bitmap
    }
    init {
        links = link
        con = c
        vp=v
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val h3:Handler = object : Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                im.setImageBitmap(bit)
                im.setOnClickListener(View.OnClickListener {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(links.get(2)))
                    startActivity(i)
                })
            }
        }
        val view:View = inflater.inflate(R.layout.global_cardview,null)
        im = view.findViewById(R.id.im)
        val progressBar = view.findViewById<ProgressBar>(R.id.pd34)
        val ob = c(h3,progressBar,con,vp)
        ob.execute(3)
        return view
    }
}
class frag4(c:Context,v:ViewPager,link:List<String?>):Fragment()
{
    lateinit var im:ImageView
    companion object
    {
        lateinit var links:List<String?>
        lateinit var vp:ViewPager
        lateinit var con: Context
        lateinit var bit:Bitmap
    }
    init {
        links = link
        vp=v
        con = c
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val h4:Handler = object : Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                im.setImageBitmap(bit)
                im.setOnClickListener(View.OnClickListener {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(links.get(3)))
                    startActivity(i)
                })
            }
        }
        val view:View = inflater.inflate(R.layout.global_cardview,null)
        im = view.findViewById(R.id.im)
        val progressBar = view.findViewById<ProgressBar>(R.id.pd34)
        val ob = d(h4,progressBar,con,vp)
        ob.execute(4)
        return view
    }
}
class frag5(c:Context,v:ViewPager,link:List<String?>):Fragment()
{
    lateinit var im:ImageView
    companion object
    {
        lateinit var links:List<String?>
        lateinit var vp:ViewPager
        lateinit var con: Context
        var bit:Bitmap? = null
    }
    init {
        links = link
        vp=v
        con = c
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val h5:Handler = object : Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                im.setImageBitmap(bit)
                im.setOnClickListener(View.OnClickListener {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(links.get(4)))
                    startActivity(i)
                })
            }
        }
        val view:View = inflater.inflate(R.layout.global_cardview,null)
        im = view.findViewById(R.id.im)
        val progressBar = view.findViewById<ProgressBar>(R.id.pd34)
        val ob = e(h5,progressBar,con,vp)
        ob.execute(5)
        return view
    }
}
class frag6(c:Context,v:ViewPager,link:List<String?>):Fragment()
{
    lateinit var im:ImageView
    companion object
    {
        lateinit var links:List<String?>
        lateinit var vp:ViewPager
        lateinit var con: Context
        var bit:Bitmap? = null
    }
    init {
        links = link
        vp=v
        con = c
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val h6:Handler = object : Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                im.setImageBitmap(bit)
                im.setOnClickListener(View.OnClickListener {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(links.get(5)))
                    startActivity(i)
                })
            }
        }
        val view:View = inflater.inflate(R.layout.global_cardview,null)
        im = view.findViewById(R.id.im)
        val progressBar = view.findViewById<ProgressBar>(R.id.pd34)
        val ob = f(h6,progressBar,con,vp)
        ob.execute(6)
        return view
    }
}