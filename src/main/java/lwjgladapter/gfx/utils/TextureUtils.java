package lwjgladapter.gfx.utils;

import lwjgladapter.gfx.base.Texture;
import lwjgladapter.maths.shapes.RectInt;

public class TextureUtils {

    public static RectInt getBoundariesForTexture(Texture texture, int x, int y, float scaleX, float scaleY) {
        return new RectInt(x, y, (int) Math.ceil(texture.getWidth() * scaleX), (int) Math.ceil(texture.getHeight() * scaleY));
    }
}
