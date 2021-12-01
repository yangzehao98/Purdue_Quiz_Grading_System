import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project 4 Group 57 - Question Class
 * Question classes to create Question objects that will be stored in the Quiz Class.
 *
 * @author Evans Tang, section L18
 * @version 11/14/2021
 */

public class Question implements Serializable {
    private String question;
    private ArrayList<String> choices;
    private String quizName;
    private String answer;
    private String studentAnswer;

    public Question(String question, ArrayList<String> choices, String quizName, String answer) {
        this.question = question;
        this.choices = choices;
        this.quizName = quizName;
        this.answer = answer;
    }

    public Question() {
    }


    public String readStudentAnswer(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                answer = line;
                return line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    //creates a String of the question's answer choices
    public String getChoices() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < choices.size(); i++) {
            sb.append(i + 1 + ". " + choices.get(i) + "\n");
        }
        return sb.toString();
    }

    public ArrayList<String> getChoicesArray() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public java.lang.String toString() {
        return question + "\nChoices:\n" + getChoices();
    }

}
