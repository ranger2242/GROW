package shapes1_5_5.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import shapes1_5_5.files.FilePaths;
import shapes1_5_5.states.State;

import static shapes1_5_5.states.State.scr;

/**
 * Created by range_000 on 1/5/2017.
 */
public class Text {

    public int size;
    public Color c;
    public Vector2 pos;
    public String text;

    public Text(String s,Vector2 v,Color c, int size){
        text=s;
        pos=v;
        this.c=c;
        this.size=size;
    }
    public Text(String s,Vector2 v){
        this(s,v,Color.WHITE,1);
    }


    //STATICS=============================================
    public static final BitmapFont[] fonts = new BitmapFont[6];
    public static BitmapFont font;
    static String fontFilePath ="fonts\\prstart.ttf";
    static FileHandle file = Gdx.files.internal(FilePaths.getPath(fontFilePath));
    static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
    static FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

    public Text(String highscores, float i, float v) {
        this(highscores,new Vector2(i,v));
    }

    public static void generateFonts(){
        for(int i=0;i<6;i++)
            fonts[i] = createFont(8+(i*2));
        setFontSize(5);
    }


    private static BitmapFont createFont(int x) {
        parameter.size = x;
        return generator.generateFont(parameter);
    }
    public static float strWidth(String s){
        CharSequence cs=s;
        GlyphLayout gl = new GlyphLayout(getFont(),cs);
        return gl.width;
    }
    public static float centerString(String s){
        return (State.getView().x+scr.size.x/2)- (strWidth(s)/2);
    }

    public static float[] fitLineToWord(String s){
        //x1,y1,x2,y2
        float[] arr=new float[8];
        arr[0]= -10;
        arr[1]=-15;
        arr[2]=strWidth(s)+20;
        arr[3]=-15;
        arr[4]= -10;
        arr[5]=-12;
        arr[6]=strWidth(s)+35;
        arr[7]=-12;
        return arr;
    }

    public static void setFontSize(int x) {
        font = fonts[x];

    }

    public static BitmapFont getFont() {
        return font;
    }

    public static void resetFont() {
        for(BitmapFont f: fonts){
            Color c= new Color(1,1,1,1);
            f.setColor(c);
        }
    }
}
