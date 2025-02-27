import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.converters.CSVSaver;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class SurveyApp {
    
    private Classifier classifier;
    private Instances dataset;
    private ArrayList<String[]> surveyResponses = new ArrayList<>();

    public SurveyApp(String modelPath, String datasetPath) throws Exception {
        // Load the trained model
        classifier = (Classifier) weka.core.SerializationHelper.read(modelPath);

        // Load the dataset structure
        DataSource source = new DataSource(datasetPath);
        dataset = source.getDataSet();
        dataset.setClassIndex(dataset.numAttributes() - 1);
    }

    public String predict(Instance instance) throws Exception {
        // Predict the political party
        double result = classifier.classifyInstance(instance);
        return instance.classAttribute().value((int) result);
    }

    public void saveResponsesToCSV() throws Exception {
        // Save the responses to a CSV file for future use
        CSVSaver saver = new CSVSaver();
        saver.setInstances(dataset);
        saver.setFile(new File("user_responses.csv"));
        saver.writeBatch();
    }

    // Method to validate and standardize user input
    private static String getValidResponse(Scanner scanner) {
        String input;
        while (true) {
            input = scanner.nextLine().trim().toUpperCase(); // Trim spaces and convert to uppercase
            if (input.matches("[A-D]")) { // Ensure valid input (A, B, C, or D)
                return input;
            }
            System.out.println("Invalid choice. Please enter A, B, C, or D.");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Path to the trained model and dataset structure
        String modelPath = "trained_model.model"; // Replace with your actual model path
        String datasetPath = "political_data.arff"; // Replace with your actual dataset path

        // Create an instance of the survey app
        SurveyApp surveyApp = new SurveyApp(modelPath, datasetPath);

        // Create a new instance for the user's survey responses
        Instance newSurveyResponse = surveyApp.dataset.firstInstance();

        // Collect user's survey responses
        System.out.println("Welcome to the Political Survey!");

        System.out.println("What should the government do about healthcare?\n" +
                           "A. Implement universal healthcare for all\n" +
                           "B. Reduce government involvement\n" +
                           "C. Provide healthcare only for low-income\n" +
                           "D. Stay out of it");
        String q1 = getValidResponse(scanner);
        newSurveyResponse.setValue(0, q1);

        System.out.println("How should the government handle climate change?\n" +
                           "A. Enforce strict environmental regulations\n" +
                           "B. Let the free market handle it\n" +
                           "C. Invest heavily in renewable energy\n" +
                           "D. Climate change is not a priority");
        String q2 = getValidResponse(scanner);
        newSurveyResponse.setValue(1, q2);

        System.out.println("What role should the government play in education?\n" +
                           "A. Increase funding for public schools\n" +
                           "B. Encourage school choice and privatization\n" +
                           "C. Make college tuition-free\n" +
                           "D. Reduce government involvement");
        String q3 = getValidResponse(scanner);
        newSurveyResponse.setValue(2, q3);

        System.out.println("How should the government address wealth inequality?\n" +
                           "A. Raise taxes on the wealthy\n" +
                           "B. Lower taxes for all to stimulate the economy\n" +
                           "C. Implement universal basic income\n" +
                           "D. The government should not intervene");
        String q4 = getValidResponse(scanner);
        newSurveyResponse.setValue(3, q4);

        System.out.println("What political issues are most important to you?\n" +
                           "A. Economy\n" +
                           "B. Healthcare\n" +
                           "C. Education\n" +
                           "D. Immigration");
        String q5 = getValidResponse(scanner);
        newSurveyResponse.setValue(4, q5);

        System.out.println("Which sources do you trust for political news?\n" +
                           "A. Mainstream media\n" +
                           "B. Independent news outlets\n" +
                           "C. Social Media\n" +
                           "D. Word of mouth");
        String q6 = getValidResponse(scanner);
        newSurveyResponse.setValue(5, q6);

        System.out.println("How often do you discuss politics with friends and family?\n" +
                           "A. Frequently\n" +
                           "B. Occasionally\n" +
                           "C. Rarely\n" +
                           "D. Never");
        String q7 = getValidResponse(scanner);
        newSurveyResponse.setValue(6, q7);

        System.out.println("How do you feel about the current state of democracy in your country?\n" +
                           "A. Strong\n" +
                           "B. Fair\n" +
                           "C. Weak\n" +
                           "D. Corrupt");
        String q8 = getValidResponse(scanner);
        newSurveyResponse.setValue(7, q8);

        // Validate and map political party selection
        System.out.println("Which political party do you identify with?\n" +
                           "A. Democrat\n" +
                           "B. Republican\n" +
                           "C. Independent\n" +
                           "D. Libertarian");

        String party;
        while (true) {
            String partyInput = scanner.nextLine().trim().toUpperCase();
            switch (partyInput) {
                case "A": party = "Democrat"; break;
                case "B": party = "Republican"; break;
                case "C": party = "Independent"; break;
                case "D": party = "Libertarian"; break;
                default:
                    System.out.println("Invalid choice. Please enter A, B, C, or D.");
                    continue;
            }
            break;
        }

        newSurveyResponse.setValue(8, party);

        // Store the user's response
        String[] responses = {q1, q2, q3, q4, q5, q6, q7, q8, party};
        surveyApp.surveyResponses.add(responses);

        // Save responses to CSV for future use
        surveyApp.saveResponsesToCSV();

        // Predict the political party before the end
        String predictedParty = surveyApp.predict(newSurveyResponse);
        System.out.println("Predicted Political Party: " + predictedParty);

        scanner.close();
    }
}
