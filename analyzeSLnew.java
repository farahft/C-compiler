import java.util.*;

public class analyzeSLnew {
        public Stack<String> sem = new Stack<String>();
        public Stack<String> stackState = new Stack<>();
        public Stack<String> analyse = new Stack<>();
        public Stack<String> stackSymbol = new Stack<>();
        public String strInput;
        public String action = "";
        int index = 0;
        public String[] LRGS = {
                        // "aux1-> AUX", // 0
                        "AUX->T ; AUX", // 0
                        "AUX->T ; E", // 1
                        "T->entier ch", // 2
                        "T->caractere ch", // 3
                        "E->A ; E", // 4
                        "E->P ; E", // 5
                        "E->S ; E", // 6
                        "E->epsilon", // 7
                        "S->si B alors E", // 8
                        "S->si B alors E sinon E", // 9
                        "P->pour ( ch = ch ; ch or ch ; O ) E", // 10
                        "A->lire ch", // 11
                        "A->ecrire ch", // 12
                        "A->ch := O", // 13
                        "O->( ch oa O )", // 14
                        "O->ch", // 15
                        "B->( ch or O )", // 16
                        "B->ch", // 17
                        "or--> <|>|<=|>=|=|<>",
                        "oa -->+|*|-|/",
                        "ch--> ['a'..'z']* ['1'..'9']",
                        "ch -->[1..9]"
        };

