package com.quadx.wgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.quadx.wgame.state.GameState;
import shapes1_5_5.gui.Fonts;
import shapes1_5_5.physics.Body;
import shapes1_5_5.physics.Physics;
import shapes1_5_5.shapes.ShapeRendererExt;
import shapes1_5_5.timers.Delta;

import static com.quadx.wgame.state.GameState.world;
import static shapes1_5_5.states.State.scr;
import static shapes1_5_5.timers.Time.SECOND;
import static shapes1_5_5.timers.Time.ft;

/**
 * Created by Chris Cavazos on 10/20/2018.
 */
public class Player {
    Body body = new Body();
    float a = 0;
    float h = 0;
    Delta dPlant = new Delta(8f*ft);
    boolean jumping = false;
    Delta dJump = new Delta(.15f * SECOND);
    float jr=0;
    public int seed= 3;
    boolean falling= false;
    Vector2 tpos = new Vector2();
    boolean flip= false;


    TextureRegion texture = new TextureRegion();


    public Player() {
        body.setBoundingBox(new Vector2(0, 0), new Vector2(32, 32));
        setPos(new Vector2(world.r-20,0));
        dPlant.finish();

        texture = Game.atlas.findRegion("friend");
        texture =texture.split(32,32)[0][0];

    }

    public void collision(Monster m){
        if(GameState.isInBound(m.a,a,5) ){
            if(falling)
                m.dead=true;
            else{
                if(!jumping) {
                    float angd = (a - m.a + 180 + 360) % 360 - 180;
                    move(angd < 0 ? -5 : 5);
                }
            }
        }
    }

    public void update(float dt) {
        dPlant.update(dt);
        if(jumping){
            dJump.update(dt);
            if(dJump.isDone()){
                jr-=world.acc*(dt*2);
                falling=true;
                if(jr<2) {
                    jumping = false;
                    falling=false;

                    jr=0;
                    dJump.reset();
                }
            }else
                jr+=world.acc*(dt*2);
            setPos(new Vector2(world.r+jr -20, a));
        }
        a%=360;

    }

    void setPos(Vector2 rad){
        Vector2 v=Physics.getRadialVector(rad.x,rad.y).add(world.body.pos());
        body.setPos(v);
        tpos = Physics.getRadialVector(rad.x, rad.y+(flip?-3:  10)).add(world.body.pos());

    }

    public void move(float ang) {
        a += ang;
        flip=(ang>0);
        setPos(new Vector2(world.r-20,a));
    }

    public void render(ShapeRendererExt sr) {
        sr.setColor(Color.BLUE);
        sr.rect(body.getBoundingBox(), a);
    }

    public void plant() {
        if (dPlant.isDone() && seed>0) {
            if (world.addPlant(new Plant(a))) {
                dPlant.reset();
                seed--;
                GameState.score += 5;
            }
        }
    }

    public void jump() {
        jumping=true;
    }

    public void addSeed(int i) {
        seed+=i;

    }

    public void renderSB(SpriteBatch sb) {
        Fonts.setFontSize(4);
        Fonts.getFont().draw(sb,"SEEDS: "+seed,10,scr.size.y-10);
        sb.draw(texture,tpos.x,tpos.y,0,0,32,32,flip?-2:2,2,a-90);

    }
}
