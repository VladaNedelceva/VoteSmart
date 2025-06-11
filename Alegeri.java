package Tema1;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.*;
import java.util.Date;


public class Alegeri {
    private String id_alegeri;
    private String nume_alegeri;
    private boolean inProgress;
    private Map<String, Circumscriptie> circumscriptii;
    private Set<Candidat> candidati;
    private Set<Candidat> listaCandidati;
    private Map<String, Set<String>> voturi = new HashMap<>();
    private Map<String, Set<String>> votanti;
    private String stare;
    private List<Alegeri> alegeriList = new ArrayList<>();
    ;
    private Map<String, Map<String, Integer>> voturiPerCircumscriptie = new HashMap<>();
    private Map<String, Set<String>> votantiPerCircumscriptie = new HashMap<>();
    private Map<String, Alegeri> alegeriMap;
    private Map<String, String> numeVotanti;
    private Alegeri alegeriCurr;
    private String idAlegeri;
    private String numeCircumscriptie;
    private Date dataStart;
    private Date dataFinalizare;


    public Alegeri(String id_alegeri, String nume_alegeri) {
        this.id_alegeri = id_alegeri;
        this.nume_alegeri = nume_alegeri;
        this.inProgress = false;
        this.circumscriptii = new HashMap<>();
        ;
        this.candidati = new HashSet<>();
        this.listaCandidati = new HashSet<>();
        this.votanti = new HashMap<>();
        this.stare = "NEINCEPUT";
        this.alegeriList = new ArrayList<>();
        this.numeVotanti = new HashMap<>();
        this.alegeriMap = new HashMap<>();
        this.idAlegeri = idAlegeri;
        this.numeCircumscriptie = numeCircumscriptie;
        this.dataStart = dataStart;
        this.dataFinalizare = dataFinalizare;
    }


    public boolean inProgres() {
        return inProgress;
    }

