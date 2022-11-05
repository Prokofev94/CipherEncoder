package chucknorris;

import java.util.Scanner;

public class Main {
    static Scanner scanner =  new Scanner(System.in);
    public static void main(String[] args) {
        start();
    }

    static void start() {
        String input;
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            input = scanner.nextLine();
            switch (input) {
                case "encode":
                    encode();
                    break;
                case "decode":
                    decode();
                    break;
                case "exit":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.printf("There is no '%s' operation\n", input);
            }
            System.out.println();
        }
    }

    static void encode() {
        System.out.println("Input string:");
        String input = scanner.nextLine();
        System.out.println("Encoded string:");
        StringBuilder binary = new StringBuilder();
        for (char ch : input.toCharArray()) {
            String binaryValue = Integer.toBinaryString(ch);
            binaryValue = "0".repeat(7 - binaryValue.length()) + binaryValue;
            binary.append(binaryValue);
        }
        binary.append(" ");

        StringBuilder result = new StringBuilder();
        char bin = '2';
        int count = 0;
        for (char ch : binary.toString().toCharArray()) {
            if (ch != bin) {
                if (bin == '0') {
                    result.append("00 ");
                } else if (bin == '1') {
                    result.append("0 ");
                }
                result.append("0".repeat(count)).append(" ");
                count = 1;
                bin = ch;
            } else {
                count++;
            }
        }
        System.out.println(result);
    }

    static void decode() {
        System.out.println("Input encoded string:");
        String input = scanner.nextLine();
        if (isNotValid(input)) {
            System.out.println("Encoded string is not valid.");
            return;
        }
        System.out.println("Decoded string:");
        String[] array = input.split(" ");
        StringBuilder binary = new StringBuilder();
        for (int i = 1; i < array.length; i += 2) {
            String s = "0".equals(array[i - 1]) ? "1" : "0";
            binary.append(s.repeat(array[i].length()));
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 7) {
            String binaryValue = binary.substring(i, i + 7);
            result.append((char) Integer.parseInt(binaryValue, 2));
        }
        System.out.println(result);
    }

    static boolean isNotValid(String code) {
        if (!"".equals(code.replaceAll("0", "").replaceAll(" ", ""))) {
            return true;
        }
        String[] array = code.split(" ");
        if (array.length % 2 == 1) {
            return true;
        }
        int bytes = 0;
        for (int i = 0; i < array.length; i += 2) {
            if (array[i].length() > 2) {
                return true;
            }
            bytes += array[i + 1].length();
        }
        return bytes % 7 != 0;
    }
}