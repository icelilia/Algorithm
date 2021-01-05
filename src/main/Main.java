package main;

import aes.AES;
import string.MD5;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String option;
        String key;
        String IV;
        String inputFilePath;
        String outputFilePath;
        // 开始界面
        divider();
        System.out.println("AES Encrypt and Decrypt Program by Spotted Dog");
        divider();
        System.out.println("#       option");
        System.out.println("1       encrypt");
        System.out.println("2       decrypt");
        System.out.println("other   quit");
        divider();
        System.out.print("Please choose your option:");

        option = scanner.nextLine();
        if (option.contentEquals("1")) {
            divider();
            System.out.println("Input your password. Anything is OK, but You should remember it:");
            key = scanner.nextLine();
            key = MD5.stringToMD5(key);
            divider();
            System.out.println("Input your bias vector, the requirements are the same as above:");
            IV = scanner.nextLine();
            IV = MD5.stringToMD5(IV);
            divider();
            System.out.println("Input the full path of the input file that you want to encrypt:");
            inputFilePath = scanner.nextLine();
            divider();
            System.out.println("Input the full path of the output file. If it's empty, it will use a default value:");
            outputFilePath = scanner.nextLine();
            divider();
            if (outputFilePath.contentEquals("")) {
                try {
                    AES aes = new AES(key, IV, 1);
                    aes.encryptFile(inputFilePath);
                    System.out.println("OK, the file has been encrypted with the suffix '.aes' in the same directory.");
                } catch (Exception e) {
                    System.out.println("Ops, an unknown error occurred during encryption.");
                }
            } else {
                try {
                    AES aes = new AES(key, IV, 1);
                    aes.encryptFile(inputFilePath, outputFilePath);
                    System.out.println("OK, the file has been encrypted.");
                } catch (Exception e) {
                    System.out.println("Ops, an unknown error occurred during encryption.");
                }
            }
        } else if (option.contentEquals("2")) {
            divider();
            System.out.println("Input your password:");
            key = scanner.nextLine();
            key = MD5.stringToMD5(key);
            divider();
            System.out.println("Input your bias vector:");
            IV = scanner.nextLine();
            IV = MD5.stringToMD5(IV);
            divider();
            System.out.println("Input the full path of the input file that you want to decrypt:");
            inputFilePath = scanner.nextLine();
            divider();
            System.out.println("Input the full path of the output file:");
            outputFilePath = scanner.nextLine();
            divider();
            try {
                AES aes = new AES(key, IV, 1);
                aes.decryptFile(inputFilePath, outputFilePath);
                System.out.println("OK, the file has been decrypted.");
            } catch (Exception e) {
                System.out.println("Ops, an unknown error occurred during decryption.");
            }
        }
        divider();
        System.out.println("Press \"Enter\" to quit.");
        divider();
        option = scanner.nextLine();
        scanner.close();
    }

    private static void divider() {
        System.out.println("===================================================================================");
    }
}
