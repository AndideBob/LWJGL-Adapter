package lwjgladapter.gfx.base;

import lombok.Getter;
import lwjgladapter.GameWindowConstants;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public final class Texture {

    private static final float valueAccuracyThreshold = 0.01f;

    private final int id;
    private final int width;
    private final int height;

    private final Pixel[] pixels;

    @Getter
    private int hueShift;
    @Getter
    private float redValue;
    @Getter
    private float greenValue;
    @Getter
    private float blueValue;
    @Getter
    private float alphaValue;

    public Texture(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
        pixels = new Pixel[width * height];
        hueShift = 0;
        redValue = 1f;
        greenValue = 1f;
        blueValue = 1f;
        alphaValue = 1f;
    }

    public void setPixelAt(int x, int y, Pixel pixel) {
        pixels[y * width + x] = pixel;
    }

    private boolean isChangeWithinThreshold(float valueA, float valueB) {
        return Math.abs(valueA - valueB) >= valueAccuracyThreshold;
    }

    public final void setHueShift(int hueDegrees) {
        int realDegrees = hueDegrees % 360;
        if (realDegrees < 0) {
            realDegrees += 360;
        }
        if (hueShift != realDegrees) {
            hueShift = realDegrees;
            loadIntoMemory();
        }
    }

    public void setColorValues(float percentageRed, float percentageGreen, float percentageBlue, float percentageAlpha) {
        boolean valuesChanged = false;
        if (isChangeWithinThreshold(redValue, percentageRed)) {
            redValue = Math.max(0f, Math.min(1f, percentageRed));
            valuesChanged = true;
        }
        if (isChangeWithinThreshold(greenValue, percentageGreen)) {
            greenValue = Math.max(0f, Math.min(1f, percentageGreen));
            valuesChanged = true;
        }
        if (isChangeWithinThreshold(blueValue, percentageBlue)) {
            blueValue = Math.max(0f, Math.min(1f, percentageBlue));
            valuesChanged = true;
        }
        if (isChangeWithinThreshold(alphaValue, percentageAlpha)) {
            alphaValue = Math.max(0f, Math.min(1f, percentageAlpha));
            valuesChanged = true;
        }
        if (valuesChanged) {
            loadIntoMemory();
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void loadIntoMemory() {
        ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(width * height * 4);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel p = pixels[y * width + x];
                p.setHueShift(hueShift);
                pixelBuffer.put(p.getCurrentRed()); //R
                pixelBuffer.put(p.getCurrentGreen());  //G
                pixelBuffer.put(p.getCurrentBlue());       //B
                pixelBuffer.put(p.getCurrentAlpha()); //A
            }
        }
        pixelBuffer.flip();

        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelBuffer);
    }

    public void draw(int x, int y) {
        draw(x, y, 1f, 1f);
    }

    public void draw(int x, int y, float scaleX, float scaleY) {
        float actualX = x * GameWindowConstants.getSCALE_FACTOR_X();
        float actualWidth = width * GameWindowConstants.getSCALE_FACTOR_X() * scaleX;
        float actualY = y * GameWindowConstants.getSCALE_FACTOR_Y();
        float actualHeight = height * GameWindowConstants.getSCALE_FACTOR_Y() * scaleY;

        float minX = (2f * actualX) / GameWindowConstants.getSCREEN_WIDTH() - 1f;
        float maxX = (2f * (actualX + actualWidth)) / GameWindowConstants.getSCREEN_WIDTH() - 1f;
        float minY = (2f * actualY) / GameWindowConstants.getSCREEN_HEIGHT() - 1f;
        float maxY = (2f * (actualY + actualHeight)) / GameWindowConstants.getSCREEN_HEIGHT() - 1f;

        bind();
        glColor4f(getRedValue(), getGreenValue(), getBlueValue(), getAlphaValue());
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(minX, maxY);

        glTexCoord2f(0, 1);
        glVertex2f(minX, minY);

        glTexCoord2f(1, 1);
        glVertex2f(maxX, minY);

        glTexCoord2f(1, 0);
        glVertex2f(maxX, maxY);
        glEnd();
    }
}
