package com.me.mygdxgame.input;

import com.badlogic.gdx.Input.Keys;

public class InputMapper
{
	public static class KEYS
	{
		public static class MOVEMENT
		{
			public static final int JUMP = Keys.SPACE;
			public static final int FORWARD = Keys.W;
			public static final int BACKWARD = Keys.S;
			public static final int LEFT = Keys.A;
			public static final int RIGHT = Keys.D;
		}

		public static class SETTINGS
		{
			public static final int TOGGLE_LIGHT = Keys.L;
			public static final int TOGGLE_COLLISION = Keys.C;
			public static final int TOGGLE_HUD = Keys.H;
			public static final int TOGGLE_OCTREES = Keys.O;
			public static final int TOGGLE_PORTALS = Keys.P;
			public static final int INCREASE_VISUAL_RANGE = Keys.PLUS;
			public static final int DECREASE_VISUAL_RANGE = Keys.MINUS;
			public static final int TOGGLE_CONSOLE = Keys.CONTROL_LEFT;
		}
	}
}
