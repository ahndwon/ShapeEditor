import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class Program extends PApplet{

    private boolean dragMode = false;
    private boolean duplicateMode = false;
    private int wheelCount = 0;
    private Shape selectedShape = null;

    private List<Shape> shapes = new ArrayList<>();
    private int index;

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
                saveFile();
                break;

            case 'o' :
                readFile();
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
        boolean isCollide = false;
        if (shapes.size()==0) {
            isCollide = false;

        } else {
            for (Shape s : shapes) {
                if (mShape.collisionTest(mShape, s)) {
                    System.out.println("Collision!!");
                    isCollide = true;
                }
            }
        }return isCollide;
    }

    private void saveFile() {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Shape.class, new ShapeTypeAdapter())
                .create();

        //1. Convert object to JSON string
        String json = gson.toJson(shapes);
        System.out.println(json);

        //2. Convert object to JSON string and save into a file directly
        try (FileWriter writer = new FileWriter("shapes.txt")) {
            gson.toJson(shapes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile()  {
        try (Reader reader = new FileReader("shapes.txt")) {
            Gson gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(Shape.class, new ShapeTypeAdapter())
                    .create();
            // Convert JSON to Java Object

            Type type = new TypeToken<List<Shape>>(){}.getType();
            shapes = gson.fromJson(reader, type);

        } catch (IOException e) {
            e.printStackTrace();
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


























