package cui;

import main.Taal;
import domein.DomeinController;
import exceptions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * ConsoleApplicatie met een console UI.
 *
 * @author groep 31
 */
public class ConsoleApplicatie {

    private ResourceBundle TAAL;

    private final DomeinController dc;

    /**
     * Maak een instantie van de consoleapplicatie.
     *
     * @param dc De domeincontroller
     */
    public ConsoleApplicatie(DomeinController dc) {
        this.dc = dc;

        // Taal = initieel locale van BS van de gebruiker
        TAAL = ResourceBundle.getBundle("properties.i18n");
        Taal.setTaal(TAAL);
    }

    /**
     * Start de console applicatie.
     *
     */
    public void startApplicatie() {
        try (Scanner scanner = new Scanner(System.in)) {
            int keuze;

            // Taal zelf kiezen
            keuze = kiesTaal(scanner);

            // Registreer of meld aan
            if (keuze != 0) {
                keuze = registreerOfMeldAan(scanner);
            } // else: Sluit het programma af

            // Toon het menu & navigeer erdoor tot als je wilt afsluiten
            if (keuze != 0) {
                navigeerMenu(scanner);
            } // else: Sluit het programma af
        }
    }

    /**
     * Kies de taal waarin de consoleapplicatie getoond zal worden.
     *
     * @param scanner De scanner
     * @return De taal als een int, of 0 om af te sluiten
     */
    private int kiesTaal(Scanner scanner) {
        // Kies een TAAL menu
        System.out.println("-------------------------------------------------");
        System.out.println(TAAL.getString("welkom"));
        System.out.println("-------------------------------------------------");
        System.out.println(TAAL.getString("kiesTaal"));
        System.out.println("\t 1. Nederlands");
        System.out.println("\t 2. English");
        System.out.println("\t 3. Français");
        System.out.println("    0. " + TAAL.getString("menu0"));
        System.out.println();

        // Nieuwe locale aanmaken
        Locale locale;
        int keuze = -1;
        while (keuze < 0 || keuze > 3) {
            try {
                // Probeer een keuze te maken
                System.out.print(TAAL.getString("uwKeuze") + ": ");
                keuze = Integer.parseInt(scanner.nextLine());

                // Keuze inlezen gelukt
                switch (keuze) {
                    case 1:
                        // Nederlands
                        System.out.println("");
                        locale = new Locale("nl");
                        Taal.setTaal(ResourceBundle.getBundle("properties.i18n", locale));
                        break;
                    case 2:
                        // Engels
                        System.out.println("");
                        locale = Locale.ENGLISH;
                        Taal.setTaal(ResourceBundle.getBundle("properties.i18n", locale));
                        break;
                    case 3:
                        // Frans
                        System.out.println("");
                        locale = Locale.FRENCH;
                        Taal.setTaal(ResourceBundle.getBundle("properties.i18n", locale));
                        break;
                    case 0:
                        // Afsluiten
                        System.out.println("\n" + TAAL.getString("totVolgende") + "\n");
                        break;
                    default:
                        // Fout getal als input gegeven
                        System.out.format(TAAL.getString("geheelGetalGrenzen") + "\n", 0, 3);
                        break;
                }
            } catch (NumberFormatException e) {
                // Geen getal als input gegeven
                System.out.println(TAAL.getString("geheelGetal"));
            }
        }

        // Taal updaten naar keuze van gebruiker
        TAAL = Taal.getTaal();

        // Keuze returnen
        return keuze;
    }

