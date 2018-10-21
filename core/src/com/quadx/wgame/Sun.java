package com.quadx.wgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import shapes1_5_5.physics.Body;
import shapes1_5_5.physics.Physics;
import shapes1_5_5.shapes.ShapeRendererExt;

import static com.quadx.wgame.state.GameState.world;

/**
 * Created by Chris Cavazos on 10/20/2018.
 */
public class Sun {
    Body body;
    float ang = 0;
    Rectangle rain;
    int x= 2000;
    public int day = 0;

    TextureRegion texture;
    Vector2 tpos;

    public Sun() {
        texture = Game.atlas.findRegion("sun");
        tpos = new Vector2();

        body = new Body();
        Vector2 pos = Physics.getRadialVector(world.r + 150, ang+180).add(world.body.pos());
        body.setBoundingBox(pos, new Vector2(40, 40));
        updateShade();
    }

    void updateShade(){
        Vector2 pos2 = Physics.getRadialVector(world.r + 1000, ang+270).add(world.body.pos());
        rain = new Rectangle(pos2.x,pos2.y,x,x);
    }
    public void update(float dt){
        ang+=.2 ;
        ang%=360;
        if(ang<=.3)
            day++;
       setPos(new Vector2(world.r, ang));
        updateShade();
    }

    void setPos(Vector2 rad){
        Vector2 v=Physics.getRadialVector(rad.x+150 ,rad.y+180).add(world.body.pos());
        body.setBoundingBox(v,new Vector2(40,40));
        tpos = Physics.getRadialVector(rad.x+150, rad.y+173).add(world.body.pos());

    }

    public void render(ShapeRendererExt sr) {
        sr.setColor(new Color(0,0,0,.8f));
        sr.rect(rain, ang);
//        sr.setColor(Color.GOLD);
//
//        sr.rect(body.getBoundingBox(), ang);

    }

    public void renderSB(SpriteBatch sb){
        sb.draw(texture,tpos.x,tpos.y,0,0,32,32,2,2,ang-90);
    }

}
