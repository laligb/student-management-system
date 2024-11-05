package com.enrollment;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
// import java.beans.EventHandler;
import java.io.IOException;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

/**
 * JavaFX App
 */
public class App extends Application {

  /**
   * Public static main method that launches the GUI app.
   * @param args
   */
  public static void main(String[] args) {
    launch();
  }

  /**
   * Private variables
   */
  private TableView<Student> studentTableView;
  CourseManagement courseManagement = new CourseManagement();

  /**
   * Method that creates the scene and shows the window in GUI.
   */
  @Override
  public void start(@SuppressWarnings("exports") Stage stage) throws IOException {

    /**
     * Loads student's data.
     */
    studentData();

    /**
     * Creates the panel of window
     */
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(titleBox());
    borderPane.setCenter(centerPanel());

    /**
     * Final settings of scene and show the window
     */
    Scene scene = new Scene(borderPane, 800, 600);
    stage.setScene(scene);
    stage.setTitle("Student Management System");
    stage.show();

  }

  /**
   * Created the main GUI container inside of window. It contains title, buttons,
   * tables and styles.
   * @return the panel scene.
   */
  private VBox centerPanel() {
    VBox centerPanel = new VBox();
    centerPanel.setAlignment(Pos.CENTER);
    centerPanel.setStyle("-fx-padding: 20px;");

    Label infoLabel = new Label("Student List:");


    infoLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
    infoLabel.setStyle("-fx-padding: 20px;");

    Button addStudentButton = manageStudent("Add Student");
    addStudentButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-padding: 10px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 5px;");
    addStudentButton.setOnMouseExited(e -> addStudentButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-padding: 10px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 5px;"));
    addStudentButton.setOnAction(e -> addStudentForm());

    studentTableView = studentTableView();


    centerPanel.getChildren().addAll(infoLabel, addStudentButton, studentTableView);

    return centerPanel;

  }

  /**
   * Method that designs the box of title
   * @return titleBox
   */
  private HBox titleBox() {
    Label title = titleLabel();
    HBox titleBox = new HBox(title);
    titleBox.setAlignment(Pos.CENTER);
    titleBox.setStyle("-fx-padding: 10px;");
    return titleBox;
  }

  /**
   * Method that creates the main title of window.
   * @return Label title
   */
  private Label titleLabel() {
    Label title = new Label("Student Management System");
    title.setTextFill(Color.DARKBLUE);
    title.setFont(Font.font("System", FontWeight.BOLD, 40));
    return title;
  }

  /**
   * Method that creates general button for actions
   * @param action string variable of action "add", "update" ...
   * @return button with style
   */
  private Button manageStudent(String action) {
    Button button = new Button();
    button.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-padding: 10px; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;");
    button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-padding: 10px; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;"));
    button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: black;  -fx-padding: 10px; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;"));
    button.setText(action);
    return button;
  }

  /**
   * Method that creates GUI of view as Table. Inside of table it shows the list of students,
   * their courses and action buttons: view, update, enroll, and assign grade.
   * @return the table in GUI
   */
  @SuppressWarnings("unchecked")
  private TableView<Student> studentTableView() {
        studentTableView = new TableView<>();

        TableColumn<Student, String> nameColumn = new TableColumn<>("Student Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Student, String> coursesAndGradesColumn = new TableColumn<>("Courses & Grades");
        coursesAndGradesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFormattedCourses()));

        TableColumn<Student, Void> actionsColumn = new TableColumn<>("Actions");

        /**
         * Adds action buttons in table
         */
        actionsColumn.setCellFactory(col -> new TableCell<Student, Void>() {

          private final Button updateButton = manageStudent("Update");
          private final Button viewButton = manageStudent("View");
          private final Button enrollButton = manageStudent("Enroll");
          private final Button assignButton = manageStudent("Assign grade");


          /**
           * Method that updates the item
           * @param item
           * @param empty
           */
          @Override
          protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getIndex() >= studentTableView.getItems().size()) {
                setGraphic(null);
            } else {
                Student currentStudent = studentTableView.getItems().get(getIndex());
                updateButton.setOnAction(e -> updateStudentForm(currentStudent));
                viewButton.setOnAction(e -> viewStudentInfo(currentStudent));
                enrollButton.setOnAction(e -> enrollStudentForm(currentStudent));
                assignButton.setOnAction(e -> assignGradeForm(currentStudent));


                HBox buttons = new HBox(4 ,updateButton, viewButton, enrollButton, assignButton);
                setGraphic(buttons);


            }
          }
        });

        studentTableView.getColumns().addAll(nameColumn, coursesAndGradesColumn, actionsColumn);
        studentTableView.setItems(getStudentData());

        return studentTableView;
    }

    /**
     * Method that gets the student lits from static CourseManagement list
     * @return student list
     */
    private ObservableList<Student> getStudentData() {
      return FXCollections.observableArrayList(CourseManagement.getStudentList());
    }

    /**
     * Forms
     */

    /**
     * Method that calls new window to view details of student
     */
    private void viewStudentInfo(Student student) {
      Stage stage = new Stage();
      VBox vbox = new VBox(10);
      vbox.setAlignment(Pos.CENTER);
      vbox.setStyle("-fx-padding: 20px; -fx-background-color: white;");

      Label title = new Label("Student Details");
      Label id = new Label("ID: " + student.getId());
      Label name = new Label("Name: " + student.getName());
      Label courses = new Label("Courses: " + student.getFormattedCourses());

      Button closeButton = new Button("Close");
      closeButton.setOnAction(e -> stage.close());

      vbox.getChildren().addAll(title, id, name, courses, closeButton);

      Scene scene = new Scene(vbox, 700, 500);
      stage.setScene(scene);
      stage.show();
    }

    /**
     * Method that handles errors and shows in alert message
     * @param title
     * @param message
     */
    private void showAlert(String title, String message) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle(title);
      alert.setHeaderText("Error");
      alert.setContentText(message);
      alert.showAndWait();
    }

    /**
     * Form that adds a new student in a Student list and shows in GUI table
     */
    private void addStudentForm(){
      Stage addStage = new Stage();
      VBox vbox = new VBox(10);
      vbox.setAlignment(Pos.CENTER);
      vbox.setStyle("-fx-padding: 20px; -fx-background-color: white;");

      Label id = new Label("ID");
      TextField idField = new TextField();

      Label name = new Label("Student name");
      TextField nameField = new TextField();

      Button submitButton = new Button("Submit");
      submitButton.setOnAction(e -> {
        String studentName = nameField.getText();
        int idValue;
        /**
         * Handles the error if id is not valid
         */
        try {
            idValue = Integer.parseInt(idField.getText());

            Student newStudent = new Student(studentName, idValue);
            CourseManagement.addStudent(newStudent);
            addStage.close();
            studentTableView.setItems(getStudentData());
            studentTableView.refresh();
        } catch (NumberFormatException ex) {
            showAlert("Input Error", "Please enter a valid number for ID.");
        }
    });

      vbox.getChildren().addAll(id, idField, name, nameField, submitButton);

      Scene scene = new Scene(vbox, 300, 200);
      addStage.setScene(scene);
      addStage.show();
    }

    /**
     * Updates the information of student
     * @param student Student
     */
    private void updateStudentForm(Student student) {
      Stage stage = new Stage();
      VBox vbox = new VBox(10);
      vbox.setAlignment(Pos.CENTER);
      vbox.setStyle("-fx-padding: 20px; -fx-background-color: white;");

      Label title = new Label("Update Student Info");

      Label id = new Label("ID");
      TextField idField = new TextField(String.valueOf(student.getId()));
      idField.setEditable(false);

      Label name = new Label("Student Name");
      TextField nameField = new TextField(student.getName());




      Button submitButton = new Button("Submit");
      submitButton.setOnAction(e -> {
          String newName = nameField.getText();
          if (newName.trim().isEmpty()) {
              showAlert("Input Error", "Name cannot be empty.");
              return;
          }

          student.setName(nameField.getText());
          CourseManagement.updateStudent(student);
          stage.close();

          // Refresh the student data
          studentTableView.setItems(getStudentData());
          studentTableView.refresh();

      });

      vbox.getChildren().addAll(title, id, idField, name, nameField, submitButton);

      Scene scene = new Scene(vbox, 300, 200);
      stage.setScene(scene);
      stage.show();
    }

    /**
     * Method that opens a new form to enroll the student in a course
     */
    private void enrollStudentForm(Student student) {
      Stage enrollStage = new Stage();
      VBox vbox = new VBox(10);
      vbox.setAlignment(Pos.CENTER);
      vbox.setStyle("-fx-padding: 20px; -fx-background-color: white;");

      Label title = new Label("Enroll " + student.getName() + " in a Course");

      ComboBox<Course> courseComboBox = new ComboBox<>();

      for (Course course : CourseManagement.getCourseList()) {
        if (!student.getEnrolledCourses().contains(course)) {
          courseComboBox.getItems().add(course);
        }
      }

      Button enrollButton = new Button("Enroll");
      enrollButton.setOnAction(e -> {
        Course selectedCourse = courseComboBox.getValue();
        if (selectedCourse != null) {
            CourseManagement.enrollStudent(student, selectedCourse);
            System.out.println(student.getName() + " enrolled in course: " + selectedCourse.getName());
            enrollStage.close();
            studentTableView.refresh();
        } else {
            showAlert("Selection Error", "Please select a course.");
        }
      });

      vbox.getChildren().addAll(title, courseComboBox, enrollButton);
      Scene scene = new Scene(vbox, 300, 200);
      enrollStage.setScene(scene);
      enrollStage.show();


    }


      /**
       * Form that assigns grades to a student from the list of courses it
       * selects one and gives the grade
       */
      private void assignGradeForm(Student student) {
        Stage gradeStage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: white;");

        Label title = new Label("Assign Grade to " + student.getName());
        Label courseLabel = new Label("Select Course:");

        ComboBox<Course> courseComboBox = new ComboBox<>();

        for (Course course : student.getEnrolledCourses()) {
            courseComboBox.getItems().add(course);
        }

        Label gradeLabel = new Label("Grade:");
        TextField gradeField = new TextField();

        Button submitButton = new Button("Assign Grade");
        submitButton.setOnAction(e -> {
          Course selectedCourse = courseComboBox.getValue();
          float grade;

          try {
              grade = Float.parseFloat(gradeField.getText());
              if (selectedCourse != null) {
                  CourseManagement.assignGrade(student, selectedCourse, grade);
                  System.out.println("Grade assigned: " + grade + " for course: " + selectedCourse.getName());
                  gradeStage.close();
                  studentTableView.refresh();
              } else {
                  showAlert("Selection Error", "Please select a course.");
              }
          } catch (NumberFormatException ex) {
              showAlert("Input Error", "Please enter a valid grade.");
          }
        });


        vbox.getChildren().addAll(title, courseLabel, courseComboBox, gradeLabel, gradeField, submitButton);
        Scene scene = new Scene(vbox, 300, 200);
        gradeStage.setScene(scene);
        gradeStage.setTitle("Assign Grade");
        gradeStage.show();
    }


    /**
     * Mock student data for testing
     */
    private void studentData() {
    // List of courses for testing
      Course math = new Course(122, "Math", 3);
      Course physics = new Course(245, "Physics", 10);
      Course biology = new Course(456, "Biology", 12);
      Course java = new Course(574, "Java", 5);
      Course javascript = new Course(685, "Javascript", 16);
      Course react = new Course(274, "React", 6);

      // Add courses to the management system
      CourseManagement.addCourse(math);
      CourseManagement.addCourse(physics);
      CourseManagement.addCourse(biology);
      CourseManagement.addCourse(java);
      CourseManagement.addCourse(javascript);
      CourseManagement.addCourse(react);

      // List of students for testing
      Student david = new Student("David", 0);
      Student mary = new Student("Mary", 1);
      Student lilia = new Student("Lilia", 2);
      Student andrey = new Student("Andrey", 3);
      Student felix = new Student("Felix", 4);
      Student george = new Student("George", 5);
      Student diana = new Student("Diana", 6);
      Student nick = new Student("Nick", 7);
      Student miguel = new Student("Miguel", 8);
      Student sara = new Student("Sara", 9);

      // Enroll students in courses
      CourseManagement.enrollStudent(sara, react);
      CourseManagement.enrollStudent(felix, react);
      CourseManagement.enrollStudent(nick, react);
      CourseManagement.enrollStudent(sara, java);
      CourseManagement.enrollStudent(felix, biology);
      CourseManagement.enrollStudent(nick, javascript);
      CourseManagement.enrollStudent(george, java);
      CourseManagement.enrollStudent(david, physics);
      CourseManagement.enrollStudent(nick, java);

      CourseManagement.assignGrade(sara, react, 87.6f);
      CourseManagement.assignGrade(nick, java, 98.7f);

      CourseManagement.addStudent(lilia);
      CourseManagement.addStudent(andrey);
  }
}


