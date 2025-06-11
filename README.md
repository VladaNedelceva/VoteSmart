La rularea aplicatiei, fisierul App.java, afiseaza un meniu prin care se afiseaza comenzile pe care le are disponibile utilizatorul:
0 -> Creare alegeri
1 -> Pornire alegeri
2 -> Adaugare circumscriptie
3 -> Eliminare circumscriptie
4 -> Adaugare candidat in alegeri
5 -> Eliminare candidat din alegeri
6 -> Adaugare votant in circumscriptie
9 -> Votare
10 -> Oprire alegeri
11 -> Creeaza raport voturi per circumscriptie
16 -> Sterge alegeri
17 -> Listare alegeri
18 -> Iesire

La creare alegeri utilizatorul un id unic si un nume pentru alegeri si aplicatia verifica daca id-ul
exista deja si adauga alegerile in lista.

La pornirea alegerilor, metoda permite schimbarea starii unei alegeri ca sa marcheze inceputul procesului de votare.

La adaugare circumscriptii asociaza circumscriptii la o alegere activa si verifica daca cumva exista deja circumscriptia
introdusa de utilizator.

La eliminarea circumscriptiilor, metoda elimina o circumscriptie  deja introdusa de catre utilizator la comanda anterioara.

La adaugarea candidatilor, metoda adauga un candidat prin specificarea numelui, cnp-ului si varstei, prin care se
asigura ca cnp-ul este valid, adica are 13 caractere, si varsta sa fie valida, adica mai mare de 35.

La eliminarea candidatilor, metoda elimina un candidat in baza cnp-ului dintr-o alegere, id.

La adaugarea votantilor, metoda asociaza votanti circumscriptiilor disponibile deja, gestionand cnp-ul, din nou verifica 
daca are 13 caractere, varsta, sa fie minim 18, daca este neidndemanatic sau nu si numele.

La votare,metoda inregistreaza votul unui votant catre unul din candidatii disponibili, prin idul alegerilor, cnp-ul
votantului si cnp-ul candidatului. Se verifica daca nu s-a comis frauda, in cazul in care votantul nu este inscris la
circumscriptie sau daca ineacrca sa voteze a doua oara. Daca votantul este neindemanatic, votul se anuleaza.

LA raportul de voturi per circumscriptie, metoda creeaza un raport care afiseaza distributia voturilor in circumscriptia
introdus/disponibila. Metoda emite o eroare in cazul in care inca nu a fost inregistrat nici un vot la circumscriptia
introdus, daca nu exist o circumscriptie cu numele introdus, daca nu exista alegeri cu idul introdus sau daca starea alegerilor
este inca in curs.

La stergerea alegerilor, metoda afiseaza toate alegerile inregistrate la comanda 0, id-ul + nume alegeri.




In fisierul Alegeri.java, avem:
Gestionarea circumscriptiilor, care permite adaugarea, verificarea existentei si stergerea circumscriptiilor, care sunt
gestionate printr-un Map, unde fiecare cheie reprezinta numele circumscriptiei.

La gestionarea candidatilor, se pot adauga, sterge candidati si verifica daca un candidat exista. Lista candidatilor
este stocata intr-un Set pentru a preveni duplicarea datelor.

La gestionarea votantilor sistemul permite inregistrearea votantilor per circmuscriptie, validarea si verificarea
existentei unui votant. Votantii sunt otganizati intr-un Map, unde cheia este numele circumscriptiei, iar valoarea este
un Set de cnp-uri.

La inregistrarea voturilor, fiecare votant poate vota pentru un candidat, iar voturile sunt salvate intr-un Map, cu cheia
reprezentand cnp-ul votantului si valoarea fiind un Set de cnp-uri ale candidatilor votati. Metoda detecteaza frauda in 
cazul in care un votant incearca sa voteze de mai multe ori.

La starea alegerilor, acestea putand fi NEINCEPUT, IN_DESFASURARE sau TERMINAT, metoda opreste procesul de votare si
actualizeaza starea la TERMINAT.



