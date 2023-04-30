package nazarrod.adventgem.advgem.View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GraphicController {
    Stage stage;
    Canvas canvas;
    GraphicsContext gc;

    public GraphicController(Stage stage, Canvas canvas, GraphicsContext graphicsContext) {
        this.stage = stage;
        this.canvas = canvas;
        this.gc = graphicsContext;
    }

    public void drawPlatform(int x,int y,int w,int h){
        Rectangle rectangle = new Rectangle(x,y,w,h);
        Image image = new Image("platform.png");
        ImagePattern imagePattern = new ImagePattern(image);
        rectangle.setFill(imagePattern);
//        rectangle.setFill(Color.YELLOW);
        gc.save();
        gc.setFill(rectangle.getFill());
        gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        gc.restore();
    }
}