/**
 * The student class that store information such as name, id, enrolled courses,
 * and grades in a private instanced variables. This information is encapsulated
 * and is possible to manage instance variables using public methods like setters
 * and getters.
 */
class Student {

  /**
   * The Student class have private instance variables to store student
   * information such as name, ID, and enrolled courses.
  */
  private String name;
  private int id;

  /**
   * The student have private list of enrolledCourses and Map of grades,
   * that corresponds the course.
   */
  private ArrayList<Course> enrolledCourses;
  private Map<Course, Float> grades;

  /**
   * Constructor Student that initialize encapsulated private variables
   * @param name String variable tof student's name
   * @param id integer id number
  */
  Student (String name, int id) {
    this.name = name;
    this.id = id;
    this.enrolledCourses = new ArrayList<>();
    this.grades = new HashMap<>();
  }

  // Getters

  /**
   * Method to get the name
   * @return the name of student
   */
  public String getName() {
    return name;
  }

  /**
   * Public getter method that returns the id of student
   * @return
   */
  public int getId() {
    return id;
  }

  /**
   * Public getter method that returns the list of enrolled students. To change
   * the list dynamically, it uses ArrayList class.
   * @return the collections of course as type ArrayList.
   */
  public ArrayList<Course> getEnrolledCourses() {
    return enrolledCourses;
  }

