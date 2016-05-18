package com.zerous.framework;

import android.content.res.*;
import android.content.*;
import android.os.*;
import java.io.*;
import android.preference.*;

public class FileIO
{
	Context c;
	AssetManager am;
	String storagePath;
	public FileIO(Context context)
	{
		c = context;
		am = context.getAssets();
		storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}
	
	public InputStream readAsset(String fileName) throws IOException
	{
		return am.open(fileName);
	}
	
	public InputStream readFile(String fileName) throws IOException
	{
		return new FileInputStream(storagePath + fileName);
	}
	
	public OutputStream writeFile(String fileName) throws IOException
	{
		return new FileOutputStream(storagePath + fileName);
	}
	
	public SharedPreferences getPreferences()
	{
		return PreferenceManager.getDefaultSharedPreferences(c);
	}
}
