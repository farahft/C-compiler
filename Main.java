import java.util.*;

public class Main {

    static ArrayList<String> tab = new ArrayList<String>();

    public static void main(String[] args) {

        System.out.println("***********************Analyse Lexical*************************");
        Scanner anaLex = new Scanner(
                "C:/Users/Farah Ftouhi/OneDrive/Documents/IGL3/Compilation/tp/comp/test1.txt");
        // System.out.println(anaLex);

        UniteLexicale ul = null;
        ArrayList<UniteLexicale> tabUni = new ArrayList<>();
        do {
            ul = anaLex.lexemeSuivant();
            System.out.println(ul.toString());
            tabUni.add(ul);

        } while (ul.getCategorie() != Categorie.EOF);

        analyzeSLnew test22 = new analyzeSLnew(tabUni);
    }
}
