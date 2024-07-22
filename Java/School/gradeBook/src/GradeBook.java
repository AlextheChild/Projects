import java.util.*;

public class GradeBook{
    Scanner in = new Scanner(System.in);
    ArrayList<Student> Students = new ArrayList<Student>();
    int numStudents;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<Integer> yogs = new ArrayList<Integer>();

    //create a new grade book
    public GradeBook(){
        System.out.println("How many students are there?");
        numStudents = in.nextInt();
        //name
        System.out.println("What are the names of the students?");
        for(int i = 0; i < numStudents; i++){
            names.add(in.next());
        }
        //yog
        System.out.println("What are the yogs of the students?");
        for(int i = 0; i < numStudents; i++){
            yogs.add(in.nextInt());
        }
        //create the actual student with no courses
        for(int i = 0; i < numStudents; i++){
            Student q = new Student(names.get(i), yogs.get(i));
            Students.add(q);
        }

        //adds courses with null for each grades array
        System.out.println("Add courses to each student");
        for(int i = 0; i < numStudents; i++){
            System.out.println("Adding to " + names.get(i) + ":");
            System.out.println("How many courses does " + names.get(i) + " have?");
            for(int q = 0; q<in.nextInt(); q++){
                //course name
                System.out.println("What is the course called?");
                String courseName = in.next();
                //course block
                System.out.println("What block is the course?");
                char block = in.next().charAt(0);
                //add courses with no grades
                Students.get(q).StudentCourses.add(new StudentCourse(courseName, block));
            }
        }
    }

    //create an individual new student with everything and a null grades array
    public void addStudent(){
        //adding student information
        System.out.println("What is the student's name and yog?");
        String name = in.next();
        int yog = in.nextInt();
        Student q = new Student(name, yog);
        //courses
        System.out.println("How many courses does" + q.name + "have?");
        int n = in.nextInt();
        for(int w = 0; w<n; w++){
                //course name
                System.out.println("What is the course called?");
                String course = in.next();
                //course block
                System.out.println("What block is the course?");
                char block = in.next().charAt(0);
                q.StudentCourses.add(new StudentCourse(course, block));
        }
    }

    //add grades to the specific course of a specific student
    public void addGrades(){
        int studentIndex = 0;
        int courseIndex = 0;

        //who to add grades to
        System.out.println("Who do you want to add grades to?");
        String name = in.next();
        //figure out index of student who has the name
        for(int i = 0; i<numStudents; i++){
            if(Students.get(i).name.equals(name)){
                studentIndex = i;
            }
        }
        //which course to add to
        System.out.println("Which course do you want to add grades to?");
        //show the user what courses the selected student has
        System.out.println(Students.get(studentIndex).name + "'s courses: ");
        for(int i = 0; i<Students.get(studentIndex).StudentCourses.size(); i++){
            System.out.println(Students.get(studentIndex).StudentCourses.get(i));
        }
        String courseName = in.next();
        //figure out index of course
        for(int i = 0; i<Students.size(); i++){
            if(Students.get(studentIndex).StudentCourses.get(i).name.equals(courseName)){
                courseIndex = i;
            }
        }
        //add grades to the course
        System.out.println("How many grades does " + Students.get(studentIndex).name + "have in " + Students.get(studentIndex).StudentCourses.get(courseIndex).name +"?");
        int numGrades = in.nextInt();
        System.out.println("List grades:");
        for(int i = 0; i<numGrades; i++){
            Students.get(studentIndex).StudentCourses.get(i).grades.add(in.nextInt());
        }
    }
    //average grades
    public int averageGrades(ArrayList<Integer> grades){
        int sum = 0;
        int average = 0;
        for(int i = 0; i<grades.size(); i++){
            sum += grades.get(i);
        }
        average = sum/grades.size();
        return average;

    }

    //old grade averaging code that only works for one specific student

    // public int averageGrades(){
    //     int sum = 0;
    //     int average = 0;
    //     int studentIndex = 0;
    //     int courseIndex = 0;

    //     //whose grades to average
    //     System.out.println("Whose grades do you want the average of?");
    //     String name = in.next();
    //     //figure out index of student who has the name
    //     
    //     for(int i = 0; i<Students.size(); i++){
    //         if(Students.get(i).name.equals(name)){
    //             studentIndex = i;
    //         }
    //     }
    //     //which course's grades to average
    //     System.out.println("Which course's grades do you want the average of?");
    //     System.out.println(Students.get(studentIndex).name + "'s courses: ");
    //     for(int i = 0; i<Students.get(studentIndex).StudentCourses.size(); i++){
    //         System.out.println(Students.get(studentIndex).StudentCourses.get(i));
    //     }
    //     String courseName = in.next();

    //     //average the grades
    //     for(int i = 0; i<Students.get(studentIndex).StudentCourses.get(courseIndex).grades.size(); i++){
    //         sum += Students.get(studentIndex).StudentCourses.get(courseIndex).grades.get(i);
    //     }
    //     average = sum/Students.get(studentIndex).StudentCourses.get(courseIndex).grades.size();
    //     return average;
    // }
}