import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Scanner {
    private ArrayList<Character> fluxCaracteres;
    private int indiceCourant;
    private char caractereCourant;
    private boolean eof;

    public Scanner() {
        this("");
    }

    public Scanner(String nomFich) {
        BufferedReader f = null;
        int car = 0;
        fluxCaracteres = new ArrayList<Character>();
        indiceCourant = 0;
        eof = false;
        try {
            f = new BufferedReader(new FileReader(nomFich));
        } catch (IOException e) {
            System.out.println("taper votre texte ci-dessous (ctrl+z pour finir)");
            f = new BufferedReader(new InputStreamReader(System.in));
        }

        try {
            while ((car = f.read()) != -1)
                fluxCaracteres.add((char) car);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void caractereSuivant() {
        if (indiceCourant < fluxCaracteres.size())
            caractereCourant = fluxCaracteres.get(indiceCourant++);
        else
            eof = true;
    }

    public void reculer() {
        if (indiceCourant > 0)
            indiceCourant--;
    }

    // @Override
    // public String toString() {
    // return fluxCaracteres.toString();
    // }

    public UniteLexicale lexemeSuivant() {
        caractereSuivant();

        while (eof || Character.isWhitespace(caractereCourant)) {
            if (eof)
                return new UniteLexicale(Categorie.EOF, "$");
            caractereSuivant();
        }

        if (Character.isLetterOrDigit(caractereCourant)) {
            return getCH();
        }

        if (caractereCourant == ':')
            return getOPPAff();

        if (caractereCourant == ';')
            return new UniteLexicale(Categorie.PV, ";");

        if (caractereCourant == '<' || caractereCourant == '>' || caractereCourant == '=')
            return getOR();
        if (caractereCourant == '+' || caractereCourant == '*' || caractereCourant == '/' || caractereCourant == '-')
            return new UniteLexicale(Categorie.OA, caractereCourant + "");
        if (caractereCourant == '(' || caractereCourant == ')')
            return new UniteLexicale(Categorie.parent, caractereCourant + "");

        return null;
    }

    public UniteLexicale getCH() {
        int etat = 0;
        StringBuffer sb = new StringBuffer();
        while (true) {
            switch (etat) {
                case 0:
                    etat = 1;
                    sb.append(caractereCourant);
                    break;
                case 1:
                    caractereSuivant();
                    if (eof)
                        etat = 3;
                    else if (Character.isLetterOrDigit(caractereCourant))
                        sb.append(caractereCourant);
                    else
                        etat = 2;
                    break;
                case 2:
                    reculer();
                    etat = 3;
                case 3:
                    if ((sb.toString()).equals("si")) {
                        return new UniteLexicale(Categorie.si, "si");
                    } else {
                        if ((sb.toString()).equals("sinon")) {
                            return new UniteLexicale(Categorie.sinon, "sinon");
                        } else {
                            if ((sb.toString()).equals("alors")) {
                                return new UniteLexicale(Categorie.alors, "alors");
                            } else {
                                if ((sb.toString()).equals("pour")) {
                                    return new UniteLexicale(Categorie.pour, "pour");
                                } else {
                                    if ((sb.toString()).equals("entier")) {
                                        return new UniteLexicale(Categorie.entier, "entier");
                                    } else {
                                        if ((sb.toString()).equals("caractere")) {
                                            return new UniteLexicale(Categorie.caractere, "caractere");
                                        } else {
                                            if ((sb.toString()).equals("lire")) {
                                                return new UniteLexicale(Categorie.lire, "lire");
                                            } else {
                                                if ((sb.toString()).equals("ecrire")) {
                                                    return new UniteLexicale(Categorie.ecrire, "ecrire");
                                                } else {
                                                    return new UniteLexicale(Categorie.CH, sb.toString());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }

    public UniteLexicale getOPPAff() {
        int etat = 0;
        StringBuffer sb = new StringBuffer();
        while (true) {
            switch (etat) {
                case 0:
                    if (eof)
                        break;
                    else if (caractereCourant == ':') {
                        sb.append(caractereCourant);
                        caractereSuivant();
                        etat = 1;

                    } else
                        break;

                case 1:
                    if (eof)
                        break;
                    else if (caractereCourant == '=') {
                        sb.append(caractereCourant);
                        caractereSuivant();
                        etat = 2;

                    } else
                        break;

                case 2:
                    if (eof)
                        etat = 3;
                    else
                        etat = 4;
                case 3:

                    return new UniteLexicale(Categorie.OPPAff, sb.toString());
                case 4:
                    reculer();
                    return new UniteLexicale(Categorie.OPPAff, sb.toString());

            }

        }
    }

    public UniteLexicale getOR() {
        int etat = 0;
        StringBuffer sb = new StringBuffer();
        while (true) {
            switch (etat) {
                case 0:
                    if (eof)
                        break;
                    else if (caractereCourant == '=') {
                        sb.append(caractereCourant);
                        caractereSuivant();
                        etat = 1;

                    } else if (caractereCourant == '>') {
                        sb.append(caractereCourant);
                        caractereSuivant();
                        etat = 2;
                    } else if (caractereCourant == '<') {
                        sb.append(caractereCourant);
                        caractereSuivant();
                        etat = 3;
                    } else
                        break;

                case 1:
                    if (eof)
                        etat = 4;
                    else
                        etat = 6;

                case 2:
                    if (eof)
                        etat = 4;
                    else if (caractereCourant == '=') {
                        sb.append(caractereCourant);
                        caractereSuivant();
                        etat = 5;
                    }

                case 3:
                    if (eof)
                        etat = 4;
                    else if (caractereCourant == '=') {
                        sb.append(caractereCourant);
                        caractereSuivant();
                        etat = 5;
                    } else if (caractereCourant == '>') {
                        sb.append(caractereCourant);
                        caractereSuivant();
                        etat = 5;
                    } else
                        etat = 6;

                case 4:
                    return new UniteLexicale(Categorie.OR, sb.toString());

                case 5:
                    if (eof) {
                        return new UniteLexicale(Categorie.OR, sb.toString());
                    } else {
                        reculer();
                        return new UniteLexicale(Categorie.OR, sb.toString());
                    }

                case 6:
                    reculer();
                    return new UniteLexicale(Categorie.OR, sb.toString());

            }

        }
    }

}