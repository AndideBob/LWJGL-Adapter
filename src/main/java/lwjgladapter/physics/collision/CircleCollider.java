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
public class CircleCollider extends Collider {

    private float radius;

    public CircleCollider(Vector2Int position, float radius) {
        super(PhysicsHelper.getNextColliderID(), position);
        this.radius = radius;
    }

    @Override
    public Collision getCollisionWith(Collider other) throws CollisionNotSupportedException {
        if (other instanceof CircleCollider) {
            return intersectsWithCircle((CircleCollider) other);
        }
        if (other instanceof RectCollider) {
            return intersectsWithRect((RectCollider) other);
        }
        return super.getCollisionWith(other);
    }

    private boolean intersectsWithPoint(Vector2Int point) {
        int deltaX = getPosition().getX() - point.getX();
        int deltaY = getPosition().getY() - point.getY();
        return (Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) < Math.pow(radius, 2);
    }

    private Collision intersectsWithCircle(CircleCollider other) {
        double xDiff = other.getPosition().getX() - getPosition().getX();
        double yDiff = other.getPosition().getY() - getPosition().getY();
        double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
        double radiusDistance = getRadius() + other.getRadius();
        if (distance <= radiusDistance) {
            double differenceFactor = (getRadius() - ((radiusDistance - distance) / 2)) / getRadius();
            int x = getPosition().getX() + (int) Math.round(xDiff * differenceFactor);
            int y = getPosition().getY() + (int) Math.round(yDiff * differenceFactor);
            return new Collision(PhysicsHelper.generateCollisionKey(this, other), x, y);
        }
        return null;
    }

    private Collision intersectsWithRect(RectCollider other) {
        int nearestX = Math.max(other.getPosition().getX(), Math.min(getPosition().getX(), other.getPosition().getX() + other.getWidth()));
        int nearestY = Math.max(other.getPosition().getY(), Math.min(getPosition().getY(), other.getPosition().getY() + other.getHeight()));
        if (intersectsWithPoint(new Vector2Int(nearestX, nearestY))) {
            return new Collision(PhysicsHelper.generateCollisionKey(this, other), nearestX, nearestY);
        }
        return null;
    }
}
