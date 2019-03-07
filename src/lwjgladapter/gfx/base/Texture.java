package lwjgladapter.gfx.base;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glColor4f;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import lwjgladapter.GameWindowConstants;

public final class Texture {
	
	private static final float valueAccuracyThreshold = 0.01f;

	private int id;
	private int width;
	private int height;
	
	private Pixel[] pixels;
	
	private int hueShift;
	private float redValue;
	private float greenValue;
	private float blueValue;
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
	
	public void setPixelAt(int x, int y, Pixel pixel){
		pixels[y * width + x] = pixel;
	}
	
	public final int getHueShift() {
		return hueShift;
	}

	public final float getRedValue() {
		return redValue;
	}

	public final float getGreenValue() {
		return greenValue;
	}

	public final float getBlueValue() {
		return blueValue;
	}

	public final float getAlphaValue() {
		return alphaValue;
	}
	
	private boolean isChangeWithinTheshold(float valueA, float valueB){
		return Math.abs(valueA - valueB) < valueAccuracyThreshold;
	}

	public final void setHueShift(int hueDegrees){
		int realDegrees = hueDegrees % 360;
		if(realDegrees < 0) {realDegrees += 360;}
		if(hueShift != hueDegrees){
			hueShift = hueDegrees;
			loadIntoMemory();
		}
	}
	
	public final void setColorValues(float precentageRed, float precentageGreen, float precentageBlue, float precentageAlpha){
		boolean valuesChanged = false;
		if(!isChangeWithinTheshold(redValue, precentageRed)){
			redValue = Math.max(0f, Math.min(1f, precentageRed));
			valuesChanged = true;
		}
		if(!isChangeWithinTheshold(greenValue, precentageGreen)){
			greenValue = Math.max(0f, Math.min(1f, precentageGreen));
			valuesChanged = true;
		}
		if(!isChangeWithinTheshold(blueValue, precentageBlue)){
			blueValue = Math.max(0f, Math.min(1f, precentageBlue));
			valuesChanged = true;
		}
		if(!isChangeWithinTheshold(alphaValue, precentageAlpha)){
			alphaValue = Math.max(0f, Math.min(1f, precentageAlpha));
			valuesChanged = true;
		}
		if(valuesChanged){
			loadIntoMemory();
		}
	}
	
	public final void bind(){
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public final void loadIntoMemory(){
		ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(width * height * 4);
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
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
	
	public void draw(int x, int y){
		draw(x, y, 1f, 1f);
	}
	
	public void draw(int x, int y, float scaleX, float scaleY){
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
