/* Autor : Piotr Szuberski
*  Uniwersytet Warszawski
*  p.szuberski@student.uw.edu.pl
*/

package farby;

import java.io.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KonfiguracjaMaszyny {
    private Map<String, Map<String, String>> listaZaleznosci = new TreeMap<>();
    private Map<String, Farba> listaFarb = new TreeMap<>();
    private Map<String, Pigment> listaPigmentow = new TreeMap<>();

    static SecureRandom rnd = new SecureRandom();

    static final String znakiKolorow = "0123456789aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźż-";
    static final String znakiPigmentow = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    
    public KonfiguracjaMaszyny() throws BladMaszyny {
        File conf = new File("src/farby/maszyna.conf");
        String confLine;

        try (BufferedReader confReader = new BufferedReader(new FileReader(conf))) {

            confLine = confReader.readLine();
            wczytajKolory(confLine);

            confLine = confReader.readLine();
            wczytajPigmenty(confLine);

        } catch (FileNotFoundException e) {
            throw new BladMaszyny();
        } catch (IOException e) {
            throw new BladMaszyny();
        }
    }

    public Map<String, Map<String, String>> listaZaleznosci() {
        return listaZaleznosci;
    }

    public Map<String, Farba> listaFarb() {
        return listaFarb;
    }

    public Map<String, Pigment> listaPigmentow() {
        return listaPigmentow;
    }

    public Farba dodajKolor() throws BladParametrow{
        Farba farba;
        String nazwa;
        int len = rnd.nextInt(16) + 4;

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(znakiKolorow.charAt(rnd.nextInt(znakiKolorow.length())));

        nazwa = sb.toString();

        if (!listaFarb.containsKey(nazwa)) {
            int toksycznosc = rnd.nextInt(101);
            int jakosc = rnd.nextInt(101);
            farba = new Farba(nazwa, toksycznosc, jakosc);
            listaFarb().put(nazwa, farba);
            listaZaleznosci.put(farba.nazwa(), new TreeMap<>());
        }
        else
            throw new BladParametrow();
        
        return farba;
    }

    private Modyfikator losujMod() {
        int len = rnd.nextInt(3);
        double mod;
        mod = 100 * rnd.nextDouble();

        switch (len) {
            case 0 :
                return new Modyfikator(mod, 'x');
            case 1 :
                return new Modyfikator(mod, '+');
            case 2 :
                return new Modyfikator(mod, '-');
            default:
                throw new RuntimeException("rnd nie zadziałał");
        }
    }

    private String losujNazweFarby() {
        int len = rnd.nextInt(14) + 4;
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++)
            sb.append(znakiKolorow.charAt(rnd.nextInt(znakiKolorow.length())));

        return sb.toString();
    }

    private String losujNazwePigmentu() {
        StringBuilder sb = new StringBuilder(7);

        for (int i = 0; i < 7; i++)
            sb.append(znakiPigmentow.charAt(rnd.nextInt(znakiPigmentow.length())));

        return sb.toString();
    }

    public Pigment dodajPigment() throws BladParametrow {
        Pigment pigment;
        String nazwaPigmentu = losujNazwePigmentu();
        String nazwaFarby1 = losujNazweFarby();
        String nazwaFarby2 = losujNazweFarby();
        Modyfikator tox = losujMod();
        Modyfikator jak = losujMod();

        if ((listaZaleznosci.containsKey(nazwaFarby1) &&
                listaZaleznosci.get(nazwaFarby1).containsKey(nazwaPigmentu) &&
                listaZaleznosci.get(nazwaFarby1).get(nazwaPigmentu).equals(nazwaFarby2)))
            throw new BladParametrow();
        
        if (!listaZaleznosci.containsKey(nazwaFarby1))
            listaZaleznosci.put(nazwaFarby1, new TreeMap<>());
        
        listaZaleznosci.get(nazwaFarby1).put(nazwaPigmentu, nazwaFarby2);
        
        if (!listaPigmentow.containsKey(nazwaPigmentu)) {
            pigment = new Pigment(nazwaPigmentu, tox, jak);
            listaPigmentow.put(nazwaPigmentu, pigment);
            return pigment;
        }
        
        return listaPigmentow.get(nazwaPigmentu);
    }

    public void wczytajKolory(String nazwaPliku) throws IOException, BladMaszyny{
        File kolory = new File(nazwaPliku);
        String linia, nazwaKoloru;
        Pattern p = Pattern.compile("^[b|a][\\p{javaLowerCase}\\-\\+]+");
        Matcher m = p.matcher("bcbbaaabbbbbcą-ćca+zzzz");
        System.out.println(p.quote("^[b|a][\\p{javaLowerCase}\\-\\+]+"));
        System.out.println(m);
        int toksycznosc, jakosc;
        String wzorzec = "[" + znakiKolorow + "]+";
        Scanner koloryReader = new Scanner(kolory);
        while (koloryReader.hasNext()) {
            try {

                linia = koloryReader.nextLine();
                Scanner scan = new Scanner(linia);
                nazwaKoloru = scan.next();
                if (!nazwaKoloru.matches(wzorzec))
                    throw new InputMismatchException();
                toksycznosc = scan.nextInt();
                jakosc = scan.nextInt();
                if (scan.hasNext()) throw new InputMismatchException();
                scan.close();

                if (!listaZaleznosci.containsKey(nazwaKoloru))
                    listaZaleznosci.put(nazwaKoloru, new TreeMap<>());
                else
                    throw new InputMismatchException();

                Farba farba = new Farba(nazwaKoloru, toksycznosc, jakosc);

                listaFarb.put(farba.nazwa(), farba);

            } catch (InputMismatchException e) {
                throw new BladMaszyny();
            } catch (NumberFormatException e) {
                throw new BladMaszyny();
            }  catch (BladParametrow e) {
                throw new BladMaszyny();
            }
        }
    }

    private Modyfikator wstawTyp(String napis) throws InputMismatchException, NumberFormatException {
        char c = napis.charAt(0);
        double mod;

        String modString = napis.replaceAll("[x+-]", "");
        mod = Double.parseDouble(modString);

        if (c != 'x' && c != '+' && c != '-')
            throw new InputMismatchException();

        return new Modyfikator(mod, c);
    }


    public void wczytajPigmenty(String nazwaFolderu) throws IOException, BladMaszyny {

        File pigmenty = new File(nazwaFolderu);
        String nazwaPigmentu, toksycznoscString, jakoscString, farba1, farba2, linia;
        Modyfikator toksycznosc, jakosc;
        Scanner pigmentyReader = new Scanner(pigmenty);
        while (pigmentyReader.hasNext()) {
            try {
                linia = pigmentyReader.nextLine();

                Scanner scan = new Scanner(linia);

                nazwaPigmentu = scan.next();
                if (!nazwaPigmentu.matches("[A-Z0-9]+"))
                    throw new InputMismatchException();
                farba1 = scan.next();
                farba2 = scan.next();
                toksycznoscString = scan.next();
                jakoscString = scan.next();
                if (scan.hasNext()) throw new InputMismatchException();
                scan.close();

                toksycznosc = wstawTyp(toksycznoscString);
                jakosc = wstawTyp(jakoscString);

                if (listaZaleznosci.containsKey(farba1) &&
                        listaZaleznosci.get(farba1).containsKey(nazwaPigmentu) &&
                        listaZaleznosci.get(farba1).get(nazwaPigmentu).equals(farba2)) {
                    throw new InputMismatchException();
                }

                if (!listaZaleznosci.containsKey(farba1))
                    listaZaleznosci.put(farba1, new TreeMap<>());
                
                listaZaleznosci.get(farba1).put(nazwaPigmentu, farba2);
                
                if (!listaPigmentow.containsKey(nazwaPigmentu)) {
                    Pigment pigment = new Pigment(nazwaPigmentu, toksycznosc, jakosc);
                    listaPigmentow.put(nazwaPigmentu, pigment);
                }
            } catch (InputMismatchException e) {
                throw new BladMaszyny();
            } catch (NumberFormatException e) {
                throw new BladMaszyny();
            }
        }
        pigmentyReader.close();
    }

}
