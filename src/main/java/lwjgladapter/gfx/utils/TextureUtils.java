package lwjgladapter.gfx.utils;

import lwjgladapter.gfx.base.Texture;
import lwjgladapter.maths.shapes.Rect;

public class TextureUtils {

    public static Rect getBoundariesForTexture(Texture texture, int x, int y, float scaleX, float scaleY){
        return new Rect(x, y, (int) Math.ceil(texture.getWidth() * scaleX), (int) Math.ceil(texture.getHeight() * scaleY));
    }
}
