package com.quadx.wgame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import shapes1_5_5.physics.Body;
import shapes1_5_5.physics.Physics;
import shapes1_5_5.shapes.ShapeRendererExt;
import shapes1_5_5.timers.Delta;

import static com.quadx.wgame.state.GameState.world;
import static shapes1_5_5.timers.Time.SECOND;

/**
 * Created by Chris Cavazos on 10/20/2018.
 */
public class Monster {
    public boolean dead = false;
    Body body = new Body();
    float a = 90;
    float h = 0;
    Delta dPlant = new Delta(1 * SECOND);
    Plant target=null;
    boolean stop =false;

    Vector2 tpos = new Vector2();
    TextureRegion texture = new TextureRegion();

    public Monster() {
        body.setBoundingBox(new Vector2(0, 0), new Vector2(32, 32));
        body.setPos(Physics.getRadialVector(world.r-15, 180).add(world.body.pos()));
        dPlant.finish();
        target=  world.getRandPlant(this);


        texture = Game.atlas.findRegion("en0");
        texture = texture.split(32,32)[0][0];


    }

    public Monster(float ang) {
        this();
        a=ang;
    }

    public void update(float dt) {
        if(target !=null && !target.dead) {
            if(!stop) {

                if(!target.chopping) {
                    float angd = (a - target.ang + 180 + 360) % 360 - 180;
                    if (angd >= 0) {
                        move(-.9f);
                    } else {
                        move(.9f);
                    }

                    if (target.dead)
                        target = null;
                }
            }
        }else{

           // move((sun.ang % 360) - (a % 360) < 1 ? -1 : 1);

            target=world.getRandPlant(this);
        }

        a%=360;
    }

    boolean flip= false;

    public void move(float ang) {
            a += ang;
            flip = ang < 0;
            setPos(new Vector2(world.r - 15, a));
    }

    void setPos(Vector2 rad){
        Vector2 v=Physics.getRadialVector(rad.x,rad.y).add(world.body.pos());
        body.setPos(v);
        tpos = Physics.getRadialVector(rad.x, rad.y+(3*(flip?-1:3))).add(world.body.pos());

    }

    public void render(ShapeRendererExt sr) {
    }

    public void renderSB(SpriteBatch sb){
        sb.draw(texture,tpos.x,tpos.y,0,0,32,32,2*(flip?-1:1),2,a-90);
    }

    public void collision(Monster m) {
/*        if(!m.equals(this) && isInBound(m.a,a,5)) {
            float angd = (m.a - a + 180 + 360) % 360 - 180;
            if (angd>= 0) {
                move(-5);
            } else {
                move(5);
            }

        }*/

    }
}
