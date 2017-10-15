import processing.core.PApplet;


class Triangle extends Shape {
    private transient boolean strokeChanged = false;

    public Triangle( float mouseX, float mouseY) {
        super( mouseX, mouseY);
    }

    @Override
    public boolean collisionTest(Shape s1, Shape s2) {
        if((s1 instanceof Triangle) == (s2 instanceof Triangle)) {
            float distance = s1.getPoint().distSquared(s2.getPoint());
            float radiusSum = s2.getWidth() / 2 + s2.getWidth() / 2;
            return distance <= radiusSum * radiusSum;
        }
        return false;
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

        pApplet.fill(0,0,super.getTransparency());
        pApplet.triangle(this.getPoint().getX() , this.getPoint().getY() + super.getWidth() ,
                this.getPoint().getX() + super.getWidth()/2, this.getPoint().getY(),
                this.getPoint().getX() + super.getWidth(), this.getPoint().getY() + super.getWidth());
    }

    @Override
    public Triangle clone() {
        return (Triangle) super.clone();
    }
}
