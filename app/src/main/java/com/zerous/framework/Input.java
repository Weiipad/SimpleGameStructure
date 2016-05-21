package com.zerous.framework;
import android.view.*;

public class Input
{
	public static class TouchEvent
	{
		public static final int TOUCH_DOWN = 0;
		public static final int TOUCH_UP = 1;
		public static final int TOUCH_MOVE = 2;
		
		public int type;
		public int x, y;
		public int pointer;
	}
	
	TouchHandler touch;
	
	public Input(View v)
	{
		touch = new TouchHandler(v);
	}
	
	public int getTouchX(int pointer)
	{
		return touch.getTouchX(pointer);
	}
	
	public int getTouchY(int pointer)
	{
		return touch.getTouchY(pointer);
	}
	
	public int getTouchType(int pointer)
	{
		return touch.getTouchType(pointer);
	}
}
