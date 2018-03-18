/* Autor : Piotr Szuberski
*  Uniwersytet Warszawski
*  p.szuberski@student.uw.edu.pl
*/

package farby;

public class Farba {
    private String nazwa;
    private int toksycznosc;
    private int jakosc;

    public Farba(String nazwa, int toksycznosc, int jakosc) throws BladParametrow {
        this.nazwa = nazwa;
        this.toksycznosc = toksycznosc;
        this.jakosc = jakosc;
        if (toksycznosc > 100 || toksycznosc < 0 ||
                jakosc > 100 || jakosc < 0)
            throw new BladParametrow();
    }

    public String nazwa() {
        return nazwa;
    }

    public int toksycznosc() {
        return toksycznosc;
    }

    public int  jakosc() {
        return jakosc;
    }
    
    public void zmienToksycznosc(int toks) throws BladParametrow {
        if (!(toks > 100 || toks < 0))
            toksycznosc = toks;
        else
            throw new BladParametrow();
    }
    
    public void zmienJakosc(int jak) throws BladParametrow {
        if (!(jak > 100 || jak < 0))
            jakosc = jak;
        else
            throw new BladParametrow();
    }
    
    public String toString() {
        return nazwa + " " + toksycznosc + " " + jakosc;
    }
}