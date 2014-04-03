package parser;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.neuron.Neuron;
import neuralnetwork.neuron.Synapse;

/**
 * CSV format writer.
 *
 * @author Vitaliy Garnashevich
 */
public class CsvWriter implements Closeable {

    public static final char FIELD_SEP_SEMICOLON = ';';
    public static final char FIELD_SEP_COMMA = ',';
    public static final char FIELD_SEP_TAB = '\t';
    public static final char FIELD_SEP_BAR = '|';

    public static final char QUOTE_SINGLE = '\'';
    public static final char QUOTE_DOUBLE = '"';

    public static final String EOL_CR = "\r";
    public static final String EOL_LF = "\n";
    public static final String EOL_CRLF = "\r\n";

    private final StringBuilder writer;
    private final char fieldSep;
    private final char quoteChar;
    private final String lineSep;

    private final String fieldSepStr;
    private final String quoteCharStr;
    private final String doubleQuoteCharStr;
    private final String quoteCharPattern;

    public CsvWriter(StringBuilder writer) {
        this(writer, FIELD_SEP_COMMA, QUOTE_DOUBLE, EOL_CRLF);
    }

    public CsvWriter(StringBuilder writer, char fieldSeparator) {
        this(writer, fieldSeparator, QUOTE_DOUBLE, EOL_CRLF);
    }

    public CsvWriter(StringBuilder writer, char fieldSeparator, char quoteChar, String lineSeparator) {
        this.writer = writer;
        this.fieldSep = fieldSeparator;
        this.quoteChar = quoteChar;
        this.lineSep = lineSeparator;

        this.fieldSepStr = String.valueOf(fieldSep);
        this.quoteCharStr = String.valueOf(quoteChar);
        this.doubleQuoteCharStr = quoteChar + "" + quoteChar;
        this.quoteCharPattern = "\\" + quoteChar;
    }

    public char getFieldSeparator() {
        return fieldSep;
    }

    public char getQuoteChar() {
        return quoteChar;
    }

    public String getLineSeparator() {
        return lineSep;
    }

    /**
     * Close underlying writer.
     */
    @Override
    public void close() throws IOException {
        //writer.close();
    }

    /**
     * Write row of values as CSV.
     *
     * @param neuralNetwork
     * @throws java.io.IOException
     */
    public void write(NeuralNetwork neuralNetwork) throws IOException {

        String str = String.valueOf(neuralNetwork.inputNeurons.size()) + ' '
                + neuralNetwork.hiddenNeurons.size() + ' '
                + neuralNetwork.outputNeurons.size();

        writer.append(str);
        writer.append(lineSep);
        boolean isFirst = true;
        for (Neuron value : neuralNetwork.inputNeurons) {
//            if (!isFirst) {
//                writer.write(fieldSepStr);
//            }
//            isFirst = false;

            if (value != null) {

                for (Synapse synapse : value.getOutputsSynapse()) {
                    writer.append(String.valueOf(synapse.getWeight()));
                    writer.append(fieldSep);
                }
                writer.append(lineSep);
            }

        }
        writer.append(lineSep);
        for (Neuron neuron : neuralNetwork.outputNeurons) {
            if (neuron != null) {
                for (Synapse synapse : neuron.getInputsSynapse()) {
                    writer.append(String.valueOf(synapse.getWeight()));
                    writer.append(fieldSep);
                }
                writer.append(lineSep);
            }
        }
        writer.append(lineSep);
        File file = new File("network.csv");
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        printWriter.print(writer.toString());
        printWriter.flush();

    }

}
