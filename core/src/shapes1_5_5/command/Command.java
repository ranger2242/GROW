package shapes1_5_5.command;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.PovDirection;
import shapes1_5_5.controllers.Xbox360Pad;
import shapes1_5_5.states.State;

import java.util.ArrayList;

import static shapes1_5_5.controllers.Controllers.controllerMode;

/**
 * Created by Chris Cavazos on 8/8/2016.
 */
public abstract class Command {


    public static ArrayList<Command> commands = new ArrayList<>();
    protected String name;
    protected int keyboard;
    protected int contB = -1;
    protected int contA = -1;
    protected int axis = 0;
    protected PovDirection contD;
    protected String contButtonName = "";
    public static boolean disableControls = false;

    public Command() {
        init();
    }

    public static Class cls;

    public static void clearList() {
        commands.clear();
    }

    public static void add(Command c) {
        commands.add(c);
    }

    public static void runList() {
        try {
            for (Command c : commands) {
                c.execute();
            }
        } catch (Exception e) {
        }

    }

    public abstract void execute();

    public void changeKey(int k) {
        keyboard = k;
    }

    public void changeCont(int c, int a, PovDirection d) {
        contD = d;
        contB = c;
        axis = a;
    }

    public int getButtonK() {
        return keyboard;
    }

    public int getButtonC() {
        return contB;
    }


    protected boolean pressed() {
        if (!disableControls) {
            boolean special = false;
            if (keyboard == 59 || keyboard == 60) {//check shift
                if (Gdx.input.isKeyPressed(59) || Gdx.input.isKeyPressed(60)) {
                    special = true;
                }
            }
            return (Gdx.input.isKeyPressed(keyboard) || special) ||
                    (controllerMode && (isDpad() || isStick() || isButton()));
        } else {
            return false;
        }
    }

    private boolean isButton() {
        if (contB != -1) {
            contButtonName = Xbox360Pad.bnames.get(contB);
            try {
                return State.controller.getButton(contB);

            } catch (NullPointerException e) {

                return false;
            }
        } else {
            return false;

        }
    }

    public void init() {
        isButton();
        isStick();
        isDpad();
    }

    private boolean isStick() {
        if (contA != -1) {
            switch (contA) {
                case 0: {//left y
                    if (axis == -1) {
                        contButtonName = "L UP";
                        return Xbox360Pad.getLUp();
                    } else if (axis == 1) {
                        contButtonName = "L DOWN";
                        return Xbox360Pad.getLDown();
                    }
                }
                case 1: {//left x
                    if (axis == -1) {
                        contButtonName = "L LEFT";
                        return Xbox360Pad.getLLeft();
                    } else if (axis == 1) {
                        contButtonName = "L RIGHT";
                        return Xbox360Pad.getLRight();
                    }
                }
                case 2: {//right y
                    if (axis == -1) {
                        contButtonName = "R UP";
                        return Xbox360Pad.getRUp();
                    } else if (axis == 1) {
                        contButtonName = "R DOWN";
                        return Xbox360Pad.getRDown();
                    }
                }
                case 3: {//right x
                    if (axis == -1) {
                        contButtonName = "R LEFT";
                        return Xbox360Pad.getRLeft();
                    } else if (axis == 1) {
                        contButtonName = "R RIGHT";
                        return Xbox360Pad.getRRRight();
                    }
                }
                case 4: {//lt
                    if (axis == 1) {
                        contButtonName = "LT";
                        return Xbox360Pad.getLT();
                    }

                }
                case 5: {//rt
                    contButtonName = "RT";
                    return Xbox360Pad.getRT();
                }
                case 8: {
                    contButtonName = "RT";
                    return Xbox360Pad.getRT();
                }
                case 9: {
                    contButtonName = "LT";
                    return Xbox360Pad.getLT();
                }
                default: {
                    return false;
                }
            }
        } else {
            return false;
        }

    }

    private boolean isDpad() {
        if (contD != null) {
            switch (contD) {
                case center: {
                    break;
                }
                case north: {
                    contButtonName = Xbox360Pad.dnames.get(0);
                    return Xbox360Pad.dup;
                }
                case south: {
                    contButtonName = Xbox360Pad.dnames.get(1);
                    return Xbox360Pad.ddown;
                }
                case east: {
                    contButtonName = Xbox360Pad.dnames.get(2);
                    return Xbox360Pad.dright;
                }
                case west: {
                    contButtonName = Xbox360Pad.dnames.get(3);
                    return Xbox360Pad.dleft;
                }
                case northEast:
                    return Xbox360Pad.dright && Xbox360Pad.ddown;
                case southEast:
                    return Xbox360Pad.ddown && Xbox360Pad.ddown;
                case northWest:
                    return Xbox360Pad.dleft && Xbox360Pad.dup;
                case southWest:
                    return Xbox360Pad.dright && Xbox360Pad.dup;
            }
        }
        return false;
    }

    public String print() {
        StringBuilder s = new StringBuilder("  " + name);
        while (s.length() < 70)
            s.append(" ");
        if (!controllerMode)
            s.append(Input.Keys.toString(keyboard));
        if (controllerMode)
            s.append(contButtonName);

        return s.toString();
    }
}
