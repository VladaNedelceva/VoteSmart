package Tema1;

public class Votant {
    private String cnp;
    private int varsta;
    private boolean neindemanatic;
    private String nume;

    public Votant(String cnp, int varsta, boolean neindemanatic, String nume) {
        this.cnp = cnp;
        this.varsta = varsta;
        this.neindemanatic = neindemanatic;
        this.nume = nume;
    }

    public String getCNP() {
        return cnp;
    }

    public int getVarsta() {
        return varsta;
    }

    public boolean isNeindemanatic() {
        return neindemanatic;
    }

    public String getNume() {
        return nume;
    }
}
