import processing.core.PApplet;

class Circle extends Shape {
    private float radius = this.getWidth()/2;
    private boolean strokeChanged = false;


    public Circle( float mouseX, float mouseY) {
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
        float centerX = this.getPoint().getX() + getWidth()/2;
        float centerY = this.getPoint().getY() + getWidth()/2;
        pApplet.fill(0,super.getTransparency(),0);
        pApplet.ellipse(centerX, centerY,
                getWidth(), getWidth());
    }

    @Override
    public Circle clone() {
        return (Circle) super.clone();
    }

    @Override
    public boolean collisionTest(Shape s1, Shape s2) {
        Point closestPoint;

        if((s1 instanceof Circle) == (s2 instanceof Circle)) {
            float distance = s1.getPoint().distSquared(s2.getPoint());
            float radiusSum = s2.getWidth() / 2 + s2.getWidth() / 2;
            return distance <= radiusSum * radiusSum;
        } else if ((s1 instanceof Circle) == (s2 instanceof Rect)) {
            float closestX = s1.getPoint().getX();
            float closestY = s1.getPoint().getY();

            closestPoint = new Point( closestX, closestY);

            if(s1.getPoint().getX() < s2.getPoint().getX()) {
                closestPoint.setX(s2.getPoint().getX());
            }
            else if(s1.getPoint().getX() > s2.getPoint().getX() + s2.getWidth()) {
                closestPoint.setX(s2.getPoint().getX() + s2.getWidth());
            }

            if(s1.getPoint().getY() < s2.getPoint().getY()) {
                closestPoint.setY(s2.getPoint().getY());
            }
            else if(s1.getPoint().getY() > s2.getPoint().getY() + s2.getWidth()) {
                closestPoint.setY(s2.getPoint().getY() + s2.getWidth());
            }

            return s1.getPoint().distSquared(closestPoint) < s1.getWidth()/2 * s1.getWidth()/2;
        } else
            return false;
    }
}
