/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rover.ruckus.prototype;

/**
 *
 * @author kenny ge

 */
public abstract class Prototype {
    
    abstract void placeMarker();
    /**
     * @param power - the power of the motors Set power of both movement motors
     * range [0, 1]
     * @return - the delta distance since the last call of this method in cm
     * Calculate using wheel diameter
     * For every rotation of the sprocket corresponds its circumference in distance
     */
    public abstract float setMovePower(float power);

    /**
     * @param power - the power of the motors in the range [-1, 1]. positive
     * represents counterclockwise
     * @return - the delta angle since the last call of this method in degrees
     * Note that counterclockwise is positive and clockwise is negative
     * Calculate using circles & math
     */
    public abstract float setTurnPower(float power);
    
    public abstract void calibrate();

    public abstract void detach();///Users/mythilygurumurthy/NetBeansProjects/Rover Ruckus Prototype/src/rover/ruckus/prototype/Prototype.java
    
}