    /**
     * Kies of je wilt registreren of aanmelden.
     *
     * @param scanner De scanner
     * @return De keuze als een int, of 0 om af te sluiten
     */
    private int registreerOfMeldAan(Scanner scanner) {
        // Registreren en/of aanmelden menu
        System.out.println("-------------------------------------------------");
        System.out.println(TAAL.getString("begroeting"));
        System.out.println("-------------------------------------------------");
        System.out.println(TAAL.getString("wat"));
        System.out.println("\t 1. " + TAAL.getString("aanmelden"));
        System.out.println("\t 2. " + TAAL.getString("registreren"));
        System.out.println("    0. " + TAAL.getString("menu0"));
        System.out.println();

        int keuze = -1;
        while (keuze < 0 || keuze > 2) {
            try {
                // Probeer een keuze te maken
                System.out.print(TAAL.getString("uwKeuze") + ": ");
                keuze = Integer.parseInt(scanner.nextLine());

                // Keuze inlezen gelukt
                switch (keuze) {
                    case 1:
                        // Aanmelden
                        System.out.println("");
                        meldAan(scanner);
                        break;
                    case 2:
                        // Registreren
                        System.out.println("");
                        registreer(scanner);
                        break;
                    case 0:
                        // Afsluiten
                        System.out.println("\n" + TAAL.getString("totVolgende") + "\n");
                        break;
                    default:
                        // Fout getal als input gegeven
                        System.out.format(TAAL.getString("geheelGetalGrenzen") + "\n", 0, 2);
                        break;
                }
            } catch (NumberFormatException e) {
                // Geen getal als input gegeven
                System.out.println(TAAL.getString("geheelGetal"));
            }
        }

        // Keuze returnen
        return keuze;
    }

