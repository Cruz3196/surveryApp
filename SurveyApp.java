import java.util.Scanner;

public class SurveyApp {

    // Inner class for the survey
    public static class Survey {
        // Variables for political scores
        int republican = 0;
        int democrat = 0;
        int greenParty = 0;
        int libertarian = 0;

        // Questions array
        String[] questions = {
        // Question 1 
            "What should the government do about healthcare?\n" +
            "A. Implement universal healthcare for all\n" +
            "B. Reduce government involvement\n" +
            "C. Provide healthcare only for low-income\n" +
            "D. Stay out of it\n",
        // Question 2 
            "How should the government handle climate change?\n" +
            "A. Enforce strict environmental regulations\n" +
            "B. Let the free market handle it\n" +
            "C. Invest heavily in renewable energy\n" +
            "D. Climate change is not a priority\n",
        // Question 3
            "What role should the government play in education?\n" +
            "A. Increase funding for public schools\n" +
            "B. Encourage school choice and privatization\n" +
            "C. Make college tuition-free\n" +
            "D. Reduce government involvement\n",
        // Question 4
            "How should the government address wealth inequality?\n" +
            "A. Raise taxes on the wealthy\n" +
            "B. Lower taxes for all to stimulate the economy\n" +
            "C. Implement universal basic income\n" +
            "D. The government should not intervene\n"
        };

        // Method to display questions
        // when you "void" when you don't want to return anything. 
        public void askQuestions() {
            Scanner scanner = new Scanner(System.in);
            for (String question : questions) {
                System.out.println(question);
                System.out.print("Your answer (A/B/C/D): ");
                String answer = scanner.nextLine().toUpperCase();

                // You can implement logic to adjust scores here based on answers
            }
            scanner.close();
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        Survey mySurvey = new Survey();
        mySurvey.askQuestions();
    }
}
