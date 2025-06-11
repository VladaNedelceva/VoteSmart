package Tema1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.text.*;

public class App {
    private Scanner scanner;
    private Alegeri alegeriCurr;
    private List<Alegeri> alegeriList;

    public App(InputStream input) {
        this.scanner = new Scanner(input);
        this.alegeriCurr = null;
        this.alegeriList = new ArrayList<>();
    }

    public void run() {
        boolean flag = true;

        while (flag) {
            // crearea meniului cu comenzi
            System.out.println("\nComenzi disponibile:");
            System.out.println("0. Creare alegeri");
            System.out.println("1. Pornire alegeri");
            System.out.println("2. Adaugare circumscriptie");
            System.out.println("3. Eliminare circumscriptie");
            System.out.println("4. Adaugare candidat in alegeri");
            System.out.println("5. Eliminare candidat din alegeri");
            System.out.println("6. Adaugare votant in circumscriptie");
            System.out.println("9. Votare");
            System.out.println("10. Oprire alegeri");
            System.out.println("11. Creeaza raport voturi per circumscriptie");
            System.out.println("16. Sterge alegeri");
            System.out.println("17. Listare alegeri");
            System.out.println("18. Iesire");
            System.out.print("Alege o comanda: ");

            int comanda = scanner.nextInt();
            scanner.nextLine();

            switch (comanda) {
                case 0:
                    creareAlegeri();
                    break;
                case 1:
                    pornireAlegeri();
                    break;
                case 2:
                    adaugCircumscriptie();
                    break;
                case 3:
                    eliminCircumscriptie();
                    break;
                case 4:
                    adaugCandidat();
                    break;
                case 5:
                    stergeCandidat();
                    break;
                case 6:
                    adaugVotantCircumscriptie();
                    break;
                case 9:
                    votare();
                    break;
                case 10:
                    oprireAlegeri();
                    break;
                case 11:
                    creeazaRaportCircumscriptie();
                    break;
                case 16:
                    stergeAlegeri();
                    break;
                case 17:
                    listAlegeri();
                    break;
                case 18:
                    System.out.println("Inchiderea aplicatiei");
                    flag = false;
                    break;
                default:
                    System.out.println("Comanda a fost introdusa incorect.");
            }
        }
    }

    // 0. Creare alegeri
    private void creareAlegeri() {

        System.out.println("Introduceti ID-ul si numele alegerilor pe o singura linie:");
        String input = scanner.nextLine();

        //separ id-ul de numele alegerilor
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("EROARE: Input invalid.");
            return;
        }

        String id_alegeri = parts[0];
        String nume_alegeri = parts[1];

