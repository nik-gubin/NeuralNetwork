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
public class CsvParser implements NetworkParser {

    private final String path;

    public CsvParser(String path) {
        this.path = path;
    }

    @Override
    public NeuralNetwork getNetwork() {
         CsvReader cp = new CsvReader();
         return cp.parseAsNetwork(path);
    }

    @Override
    public void setNetwork(NeuralNetwork nn) {
        CsvWriter cw = new CsvWriter(path);
        cw.write(nn);
    }

}
