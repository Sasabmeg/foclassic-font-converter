package net.fodev.foclassic;

import net.fodev.foclassic.controller.BfgParser;
import net.fodev.foclassic.model.Fofnt;
import net.fodev.foclassic.model.GlyphCorrection;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Fallout Online Classic - Font converter.");
        ArgumentParser argumentParser;
        argumentParser = setupArguments();

        Namespace ns = null;
        try {
            ns = argumentParser.parseArgs(args);
        } catch (ArgumentParserException e) {
            argumentParser.handleError(e);
            System.exit(1);
        }

        //  set bfg default type
        String type = ns.getString("type");
        if (!"fb".equalsIgnoreCase(type) && !"fofnt".equalsIgnoreCase(type)) {
            type = "bfg";
        }

        String inputFilename = ns.getString("inputFilename");
        if (inputFilename == null) {
            System.err.println("Missing input filename.");
            System.exit(1);
        }

        String outputFilename = ns.getString("outputFilename");
        if (outputFilename == null) {
            System.err.println("Missing output filename.");
            System.exit(1);
        }

        GlyphCorrection corrections = glyphCorrections(ns);
        int yAdvanceCorrection = getGlyphCorrectionIntegerParams(ns, "yAdvanceCorrection");
        int lineHeightCorrection = getGlyphCorrectionIntegerParams(ns, "lineHeightCorrection");

        if ("bfg".equals(type)) {
            System.out.println(String.format("Trying conversion on Bitmap File Generator created file %s, output as %s.", inputFilename, outputFilename));
            BfgParser parser = new BfgParser();
            Fofnt actual;
            try {
                actual = parser.parseFontFromXmlFile(inputFilename, corrections);
                actual.setVersion("2");
                actual.setLineHeight(actual.getLineHeight() + lineHeightCorrection);
                actual.setYAdvance(1 + yAdvanceCorrection);
                parser.writeFontToFile(actual, outputFilename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static ArgumentParser setupArguments() {
        ArgumentParser argumentParser;
        argumentParser = ArgumentParsers.newFor("foclassic-font-converter").build()
                .defaultHelp(true)
                .description("Fallout Online Classic - Font converter. " +
                        "Creates .fofnt files from various .xml font files. " +
                        "Some parameters can be adjusted, like: width, xAdvance.");
        argumentParser.addArgument("-t", "--type")
                .choices("fofnt", "bfg", "fb").setDefault("bfg")
                .help("Specify font generator used, default is Bitmap Font Generator (bfg), other option is Font Builder (fb). " +
                        "Make sure to use xml option when generating fonts.");
        argumentParser.addArgument("-in", "--inputFilename")
                .help("Specify the font file to be converted.");
        argumentParser.addArgument("-out", "--outputFilename")
                .help("Specify the converted font's filename.");
        argumentParser.addArgument("-xc", "--xCorrection")
                .help("Specify specify x correction.");
        argumentParser.addArgument("-yc", "--yCorrection")
                .help("Specify specify y correction.");
        argumentParser.addArgument("-wc", "--widthCorrection")
                .help("Specify specify width correction.");
        argumentParser.addArgument("-hc", "--heightCorrection")
                .help("Specify specify height correction.");
        argumentParser.addArgument("-oxc", "--offsetXCorrection")
                .help("Specify specify offsetX correction.");
        argumentParser.addArgument("-oyc", "--offsetYCorrection")
                .help("Specify specify offsetY correction.");
        argumentParser.addArgument("-xac", "--xAdvanceCorrection")
                .help("Specify specify xAdvance correction.");
        argumentParser.addArgument("-yac", "--yAdvanceCorrection")
                .help("Specify specify yAdvance correction.");
        argumentParser.addArgument("-lhc", "--lineHeightCorrection")
                .help("Specify specify lineHeight correction.");

        return argumentParser;
    }

    private static GlyphCorrection glyphCorrections(Namespace ns) {
        return new GlyphCorrection(
                getGlyphCorrectionIntegerParams(ns, "xCorrection"),
                getGlyphCorrectionIntegerParams(ns, "yCorrection"),
                getGlyphCorrectionIntegerParams(ns, "widthCorrection"),
                getGlyphCorrectionIntegerParams(ns, "heightCorrection"),
                getGlyphCorrectionIntegerParams(ns, "offsetXCorrection"),
                getGlyphCorrectionIntegerParams(ns, "offsetYCorrection"),
                getGlyphCorrectionIntegerParams(ns, "xAdvanceCorrection")
        );
    }

    private static int getGlyphCorrectionIntegerParams(Namespace ns, String paramName) {
        int paramValue = 0;
        String str = ns.getString(paramName);
        if (str != null) {
            try {
                paramValue = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                System.err.println("Number format exception for parameter: " + paramName);
            }
        }
        return paramValue;
    }
}
