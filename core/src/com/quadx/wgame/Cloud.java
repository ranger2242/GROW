package com.quadx.wgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import shapes1_5_5.physics.Body;
import shapes1_5_5.physics.Physics;
import shapes1_5_5.shapes.ShapeRendererExt;
import shapes1_5_5.timers.Delta;

import static com.quadx.wgame.state.GameState.world;
import static shapes1_5_5.timers.Time.MINUTE;

/**
 * Created by Chris Cavazos on 10/20/2018.
 */
public class Cloud {
    Body body;
    public float ang = 90;
    Delta dOn =  new Delta(.5f*MINUTE);
    Delta dOff =  new Delta(1*MINUTE);
    boolean on = true;
    ParticleEffect effect;


    public Cloud() {
        effect = new ParticleEffect();
        body = new Body();
        body.setBoundingBox(Physics.getRadialVector(world.r + 70, ang).add(world.body.pos())
                , new Vector2(40, 200));
        effect.load(Gdx.files.internal("rain.pt"), new TextureAtlas(Gdx.files.internal("pack/particle.atlas")));
        effect.start();

    }

    public void update(float dt){
        if(on)
            dOn.update(dt);
        else
            dOff.update(dt);

        if(on) {
            ang += .08;
            body.setBoundingBox(Physics.getRadialVector(world.r + 70, ang).add(world.body.pos())
                    , new Vector2(40, 200));

            ParticleEmitter.ScaledNumericValue angle = effect.getEmitters().first().getAngle();

            /* find angle property and adjust that by letting the min, max of low and high span their current size around your angle */

            float r=200;

            float p=165;
            float s=(float) (r*Math.sin(Math.toRadians(-ang+p)));
            float c=(float) (r*Math.cos(Math.toRadians(-ang+p)));

            Vector2 v=Physics.getRadialVector(world.r + 80, ang+30).add(world.body.pos());
            effect.setPosition(v.x,v.y);

            effect.getEmitters().first().getSpawnHeight().setHigh(c);
            effect.getEmitters().first().getSpawnWidth().setHigh(s);
            effect.getEmitters().first().getSpawnHeight().setLow(c);
            effect.getEmitters().first().getSpawnWidth().setLow(s);

            angle.setHigh(ang+180);
            angle.setLow (ang+180);

        }else{
            effect.dispose();
        }
        if(dOn.isDone() || dOff.isDone()){
            on=!on;
            dOn.reset();
            dOff.reset();
        }
    }

    public void renderSB(SpriteBatch sb){
        if(on)
        effect.draw(sb, Gdx.graphics.getDeltaTime());


    }

    public void render(ShapeRendererExt sr) {
        if(on) {
            sr.setColor(new Color(.4f, .4f, .4f, .4f));
            sr.rect(body.getBoundingBox(), ang + 15);
        }
    }

}
