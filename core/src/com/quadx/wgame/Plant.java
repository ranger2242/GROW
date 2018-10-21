package com.quadx.wgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.quadx.wgame.state.GameState;
import shapes1_5_5.physics.Body;
import shapes1_5_5.physics.Physics;
import shapes1_5_5.shapes.Rect;
import shapes1_5_5.shapes.ShapeRendererExt;
import shapes1_5_5.timers.Delta;

import static com.quadx.wgame.state.GameState.*;
import static shapes1_5_5.physics.EMath.rn;
import static shapes1_5_5.timers.Time.SECOND;

/**
 * Created by Chris Cavazos on 10/20/2018.
 */
public class Plant {
     Body body;
     float ang = 0;
     int size = 1;
     private Delta dGrow = new Delta(2 * SECOND);
     private Delta dEat = new Delta(1 * SECOND);
     private Delta dChop = new Delta(2 * SECOND);

     boolean dead = false;
     boolean shaded = false;
     boolean attacked= false;
     boolean chopping = false;
     boolean flip = false;
     int max= 4;

     TextureRegion texture;
     Vector2 tpos;
     String plSize;

    public Plant(float ang) {
        plSize = "pl1";
        flip = rn.nextBoolean();
        texture = Game.atlas.findRegion(plSize);

        body = new Body();
        setPos(new Vector2(world.r-10-rn.nextInt(20), ang));
        this.ang = ang%360;
    }

    void setPos(Vector2 rad){
        Vector2 v=Physics.getRadialVector(rad.x,rad.y).add(world.body.pos());
        body.setBoundingBox(v, new Vector2(15,5));
        tpos = Physics.getRadialVector(rad.x, rad.y+ (6*(flip?-1:1))).add(world.body.pos());

    }

    void render(ShapeRendererExt sr) {
//        if(!shaded)
            sr.setColor(Color.GREEN);
//        else
//            sr.setColor(Color.RED);
//        if(size==max){
//            sr.setColor(Color.YELLOW);
//
//        }

        if(chopping && size == max)
            sr.rect(Rect.rect(Physics.getRadialVector(world.r+100, ang).add(world.body.pos()),
                    new Vector2(30*(1-dChop.percent()),2)), ang+90);
       // sr.rect(body.getBoundingBox(), ang);
    }

    public void renderSB(SpriteBatch sb) {
        sb.draw(texture,tpos.x,tpos.y,0,0,32,32,flip?-2:2,2,ang-90);

    }

    public void collision(Monster m){
        if (body.getBoundingBox().overlaps(m.body.getBoundingBox()) &&m.target!=null
                &&  m.target.equals(this)) {
            getEaten(Gdx.graphics.getDeltaTime());
            m.stop=true;
            attacked=true;
        }else
            m.stop = false;
            attacked=false;
    }
    public void collision( Player p1){
        if(GameState.isInBound(ang,p1.a+2,3)){
            chopping=true;
        }else{
            chopping=false;
        }
    }
    public void update(float dt) {
        float angd = (ang - sun.ang + 180 + 360) % 360 - 180;
        shaded =  angd<90 && angd>-90;


        if(!shaded)
            dGrow.update(dt);

        if(cloud.on &&  isInBound( ang,cloud.ang+10, 15)) {
            dGrow.finish();
        }

        if (!attacked && dGrow.isDone() && size < max) {
            dGrow.reset();
            size++;

        }

        if(chopping && size>max-1){
            dChop.update(dt);
            if(dChop.isDone()){
                dead=true;
                p1.addSeed(rn.nextInt(2)+1);
            }
        }


        fixSize();
        System.out.println(size);

        if(size>0){
            plSize = "pl" + size;
            texture = Game.atlas.findRegion(plSize);
        }
    }


    private void fixSize() {
        body.setDim(new Vector2(size(), 5));

    }

    public void getEaten(float dt) {
        dEat.update(dt);
        if (dEat.isDone()) {
            dEat.reset();
            dGrow.reset();
            size--;
        }
        if (size < 1) {
            dead = true;
        }
    }

    float size() {
        return (15 * (size));
    }

}
