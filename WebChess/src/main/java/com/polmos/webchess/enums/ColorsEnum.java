package com.polmos.webchess.enums;

/**
 *
 * @author Piotrek
 */
public enum ColorsEnum {

    WHITE("WHITE"), BLACK("BLACK");

    ColorsEnum(String color) {
        this.colorName = color;
    }
    private String colorName;

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
