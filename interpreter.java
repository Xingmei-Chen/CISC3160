import java.util.HashMap;
import java.util.ArrayList;

public class Interpreter {
    /* Scanner for the input program file */
    Scanner sc;

    HashMap<String, Integer> symbolTable;

 
    ArrayList<Integer> values;

    Interpreter(Scanner sc) {
        this.sc = sc;
        symbolTable = new HashMap<String, Integer>();
        values = new ArrayList<Integer>();
    }

    void program() {
        switch (sc.currentToken()) {
            case IDENTIFIER:
                assignmentList();
                break;
            case EOF:
                break;
            default:
                error();
        }
        for (int i = 0; i < values.size(); i++) {
            System.out.println(values.get(i));
        }
    }

    void assignmentList() {
        switch (sc.currentToken()) {
            case IDENTIFIER:
                assignment();
                assignmentList();
                break;
            case EOF:
                break;
            default:
                error();
        }
    }

    void assignment() {
        int val = 0;
        String var = null;
        switch (sc.currentToken()) {
            case IDENTIFIER:
                var = sc.getID();
                match(Token.IDENTIFIER);
                match(Token.ASSIGN);
                val = exp();
                match(Token.SEMICOLON);
                break;
            default:
                error();
        }
        symbolTable.put(var, val);
        values.add(val);
    }

    int exp() {
        int val = 0, total = 0;
        switch (sc.currentToken()) {
            case LPAREN:
            case ADD:
            case SUB:
            case IDENTIFIER:
            case LITERAL:
                val = term();
                total = termTail(val);
                break;
            default:
                error();
        }
        return total;
    }

    int term() {
        int val = 0, total = 0;
        switch (sc.currentToken()) {
            case LPAREN:
            case ADD:
            case SUB:
            case IDENTIFIER:
            case LITERAL:
                val = fact();
                total = factTail(val);
                break;
            default:
                error();
        }
        return total;
    }

    int fact() {
        int val = 0;
        String var = null;
        switch (sc.currentToken()) {
            case LPAREN:
                match(Token.LPAREN);
                val = exp();
                match(Token.RPAREN);
                break;
            case ADD:
                match(Token.ADD);
                val = exp();
                break;
            case SUB:
                match(Token.SUB);
                val = -exp();
                break;
            case IDENTIFIER:
                var = sc.getID();
                if (!symbolTable.containsKey(var))
                    reportUninitializedVariables();
                val = symbolTable.get(var);
                match(Token.IDENTIFIER);
                break;
            case LITERAL:
                val = sc.getInteger();
                match(Token.LITERAL);
                break;
            default:
                error();
        }
        return val;
    }

    int termTail(int val) {
        int total = val;
        switch (sc.currentToken()) {
            case ADD:
                match(Token.ADD);
                val += term();
                total = termTail(val);
                break;
            case SUB:
                match(Token.SUB);
                val -= term();
                total = termTail(val);
                break;
            case RPAREN:
            case EOF:
            case IDENTIFIER:
            case SEMICOLON:
                break;
            default:
                error();
        }
        return total;
    }

    int factTail(int val) {
        int total = val;
        switch (sc.currentToken()) {
            case MULT:
                match(Token.MULT);
                val *= fact();
                total = factTail(val);
                break;
            case RPAREN:
            case EOF:
            case IDENTIFIER:
            case SEMICOLON:
            case ADD:
            case SUB:
                break;
            default:
                error();
        }
        return total;
    }

    void match(Token t) {
        if (sc.currentToken() == t) {
            sc.nextToken();
        } else {
            error();
        }
    }

    void error() {
        System.out.println("error");
        System.exit(1);
    }

    void reportUninitializedVariables() {
        System.out.println("uninitialized variables");
        System.exit(1);
    }

    public static void main(String args[]) {
        if (args.length == 0)
            return;

        Scanner sc = new Scanner(args[0]);
        Interpreter interpreter = new Interpreter(sc);
        interpreter.program();
    }
}