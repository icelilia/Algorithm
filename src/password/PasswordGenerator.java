package password;

import string.MD5;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PasswordGenerator {
    private static String username;
    private static String timeStamp;
    private static List<String> applicationList;
    private static List<String> passwordList;

    // 每次需要更新密码，就运行一遍该程序
    public static void main(String[] args) {
        // 用户名
        initUsername();
        // 时间戳
        initTimeStamp();
        // 应用值
        initApplications();
        // 生成密码
        getPassword();
        // 输出密码
        output();
    }

    private static void initUsername() {
        username = "Spotted_Dog";
    }

    private static void initTimeStamp() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        timeStamp = simpleDateFormat.format(date);
    }

    private static void initApplications() {
        applicationList = new ArrayList<>();

        applicationList.add("QQ");
        applicationList.add("Wechat");
        applicationList.add("Bilibili");

        applicationList.add("Steam");
        applicationList.add("Blizzard");

        applicationList.add("OnePlus");
        applicationList.add("Microsoft");
        applicationList.add("JingDong");
        applicationList.add("Iyuba");
        applicationList.add("Google");
        applicationList.add("Baidu");
        applicationList.add("AliPay");
        applicationList.add("JetBrains");
        applicationList.add("Github");

        applicationList.add("ICBC_APP");

        applicationList.add("YY");

        applicationList.add("byr");
    }

    private static void getPassword() {
        passwordList = new ArrayList<>();
        for (String str : applicationList) {
            String temp = username + timeStamp + str;
            String password = MD5.stringToMD5(temp);
            assert password != null;
            password = password.substring(0, 15);
            passwordList.add(password);
        }
    }

    private static void output() {
        try {
            File outputFile = new File("C:\\Users\\Spotted_Dog\\Desktop\\password.txt");
            Writer fileWriter = new BufferedWriter(new FileWriter(outputFile));
            fileWriter.write(timeStamp + "\n");
            fileWriter.write("Application" + "\t" + "Password" + "\n");
            for (int i = 0; i < applicationList.size(); i++) {
                fileWriter.write(applicationList.get(i) + "\t" + "\t" + passwordList.get(i) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
