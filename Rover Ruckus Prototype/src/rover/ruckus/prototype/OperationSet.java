/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rover.ruckus.prototype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author kenny ge
 * A set of simultaneous operations
 */
public class OperationSet implements Operation {
    
    public List<Operation> operations;

    public OperationSet() {
        operations = new ArrayList<>();
    }
    
    public void add(Operation o){
        
    }

    @Override
    public boolean complete() {
        return operations.isEmpty();
    }

    @Override
    public void executeFrame() {
        operations.stream().forEach((o) -> {
            if(!o.complete())
                o.executeFrame();
        });
        Iterator<Operation> iterator = operations.iterator();
        
        while(iterator.hasNext()){
            Operation o = iterator.next();
            if(o.complete()){
                iterator.remove();
            }
        }
    }
    
}
