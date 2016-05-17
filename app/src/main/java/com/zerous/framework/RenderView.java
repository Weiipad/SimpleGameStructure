package com.zerous.framework;

import android.view.*;
import android.content.*;
import android.graphics.*;
import android.util.*;
import android.widget.*;
import java.io.*;
import android.os.*;
import android.content.res.*;

public class RenderView extends SurfaceView implements Runnable
{
	volatile boolean running = false;
	SurfaceHolder holder;
	Thread gt;
	Bitmap framebuffer;
	Game game;
	
	Canvas c;

	public RenderView(Game game, Bitmap framebuffer)
	{
		super(game);
		this.game = game;
		holder = this.getHolder();
		this.framebuffer = framebuffer;
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
			
			game.getCurrentScreen().update();
			game.getCurrentScreen().render();
			
			c = holder.lockCanvas();
			
			c.drawBitmap(framebuffer, 0, 0, new Paint());
			//c.drawColor(Color.BLUE);
			
			if(c != null)
				holder.unlockCanvasAndPost(c);
		}
	}
}
