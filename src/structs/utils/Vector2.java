package structs.utils;

public class Vector2 {
    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 zero() {
        return new Vector2(0, 0);
    }

    @Override
    public String toString() {
        return "x:" + x + "   y:" + y;
    }
}
