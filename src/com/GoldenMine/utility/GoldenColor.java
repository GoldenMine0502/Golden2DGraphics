package com.GoldenMine.utility;

import java.awt.*;

public class GoldenColor {
    int R;
    int G;
    int B;
    int A;

    public GoldenColor(int RGB) {
        setRGB(RGB);
    }

    public Color toColor() {
        return new Color(getRGB(), true);
    }

    public void setRGB(int RGB) {
        A = (RGB >> 24) & 0xFF;
        R = (RGB >> 16) & 0xFF;
        G = (RGB >> 8) & 0xFF;
        B = RGB & 0xFF;
    }

    public int getRGB() {
        return A << 24 | R << 16 | G << 8 | B;
    }

    public int getR() {
        return R;
    }

    public int getG() {
        return G;
    }

    public int getB() {
        return B;
    }

    public int getA() {
        return A;
    }

    public void setR(int R) {
        this.R = R;
    }

    public void setG(int G) {
        this.G = G;
    }

    public void setB(int B) {
        this.G = G;
    }

    public void setA(int A) {
        this.A = A;
    }

    public void addR(int R) {
        this.R |= R;
    }

    public void addG(int G) {
        this.G |= G;
    }

    public void addB(int B) {
        this.B |= B;
    }

    public void addA(int A) {
        this.A |= A;
    }
}