  /**
   * Returns the enrolled courses and their corresponding grades as a formatted string for display.
   * @return String of course names and grades separated by commas
   */
  public String getFormattedCourses() {
    StringBuilder coursesWithGrades = new StringBuilder();
    for (Course course : enrolledCourses) {
        Float grade = grades.get(course);
        if (grade != null) {
            coursesWithGrades.append(course.getName()).append(" (").append(grade).append("), ");
        } else {
            coursesWithGrades.append(course.getName()).append(" (Not Graded), ");
        }
    }
    if (coursesWithGrades.length() > 0) {
        coursesWithGrades.setLength(coursesWithGrades.length() - 2);
    }
    return coursesWithGrades.toString();
  }


  /**
   * Public void method that returns student's full information: name, id,
   * courses, grades and prints all of this.
   */
  public String studentInfo() {
    return " \n Name: "
    + this.name + "\n ID: "
    + this.id + "\n Enrolled courses: "
    + this.enrolledCourses + "\n Grades: "
    + this.grades;
  }

  /**
   * Public getter method that gets the Map of grades and courses
   * @return Map {course=grade}
   */
  public Map<Course, Float> getGrades() {
    return grades;
  }

  /**
  * Public method prints the student object in readable way. It overrides
  * method toString() from Object class.
  */
  @Override
  public String toString() {
    return this.name;
  }

