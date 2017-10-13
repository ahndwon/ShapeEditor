import java.io.*;
import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class Program extends PApplet{

    private boolean dragMode = false;
    private boolean duplicateMode = false;
    private int wheelCount = 0;
    private Shape selectedShape = null;

    List<Shape> shapes = new ArrayList<>();
    int index;

    @Override
    public void keyPressed() {

        Shape mShape;
        switch (key){
            case '1' :
                mShape = new Rect(mouseX, mouseY);
                if(!isCollide(mShape,shapes)) {
                    shapes.add(mShape);
                }
                break;

            case '2' :
                mShape = new Circle( mouseX,mouseY);
                if(!isCollide(mShape,shapes)) {
                    shapes.add(mShape);
                }
                break;

            case '3' :
                mShape = new Triangle(mouseX,mouseY);
                if(!isCollide(mShape,shapes)) {
                    shapes.add(mShape);
                }
                break;

            case 'd' :
                if(!duplicateMode){
                    duplicateMode = true;
                    System.out.println("duplicateMode on");
                } else {
                    duplicateMode = false;
                    System.out.println("duplicateMode off");
                }
                break;

            case 's' :
                try{saveFile();}
                catch(IOException e) { e.printStackTrace(); }
                break;

            case 'o' :
                try {
                    readFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Shape clone = null;
        for(int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).isPressed(e.getX(), e.getY())) {
                index = i;
                selectedShape = shapes.get(i);
            }
        }
        if (duplicateMode) {
            for(int i = 0; i < shapes.size(); i++) {
                if (shapes.get(i).isPressed(e.getX(), e.getY())) {
                    index = i;
                    clone = shapes.get(i).clone();
                    clone.move();
                }
            }
            if(clone != null) {

                shapes.add(clone);

                draw();
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        for(int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).isPressed(e.getX(), e.getY())) {
                index = i;
                shapes.get(i).showHover();
            } else {
                shapes.get(i).exitHover();
            }
        }


    }

    public void mouseWheel(MouseEvent e) {
        wheelCount = e.getCount();
        if(selectedShape != null) {
            selectedShape.setTransparency(wheelCount);
        } else
            System.out.println("selectedShape is null");



    }
    public void mouseDragged(MouseEvent e) {
        if(!duplicateMode) {
            shapes.get(index).getPoint().setX(this.mouseX);
            shapes.get(index).getPoint().setY(this.mouseY);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (dragMode) {
            draw();
        }
    }

    public void draw() {
        clear();
        for(Shape s : shapes) {
            s.draw(this);
        }
    }

    public boolean isCollide(Shape mShape, List<Shape> shapes) {
        boolean isCollide;
        if (shapes.size()==0) {
            isCollide = false;
            return isCollide;
        } else {
            for (Shape s : shapes) {
                if (mShape.collisionTest(mShape, s)) {
                    System.out.println("Overlapped!!");
                    isCollide = true;
                    return isCollide;
                }
            }
            isCollide = false;
            return isCollide;
        }
    }

    private void saveFile() throws IOException {
        try{
            FileOutputStream fos= new FileOutputStream("myfile");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(shapes);
            oos.close();
            fos.close();
            System.out.println("File Saved");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void readFile() throws IOException, ClassNotFoundException {
        ArrayList<Shape> shapeRead ;
        try
        {
            FileInputStream fis = new FileInputStream("myfile");
            ObjectInputStream ois = new ObjectInputStream(fis);
            shapeRead = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("File Loaded");
        }catch(IOException ioe){
            ioe.printStackTrace();
            return;
        }catch(ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }

        shapes = new ArrayList<>();
        for(Shape s : shapeRead){
            clear();
            shapes.add(s);
        }

    }


    public void settings() { this.size(500,500); }
    public void setup() {  this.background(0);}

    public static void main(String[] args) {
        PApplet.main("Program");
        System.out.println("Key 1 : Rectangle");
        System.out.println("Key 2 : Circle");
        System.out.println("Key 3 : Triangle");
        System.out.println("Key D : Duplicate Mode");
        System.out.println("Key S : Save to file");
        System.out.println("Key O : Open file");
        System.out.println("Drag Object by Dragging");
        System.out.println("Change Objects's color by Scrolling after selecting it");
    }
}


























