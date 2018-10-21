package shapes1_5_5.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static shapes1_5_5.states.State.scr;

/**
 * Created by Chris Cavazos on 12/7/2017.
 */
public class Fonts {
    private static BitmapFont font = new BitmapFont();
    private static final BitmapFont[] fonts=new BitmapFont[20];
    private static GlyphLayout l = new GlyphLayout();

    public static void initFonts(){
        for(int i=0;i<20;i++){
            fonts[i]=makeFont((i*5)+4);
        }

    }
    public static BitmapFont getFont() {
        return font;
    }

    private static BitmapFont makeFont(int i){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = i;
        if(parameter.size<8)parameter.size=8;
        return generator.generateFont(parameter);
    }
    public static void setFontSize(int i) {
      font=fonts[i-1];
    }

    public static float getWidth(String s) {
        l.setText(font,s);
        return l.width;

    }

    public static float centerString(String s){
       return  (scr.size.x/2) - (Fonts.getWidth(s)/2);

    }

    public static float getHeight(String s) {
        l.setText(font,s);
        return l.height;

    }

    public static void resetAlpha() {
        font.getColor().a=1;
    }
}
