package app;
import static java.util.Arrays.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import neuralnetwork.NeuralNetwork;
import parser.CsvParser;
import parser.CsvWriter;

public class Test {

    public static void main(String[] args) throws Exception {
        StringBuilder out = new StringBuilder();
        CsvWriter writer = new CsvWriter(out);
        NeuralNetwork net = new NeuralNetwork(2,1,1,3);
                Program.testBool(net, Integer.MAX_VALUE/40000, 0.9);
        writer.write(net);
       
        System.out.println(out);
        System.out.println();
        System.out.println(net.toString());
        
        CsvParser parser = new CsvParser();
        Iterator<Map<String,String>> iter = parser.parseAsMaps(new StringReader(out.toString()));
        while (iter.hasNext()) {
            Map<String,String> m=iter.next();
            for (Map.Entry<String, String> entry : m.entrySet()) {
                String string = entry.getKey();
                String string1 = entry.getValue();
                 System.out.println("key = "+ string+ " value = "+string1);
                
                 
            }
            System.out.println();
            
        }
    }

}
