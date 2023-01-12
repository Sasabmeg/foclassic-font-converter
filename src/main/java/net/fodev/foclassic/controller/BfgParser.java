package net.fodev.foclassic.controller;

import net.fodev.foclassic.model.Fofnt;
import net.fodev.foclassic.model.Glyph;
import net.fodev.foclassic.model.GlyphCorrection;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

public class BfgParser {


    public void writeFontToFile(Fofnt fofnt, String filename) throws IOException {
        FileWriter fw = new FileWriter(filename, false);
        fw.write(fofnt.toString());
        fw.close();
    }

    public Fofnt parseFontFromXmlFile(String filename, GlyphCorrection corrections) throws IOException {
        Fofnt fofnt = new Fofnt();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        do {
            line = reader.readLine();
            if (line != null && line.length() > 2) {
                line = line.trim();
                if (line.startsWith("<?xml") || line.startsWith("<font") || line.startsWith("<info")) {
                    System.out.println("XML version, font face info and other crap ignored.");
                    //  ignore
                } else if (line.startsWith("<common")) {
                    fofnt = parseFontInfoFromXmlString(line);
                } else if (line.startsWith("<page id")) {
                    fofnt.setImageFile(parseImageNameFromXmlString(line));
                } else if (line.startsWith("<char ")){
                    Glyph glyph = parseGlyphFromXmlString(line, corrections);
                    fofnt.getGlyphs().add(glyph);
                } else {
                    System.out.println("Ignoring line: " + line);
                }
            }
        } while (line != null);
        return fofnt;
    }

    private String parseImageNameFromXmlString(String line) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        String file = "N/A";
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(line)));
            file = doc.getDocumentElement().getAttribute("file");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return file;
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
            String lineHeight = doc.getDocumentElement().getAttribute("lineHeight");
            fofnt.setLineHeight(Integer.parseInt(lineHeight));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return fofnt;
    }

    public Glyph parseGlyphFromXmlString(String line, GlyphCorrection corrections) {
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
            String code = doc.getDocumentElement().getAttribute("id");
            String x = doc.getDocumentElement().getAttribute("x");
            String y = doc.getDocumentElement().getAttribute("y");
            String width = doc.getDocumentElement().getAttribute("width");
            String height = doc.getDocumentElement().getAttribute("height");
            String xOffset = doc.getDocumentElement().getAttribute("xoffset");
            String yOffset = doc.getDocumentElement().getAttribute("yoffset");
            String xAdvance = doc.getDocumentElement().getAttribute("xadvance");
            glyph = parseFromStringValues(code, x, y, width, height, xOffset, yOffset, xAdvance);
            glyph.setX(glyph.getX() + corrections.getX());
            glyph.setY(glyph.getY() + corrections.getY());
            glyph.setWidth(glyph.getWidth() + corrections.getWidth());
            glyph.setHeight(glyph.getHeight() + corrections.getHeight());
            glyph.setOffsetX(glyph.getOffsetX() + corrections.getOffsetX());
            glyph.setOffsetY(glyph.getOffsetY() + corrections.getOffsetY());
            glyph.setXAdvance(glyph.getXAdvance() + corrections.getXAdvance());
            /*
            System.out.println(String.format("Parse from XML: code='%s', x=%s, y=%s, width=%s, height=%s, xOffset=%s, yOffset=%s, xAdvance=%s, corrections=%s",
                    code, x, y, width, height, xOffset, yOffset, xAdvance, corrections));

             */
        }
        catch (Exception e)
        {
            System.out.println(line);
            e.printStackTrace();
            /*
            String code = getTokenValue(line, "code=\"");
            String rect = getTokenValue(line, "rect=\"");
            String width = getTokenValue(line, "width=\"");
            String offset = getTokenValue(line, "offset=\"");

            System.out.println(String.format("code=%s, rect=%s, width=%s, offset=%s", code, rect, width, offset));

            glyph = parseFromStringValues(code, width, rect, offset);

             */
        }
        return glyph;
    }

    Glyph parseFromStringValues(String code, String x, String y, String width, String height, String xOffset, String yOffset, String xAdvance) {
        char c = (char)Integer.parseInt(code);
        int xx = Integer.parseInt(x);
        int yy = Integer.parseInt(y);
        int w = Integer.parseInt(width);
        int h = Integer.parseInt(height);
        int oX = Integer.parseInt(xOffset);
        int oY = -Integer.parseInt(yOffset);
        //int xA = Integer.parseInt(xAdvance);
        Glyph glyph = new Glyph(c, xx, yy, w, h, oX, oY, w + 1);
        return glyph;
    }

}
