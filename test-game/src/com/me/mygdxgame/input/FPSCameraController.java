package com.me.mygdxgame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class FPSCameraController extends CameraInputController
{
	public final Vector3 newPosition = new Vector3();

	public FPSCameraController(Camera camera)
	{
		super(camera);
		this.rotateLeftKey = InputMapper.KEYS.MOVEMENT.LEFT;
		this.rotateRightKey = InputMapper.KEYS.MOVEMENT.RIGHT;
		this.rotateAngle = 90.0f;
	}

	public void accept()
	{
		camera.translate(newPosition);
		if (forwardTarget)
			target.add(newPosition);
		if (autoUpdate)
			camera.update();		
	}

	public void reject()
	{
		if (autoUpdate)
			camera.update();
	}

	public boolean hasMoved()
	{
		return (forwardPressed || backwardPressed);
	}

	@Override
	public void update()
	{
		final float delta = Gdx.graphics.getDeltaTime();
		if (rotateRightPressed || rotateLeftPressed)
		{
			if (rotateRightPressed)
				camera.rotate(camera.up, -delta * rotateAngle);
			if (rotateLeftPressed)
				camera.rotate(camera.up, delta * rotateAngle);
			if (autoUpdate)
				camera.update();		
		}

		if (forwardPressed || backwardPressed)
		{
			if (forwardPressed)
				newPosition.set(camera.direction.x, 0.0f, camera.direction.z).scl(delta * translateUnits);
			if (backwardPressed)
				newPosition.set(camera.direction.x, 0.0f, camera.direction.z).scl(-delta * translateUnits);
		}
	}

	@Override
	protected boolean process(float deltaX, float deltaY, int button)
	{
		// process is called after incoming touchdrag messages
		return super.process(deltaX, deltaY, button);
	}

	@Override
	public boolean keyDown(int keycode)
	{
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode)
	{
		return super.keyUp(keycode);
	}
}