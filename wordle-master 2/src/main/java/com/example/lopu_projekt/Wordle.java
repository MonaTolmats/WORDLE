package com.example.lopu_projekt;
import java.io.IOException;
import java.util.Scanner;

public class Wordle {
    public static void main(String[] args) throws IOException {
        Sõna otsitavSõna = new Sõna();
        //System.out.println("Otsitav sõna: " + otsitavSõna); // aktiveeri, et näha genereeritud sõna

        String[][] keyboardLayout = new String[][]{
                {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
                {"Z", "X", "C", "V", "B", "N", "M", "ENTER", "DELETE"}
        };

        Täht[][] keyboard = new Täht[3][10];

        for (int i = 0; i < 3; i++) {
            for (int t = 0; t < keyboardLayout[i].length; t++) {
                keyboard[i][t] = new Täht(keyboardLayout[i][t], "grey");
            }
        }

        Kontroll kontroll = new Kontroll(keyboard);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Sisesta oma nimi:");
        String name = scanner.nextLine();

        System.out.println("Tere, " + name + "!");

        for (int i = 0; i < 6; i++) {
            System.out.println("Sisesta oma sõna (5 tähte):");
            String pakutavSõna = scanner.nextLine();
            if (pakutavSõna.length() == 5) {
                if (kontroll.kontrolliSõna(pakutavSõna, otsitavSõna.getOtsitavSõna())) {
                    System.out.println("Palju õnne " + name + ", Sa võitsid!");
                    break;
                }
            } else {
                i--;
                System.out.println("Sisesta 5-täheline sõna");
                continue;
            }
        }
    }
}