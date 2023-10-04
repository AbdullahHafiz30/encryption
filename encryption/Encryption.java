package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ABDULLAH HAFIZ ID: 4101979 SECTION: M2 & ABDULLAH ALENEZI ID:4100204
 * SECTION : M2
 */
public class Encryption {

    //taken from baeldung.com for turning the key from String to secretKey
    private static SecretKey convertToSecretKey(String key, String algo) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, algo);
    }

    private static void enc(String key, String algo, String name) throws IOException {

        SecretKey Key = convertToSecretKey(key, algo);
        //taken from youtube for encrypting a text file
        try {
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.ENCRYPT_MODE, Key);
            //this section for File encryption
            FileInputStream fis = new FileInputStream(name);
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Abdullah\\Desktop\\TAIBAHU\\9.5-summer 0095\\encryption\\encrypted.txt"); //you have to change the directory to your desired file 
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            byte[] data = new byte[100];
            int i = cis.read(data);
            while (i != -1) {
                fos.write(data, 0, i);
                i = cis.read(data);
            }
            System.out.println("Encrypting is done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dec(String key, String algo, String name) throws IOException {

        SecretKey originalKey = convertToSecretKey(key, algo);
        //for decrypting a text file
        try {
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            //this section for File decryption
            FileInputStream fis = new FileInputStream(name);
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Abdullah\\Desktop\\TAIBAHU\\9.5-summer 0095\\encryption\\decrypted.txt");//you have to change the directory to your desired file
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            byte[] data = new byte[100];
            int i = cis.read(data);
            while (i != -1) {
                fos.write(data, 0, i);
                i = cis.read(data);
            }
            System.out.println("Decrypting is done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void enc1(String key, String algo, String name) throws IOException {

        SecretKey Key = convertToSecretKey(key, algo);
        //taken from youtube for encrypting a text file
        try {
            Cipher cipher = Cipher.getInstance(algo); //try changing this 
            cipher.init(Cipher.ENCRYPT_MODE, Key);
            //this section for Folder encryption
            FileInputStream fis = new FileInputStream(name);
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Abdullah\\Desktop\\TAIBAHU\\9.5-summer 0095\\encryption\\encrypted.zip");  //you have to change the directory to your desired Folder
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(data)) != -1) {
                fos.write(data, 0, bytesRead);
            }
            cis.close();
            fos.close();
            fis.close();
            System.out.println("Encrypting is done!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void dec1(String key, String algo, String name) throws IOException {

        SecretKey originalKey = convertToSecretKey(key, algo);
        //for decrypting a text file
        try {
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            //this section for Folder decryption
            FileInputStream fis = new FileInputStream(name);
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Abdullah\\Desktop\\TAIBAHU\\9.5-summer 0095\\encryption\\output.zip"); //you have to change the directory to your desired Folder
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(data)) != -1) {
                cos.write(data, 0, bytesRead);
            }
            cos.close();
            fos.close();
            fis.close();
            System.out.println("Decrypting is done!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //this section for encryption menu
    private static void encrypt(Scanner input) throws IOException {
        String e;
        do {
            System.out.println("\n\nWhat do you want to encrypt?\n"
                    + "========================================================\n"
                    + "1. Encrypt File\n"
                    + "2. Encrypt Folder\n"
                    + "3. Go back to Encryption menu\n\n"
                    + "Enter your choice: ");
            e = input.nextLine();
            switch (e) {
                case "1":
                    String filename,
                     algo,
                     key;

                    System.out.println("Enter your file name: ");
                    filename = "C:\\Users\\Abdullah\\Desktop\\TAIBAHU\\9.5-summer 0095\\encryption\\" + input.nextLine(); //you have to change directory
                    System.out.println("Choose the algorithm(AES, DES): ");
                    algo = input.nextLine();
                    System.out.println("Enter the secret key: ");
                    key = input.nextLine();
                    File inputFile = new File(filename);
                    if (inputFile.exists()) {
                        enc(key, algo, filename);
                    } else {
                        System.out.println("File does not exist!");
                    }
                    break;
                case "2":
                    String foldername,
                     algo1,
                     key1;

                    System.out.println("Enter your folder name: ");
                    foldername = "C:\\Users\\Abdullah\\Desktop\\TAIBAHU\\9.5-summer 0095\\encryption\\" + input.nextLine();   //you have to change directory
                    System.out.println("Choose the algorithm(AES, DES): ");
                    algo1 = input.nextLine();
                    System.out.println("Enter the secret key: ");
                    key1 = input.nextLine();
                    File inputFolder = new File(foldername);
                    if (inputFolder.exists()) {
                        enc1(key1, algo1, foldername);
                    } else {
                        System.out.println("Folder does not exist!");
                    }
                    break;

                case "3":
                    System.out.println("Going back to encryption menu");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!e.matches("[0-9_]+") || Integer.parseInt(e) != 3);
    }

    private static void decrypt(Scanner input) throws IOException {
        String e;
        do {
            System.out.println("\n\nWhat do you want to decrypt?\n"
                    + "========================================================\n"
                    + "1. Decrypt File\n"
                    + "2. Decrypt Folder\n"
                    + "3. Go back to Encryption menu\n\n"
                    + "Enter your choice: ");
            e = input.nextLine();
            switch (e) {
                case "1":
                    String filename,
                     algo,
                     key;

                    System.out.println("Enter your file name: ");
                    filename = "C:\\Users\\Abdullah\\Desktop\\TAIBAHU\\9.5-summer 0095\\encryption\\" + input.nextLine();  //you have to change directory
                    System.out.println("Choose the algorithm(AES, DES): ");
                    algo = input.nextLine();
                    System.out.println("Enter the secret key: ");
                    key = input.nextLine();
                    File inputFile = new File(filename);
                    if (inputFile.exists()) {
                        dec(key, algo, filename);
                    } else {
                        System.out.println("File does not exist!");
                    }
                    break;
                case "2":
                    String foldername,
                     algo1,
                     key1;

                    System.out.println("Enter your folder name: ");
                    foldername = "C:\\Users\\Abdullah\\Desktop\\TAIBAHU\\9.5-summer 0095\\encryption\\" + input.nextLine();  //you have to change directory
                    System.out.println("Choose the algorithm(AES, DES): ");
                    algo1 = input.nextLine();
                    System.out.println("Enter the secret key: ");
                    key1 = input.nextLine();
                    File inputFolder = new File(foldername);
                    if (inputFolder.exists()) {
                        dec1(key1, algo1, foldername);
                    } else {
                        System.out.println("Folder does not exist!");
                    }
                    break;
                case "3":
                    System.out.println("Going back to encryption menu");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!e.matches("[0-9_]+") || Integer.parseInt(e) != 3);
    }

    private static void handleEncryption(Scanner input) throws IOException {
        String x;

        do {
            System.out.println("\n\nENCRYPTION MENU\n"
                    + "========================================================\n"
                    + "1. Encrypt\n"
                    + "2. Decrypt\n"
                    + "3. Back to main menu\n\n"
                    + "Enter your choice: ");
            x = input.nextLine();
            switch (x) {
                case "1":
                    encrypt(input);
                    break;
                case "2":
                    decrypt(input);
                    break;
                case "3":
                    System.out.println("Going back to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!x.matches("[0-9_]+") || Integer.parseInt(x) != 3);
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);
        String choice;

        do {
            System.out.println("\nMAIN MENU\n"
                    + "========================================================\n"
                    + "What do you need to implement?\n"
                    + "1. Encryption\n"
                    + "2. Hashing\n"
                    + "3. Exit\n\n"
                    + "Enter your choice: ");
            choice = input.nextLine();
            switch (choice) {
                case "1":
                    handleEncryption(input);
                    break;
                case "2":

                    handleHashing(input);
                    break;
                case "3":
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            input.nextLine();
        } while (!choice.matches("[0-9_]+") || Integer.parseInt(choice) != 0);
    }
    //this section for hashing menu

    private static void handleHashing(Scanner input) throws NoSuchAlgorithmException, IOException {
        System.out.println("Type your file name: ");
        String fileN = "C:\\Users\\Abdullah\\Desktop\\TAIBAHU\\9.5-summer 0095\\encryption\\" + input.nextLine();  //you have to change directory
        System.out.println("Choose the Algorithm (SHA-256, SHA-512): "); // dont forget the SLASH or the program wont work
        String sha = input.nextLine();

        byte[] digest = MessageDigest.getInstance(sha).digest(Files.readAllBytes(Paths.get(fileN)));
        String hexDigest = bytesToHex(digest);

        String output = "C:\\Users\\Abdullah\\Desktop\\TAIBAHU\\9.5-summer 0095\\encryption\\output.txt";  //you have to change directory
        Files.write(Paths.get(output), hexDigest.getBytes());

        System.out.println("Done! The message digest of the file text2.txt is generated using " + sha);
        System.out.println("Output file is " + output);

    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
