package com.chat.server.model.chat;

import java.io.Serializable;

public class Style implements Serializable {
    private int id;
    private String fontName;
    private String fontFamily;
    private String fontColor;
    private String background;
    private float fontSize;
    private boolean bold;
    private boolean italic;
    private boolean underline;

    public Style() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    @Override
    public String toString() {
        return " -fx-font-name:" + fontName + ';' +
                " -fx-font-family:" + fontFamily + ';' +
                " -fx-fill:" + fontColor + ';' +
                " -fx-background-color:" + background + ';' +
                " -fx-font-size:" + fontSize + ";" + (
                bold || italic || underline ?
                        " -fx-font-weight :" + (bold ? "bold " : "") +
                                (italic ? "italic " : "") +
                                (underline ? " underline" : "") + ";" : "");

    }
}
