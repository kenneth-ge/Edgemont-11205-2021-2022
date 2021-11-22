/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rover.ruckus.prototype;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author mythilygurumurthy
 */
public class OperationSequence implements Operation {

    public Deque<Operation> otherOperations;
    
    public OperationSequence(){
        otherOperations = new ArrayDeque<>();
    }

    @Override
    public boolean complete() {
        return otherOperations.isEmpty();
    }

    @Override
    public void executeFrame() {
        if(!complete()){
            otherOperations.peek().executeFrame();
            if(otherOperations.peek().complete()){
                otherOperations.remove();
            }
        }
    }
    
}
