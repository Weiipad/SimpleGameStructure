package com.zerous.framework;

import android.app.*;
import android.os.*;
import android.graphics.*;
import android.view.*;

public abstract class Game extends Activity
{
	Graphics graphics;
	Screen screen;
	RenderView renderView;
	
	public abstract Screen getStartScreen()
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Display d = getWindowManager().getDefaultDisplay();
		
		Bitmap framebuffer = Bitmap.createBitmap(d.getWidth(), d.getHeight(), Bitmap.Config.ARGB_8888);
		renderView = new RenderView(this, framebuffer);
		graphics = new Graphics(framebuffer);
		
		screen = getStartScreen();
		
		Zex.graphics = graphics;
		
		setContentView(renderView);
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		renderView.resume();
		Zex.graphics = getGraphics();
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		renderView.pause();
	}
	
	public Graphics getGraphics()
	{
		return graphics;
	}
	
	public Screen getCurrentScreen()
	{
		return screen;
	}
}
