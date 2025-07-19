import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class NibyStatki {
    public static void main(String[] args) {
        boolean[][] planszaGracza = new boolean[5][5];
        boolean[][] planszaKomputera = new boolean[5][5];
        boolean[][] planszaPorownawcza = new boolean[5][5];
        boolean[][] coWidziGracz = new boolean[5][5];
        Set<String> strzalyKomputera = new HashSet<>();

        Scanner scan = new Scanner(System.in);
        int a = 0;
        int b = 0;

        //faza generowania planszy i ustawiania na niej statków
        System.out.println("Rozpoczęcie fazy rozstawiania statków");

        while (a < 5) {

            odczytajTablice(planszaGracza);

            System.out.println("Wprowadź pierwszą współrzędną (od 1 do 5):");
            int wspolrzednaGracza1 = scan.nextInt() - 1;
            System.out.println("Wprowadź drugą współrzędną (od 1 do 5): ");
            int wspolrzednaGracza2 = scan.nextInt() - 1;

            planszaGracza[wspolrzednaGracza1][wspolrzednaGracza2] = true;

            if ((a >= 1) && (porownajPlansze(planszaGracza, planszaPorownawcza))) {
                System.out.println("Na tym polu już jest statek");
            } else {
                for (int planszai = 0; planszai < planszaGracza.length; planszai++) {
                    System.arraycopy(planszaGracza[planszai],0, planszaPorownawcza[planszai],0, planszaGracza[planszai].length);
                }
                a++;
            }
        }
        odczytajTablice(planszaGracza);

        //teraz komputer będzie ustawiał na swojej planszy statki
        Random random = new Random();

        while (b < 5) {

            int wspolrzednaKomputera1 = random.nextInt(5);
            int wspolrzednaKomputera2 = random.nextInt(5);

            planszaKomputera[wspolrzednaKomputera1][wspolrzednaKomputera2] = true;

            if (!((b >= 1) & (porownajPlansze(planszaKomputera, planszaPorownawcza)))) {
                for (int planszaj = 0; planszaj < planszaKomputera.length; planszaj++) {
                    System.arraycopy(planszaKomputera[planszaj],0, planszaPorownawcza[planszaj], 0, planszaKomputera[planszaj].length);
                }
                b++;
            }
        }
        //System.out.println("Plansza komputera:"); //to i poniższa linijka do skasowania w ostatecznej wersji
        //odczytajTablice(planszaKomputera); //do sprawdzenia, czy plansza dla komputera wygenerowała się prawidłowo

        //rozpoczęcie gry
        int zycieGracza = 5;
        int zycieKomputera = 5;
        int strzalKomputera1;
        int strzalKomputera2;
        System.out.println("Rozpoczęcie gry");

        while (true) {
            System.out.println("Strzelaj! Wprowadź pierwszą współrzędną:");
            int strzalGracza1 = scan.nextInt() - 1;
            System.out.println("Wprowadź drugą współrzędną:");
            int strzalGracza2 = scan.nextInt() - 1;
            coWidziGracz[strzalGracza1][strzalGracza2] = true;

            if (planszaKomputera[strzalGracza1][strzalGracza2]) {
                System.out.println("TRAFIONY! Zatopiono statek ze współrzędnych: " + (strzalGracza1 + 1) + " - " + (strzalGracza2 + 1));
                planszaKomputera[strzalGracza1][strzalGracza2] = false;
                zycieKomputera--;
            } else {
                System.out.println("PUDŁO!");
            }

            System.out.println("Tura przeciwnika (komputer)...");

            while (true) {
                strzalKomputera1 = random.nextInt(5);
                strzalKomputera2 = random.nextInt(5);
                String strzal = strzalKomputera1 + "," + strzalKomputera2;
                if (strzalyKomputera.contains(strzal)) {
                    continue;
                }
                strzalyKomputera.add(strzal);
                break;
            }
            if (planszaGracza[strzalKomputera1][strzalKomputera2]) {
                System.out.println("OBERWAŁEŚ!!! Straciłeś statek ze współrzędnych: " + (strzalKomputera1 + 1) + " - " + (strzalKomputera2 + 1));
                planszaGracza[strzalKomputera1][strzalKomputera2] = false;
                zycieGracza--;
            } else {
                System.out.println("PRZECIWNIK SPUDŁOWAŁ!");
            }
            System.out.println("Ilość statków gracza: " + zycieGracza + "\nTwoja plansza:");
            odczytajTablice(planszaGracza);
            System.out.println("Ilość statków komputera: " + zycieKomputera + "\nPlansza przeciwnika:");
            odczytajCoWidziGracz(coWidziGracz);

            if (zycieKomputera == 0 && zycieGracza == 0) {
                System.out.println("REMIS!");
                break;
            }
            if (zycieKomputera == 0) {
                System.out.println("ZWYCIĘSTWO!");
                break;
            }
            if (zycieGracza == 0) {
                System.out.println("PORAŻKA!");
                break;
            }
        }
        System.out.println("Lista strzałów komputera:"); //to będzie to skasowania w ostatecznej wersji. Albo i nie
        for (String strzal : strzalyKomputera) {
            System.out.println(strzal);
        }
    }

    public static void odczytajTablice(boolean[][] plansza) {
        for (int i = 0; i < plansza.length; i++) {
            for (int j = 0; j < plansza[i].length; j++) {
                if (plansza[j][i]) {
                    System.out.print("X" + "\t");
                } else {
                    System.out.print("_" + "\t");
                }
            }
            System.out.println();
        }
    }

    public static void odczytajCoWidziGracz(boolean[][] plansza) {
        for (int i = 0; i < plansza.length; i++) {
            for (int j = 0; j < plansza[i].length; j++) {
                if (plansza[j][i]) {
                    System.out.print("*" + "\t");
                } else {
                    System.out.print("_" + "\t");
                }
            }
            System.out.println();
        }
    }

    public static boolean porownajPlansze(boolean[][] tab1, boolean[][] tab2) {
        for (int tabi = 0; tabi < tab1.length; tabi++) {
            for (int tabj = 0; tabj < tab2.length; tabj++) {
                if (tab1[tabi][tabj] != tab2[tabi][tabj]) {
                    return false;
                }
            }
        }
        return true;
    }
}