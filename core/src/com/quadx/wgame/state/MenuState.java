package com.quadx.wgame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.quadx.wgame.Game;
import com.quadx.wgame.World;
import com.quadx.wgame.commands.StartComm;
import shapes1_5_5.command.Command;
import shapes1_5_5.controllers.Xbox360Pad;
import shapes1_5_5.gui.Fonts;
import shapes1_5_5.states.GameStateManager;
import shapes1_5_5.states.State;

import static com.quadx.wgame.state.GameState.world;

/**
 * Created by Chris Cavazos on 10/21/2018.
 */
public class MenuState extends State {
    public static int highScore;

    static Texture back;


    public MenuState(GameStateManager gsm) {
        super(gsm);

        Command.clearList();
        Command.add(new StartComm());
        world= new World();
        back= new Texture(Gdx.files.internal("pack/background.png"));
    }

    @Override
    public void update(float dt) {
        Command.runList();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(MenuState.back,0,0);
        world.renderBase(sb);
        Fonts.setFontSize(4);
        TextureRegion t =Game.atlas.findRegion("friend").split(32,32)[0][0];
        sb.draw(t,50,scr.size.y/2-200,0,0,t.getRegionWidth(),t.getRegionHeight(),2,2,0);
        Fonts.getFont().draw(sb,"PLAYER",50,scr.size.y/2-210);

        TextureRegion t2 =Game.atlas.findRegion("en0").split(32,32)[0][0];
        sb.draw(t2,50,scr.size.y/2-300,0,0,t.getRegionWidth(),t.getRegionHeight(),2,2,0);
        Fonts.getFont().draw(sb,"ENEMY",50,scr.size.y/2-310);

        TextureRegion t3 =Game.atlas.findRegion("pl4").split(32,32)[0][0];
        sb.draw(t3,50,scr.size.y/2-400,0,0,t.getRegionWidth(),t.getRegionHeight(),2,2,0);
        Fonts.getFont().draw(sb,"PROTECT",50,scr.size.y/2-410);

        String instruct = "Controls: ";
        Fonts.getFont().draw(sb, instruct, Fonts.centerString(instruct), scr.size.y-50);

        String cMove = "D-PAD / A, D - Move";
        Fonts.getFont().draw(sb, cMove, Fonts.centerString(cMove), scr.size.y-80);

        String cCmd = "A / R-CTRL - Plant";
        String cCmd1 = "B / Space - Jump";
        Fonts.getFont().draw(sb, cCmd, Fonts.centerString(cCmd), scr.size.y-110);
        Fonts.getFont().draw(sb, cCmd1, Fonts.centerString(cCmd1), scr.size.y-140);

        String kInst1 = "Protect your plants from monsters";
        Fonts.getFont().draw(sb, kInst1, Fonts.centerString(kInst1), scr.size.y + -210);

        String kInst2 = "Kill the monsters by squishing them";
        Fonts.getFont().draw(sb, kInst2, Fonts.centerString(kInst2), scr.size.y + -240);

        String kInst3 = "Collect fully grown plants by standing on them";
        Fonts.getFont().draw(sb, kInst3, Fonts.centerString(kInst3), scr.size.y + -270);


        String score = "HIGH SCORE: " + highScore;
        Fonts.setFontSize(8);
        Fonts.getFont().draw(sb, score,Fonts.centerString(score),80);

        Fonts.setFontSize(20);
        String title = "G R O W";
        Fonts.getFont().draw(sb, title,Fonts.centerString(title), (scr.size.y/2)+70);

        Fonts.setFontSize(5);
        String inst = "Press Start";
        Fonts.getFont().draw(sb, inst,Fonts.centerString(inst), (scr.size.y/2-100));

        sb.end();

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
        System.out.println(buttonCode);
        if(buttonCode == Xbox360Pad.BUTTON_START){
            gsm.push(new GameState(gsm));

        }
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
        Xbox360Pad.updatePOV(value);

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
