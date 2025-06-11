VoteSmart is a Java-based console application that simulates a simplified national voting platform.

When running the application, the App.java file displays a menu with the available commands for the user:
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

When creating elections, the user provides a unique ID and a name for the election. The application checks if the ID already exists and adds the election to the list if valid.

When starting an election, the method changes its internal state to mark the beginning of the voting process.

Adding a circumscriptie associates it with an active election and verifies if the same name has already been used.

When removing a circumscriptie, the method deletes one that was previously added by the user.

When adding candidates, the method requires a name, CNP, and age. It verifies that the CNP is valid (13 characters) and that the candidate is at least 35 years old.

When removing candidates, the method deletes a candidate based on their CNP and the election ID.

When adding votanti, the method assigns them to available circumscriptii, validating the CNP (13 characters), age (at least 18), the name, and whether the person is marked as neindemanatic.

During voting, the method records a vote from a votant to one of the available candidati, using the election ID, the votant’s CNP, and the candidate’s CNP. It checks for fraud: voting more than once, voting in a different circumscriptie, or voting while being neindemanatic (in which case the vote is invalidated).

For generating vote reports per circumscriptie, the method creates a report that shows vote distribution within the specified region. Errors are thrown if: no votes were registered, the circumscriptie does not exist, the election ID is invalid, or the election is still in progress.

When deleting elections, the method displays all previously created elections (added via command 0), showing their ID and name.


In the file Alegeri.java, we have:
Management of circumscriptii, including adding, checking existence, and deletion. These are stored in a Map, where each key represents a circumscriptie name.

Candidate management allows adding, removing, and verifying candidates. The candidate list is stored in a Set to prevent duplicates.

Voter (votant) management allows adding, validating, and checking if a votant exists within a given circumscriptie. Voters are stored in a Map, where the key is the circumscriptie name and the value is a Set of CNPs.

Votes are recorded in a Map where the key is the votant’s CNP and the value is a Set of candidate CNPs they voted for. Fraud is detected if a votant tries to vote more than once.

The election state can be: NEINCEPUT, IN_DESFASURARE, or TERMINAT. The method for ending elections updates this state to TERMINAT.



BONUS – Additional edge cases to consider:

1. When adding a circumscriptie, one could check against a predefined list of regions where voting is not allowed (e.g., very small counties). If such a region is used, an error is returned:
"EROARE: In regiunea <nume_regiune> nu sunt disponibile puncte de votare"

2. If a candidat and a votant share the same CNP but different names, the system currently allows it, but ideally should block this case to prevent identity fraud.

3. For generating reports per circumscriptie, the system currently returns
"EROARE: Încă nu s-a terminat votarea"
if the election is ongoing. However, the system should also verify whether the election has even started, and return:
"EROARE: Încă nu a inceput votarea"

Suggestions for command/refactor improvements:

1. Listing candidates (Listarea candidatilor) should be allowed at any election stage, not just IN_CURS or TERMINAT.

2. Adding a votant could also include a gender field: male/female/other — useful for detailed statistical analysis (Analiză detaliată). Voters could also be grouped into age categories, e.g., 18–34, 35–50, etc., and reports could highlight the most active groups and their distribution.

3. A new command Stergere votant could be added to allow correcting wrong entries (wrong name, CNP, age, or neindemanatic flag).
The command would take input like: <id_alegeri> <CNP_votant> and handle cases such as:

Success: "S-a sters votantul <nume_votant>"

Not found: "EROARE: Nu există un votant cu CNP-ul <CNP_votant>"

Invalid election ID: "EROARE: Nu există alegeri cu acest id"
This command should work even if elections are ongoing (IN_CURS), as the user was allowed to add the votant in the first place.
