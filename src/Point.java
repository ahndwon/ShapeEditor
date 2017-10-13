import java.io.Serializable;

public class Point implements Serializable{
    private float posX;
    private float posY;

    public Point(float posX , float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public float getX() { return posX; }

    public float getY() {
        return posY;
    }

    public void setX(float posX) {
        this.posX = posX;
    }

    public void setY(float posY) {
        this.posY = posY;
    }

    public float distSquared(Point other) {
        float distX = this.posX - other.posX;
        float distY = this.posY - other.posY;
        return distX*distX + distY*distY;
    }
}
