import java.io.*;
import java.util.*;

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

            Map<String, double[]> answerWeights = new HashMap<>(); 

            public Survey(){
                answerWeights.put("A", new double[]{0.2,1.0,0.7,0.1});
                answerWeights.put("B", new double[]{1.0,0.2,0.1,0.6}); 
                answerWeights.put("C", new double[]{0.3,.8,1.0,0.2}); 
                answerWeights.put("D", new double[]{0.8,0.1,0.2,1.0}); 
            }
            
            // Method to display questions
            public void askQuestions(){
                Scanner scanner = new Scanner(System.in);
                StringBuilder userResponses = new StringBuilder();

                for(int i = 0; i < questions.length; i++){
                    System.out.println(questions[i]);
                    System.out.print("(A, B, C, D): ");
                

                    String answer = scanner.nextLine().trim().toUpperCase(); 

                    while (!answer.matches("[A-D]")){
                        System.out.print("Invalid Input. Please Enter A, B, C, or D:");
                        answer = scanner.nextLine().trim().toUpperCase();
                        }
                        processAnswer(answer); // process the answer.
                        userResponses.append(questions[i]).append("Answer: ").append(answer).append("\n");

                        // predict political prty mid-survery if confidence is high 
                        String predictedParty = predictParty(); 
                        if (predictedParty != null){
                            System.err.println("\n: Based on your answers so far, your political alignment is likely: " + predictedParty + "\n");
                        }
                    }
                // final questions: ask the user to self-identify their party 
                System.out.println("\nFinal Question: which political party do you identify with?");
                System.out.println("1. Republican");
                System.out.println("2. Democrat");
                System.out.println("3. Green Party");
                System.out.println("4. Libertarian");
                System.out.println("Enter the number corresponding to your party: ");

                int partyChoice = scanner.nextInt(); 
                String actualParty = getPartyName(partyChoice); 

                scanner.close(); // close scanner 

                saveResults(userResponses.toString(), actualParty); // save responses to a file 
                displayResults(); // display the final results 
            };

            // method to update political scores 
            private void processAnswer(String answer){
                double[] weights = answerWeights.get(answer); 
                    republican += weights[0]; 
                    democrat += weights[1]; 
                    greenParty += weights[2]; 
                    libertarian += weights[3]; 
            }

            private String predictParty(){
                double maxScore = Math.max(Math.max(democrat, libertarian), Math.max(greenParty, republican));
                // predicting only one party has a strong lead at least by 2 points 
                if(maxScore - getSecondHighestScore() >= 2){
                    return getDominantParty();
                }
                return null; 
            }

            private double getSecondHighestScore(){
                double[] scores = {republican, greenParty, libertarian, democrat}; 
                Arrays.sort(scores); 
                return scores[2]; // second highest score 
            }

            // determine highest political score
            private String getDominantParty(){
                double max = Math.max(Math.max(democrat, libertarian), Math.max(greenParty, republican));
                if (max == democrat) return "Democrat";
                if (max == republican) return "Republican";
                if (max == greenParty) return "Green Party";
                return "Libertarian";
            }
            
            private String getPartyName(int choice){
                switch(choice){
                    case 1: return "Republican"; 
                    case 2: return "Democrat"; 
                    case 3: return "Green Party"; 
                    case 4: return "Libertarian"; 
                    default: return "unknown"; 
                }
            }

            // save reponses to a file based on dominant political alignment
            private void saveResults(String responses, String actualParty){
                String fileName = "political_data.csv"; 
                boolean fileExists = new File(fileName).exists(); 

                try(FileWriter writer = new FileWriter(fileName, true)){
                    if (!fileExists){
                        writer.write("Republican, Democrat, Greenparty, Libertarian, ActualParty\n");
                    }
                    writer.write(String.format("%.2f,%.2f,%.2f,%.2f,%s\n", 
                        (double)republican,(double) democrat,(double) greenParty, (double)libertarian, actualParty));

                    System.out.println("\nYour responses have been saved in: " + fileName);
                }catch (IOException e){
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            }

            // Method to display final results 
            private void displayResults(){
                System.out.println("\nSurvey Results:");
                System.out.println("Republican Score: " + republican);
                System.out.println("Democrat Score: " + democrat);
                System.out.println("Green Party Score: " + greenParty);
                System.out.println("Libertarian Party: " + libertarian);
                System.out.println("Your political alignment is: " + getDominantParty());
            }
        }
    // Main method to run the program
        public static void main(String[] args) {
            Survey mySurvey = new Survey();
            mySurvey.askQuestions();
    }
}
