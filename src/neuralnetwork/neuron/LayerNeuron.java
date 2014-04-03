/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neuralnetwork.neuron;

import java.util.Iterator;

/**
 *
 * @author Николай
 */
public class LayerNeuron extends Neuron
{
	private TransferFunction func;
	
	public LayerNeuron(TransferFunction func)
	{
		super();
		this.func = func;
	}
	
	private double weightSumInputs()
	{
		double sum = 0;
		Iterator<Synapse> it = inputSynapses.iterator();
		
		while (it.hasNext())
		{
			Synapse syn = it.next();
			sum += syn.getA().getOutput() * syn.getWeight();
		}
		
		return sum;
	}
	
	
        @Override
	public double getOutput() 
	{		
		double sum = weightSumInputs();
		return func.calculate(sum);
	}
}
