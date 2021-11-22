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
public class Turn implements Operation {

    public Prototype p;
    public float currentAngle, deltaAngle;
    
    public Turn(float deltaAngle, Prototype p){
        this.deltaAngle = deltaAngle;
        this.p = p;
    }

    /** @returns - whether or not the magnitude of the angle difference
     is equal to the desired magnitude. Note that direction is determined in the
     executeFrame function*/
    @Override
    public boolean complete() {
        return Math.abs(currentAngle) >= Math.abs(deltaAngle);
    }

    @Override
    public void executeFrame() {
        if(!complete()){
            float delta = p.setTurnPower(Math.signum(deltaAngle));
            currentAngle += delta;
            Handler.angleDeg += currentAngle;
        }
    }
    
}
