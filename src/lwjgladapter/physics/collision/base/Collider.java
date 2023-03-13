package lwjgladapter.physics.collision.base;

import lombok.Getter;
import lombok.Setter;
import lwjgladapter.maths.vectors.Vector2;
import lwjgladapter.physics.PhysicsHelper;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public abstract class Collider {

    @Getter
    private long id;
    private boolean wasKeySet = false;

    @Getter
    @Setter
    private Vector2 position;
    @Getter
    @Setter
    private boolean active;

    /**
     * The constructor of Collider needs to be called with an id from {@link lwjgladapter.physics.PhysicsHelper.getNextColliderID() PhysicsHelper.getNextColliderID()}
     *
     * @param id
     */
    public Collider(long id, Vector2 position) {
        setID(id);
        this.position = position;
    }

    /**
     * This method should only be called by the constructor of Collider
     *
     * @param id They id used to identify this collider.
     */

    private final void setID(long id) {
        if (!wasKeySet) {
            this.id = id;
            wasKeySet = true;
            active = true;
            PhysicsHelper.getInstance().registerCollider(this);
        }
    }

    /**
     * This method should be called when a collider is no longer needed. It will be removed from the PhysicsHelper
     */
    public final void unregister() {
        if (wasKeySet) {
            PhysicsHelper.getInstance().unregisterCollider(this);
        }
    }

    public boolean wasIDSet() {
        return wasKeySet;
    }

    /**
     * This method checks whether this collider intersects with another Collider. However this check is not commutative and should
     * therefor not be called by the game itself. The game should always call {@link lwjgladapter.physics.PhysicsHelper#checkCollisionBetween(Collider, Collider) PhysicsHelper.checkCollisionBetween(ColliderA, ColliderB)}
     *
     * @param other The other Collider.
     * @return
     * @throws CollisionNotSupportedException
     */

    public Collision getCollisionWith(Collider other) throws CollisionNotSupportedException {
        throw new CollisionNotSupportedException("No collision defined between " + this.getClass().getName() + " and " + other.getClass().getName() + "!");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Collider other = (Collider) obj;
        return id == other.id;
    }
}
