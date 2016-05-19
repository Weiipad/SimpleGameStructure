package com.zerous.framework;

import android.graphics.*;
import com.zerous.framework.Graphics.PixmapFormat;

public class Pixmap
{
	Bitmap bitmap;
	PixmapFormat format;
	
	public Pixmap(Bitmap bitmap, PixmapFormat format)
	{
		this.bitmap = bitmap;
		this.format = format;
	}
	
	public void scale(float xy)
	{
		Matrix m = new Matrix();
		m.postScale(xy, xy);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
	}
	
	public int getWidth()
	{
		return bitmap.getWidth();
	}
	
	public int getHeight()
	{
		return bitmap.getHeight();
	}
	
	public PixmapFormat getFormat()
	{
		return format;
	}
	
	public void dispose()
	{
		bitmap.recycle();
	}
}
