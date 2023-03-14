package lwjgladapter.physics.collision;

import lombok.Getter;
import lombok.Setter;
import lwjgladapter.maths.vectors.Vector2;
import lwjgladapter.physics.PhysicsHelper;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

@Getter
@Setter
public class CircleCollider extends Collider {

    private float radius;

    public CircleCollider(Vector2 position, float radius) {
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

    private boolean intersectsWithPoint(Vector2 point) {
        int deltaX = getPosition().x - point.x;
        int deltaY = getPosition().y - point.y;
        return (Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) < Math.pow(radius, 2);
    }

    private Collision intersectsWithCircle(CircleCollider other) {
        double xDiff = other.getPosition().x - getPosition().x;
        double yDiff = other.getPosition().y - getPosition().y;
        double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
        double radiusDistance = getRadius() + other.getRadius();
        if (distance <= radiusDistance) {
            double differenceFactor = (getRadius() - ((radiusDistance - distance) / 2)) / getRadius();
            int x = getPosition().x + (int) Math.round(xDiff * differenceFactor);
            int y = getPosition().y + (int) Math.round(yDiff * differenceFactor);
            return new Collision(PhysicsHelper.generateCollisionKey(this, other), x, y);
        }
        return null;
    }

    private Collision intersectsWithRect(RectCollider other) {
        int nearestX = Math.max(other.getPosition().x, Math.min(getPosition().x, other.getPosition().x + other.getWidth()));
        int nearestY = Math.max(other.getPosition().y, Math.min(getPosition().y, other.getPosition().y + other.getHeight()));
        if (intersectsWithPoint(new Vector2(nearestX, nearestY))) {
            return new Collision(PhysicsHelper.generateCollisionKey(this, other), nearestX, nearestY);
        }
        return null;
    }
}