BONUS:
Ce alte cazuri limită ați mai trata în această aplicație (minim 3 cazuri, a se descrie în README)?
1. La adaugarea circumscriptie: in cazul dat as trata cazul in care exista o lista cu numele regiunilor unor 
circumscriptii in care nu se pot organiza puncte de votare (cazul dat fiind la judete mai mici). Respectiv la introducerea
de catre utilizator a unui id valid, numeCircumscriptie valid, dar regiune nevalida, aplicatia ar intoarce o eroare,
utiizatorul neputand sa adauge votanti aici: "EROARE: In regiunea <nume_regiune> nu sunt disponibile puncte de votare"
2. Verificarea dintre datele candidatilor si votantilor: In timpul implementarii aplicatiei am observat ca daca exista un 
candidat si un votant, cu nume diferite, dar acelasi cnp, aceasta nu returneaza o eroare, dar teoretic ar trebui verificat 
acest lucru, pentru a evita fraudele electorale.
3. La Creează raport voturi per circumscripție: ar trebui verificat, respectiv aplicatia sa returneze eroare daca alegerile
inca nu au inceput. Deja aplicatia returneaza doar EROARE: Încă nu s-a terminat votarea, daca alegerile sunt pornite, dar
nu oprite, insa ar trebui verificat daca au inceput alegerile, respectiv sa returneze EROARE: Încă nu a inceput votarea.


Cum ați refactoriza comenzile și răspunsurile din aplicație (minim 3 propuneri, a se descrie în README)?
1. La Listarea candidaților din alegeri: avem cazul "Alegerile nu sunt în stagiul ÎN_CURS sau TERMINAT" si raspunsul 
"EROARE: Încă nu au început alegerile". Nu mi se pare neaparat corect sa nu listam candidatii in cursul procesului de votare. 
Deci listarea candidatilor ar trebui sa fie permisa oricand, indiferent de starea alegerilor.
2. Adaugarea votantului: As adauga si categoria de sex: barbat/femeie/altele, pentru eventuale statistici, cum ar fi pentru
Analiză detaliată per circumscripție/nationala. O alta idee ar fi ca fiecare votant sa fie stocat intr-o categorie anumita
de varsta, de exemplu votantii (<nume_votant> <varsta_votant) Mihai 35, Andrei 42, Ilie 48: sa fie in categoria de varsta 35-50;
Andreea 19, Ludmila 28, Adriana 31: sa fie in categoria de varsta 18-34. Asta la fel ar fi posibil de utilizat la Analiză 
detaliată per circumscripție/nationala, de exemplu: SUCCES: "În <nume_circumscripție> au fost 
<nr_voturi_circumscripție> voturi din <nr_voturi_național>. Adică <procentaj>% pe tara, mai exact: <procentaj_femei>%. femei, <procentaj_barbati>% barbati 
si <procentaj_altii>% altii. Cele mai multe voturi au fost strânse de <CNP> <nume> de categoriile de varsta <categ_age>. 
Acestea constituie <procentaj>% din voturile circumscripției. Cei mai activi au fost persoanele de <categ_age> ani."
3. Ar mai trebui o comanda stergerea votantului: in cazul in care se comite o greseala la adaugarea vontatului: de exemplu
s-a introdus gresit numele sau cnp-ul sau varsta sau daca este neindemanatic sau nu. In asa caz, se sterge votantul (al 
caror date au fost introduse incorect) si se adauga din nou. Inputul la stergere ar trebui sa fie <id_alegeri> <CNP_votant>.
La cazuri am putea avea Succes: "S-a sters votantul <nume_votant"; Nu fost gasit obiectul: "EROARE: Nu există un votant
cu CNP-ul <CNP_votant>"; Id alegeri invalid: "EROARE: Nu există alegeri cu acest id". Cazul "Alegerile nu sunt în stagiul 
ÎN_CURS" nu ar fi necesar, deoarece daca a fost posibila adaugarea initiala, inseamna ca sigur sunt IN_CURS alegerile.



