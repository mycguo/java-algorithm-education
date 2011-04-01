package edu.effective.java.interview;

/* http://www.cs.utsa.edu/~wagner/CS3723/rdparse/rdparser.html
 *  Arith0.java: simple parser -- no output
 * grammar:
 *   P ---> E '#'
 *   E ---> T {('+'|'-') T}
 *   T ---> S {('*'|'/') S}
 *   S ---> F '^' S | F
 *   F ---> char | '(' E ')'
 */
class Arith {
   private int level = 0;
   private GetChar getChar = new GetChar();
   private char next;

   private void P() {
      scan();
      E();
      if (next != '#') error(1);
      else System.out.println("Successful parse");
   }

   private void E() {
      enter('E');
      T();
      while (next == '+' || next == '-') {
         scan();
         T();
      }
      leave('E');
   }

   private void T() {
      enter('T');
      S();
      while (next == '*' || next == '/') {
         scan();
         S();
      }
      leave('T');
   }

   private void S() {
      enter('S');
      F();
      if (next == '^') {
         scan();
         S();
      }
      leave('S');
   }

   private void F() {
      enter('F');
      if (Character.isLetter(next)) {
         scan();
      }
      else if (next == '(') {
         scan();
         E();
         if (next == ')') scan();
         else error(2);
      }
      else {
         error(3);
      }
      leave('F');
   }

   private void scan() {
      while (Character.isWhitespace(next = getChar.getNextChar()))
         ;
   }

   private void error(int n) {
      System.out.println("*** ERROR: " + n);
      System.exit(1);
   }

   private void enter(char name) {
      spaces(level++);
      System.out.print("+-" + name + ": Enter, \t");
      System.out.println("Next == " + next);
   }

   private void leave(char name) {
      spaces(--level);
      System.out.print("+-" + name + ": Leave, \t");
      System.out.println("Next == " + next);
   }

   private void spaces(int local_level) {
      while (local_level-- > 0)
         System.out.print("| ");
   }

   public static void main(String[] args) {
      Arith arith0 = new Arith();
      arith0.P();
   }
}
