package lwjgladapter.gfx.utils;

import lwjgladapter.gfx.base.Texture;
import lwjgladapter.maths.shapes.RectInt;

public class TextureUtils {

    public static RectInt getBoundariesForTexture(Texture texture, int x, int y, float scaleX, float scaleY) {
        float width = texture.getWidth() * scaleX;
        float height = texture.getHeight() * scaleY;
        int widthInt = width > 0 ? (int) Math.ceil(width) : (int) Math.floor(width);
        int heightInt = height > 0 ? (int) Math.ceil(height) : (int) Math.floor(height);
        return new RectInt(Math.min(x, x + widthInt), Math.min(y, y + heightInt), Math.abs(widthInt), Math.abs(heightInt));
    }
}
