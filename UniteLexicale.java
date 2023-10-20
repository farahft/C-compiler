public class UniteLexicale {
    private Categorie categorie;
    public String lexeme;

    public UniteLexicale(Categorie categorie, String lexeme) {
        this.categorie = categorie;
        this.lexeme = lexeme;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public String toString() {
        return "<" + categorie.toString() + "," + lexeme + ">";
    }
}
