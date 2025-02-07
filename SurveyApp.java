class SurveyApp{

    public static void main(String[] arg){
        // variables for political score 
        int republican =0 ; 
        int democrat - 0;
        int greenParty = 0;
        int libertarian = 0;

        // creating arrays for both questions and answers 
        String [] questions = {
            "What should the government do about healthcare?", 
            "How should the government handle climate change?",
            "What role should the government play in education?",
            "How should the government address wealth inequality?"
        }; 
        for(int i = 0; i < 4; i++){
            System.out.println(questions[i]);
        };
    }; 
};

