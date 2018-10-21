package com.quadx.wgame.commands;

import com.badlogic.gdx.Input;
import com.quadx.wgame.state.GameState;
import com.quadx.wgame.state.MenuState;
import shapes1_5_5.command.Command;
import shapes1_5_5.controllers.Xbox360Pad;

import static shapes1_5_5.states.State.gsm;

/**
 * Created by Chris Cavazos on 10/20/2018.
 */
public class StartComm extends Command {
    public StartComm(){
        name="Use";
        keyboard= Input.Keys.ANY_KEY;
        contB = Xbox360Pad.BUTTON_START;
    }
    @Override
    public void execute() {
        if(pressed()) {
            if(cls.equals(MenuState.class)) {
                gsm.push(new GameState(gsm));
            }
        }
    }
}
