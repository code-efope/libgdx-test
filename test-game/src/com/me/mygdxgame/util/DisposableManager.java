package com.me.mygdxgame.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class DisposableManager implements Disposable
{
	private final Array<Disposable> disposables = new Array<Disposable>();
	
	public void addDisposable(Disposable disposable)
	{
		disposables.add(disposable);
	}
	
	@Override
	public void dispose()
	{
		for (Disposable disposable: disposables)
		{
			if (disposable != null)
				disposable.dispose();
		}
		disposables.clear();
	}
}
