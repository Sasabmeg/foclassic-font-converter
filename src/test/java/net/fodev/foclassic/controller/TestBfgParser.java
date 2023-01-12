package net.fodev.foclassic.controller;

import net.fodev.foclassic.model.Fofnt;
import net.fodev.foclassic.model.GlyphCorrection;
import org.junit.Test;

import java.io.IOException;

public class TestBfgParser {
    BfgParser parser = new BfgParser();

    @Test
    public void parseFontFromXmlFile_single_valid() throws IOException {
        String filename = "default_125";
        GlyphCorrection corrections = new GlyphCorrection();
        corrections.setWidth(-2);
        corrections.setXAdvance(-2);
        Fofnt actual = parser.parseFontFromXmlFile("src/main/resources/fonts/" + filename + ".fnt", corrections);
        actual.setVersion("2");
        actual.setImageFile(filename + ".png");
        actual.setLineHeight(14);
        actual.setYAdvance(1);
        parser.writeFontToFile(actual, "out/" + filename + ".fofnt");
    }
}
