package net.fodev.foclassic.model;

import lombok.Getter;
import lombok.Setter;

public class GlyphCorrection {
    @Getter @Setter int x;
    @Getter @Setter int y;
    @Getter @Setter int width;
    @Getter @Setter int height;
    @Getter @Setter int offsetX;
    @Getter @Setter int offsetY;
    @Getter @Setter int xAdvance;

    public GlyphCorrection(int x, int y, int width, int height, int offsetX, int offsetY, int xAdvance) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.xAdvance = xAdvance;
    }

    public GlyphCorrection(int width, int xAdvance) {
        this.width = width;
        this.xAdvance = xAdvance;
    }

    public GlyphCorrection() {
    }

    @Override
    public String toString() {
        return "GlyphCorrection{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", xAdvance=" + xAdvance +
                "}";
    }
}