        public String[][] tableSLR = {
                        { "etat", ";", "entier", "ch", "caractere", "si", "alors", "sinon", "pour", "(", "=", "or", ")",
                                        "lire",
                                        "ecrire", ":=", "oa", "$", "AUX", "T", "E", "S", "P", "A", "O", "B" },
                        { "0", "err", "s3", "err", "s4", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "1", "2", "err", "err", "err", "err", "err",
                                        "err" },
                        { "1", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "ACC", "err", "err", "err", "err", "err", "err",
                                        "err", "err" },
                        { "2", "s5", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err" },
                        { "3", "err", "err", "s6", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err" },
                        { "4", "err", "err", "s7", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err" },
                        { "5", "r8", "s3", "s15", "s4", "s17", "err", "r8", "16", "err", "err", "err", "err", "s13",
                                        "s14", "err", "err", "r8", "8", "2", "9", "12", "11", "10", "err", "err" },
                        { "6", "r3", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err" },
                        { "7", "r4", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err" },
                        { "8", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "r1", "err", "err", "err", "err", "err", "err",
                                        "err", "err" },
                        { "9", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "r2", "err", "err", "err", "err", "err", "err",
                                        "err", "err" },
                        { "10", "s18", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err" },
                        { "11", "s19", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err" },
                        { "12", "s20", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err" },
                        { "13", "err", "err", "s21", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err" },
                        { "14", "err", "err", "s22", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err" },
                        { "15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err", "s23", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err" },
                        { "16", "err", "err", "err", "err", "err", "err", "err", "err", "s24", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err" },
                        { "17", "err", "err", "s27", "err", "err", "err", "err", "err", "s26", "err", "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "25" },
                        { "18", "r8", "err", "s15", "err", "s17", "err", "r8", "s16", "err", "err", "err", "err", "s13",
                                        "s14",
                                        "err", "err", "r8", "err", "err", "28", "12", "11", "10", "err", "err" },
                        { "19", "r8", "err", "s15", "err", "s17", "err", "r8", "s16", "err", "err", "err", "err", "s13",
                                        "s14",
                                        "err", "err", "r8", "err", "err", "29", "12", "11", "10", "err", "err" },

                        { "20", "r8", "err", "s15", "err", "s17", "err", "r8", "s16", "err", "err", "err", "err", "s13",
                                        "s14",
                                        "err", "err", "r8", "err", "err", "30", "12", "11", "10", "err", "err" },
                        { "21", "r12", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "22", "r13", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "23", "err", "err", "s33", "err", "err", "err", "err", "err", "s32", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "31", "err" },
                        { "24", "err", "err", "s34", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "25", "err", "err", "err", "err", "err", "s35", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "26", "err", "err", "s36", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "27", "err", "err", "err", "err", "err", "r18", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "28", "r5", "err", "err", "err", "err", "err", "r5", "err", "err", "err", "err", "err", "err",
                                        "err",
                                        "err", "err", "r5", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "29", "r6", "err", "err", "err", "err", "err", "r6", "err", "err", "err", "err", "err", "err",
                                        "err",
                                        "err", "err", "r6", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "30", "r7", "err", "err", "err", "err", "err", "r7", "err", "err", "err", "err", "err", "err",
                                        "err",
                                        "err", "err", "r7", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "31", "r14", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "32", "err", "err", "s37", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "33", "r16", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "r16",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "34", "err", "err", "err", "err", "err", "err", "err", "err", "err", "s38", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "35", "r8", "err", "s15", "err", "s17", "err", "r8", "s16", "err", "err", "err", "err", "s13",
                                        "s14",
                                        "err", "err", "r8", "err", "err", "39", "12", "11", "10", "err", "err" },
                        { "36", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "s40", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "37", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "s41", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "38", "err", "err", "s42", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "39", "r9", "err", "err", "err", "err", "err", "s43", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "40", "err", "err", "s33", "err", "err", "err", "err", "err", "s32", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "44", "err" },
                        { "41", "err", "err", "s33", "err", "err", "err", "err", "err", "s32", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "45", "err" },
                        { "42", "s46", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "43", "r8", "err", "s15", "err", "s17", "err", "r8", "s16", "err", "err", "err", "err", "s13",
                                        "s14",
                                        "err", "err", "r8", "err", "err", "47", "12", "11", "10", "err", "err" },
                        { "44", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "s48",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "45", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "s49",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "46", "err", "err", "s50", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "47", "r10", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "48", "err", "err", "err", "err", "err", "r17", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "49", "r15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "r15",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "50", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "s51", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "51", "err", "err", "s52", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "52", "s53", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "53", "err", "err", "s33", "err", "err", "err", "err", "err", "s32", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "54", "err" },
                        { "54", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "s55",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
                        { "55", "r8", "err", "s15", "err", "s17", "err", "r8", "s16", "err", "err", "err", "err", "s13",
                                        "s14",
                                        "err", "err", "r8", "err", "err", "56", "12", "11", "10", "err", "err" },

                        { "56", "r11", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                                        "err", "err",
                                        "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" } };

        public analyzeSLnew(ArrayList<UniteLexicale> tt) {

                action = "";
                index = 0;

                analyse.push("0");

                System.out.println("********pile     	    Entrée            Action***********");
                this.AfficherSLRnew(tt);

                while (index < tt.size())

                {

                        String s = analyse.peek();
                        if (tt.get(index).getCategorie().toString().equals("ch")
                                        || tt.get(index).getCategorie().toString().equals("oa")
                                        || tt.get(index).getCategorie().toString().equals("or")) {
                                action = Action(s, tt.get(index).getCategorie().toString());
                        } else {
                                action = Action(s, tt.get(index).lexeme);

                        }

                        if (action.charAt(0) == 's') {

                                analyse.push(tt.get(index).lexeme);
                                analyse.push(action.substring(1));

                                index++;
                                action = "shift ";

                                AfficherSLRnew(tt);
                        }
                        // Réduction
                        else if (action.charAt(0) == 'r') {

                                String str = LRGS[Integer.parseInt(action.substring(1)) - 1];
                                Sementique(Integer.parseInt(action.substring(1)) - 1);
                                String tabparties[] = str.split("->");

                                String Partiegauche = tabparties[0];
                                // System.out.println("Partiegauche"+Partiegauche);

                                String Partiedroite = tabparties[1];
                                // System.out.println("Partiedroite"+Partiedroite);

                                String tabtoken[] = Partiedroite.split(" ");
                                int taillepile = tabtoken.length + tabtoken.length;

                                if (!(Partiedroite.equals("epsilon"))) {
                                        for (int i = 0; i < taillepile; i++) {
                                                analyse.pop();
                                        }
                                }

                                String sommetpile = analyse.peek();
                                analyse.push(Partiegauche);

                                String x = Action(sommetpile, Partiegauche);

                                analyse.push(x);

                                action = "reduce:" + str;
                                AfficherSLRnew(tt);
                        }
                        // acceptation
                        else if (action == "ACC") {
                                System.out.println("analyze SLR successfully");
                                System.out.println("Semantique " + sem);

                                break;
                        }

                        else
                        // erreur
                        {
                                System.out.println("analyze SLR failled");
                                break;
                        }

                }

        }

        public String Action(String s, String a) {
                for (int i = 1; i < 57; i++) {
                        if (tableSLR[i][0].equals(s)) {
                                for (int j = 1; j < 26; j++) {
                                        if (tableSLR[0][j].equals(a)) {
                                                return tableSLR[i][j];
                                        }
                                }
                        }
                }

                return "err";
        }

        public void AfficherSLR(ArrayList tt) {
                // SLR

                String ss = "---";
                String ss1 = "--";
                int taillepile = analyse.size();
                int taillepilediv2 = taillepile / 2;
                for (int i = 0; i < taillepilediv2; i++)
                        ss = ss + "---";
                int tailleinput = tt.size();
                for (int i = 0; i < tailleinput; i++)
                        ss1 = ss1 + "--";

                strInput = "";
                for (int i = index; i < tt.size(); i++)
                        strInput = strInput + ((UniteLexicale) tt.get(i)).lexeme;

                System.out.printf("%s", analyse + ss1);
                System.out.printf("%s", strInput + ss);
                System.out.printf("%s", action);
                System.out.printf("Semantique " + sem);
                System.out.println();
                System.out.println();
        }

        public void AfficherSLRnew(ArrayList tt) {
                // SLR

                String ss = "---";
                String ss1 = "--";
                int taillepile = analyse.size();
                int taillepilediv2 = taillepile / 2;
                for (int i = 0; i < taillepilediv2; i++)
                        ss = ss + "---";
                int tailleinput = tt.size();
                for (int i = 0; i < tailleinput; i++)
                        ss1 = ss1 + "--";

                strInput = "";
                for (int i = index; i < tt.size(); i++)
                        strInput = strInput + ((UniteLexicale) tt.get(i)).lexeme;

                System.out.printf("%s", analyse + ss1);
                System.out.printf("%s", strInput + ss);
                System.out.printf("%s", action);
                System.out.println();
                System.out.println("Semantique " + sem);
        }

        public void ouput() {

                System.out.println("**********Tableau SLR¨********");

                for (int i = 0; i < 57; i++) {
                        for (int j = 0; j < 26; j++) {
                                System.out.printf("%6s", tableSLR[i][j] + " ");
                        }
                        System.out.println();
                }
                System.out.println("**********Fin tableau SLR********");

        }

        void Sementique(int regle) {
                String temp1 = "";
                String temp2 = "";
                String temp3 = "";

                switch (regle) {

                        case 0:
                                temp1 = sem.pop();
                                temp2 = sem.pop();
                                if (temp1.equals("vide") && (temp2.equals("entier") || temp2.equals("caractere"))) {
                                        sem.push("vide");
                                } else {
                                        sem.push("erreur");
                                }
                                break;
                        case 1:
                                temp1 = sem.pop();
                                temp2 = sem.pop();
                                if (temp1.equals("vide") && (temp2.equals("entier") || temp2.equals("caractere"))) {
                                        sem.push("vide");
                                } else {
                                        sem.push("erreur");
                                }
                                break;
                        case 2:

                                sem.push("entier");
                                break;
                        case 3:
                                sem.push("caractere");
                                break;
                        case 4:
                                temp1 = sem.pop();
                                temp2 = sem.pop();
                                if (temp1.equals("vide") && temp2.equals("vide")) {
                                        sem.push("vide");
                                } else {
                                        sem.push("erreur");
                                }
                                break;
                        case 5:
                                temp1 = sem.pop();
                                temp2 = sem.pop();
                                if (temp1.equals("vide") && temp2.equals("vide")) {
                                        sem.push("vide");
                                } else {
                                        sem.push("erreur");
                                }
                                break;
                        case 6:
                                temp1 = sem.pop();
                                temp2 = sem.pop();
                                if (temp1.equals("vide") && temp2.equals("vide")) {
                                        sem.push("vide");
                                } else {
                                        sem.push("erreur");
                                }
                                break;
                        case 7:
                                sem.push("vide");
                                break;
                        case 8:
                                temp1 = sem.pop();
                                temp2 = sem.pop();
                                if (temp2.equals("boolean") & temp1.equals("vide")) {
                                        sem.push("vide");
                                } else {
                                        sem.push("erreur");
                                }
                                break;
                        case 9:
                                temp1 = sem.pop();
                                temp2 = sem.pop();
                                temp3 = sem.pop();
                                if (temp3.equals("boolean") & temp2.equals("vide") & temp1.equals("vide")) {
                                        sem.push("vide");
                                } else {
                                        sem.add("erreur");
                                }
                                break;
                        case 10:
                                sem.push("vide");
                                break;
                        case 11:
                                sem.push("vide");
                                break;
                        case 12:
                                sem.push("vide");
                                break;
                        case 13:
                                break;
                        case 14:
                                sem.push("entier");
                                break;
                        case 15:
                                break;
                        case 16:
                                sem.push("boolean");
                                break;
                        case 17:
                                break;

                }

        }
}
