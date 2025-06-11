package Tema1;

public class Candidat {
    private String nume;
    private String cnp;
    private int varsta;

    public Candidat(String nume, String cnp, int varsta) {
        this.nume = nume;
        this.cnp = cnp;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public String getCNP() {
        return cnp;
    }

    public int getVarsta() {
        return varsta;
    }
}
