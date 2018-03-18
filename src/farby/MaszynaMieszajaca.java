/* Autor : Piotr Szuberski
*  Uniwersytet Warszawski
*  p.szuberski@student.uw.edu.pl
*/

package farby;

public class MaszynaMieszajaca {
    KonfiguracjaMaszyny konfiguracja;

    public MaszynaMieszajaca(KonfiguracjaMaszyny konfiguracja) {
        this.konfiguracja = konfiguracja;
    }

    public KonfiguracjaMaszyny konfiguracja() {
        return konfiguracja;
    }

    private int zmienMod(int bazowaWartosc, Modyfikator modyfikator) {
        int modyfikacja;

        switch (modyfikator.typ()) {
            case 'x' :
                modyfikacja = (int) ((double) bazowaWartosc * modyfikator.wartosc());
                break;
            case '+' :
                modyfikacja = (int) ((double) bazowaWartosc + modyfikator.wartosc());
                break;
            case '-' :
                modyfikacja = (int) ((double) bazowaWartosc - modyfikator.wartosc());
                break;
            default:
                throw new RuntimeException("błedny modw miejscu gdzie powinien"
                        + "być poprawny");
        }
        
        return modyfikacja;
    }

    public Farba mieszaj(Farba baza, Pigment pigment) throws BladMaszyny, BladParametrow {
        int toksycznoscEfekt, jakoscEfekt;
        String nazwaEfekt;

        if (!konfiguracja.listaZaleznosci().containsKey(baza.nazwa()))
            throw new BladMaszyny();
        if (!konfiguracja.listaZaleznosci().get(baza.nazwa()).containsKey(pigment.nazwa()))
            throw new BladMaszyny();

        nazwaEfekt = konfiguracja.listaZaleznosci().get(baza.nazwa()).get(pigment.nazwa());
        toksycznoscEfekt = zmienMod(baza.toksycznosc(), pigment.toksycznoscMod());
        jakoscEfekt = zmienMod(baza.jakosc(), pigment.jakoscMod());

        if (toksycznoscEfekt > 100 || toksycznoscEfekt < 0 || jakoscEfekt > 100 || jakoscEfekt < 0)
            throw new BladParametrow();

        return new Farba(nazwaEfekt, toksycznoscEfekt, jakoscEfekt);
    }

}
