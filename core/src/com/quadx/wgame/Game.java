package com.quadx.wgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.quadx.wgame.state.MenuState;
import shapes1_5_5.gui.Fonts;
import shapes1_5_5.gui.Screen;
import shapes1_5_5.states.GameStateManager;

public class Game extends ApplicationAdapter {
    public static TextureAtlas atlas;
    SpriteBatch batch;
	GameStateManager gsm;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		atlas = new TextureAtlas("textures/textures.atlas");
		Fonts.initFonts();
		gsm.push(new MenuState(gsm));
		Screen.setSize(new Vector2(1000,1000));

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
