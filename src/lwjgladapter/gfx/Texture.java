package lwjgladapter.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import lwjgladapter.GameWindowConstants;

public class Texture {
	
	private int id;
	private int width;
	private int height;

	public Texture(String filename) {
		BufferedImage bi;
		try{
			bi = ImageIO.read(new File(filename));
			width = bi.getWidth();
			height = bi.getHeight();
			
			int[] pixels_raw = new int[width * height];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
					int pixel = pixels_raw[y * width + x];
					pixels.put((byte)((pixel >> 16) & 0xFF)); //R
					pixels.put((byte)((pixel >> 8) & 0xFF));  //G
					pixels.put((byte)((pixel) & 0xFF));       //B
					pixels.put((byte)((pixel >> 24) & 0xFF)); //A
				}
			}
			
			pixels.flip();
			
			id = glGenTextures();
			
			glBindTexture(GL_TEXTURE_2D, id);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	private void bind(){
		glBindTexture(GL_TEXTURE_2D, id);
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
