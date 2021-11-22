/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rover.ruckus.prototype;

/**
 *
 * @author mythilygurumurthy
 */
public class RoverRuckusPrototype {
    
    public Prototype prototype;
    
    public void init(){
        /**Note to builders: Please uncomment this line and fill in the name
         of your subclass!!!*/
        //prototype = new ImplementedPrototype();
        
        //2nd red clockwise       
        prototype.calibrate();
        prototype.detach();
        
        Handler.moveForward(false, 66);
        Handler.turn(false, 90);
        Handler.moveForward(false, 130);
        Handler.turn(false, 45);
        Handler.moveForward(false, 90);
        
        prototype.placeMarker();
        
        Handler.turn(false, 145);
        
        
        
        //Move the marker closer to the starting position
    }
    
    public void update(){
        Handler.callFrame();
        //Uncomment if necessary - refresh rate of 60Hz
        /*try{
          Thread.sleep(1000f / 60f);  
        }catch(Exception e){
            e.printStackTrace();
        }*/
    }
    
    public void moveToPosition(){
        
    }
}
