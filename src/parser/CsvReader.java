package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.ValuesList;

/**
 * CSV format parser.
 *
 * <ul>
 * <li> CSV format is specified by <a
 * href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>.
 * <li> Each line should end with CRLF (but CR or LF will do too).
 * <li> A line consists of fields delimited with "field separator" (usually a
 * comma).
 * <li> Complex field values, which contain field separators or which span
 * through multiple lines, could be surrounded with double quotes (or single
 * quotes, or any other char, depending on configuration).
 * </ul>
 *
 * @author Vitaliy Garnashevich
 */
public class CsvReader {

    public static final char FIELD_SEP_SEMICOLON = ';';
    public static final char FIELD_SEP_COMMA = ',';
    public static final char FIELD_SEP_TAB = '\t';
    public static final char FIELD_SEP_BAR = '|';

    public static final char QUOTE_SINGLE = '\'';
    public static final char QUOTE_DOUBLE = '"';

    private final char fieldSep;
    private final char quoteChar;

    public CsvReader() {
        this(FIELD_SEP_COMMA, QUOTE_DOUBLE);
    }

    public CsvReader(char fieldSeparator) {
        this(fieldSeparator, QUOTE_DOUBLE);
    }

    public CsvReader(char fieldSeparator, char quoteChar) {
        this.fieldSep = fieldSeparator;
        this.quoteChar = quoteChar;
    }

    public char getFieldSeparator() {
        return fieldSep;
    }

    public char getQuoteChar() {
        return quoteChar;
    }

    /**
     * Parse CSV file as a set of {@link Map}s. Note that the first line is
     * required to be the header. Header is parsed automatically, in order to
     * know map keys.
     * <p>
     * Don't forget to close the reader when it is no longer needed.
     *
     * @param path
     * @return
     */
    public NeuralNetwork parseAsNetwork(String path) {
        NeuralNetwork network = null;
        BufferedReader r = null;
        try {
            File file = new File(path);
            r = new BufferedReader(new FileReader(file));
            Scanner scanner = new Scanner(r);

            List<String> list = parseNextLine(scanner);
            if (list.size() != 3) {
                throw new IllegalArgumentException("Недостаточно аргументов для создание нейросети. Необходимо три параметра.");
            }
            int in = Integer.parseInt(list.get(0));
            int out = Integer.parseInt(list.get(2));
            int midd = Integer.parseInt(list.get(1));
            network = new NeuralNetwork(in, out, midd);
            parseNextLine(scanner);
            List<ValuesList> valuesList = new ArrayList<>();

            for (int i = 0; i < in; i++) {
                list = parseNextLine(scanner);
                if (list.get(i).equals("")) {
                    throw new NumberFormatException("Не верный формат весов");
                }
                ValuesList vl = new ValuesList();

                for (String string : list) {
                    vl.add(Double.parseDouble(string));
                }
                valuesList.add(vl);
            }
            network.setInputWeight(valuesList);

            parseNextLine(scanner);
            valuesList = new ArrayList<>();

            for (int i = 0; i < out; i++) {
                list = parseNextLine(scanner);
                if (list.get(i).equals("")) {
                    throw new NumberFormatException("Не верный формат весов");
                }
                ValuesList vl = new ValuesList();

                for (String string : list) {
                    vl.add(Double.parseDouble(string));
                }
                valuesList.add(vl);
            }
            network.setOutputWeight(valuesList);

        } catch (IOException ex) {
            Logger.getLogger(CsvReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                r.close();
            } catch (IOException ex) {
                Logger.getLogger(CsvReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            return network;
        }
    }

    /**
     * Parse CSV file as a set of {@link List}s. Note that the first line could
     * be a header.
     * <p>
     * Don't forget to close the reader when it is no longer needed.
     *
     * @param reader
     * @return
     */
    public Iterator<List<String>> parseAsLists(Reader reader) {
        return new ListsIterator(reader);
    }

    private abstract class AbstractIterator<T> implements Iterator<T> {

        private T next;
        private boolean done;

        @Override
        public boolean hasNext() {
            if (done) {
                return false;
            }
            if (next == null) {
                next = getNext();
                if (next == null) {
                    done = true;
                    return false;
                }
            }
            return true;
        }

        protected abstract T getNext();

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T result = next;
            next = null;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class MapsIterator extends AbstractIterator<Map<String, String>> {

        private final Iterator<List<String>> iter;
        private final List<String> header;

        public MapsIterator(Iterator<List<String>> iter, List<String> header) {
            this.iter = iter;
            this.header = header;
        }

        @Override
        protected Map<String, String> getNext() {
            if (!iter.hasNext()) {
                return null;
            }
            Iterator<String> values = iter.next().iterator();
            Iterator<String> heads = header.iterator();

            Map<String, String> result = new LinkedHashMap<String, String>();
            while (heads.hasNext()) {
                String head = heads.next();
                String value = values.hasNext() ? values.next() : "";
                result.put(head, value);
            }
            return result;
        }

    }

    private class ListsIterator extends AbstractIterator<List<String>> {

        private final Scanner scanner;

        private ListsIterator(Reader reader) {
            this.scanner = new Scanner(reader);
        }

        @Override
        protected List<String> getNext() {
            return parseNextLine(scanner);
        }

    }

    private List<String> parseNextLine(Scanner scanner) {
        try {
            List<String> result = new LinkedList<String>();
            if (scanner.isEof()) {
                return null;
            }
            while (parseValue(scanner, result)) {
                // empty
            }
            return result;
        } catch (IOException e) {
            throw new CsvParserException("Failed to parse CSV file", e);
        }
    }

    /**
     * Returns <code>false</code> when end of line reached.
     */
    private boolean parseValue(Scanner scanner, List<String> result) throws IOException {
        StringBuilder builder = new StringBuilder();
        int ch;
        while ((ch = scanner.read()) != -1) {
            if (ch == fieldSep) {
                result.add(builder.toString());
                return true;
            } else if (ch == quoteChar) {
                parseQuotedValue(scanner, builder);
            } else if (ch == 13) {
                ch = scanner.read();
                if (ch != 10) {
                    scanner.unread(ch);
                }
                break;
            } else if (ch == 10) {
                break;
            } else {
                builder.append((char) ch);
            }
        }
        // end of line (or file) reached
        result.add(builder.toString());
        return false;
    }

    private void parseQuotedValue(Scanner scanner, StringBuilder builder) throws IOException {
        int ch;
        while ((ch = scanner.read()) != -1) {
            if (ch == quoteChar) {
                ch = scanner.read();
                if (ch != quoteChar) {
                    scanner.unread(ch);
                    break;
                }
            }
            builder.append((char) ch);
        }
    }

    /**
     * Allows reading by one character. Allows "unreading" at most one
     * character.
     */
    private static class Scanner {

        private final Reader reader;
        private int ch = -1;

        public Scanner(Reader reader) {
            this.reader = reader;
        }

        public int read() throws IOException {
            if (ch == -1) {
                return reader.read();
            }

            int result = ch;
            ch = -1;
            return result;
        }

        public void unread(int c) {
            if (ch != -1) {
                throw new IllegalStateException();
            }
            ch = c;
        }

        public boolean isEof() throws IOException {
            ch = this.read();
            return ch == -1;
        }
    }

}
