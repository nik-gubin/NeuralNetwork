/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import java.util.ArrayList;

/**
 *
 * @author Николай
 */
public class ValuesList extends ArrayList<Double> {

    public ValuesList() {
    }

    
    public ValuesList(int capacity) {
        for (int i = 0; i < capacity; i++) {
            this.add(0d);
        }
    }

}
