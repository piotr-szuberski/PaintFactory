/* Autor : Piotr Szuberski
*  Uniwersytet Warszawski
*  p.szuberski@student.uw.edu.pl
*/

package farby;

public class Pigment {
    private String nazwa;
    private Modyfikator toksycznoscMod;
    private Modyfikator jakoscMod;

    public Pigment(String nazwa, Modyfikator toksycznosc, Modyfikator jakosc) {
        this.nazwa = nazwa;
        toksycznoscMod = toksycznosc;
        jakoscMod = jakosc;
    }

    public String nazwa() {
        return nazwa;
    }

    public Modyfikator toksycznoscMod() {
        return toksycznoscMod;
    }

    public Modyfikator jakoscMod() {
        return jakoscMod;
    }

    
    public void zmienToksycznosc(char typ, double wartosc) throws BladParametrow {
        if (wartosc >= 0.0 && wartosc <= 100.0)
            toksycznoscMod.zmien(typ, wartosc);
        else
            throw new BladParametrow();
    }
    
    public void zmienJakosc(char typ, double wartosc) throws BladParametrow {
        if (wartosc >= 0.0 && wartosc <= 100.0)
            jakoscMod.zmien(typ, wartosc);
        else
            throw new BladParametrow();
    }
    
    public String toString() {
        return "" + nazwa + " " + toksycznoscMod.toString() + " " +
                jakoscMod.toString();
    }
}
