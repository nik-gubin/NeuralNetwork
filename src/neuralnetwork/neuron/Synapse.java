/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neuralnetwork.neuron;

/**
 *
 * @author Николай
 */
public class Synapse {
	private final Neuron a;
	private final Neuron b;
	private double weight;
	
	public Synapse(Neuron a, Neuron b, double weight)
	{
		this.a = a;
		this.b = b;
		this.weight = weight;
	}
	
	public Synapse(Neuron a, Neuron b)
	{
		this.a = a;
		this.b = b;
		this.weight = (Math.random() * 2) - 1; // random between -1.0 and 1.0
	}
	
	public Neuron getA()
	{
		return a;
	}
	
	public Neuron getB()
	{
		return b;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
}