  // setters

  /**
   * Public setter method that sets the name of student
   * @param name sets name of student
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Public method that enrolles students in courses. It accepts a Course object
   * as a parameter and add the course to the student's enrolled courses. It also
   * validates and prevents double enrolment if student is already in course.
   * @param course Course type parameter where student is going to enroll
   */
  public void addCourse(Course course) {
    if (!enrolledCourses.contains(course)) {
      enrolledCourses.add(course);
      System.out.println("Course " + course + " added.");
    } else {
      System.err.println("Student is already enrolled on this course");
    }
  }

  /**
   * Public method which assigns grades to student. It acceots a Course object and
   * a grade for the student and update the student;s grade for that course.
   * The method also handles the error if student is not enrolled in the course.
   * @param course Course object
   * @param grade float grade value
   */
  public void setGrade(Course course, float grade) {
    if (enrolledCourses.contains(course)) {
      grades.put(course, grade);
      System.out.println("Graded: " + course + ", "+ grade);
    }else {
      System.err.println("\n" + this.name + " is not enrolled in " + course);
      System.err.println("Please, provide correct course.\n");
    }
  }

  /**
   * Public method that prints all courses where student is enrolled
   */
  public void printStudentCourses() {
    System.out.println("Student "+ this.name +
     "enrolled in a next courses: " + this.enrolledCourses);
  }

  /**
   * Public method that prints all grades of students in HashMap format. Where
   * key is course and value grade.
   */
  public void printStudentGrade() {
    System.out.println("Grades " + grades);
  }
}

/**
 * class Course that have private instance variables to store course information
 * such as course code, name, and maximum capacity.
 */
class Course {

  /**
   * Encapsulated variables courseCode, name and capacity.
   */
  private int courseCode;
  private String name;
  private int capacity;

  /**
   * Static public variable to keep track of the total number of
   * enrolled students. For the beginning We assign it as 0.
   */
  public static int totalEnrolledStudents = 0;


