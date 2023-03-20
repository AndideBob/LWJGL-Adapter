package lwjgladapter.gfx.camera;

import lombok.Getter;
import lwjgladapter.GameWindowConstants;
import lwjgladapter.maths.shapes.RectInt;
import lwjgladapter.maths.vectors.Vector2Int;

@Getter
public class Camera2D {

    private Vector2Int position;

    private RectInt viewPort;

    public Camera2D(Vector2Int position) {
        setPosition(position);
    }

    public void setPosition(Vector2Int position) {
        this.position = position;
        updateViewPort();
    }

    private void updateViewPort() {
        viewPort = new RectInt(position.getX(), position.getY(), GameWindowConstants.DEFAULT_SCREEN_WIDTH, GameWindowConstants.DEFAULT_SCREEN_HEIGHT);
    }
}
