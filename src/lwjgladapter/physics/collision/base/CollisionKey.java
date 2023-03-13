package lwjgladapter.physics.collision.base;

public record CollisionKey(long objectAID, long objectBID) {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long a = Math.min(objectAID, objectBID);
		long b = Math.max(objectAID, objectBID);
		result = prime * result + (int) (a ^ (a >>> 32));
		result = prime * result + (int) (b ^ (b >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollisionKey other = (CollisionKey) obj;
		long minThis = Math.min(objectAID, objectBID);
		long maxThis = Math.max(objectAID, objectBID);
		long minOther = Math.min(other.objectAID, other.objectBID);
		long maxOther = Math.max(other.objectAID, other.objectBID);
		if (minThis != minOther)
			return false;
		return maxThis == maxOther;
	}


}
