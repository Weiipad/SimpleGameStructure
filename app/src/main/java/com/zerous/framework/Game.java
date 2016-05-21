package com.zerous.framework;

import android.app.*;
import android.os.*;
import android.graphics.*;
import android.view.*;

public abstract class Game extends Activity
{
	Graphics graphics;
	FileIO file;
	Input input;
	
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
		graphics = new Graphics(getAssets(), framebuffer);
		file = new FileIO(this);
		input = new Input(renderView);
		
		screen = getStartScreen();
		
		Zex.graphics = graphics;
		Zex.file = file;
		Zex.input = input;
		
		setContentView(renderView);
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		renderView.resume();
		Zex.graphics = getGraphics();
		Zex.file = getFileIO();
		Zex.input = getInput();
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		renderView.pause();
	}
	
	public Input getInput()
	{
		return input;
	}
	
	public FileIO getFileIO()
	{
		return file;
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
