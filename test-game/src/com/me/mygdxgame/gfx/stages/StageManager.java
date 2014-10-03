package com.me.mygdxgame.gfx.stages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.me.mygdxgame.interfaces.RendererIf;

/*
 * the ScreenManager handles all loaded screens and transitions between them
 * when an event is fired from within a screen the according transition is searched and
 * the target screen is activated
 * 
 * while active the screen gets rendered
 */
public class StageManager implements Disposable, RendererIf
{
	private final Map<String, Stage> stages = new HashMap<String, Stage>();
	private final List<StageTransition> transitions = new ArrayList<StageTransition>();
	private Stage currentStage;

	public StageManager()
	{
		currentStage = null;
		addStage("test", new TestStage());
		Stage s = new TestStage();
		s.act();
	}

	public void setStage(Stage stage)
	{
		if (stage != null)
			currentStage = stage;
	}

	private void addStage(String stageName, Stage stageData)
	{
		stages.put(stageName, stageData);
	}

	public Stage getStage(String screenName)
	{
		return stages.get(screenName);
	}

	public void addTransition(StageTransition transition)
	{
		transitions.add(transition);
	}

	@Override
	public void dispose()
	{
		for (Stage stage: stages.values())
			stage.dispose();
		stages.clear();
	}

	@Override
	public void render(float deltaTime)
	{
		if (currentStage != null)
			currentStage.act(deltaTime);
	}

	@Override
	public void render()
	{
		render(0);
	}
}