        // verificam daca exista deja alegeri cu acest id
        for (Alegeri alegeri : alegeriList) {
            if (alegeri.getId_alegeri().equals(id_alegeri)) {
                System.out.println("EROARE: Deja exista alegeri cu id " + id_alegeri);
                return;
            }
        }
        //adaugam alegerile in lista
        alegeriCurr = new Alegeri(id_alegeri, nume_alegeri);
        alegeriList.add(alegeriCurr);
        System.out.println("S-au creat alegerile " + nume_alegeri);
    }



    // 1. Pornire alegeri
    private void pornireAlegeri() {
        System.out.println("Introduceti ID-ul si numele alegerilor pe care doriti sa le porniti:");
        String input = scanner.nextLine();

        // separ id-ul de numele alegerilor
        String[] parts = input.split(" ", 2);
        if (parts.length < 1) {
            System.out.println("EROARE: Input invalid.");
            return;
        }

        String id_alegeri = parts[0]; // extrag doar id-ul

        Alegeri alegeriCautate = null; // initializez variabila

        for (Alegeri alegeri : alegeriList) {
            if (alegeri.getId_alegeri().equals(id_alegeri)) {
                alegeriCautate = alegeri;
                break; // oprire dupa gasirea primei coincideri
            }
        }

        // daca nu sunt gasite alegerile
        if (alegeriCautate == null) {
            System.out.println("EROARE: Nu exista alegeri cu acest id");
            return;
        }

        // daca exista, verific starea lor
        if (alegeriCautate.inProgres()) {
            System.out.println("EROARE: Alegerile deja au inceput.");
        } else {
            alegeriCautate.setInProgres(true);
            System.out.println("Au pornit alegerile " + alegeriCautate.getNumeAlegeri());
        }
    }


    //2.Adaugare circumscriptie
    private void adaugCircumscriptie() {
        System.out.println("Introduceti ID-ul alegerilor, numele circumscriptiei si regiunea:");
        String input = scanner.nextLine();

        String[] parts = input.split(" ", 3); //separ inputul in 3 parti
        if (parts.length < 3) {
            System.out.println("EROARE: Input invalid.");
            return;
        }

        String id_alegeri = parts[0];
        String nume_circumscriptie = parts[1];
        String regiune = parts[2];

        Alegeri alegeriCurente = null;

        // gasim alegerile dupa id
        for (Alegeri alegeri : alegeriList) {
            if (alegeri.getId_alegeri().equals(id_alegeri)) {
                alegeriCurente = alegeri;
                break;
            }
        }

        //nu exista
        if (alegeriCurente == null) {
            System.out.println("EROARE: Nu exista alegeri cu acest id");
            return;
        }

        //verific starea alegerilor
        if (!alegeriCurente.inProgres()) {
            System.out.println("EROARE: Nu este perioada de votare");
            return;
        }

        // verific daca exita deja circumscriptia
        if (alegeriCurente.existaCircumscriptie(nume_circumscriptie)) {
            System.out.println("EROARE: Deja exista o circumscriptie cu numele " + nume_circumscriptie);
            return;
        }

        // Adaug circumscriptia
        if (alegeriCurente.adaugCircumscriptie(nume_circumscriptie)) {
            System.out.println("S-a adaugat circumscriptia " + nume_circumscriptie);
        } else {
            System.out.println("EROARE: Deja exista o circumscriptie cu numele " + nume_circumscriptie);
        }

    }


    // 3.Eliminare circumscriptie
    private void eliminCircumscriptie() {
        System.out.println("Introduceti ID-ul alegerilor si numele circumscriptiei pe care doriti sa o eliminati:");
        String input = scanner.nextLine();

        String[] parts = input.split(" ", 2); // separ inputul in 2 parti
        if (parts.length < 2) {
            System.out.println("EROARE: Input invalid.");
            return;
        }

        String id_alegeri = parts[0];
        String nume_circumscriptie = parts[1];

        // gasim alegerile dupa id
        Alegeri alegeriCurente = null;
        for (Alegeri alegeri : alegeriList) {
            if (alegeri.getId_alegeri().equals(id_alegeri)) {
                alegeriCurente = alegeri;
                break;
            }
        }

        // verific daca alegerile exista
        if (alegeriCurente == null) {
            System.out.println("EROARE: Nu exista alegeri cu acest id");
            return;
        }

        // verific starea alegerilor
        if (!alegeriCurente.inProgres()) {
            System.out.println("EROARE: Nu este perioada de votare");
            return;
        }

        // Succes: sterg circumscriptia
        if (alegeriCurente.stergeCircumscriptie(nume_circumscriptie)) {
            System.out.println("S-a sters circumscriptia " + nume_circumscriptie);
        } else {
            System.out.println("EROARE: Nu exista o circumscriptie cu numele " + nume_circumscriptie);
        }
    }


    //4. Adaugare candidat
    private void adaugCandidat() {
        System.out.println("Introduceti id-ul alegerilor, cnp-ul, varsta si numele candidatului separate prin spatiu:");
        String input = scanner.nextLine();

        String[] parts = input.split(" ", 4); // impart inputul in 4 parti

        if (parts.length < 4) {
            System.out.println("EROARE: Input invalid");
            return;
        }

        String idAlegeri = parts[0];
        String cnp = parts[1];
        int varsta = Integer.parseInt(parts[2]);
        String nume = parts[3];

        // verific dupa id daca exista alegerile introduse
        if (alegeriCurr == null || !alegeriCurr.getId_alegeri().equals(idAlegeri)) {
            System.out.println("EROARE: Nu exista alegeri cu acest id");
            return;
        }

        // verific starea alegerilor
        if (!alegeriCurr.inProgres()) {
            System.out.println("EROARE: Nu este perioada de votare");
            return;
        }

        // verific validitatea cnp-ului
        if (cnp.length() != 13) {
            System.out.println("EROARE: CNP invalid");
            return;
        }

        // verific varsta sa fie valida
        if (varsta < 35) {
            System.out.println("EROARE: Varsta invalida");
            return;
        }

        // verific sa nu existe alt candidat cu acelasi cnp
        for (Candidat candidat : alegeriCurr.getListaCandidati()) {
            if (candidat.getCNP().equals(cnp)) {
                System.out.println("EROARE: Candidatul " + nume + " are deja acelasi CNP");
                return;
            }
        }

        // adaug candidatul
        Candidat candidatNou = new Candidat(nume, cnp, varsta);
        alegeriCurr.adaugaCandidat(candidatNou);
        System.out.println("S-a adaugat candidatul " + nume);
    }


    //5. Eliminare candidat din alegeri
    private void stergeCandidat() {
        System.out.println("Introduceti id-ul alegerilor si cnp-ul separate prin spatiu:");
        String input = scanner.nextLine();

        String[] parts = input.split(" ", 2);

        if (parts.length < 2) {
            System.out.println("EROARE: Input invalid");
            return;
        }

        String idAlegeri = parts[0];
        String cnp = parts[1];

        // verific daca exista alegeri cu id-ul introdus
        if (alegeriCurr == null || !alegeriCurr.getId_alegeri().equals(idAlegeri)) {
            System.out.println("EROARE: Nu exista alegeri cu acest id");
            return;
        }

        // verific starea alegerilor
        if (!alegeriCurr.inProgres()) {
            System.out.println("EROARE: Nu este perioada de votare");
            return;
        }

        // sterg candidatul
        String numeSters = alegeriCurr.stergeCandidat(cnp);

        if (numeSters != null) {
            System.out.println("S-a sters candidatul " + numeSters);
        } else {
            System.out.println("EROARE: Nu exista un candidat cu CNP-ul " + cnp);
        }
    }


    //6.Adaugare votant
    private void adaugVotantCircumscriptie() {
        System.out.println("Introduceti ID-ul alegerilor, numele circumscriptiei, CNP-ul, varsta, daca este neindemanatic (da/nu) si numele votantului, separate prin spatiu:");
        String input = scanner.nextLine();

        String[] parts = input.split(" ", 6); // impart inputul in 6 parti
        if (parts.length < 6) {
            System.out.println("EROARE: Input invalid.");
            return;
        }

        String idAlegeri = parts[0];
        String numeCircumscriptie = parts[1];
        String cnp = parts[2];

        int varsta;
        // converstesc varsta din string in int pt a putea si verifica mai tarziu varsta minima valida de votare
        try {
            varsta = Integer.parseInt(parts[3]);
        } catch (NumberFormatException e) {
            System.out.println("EROARE: Varsta invalida");
            return;
        }
        boolean neindemanatic = parts[4].equalsIgnoreCase("da");
        String nume = parts[5];

        // gasesc alegerile
        Alegeri alegeriCurente = null;
        for (Alegeri alegeri : alegeriList) {
            if (alegeri.getId_alegeri().equals(idAlegeri)) {
                alegeriCurente = alegeri;
                break;
            }
        }

        if (alegeriCurente == null) { //verific daca exista
            System.out.println("EROARE: Nu exista alegeri cu acest ID");
            return;
        }

        if (!alegeriCurente.inProgres()) { //verific starea alegerilor
            System.out.println("EROARE: Nu este perioada de votare");
            return;
        }

        if (!alegeriCurente.getCircumscriptii().containsKey(numeCircumscriptie)) { //verific daca exista circumscriptia introdusa
            System.out.println("EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie);
            return;
        }

        if (cnp.length() != 13) { // verific cnp-ul
            System.out.println("EROARE: CNP invalid");
            return;
        }

        if (varsta < 18) { //verific varsta minima
            System.out.println("EROARE: Varsta invalida");
            return;
        }

        // adaug votantul
        boolean rezultat = alegeriCurente.adaugaVotant(numeCircumscriptie, cnp);
        if (rezultat) {
            System.out.println("S-a adaugat votantul " + nume);
        } else {
            System.out.println("EROARE: Votantul " + nume + " are deja acelasi CNP");
        }
    }



    //9.Votare
    private void votare() {
        System.out.println("Introduceti id-ul alegerilor, numele circumscriptiei, cnp-ul votantului si cnp-ul candidatului cu care voteaza, separate prin spatiu:");
        String input = scanner.nextLine();

        String[] parts = input.split(" ", 4); // impart inputul in 4 parti
        if (parts.length < 4) {
            System.out.println("EROARE: Input invalid");
            return;
        }

        String idAlegeri = parts[0];
        String numeCircumscriptie = parts[1];
        String cnpVotant = parts[2];
        String cnpCandidat = parts[3];

        // gasesc alegeri dupa id
        Alegeri alegeriCurente = null;
        for (Alegeri alegeri : alegeriList) {
            if (alegeri.getId_alegeri().equals(idAlegeri)) {
                alegeriCurente = alegeri;
                break;
            }
        }

        if (alegeriCurente == null) { // verific daca exista id
            System.out.println("EROARE: Nu exista alegeri cu acest id");
            return;
        }

        if (!alegeriCurente.inProgres()) { // verific starea alegerilor
            System.out.println("EROARE: Nu este perioada de votare");
            return;
        }

        // inregistrez votul
        String rezultat = alegeriCurente.inregistreazaVot(numeCircumscriptie, cnpVotant, cnpCandidat);
        System.out.println(rezultat);
    }



    // 10. Oprire alegeri
    private void oprireAlegeri() {
        System.out.println("Introduceti ID-ul alegerilor pe care doriti sa le opriti:");
        String id_alegeri = scanner.nextLine();


        // caut alegerile dupa id
        Alegeri alegeriCautate = null;
        for (Alegeri alegeri : alegeriList) {
            if (alegeri.getId_alegeri().equals(id_alegeri)) {
                alegeriCautate = alegeri;
                break; // oprire dupa gasirea primei coincideri
            }
        }

        // verific daca exista id
        if (alegeriCautate == null) {
            System.out.println("EROARE: Nu exista alegeri cu acest id");
            return;
        }

        if ("TERMINAT".equals(alegeriCautate.getStare())) {
            System.out.println("S-au terminat alegerile " + alegeriCautate.getNumeAlegeri());
            return;
        }

        // verific starea alegerilor
        if (!alegeriCautate.inProgres()) {
            System.out.println("EROARE: Nu este perioada de votare");
            return;
        }

        // oprirea alegerilor
        alegeriCautate.setStare("TERMINAT");
        System.out.println("S-au terminat alegerile " + alegeriCautate.getNumeAlegeri());
    }


    // 11. Raport voturi per circumscriptie
    public Alegeri getAlegeriById(String idAlegeri) { // metoda pt cautarea alegerilor dupa id
        for (Alegeri alegeri : alegeriList) {
            if (alegeri.getId_alegeri().equals(idAlegeri)) {
                return alegeri;
            }
        }
        return null;
    }

    //metoda pt verificarea starii alegerilor
    private void verificaFinalizareAlegeri(String idAlegeri) {
        Alegeri alegeri = getAlegeriById(idAlegeri);
        if (alegeri != null) {
            if (!alegeri.isTerminat()) {
                System.out.println("EROARE: Inca nu s-a terminat votarea");
                return;
            }
        } else {
            System.out.println("EROARE: Nu exista alegeri cu acest id");
            return;
        }
    }
    private void creeazaRaportCircumscriptie() {
        System.out.println("Introduceti ID-ul alegerilor si numele circumscriptiei separate prin spatiu:");
        String input = scanner.nextLine();

        String[] parts = input.split(" ", 2); // impart inputul in 2 parti
        if (parts.length < 2) {
            System.out.println("EROARE: Input invalid.");
            return;
        }

        String id_alegeri = parts[0];
        String numeCircumscriptie = parts[1];

        // obtin obiectul Alegeri pe baza id-ului
        Alegeri alegeri = getAlegeriById(id_alegeri);
        if (alegeri == null) {
            System.out.println("EROARE: Nu exista alegeri cu acest id");
            return;
        }

        // verific starea alegerilor
        if (!alegeri.isTerminat()) {
            System.out.println("EROARE: Inca nu s-a terminat votarea");
            return;
        }

        // obtinerea raportului voturilor
        Map<String, Integer> raport = alegeri.getVoturiPerCircumscriptie(numeCircumscriptie);
        if (raport == null) {
            System.out.println("EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie);
            return;
        }

        // verific daca raportul este gol
        if (raport.isEmpty()) {
            System.out.println("GOL: Lumea nu isi exercita dreptul de vot in " + numeCircumscriptie);
            return;
        }

        // afisarea raportului
        System.out.println("Raport voturi " + numeCircumscriptie + ":");
        for (Map.Entry<String, Integer> entry : raport.entrySet()) {
            String numeCandidat = entry.getKey();
            int numarVoturi = entry.getValue();
            String cnpCandidat = alegeri.getCnpCandidat(numeCandidat);
            System.out.println(numeCandidat + " " + cnpCandidat + " - " + numarVoturi);
        }
    }



    //17.listare alegeri
    private void listAlegeri() {
        // verific daca exista alegeri
        if (alegeriList.isEmpty()) {
            System.out.println("GOL: Nu sunt alegeri");
            return;
        }

        // afisez alegerile inregistrate prin comanda 0
        System.out.println("Alegeri:");
        for (Alegeri alegeri : alegeriList) {
            System.out.println(alegeri.getId_alegeri() + " " + alegeri.getNumeAlegeri());
        }
    }


    //16.sterge alegeri
    private void stergeAlegeri() {
        System.out.println("Introduceti ID-ul alegerilor pe care doriti sa le stergeti:");
        String id_alegeri = scanner.nextLine();

        // gasesc alegerile
        Alegeri alegeri = getAlegeriById(id_alegeri);
        if (alegeri == null) {
            System.out.println("EROARE: Nu exista alegeri cu acest id");
            return;
        }

        // stochez numele alegerilor
        String numeAlegeri = alegeri.getNumeAlegeri();

        // sterg alegerile din lista
        alegeriList.remove(alegeri);

        System.out.println("S-au sters alegerile " + numeAlegeri + ".");
    }



    public static void main(String[] args) {
        App app = new App(System.in);
        app.run();
    }
}