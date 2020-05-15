import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

class Scanner {
   
    String patternID = "[a-zA-Z_][a-zA-Z_0-9]*";
 
    String patternLiteral = "0|[1-9][0-9]*";

    BufferedReader in;

    Token token;
 
    StringBuilder tokenImage;

    Scanner(String filename) {
        try {
            this.in = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not exists");
        }
        
        this.token = this.nextToken();
    }

    
    public boolean isLetter(char c) {
        return Character.isLetter(c) || c == '_';
    }

    
    public boolean isLetterOrDigit(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

   
    public String getID() {
        return this.tokenImage.toString();
    }

   
    public int getInteger() {
        return Integer.parseInt(this.tokenImage.toString());
    }

    
    public Token currentToken() {
        return this.token;
    }

    public Token nextToken() {
        try {
            int c = this.in.read();
            
            while (Character.isWhitespace(c) && c != -1) {
                c = this.in.read();
            }
            
            if (c == -1) {
                this.token = Token.EOF;
            } else {
                switch ((char) c) {
                    
                    case ';':
                        this.token = Token.SEMICOLON;
                        break;

                    case '(':
                        this.token = Token.LPAREN;
                        break;

                    case ')':
                        this.token = Token.RPAREN;
                        break;

                    case '=':
                        this.token = Token.ASSIGN;
                        break;

                    case '+':
                        this.token = Token.ADD;
                        break;

                    case '-':
                        this.token = Token.SUB;
                        break;

                    case '*':
                        this.token = Token.MULT;
                        break;

                    default: {
                        boolean continued = true;
                        this.tokenImage = new StringBuilder();
                        
                        if (Character.isDigit((char) c)) {
                            while (continued) {
                                this.tokenImage.append((char) c);
                                this.in.mark(1);
                                c = this.in.read();
                                continued = c != -1 && Character.isDigit((char) c);
                                if (!continued) {
                                    this.in.reset();
                                }
                            }
                        }
                        
                       
                        else if (isLetter((char) c)) {
                            while (continued) {
                                this.tokenImage.append((char) c);
                                this.in.mark(1);
                                c = this.in.read();
                                continued = c != -1 && (isLetterOrDigit((char) c));
                                if (!continued) {
                                    this.in.reset();
                                }
                            }
                        } else {
                            this.tokenImage.append((char) c);
                        }

                        if (this.tokenImage.toString().matches(this.patternID)) {
                            
                            this.token = Token.IDENTIFIER;
                        } else if (this.tokenImage.toString().matches(this.patternLiteral)) {
                          
                            this.token = Token.LITERAL;
                        } else {
                            
                            this.token = Token.UNKNOWN;
                        }

                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("READ ERROR");
        }
        return this.token;
    }
}