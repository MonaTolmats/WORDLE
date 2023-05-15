package com.example.lopu_projekt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class Sõna {
    private final String otsitavSõna;

    public Sõna() throws IOException {
        this.otsitavSõna = genereeriSõna();
    }

    public String getOtsitavSõna() {
        return otsitavSõna;
    }

    private String genereeriSõna() throws IOException {
        List<String> sõnastik = Files.readAllLines(Path.of("src/main/resources/com/example/lopu_projekt/sonad.txt"), StandardCharsets.UTF_8);
        Random random = new Random();
        int randomIndeks = random.nextInt(sõnastik.size());
        return sõnastik.get(randomIndeks);
    }

    @Override
    public String toString() {
        return otsitavSõna;
    }
}

