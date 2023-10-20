public enum Categorie {
    EOF,
    $,
    NUL,
    // les terminaux
    OR,
    OA,
    PV,
    OPPAff,
    CH,
    si,
    sinon,
    alors,
    pour,
    entier,
    caractere,
    lire,
    ecrire,
    parent,

    // les non terminaux

    ;

    public static final int MIN = 3, MAX = 17;

    /*
     * La méthode java string toLowerCase () renvoie la chaîne en minuscules. En
     * d'autres termes,
     * il convertit tous les caractères de la chaîne en minuscules.
     */

    public String toString() {
        return this.name().toLowerCase();
    }

    /*
     * La méthode equalsIgnoreCase() compare deux chaînes en ignorant les
     * différences entre
     * minuscules et majuscules et renvoie « true » si les chaînes sont égales sinon
     * renvoie « false ».
     */
    public static Categorie toCategorie(String s) {
        for (Categorie c : Categorie.values())
            if (c.toString().equalsIgnoreCase(s))
                return c;
        return null;
    }

    /*
     * La méthode ordinal() permet de retrouver le numéro d'ordre d'un élément
     * énuméré,
     * dans la liste de tous les éléments d'une énumération. Le premier numéro
     * d'ordre est 0.
     */
    public boolean estTerminal() {
        return ordinal() >= MIN && ordinal() <= MAX;
    }

    public boolean estNonTerminal() {
        return ordinal() > MAX;
    }
}
