/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kenny.measuring.tool;

import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class KennyMeasuringTool extends javafx.application.Application
{
  private Circle start;
  private Circle end;
  private Line line;
  private Pane draggableGroup;
  
  public KennyMeasuringTool() {}
  
  public static void main(String[] args)
  {
    launch(args);
  }
  





  private final double S = 700.0D;
  
  private Text text;
  
  public void start(javafx.stage.Stage primaryStage)
  {
    primaryStage.setTitle("Rover Ruckus Measuring Tool by Kenny Ge");
    start = new Circle(5.0D);
    start.setCenterX(100.0D);
    start.setCenterY(25.0D);
    end = new Circle(5.0D);
    end.setCenterX(200.0D);
    end.setCenterY(75.0D);
    






    enableDrag(start);
    enableDrag(end);
    
    line = new Line();
    line.setStartX(start.getCenterX());
    line.setStartY(start.getCenterY());
    line.setEndX(end.getCenterX());
    line.setEndY(end.getCenterY());
    
    javafx.scene.layout.StackPane root = new javafx.scene.layout.StackPane();
    
    javafx.scene.image.Image image = new javafx.scene.image.Image(KennyMeasuringTool.class.getResourceAsStream("rover_ruckus.png"));
    javafx.scene.image.ImageView view = new javafx.scene.image.ImageView();
    view.setFitWidth(700.0D);
    view.setFitHeight(700.0D);
    
    view.setImage(image);
    
    root.getChildren().add(view);
    
    text = new Text();
    text.setFont(new javafx.scene.text.Font(20.0D));
    text.setText("Once you move the nodes, the distance here will update to reflect the length of the line");
    text.setX(100.0D);
    text.setY(100.0D);
    
    draggableGroup = new Pane();
    draggableGroup.getChildren().add(start);
    draggableGroup.getChildren().add(end);
    draggableGroup.getChildren().add(line);
    draggableGroup.getChildren().add(text);
    
    root.getChildren().add(draggableGroup);
    
    primaryStage.setScene(new javafx.scene.Scene(root, 700.0D, 700.0D));
    primaryStage.show();
  }
  
  private double deltaX = 0.0D; private double deltaY = 0.0D;
  
  public void updateLine() {
    double deltaXPix = Math.abs(line.getStartX() - line.getEndX());
    double deltaYPix = Math.abs(line.getStartY() - line.getEndY());
    
    double deltaX = deltaXPix / 700.0D * 365.76D;
    double deltaY = deltaYPix / 700.0D * 365.76D;
    
    double hypotenuse = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    
    text.setText(hypotenuse + " cm");
  }
  
  private void enableDrag(final Circle circle) {
    circle.setOnMousePressed((mouseEvent)->{
        deltaX = (circle.getCenterX() - mouseEvent.getX());
        deltaY = (circle.getCenterY() - mouseEvent.getY());
        circle.getScene().setCursor(javafx.scene.Cursor.MOVE);
      });
    circle.setOnMouseDragged((mouseEvent)->{
        circle.setCenterX(mouseEvent.getX() + deltaX);
        circle.setCenterY(mouseEvent.getY() + deltaY);
        if (circle == start) {
          line.setStartX(circle.getCenterX());
          line.setStartY(circle.getCenterY());
        } else {
          line.setEndX(circle.getCenterX());
          line.setEndY(circle.getCenterY());
        }
        updateLine();
      });
  }
}
