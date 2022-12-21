package net.fodev.foclassic.controller;

import net.fodev.foclassic.model.Fofnt;
import net.fodev.foclassic.model.Glyph;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TestParser {
    Parser parser = new Parser();
    String xml1 = " <Char width=\"11\" offset=\"-1 8\" rect=\"160 2 11 12\" code=\"/\"/>\n";

    @Test
    public void getFontCharacterList_Single_Valid() throws IOException {
        List<Character> letters = parser.getFontCharacterList("src/main/resources/Default.fofnt");
        FileWriter fw = new FileWriter("out/letters.txt", false);
        letters.stream().sorted().forEach(l -> {
            String line = String.format("%s - \\u%04x", l, (int)l);
            System.out.println(line);
            try {
                fw.write(line + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fw.close();
    }

    @Test
    public void getFontCharacterList_SampleSameLine_Valid() throws IOException {
        List<Character> letters = parser.getFontCharacterList("src/main/resources/Default.fofnt");
        FileWriter fw = new FileWriter("out/letters_sample.txt", false);
        letters.stream().sorted().forEach(l -> {
            String line = String.format("%s", l);
            System.out.println(line);
            try {
                fw.write(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fw.close();
    }

    @Test
    public void parseGlyphFromXmlString_Single_Valid() {
        System.out.println(xml1);
        Glyph actual = parser.parseGlyphFromXmlString(xml1);
        System.out.println(actual);
    }

    @Test
    public void parseFontInfoFromXmlFile_Single_Valid() throws IOException {
        Fofnt actual = parser.parseFontFromXmlFile("src/main/resources/default_14.xml");
        System.out.println(actual);
    }

    @Test
    public void writeFontToFile_Single_Valid() throws IOException {
        Fofnt actual = parser.parseFontFromXmlFile("src/main/resources/default_14.xml");
        actual.setVersion("2");
        actual.setImageFile("Default.png");
        actual.setLineHeight(15);
        actual.setYAdvance(1);
        parser.writeFontToFile(actual, "out/default_14.fofnt");
    }
}
