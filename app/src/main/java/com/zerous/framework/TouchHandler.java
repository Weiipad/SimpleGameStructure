package com.zerous.framework;

import android.view.*;
import android.view.View.*;
import com.zerous.framework.Input.*;
import com.zerous.framework.Pool.*;
import java.util.*;

public class TouchHandler implements OnTouchListener
{
	private static final int MAX_TOUCHPOINTS = 10;

	int[] touchType = new int[MAX_TOUCHPOINTS];
	int[] touchX = new int[MAX_TOUCHPOINTS];
	int[] touchY = new int[MAX_TOUCHPOINTS];
	int[] id = new int[MAX_TOUCHPOINTS];
	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();

	public TouchHandler(View v)
	{
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>()
		{
			@Override
			public TouchEvent createObject()
			{
				return new TouchEvent();
			}
		};

		touchEventPool = new Pool<TouchEvent>(factory, 100);
		v.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		synchronized(this)
		{
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
			int pointerCount = event.getPointerCount();
			TouchEvent touchEvent;
			for(int i = 0;i < MAX_TOUCHPOINTS;i++)
			{
				if(i >= pointerCount)
				{
					id[i] = -1;
					continue;
				}
				int pointerId = event.getPointerId(i);
				if(event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex)
				{
					continue;
				}
				switch(action)
				{
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_POINTER_DOWN:
						touchEvent = touchEventPool.newObject();
						touchEvent.type = TouchEvent.TOUCH_DOWN;
						touchEvent.pointer = pointerId;
						touchEvent.x = touchX[i] = (int)event.getX(i);
						touchEvent.y = touchY[i] = (int)event.getY(i);
						touchType[i] = touchEvent.type;
						id[i] = pointerId;
						touchEventsBuffer.add(touchEvent);
						break;

					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_POINTER_UP:
					case MotionEvent.ACTION_CANCEL:
						touchEvent = touchEventPool.newObject();
						touchEvent.type = TouchEvent.TOUCH_UP;
						touchEvent.pointer = pointerId;
						touchEvent.x = touchX[i] = (int)event.getX(i);
						touchEvent.y = touchY[i] = (int)event.getY(i);
						touchType[i] = touchEvent.type;
						id[i] = -1;
						touchEventsBuffer.add(touchEvent);
						break;

					case MotionEvent.ACTION_MOVE:
						touchEvent = touchEventPool.newObject();
						touchEvent.type = touchEvent.TOUCH_MOVE;
						touchEvent.pointer = pointerId;
						touchEvent.x = touchX[i] = (int)event.getX(i);
						touchEvent.y = touchY[i] = (int)event.getY(i);
						touchType[i] = touchEvent.type;
						id[i] = pointerId;
						touchEventsBuffer.add(touchEvent);
						break;
				}
			}
			return true;
		}
	}

	public int getTouchType(int pointer)
	{
		synchronized(this)
		{
			int index = getIndex(pointer);
			if(index < 0||index >= MAX_TOUCHPOINTS)
				return 1;
			else
				return touchType[index];
		}
	}

	public int getTouchX(int pointer)
	{
		synchronized(this)
		{
			int index = getIndex(pointer);
			if(index < 0 || index >= MAX_TOUCHPOINTS)
				return 0;
			else
				return touchX[index];
		}
	}

	public int getTouchY(int pointer)
	{
		synchronized(this)
		{
			int index = getIndex(pointer);
			if(index < 0 || index >= MAX_TOUCHPOINTS)
				return 0;
			else
				return touchY[index];
		}
	}

	public List<TouchEvent> getTouchEvents()
	{
		synchronized(this)
		{
			int len = touchEvents.size();
			for(int i = 0;i < len;i++)
			{
				touchEventPool.free(touchEvents.get(i));
			}
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}

	private int getIndex(int pointerId)
	{
		for(int i = 0;i < MAX_TOUCHPOINTS;i++)
		{
			if(id[i] == pointerId)
			{
				return i;
			}
		}
		return -1;
	}
}
