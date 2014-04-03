/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;

import neuralnetwork.NeuralNetwork;

/**
 *
 * @author Николай
 */
public interface NetworkParser {
    public NeuralNetwork getNetwork();
    public void setNetwork(NeuralNetwork nn);
    
}
