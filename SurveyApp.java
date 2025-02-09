import java.io.FileWriter;
import java.io.IOException;
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
            public void askQuestions(){
                Scanner scanner = new Scanner(System.in);
                StringBuilder userResponses = new StringBuilder();

                for(int i = 0; i < questions.length; i++){
                    System.out.println(questions[i]);
                    System.out.print("(A, B, C, D)");
                

                    String answer = scanner.nextLine().trim().toUpperCase(); 

                    while (!answer.matches("[A-D]")){
                        System.out.print("Invalid Input. Please Enter A, B, C, or D:");
                        answer = scanner.nextLine().trim().toUpperCase();
                        }
                        processAnswer(answer); // process the answer.
                        userResponses.append(questions[0]).append("Answer: ").append(answer).append("\n");
                    }
                scanner.close(); // close scanner 
                saveResults(userResponses.toString()); // save responses to a file 
                displayResults(); // display the final results 
            };

            // method to update political scores 
            private void processAnswer(String answer){
                switch (answer) {
                    case "A": republican++; break; 
                    case "B": democrat++; break; 
                    case "C": greenParty++; break; 
                    case "D": libertarian++; break; 
                }
            }

            // save reponses to a file based on dominant political alignment
            private void saveResults(String responses){
                String party = getDominantParty();
                String fileName = party + "_reponses.txt";

                try(FileWriter writer = new FileWriter(fileName, true)){
                    writer.write(responses + "\n" );
                    System.out.println("\nYour reponses have been saved in: " + fileName);
                }catch (IOException e){
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            }

            // determine highest political score
            private String getDominantParty(){
                int max = Math.max(Math.max(democrat, libertarian), Math.max(greenParty, libertarian));
                if (max == democrat) return "Democrat";
                if (max == democrat) return "Republican";
                if (max == democrat) return "Green Party";
                return "Libertarian";
            }

            // Method to display final results 
            private void displayResults(){
                System.out.println("\nSurvey Results:");
                System.out.println("Repulican Score: " + republican);
                System.out.println("Democrat Score: " + democrat);
                System.out.println("Green Party Score: " + greenParty);
                System.out.println("Libertarian Party: " + libertarian);
                System.out.println("Your political alignment is: " + getDominantParty());
            }


        // Main method to run the program
        public static void main(String[] args) {
            Survey mySurvey = new Survey();
            mySurvey.askQuestions();
        }
    }
}
