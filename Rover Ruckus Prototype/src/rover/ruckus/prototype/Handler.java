/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rover.ruckus.prototype;

import java.util.ArrayDeque;
import java.util.Deque;

public class Handler {

    public static Deque<OperationSet> queue;
    public static Prototype prototype;
    
    public static float x, y;
    /**
     * The x-axis is 0 degrees, positive is counterclockwise
     */
    public static float angleDeg;
    
    public static void create(float x, float y, float angleDeg) {
        Handler.x = x;
        Handler.y = y;
        Handler.angleDeg = angleDeg;
        queue = new ArrayDeque<>();
        
        prototype = new ImplementedPrototype();
        
        //Uncomment if necessary
        prototype.calibrate();
    }

    public static void cancelAllActions() {
        queue.remove();
    }

    public static void callFrame() {
        if(!queue.isEmpty()){
            Operation o = queue.peek();
            if(o.complete()){
                queue.remove();
                callFrame();
                return;
            }
            o.executeFrame();
        }
    }

    public static void addQueueObjectsSimultaneous(Operation... o) {
        if (queue.isEmpty()) {
            OperationSet set = new OperationSet();
            queue.add(set);
        }
        for (Operation o2 : o) {
            queue.peek().add(o2);
        }
    }

    public static void addQueueObjectsBuffered(Operation... o) {
        OperationSet set = new OperationSet();
        for (Operation o2 : o) {
            set.add(o2);
        }
        queue.add(set);
    }

    public static void addQueueObjects(boolean simultaneous, Operation... o) {
        if (simultaneous) {
            addQueueObjectsSimultaneous(o);
        } else {
            addQueueObjectsBuffered(o);
        }
    }

    /**
     * @param degrees - the angle measure in degrees (counterclockwise is positive)
     * @param simultaneous - whether or not this action will be done while the other
     * operations are also
     */
    public static void turn(boolean simultaneous, float degrees) {
        angleDeg += degrees;
        addQueueObjects(simultaneous, new Turn(degrees, prototype));
    }

    /**
     * Returns the float value of the inverse tan function in degrees
     */
    public static float atan(float ratio) {
        return (float) Math.toDegrees(Math.atan(ratio));
    }

    /**
     * @param xFinal - the x coordinate to go to in the range [-1, 1]
     * @param yFinal - the y coordinate to go to in the range [-1, 1] Note that
     * this is a blocking command! This means that, until the current command
     * finishes, the robot can do nothing else! If you want me to add in the
     * feature to do other things, please let me know and give me the source
     * code so I can implement it. That task is non-trivial.
     */
    public static void moveToCoord(float xFinal, float yFinal) {
        float deltaX = xFinal - x;
        float deltaY = yFinal - y;

        float theta = -1;

        if (deltaX == 0 && deltaY == 0) {
            return;
        } else if (deltaX == 0) {
            theta = deltaY < 0 ? 270 : 90;
        } else if (deltaY == 0) {
            theta = deltaX < 0 ? 180 : 0;
        }

        float xAbs = Math.abs(deltaX);
        float yAbs = Math.abs(deltaY);

        if (deltaX > 0 && deltaY > 0) { //first quadrant
            theta = atan(yAbs / xAbs);
        } else if (deltaX > 0 && deltaY < 0) {
            theta = 270 + atan(xAbs / yAbs);
        } else if (deltaY > 0) { //don't need to check deltaX again because we know it is negative
            theta = 90 + atan(xAbs / yAbs);
        } else {
            theta = 180 + atan(yAbs / xAbs);
        }

        //It doesn't matter if they're negative because squaring removes the sign
        float deltaXCm = deltaX * 152.4f;
        float deltaYCm = deltaY * 152.4f;

        float hypotenuse = pythag(deltaXCm, deltaYCm);

        setAngle(false, theta);
        moveForward(false, hypotenuse);
    }

    public static float pythag(float x, float y) {
        return (float) Math.sqrt(x * x + y * y);
    }

    public static void setAngle(boolean simultaneous, float angleDeg) {
        float delta = angleDeg - Handler.angleDeg;
        while (delta < 0) {
            delta += 360;
        }
        turn(simultaneous, delta % 360f);
    }

    public static void moveForward(boolean simultaneous, float cm) {
        float xCm = (float) (Math.cos(Math.toRadians(angleDeg)) * cm);
        float yCm = (float) (Math.sin(Math.toRadians(angleDeg)) * cm);

        float xCoord = xCm / Constants.ARENA_SIZE;
        float yCoord = yCm / Constants.ARENA_SIZE;

        x += xCoord;
        y += yCoord;

        move(simultaneous, cm);
    }   
    
    public static void move(boolean simultaneous, float cm) {
        addQueueObjects(simultaneous, new Move(cm, prototype));
    }
    
    public static void updatePolarCartesianDelta(float dist){
        float dy = (float)Math.sin(angleDeg) * dist;
        float dx = (float) Math.cos(angleDeg) * dist;
        x += dx;
        y += dy;
    }
}
