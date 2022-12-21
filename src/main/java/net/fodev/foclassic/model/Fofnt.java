package net.fodev.foclassic.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Fofnt {
    @Getter @Setter private String version;
    @Getter @Setter private String imageFile;
    @Getter @Setter private int lineHeight;
    @Getter @Setter private int yAdvance;
    @Getter @Setter private int size;
    @Getter @Setter private String family;
    @Getter @Setter private int height;
    @Getter @Setter private String style;
    @Getter @Setter private List<Glyph> glyphs;

    public Fofnt() {
        glyphs = new ArrayList<>();
    }

    @Override
    public String toString() {
        String fofntString = String.format("" +
                "Version    %s\n" +
                "Image      %s\n" +
                "LineHeight %d\n" +
                "YAdvance   %d\n" +
                "\n", version, imageFile, lineHeight, yAdvance);

        for (Glyph glyph : glyphs) {
            fofntString += glyph + "\n";
        }

        return fofntString;
    }
}
