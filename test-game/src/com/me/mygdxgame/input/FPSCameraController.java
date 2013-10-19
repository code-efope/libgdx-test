package com.me.mygdxgame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class FPSCameraController extends CameraInputController
{
	public final Vector3 newPosition = new Vector3();
	public int jumpKey = Keys.SPACE;
	protected boolean jumpPressed = false;

	public FPSCameraController(Camera camera)
	{
		super(camera);
		this.rotateLeftKey = Keys.A;
		this.rotateRightKey = Keys.D;
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

	public boolean moved()
	{
		return (forwardPressed || backwardPressed || jumpPressed);
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

		if (forwardPressed || backwardPressed || jumpPressed)
		{
			if (forwardPressed)
			{
				newPosition.set(camera.direction.x, 0.0f, camera.direction.z).scl(delta * translateUnits);
			}
			if (backwardPressed)
			{
				newPosition.set(camera.direction.x, 0.0f, camera.direction.z).scl(-delta * translateUnits);
			}
			if (jumpPressed)
			{
				newPosition.set(0.0f, 0.5f, 0.0f).scl(delta * translateUnits);
			}
//			Gdx.app.log(this.getClass().getName(), "newPosition: " + newPosition);
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
		if (keycode == jumpKey)
			jumpPressed = true;
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if (keycode == jumpKey)
			jumpPressed = false;
		return super.keyUp(keycode);
	}
}

/*
 * public class CameraInputController extends InputAdapter {
 * public void update() {
 * if (rotateRightPressed || rotateLeftPressed || forwardPressed || backwardPressed) {
 * final float delta = Gdx.graphics.getDeltaTime();
 * if (rotateRightPressed)
 * camera.rotate(camera.up, -delta * rotateAngle);
 * if (rotateLeftPressed)
 * camera.rotate(camera.up, delta * rotateAngle);
 * if (forwardPressed) {
 * camera.translate(tmpV1.set(camera.direction).scl(delta * translateUnits));
 * if (forwardTarget)
 * target.add(tmpV1);
 * }
 * if (backwardPressed) {
 * camera.translate(tmpV1.set(camera.direction).scl(-delta * translateUnits));
 * if (forwardTarget)
 * target.add(tmpV1);
 * }
 * if (autoUpdate)
 * camera.update();
 * }
 * }
 * 
 * @Override
 * public boolean touchDown (int screenX, int screenY, int pointer, int button) {
 * if (this.button < 0 && (activateKey == 0 || activatePressed)) {
 * startX = screenX;
 * startY = screenY;
 * this.button = button;
 * }
 * return activatePressed;
 * }
 * 
 * @Override
 * public boolean touchUp (int screenX, int screenY, int pointer, int button) {
 * if (button == this.button)
 * this.button = -1;
 * return activatePressed;
 * }
 * 
 * protected boolean process(float deltaX, float deltaY, int button) {
 * if (button == rotateButton) {
 * tmpV1.set(camera.direction).crs(camera.up).y = 0f;
 * camera.rotateAround(target, tmpV1.nor(), deltaY * rotateAngle);
 * camera.rotateAround(target, Vector3.Y, deltaX * -rotateAngle);
 * } else if (button == translateButton) {
 * camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(-deltaX * translateUnits));
 * camera.translate(tmpV2.set(camera.up).scl(-deltaY * translateUnits));
 * if (translateTarget)
 * target.add(tmpV1).add(tmpV2);
 * } else if (button == forwardButton) {
 * camera.translate(tmpV1.set(camera.direction).scl(deltaY * translateUnits));
 * if (forwardTarget)
 * target.add(tmpV1);
 * }
 * if (autoUpdate)
 * camera.update();
 * return true;
 * }
 * 
 * @Override
 * public boolean touchDragged (int screenX, int screenY, int pointer) {
 * if (this.button < 0)
 * return false;
 * final float deltaX = (screenX - startX) / Gdx.graphics.getWidth();
 * final float deltaY = (startY - screenY) / Gdx.graphics.getHeight();
 * startX = screenX;
 * startY = screenY;
 * return process(deltaX, deltaY, button);
 * }
 * 
 * @Override
 * public boolean scrolled (int amount) {
 * if (!alwaysScroll && activateKey != 0 && !activatePressed)
 * return false;
 * camera.translate(tmpV1.set(camera.direction).scl(amount * scrollFactor * translateUnits));
 * if (scrollTarget)
 * target.add(tmpV1);
 * if (autoUpdate)
 * camera.update();
 * return true;
 * }
 * 
 * @Override
 * public boolean keyDown (int keycode) {
 * if (keycode == activateKey)
 * activatePressed = true;
 * if (keycode == forwardKey)
 * forwardPressed = true;
 * else if (keycode == backwardKey)
 * backwardPressed = true;
 * else if (keycode == rotateRightKey)
 * rotateRightPressed = true;
 * else if (keycode == rotateLeftKey)
 * rotateLeftPressed = true;
 * return false;
 * }
 * 
 * @Override
 * public boolean keyUp (int keycode) {
 * if (keycode == activateKey) {
 * activatePressed = false;
 * button = -1;
 * }
 * if (keycode == forwardKey)
 * forwardPressed = false;
 * else if (keycode == backwardKey)
 * backwardPressed = false;
 * else if (keycode == rotateRightKey)
 * rotateRightPressed = false;
 * else if (keycode == rotateLeftKey)
 * rotateLeftPressed = false;
 * return false;
 * }
 * }
 */