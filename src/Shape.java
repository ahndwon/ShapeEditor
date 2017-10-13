import processing.core.PApplet;
import java.io.Serializable;


public abstract class Shape implements Cloneable,Serializable {
    private final static int width  = 30;
    private int transparency = 125;
    private Point point;

    public abstract void draw(PApplet pApplet);
    public abstract void showHover();
    public abstract void exitHover();
    public abstract boolean collisionTest(Shape s1, Shape s2);

    public Shape( float mouseX, float mouseY) {
            point = new Point(mouseX, mouseY);

    }


    public float getWidth() { return width;}
    public Point getPoint() { return point; }

    public void move() {
        float px = point.getX() + getWidth();
        float py = point.getY() + getWidth();
        this.point.setX(px);
        this.point.setY(py);
    }

    public boolean isPressed(float mouseX, float mouseY) {
        if( point.getX() < mouseX && mouseX< point.getX() + width &&
                point.getY() <mouseY && mouseY < point.getY() + width){
            return true;
        } else
            return false;
    }

    public void setTransparency(int wheelCount) {
        transparency += wheelCount;
        if(transparency < 0) { transparency = 0; }
        else if(transparency > 255) { transparency = 255; }
    }

    public int getTransparency() { return transparency;}

    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
