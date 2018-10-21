package com.quadx.wgame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.quadx.wgame.*;
import com.quadx.wgame.commands.*;
import shapes1_5_5.command.Command;
import shapes1_5_5.controllers.Xbox360Pad;
import shapes1_5_5.gui.FPSModule;
import shapes1_5_5.gui.Fonts;
import shapes1_5_5.shapes.ShapeRendererExt;
import shapes1_5_5.states.GameStateManager;
import shapes1_5_5.states.State;
import shapes1_5_5.timers.Delta;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.gl20;
import static shapes1_5_5.timers.Time.SECOND;

/**
 * Created by Chris Cavazos on 10/20/2018.
 */
public class GameState extends State {
    ShapeRendererExt sr;
    public static World world;
    public static Player p1;
    FPSModule fps = new FPSModule();
    Moon moon;
    public static Cloud cloud;
    public static Sun sun;
    ArrayList<Monster> monsters = new ArrayList<>();
    Delta dSpawn = new Delta(3 * SECOND);
    public static int score;

    Delta dOver = new Delta(5 * SECOND);

    public GameState(GameStateManager gsm) {
        super(gsm);
        sr = new ShapeRendererExt();
        Xbox360Pad.init();
        sr.setAutoShapeType(true);
        Command.clearList();
        Command.add(new LeftComm());
        Command.add(new RightComm());
        Command.add(new PlantComm());
        Command.add(new JumpComm());
        Command.add(new StartComm());

        score = 0;
        world = new World();
        world.addPlant(new Plant(30));
        moon = new Moon();
        sun = new Sun();
        cloud = new Cloud();
        p1 = new Player();
        fps.setEnabled(true);
        for (int i = 0; i < 4; i++) {
            world.addPlant(new Plant(180 + (10 * i)));
            world.addPlant(new Plant((10 * i)));

        }
        monsters.add(new Monster());
        monsters.add(new Monster(20));

    }

    @Override
    public void update(float dt) {
        if (!isGameOver()) {

            if (Gdx.input.isKeyPressed(Input.Keys.P)) {
                monsters.add(new Monster());
            }

            fps.update(dt);
            dSpawn.update(dt);
            Command.runList();
            moon.update(dt);
            sun.update(dt);
            cloud.update(dt);
            if (dSpawn.isDone()) {
                monsters.add(new Monster(moon.ang));
                monsters.add(new Monster(moon.ang + 10));
                int s= score/5000;
                for(int i=0;i<s;i++)
                    monsters.add(new Monster(moon.ang+10+(5*i)));

                dSpawn.reset();

            }
            for (int i = monsters.size() - 1; i >= 0; i--) {
                Monster m = monsters.get(i);
                monsters.forEach(x -> x.collision(m));
                world.collision(m);
                p1.collision(m);
                if (m.dead) {
                    monsters.remove(i);
                    score += 100;
                }
            }
            world.collision(p1);

            world.update(dt);

            p1.update(dt);
            monsters.forEach(x -> x.update(dt));
        } else {
            if (MenuState.highScore < score) {
                MenuState.highScore = score;
            }
            dOver.update(dt);
            if (dOver.isDone()) {
                gsm.pop();
            }
        }

    }

    boolean isGameOver() {
        return p1.seed == 0 && world.plants.size() == 0;
    }

    public static boolean isInBound(float angA, float angB, float spread) {
        float angd = (angA - angB + 180 + 360) % 360 - 180;
        return angd < spread && angd > -spread;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(MenuState.back,0,0);
        world.renderBase(sb);
        sb.end();


        sr.begin(ShapeRenderer.ShapeType.Filled);
        world.render(sr);
        p1.render(sr);
        monsters.forEach(x -> x.render(sr));

        //moon.render(sr);
        cloud.render(sr);
        sr.end();
        sb.begin();
        monsters.forEach(x -> x.renderSB(sb));

        p1.renderSB(sb);
        world.renderSB(sb);
        cloud.renderSB(sb);
        Fonts.setFontSize(4);
        Fonts.getFont().draw(sb, "DAYS: " + sun.day, 10, scr.size.y - 60);
        Fonts.getFont().draw(sb, "SCORE: " + score, Fonts.centerString("SCORE: " + score), scr.size.y - 30);


        sun.renderSB(sb);
        moon.renderSB(sb);
        sb.end();
        Fonts.getFont().setColor(Color.WHITE);
        fps.render(sb, sr, new Vector2(20, 20));

        Gdx.gl.glEnable(gl20.GL_BLEND);
        Gdx.gl.glBlendFunc(gl20.GL_SRC_ALPHA, gl20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sun.render(sr);
        sr.end();

        if (isGameOver()) {
            sb.begin();
            Fonts.setFontSize(5);
            Fonts.getFont().draw(sb, "GAME OVER", Fonts.centerString("GAME OVER"), scr.size.y / 2);
            sb.end();
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {

        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
