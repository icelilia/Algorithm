package aes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 简单的AES算法类
 * </p>
 * <p>
 * 仅考虑CBC模式
 * </p>
 *
 * @author Spotted_Dog
 */
// 注意，由于Java中不存在无符号byte类型的数据，所以使用16位的short类型来储存byte
public class AES {
    private final short[] key;
    private final short[] IV;
    private final int mode;
    // 轮密钥二维数组
    private final short[][] roundKey = new short[11][16];
    // 文件分割符常量
    private final static String SEPARATOR = File.separator;
    // 加密文件时原始ArrayList的默认大小
    private final static int DEFAULT_FILE_LENGTH = 1024 * 1024 * 64;

    /**
     * <p>
     * key为密钥字符串，IV为初始向量字符串，二者的格式均为：^[0-9a-fA-F]{32}$
     * </p>
     * <p>
     * 二者均不区分大小写，任何不匹配的情况都会导致抛出异常
     * </p>
     * <p>
     * 分组连接的模式，0为ECB，1为CBC，其余则会抛出异常
     * </p>
     *
     * @param key  密钥字符串
     * @param IV   初始向量字符串
     * @param mode 分组连接的模式
     */
    public AES(String key, String IV, int mode) {
        this.key = stringToBytes(key);
        this.IV = stringToBytes(IV);
        this.mode = mode;
        // 初始化轮密钥
        initRoundKey();
    }

    /**
     * 初始化轮密钥
     */
    private void initRoundKey() {
        // 第一个轮密钥就是密钥本身
        System.arraycopy(key, 0, roundKey[0], 0, 16);
        // 十轮轮密钥
        for (int turn = 1; turn <= 10; turn++) {
            System.arraycopy(getRoundKey(turn), 0, roundKey[turn], 0, 16);
        }
    }

    /**
     * 获取轮密钥
     *
     * @param turn 当前轮数
     * @return 轮密钥数组
     */
    private short[] getRoundKey(int turn) {
        short[] res = new short[16];
        // 分段
        short[] w0 = new short[4];
        short[] w1 = new short[4];
        short[] w2 = new short[4];
        short[] w3 = new short[4];
        System.arraycopy(roundKey[turn - 1], 0, w0, 0, 4);
        System.arraycopy(roundKey[turn - 1], 4, w1, 0, 4);
        System.arraycopy(roundKey[turn - 1], 8, w2, 0, 4);
        System.arraycopy(roundKey[turn - 1], 12, w3, 0, 4);
        // 计算
        short[] temp;
        temp = xor(w0, g(w3, turn));
        System.arraycopy(temp, 0, res, 0, 4);
        temp = xor(temp, w1);
        System.arraycopy(temp, 0, res, 4, 4);
        temp = xor(temp, w2);
        System.arraycopy(temp, 0, res, 8, 4);
        temp = xor(temp, w3);
        System.arraycopy(temp, 0, res, 12, 4);
        return res;
    }

    private final short[] Rcon = {
            0x00, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36
    };

    /**
     * g函数
     *
     * @param w    待处理的4位byte数组
     * @param turn 当前轮数
     * @return 处理后的4位byte数组
     */
    private short[] g(short[] w, int turn) {
        short[] res = new short[4];
        System.arraycopy(w, 0, res, 0, 4);
        short temp = res[0];
        res[0] = res[1];
        res[1] = res[2];
        res[2] = res[3];
        res[3] = temp;
        for (int i = 0; i < 4; i++) {
            res[i] = subBytes(res[i]);
        }
        res[0] = (short) (res[0] ^ Rcon[turn]);
        return res;
    }

