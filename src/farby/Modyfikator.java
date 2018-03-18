/* Autor : Piotr Szuberski
*  Uniwersytet Warszawski
*  p.szuberski@student.uw.edu.pl
*/

package farby;

public class Modyfikator {
    double wartosc;
    char typ;
    
    public Modyfikator(double wartosc, char typ) {
        this.wartosc = zaokraglij(wartosc);
        this.typ = typ;
    }
    
    private double zaokraglij(double x) {
        int xHelp = (int) (100 * x);
        x = ((double) xHelp) / 100.0;
        return x;
    }

    public String toString() {
        String wynik = "" + typ;
        wynik += "" + wartosc;
        return wynik;
    }

    public double wartosc() {
        return wartosc;
    }

    public char typ() {
        return typ;
    }
    
    public void zmien(char typ, double wartosc) {
        this.wartosc = zaokraglij(wartosc);
        this.typ = typ;
    }
}
