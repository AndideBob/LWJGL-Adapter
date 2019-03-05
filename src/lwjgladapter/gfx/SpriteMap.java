package lwjgladapter.gfx;

import static org.lwjgl.opengl.GL11.glGenTextures;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import lwjgladapter.gfx.base.Pixel;
import lwjgladapter.gfx.base.Texture;
import lwjgladapter.logging.Logger;

public class SpriteMap {

	private int tileAmount;
	private Map<Integer, Texture> textureMap;
	private int width;
	private int height;

	public SpriteMap(String filename, int tileSizeX, int tileSizeY) {
		BufferedImage bi;
		textureMap = new HashMap<Integer, Texture>();
		try{
			bi = ImageIO.read(new File(filename));
			int tilesX = (int)Math.floor(bi.getWidth() / tileSizeX);
			int tilesY = (int)Math.floor(bi.getHeight() / tileSizeY);
			tileAmount = tilesX * tilesY;
			width = tileSizeX;
			height = tileSizeY;
			
			int[] pixels_raw = new int[bi.getWidth() * bi.getHeight()];
			pixels_raw = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());
			
			for(int counter = 0; counter < tileAmount; counter++){
				
				int posX = (counter % tilesX) * width;
				int posY = ((int)Math.floor(counter / tilesX)) * height;
				
				Texture texture = new Texture(glGenTextures(), width, height);
				
				
				int internalY = 0;
				for(int y = posY; y < posY + height; y++){
					int internalX = 0;
					for(int x = posX; x < posX + width; x++){
						texture.setPixelAt(internalX, internalY, new Pixel(pixels_raw[y * bi.getWidth() + x]));
						internalX++;
					}
					internalY++;
				}
				
				texture.loadIntoMemory();
				
				textureMap.put(counter, texture);
			}
		}
		catch(IOException e){
			Logger.logError(e);
		}
	}
	
	public final int getHueShift() {
		return textureMap.get(0).getHueShift();
	}

	public final float getRedValue() {
		return textureMap.get(0).getRedValue();
	}

	public final float getGreenValue() {
		return textureMap.get(0).getGreenValue();
	}

	public final float getBlueValue() {
		return textureMap.get(0).getBlueValue();
	}

	public final float getAlphaValue() {
		return textureMap.get(0).getAlphaValue();
	}

	public final void setHueShift(int hueDegrees){
		for(Texture t : textureMap.values()){
			t.setHueShift(hueDegrees);
		}
	}
	
	public final void setColorValues(float precentageRed, float precentageGreen, float precentageBlue, float precentageAlpha){
		for(Texture t : textureMap.values()){
			t.setColorValues(precentageRed, precentageGreen, precentageBlue, precentageAlpha);
		}
	}
	
	public void draw(int tile, int x, int y){
		draw(tile, x, y, 1f, 1f);
	}
		
	public boolean draw(int tile, int x, int y, float scaleX, float scaleY){
		Texture texture = textureMap.get(tile);
		if(texture != null){
			texture.draw(x, y, scaleX, scaleY);
			return true;
		}
		
		return false;
	}

}