    public void setInProgres(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    public String getStare() {
        return stare;
    }

    public boolean isTerminat() {
        return "TERMINAT".equals(stare);
    }

    public String getId_alegeri() {
        return id_alegeri;
    }

    public String getNumeAlegeri() {
        return this.nume_alegeri;
    }

    public Map<String, Circumscriptie> getCircumscriptii() {
        return circumscriptii;
    }

    // metoda returneaza un Map care asociaza cnp-ul fiecarui candidat cu obiectul
    // parcurge listaCandidati si adauga fiecare candidat in harta, folosind CNP-ul ca cheie unica
    public Map<String, Candidat> getCandidatiMap() {
        Map<String, Candidat> candidatiMap = new HashMap<>();

        for (Candidat candidat : listaCandidati) {
            candidatiMap.put(candidat.getCNP(), candidat);
        }

        return candidatiMap;
    }

    /* metoda creeaza si returneaza un Map ce asociaza fiecare vot cu un mesaj
    care contine informatii despre votantul care a emis votul */
    /* parcurge fiecare pereche cheie-valoare din Map voturi si creaaza un Map cu detalii
    despre voturile fiecarui votant */
    public Map<String, String> getVoturiMap() {
        Map<String, String> voturiMap = new HashMap<>();

        // iterez prin fiecare pereche cheie-valoare din Map
        for (Map.Entry<String, Set<String>> entry : this.voturi.entrySet()) {
            String cnpVotant = entry.getKey(); // CNP-ul votantului
            Set<String> voturiSet = entry.getValue(); // Setul de voturi pentru acest votant

            // iterez prin fiecare vot al votantului
            for (String vot : voturiSet) {
                // Adaug in voturiMap fiecare vot, cu detalii pentru votantul respectiv
                voturiMap.put(vot, "Votul lui " + cnpVotant + " pentru candidatul cu CNP-ul " + vot);
            }
        }

        return voturiMap;
    }


    /* Metoda parcurge fiecare pereche cheie-valoare din harta "voturi" si creeaza un Map cu detalii
    despre voturile fiecarui votant.*/
    public Map<String, Integer> getVoturiPerCircumscriptie(String numeCircumscriptie) {
        Map<String, Integer> voturiPerCandidat = voturiPerCircumscriptie.get(numeCircumscriptie);
        return (voturiPerCandidat != null) ? voturiPerCandidat : new HashMap<>();
    }


    public boolean adaugCircumscriptie(String numeCircumscriptie) {
        if (circumscriptii.containsKey(numeCircumscriptie)) {
            return false;
        }
        circumscriptii.put(numeCircumscriptie, new Circumscriptie(numeCircumscriptie));
        return true;
    }


    public boolean existaCircumscriptie(String numeCircumscriptie) {
        return circumscriptii.containsKey(numeCircumscriptie);
    }

    public boolean stergeCircumscriptie(String nume_circumscriptie) {
        if (!circumscriptii.containsKey(nume_circumscriptie)) {
            return false;
        }

        // sterg circumscriptia si datele aferente
        circumscriptii.remove(nume_circumscriptie);
        return true;
    }

    public void adaugaCandidat(Candidat candidat) {
        listaCandidati.add(candidat);
    }

    public Set<Candidat> getListaCandidati() {
        return listaCandidati;
    }

    public String stergeCandidat(String cnp) {
        for (Candidat candidat : listaCandidati) {
            if (candidat.getCNP().equals(cnp)) {
                String nume = candidat.getNume();
                voturi.remove(cnp);
                listaCandidati.remove(candidat);
                return nume;
            }
        }
        return null;
    }

    public boolean adaugaVotant(String numeCircumscriptie, String cnpVotant) {
        if (!circumscriptii.containsKey(numeCircumscriptie)) {
            System.out.println("EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie);
            return false;
        }

        Set<String> votanti = votantiPerCircumscriptie.get(numeCircumscriptie);
        if (votanti == null) {
            votanti = new HashSet<>();
            votantiPerCircumscriptie.put(numeCircumscriptie, votanti);
        }

        if (votanti.contains(cnpVotant)) {
            return false;
        }
        votanti.add(cnpVotant);
        return true;
    }


    public boolean existaCandidat(String cnpCandidat) {
        for (Candidat candidat : listaCandidati) {
            if (candidat.getCNP().equals(cnpCandidat)) {
                return true;
            }
        }
        return false;
    }

    public boolean esteVotantValid(String numeCircumscriptie, String cnpVotant) {
        Set<String> votantiPerCircumscriptie = votanti.get(numeCircumscriptie);
        return votantiPerCircumscriptie != null && votantiPerCircumscriptie.contains(cnpVotant);
    }


    public boolean existaVotant(String cnpVotant) {
        for (Set<String> votantiPerCircumscriptie : votanti.values()) {
            if (votantiPerCircumscriptie.contains(cnpVotant)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, Set<String>> getVotantiPerCircumscriptie() {
        return votantiPerCircumscriptie;
    }

    private Candidat findCandidatByCnp(String cnpCandidat) {
        for (Candidat candidat : listaCandidati) {
            if (candidat.getCNP().equals(cnpCandidat)) {
                return candidat;
            }
        }
        return null;
    }


    // Metoda de votare
    public String inregistreazaVot(String numeCircumscriptie, String cnpVotant, String cnpCandidat) {
        if (!circumscriptii.containsKey(numeCircumscriptie)) {
            return "EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie;
        }

        Set<String> votanti = this.votantiPerCircumscriptie.get(numeCircumscriptie);
        if (votanti == null || !votanti.contains(cnpVotant)) {
            return "EROARE: Nu exista un votant cu CNP-ul " + cnpVotant;
        }

        Candidat candidat = findCandidatByCnp(cnpCandidat);
        if (candidat == null) {
            return "EROARE: Nu exista un candidat cu CNP-ul " + cnpCandidat;
        }

        Set<String> voturiVotant = this.voturi.get(cnpVotant);
        if (voturiVotant != null && voturiVotant.contains(cnpCandidat)) {
            return "FRAUDA: Votantul cu CNP-ul " + cnpVotant + " a incercat sa comita o frauda. Votul a fost anulat";
        }

        if (voturiVotant == null) {
            voturiVotant = new HashSet<>();
            this.voturi.put(cnpVotant, voturiVotant);
        }
        voturiVotant.add(cnpCandidat);

        return cnpVotant + " a votat pentru " + candidat.getNume();
    }


    public void opresteAlegeri() {
        if ("TERMINAT".equals(this.stare)) {
            System.out.println("S-au terminat alegerile " + this.nume_alegeri);
            return;
        }
        // verific starea alegerilor
        if (!inProgress || "NEINCEPUT".equals(this.stare)) {
            System.out.println("EROARE: Nu este perioada de votare");
            return;
        }

        // oprirea alegerilor
        this.setStare("TERMINAT");
        this.setInProgres(false);
        System.out.println("S-au terminat alegerile " + this.nume_alegeri);
    }


    // Adaug voturi pentru un candidat intr-o circumscriptie
    public void adaugaVoturi(String numeCircumscriptie, String cnp, int numarVoturi) {
        if (this.voturiPerCircumscriptie.containsKey(numeCircumscriptie)) {
            this.voturiPerCircumscriptie.get(numeCircumscriptie).put(cnp, numarVoturi);
        } else {
            System.out.println("EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie);
        }
    }

    public Alegeri getAlegeriById(String id_alegeri) {
        for (Alegeri alegeri : alegeriList) {
            if (alegeri.getId_alegeri().equals(id_alegeri)) {
                return alegeri;
            }
        }
        return null;
    }


    public String getCnpCandidat(String numeCandidat) {
        for (Candidat candidat : listaCandidati) {
            if (candidat.getNume().equals(numeCandidat)) {
                return candidat.getCNP();
            }
        }
        return null;
    }

}