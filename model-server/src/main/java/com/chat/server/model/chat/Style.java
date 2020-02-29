package com.chat.server.model.chat;

import java.io.Serializable;

public class Style implements Serializable {
    private int id;
    private String fontName;
    private String fontFamily;
    private String fontColor;
    private String background;
    private int fontSize;
    private boolean bold;
    private boolean italic;
    private boolean underline;

    public Style() {
    }

    public Style(String fontName, String fontFamily, String fontColor,
                 String background, int fontSize, boolean bold, boolean italic, boolean underline) {
        this.fontName = fontName;
        this.fontFamily = fontFamily;
        this.fontColor = fontColor;
        this.background = background;
        this.fontSize = fontSize;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
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

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
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
                " -fx-text-fill:" + fontColor + ';' +
                " -fx-background-color:" + background + ';' +
                " -fx-font-size:" + fontSize + ";" + (
                bold || italic || underline ?
                        " -fx-font-weight :" + (bold ? "bold " : "") +
                                (italic ? "italic " : "") +
                                (underline ? " underline" : "") + ";" : "");

    }
}
