/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.neuron.Neuron;
import neuralnetwork.neuron.Synapse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Николай
 */
public class XMLParser implements NetworkParser {

    private final String path;

    public XMLParser(String path) {
        this.path = path;
    }

//    private PathElement createPathElement(String nameElement, Integer id) {
//        switch (nameElement) {
//            case "Switch":
//                return new Switch(id);
//            case "PC":
//                return new PC(id);
//            case "Router":
//                return new Router(id);
//            default:
//                throw new NullPointerException("Неизвестный элемент " + nameElement);
//        }
////    }
//    private PathElement parsePathElement(Node item) {
//        Integer id = null;
//        PathElement pathElement = null;
//        Double timeDelay = null;
//        Double costs = null;
//
//        String typeElement = item.getAttributes().item(0).getNodeValue();
//
//        for (int i = 0; i < item.getChildNodes().getLength(); i++) {
//            Node node = item.getChildNodes().item(i);
//            if (node instanceof Element) {
//
//                Element childElement = (Element) node;
//                Text textNode = (Text) childElement.getFirstChild();
//                String text = textNode.getData().trim();
//                switch (childElement.getTagName()) {
//                    case "id":
//                        id = Integer.parseInt(text);
//                        break;
//                    case "timedelay":
//                        timeDelay = Double.parseDouble(text);
//                        break;
//                    case "costs":
//                        costs = Double.parseDouble(text);
//                        break;
//                }
//            }
//        }
//        if (id != null) {
//            pathElement = createPathElement(typeElement, id);
//            pathElement.setCosts(costs);
//            pathElement.setTimeDelay(timeDelay);
//        }
//
//        return pathElement;
//    }
//    private void createConnections(Node nodeNetwork, Network network) {
//        NodeList nodePathElements = nodeNetwork.getChildNodes();
//        for (int i = 0; i < nodePathElements.getLength(); i++) {
//            if (nodePathElements.item(i) instanceof Element) {
//                Node pathElement = nodePathElements.item(i);
//                int id = 0;
//                for (int j = 0; j < pathElement.getChildNodes().getLength(); j++) {
//                    if (pathElement.getChildNodes().item(j) instanceof Element) {
//                        Element connections = (Element) pathElement.getChildNodes().item(j);
//                        if (connections.getTagName().equals("id")) {
//                            Text textNode = (Text) connections.getFirstChild();
//                            String text = textNode.getData().trim();
//                            id = Integer.parseInt(text);
//                        }
//                        if (connections.getTagName().equals("connections")) {
//                            addConections(connections, network, id);
//                        }
//                    }
//                }
//            }
//        }
//    }
//    private Node openXML(String fileName) {
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            File f = new File(fileName);
//            Document doc = builder.parse(f);
//            Element root = doc.getDocumentElement();
//            return root.getChildNodes().item(1);
//        } catch (SAXException | IOException | ParserConfigurationException ex) {
//            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//    public Network serializeXMLToNetwork() {
//        Node nw = openXML(this.path);
//        Network network = new Network();
//        NodeList itemNework = nw.getChildNodes();
//        for (int i = 0; i < itemNework.getLength(); i++) {
//            Node item = itemNework.item(i);
//            if (item instanceof Element) {
//                PathElement pathElement = parsePathElement(item);
//                if (pathElement != null) {
//                    network.add(pathElement);
//                }
//            }
//        }
//        if (!network.getPathElements().isEmpty()) {
//            createConnections(nw, network);
//        }
//        return network;
//
//
//
//
//    }
//    private void addConections(Element connections, Network network, int id) throws NumberFormatException, DOMException {
//        for (int k = 0; k < connections.getChildNodes().getLength(); k++) {
//            if (connections.getChildNodes().item(k) instanceof Element) {
//                Element connection = (Element) connections.getChildNodes().item(k);
//                Text textNode = (Text) connection.getFirstChild();
//                String text = textNode.getData().trim();
//                int conn = Integer.parseInt(text);
//                PathElement p = network.getItemId(id);
//                p.addConnection(network.getItemId(conn));
//            }
//        }
//    }
    @Override
    public NeuralNetwork getNetwork() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setNetwork(NeuralNetwork nn) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
            docBuilder = dbf.newDocumentBuilder();
            Document doc = docBuilder.getDOMImplementation().createDocument("", "network", null);
            Element root = doc.getDocumentElement();
            for (Neuron n : nn.inputNeurons) {
                Element input = doc.createElement("neuron");
                input.setAttribute("type", "input");

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(n.id)));
                input.appendChild(id);

                root.appendChild(input);
            }
            for (Neuron n : nn.hiddenNeurons) {
                Element hidden = doc.createElement("neuron");
                hidden.setAttribute("type", "hidden");

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(n.id)));
                hidden.appendChild(id);

                Element conn = doc.createElement("connections");
                hidden.appendChild(conn);
                for (Synapse syn : n.getInputsSynapse()) {
                    Neuron neuron = syn.getA();
                    if (neuron.id == n.id) {
                        neuron = syn.getB();
                    }
                    Element c = doc.createElement("connection");
                    c.setAttribute("weight", String.valueOf(syn.getWeight()));
                    c.appendChild(doc.createTextNode(String.valueOf(neuron.id)));
                    conn.appendChild(c);
                }

                root.appendChild(hidden);
            }
            for (Neuron n : nn.outputNeurons) {
                Element output = doc.createElement("neuron");
                output.setAttribute("type", "output");

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(n.id)));
                output.appendChild(id);

                Element conn = doc.createElement("connections");
                output.appendChild(conn);
                for (Synapse syn : n.getInputsSynapse()) {
                    Neuron neuron = syn.getA();
                    if (neuron.id == n.id) {
                        neuron = syn.getB();
                    }
                    Element c = doc.createElement("connection");
                    c.setAttribute("weight", String.valueOf(syn.getWeight()));
                    c.appendChild(doc.createTextNode(String.valueOf(neuron.id)));
                    conn.appendChild(c);
                }

                root.appendChild(output);
            }

            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(new File("as.xml")));
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
