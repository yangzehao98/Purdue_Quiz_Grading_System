import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

//created methods that will replace specified lines in the main
//need return values for guis
//need to change all system.out.println because of guis
//in the main, delete the extra scanner.nextline() line

public class Methods {

    public Methods() {
    }
    public ArrayList<Course> getCourses() throws IOException {
        ReadWrite fileObj = new ReadWrite();
        ArrayList<Course> courses = fileObj.readCourses("courses.txt");
        return courses;
    }

    //new methods:
    //for line 446
    public void viewQuizzesStudent(int courseChoice, User user) throws IOException {
        for (int i = 0; i < getCourses().get(courseChoice - 1).getTakenQuizList().size(); i++) {
            if (getCourses().get(courseChoice - 1).getTakenQuizList()
                    .get(i).getUsername().equals(user.getUserName())) {
                System.out.println(getCourses().get(courseChoice - 1).getStudentTakenQuizzes(i));
            }
        }
    }

    //for line 426
    public void takeQuizzes(int courseChoice, User user, String quizChoice) throws IOException {
        ReadWrite fileObj = new ReadWrite();
        Quiz takenQuiz = getCourses().get(courseChoice - 1).getQuizList()
                .get(Integer.parseInt(quizChoice) - 1).takeQuiz(user.getUserName());
        getCourses().get(courseChoice - 1).addTakenQuiz(takenQuiz);
        fileObj.writeEditedCourses("courses.txt", getCourses());
    }

    //line 92
    public boolean createCourse(String courseName, User user) throws IOException {
        ReadWrite fileObj = new ReadWrite();
        Course newCourse = new Course(user.getUserName(), courseName,
                new ArrayList<Quiz>(), new ArrayList<Quiz>());
        ArrayList<Course> courses = getCourses();
        courses.add(newCourse);
        fileObj.writeEditedCourses("courses.txt", courses);
    }

    //line 145
    public void createQuestion(Question questionObj, String question, String quizName) {
        questionObj.setQuestion(question);
        questionObj.setQuizName(quizName);
    }

    //line 150
    public void createAnswerChoices(Question questionObj, ArrayList<String> choices, String choice) {
        choices.add(choice);
        questionObj.setChoices(choices);

    }

    //line 164
    public void createAnswer(ArrayList<Question> questions, Question questionObj, String answer) {
        questionObj.setAnswer(answer);
        questions.add(questionObj);
    }

    //line 225
    public String toStringQuestion(int courseNumber, String quizChoice) throws IOException {
        String questions = "";
        for (int i = 0; i < getCourses().get(courseNumber).getQuizList()
                .get(Integer.parseInt(quizChoice))
                .getQuestions().size(); i++) {
            questions = getCourses().get(courseNumber).getQuizList()
                    .get(Integer.parseInt(quizChoice))
                    .getQuestions().get(i).toString() + "\nAnswer: " +
                    getCourses().get(courseNumber).getQuizList()
                            .get(Integer.parseInt(quizChoice))
                            .getQuestions().get(i).getAnswer() + "\n";
        }
        return questions;
    }

    //line 375
    public void editAccount(User user, ReadWrite fileObj) throws IOException {
        for (int i = 0; i < getCourses().size(); i++) {
            if (getCourses().get(i).getTeacherName().equals(getCourses())) {
                getCourses().get(i).setTeacherName(user.getUserName());
            }
        }
        fileObj.writeEditedCourses("courses.txt", getCourses());
    }

    //line 382
    public void deleteAccount(User user, ReadWrite fileObj, String oldUsername) throws IOException {
        for (int i = 0; i < getCourses().size(); i++) {
            if (getCourses().get(i).getTeacherName().equals(oldUsername)) {
                getCourses().remove(i);
            }
        }
        fileObj.writeEditedCourses("courses.txt", getCourses());
    }

    //line 409
    public String toStringCourses() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Objects.requireNonNull(getCourses()).size(); i++) {
            sb.append(i + 1 + ": " +
                    getCourses().get(i).getCourseName() + "\n");
        }
        return sb.toString();
    }



}
