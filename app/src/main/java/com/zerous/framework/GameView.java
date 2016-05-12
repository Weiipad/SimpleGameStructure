package com.zerous.framework;

import android.view.*;
import android.content.*;
import android.graphics.*;
import android.util.*;
import android.widget.*;
import java.io.*;
import android.os.*;

public class GameView extends SurfaceView implements Runnable
{
	volatile boolean running = false;
	SurfaceHolder holder;
	Thread gt;
	Bitmap framebuffer;

	Canvas c;

	public android.content.res.Resources androidRes;

	public Context context;

	public GameView(Context c, Bitmap framebuffer)
	{
		super(c);
		holder = this.getHolder();
		context = c;	
		this.framebuffer = framebuffer;
		androidRes = c.getResources();
	}

	public void resume()
	{
		running = true;
		gt = new Thread(this);
		gt.start();
	}

	public void pause()
	{
		running = false;
		while(true)
		{
			try
			{
				gt.join();
				break;
			}
			catch(InterruptedException e)
			{

			}
		}
	}

	@Override
	public void run()
	{
		while(running)
		{
			if(!holder.getSurface().isValid())
				continue;
			c = holder.lockCanvas();
			currentScreen.draw(c);
			if(c != null)
				holder.unlockCanvasAndPost(c);
		}
	}
}
