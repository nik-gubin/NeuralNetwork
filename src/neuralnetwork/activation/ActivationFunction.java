/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neuralnetwork.activation;

/**
 *
 * @author Николай
 */
public interface ActivationFunction {

	public double calculate(double x);
	
	public double derive(double f);
}
