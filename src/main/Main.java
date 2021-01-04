package main;

import aes.AES;

import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        AES aes;
        try {
            aes = new AES("4307252000072919151234567890ABCD", "00000000000000000000000000000000");
        } catch (Exception e) {
            return;
        }
        byte[] originalP = "日月昭昭，故国有明".getBytes(StandardCharsets.UTF_8);
        short[] c = aes.encrypt("日月昭昭，故国有明");
        System.out.println("原始明文数组：");
        for (byte value : originalP) {
            System.out.printf("%02x ", value);
        }
        System.out.println();
        System.out.println("密文数组：");
        for (short value : c) {
            System.out.printf("%02x ", (byte) value);
        }
        System.out.println();
        System.out.println("明文字符串：");
        System.out.println(aes.decrypt(c));
    }
}
