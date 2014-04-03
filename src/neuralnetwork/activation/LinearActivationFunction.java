package neuralnetwork.activation;

public class LinearActivationFunction implements ActivationFunction {

        @Override
	public double calculate(double x)
	{
		return x;
	}
	
        @Override
	public double derive(double f)
	{
		return 1.0;
	}
}
