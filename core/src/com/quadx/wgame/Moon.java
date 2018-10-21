package com.quadx.wgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import shapes1_5_5.physics.Body;
import shapes1_5_5.physics.Physics;
import shapes1_5_5.shapes.ShapeRendererExt;

import static com.quadx.wgame.state.GameState.world;

/**
 * Created by Chris Cavazos on 10/20/2018.
 */
public class Moon {
    Body body;
    public float ang = 0;
    Vector2 tpos = new Vector2();
    TextureRegion texture;


    public Moon() {
        body = new Body();
        body.setBoundingBox(Physics.getRadialVector(world.r + 150, ang).add(world.body.pos())
                , new Vector2(40, 40));
        texture = Game.atlas.findRegion("moon");
        texture = texture.split(32,32)[0][0];

    }

    public void update(float dt){
        ang+=.2;
        setPos(new Vector2(world.r, ang));
    }

    void setPos(Vector2 rad){
        Vector2 v=Physics.getRadialVector(rad.x+150 ,rad.y+10).add(world.body.pos());
        body.setBoundingBox(v,new Vector2(40,40));
        tpos = Physics.getRadialVector(rad.x+150, rad.y+17).add(world.body.pos());

    }

    public void render(ShapeRendererExt sr) {
        sr.setColor(Color.LIGHT_GRAY);
        sr.rect(body.getBoundingBox(), ang);
    }

    public void renderSB(SpriteBatch sb){
        sb.draw(texture,tpos.x,tpos.y,0,0,32,32,2,2,ang-90);
    }

}