  /**
   * Class Course that have private instance variables to store course
   * information such as course code, name, and maximum capacity.
   * @param courseCode integer parameter of course code
   * @param name String parameter of course's name
   * @param capacity integer parameter of maximum capacity
   */
  Course(int courseCode, String name, int capacity) {
    this.courseCode = courseCode;
    this.name = name;
    this.capacity = capacity;
  }

  /**
   * Public getter method of course code
   * @return course code variable of course
   */
  public int getCourseCode() {
    return courseCode;
  }

  /**
   * Public getter method of name
   * @return name of course
   */
  public String getName() {
    return name;
  }

  /**
   * Public getter method of maximum capacity
   * @return gets maximum capacity of course in integer
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * Public static method that retursn total amount of enrolled students
   * @return total number of enrolled students in integer.
   */
  public static int getTotalEnrolled() {
    return totalEnrolledStudents;
  }

  // Setters

  /**
   * Public setter which sets the code of course
   * @param courseCode integer parameter of course's code
   */
  public void setCourseCode(int courseCode) {
     this.courseCode = courseCode;
  }

  /**
   * Public setter which sets the name of course
   * @param name String parameter of course's name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Public setter which sets the capacity of the course
   * @param capacity integer parameter of course's capacity
   */
  public void setCapacity(int number) {
    this.capacity = capacity-number;
  }

  /**
   * Public method prints the course object in readable way. It overrides
   * method toString() from Object class.
  */
  @Override
  public String toString() {
      return this.name;
  }

}

/**
 * The course management class have private static variables to store a list of
 * courses and overall course grades for each student.
 */
class CourseManagement {

  /**
   * private static lists of course in ArrayList and overall grades HashMap format.
   */
  private static ArrayList<Course>courseList =  new ArrayList<>();
  private static Map<Student, Float> overallGrades = new HashMap<>();
  private static ArrayList<Student>studentList = new ArrayList<>();

  /**
   * Public getter to see all list of courses
   */
  public static ArrayList<Course> getCourseList() {
    return courseList;
  }

  public static ArrayList<Student> getStudentList() {
    return studentList;
  }



  /**
   * Public method to get the course by it's name and return Course object
   * @param name String name of course
   * @return Course object
   */
  public static Course getCourseByName(String name) {
    for (Course course : courseList) {
        if (course.getName().equals(name)) {
            System.out.println(course.getName());
            return course;

        }
    }
    System.err.println("Course does not exist");
    return null;
}

public static void addStudent(Student student) {
  studentList.add(student);
}

  /**
   * Public static method that adds new course object into list
   * @param course Course object
   */
  public static void addCourse(Course course) {
    if (course != null) {
      courseList.add(course);
      System.out.println("Course added successfully");
    }else {
      System.out.println("Empty or Incorrect input");
    }
  }

  /**
   * Updates the information of student
   * @param updatedStudent
   */
   public static void updateStudent(Student updatedStudent) {
    for (Student student : studentList) {
        if (student.getId() == updatedStudent.getId()) {
            student.setName(updatedStudent.getName());
            break;
        }
      }
   }

  /**
   * Public static method which accept a Student object and a course object.
   * It enrolls the student in a course and updates the amount of
   * totalEnrolledStudents static variable of Course class.
   * @param student
   * @param course
   */
  public static void enrollStudent(Student student, Course course) {

    if (course.getCapacity() > 0 )  {
        Course.totalEnrolledStudents++;
        student.addCourse(course);
        course.setCapacity(1);
        studentList.add(student);
    }

    System.out.println(student + " enrolled in course: " + course + "Current capacity:" + course.getCapacity() );
  }

  /**
   * Public static method which accept a Student object, a Course object,
   * and a grade. It assignes the grade to student for this course.
   * @param student Student object
   * @param course Course object
   * @param grade float grade
   */
  public static void assignGrade(Student student, Course course, float grade) {
    student.setGrade(course, grade);
  }

  /**
   * Public method that accepts Student object and calculates overall course
   * grade for this student based on the grade assigned to them. Overall is
   * calculated as sum of all grades and divide by their amount.
   * @param student Student object
   * @return overall of student's grade: average in a float.
   */
   public static float calculateOverallGrade(Student student) {
      Map<Course, Float> grades = student.getGrades();
      if (grades.isEmpty()) {
          overallGrades.put(student,0.0f);
          return 0.0f;
      }

      float total = 0.0f;
      for (Float grade : grades.values()) {
          total += grade; // Sum of all grades
      }
      float average = total / grades.size(); // total sum of grades devide to amount of grades
      overallGrades.put(student, average); // adding to Map.
      return average;
  }

}
