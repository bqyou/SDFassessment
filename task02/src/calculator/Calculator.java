package calculator;

import java.io.Console;

public class Calculator implements Runnable {

    private Console cons;

    public Calculator() {
        this.cons = System.console();
    }

    @Override
    public void run() {
        System.out.println("Welcome.\n");
        String input = "";
        Integer last = 0;
        while (!input.equals("exit")) {
            input = cons.readLine(">");
            if (input.trim().toLowerCase().equals("exit")) {
                break;
            }
            String[] array = input.split(" ");
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
                System.out.println(last);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        }
        System.out.println("Bye bye");
    }

}
