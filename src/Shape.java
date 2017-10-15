import processing.core.PApplet;
import java.io.Serializable;

public abstract class Shape implements Cloneable,Serializable {
    private int width = 30;
    private int transparency = 125;
    private Point point;

    public abstract void draw(PApplet pApplet);
    public abstract void showHover();
    public abstract void exitHover();
    public abstract boolean collisionTest(Shape s1, Shape s2);

    public Shape(int mouseX, int mouseY) {
            point = new Point(mouseX, mouseY);

    }

    public void setPoint(int mouseX, int mouseY) {
        this.point = new Point(mouseX, mouseY);
    }
    public Point getPoint() { return point; }


    public int getWidth() { return width;}


    public boolean isPressed(int mouseX, int mouseY) {
        return point.getX() < mouseX && mouseX < point.getX() + width &&
                point.getY() < mouseY && mouseY < point.getY() + width;
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
            Shape clone = (Shape)super.clone();
            clone.setPoint(clone.getPoint().getX() + clone.getWidth(),
                    clone.getPoint().getY() + clone.getWidth());
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
