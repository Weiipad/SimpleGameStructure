package com.zerous.test;

import com.zerous.framework.*;

public class MyGame extends Game
{

	@Override
	public Screen getStartScreen()
	{
		return new MyTestScreen();
	}
}
