package lwjgladapter.gfx.camera;

import lombok.Getter;
import lwjgladapter.GameWindowConstants;
import lwjgladapter.maths.shapes.Rect;
import lwjgladapter.maths.vectors.Vector2;

@Getter
public class Camera2D {

    private Vector2 position;

    private Rect viewPort;

    public Camera2D(Vector2 position) {
        setPosition(position);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        updateViewPort();
    }

    private void updateViewPort() {
        viewPort = new Rect(position.x, position.y, GameWindowConstants.DEFAULT_SCREEN_WIDTH, GameWindowConstants.DEFAULT_SCREEN_HEIGHT);
    }
}
