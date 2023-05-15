package com.example.lopu_projekt;

public class Täht {
    private String täht;
    private String värv;

    public Täht(String täht, String värv) {
        this.täht = täht;
        this.värv = värv;
    }

    public String getTäht() { return this.täht;}

    public String getVärv() {
        return värv;
    }

    public void setVärv(String värv) {
        this.värv = värv;
    }
}
