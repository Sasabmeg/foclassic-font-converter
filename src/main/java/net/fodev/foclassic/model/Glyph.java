package net.fodev.foclassic.model;

import lombok.Getter;
import lombok.Setter;

public class Glyph {
    @Getter @Setter private char code;
    @Getter @Setter private int x;
    @Getter @Setter private int y;
    @Getter @Setter private int width;
    @Getter @Setter private int height;
    @Getter @Setter private int offsetX;
    @Getter @Setter private int offsetY;
    @Getter @Setter private int xAdvance;

    public Glyph() {
    }

    public Glyph(char code, int x, int y, int width, int height, int offsetX, int offsetY, int xAdvance) {
        this.code = code;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.xAdvance = xAdvance;
    }

    @Override
    public String toString() {
        String fofntFormat = String.format("" +
                "##  Unicode: \\u%04x\n" +
                "Letter '%s'\n" +
                "  PositionX %d\n" +
                "  PositionY %d\n" +
                "  Width     %d\n" +
                "  Height    %d\n" +
                "  OffsetX   %d\n" +
                "  OffsetY   %d\n" +
                "  XAdvance  %d\n", (int)code, code, x, y, width, height, offsetX, offsetY, xAdvance);
        return fofntFormat;
    }
}
