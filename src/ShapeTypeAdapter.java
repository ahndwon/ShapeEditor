import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

class ShapeTypeAdapter extends TypeAdapter<Shape> {

    @Override
    public void write(JsonWriter writer, Shape shape) throws IOException {
        writer.beginObject();
        writer.name("type").value(shape.getClass().getName());
        writer.name("Point X").value(shape.getPoint().getX());
        writer.name("Point Y").value(shape.getPoint().getY());
        writer.endObject();
    }

    @Override
    public Shape read(JsonReader reader) throws IOException {

        reader.beginObject();

        int temp = 0;
        Shape s = null;

        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "type":
                    String type = reader.nextString();
                    switch (type) {
                        case "Rect":
                            s = new Rect(temp, temp);
                            break;
                        case "Circle":
                            s = new Circle(temp, temp);
                            break;
                        case "Triangle":
                            s = new Triangle(temp, temp);
                            break;
                    }
                    break;
                case "Point X":
                    if (s != null) {
                        s.getPoint().setX(reader.nextInt());
                    }
                    break;
                case "Point Y":
                    if (s != null) {
                        s.getPoint().setY(reader.nextInt());
                    }
                    break;
            }
        }

        reader.endObject();
        return s;
    }
}