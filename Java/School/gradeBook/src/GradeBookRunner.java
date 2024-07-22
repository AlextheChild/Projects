import java.util.*;

public class GradeBookRunner {
    GradeBook gradeBook;
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("new");
            if(in.next().equals("new")){
                System.out.println("What would you like to do? (0 to create gradebook, 1 to add a new student, 2 to add grades to a student, 3 to view all students and their grades)");
                int choice = in.nextInt();
                
                //new gradebook
                if(choice == 0){        
                    gradeBook = new GradeBook();
                }

                //add student
                else if(choice == 1){
                    //if they didn't make a gradebook yet
                    if(gradeBook == null) System.out.println("Please make a gradebook first.");

                    gradeBook.addStudent();
                }

                //add grades
                else if(choice == 2){
                    //if they didn't make a gradebook yet
                    if(gradeBook == null) System.out.println("Please make a gradebook first.");

                    gradeBook.addGrades();
                }

                //show each student and their grades for each course
                else if(choice == 3 && grades!=null){
                    //if they didn't make a gradebook yet
                    if(gradeBook == null) System.out.println("Please make a gradebook first.");

                    for(int i = 0; i < gradeBook.Students.size(); i++){
                    //Student name:
                        System.out.println(gradeBook.Students.get(i).name + ":");
                        for(int q = 0; q<gradeBook.Students.get(i).StudentCourses.size(); q++){
                            //Course name:
                            System.out.println(gradeBook.Students.get(i).StudentCourses.get(q).name + ":");
                            //grades
                            for(int w = 0; w<gradeBook.Students.get(i).StudentCourses.get(q).grades.size(); w++){
                                System.out.println(gradeBook.Students.get(i).StudentCourses.get(q).grades.get(w));
                            }
                            //average
                            System.out.println("Average: " + gradeBook.averageGrades(gradeBook.Students.get(i).StudentCourses.get(q).grades));
                        }
                    }
                }
                //tell the user that they're bad
                else{
                    System.out.println("Please learn to count effectively and correctly. ");
                }
            }
            else{
                System.out.println("hi");
            }
        }
    }
}