package com.example.lopu_projekt;

import java.util.Objects;

public class Kontroll {

    private Täht[][] tähed;

    public Kontroll(Täht[][] tähed) {
        this.tähed = tähed;
    }

    public Täht[][] getTähed() {
        return tähed;
    }

    public boolean sisaldab(String c, String[] sõna) {
        for (String value : sõna) {
            if (Objects.equals(c, value))
                return true;
        }
        return false;
    }

    public boolean kontrolliSõna(String pakutavSõna, String otsitavSõna) {
        if (pakutavSõna.equals(otsitavSõna)) {
            return true;
        } else {
            this.tähtedeKontroll(pakutavSõna, otsitavSõna);
            return false;
        }
    }

    public void tähtedeKontroll(String pakutavSõna, String otsitavSõna) {
        String[] pakutavaSõnaTähed = pakutavSõna.split("");
        String[] otsitavaSõnaTähed = otsitavSõna.split("");

        for (int i = 0; i < 5; i++) {
            if (pakutavaSõnaTähed[i].equals(otsitavaSõnaTähed[i])) {
                for (Täht[] tähts : tähed) {
                    for (Täht täht : tähts) {
                        if (täht.getTäht().equals(pakutavaSõnaTähed[i])) {
                            täht.setVärv("#77b780");
                        }
                    }
                }
            } else {
                if (sisaldab(pakutavaSõnaTähed[i], otsitavaSõnaTähed)) {
                    for (Täht[] tähts : tähed) {
                        for (Täht täht : tähts) {
                            if (täht.getTäht().equals(pakutavaSõnaTähed[i])) {
                                if (!täht.getVärv().equals("#77b780")) {
                                    täht.setVärv("#fbd62f");
                                }
                            }
                        }
                    }
                } else {
                    for (Täht[] tähts : tähed) {
                        for (Täht täht : tähts) {
                            if (täht.getTäht().equals(pakutavaSõnaTähed[i])) {
                                if (!täht.getVärv().equals("#77b780") && !täht.getVärv().equals("#fbd62f")) {
                                    täht.setVärv("#f98eb8");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
