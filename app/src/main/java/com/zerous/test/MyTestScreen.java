package com.zerous.test;

import com.zerous.framework.*;
import android.graphics.*;

public class MyTestScreen implements Screen
{

	@Override
	public void update()
	{
		
	}

	@Override
	public void render()
	{
		Zex.graphics.clear(0xffffffff);
		Zex.graphics.drawText("HelloWorld", 50, 50);
	}
}
