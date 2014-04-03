/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.neuron;

import java.util.ArrayList;
import java.util.Iterator;
import neuralnetwork.activation.ActivationFunction;
import neuralnetwork.activation.LinearActivationFunction;
import neuralnetwork.activation.SigmoidActivationFunction;

/**
 *
 * @author Николай
 */
public class Neuron {

    public static int lastId = 0;
    public int id;

    protected ArrayList<Synapse> inputSynapses;
    protected ArrayList<Synapse> outputSynapses;

    private ActivationFunction func;

    public Neuron() {
        this.inputSynapses = new ArrayList<>();
        this.outputSynapses = new ArrayList<>();
        this.func = new LinearActivationFunction();
        this.id = lastId++;
    }

    public Neuron(int id) {
        this.inputSynapses = new ArrayList<Synapse>();
        this.outputSynapses = new ArrayList<Synapse>();
        this.func = new SigmoidActivationFunction();
        this.id = id;
        lastId = id + 1;
    }

    public Neuron(ActivationFunction func) {
        this();
        this.func = func;
    }

    @Override
    public String toString() {
        return "N" + id;
    }

    public void addInputSynapse(Synapse s) {
        inputSynapses.add(s);
    }

    public void addOutputSynapse(Synapse s) {
        outputSynapses.add(s);
    }

    public ArrayList<Synapse> getInputsSynapse() {
        return inputSynapses;
    }

    public ArrayList<Synapse> getOutputsSynapse() {
        return outputSynapses;
    }

    public static Synapse connect(Neuron a, Neuron b, double weight) {
        Synapse syn = new Synapse(a, b, weight);

        a.addOutputSynapse(syn);
        b.addInputSynapse(syn);

        return syn;
    }

    public static Synapse connect(Neuron a, Neuron b) {
        return connect(a, b, Math.random() - 0.5); // random weight between [-0.5, 0.5]
    }

    public ActivationFunction getActivationFunction() {
        return func;
    }

    public double getOutput() {
        double d = 0;
        for (Synapse s : outputSynapses) {
            d += s.getWeight();
        }
        System.out.println("getOutput: " + d);
        return d;
    }
}
