package lwjgladapter.gfx;

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
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import lwjgladapter.GameWindowConstants;
import lwjgladapter.logging.Logger;

public class TileMap {

	private int tileAmount;
	private Map<Integer, Integer> idMap;
	private int width;
	private int height;

	public TileMap(String filename, int tileSizeX, int tileSizeY) {
		BufferedImage bi;
		idMap = new HashMap<Integer, Integer>();
		try{
			bi = ImageIO.read(new File(filename));
			int tilesX = (int)Math.floor(bi.getWidth() / tileSizeX);
			int tilesY = (int)Math.floor(bi.getHeight() / tileSizeY);
			tileAmount = tilesX * tilesY;
			width = tileSizeX;
			height = tileSizeY;
			
			int[] pixels_raw = new int[bi.getWidth() * bi.getHeight()];
			pixels_raw = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());
			
			ByteBuffer pixels;
			
			for(int counter = 0; counter < tileAmount; counter++){
				pixels = BufferUtils.createByteBuffer(width * height * 4);
				
				int posX = (counter % tilesX) * width;
				int posY = ((int)Math.floor(counter / tilesX)) * height;
				
				for(int y = posY; y < posY + height; y++){
					for(int x = posX; x < posX + width; x++){
						int pixel = pixels_raw[y * bi.getWidth() + x];
						pixels.put((byte)((pixel >> 16) & 0xFF)); //R
						pixels.put((byte)((pixel >> 8) & 0xFF));  //G
						pixels.put((byte)((pixel) & 0xFF));       //B
						pixels.put((byte)((pixel >> 24) & 0xFF)); //A
					}
				}
				
				pixels.flip();
				
				int id = glGenTextures();
				
				glBindTexture(GL_TEXTURE_2D, id);
				
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
				
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
				
				idMap.put(counter, id);
			}
		}
		catch(IOException e){
			Logger.logError(e);
		}
	}

	private void bind(int tile){
		glBindTexture(GL_TEXTURE_2D, idMap.get(tile));
	}
	
	public void draw(int tile, int x, int y){
		draw(tile, x, y, 1f, 1f);
	}
		
	public boolean draw(int tile, int x, int y, float scaleX, float scaleY){
		if(tile >= tileAmount){
			return false;
		}
		
		float actualX = x * GameWindowConstants.getSCALE_FACTOR_X();
		float actualWidth = width * GameWindowConstants.getSCALE_FACTOR_X() * scaleX;
		float actualY = y * GameWindowConstants.getSCALE_FACTOR_Y();
		float actualHeight = height * GameWindowConstants.getSCALE_FACTOR_Y() * scaleY;
		
		float minX = (2f * actualX) / GameWindowConstants.getSCREEN_WIDTH() - 1f;
		float maxX = (2f * (actualX + actualWidth)) / GameWindowConstants.getSCREEN_WIDTH() - 1f;
		float minY = (2f * actualY) / GameWindowConstants.getSCREEN_HEIGHT() - 1f;
		float maxY = (2f * (actualY + actualHeight)) / GameWindowConstants.getSCREEN_HEIGHT() - 1f;
		
		bind(tile);
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
		
		return true;
	}

}
