package net.fodev.foclassic.controller;

import net.fodev.foclassic.model.Fofnt;
import net.fodev.foclassic.model.Glyph;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public List<Character> getFontCharacterList(String filename) throws IOException {
        List<Character> fontCharacters = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        do {
            line = reader.readLine();
            if (line != null && line.length() > 2) {
                line = line.trim();
                if (line.startsWith("Letter '")) {
                    int length = "Letter '".length();
                    fontCharacters.add(line.charAt(length));
                } else {
                    //System.out.println(line);
                }
            }
        } while (line != null);
        return fontCharacters;
    }

    public Fofnt parseFontFromXmlFile(String filename) throws IOException {
        Fofnt fofnt = new Fofnt();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        do {
            line = reader.readLine();
            if (line != null && line.length() > 2) {
                line = line.trim();
                if (line.startsWith("<?xml")) {
                    System.out.println("XML version and other crap ignored.");
                    //  ignore
                } else if (line.startsWith("<Font")) {
                    line = line.replace(">", "/>");
                    fofnt = parseFontInfoFromXmlString(line);
                } else if (line.startsWith("<Char ")){
                    Glyph glyph = parseGlyphFromXmlString(line);
                    fofnt.getGlyphs().add(glyph);
                } else {
                    System.out.println("Ignoring line: " + line);
                }
            }
        } while (line != null);
        return fofnt;
    }

    public void writeFontToFile(Fofnt fofnt, String filename) throws IOException {
        FileWriter fw = new FileWriter(filename, false);
        fw.write(fofnt.toString());
        fw.close();
    }

    public Fofnt parseFontInfoFromXmlString(String line) {
        Fofnt fofnt = new Fofnt();
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(line)));
            String size = doc.getDocumentElement().getAttribute("size");
            String family =doc.getDocumentElement().getAttribute("family");
            String height = doc.getDocumentElement().getAttribute("height");
            String style = doc.getDocumentElement().getAttribute("style");
            fofnt.setSize(Integer.parseInt(size));
            fofnt.setFamily(family);
            fofnt.setHeight(Integer.parseInt(height));
            fofnt.setStyle(style);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return fofnt;
    }

    public Glyph parseGlyphFromXmlString(String line) {
        Glyph glyph = new Glyph();
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(line)));
            String code = doc.getDocumentElement().getAttribute("code");
            String rect =doc.getDocumentElement().getAttribute("rect");
            String width = doc.getDocumentElement().getAttribute("width");
            String offset = doc.getDocumentElement().getAttribute("offset");
            glyph = parseFromStringValues(code, width, rect, offset);
            //System.out.println(String.format("Parse from XML: code='%s', width=%s, rect=(%s)", code, width, rect));
        }
        catch (Exception e)
        {
            System.out.println(line);
            e.printStackTrace();

            String code = getTokenValue(line, "code=\"");
            String rect = getTokenValue(line, "rect=\"");
            String width = getTokenValue(line, "width=\"");
            String offset = getTokenValue(line, "offset=\"");

            System.out.println(String.format("code=%s, rect=%s, width=%s, offset=%s", code, rect, width, offset));

            glyph = parseFromStringValues(code, width, rect, offset);
        }
        return glyph;
    }

    private String getTokenValue(String line, String token) {
        int start = line.indexOf(token) + token.length();
        int end = line.indexOf("\"", start);
        return line.substring(start, end);
    }

    Glyph parseFromStringValues(String code, String width, String rect, String offset) {
        char c = code.charAt(0);
        int w = Integer.parseInt(width);
        String[] positions = rect.split(" ");
        int x = Integer.parseInt(positions[0]);
        int y = Integer.parseInt(positions[1]);
        int h = Integer.parseInt(positions[3]);
        String [] offsets = offset.split(" ");
        int offsetX = Integer.parseInt(offsets[0]);
        int offsetY = -Integer.parseInt(offsets[1]);
        Glyph glyph = new Glyph(c, x, y, w, h, offsetX, offsetY, w + 1);
        return glyph;
    }
}
