package lwjgladapter.maths.vectors;

import java.util.Comparator;

public final class Vector2Maths {

    public static final class XComparator<V extends Vector2> implements Comparator<V> {
        @Override
        public int compare(Vector2 o1, Vector2 o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null || o2 == null) {
                return o1 == null ? -1 : 1;
            }
            return Float.compare(o1.getX().floatValue(), o2.getX().floatValue());
        }
    }

    public static final class YComparator<V extends Vector2> implements Comparator<V> {
        @Override
        public int compare(Vector2 o1, Vector2 o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null || o2 == null) {
                return o1 == null ? -1 : 1;
            }
            return Float.compare(o1.getY().floatValue(), o2.getY().floatValue());
        }
    }

    public static final class LengthComparator<V extends Vector2> implements Comparator<V> {
        @Override
        public int compare(Vector2 o1, Vector2 o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null || o2 == null) {
                return o1 == null ? -1 : 1;
            }
            return Double.compare(o1.getLength(), o2.getLength());
        }
    }
}
