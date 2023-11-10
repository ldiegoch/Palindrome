package Palindrome;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Store {
    private final String LOG_FILE = "errors.log";
    private final String CONFIG_FILE = "config.properties";
    
    public void saveToXML(String file, ArrayList<Boolean> answers) {
        Document dom = getDocument(answers);
        try{
            Transformer tranformer = TransformerFactory.newInstance().newTransformer();
            tranformer.setOutputProperty(OutputKeys.INDENT, "yes");
            tranformer.setOutputProperty(OutputKeys.METHOD, "xml");
            tranformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tranformer.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(file)));
        } catch (TransformerException te) {
            writeLog("Ocurrio un Error al convertir el resultado a un archivo. Vuelva a intentarlo con un nuevo archivo");
            System.out.println(te.getMessage());
        } catch (IOException ioe) {
            writeLog("Ocurrio un Error al guardar el archivo xml. Favor revise el archivo de salida.");
            System.out.println(ioe.getMessage());
        }
    }

    private Document getDocument(ArrayList<Boolean> answers){
        Document dom;
        Element answerElement = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try{
            int index = 0;
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            dom = documentBuilder.newDocument();

            Element answersElements = dom.createElement("answers");

            for (Boolean answer : answers) {
                answerElement = dom.createElement("answer" + index++);
                answerElement.appendChild(
                    dom.createTextNode(String.valueOf(answer))
                );
                answersElements.appendChild(answerElement);
            }

            dom.appendChild(answersElements);
            return dom;
        } catch(Exception ex) {
            this.writeLog("Ocurrion un eror mientras guardamos las respuestas. Por favor verifique no haya ningun problema con el archivo de salida.");
            return null;
        }
    }

    public ArrayList<String> readFromXML(String file) {
        ArrayList<String> texts = new ArrayList<String>();
        Document dom;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            dom = documentBuilder.parse(file);
            Element doc = dom.getDocumentElement();
            doc.normalize();
            Node node = doc.getFirstChild();
            while(node != null){
                if(node.getNodeName() != "#text"){
                    String content = node.getTextContent();
                    texts.add(content); 
                }
                node = node.getNextSibling();
            }
            return texts;
        } catch (ParserConfigurationException pce) {
            writeLog("Ocurrio un Error al procesar el archivo xml. Favor revise el archivo de entrada.");
            System.out.println(pce.getMessage());
            return null;
        } catch (SAXException se) {
            writeLog(se.getMessage());
            System.out.println(se.getMessage());
            return null;
        } catch (IOException ioe) {
            writeLog("Ocurrio un Error al obtener el archivo xml. Favor revise el archivo de entrada.");
            System.err.println(ioe.getMessage());
            return null;
        }
    }

    public boolean existFile(String file){
        File fileObj = new File(file);
        return fileObj.exists() && !fileObj.isDirectory();
    }

    public void writeLog(String msg){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE));
            writer.append(msg);
            writer.close();
        }catch(IOException ioError){
            System.out.println(ioError.getMessage());
        }
    }

    public Properties loadConfig(){
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(CONFIG_FILE));
            return properties;
        }catch(IOException ioError){
            writeLog("Ocurrio un error al leer el archivo de configuracion: " + CONFIG_FILE);
            return null;
        }
        
    }
}
