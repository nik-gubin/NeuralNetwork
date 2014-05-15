package app;
import neuralnetwork.NeuralNetwork;
import parser.CsvReader;
import parser.CsvWriter;

public class Test {

    public static void main(String[] args) throws Exception {
        StringBuilder out = new StringBuilder();
        CsvWriter writer = new CsvWriter("network.csv");
        NeuralNetwork net = new NeuralNetwork(2,1,1,3);
                Program.testBool(net, Integer.MAX_VALUE/40000, 0.9);
        writer.write(net);
       
        System.out.println(out);
        System.out.println();
        System.out.println(net.toString());
        
        CsvReader parser = new CsvReader();
        NeuralNetwork neuralNetwork = parser.parseAsNetwork("network.csv");
        System.out.println();
        System.out.println(neuralNetwork.toString());
        //NeuralNetwork iter = parser.parseAsNetwork(new StringReader(out.toString()));
//        while (iter.hasNext()) {
//            Map<String,String> m=iter.next();
//            for (Map.Entry<String, String> entry : m.entrySet()) {
//                String string = entry.getKey();
//                String string1 = entry.getValue();
//                 System.out.println("key = "+ string+ " value = "+string1);
//                
//                 
//            }
//            System.out.println();
//            
//        }
    }

}