    /**
     * Registreer.
     *
     * @param scanner De scanner
     */
    private void registreer(Scanner scanner) {
        // Registreren menu & initialisatie
        System.out.println("-------------------------------------------------");
        System.out.println(TAAL.getString("registreren"));
        System.out.println("-------------------------------------------------");
        String naam;
        String wachtwoord;
        boolean gelukt = false;

        while (!gelukt) {
            // Probeer te registreren
            System.out.print(TAAL.getString("naam") + ": ");
            naam = scanner.nextLine().trim();
            System.out.print(TAAL.getString("wachtwoord") + ": ");
            wachtwoord = scanner.nextLine().trim();
            System.out.print(TAAL.getString("wachtwoordBevestiging") + ": ");
            String wachtwoordBevestiging = scanner.nextLine().trim();

            try {
                dc.registreer(naam, wachtwoord, wachtwoordBevestiging);
                dc.meldAan(naam, wachtwoord);

                // Registreren en aanmelden gelukt
                gelukt = true;
                System.out.print("\n" + TAAL.getString("registrerenGelukt"));
                System.out.print(TAAL.getString("aanmeldenGelukt") + "\n");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Meld je aan.
     *
     * @param scanner De scanner
     */
    private void meldAan(Scanner scanner) {
        // Aanmelden menu & initialisatie
        System.out.println("-------------------------------------------------");
        System.out.println(TAAL.getString("aanmelden"));
        System.out.println("-------------------------------------------------");
        String naam;
        String wachtwoord;
        boolean gelukt = false;

        while (!gelukt) {
            // Probeer aan te melden
            System.out.print(TAAL.getString("naam") + ": ");
            naam = scanner.nextLine().trim();
            System.out.print(TAAL.getString("wachtwoord") + ": ");
            wachtwoord = scanner.nextLine().trim();
            try {
                dc.meldAan(naam, wachtwoord);

                // Aanmelden gelukt
                gelukt = true;
                System.out.print("\n" + TAAL.getString("aanmeldenGelukt") + "\n");
            } catch (Exception e) {
                // Een verplicht veld werd niet ingevuld of de speler bestaat nog niet
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Navigeer door het menu.
     *
     * @param scanner De scanner
     */
    private void navigeerMenu(Scanner scanner) {
        // Init
        int keuze = -1;

        // Verwelkom de speler
        System.out.println("\n-------------------------------------------------");
        System.out.println(TAAL.getString("hallo") + ", " + dc.geefSpelerNaam() + "!");

        while (keuze != 0) {
            try {
                // Toon menu
                System.out.println("-------------------------------------------------");
                System.out.println(TAAL.getString("menu"));
                System.out.println("-------------------------------------------------");
                System.out.println(TAAL.getString("wat"));
                System.out.println("\t 1. " + TAAL.getString("menu1"));
                System.out.println("\t 2. " + TAAL.getString("menu2"));
                System.out.println("\t 3. " + TAAL.getString("menu3"));
                System.out.println("\t 4. " + TAAL.getString("menu4"));
                System.out.println("\t 5. " + TAAL.getString("menu5"));
                System.out.println("    0. " + TAAL.getString("menu0"));
                System.out.println();

                // Probeer een keuze te maken
                System.out.print(TAAL.getString("uwKeuze") + ": ");
                keuze = Integer.parseInt(scanner.nextLine());

                // Keuze inlezen gelukt
                switch (keuze) {
                    case 1:
                        // Start een nieuw spel
                        startNieuwSpel(geefMoeilijkheidsgraad(scanner), scanner);
                        break;
                    case 2:
                        // Laad een spel
                        laadSpel(scanner);
                        break;
                    case 3:
                        // Daag iemand uit op een bepaalde moeilijkheidsgraad
                        try {
                            daagUit(geefMoeilijkheidsgraad(scanner), scanner);
                        } catch (SpelerIsInUitdagingException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        // Accepteer een uitdaging
                        try {
                            accepteerUitdaging(scanner);
                        } catch (SpelerIsInUitdagingException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        // Toon het klassement
                        toonKlassement(scanner);
                        break;
                    case 0:
                        // Afsluiten
                        System.out.println("\n" + TAAL.getString("bedankt") + " " + TAAL.getString("totVolgende") + "\n");
                        break;
                    default:
                        // Fout getal gegeven als input
                        System.out.format(TAAL.getString("geheelGetalGrenzen") + "\n", 0, 5);
                        break;
                }
            } catch (NumberFormatException e) {
                // Geen getal gegeven als input
                System.out.println(TAAL.getString("geheelGetal"));
            }
        }
    }

    /**
     * Vraag een moeilijkheidsgraad aan de speler.
     *
     * @param scanner De scanner
     * @return De moeilijkheidsgraad als een int
     * @throws VerplichtVeldException
     * @throws NumberFormatException
     */
    private int geefMoeilijkheidsgraad(Scanner scanner) {
        // Init (moeilijkheidsgraad initieel 'makkelijk')
        int moeilijkheidsgraad = 1;
        boolean gelukt = false;
        int[] winstenSpeler = dc.geefWinstenSpeler();

        // Controleren of de speler moeilijkere spellen mag spelen
        if (winstenSpeler[0] >= 20) {
            // Menu
            System.out.println("\n-------------------------------------------------");
            System.out.println(TAAL.getString("moeilijkheidsgraad"));
            System.out.println("-------------------------------------------------");
            System.out.println(TAAL.getString("kiesGraad"));
            System.out.println("\t 1. " + TAAL.getString("makkelijk") + ", " + TAAL.getString("winsten") + winstenSpeler[0]);
            System.out.println("\t 2. " + TAAL.getString("normaal") + ", " + TAAL.getString("winsten") + winstenSpeler[1]);
            if (winstenSpeler[1] >= 20) {
                System.out.println("\t 3. " + TAAL.getString("moeilijk") + ", " + TAAL.getString("winsten") + winstenSpeler[2]);
            }
            System.out.println();

            while (!gelukt) {
                try {
                    // Keuze ingeven
                    System.out.print(TAAL.getString("uwKeuze") + ": ");
                    moeilijkheidsgraad = Integer.parseInt(scanner.nextLine());
                    gelukt = true;

                    // Parsen keuze gelukt!
                    if ((winstenSpeler[0] >= 20 && winstenSpeler[1] < 20) && (moeilijkheidsgraad > 2 || moeilijkheidsgraad < 1)) {
                        // Je mag alleen makkelijk (1) of normaal (2) kiezen
                        gelukt = false;
                        System.out.println(String.format(TAAL.getString("geheelGetalGrenzen"), 1, 2));
                    }
                    if (winstenSpeler[1] >= 20 && (moeilijkheidsgraad > 3 || moeilijkheidsgraad < 1)) {
                        // Je mag alleen makkelijk (1), normaal (2) of moeilijk (3) kiezen
                        gelukt = false;
                        System.out.println(String.format(TAAL.getString("geheelGetalGrenzen"), 1, 3));
                    }
                } catch (NumberFormatException e) {
                    // Geen getal als input gegeven
                    System.out.println(TAAL.getString("geheelGetal"));
                }
            }
        }

        // Moeilijkheidsgraad returnen
        return moeilijkheidsgraad;
    }

    /**
     * Start een nieuw spel met een bepaalde moeilijkheidsgraad.
     *
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     */
    private void startNieuwSpel(int moeilijkheidsgraad, Scanner scanner) {
        // Init
        System.out.println();

        // Initialiseer het spel in de dc
        dc.initialiseerNieuwSpel(moeilijkheidsgraad);

        // Start het spel
        startSpel(scanner);
    }

    /**
     * Vraag of de speler een zet wilt doen of het spel wilt opslaan.
     *
     * @param scanner De scanner
     * @return Een lijst van kleuren als strings voor input
     */
    private List<String> vraagUserInput(Scanner scanner) {
        // Menu
        System.out.println(TAAL.getString("wat"));
        System.out.println("\t 1. " + TAAL.getString("spelen"));
        System.out.println("\t 2. " + TAAL.getString("bewaren"));
        int keuze;

        // Vraag user of hij wilt spelen of opslaan
        while (true) {
            try {
                // Probeer keuze te maken
                System.out.print(TAAL.getString("uwKeuze") + ": ");
                keuze = Integer.parseInt(scanner.nextLine());

                // Keuze maken gelukt
                switch (keuze) {
                    case 1:
                        // Speel verder
                        System.out.println();
                        return vraagUserPoging(scanner);
                    case 2:
                        // Sla het spel op
                        System.out.println();
                        return slaSpelOp(scanner);
                    default:
                        // Fout getal als input gegeven
                        System.out.println("\n" + String.format(TAAL.getString("geheelGetalGrenzen"), 1, 2) + " " + TAAL.getString("opnieuw"));
                }
            } catch (NumberFormatException e) {
                // Geen getal als input gegeven
                System.out.println("\n" + TAAL.getString("geheelGetal") + " " + TAAL.getString("opnieuw"));
            }
        }
    }

    /**
     * Vraag een zet aan de speler.
     *
     * @param scanner De scanner
     * @return Een lijst van kleuren als strings voor input
     */
    private List<String> vraagUserPoging(Scanner scanner) {
        // Lege rij aanmaken / Init
        List<String> input = new ArrayList<>();
        boolean gelukt = false;

        // Rij opvullen
        System.out.println(TAAL.getString("volgendeZet"));
        for (int i = 0; i < dc.getAantalPinnenPerRij(); i++) {
            while (!gelukt) {
                // Vraag een kleur aan de speler om een codepin te zetten
                System.out.print(TAAL.getString("kleur") + " " + (i + 1) + ": ");
                try {
                    // Controleer of het kleur correct is
                    String kleur = TAAL.getString(scanner.nextLine().trim().toUpperCase());
                    if (dc.getKleuren().contains(kleur)) {
                        // Correct kleur!
                        input.add(kleur);
                        gelukt = true;
                    } else {
                        // Verkeerd kleur! (bestaat wel in het spel)
                        throw new VerplichtVeldException(TAAL.getString("foutePin") + " " + TAAL.getString("opnieuw"));
                    }
                } catch (Exception e) {
                    if (e instanceof MissingResourceException) {
                        // Verkeerd kleur! (bestaat NIET in het spel)
                        System.out.println(TAAL.getString("foutePin") + " " + TAAL.getString("opnieuw"));
                    } else {
                        // Geen correcte string voor een kleur gegeven
                        System.out.println(e.getMessage());
                    }
                }
            }

            // Boolean resetten voor volgend kleur
            gelukt = false;
        }

        // Gevulde rij terug geven
        return input;
    }

    /**
     * Speel het spel opgegeven spel.
     *
     * @param spel Een spel
     * @return 0 als het spel gespeeld is, 1 als het opgeslagen is
     */
    private int startSpel(Scanner scanner) {
        // Init
        boolean tenEinde = false;

        // Speler succes wensen
        System.out.println(TAAL.getString("succes") + "\n\n");

        // Speel het spel
        while (!tenEinde) {
            // Print het spelbord af
            System.out.println(dc.toonSpelbord());

            for (int i = dc.getIngevuldeRijen(); i < dc.getAantalRijen(); i++) {
                // Vraag voor input
                List<String> userInput = vraagUserInput(scanner);

                // Geen user input ==> spel wordt bewaard
                if (userInput == null) {
                    System.out.println("\n" + TAAL.getString("opgeslagen") + "\n\n");
                    return 1; // Spel is bewaard voor later ==> aflsuiten
                }

                // Evalueer de input van de speler en vul deze in
                dc.setEvaluatieUserPoging(userInput, i);

                // Print het spel opnieuw af met de input
                System.out.println(dc.toonSpelbord());

                // Controleer of het de juiste code was
                if (dc.getEvaluatieUserPoging(i)) {
                    break;
                }
            }
            tenEinde = true;
        }

        // Spel spelen succes
        System.out.println(dc.handelGespeeldSpelAf());
        return 0;
    }

    /**
     * Sla het spel op.
     *
     * @param scanner De scanner
     * @return null als het opslaan gelukt is
     */
    private List<String> slaSpelOp(Scanner scanner) {
        // Menu
        System.out.println("-------------------------------------------------");
        System.out.println(TAAL.getString("spelOpslaan"));
        System.out.println("-------------------------------------------------");
        System.out.println(TAAL.getString("opslaan"));
        String naam;
        while (true) {
            // Probeer een naam op te geven voor het spel op te slaan
            System.out.print(TAAL.getString("naam") + ": ");
            naam = scanner.nextLine();

            if (naam != null && !naam.isEmpty()) {
                try {
                    // Naam geven gelukt
                    dc.slaSpelOp(naam);
                    return null;
                } catch (VerplichtVeldException e) {
                    // Naam bestaat al tussen de speler zijn opgeslagen spellen
                    System.out.println(e.getMessage());
                }
            } else {
                // Naam is leeg
                System.out.println(TAAL.getString("nameEmptyError") + "\n");
            }
        }
    }

    /**
     * Laad een spel.
     *
     * @param scanner De scanner
     */
    public void laadSpel(Scanner scanner) {
        // Probeer spel te laden
        List<String> spelNamen = dc.geefOpgeslagenSpellen();
        if (spelNamen.isEmpty()) {
            // Geen spellen gevonden voor de speler
            System.out.println("\n" + TAAL.getString("geenSpellen") + "\n");
        } else {
            // Spellen gevonden
            boolean gelukt = false;
            int teller = 1;

            // Menu
            System.out.println("\n-------------------------------------------------");
            System.out.println(TAAL.getString("laadSpel"));
            System.out.println("-------------------------------------------------");
            System.out.println(TAAL.getString("uwSpellen") + ":");
            for (String string : spelNamen) {
                System.out.println("\t " + teller + ". " + string);
                teller++;
            }
            System.out.println();

            while (!gelukt) {
                try {
                    // Probeer een spel te kiezen
                    System.out.print(TAAL.getString("kiesSpel") + ": ");
                    int keuze = Integer.parseInt(scanner.nextLine());
                    int aantal = spelNamen.size();

                    if (keuze > 0 && keuze <= aantal) {
                        // Spel succesvol gekozen
                        gelukt = true;

                        // Speler succes wensen
                        System.out.println("\n" + TAAL.getString("ladenGelukt"));

                        // Spel laden
                        String spelString = spelNamen.get(keuze - 1);
                        String spelNaam = spelString.substring(0, spelString.indexOf("(")).trim();
                        dc.laadSpel(spelNaam);

                        // Spel starten
                        startSpel(scanner);
                    } else {
                        // Fout getal gegeven als input
                        System.out.format(TAAL.getString("geheelGetalGrenzen") + "\n", 1, aantal);
                    }
                } catch (NumberFormatException e) {
                    // Geen getal gegeven als input
                    System.out.println(TAAL.getString("geheelGetal"));
                }
            }
        }
    }

    /**
     * Daag iemand uit.
     *
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @param scanner De scanner
     * @throws exceptions.SpelerIsInUitdagingException
     */
    public void daagUit(int moeilijkheidsgraad, Scanner scanner) throws SpelerIsInUitdagingException {
        // We kunnen alleen uitdagen als we nog niet in een uitdaging zitten
        if (!dc.isSpelerInUitdaging()) {
            // Init
            List<String> spelers = dc.geefUitdaagbareSpelers(moeilijkheidsgraad);

            // Probeer iemand uit te dagen
            if (spelers == null || spelers.isEmpty()) {
                System.out.println("\n" + TAAL.getString("geenSpelers") + "\n");
            } else {
                // Er kan iemand uitgedaagd worden
                boolean gelukt = false;
                int teller = 1;

                // Menu
                System.out.println("\n-------------------------------------------------");
                System.out.println(TAAL.getString("uitdagen"));
                System.out.println("-------------------------------------------------");
                System.out.println(TAAL.getString("kiesSpeler"));
                for (String string : spelers) {
                    System.out.println("\t " + teller + ". " + string);
                    teller++;
                }
                System.out.println();

                while (!gelukt) {
                    try {
                        // Probeer een speler te kiezen om uit te dagen
                        System.out.print(TAAL.getString("kiesSpeler") + ": ");
                        int keuze = Integer.parseInt(scanner.nextLine());
                        int aantal = spelers.size();

                        if (keuze > 0 && keuze <= aantal) {
                            // Speler succesvol gekozen
                            gelukt = true;

                            // Opslaan van de uitdaging in de databank
                            String spelerString = spelers.get(keuze - 1);
                            String naamUitgedaagde = spelerString.substring(0, spelerString.indexOf(",")).trim().toLowerCase();
                            dc.daagUit(naamUitgedaagde, moeilijkheidsgraad);

                            // Speler succes wensen
                            System.out.println("\n" + TAAL.getString("startUitdaging") + "\n\n");

                            // Starten van het spel voor de uitdager
                            startSpel(scanner);
                        } else {
                            // Fout getal gegeven als input
                            System.out.format(TAAL.getString("geheelGetalGrenzen") + "\n", 1, aantal);
                        }
                    } catch (NumberFormatException e) {
                        // Geen getal gegeven als input
                        System.out.println(TAAL.getString("geheelGetal"));
                    }
                }
            }
        } else {
            // Speler heeft al een openstaande uitdaging
            throw new SpelerIsInUitdagingException("\n" + TAAL.getString("userAlreadyInChallengeError") + "\n");
        }
    }

    /**
     * Accepteer een uitdaging.
     *
     * @param scanner De scanner
     * @throws SpelerIsInUitdagingException
     */
    public void accepteerUitdaging(Scanner scanner) throws SpelerIsInUitdagingException {
        // We kunnen alleen accepteren als we nog niet in een uitdaging zitten
        if (!dc.isSpelerInUitdaging()) {
            // Probeer een lijst van openstaande uitdagingen op te vragen
            List<String> uitdagingen = dc.geefOpgeslagenUitdagingen();

            if (uitdagingen.isEmpty()) {
                // Geen uitdagingen gevonden voor de speler
                System.out.println("\n" + TAAL.getString("geenUitdagingen") + "\n");
            } else {
                // Uitdagingen gevonden!
                boolean gelukt = false;
                int teller = 1;

                // Menu
                System.out.println("\n-------------------------------------------------");
                System.out.println(TAAL.getString("accepteerUitdaging"));
                System.out.println("-------------------------------------------------");

                // Uitdagingen printen
                System.out.println(TAAL.getString("uwUitdagingen") + ":");
                for (String string : uitdagingen) {
                    System.out.println("\t " + teller + ". " + string.substring(0, string.indexOf('(')).trim());
                    teller++;
                }
                System.out.println();

                while (!gelukt) {
                    try {
                        // Probeer een uitdaging te kiezen
                        System.out.print(TAAL.getString("kiesUitdaging") + ": ");
                        int uitdagingIndex = Integer.parseInt(scanner.nextLine());

                        if (uitdagingIndex > 0 && uitdagingIndex <= teller) {
                            // Uitdaging kiezen gelukt
                            gelukt = true;
                            String uitdaging = uitdagingen.get(uitdagingIndex - 1);

                            // Speler succes wensen
                            System.out.println("\n" + TAAL.getString("startUitdaging") + "\n\n");

                            // Uitdaging accepteren (speler linken aan uitdaging)
                            int uitdaging_id = Integer.parseInt(uitdaging.substring(uitdaging.indexOf('(') + 1));
                            dc.accepteerUitdaging(uitdaging_id);

                            // Maak en start een nieuw spel horende bij de uitdaging
                            startSpel(scanner);
                        } else {
                            // Verkeerd getal ingegeven
                            System.out.println(String.format(TAAL.getString("geheelGetalGrenzen"), 1, (teller - 1)));
                        }
                    } catch (NumberFormatException e) {
                        // Geen getal als input gegeven
                        System.out.println("\n" + TAAL.getString("geheelGetal"));
                    }
                }
            }
        } else {
            // Speler heeft al een openstaande uitdaging
            throw new SpelerIsInUitdagingException("\n" + TAAL.getString("userAlreadyInChallengeError") + "\n");
        }
    }

    /**
     * Toon het klassement (voor een bepaalde moeilijkheidsgraad).
     *
     * @param scanner De scanner
     */
    public void toonKlassement(Scanner scanner) {
        // Init
        boolean gelukt = false;
        int moeilijkheidsgraad;

        // Menu
        System.out.println("\n-------------------------------------------------");
        System.out.println(TAAL.getString("klassement"));
        System.out.println("-------------------------------------------------");
        System.out.println(TAAL.getString("kiesGraadKlassement"));
        System.out.println("\t 1. " + TAAL.getString("makkelijk"));
        System.out.println("\t 2. " + TAAL.getString("normaal"));
        System.out.println("\t 3. " + TAAL.getString("moeilijk"));
        System.out.println();

        while (!gelukt) {
            try {
                // Keuze proberen ingeven
                System.out.print(TAAL.getString("uwKeuze") + ": ");
                moeilijkheidsgraad = Integer.parseInt(scanner.nextLine());

                // Parsen keuze gelukt
                if (moeilijkheidsgraad > 0 && moeilijkheidsgraad < 4) {
                    gelukt = true;
                    String[] klassementStrings = new String[]{TAAL.getString("klassement") + " " + TAAL.getString("makkelijk"), TAAL.getString("klassement") + " " + TAAL.getString("normaal"), TAAL.getString("klassement") + " " + TAAL.getString("moeilijk")};

                    // Print het klassement af
                    System.out.println("\n\n-------------------------------------------------");
                    System.out.println(klassementStrings[moeilijkheidsgraad - 1]);
                    System.out.println("-------------------------------------------------");
                    String klassement = dc.geefKlassement(moeilijkheidsgraad);
                    System.out.println(klassement.substring(0, klassement.length() - 2));
                    System.out.println("-------------------------------------------------\n\n");
                } else {
                    // Verkeerd getal ingegeven
                    System.out.println(String.format(TAAL.getString("geheelGetalGrenzen"), 1, 3));
                }
            } catch (NumberFormatException e) {
                // Geen getal als input gegeven
                System.out.println(TAAL.getString("geheelGetal"));
            }
        }
    }

}
