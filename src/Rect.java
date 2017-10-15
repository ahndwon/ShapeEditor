import processing.core.PApplet;


public class Rect extends Shape {
    private transient boolean strokeChanged = false;

    public Rect(int mouseX, int mouseY) {
        super( mouseX, mouseY);
    }

    @Override
    public void showHover() {
        strokeChanged = true;
    }

    @Override
    public void exitHover() {
        strokeChanged = false;
    }

    @Override
    public void draw(PApplet pApplet) {
        if(strokeChanged) {
            pApplet.stroke(255);
        } else
            pApplet.stroke(0);

        int upperLeftX = this.getPoint().getX();
        int upperLeftY = this.getPoint().getY();
        pApplet.fill(super.getTransparency(),0,0);
        pApplet.rect(upperLeftX ,upperLeftY ,super.getWidth(),super.getWidth());
    }

    @Override
    public Rect clone() {
        return (Rect) super.clone();
    }

    @Override
    public boolean collisionTest(Shape s1, Shape s2) {
        Point closestPoint;
        int r1_upperLeftX = s1.getPoint().getX();
        int r1_upperLeftY = s1.getPoint().getY();
        int r2_upperLeftX = s2.getPoint().getX();
        int r2_upperLeftY = s2.getPoint().getY();

        if((s1 instanceof Rect) == (s2 instanceof Rect)) {
            if(r1_upperLeftX < r2_upperLeftX + s2.getWidth() &&
                r1_upperLeftX + s1.getWidth() > r2_upperLeftX &&
                r1_upperLeftY < r2_upperLeftY + s2.getWidth() &&
                r1_upperLeftY + s1.getWidth() > r2_upperLeftY)
            return true;
        } else if ((s1 instanceof Rect) == (s2 instanceof Circle)) {
            int closestX = s2.getPoint().getX();
            int closestY = s2.getPoint().getY();
            closestPoint = new Point( closestX, closestY);

            if(s2.getPoint().getX() < s1.getPoint().getX()) {
                closestPoint.setX(s1.getPoint().getX());
            }
            else if(s2.getPoint().getX() > s1.getPoint().getX() + s1.getWidth()) {
                closestPoint.setX(s1.getPoint().getX() + s1.getWidth());
            }

            if(s2.getPoint().getY() < s1.getPoint().getY()) {
                closestPoint.setY(s1.getPoint().getY());
            }
            else if(s2.getPoint().getY() > s1.getPoint().getY() + s1.getWidth()) {
                closestPoint.setY(s1.getPoint().getY() + s1.getWidth());
            }

            return s2.getPoint().distSquared(closestPoint) < s2.getWidth()/2 * s2.getWidth()/2;
        }
        return false;
    }
}
