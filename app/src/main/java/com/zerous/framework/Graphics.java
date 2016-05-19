package com.zerous.framework;
import android.content.res.*;
import android.graphics.*;
import java.io.*;

public class Graphics
{
	Paint paint;
	Canvas canvas;
	AssetManager asset;

	public static enum PixmapFormat
	{
		ARGB8888, ARGB4444, RGB565
		}

	public Graphics(AssetManager am, Bitmap framebuffer)
	{
		asset = am;
		canvas = new Canvas(framebuffer);
		paint = new Paint();
	}

	public Pixmap newPixmap(String fileName, PixmapFormat format)
	{
		Bitmap.Config config = null;
		switch (format)
		{
			case RGB565:
				config = Bitmap.Config.RGB_565;
				break;

			case ARGB4444:
				config = Bitmap.Config.ARGB_4444;
				break;

			case ARGB8888:
				config = Bitmap.Config.ARGB_8888;
				break;
		}

		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inPreferredConfig = config;

		InputStream in = null;
		Bitmap b = null;
		try
		{
			in = asset.open(fileName);
			b = BitmapFactory.decodeStream(in);
			if (b == null)
			{
				//throw xxx
			}
		}
		catch (IOException e)
		{
			//throw xxx
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					
				}
			}
		}
		
		switch (b.getConfig())
		{
			case RGB_565:
				format = PixmapFormat.RGB565;
				break;

			case ARGB_4444:
				format = PixmapFormat.ARGB4444;
				break;

			case ARGB_8888:
				format = PixmapFormat.ARGB8888;
				break;
		}
		return new Pixmap(b, format);
	}

	public void clear(int color)
	{
		canvas.drawColor(color);
	}

	public void drawText(String text, int x, int y)
	{
		paint.setAlpha(255);
		canvas.drawText(text, x, y, paint);
	}

	public void drawBitmap(Bitmap bitmap, int x, int y, int alpha)
	{
		paint.setAlpha(alpha);
		canvas.drawBitmap(bitmap, x, y, paint);
	}
}
