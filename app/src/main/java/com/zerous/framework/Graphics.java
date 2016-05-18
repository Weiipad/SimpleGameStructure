package com.zerous.framework;
import android.graphics.*;

public class Graphics
{
	Paint paint;
	Canvas canvas;
	
	public static enum PixmapFormat
	{
		ARGB8888, ARGB4444, RGB565
	}
	
	public Graphics(Bitmap framebuffer)
	{
		canvas = new Canvas(framebuffer);
		paint = new Paint();
	}
	
	public Bitmap newBitmap()
	{
		
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
