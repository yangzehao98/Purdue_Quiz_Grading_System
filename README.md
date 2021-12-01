# CS180-Group-57-Project-4
## Option 2: Quiz Tool
## Code submitted on Vocareum by Evan Glenn
## Report submitted by Jade Gu

In order to compile and run our project, please run class Main and enter your answers as prompted.
When interacting with menus in the main method, you must enter the corresponding number of the choice you would like to select. If it is not numbered, enter the string of the option you would like to select.

### Course Class
The construction of a Course object which holds the name of the Teacher, the name of the course, all Quiz objects in the Course (held in an ArrayList of Quiz objects), and all Quiz objects that have been taken by student users (again held in an ArrayList of Quiz objects). Quiz objects contain Question objects.
Special methods include getQuizzes() and getTakenQuizzes(), which format the ArrayLists into a readable output. getStudentTakenQuizzes() has the same functionality as getTakenQuizzes() but it displays the quiz name without the corresponding username, thereby removing redundancy as the student has no need to view their own username. 
Testing done in the main with many complete run throughs of the program.
  
### Quiz Class
The construction of Quiz objects with two variations, a standard Quiz and a taken Quiz. Both contain an ArrayList of Question objects, a quiz name, and a boolean to decide if Questions and question choices will be randomized. The taken Quiz variation is created by the program after a student takes a quiz and has extra attributes such as a string ArrayList of the students answers, the students username, and a timestamp of the completion time.
Special methods include takeQuiz(), which involves the actual taking of quizzes and returns a Quiz object of the taken variant. readTeacherQuestions() involves the importing of a Quiz and its Questions (the teacher still must manually enter answer choices and correct answer).
Testing done in the main with many complete run throughs of the program
  
### Question Class
The construction of Question objects containing the question, a string ArrayList of choices, the quiz name, and the correct answer, and another constructor that includes those fields with the additional field student answer. It works with the Quiz class as it builds Question objects that are stored in class Quiz. 
Special methods include getChoices(), which formats the output of a question as well as readStudentAnswer(), which reads the file that a student would submit their answer on.
Testing done in the main with many complete run throughs of the program as well as class QuestionTest

#### RunLocalTest Class
The RunLocalTest class tests all of our classes through the Main Class. The file that stores courses and users, User.txt and courses.txt, must be empty before running; it also uses the text file "QuestionTest.txt" with one line with "b" on it for testing the method readStudentAnswer(). It tests our implementation by running through the creation of accounts for a student and teacher, and it also tests that the student and teacher are able to log in. Our test class also checks the method readStudentAnswer() from the Question Class by reading from the file QuestionTest.txt and comparing the method's outcome to the expected outcome. The outcome was successful as the text file says "b," the expected outcome was "b," and the actual outcome from the method was "b." Therefore, we can confirm that we are successfully reading students’ answers from a file. We can also confirm that users are able to create accounts, that their data is being saved, and that users can log back into their accounts. There are also two more tests called makeACourseAndQuiz(), which tests whether a teacher is able to make a course and quiz and save it, and another test called takeAQuiz, which tests whether a student was able to take a quiz in a course a teacher had created. Both were successful, meaning our code can successfully create courses and quizzes that students can take.

 
### User Class
The User class allows us to register, login, and delete accounts. It includes the method Register(), which allows the user to input their username, password, and role (either teacher or student), checks if their username is unique, and if it is unique, it stores the user’s username in the user file. There is also the Login() method, which allows a user to login given their username, password, and role, and it then checks if the account exists and the password is correct. If the account has some sort of error (their account doesn’t exist or password is incorrect), the method will show an error message and loop. There are also methods to delete accounts (deleteAccount()) and edit accounts (editAccount()). 
Testing done in the main with many complete run throughs of the program.
 
### Main Class
The main class requires that you have all other classes available. It begins with a sign in menu which allows for registering and logging in using User methods. From there it launches the teacher or student menu, depending on the user type. Both menus allow for the editing and deleting of that user’s account. 

Before menus are launched, the ‘courses’ text file is read to create an ArrayList of Course objects that can be manipulated by the readCourses() method. All edits to these objects are immediately written to the file with writeEditedCourses().
The teacher menu includes creating and viewing courses, however the control flow is structured so that if the courses ArrayList is empty, it will not allow you to view courses and instead will prompt you to create a course before proceeding. 
  
  
  
  
  
  
