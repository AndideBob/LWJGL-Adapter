package lwjgladapter.gfx.base;

public class Pixel {

	private byte originalRed;
	private byte originalGreen;
	private byte originalBlue;
	private byte originalAlpha;
	
	private byte currentRed;
	private byte currentGreen;
	private byte currentBlue;
	private byte currentAlpha;
	
	private int currentHue;
	private float saturation;
	private float luminance;
	
	public Pixel(int information) {
		originalRed = ((byte)((information >> 16) & 0xFF)); //R
		originalGreen = ((byte)((information >> 8) & 0xFF));  //G
		originalBlue = ((byte)((information) & 0xFF));       //B
		originalAlpha = ((byte)((information >> 24) & 0xFF)); //A
		currentRed = originalRed;
		currentGreen = originalGreen;
		currentBlue = originalBlue;
		currentAlpha = originalAlpha;
		calculateHSL();
	}
	
	private void calculateHSL(){
		float r = currentRed / 255f;
		float g = currentGreen / 255f;
		float b = currentBlue / 255f;
		float max = Math.max(r, Math.max(g, b));
		float min = Math.min(r, Math.min(g, b));
		boolean redIsMax = r >= g && r >= b;
		boolean greenIsMax = g >= r && g >= b;
		boolean blueIsMax = b >= r && b >= g;
		luminance = (min + max) / 2f;
		if(luminance <= 0.5f){
			saturation = (max - min) / (max + min);
		}
		else if (min >= 1f && max >= 1f){
			saturation = 1f;
		}
		else{
			saturation = (max - min) / (2f - max - min);
		}
		float h = 0f;
		if(max != min){
			if(redIsMax){
				h = (g-b) / (max - min);
			}
			else if(greenIsMax){
				h = 2f + (b-r) / (max - min);
			}
			else if(blueIsMax){
				h = 4f + (r-g) / (max - min);
			}
		}
		currentHue = (int) Math.floor(h * 60f);
	}
	
	public void setValues(float precentageRed, float precentageGreen, float precentageBlue, float precentageAlpha, int hueShift){
		setColorValues(precentageRed, precentageGreen, precentageBlue, precentageAlpha);
		calculateHSL();
		setHueShift(hueShift);
	}
	
	private void setHueShift(int shift){
		int realDegrees = shift % 360;
		if(realDegrees < 0) {realDegrees += 360;}
		if(realDegrees != 0){
			int actualHue = currentHue + realDegrees;
			if(saturation <= 0f){
				currentRed = (byte)Math.round(luminance * 255f);
				currentGreen = (byte)Math.round(luminance * 255f);
				currentBlue = (byte)Math.round(luminance * 255f);
				return;
			}
			float temp_1 = (1f - Math.abs(2 * luminance - 1)) * saturation;
			float temp_2 = temp_1 * (1f - Math.abs((Math.round(actualHue / 60) % 2) - 1f));
			float r = 0f;
			float g = 0f;
			float b = 0f;
			if(actualHue < 60){
				r = temp_1;
				g = temp_2;
			}
			else if(actualHue < 120){
				r = temp_2;
				g = temp_1;
			}
			else if(actualHue < 180){
				g = temp_1;
				b = temp_2;
			}
			else if(actualHue < 240){
				g = temp_2;
				b = temp_1;
			}
			else if(actualHue < 300){
				r = temp_2;
				b = temp_1;
			}
			else{
				r = temp_1;
				b = temp_2;
			}
			float temp_3 = luminance - (temp_1 / 2);
			currentRed = (byte)Math.min(0f, Math.max(Math.round((r + temp_3) * 255f), 255f));
			currentGreen = (byte)Math.min(0f, Math.max(Math.round((g + temp_3) * 255f), 255f));
			currentBlue = (byte)Math.min(0f, Math.max(Math.round((b + temp_3) * 255f), 255f));
		}
	}
	
	private void setColorValues(float precentageRed, float precentageGreen, float precentageBlue, float precentageAlpha){
		currentRed = (byte) (Math.floor(originalRed * precentageRed));
		currentGreen = (byte) (Math.floor(originalGreen * precentageGreen));
		currentBlue = (byte) (Math.floor(originalBlue * precentageBlue));
		currentAlpha = (byte) (Math.floor(originalAlpha * precentageAlpha));
	}

	public byte getOriginalRed() {
		return originalRed;
	}

	public byte getOriginalGreen() {
		return originalGreen;
	}

	public byte getOriginalBlue() {
		return originalBlue;
	}

	public byte getOriginalAlpha() {
		return originalAlpha;
	}

	public byte getCurrentRed() {
		return currentRed;
	}

	public byte getCurrentGreen() {
		return currentGreen;
	}

	public byte getCurrentBlue() {
		return currentBlue;
	}

	public byte getCurrentAlpha() {
		return currentAlpha;
	}

}
