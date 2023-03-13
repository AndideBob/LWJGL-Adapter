package lwjgladapter.gfx.base;

import lombok.Getter;

@Getter
public class Pixel {

    private final byte originalRed;
    private final byte originalGreen;
    private final byte originalBlue;
    private final byte originalAlpha;

    private byte currentRed;
    private byte currentGreen;
    private byte currentBlue;
    private byte currentAlpha;

    private int currentHue;
    private float saturation;
    private float luminance;

    public Pixel(int information) {
        originalRed = ((byte) ((information >> 16) & 0xFF)); //R
        originalGreen = ((byte) ((information >> 8) & 0xFF));  //G
        originalBlue = ((byte) ((information) & 0xFF));       //B
        originalAlpha = ((byte) ((information >> 24) & 0xFF)); //A
        currentRed = originalRed;
        currentGreen = originalGreen;
        currentBlue = originalBlue;
        currentAlpha = originalAlpha;
        calculateHSL();
    }

    private void calculateHSL() {
        float r = fromByte(currentRed) / 255f;
        float g = fromByte(currentGreen) / 255f;
        float b = fromByte(currentBlue) / 255f;
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        boolean redIsMax = r >= g && r >= b;
        boolean greenIsMax = g >= r && g >= b;
        boolean blueIsMax = b >= r && b >= g;
        luminance = (min + max) / 2f;
        if (luminance <= 0.5f) {
            saturation = (max - min) / (max + min);
        } else if (min >= 1f && max >= 1f) {
            saturation = 1f;
        } else {
            saturation = (max - min) / (2f - max - min);
        }
        float h = 0f;
        if (max != min) {
            if (redIsMax) {
                h = (g - b) / (max - min);
            } else if (greenIsMax) {
                h = 2f + (b - r) / (max - min);
            } else if (blueIsMax) {
                h = 4f + (r - g) / (max - min);
            }
        }
        currentHue = (int) Math.floor(h * 60f);
    }

    public void setHueShift(int shift) {
        calculateHSL();
        int realDegrees = shift % 360;
        if (realDegrees < 0) {
            realDegrees += 360;
        }
        if (realDegrees != 0) {
            int actualHue = (currentHue + realDegrees) % 360;
            if (saturation <= 0f) {
                currentRed = toByte(Math.round(luminance * 255f));
                currentGreen = toByte(Math.round(luminance * 255f));
                currentBlue = toByte(Math.round(luminance * 255f));
                return;
            }
            double temp_1 = 0f;
            if (luminance <= 0.5f) {
                temp_1 = luminance * (1f + saturation);
            } else {
                temp_1 = luminance + saturation - (luminance * saturation);
            }
            double temp_2 = 2f * luminance - temp_1;
            double hue = actualHue / 360f;
            double temp_r = hue + 0.333333333f;
            double temp_g = hue;
            double temp_b = hue - 0.333333333f;
            double r = convertTempColorValue(temp_1, temp_2, temp_r);
            double g = convertTempColorValue(temp_1, temp_2, temp_g);
            double b = convertTempColorValue(temp_1, temp_2, temp_b);
            currentRed = toByte(Math.max(0f, Math.min(Math.round(r * 255f), 255f)));
            currentGreen = toByte(Math.max(0f, Math.min(Math.round(g * 255f), 255f)));
            currentBlue = toByte(Math.max(0f, Math.min(Math.round(b * 255f), 255f)));
        }
    }

    private double convertTempColorValue(double temp_1, double temp_2, double temp_color) {
        double color_val = (temp_color < 0) ? (temp_color + 1f) : ((temp_color > 1f) ? (temp_color - 1f) : temp_color);
        double value = 0f;
        if (6f * color_val < 1) {
            value = temp_2 + (temp_1 - temp_2) * 6f * color_val;
        } else if (2f * color_val < 1) {
            value = temp_1;
        } else if (3f * color_val < 2) {
            value = temp_2 + (temp_1 - temp_2) * (0.66666666f - color_val) * 6f;
        } else {
            value = temp_2;
        }
        return value;
    }

    private static int fromByte(byte value) {
        int result = value;
        return result + 128;
    }

    private static byte toByte(int value) {
        return (byte) value;
    }

    private static byte toByte(float value) {
        float result = value - 128f;
        return (byte) Math.floor(result);
    }
}