    /**
     * 将32位字符串byte数组化
     *
     * @param str 32位字符串
     * @return 16位byte数组
     */
    private short[] stringToBytes(String str) {
        short[] res = new short[16];
        char[] chars = str.toUpperCase().toCharArray();
        for (int i = 0; i < 32; i += 2) {
            short temp = 0;
            if ('0' <= chars[i] && chars[i] <= '9') {
                temp += (chars[i] - '0');
            } else if ('A' <= chars[i] && chars[i] <= 'F') {
                temp += (chars[i] - 'A' + 10);
            }
            temp <<= 4;
            if ('0' <= chars[i + 1] && chars[i + 1] <= '9') {
                temp += (chars[i + 1] - '0');
            } else if ('A' <= chars[i + 1] && chars[i + 1] <= 'F') {
                temp += (chars[i + 1] - 'A' + 10);
            }
            res[i / 2] = temp;
        }
        return res;
    }

    /**
     * 16位byte数组-->4*4byte矩阵
     *
     * @param o 16位byte数组
     * @return 4*4byte矩阵
     */
    private short[][] bytesToMatrix(short[] o) {
        short[][] res = new short[4][4];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                res[i][j] = o[j * 4 + i];
            }
        }
        return res;
    }

    /**
     * 4*4byte矩阵-->16位byte数组
     *
     * @param o 4*4byte矩阵
     * @return 16位byte数组
     */
    private short[] matrixToBytes(short[][] o) {
        short[] res = new short[16];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                res[j * 4 + i] = o[i][j];
            }
        }
        return res;
    }

    /**
     * PKCS7Padding填充
     *
     * @param o 待填充的数组
     * @return 填充后的数组
     */
    private short[] PKCS7Padding(short[] o) {
        // 填充
        int length = o.length;
        if (length % 16 == 0) {
            int newLength = length + 16;
            short[] res = new short[newLength];
            System.arraycopy(o, 0, res, 0, length);
            for (int i = length; i < newLength; i++) {
                res[i] = 16;
            }
            return res;
        } else {
            int newLength = length + (16 - (length % 16));
            short[] res = new short[newLength];
            System.arraycopy(o, 0, res, 0, length);
            for (int i = length; i < newLength; i++) {
                res[i] = (short) (16 - (length % 16));
            }
            return res;
        }
    }

    /**
     * <p>
     * 去除PKCS7Padding填充
     * </p>
     * <p>
     * 顺带将short数组转为byte数组
     * </p>
     *
     * @param o 已填充的数组
     * @return 去除填充后的数组
     */
    private byte[] PKCS7Padding_(short[] o) {
        short temp = o[o.length - 1];
        byte[] res = new byte[o.length - temp];
        for (int i = 0; i < res.length; i++) {
            res[i] = (byte) o[i];
        }
        return res;
    }

    /**
     * byte数组间的异或运算
     *
     * @param a 数组a
     * @param b 数组b
     * @return 结果数组
     */
    private short[] xor(short[] a, short[] b) {
        short[] res = new short[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = (short) ((byte) a[i] ^ (byte) b[i]);
        }
        return res;
    }

    /**
     * 预处理，将byte数组转化为short数组，方便后续操作
     *
     * @param o 待转化的byte数组
     * @return 转化后的short数组
     */
    private short[] pretreatment(byte[] o) {
        short[] res = new short[o.length];
        for (int i = 0; i < o.length; i++) {
            res[i] = (short) (o[i] & 0xFF);
        }
        return res;
    }

    /**
     * <p>
     * 加密字符串的接口
     * </p>
     * <p>
     * 使用PKCS7Padding填充，UTF-8编码
     * </p>
     *
     * @param plaintextString 待加密的明文字符串
     * @return 加密后的密文数组
     */
    public short[] encryptString(String plaintextString) {
        // 先获得明文字符串的UTF-8编码的byte数组
        byte[] originalPlaintextBytes = plaintextString.getBytes(StandardCharsets.UTF_8);
        // 预处理，扩展为short数组
        short[] originalPlaintextShorts = pretreatment(originalPlaintextBytes);
        // PKCS7Padding填充
        short[] plaintext = PKCS7Padding(originalPlaintextShorts);
        // 分组数组
        short[] group = new short[16];
        short[] lastGroup = new short[16];
        // 密文数组
        short[] ciphertext = new short[plaintext.length];
        // CBC分组连接模式
        if (mode == 1) {
            // 只有一个分组
            if (plaintext.length == 16) {
                System.arraycopy(plaintext, 0, group, 0, 16);
                // 1 与初始向量IV做异或
                group = xor(IV, group);
                // 2 AES十轮加密
                group = aes(group);
                System.arraycopy(group, 0, ciphertext, 0, 16);
            }
            // 多个分组
            else {
                // 视初始向量为上一个分组的密文
                System.arraycopy(IV, 0, lastGroup, 0, 16);
                for (int i = 0; i < plaintext.length; i += 16) {
                    System.arraycopy(plaintext, i, group, 0, 16);
                    // 1 与上一分组的密文做异或
                    group = xor(lastGroup, group);
                    // 2 AES十轮加密
                    group = aes(group);
                    System.arraycopy(group, 0, ciphertext, i, 16);
                    // 3 更新lastGroup
                    System.arraycopy(group, 0, lastGroup, 0, 16);
                }
            }
        }
        // ECB分组连接模式
        else if (mode == 0) {
            for (int i = 0; i < plaintext.length; i += 16) {
                System.arraycopy(plaintext, i, group, 0, 16);
                // 1 AES十轮加密
                group = aes(group);
                System.arraycopy(group, 0, ciphertext, i, 16);
            }
        }
        return ciphertext;
    }

    /**
     * <p>
     * 加密文件的接口
     * </p>
     * <p>
     * 使用PKCS7Padding填充
     * </p>
     * <p>
     * 过程中会默认在同一文件夹下生成"源文件名 + .aes"的加密文件
     * </p>
     *
     * @param inputFilePath 待加密文件的路径
     * @return 密文数组
     * @throws IOException 文件流相关的IO异常
     */
    public short[] encryptFile(String inputFilePath) throws IOException {
        // 构建File对象的目的：
        // 1. 快速验证inputFile是否存在
        // 2. 配合getAbsolutePath去除inputFilePath末尾可能存在的路径分隔符
        File inputFile = new File(inputFilePath);
        String outputFilePath = inputFile.getAbsolutePath() + ".aes";
        // 获取文件流
        InputStream fileInputStream = new BufferedInputStream(new FileInputStream(inputFile));
        OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(outputFilePath));
        // ArrayList暂存数据
        List<Short> originalShortList = new ArrayList<>(DEFAULT_FILE_LENGTH);
        int b = 0;
        while ((b = fileInputStream.read()) != -1) {
            originalShortList.add((short) b);
        }
        // short数组
        short[] originalShorts = new short[originalShortList.size()];
        for (int i = 0; i < originalShorts.length; i++) {
            originalShorts[i] = originalShortList.get(i);
        }
        // PKCS7Padding填充
        short[] plaintext = PKCS7Padding(originalShorts);
        // 分组数组
        short[] group = new short[16];
        short[] lastGroup = new short[16];
        // 密文数组
        short[] ciphertext = new short[plaintext.length];
        if (mode == 1) {
            // 只有一个分组
            if (plaintext.length == 16) {
                System.arraycopy(plaintext, 0, group, 0, 16);
                // 1 与初始向量IV做异或
                group = xor(IV, group);
                // 2 AES十轮加密
                group = aes(group);
                System.arraycopy(group, 0, ciphertext, 0, 16);
            } else {
                System.arraycopy(IV, 0, lastGroup, 0, 16);
                for (int i = 0; i < plaintext.length; i += 16) {
                    System.arraycopy(plaintext, i, group, 0, 16);
                    // 1 与上一分组的密文做异或
                    group = xor(lastGroup, group);
                    // 2 AES十轮加密
                    group = aes(group);
                    System.arraycopy(group, 0, ciphertext, i, 16);
                    // 3 更新lastGroup
                    System.arraycopy(group, 0, lastGroup, 0, 16);
                }
            }
        } else if (mode == 0) {
            for (int i = 0; i < plaintext.length; i += 16) {
                System.arraycopy(plaintext, i, group, 0, 16);
                // 1 AES十轮加密
                group = aes(group);
                System.arraycopy(group, 0, ciphertext, i, 16);
            }
        }
        for (short value : ciphertext) {
            fileOutputStream.write(value);
        }
        fileInputStream.close();
        fileOutputStream.close();
        return ciphertext;
    }

    /**
     * <p>
     * 加密文件的接口
     * </p>
     * <p>
     * 使用PKCS7Padding填充
     * </p>
     * <p>
     * 可以指定加密后生成的文件路径
     * </p>
     *
     * @param inputFilePath  待加密文件的路径
     * @param outputFilePath 加密后文件的路径
     * @return 密文数组
     * @throws IOException 文件流相关的IO异常
     */
    public short[] encryptFile(String inputFilePath, String outputFilePath) throws IOException {
        // 获取文件流
        InputStream fileInputStream = new BufferedInputStream(new FileInputStream(inputFilePath));
        OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(outputFilePath));
        // ArrayList暂存数据
        List<Short> originalShortList = new ArrayList<>(DEFAULT_FILE_LENGTH);
        int b = 0;
        while ((b = fileInputStream.read()) != -1) {
            originalShortList.add((short) b);
        }
        // short数组赋值
        short[] originalShorts = new short[originalShortList.size()];
        for (int i = 0; i < originalShorts.length; i++) {
            originalShorts[i] = originalShortList.get(i);
        }
        // PKCS7Padding填充
        short[] plaintext = PKCS7Padding(originalShorts);

        short[] group = new short[16];
        short[] lastGroup = new short[16];
        // 密文数组
        short[] ciphertext = new short[plaintext.length];
        // 只有一个分组
        if (plaintext.length == 16) {
            System.arraycopy(plaintext, 0, group, 0, 16);
            // 1 与初始向量IV做异或
            group = xor(IV, group);
            // 2 AES十轮加密
            group = aes(group);
            System.arraycopy(group, 0, ciphertext, 0, 16);
        } else {
            System.arraycopy(IV, 0, lastGroup, 0, 16);
            for (int i = 0; i < plaintext.length; i += 16) {
                System.arraycopy(plaintext, i, group, 0, 16);
                // 1 与上一分组的密文做异或
                group = xor(lastGroup, group);
                // 2 AES十轮加密
                group = aes(group);
                System.arraycopy(group, 0, ciphertext, i, 16);
                // 3 更新lastGroup
                System.arraycopy(group, 0, lastGroup, 0, 16);
            }
        }
        for (short value : ciphertext) {
            fileOutputStream.write(value);
        }
        fileInputStream.close();
        fileOutputStream.close();
        return ciphertext;
    }

    /**
     * AES算法十轮加密
     *
     * @param group 预处理后的分组
     * @return 加密后的密文分组
     */
    private short[] aes(short[] group) {
        short[][] temp = bytesToMatrix(group);
        // 初始化，漂白
        addRoundKey(temp, bytesToMatrix(roundKey[0]));
        // 九轮普通轮
        for (int turn = 1; turn <= 9; turn++) {
            // 1 字节替换
            subBytesForMatrix(temp);
            // 2 行移位
            shiftRows(temp);
            // 3 列混合
            mixColumns(temp);
            // 4 轮密钥相加
            addRoundKey(temp, bytesToMatrix(roundKey[turn]));
        }
        // 字节替换
        subBytesForMatrix(temp);
        // 行移位
        shiftRows(temp);
        // 轮密钥相加
        addRoundKey(temp, bytesToMatrix(roundKey[10]));
        return matrixToBytes(temp);
    }

    /**
     * <p>
     * 解密字符串的接口
     * </p>
     * <p>
     * 使用PKCS7Padding填充，UTF-8编码
     * </p>
     *
     * @param ciphertext 密文数组
     * @return 明文字符串
     */
    public String decryptString(short[] ciphertext) {
        // 分组数组
        short[] group = new short[16];
        short[] lastGroup = new short[16];
        // 明文数组
        short[] plaintext = new short[ciphertext.length];
        if (mode == 1) {
            // 只有一个分组
            if (ciphertext.length == 16) {
                System.arraycopy(ciphertext, 0, group, 0, 16);
                // 1 AES十轮解密
                group = aes_(group);
                // 2 与初始向量IV做异或
                group = xor(IV, group);
                // 3 保存明文分组至p
                System.arraycopy(group, 0, plaintext, 0, 16);
            } else {
                System.arraycopy(IV, 0, lastGroup, 0, 16);
                for (int i = 0; i < plaintext.length; i += 16) {
                    System.arraycopy(ciphertext, i, group, 0, 16);
                    // 1 AES十轮解密
                    group = aes_(group);
                    // 2 与上一分组的密文做异或
                    group = xor(lastGroup, group);
                    // 3 更新lastGroup
                    System.arraycopy(ciphertext, i, lastGroup, 0, 16);
                    // 4 保存明文分组至p
                    System.arraycopy(group, 0, plaintext, i, 16);
                }
            }
        } else if (mode == 0) {
            for (int i = 0; i < plaintext.length; i += 16) {
                System.arraycopy(ciphertext, i, group, 0, 16);
                // 1 AES十轮解密
                group = aes_(group);
                // 2 保存明文分组至p
                System.arraycopy(group, 0, plaintext, i, 16);
            }
        }
        // 去除填充
        byte[] originalPlaintextBytes = PKCS7Padding_(plaintext);
        // 还原编码
        return new String(originalPlaintextBytes, StandardCharsets.UTF_8);
    }

    /**
     * <p>
     * 解密文件的接口
     * </p>
     * <p>
     * 使用PKCS7Padding填充
     * </p>
     *
     * @param inputFilePath 加密文件的路径
     */
    public void decryptFile(String inputFilePath, String outputFilePath) throws IOException {
        InputStream fileInputStream = new BufferedInputStream(new FileInputStream(inputFilePath));
        OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(outputFilePath));
        // ArrayList暂存数据
        List<Short> originalShortList = new ArrayList<>(DEFAULT_FILE_LENGTH);
        int b = 0;
        while ((b = fileInputStream.read()) != -1) {
            originalShortList.add((short) b);
        }
        // ciphertext数组赋值
        short[] ciphertext = new short[originalShortList.size()];
        for (int i = 0; i < ciphertext.length; i++) {
            ciphertext[i] = originalShortList.get(i);
        }
        // 分组数组
        short[] group = new short[16];
        short[] lastGroup = new short[16];
        // 明文数组
        short[] plaintext = new short[ciphertext.length];
        if (mode == 1) {
            // 只有一个分组
            if (ciphertext.length == 16) {
                System.arraycopy(ciphertext, 0, group, 0, 16);
                // 1 AES十轮解密
                group = aes_(group);
                // 2 与初始向量IV做异或
                group = xor(IV, group);
                // 3 保存明文分组至p
                System.arraycopy(group, 0, plaintext, 0, 16);
            } else {
                System.arraycopy(IV, 0, lastGroup, 0, 16);
                for (int i = 0; i < plaintext.length; i += 16) {
                    System.arraycopy(ciphertext, i, group, 0, 16);
                    // 1 AES十轮解密
                    group = aes_(group);
                    // 2 与上一分组的密文做异或
                    group = xor(lastGroup, group);
                    // 3 更新lastGroup
                    System.arraycopy(ciphertext, i, lastGroup, 0, 16);
                    // 4 保存明文分组至p
                    System.arraycopy(group, 0, plaintext, i, 16);
                }
            }
        } else if (mode == 0) {
            for (int i = 0; i < plaintext.length; i += 16) {
                System.arraycopy(ciphertext, i, group, 0, 16);
                // 1 AES十轮解密
                group = aes_(group);
                // 2 保存明文分组至p
                System.arraycopy(group, 0, plaintext, i, 16);
            }
        }
        // 去除填充
        byte[] originalPlaintextBytes = PKCS7Padding_(plaintext);
        for (byte value : originalPlaintextBytes) {
            fileOutputStream.write(value);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    /**
     * AES算法十轮解密
     *
     * @param group 预处理后的分组
     * @return 解密后的分组
     */
    private short[] aes_(short[] group) {
        short[][] temp = bytesToMatrix(group);
        // 初始化
        addRoundKey(temp, bytesToMatrix(roundKey[10]));
        // 九轮普通轮
        for (int turn = 9; turn >= 1; turn--) {
            // 1.逆行移位
            shiftRows_(temp);
            // 2.逆字节替换
            subBytesForMatrix_(temp);
            // 3.轮密钥相加
            addRoundKey(temp, bytesToMatrix(roundKey[turn]));
            // 4.逆列混合
            mixColumns_(temp);
        }
        // 逆行移位
        shiftRows_(temp);
        // 逆字节替换
        subBytesForMatrix_(temp);
        // 轮密钥相加
        addRoundKey(temp, bytesToMatrix(roundKey[0]));
        return matrixToBytes(temp);
    }

    /**
     * 轮密钥相加
     *
     * @param o 操作矩阵
     * @param p 密钥矩阵
     */
    private void addRoundKey(short[][] o, short[][] p) {
        short[][] temp = new short[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // 异或运算
                temp[i][j] = (short) (o[i][j] ^ p[i][j]);
            }
        }
        for (int i = 0; i < 4; i++) {
            System.arraycopy(temp[i], 0, o[i], 0, 4);
        }
    }

    private final short[][] S = {
            {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76},
            {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0},
            {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15},
            {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75},
            {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84},
            {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF},
            {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8},
            {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2},
            {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73},
            {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB},
            {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79},
            {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08},
            {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A},
            {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E},
            {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF},
            {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}
    };

    /**
     * 字节替换
     *
     * @param x 待替换的字节
     * @return 替换后的字节
     */
    public short subBytes(short x) {
        short xh = (short) ((x & 0x00F0) >> 4);
        short xl = (short) (x & 0x000F);
        return S[xh][xl];
    }

    /**
     * 矩阵中所有元素字节替换
     *
     * @param o 待替换的矩阵
     */
    private void subBytesForMatrix(short[][] o) {
        short[][] temp = new short[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[i][j] = subBytes(o[i][j]);
            }
        }
        for (int i = 0; i < 4; i++) {
            System.arraycopy(temp[i], 0, o[i], 0, 4);
        }
    }

    private final short[][] S_ = {
            {0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB},
            {0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB},
            {0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E},
            {0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25},
            {0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92},
            {0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84},
            {0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06},
            {0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B},
            {0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73},
            {0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E},
            {0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B},
            {0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4},
            {0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F},
            {0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF},
            {0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61},
            {0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D}
    };

    /**
     * 逆字节替换
     *
     * @param x 待替换的字节
     * @return 替换后的字节
     */
    public short subBytes_(short x) {
        short xh = (short) ((x & 0x00F0) >> 4);
        short xl = (short) (x & 0x000F);
        return S_[xh][xl];
    }

    /**
     * 矩阵中所有元素逆字节替换
     *
     * @param o 待替换的矩阵
     */
    private void subBytesForMatrix_(short[][] o) {
        short[][] res = new short[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res[i][j] = subBytes_(o[i][j]);
            }
        }
        for (int i = 0; i < 4; i++) {
            System.arraycopy(res[i], 0, o[i], 0, 4);
        }
    }

    /**
     * 行移位
     *
     * @param o 待操作的矩阵
     */
    public void shiftRows(short[][] o) {
        short[][] temp = new short[4][4];
        temp[0][0] = o[0][0];
        temp[0][1] = o[0][1];
        temp[0][2] = o[0][2];
        temp[0][3] = o[0][3];
        temp[1][0] = o[1][1];
        temp[1][1] = o[1][2];
        temp[1][2] = o[1][3];
        temp[1][3] = o[1][0];
        temp[2][0] = o[2][2];
        temp[2][1] = o[2][3];
        temp[2][2] = o[2][0];
        temp[2][3] = o[2][1];
        temp[3][0] = o[3][3];
        temp[3][1] = o[3][0];
        temp[3][2] = o[3][1];
        temp[3][3] = o[3][2];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(temp[i], 0, o[i], 0, 4);
        }
    }

    /**
     * 逆行移位
     *
     * @param o 待操作的矩阵
     */
    public void shiftRows_(short[][] o) {
        short[][] temp = new short[4][4];
        temp[0][0] = o[0][0];
        temp[0][1] = o[0][1];
        temp[0][2] = o[0][2];
        temp[0][3] = o[0][3];
        temp[1][0] = o[1][3];
        temp[1][1] = o[1][0];
        temp[1][2] = o[1][1];
        temp[1][3] = o[1][2];
        temp[2][0] = o[2][2];
        temp[2][1] = o[2][3];
        temp[2][2] = o[2][0];
        temp[2][3] = o[2][1];
        temp[3][0] = o[3][1];
        temp[3][1] = o[3][2];
        temp[3][2] = o[3][3];
        temp[3][3] = o[3][0];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(temp[i], 0, o[i], 0, 4);
        }
    }

    /**
     * 模 x ^ 8 + x ^ 4 + x ^ 3 + x + 1 的 GF(2 ^ 8) 上的多项式乘法
     *
     * @param a 操作数a
     * @param b 操作数b
     * @return 计算结果
     */
    private short GFMultiplication(short a, short b) {
        short res = 0;
        while (a != 0) {
            if ((a & 0x01) != 0) {
                res ^= b;
            }
            a = (short) (a >> 1);
            if ((b & 0x80) != 0) {
                b = (short) (b << 1);
                // 这里0x1b即代表m(x)中的x^4+x^3+x+1的部分
                b ^= 0x1b;
            } else {
                b = (short) (b << 1);
            }
        }
        return res;
    }

    private final short[][] C = {
            {0x02, 0x03, 0x01, 0x01},
            {0x01, 0x02, 0x03, 0x01},
            {0x01, 0x01, 0x02, 0x03},
            {0x03, 0x01, 0x01, 0x02}
    };

    /**
     * 列混合
     *
     * @param o 待操作的矩阵
     */
    public void mixColumns(short[][] o) {
        short[][] temp = new short[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[i][j] = (short) (GFMultiplication(C[i][0], o[0][j]) ^
                        GFMultiplication(C[i][1], o[1][j]) ^
                        GFMultiplication(C[i][2], o[2][j]) ^
                        GFMultiplication(C[i][3], o[3][j]));
            }
        }
        for (int i = 0; i < 4; i++) {
            System.arraycopy(temp[i], 0, o[i], 0, 4);
        }
    }

    private final short[][] C_ = {
            {0x0E, 0x0B, 0x0D, 0x09},
            {0x09, 0x0E, 0x0B, 0x0D},
            {0x0D, 0x09, 0x0E, 0x0B},
            {0x0B, 0x0D, 0x09, 0x0E}
    };

    /**
     * 逆列混合
     *
     * @param o 待操作的矩阵
     */
    public void mixColumns_(short[][] o) {
        short[][] temp = new short[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[i][j] = (short) (GFMultiplication(C_[i][0], o[0][j]) ^
                        GFMultiplication(C_[i][1], o[1][j]) ^
                        GFMultiplication(C_[i][2], o[2][j]) ^
                        GFMultiplication(C_[i][3], o[3][j]));
            }
        }
        for (int i = 0; i < 4; i++) {
            System.arraycopy(temp[i], 0, o[i], 0, 4);
        }
    }
}
