package ru.mirea.makarovra.cryptoloader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {
    private String	text;
    private String key;
    public	static	final	String	ARG_WORD	=	"word";
    public	MyLoader(@NonNull Context context, String text, String key)	{
        super(context);
        this.text = text;
        this.key=key;
    }

    @Override
    protected	void	onStartLoading()	{
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public	String	loadInBackground()	{
        return	decryptMsg(text, key);
    }

    public static String decryptMsg(String encryptedMsg, String key) {
        try {
            byte[] btKey = Base64.decode(key, Base64.DEFAULT);
            byte[] btEncryptedMsg = Base64.decode(encryptedMsg, Base64.DEFAULT);

            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(btKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(btEncryptedMsg));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                 | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}