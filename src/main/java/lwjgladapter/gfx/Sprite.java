package lwjgladapter.gfx;

import lwjgladapter.gfx.base.Pixel;
import lwjgladapter.gfx.base.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.glGenTextures;

public class Sprite {

    private Texture texture;

    public Sprite(String filename) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(filename));
            int width = bi.getWidth();
            int height = bi.getHeight();

            int[] pixels_raw = new int[width * height];
            pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);

            texture = new Texture(glGenTextures(), width, height);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    texture.setPixelAt(x, y, new Pixel(pixels_raw[y * width + x]));
                }
            }

            texture.loadIntoMemory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final int getHueShift() {
        return texture.getHueShift();
    }

    public final float getRedValue() {
        return texture.getRedValue();
    }

    public final float getGreenValue() {
        return texture.getGreenValue();
    }

    public final float getBlueValue() {
        return texture.getBlueValue();
    }

    public final float getAlphaValue() {
        return texture.getAlphaValue();
    }

    public final void setHueShift(int hueDegrees) {
        texture.setHueShift(hueDegrees);
    }

    public final void setColorValues(float precentageRed, float precentageGreen, float precentageBlue, float precentageAlpha) {
        texture.setColorValues(precentageRed, precentageGreen, precentageBlue, precentageAlpha);
    }

    public void draw(int x, int y) {
        draw(x, y, 1f, 1f);
    }

    public void draw(int x, int y, float scaleX, float scaleY) {
        texture.draw(x, y, scaleX, scaleY);
    }
}
