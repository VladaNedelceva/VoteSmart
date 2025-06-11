package Tema1;
import java.util.Set;
import java.util.HashSet;


public class Circumscriptie {
    private String nume;
    private Set<Votant> votanti;

    public Circumscriptie(String nume) {
        this.nume = nume;
        this.votanti = new HashSet<>();
    }

    public String getNume() {
        return nume;
    }

    public Set<Votant> getVotanti() {
        return votanti;
    }

    public void addVotant(Votant votant) {
        votanti.add(votant);
    }

    public boolean containsVotant(Votant votant) {
        return votanti.contains(votant);
    }
}