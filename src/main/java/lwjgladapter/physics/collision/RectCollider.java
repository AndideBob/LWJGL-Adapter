package lwjgladapter.physics.collision;

import lombok.Getter;
import lombok.Setter;
import lwjgladapter.maths.vectors.Vector2Int;
import lwjgladapter.physics.PhysicsHelper;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

@Getter
@Setter
public class RectCollider extends Collider {

    private int width;
    private int height;

    public RectCollider(int positionX, int positionY, int width, int height) {
        this(new Vector2Int(positionX, positionY), width, height);
    }

    public RectCollider(Vector2Int position, int width, int height) {
        super(PhysicsHelper.getNextColliderID(), position);
        this.width = width;
        this.height = height;
    }

    @Override
    public Collision getCollisionWith(Collider other) throws CollisionNotSupportedException {
        if (other instanceof RectCollider) {
            return intersectsWithRect((RectCollider) other);
        }
        return super.getCollisionWith(other);
    }

    private Collision intersectsWithRect(RectCollider other) {
        int intersectionXLeft = Math.max(getPosition().getX(), other.getPosition().getX());
        int intersectionYBottom = Math.max(getPosition().getY(), other.getPosition().getY());
        int intersectionXRight = Math.min(getPosition().getX() + getWidth(), other.getPosition().getX() + other.getWidth());
        int intersectionYTop = Math.min(getPosition().getY() + getHeight(), other.getPosition().getY() + other.getHeight());
        if (intersectionXLeft < intersectionXRight && intersectionYBottom < intersectionYTop) {
            int intersectionWidth = intersectionXRight - intersectionXLeft;
            int intersectionHeight = intersectionYBottom - intersectionYTop;
            int centerX = intersectionXLeft + (int) Math.round(intersectionWidth / 2);
            int centerY = intersectionYTop + (int) Math.round(intersectionHeight / 2);
            return new Collision(PhysicsHelper.generateCollisionKey(this, other), centerX, centerY);
        }
        return null;
    }


}
