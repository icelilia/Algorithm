package main;

import aes.AES;

public class Main {

    public static void main(String[] args) {
        String key = "4307252000072919151234567890ABCD";
        String IV = "00000000000000000000000000000000";
        String plaintext = "日月昭昭，故国有明";
        short[] ciphertext;
        try {
            AES aes = new AES(key, IV, 0);

            System.out.println("明文：");
            System.out.println(plaintext);

            ciphertext = aes.encryptString(plaintext);
            System.out.println("密文：");
            for (short value : ciphertext) {
                System.out.printf("%02x", (byte) value);
            }
            System.out.println();

            plaintext = aes.decryptString(ciphertext);
            System.out.println("还原明文：");
            System.out.println(plaintext);

            ciphertext = aes.encryptFile("C:\\Users\\Spotted_Dog\\Desktop\\test.txt");
            System.out.println("文件密文：");
            for (short value : ciphertext) {
                System.out.printf("%02x", (byte) value);
            }
            System.out.println();

            aes.decryptFile("C:\\Users\\Spotted_Dog\\Desktop\\test.txt.aes", "C:\\Users\\Spotted_Dog\\Desktop\\test-new.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
