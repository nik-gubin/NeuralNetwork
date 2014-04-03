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
public final class InputNeuron extends Neuron
{
	private double value;
	
	public InputNeuron(double value)
	{
		super();
		setValue(value);
	}
	
	public void setValue(double value)
	{
		this.value = value;
	}
	
        @Override
	public double getOutput() 
	{
		return value;
	}
}
