import java.io.Serializable;

public class Point implements Serializable{
    private int posX;
    private int posY;

    public Point(int posX , int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getX() { return posX; }

    public int getY() {
        return posY;
    }

    public void setX(int posX) {
        this.posX = posX;
    }

    public void setY(int posY) {
        this.posY = posY;
    }

    public int distSquared(Point other) {
        int distX = this.posX - other.posX;
        int distY = this.posY - other.posY;
        return distX*distX + distY*distY;
    }
}
