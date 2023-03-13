package lwjgladapter.physics;

import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;
import lwjgladapter.physics.collision.base.CollisionKey;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;
import lwjgladapter.physics.collision.exceptions.DublicatePhysicsHelperException;

import java.util.HashMap;
import java.util.HashSet;

public class PhysicsHelper {

    private static PhysicsHelper instance = null;

    private static Long colliderCounter = 0L;
    private HashMap<CollisionKey, Collision> cachedCollisions = new HashMap<>();

    private HashSet<Collider> registeredColliders = new HashSet<>();

    private PhysicsHelper() throws DublicatePhysicsHelperException {
        if (instance == null) {
            instance = this;
        } else {
            throw new DublicatePhysicsHelperException("PhysicsHelper is a Singleton Object and it can only be initialized once!");
        }
    }

    public static PhysicsHelper getInstance() {
        if (instance == null) {
            try {
                return new PhysicsHelper();
            } catch (DublicatePhysicsHelperException e) {
                Logger.logError(e);
            }
        }
        return instance;
    }

    public static long getNextColliderID() {
        long value = colliderCounter;
        colliderCounter = (colliderCounter + 1) % Long.MAX_VALUE;
        return value;
    }

    /**
     * Clears the cache for collisions. This should be called on each iteration of the game.
     */

    public void resetCollisions() {
        cachedCollisions.clear();
    }

    /**
     * Checks collisions for all active colliders.
     */

    public void checkCollisions() {
        for (Collider colliderA : registeredColliders) {
            for (Collider colliderB : registeredColliders) {
                try {
                    Collision collision = getCollisionInternally(colliderA, colliderB);
                    CollisionKey key = collision == null ? generateCollisionKey(colliderA, colliderB) : collision.getKey();
                    if (!cachedCollisions.containsKey(key)) {
                        cachedCollisions.put(key, collision);
                    }
                } catch (CollisionNotSupportedException e) {
                    Logger.logError(e);
                }
            }
        }
    }

    private Collision getCollisionInternally(Collider colliderA, Collider colliderB) throws CollisionNotSupportedException {
        Collision collision = checkCollisionBetween(colliderA, colliderB);
        if (collision != null) {
            return collision;
        }
        try {
            collision = colliderA.getCollisionWith(colliderB);
        } catch (CollisionNotSupportedException e) {// Try checking collision in reverse for missing Implementations
            collision = colliderB.getCollisionWith(colliderA);
        }
        return collision;
    }

    /**
     * This method is used to determine whether two colliders are intersecting. Collisions first need to be updated using
     * {@link #checkCollisions() checkCollisions}. Collisions are then being cached
     * and need to be reset using {@link #resetCollisions() resetCollisions}.
     * Collisions are being checked commutatively which means the order in which the parameters are passed in
     * do not matter.
     *
     * @param colliderA The first collider.
     * @param colliderB The second collider.
     * @return Returns a collision between the two colliders, or NULL if no collision has occured.
     * @throws CollisionNotSupportedException This exception is thrown if neither collider implements checking for an intersection
     *                                        with the other
     */

    public Collision checkCollisionBetween(Collider colliderA, Collider colliderB) throws CollisionNotSupportedException {
        if (!colliderA.isActive() || !colliderB.isActive()) {
            return null;
        }
        CollisionKey key = generateCollisionKey(colliderA, colliderB);
        if (cachedCollisions.containsKey(key)) {
            return cachedCollisions.get(key);
        }
        return null;
    }

    public static CollisionKey generateCollisionKey(Collider colliderA, Collider colliderB) {
        return new CollisionKey(colliderA.getId(), colliderB.getId());
    }

    public void registerCollider(Collider collider) {
        registeredColliders.add(collider);
    }

    public void unregisterCollider(Collider collider) {
        registeredColliders.remove(collider);
    }
}
