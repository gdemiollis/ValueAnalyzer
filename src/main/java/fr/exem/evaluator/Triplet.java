package fr.exem.evaluator;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Triplet {

    private Triplet() {
    }

    public static double getField(String trame) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        return Math.sqrt(Math.pow(getX(trame), 2) + Math.pow(getY(trame), 2) + Math.pow(getZ(trame), 2));
    }

//    public String formatValue(byte[] data) {
//        if (data != null && data.length > 0) {
//            final StringBuilder stringBuilder = new StringBuilder(data.length);
//            for (byte byteChar : data)
//                stringBuilder.append(String.format("%02X ", byteChar));
//            return new String(data) + "\n" + stringBuilder.toString();
//        }
//        return null;
//    }

    static int getX(String trame) {
        return hexatoDec(getXTrame(trame));
    }

    static String getXTrame(String trame) {
        return trame.substring(0, 4);
    }

    static int getY(String trame) {
        return hexatoDec(getYTrame(trame));
    }

    static String getYTrame(String trame) {
        return trame.substring(6, 10);
    }

    static int getZ(String trame) {
        return hexatoDec(getZTrame(trame));
    }

    static String getZTrame(String trame) {
        return trame.substring(12, 16);
    }

    static int hexatoDec(String hex) {
        return Integer.parseInt(hex, 16);
    }
}
