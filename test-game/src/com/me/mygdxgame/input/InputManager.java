package com.me.mygdxgame.input;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class InputManager extends GestureDetector
{
	/* the listeners are sorted by insertion, thus the first one has the highest priority */
	private List<InputAdapter> adapters = new ArrayList<InputAdapter>();

	public InputManager()
	{
		super(new GestureListener()
		{
			@Override
			public boolean zoom(float initialDistance, float distance)
			{
				return false;
			}

			@Override
			public boolean touchDown(float x, float y, int pointer, int button)
			{
				Gdx.app.log("InputManager", "touchDown at " + x + "/" + y + " with " + pointer + " " + button);
				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button)
			{
				return false;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
				Vector2 pointer1, Vector2 pointer2)
			{
				return false;
			}

			@Override
			public boolean panStop(float x, float y, int pointer, int button)
			{
				return false;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY)
			{
				return false;
			}

			@Override
			public boolean longPress(float x, float y)
			{
				return false;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button)
			{
				return false;
			}
		});
	}

	public void addInputListener(InputAdapter adapter)
	{
		if (adapter != null)
			adapters.add(adapter);
	}

	@Override
	public boolean keyDown(int keycode)
	{
		for (InputAdapter adapter : adapters)
		{
			if (adapter.keyDown(keycode))
				return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		for (InputAdapter adapter : adapters)
		{
			if (adapter.keyUp(keycode))
				return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		for (InputAdapter adapter : adapters)
		{
			if (adapter.keyTyped(character))
				return true;
		}
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		for (InputAdapter adapter : adapters)
		{
			if (adapter.touchDown(screenX, screenY, pointer, button))
				return true;
		}
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		for (InputAdapter adapter : adapters)
		{
			if (adapter.touchUp(screenX, screenY, pointer, button))
				return true;
		}
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		for (InputAdapter adapter : adapters)
		{
			if (adapter.touchDragged(screenX, screenY, pointer))
				return true;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		for (InputAdapter adapter : adapters)
		{
			if (adapter.mouseMoved(screenX, screenY))
				return true;
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		for (InputAdapter adapter : adapters)
		{
			if (adapter.scrolled(amount))
				return true;
		}
		return false;
	}
}
