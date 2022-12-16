package calculator;

import java.io.Console;

public class Calculator implements Runnable {

    private Console cons;

    // set up console for taking input
    public Calculator() {
        this.cons = System.console();
    }

    @Override
    public void run() {
        System.out.println("Welcome.\n");
        String input = "";
        Integer last = 0; // initialise $last as 0

        // creating a while loop for calculator to keep running until exit is input
        while (!input.equals("exit")) {
            input = cons.readLine(">");
            if (input.trim().toLowerCase().equals("exit")) {
                break;
            }

            // split input by space
            String[] array = input.split(" ");

            // checking for input containing $last and parsing from str to int
            try {
                Integer int1;
                Integer int2;
                if (array[0].equals("$last")) {
                    int1 = last;
                } else {
                    int1 = Integer.parseInt(array[0]);
                }
                if (array[2].equals("$last")) {
                    int2 = last;
                } else {
                    int2 = Integer.parseInt(array[2]);
                }

                // mathematical operations for each case
                switch (array[1]) {
                    case "+":
                        last = int1 + int2;
                        break;
                    case "-":
                        last = int1 - int2;
                        break;
                    case "/":
                        last = int1 / int2;
                        break;
                    case "*":
                        last = int1 * int2;
                        break;
                    default:
                        break;
                }

                // prints out result
                System.out.println(last);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        }

        // prints out byebye if exit the calculator
        System.out.println("Bye bye");
    }

}
