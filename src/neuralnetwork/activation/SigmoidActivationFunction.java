package neuralnetwork.activation;

public class SigmoidActivationFunction implements ActivationFunction {

        @Override
	public double calculate(double x) 
	{
			return 1 / (1 + Math.pow(Math.E, -x));
	}

        @Override
	public double derive(double f)
	{
		return f * (1 - f);
	}

}
