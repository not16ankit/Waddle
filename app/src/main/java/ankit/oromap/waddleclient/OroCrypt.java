package ankit.oromap.waddleclient;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.spongycastle.crypto.CryptoException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class OroCrypt {

   public void encryptfile(String password, Context c, Handler h, String name, File f, InputStream inp) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        FileOutputStream fos1 = new FileOutputStream(f);
        byte[] b=new byte[100000];
        int l=0;
        while((l=inp.read(b))>-1)
        {
            fos1.write(b,0,l);
        }
        fos1.flush();
        fos1.close();
        inp.close();
        FileInputStream fis = new FileInputStream(f);
        FileOutputStream fos = new FileOutputStream(new File(c.getCacheDir(),name+".gpg"));
        byte[] key = (password).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        key = Arrays.copyOf(key,16);
        SecretKeySpec sks = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        int b1;
        long total = 0;
        long len = f.length();
        byte[] d = new byte[1024];
        while((b1 = fis.read(d)) != -1) {
            cos.write(d, 0, b1);
            total = total+b1;
            Message m = new Message();
            Bundle bun=new Bundle();
            bun.putLong("total",total);
            bun.putLong("len",len);
            m.setData(bun);
            h.sendMessage(m);
        }
        cos.flush();
        cos.close();
        fis.close();
    }
    public void decryptfile(String path,String password, String outPath,Handler h,Handler end,String tar,Context con) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        TAR bt = new TAR();
        bt.decompress(new File(tar).getAbsolutePath(),
                new File(con.getCacheDir(),"")
        ) ;
       File f=new File(path);
        FileInputStream fis = new FileInputStream(f);
        FileOutputStream fos = new FileOutputStream(outPath);
        byte[] key = (password).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        key = Arrays.copyOf(key,16);
        SecretKeySpec sks = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        CipherInputStream cis = new CipherInputStream(fis, cipher);
        int b;
        long len = f.length();
        long total = 0;
        byte[] d = new byte[1024];
        while((b = cis.read(d)) != -1) {
            fos.write(d, 0, b);
            total = total+b;
            Message m=new Message();
            Bundle bun=new Bundle();
            bun.putLong("total",total);
            bun.putLong("len",len);
            m.setData(bun);
            h.sendMessage(m);
        }
        end.sendEmptyMessage(0);
        fos.flush();
        fos.close();
        cis.close();
    }
}