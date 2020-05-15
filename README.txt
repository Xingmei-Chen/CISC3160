* Interpreter program for the simple language
 * 
 * The interpreter utilizes a recusive descending approach to parse the input 
 * program. The given grammar for the simple language has been adjusted, in 
 * order to make it more intuitive for recursive programming. And the grammar 
 * used in the implementation is demonstrated as below:
 * 
 * Program: 
 *      AssignmentList
 * 
 * AssignmentList:
 *      Assignment AssignmentList | ε
 * 
 * Assignment:
 *      Identifier = Exp;
 * 
 * Exp:
 *      Term TermTail
 * 
 * Term:
 *      Fact FactTail
 * 
 * Fact:
 *      ( Exp ) | - Exp | + Exp | Literal | Identifier
 * 
 * TermTail:
 *      + Term TermTail | - Term TermTail | ε
 * 
 * FactTail:
 *      * Fact FactTail | ε

Compile and Run:

javac *.java
java Interpreter [Input file name]

input1.in ~ input4.txt are the test file
Example of run:

java Interpreter input4.txt
