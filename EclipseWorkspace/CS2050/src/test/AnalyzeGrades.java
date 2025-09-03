/**
 * 
 */
package test;

import com.sun.java_cup.internal.runtime.Scanner;

/**
 * 
 */
public class AnalyzeGrades {
    public static void main(String[] args) {
         java.util.Scanner input = new java.util.Scanner(System.in);
         System.out.print("Enter the number of items: ");

         double[] grades = {10, 7, 8}; // array of  grades
         double sumGrades = 0; 

         System.out.print("Enter the numbers: ");
         for (int i = 0; i< grades.length; i++) 
         {
             sumGrades += grades[i];
         }
         double average = sumGrades / grades.length;

         int countAboveAverage = 0; // The numbers of elements above average
         for (int i = 0; i < grades.length; i++)
         {
              if (grades[i] > average) // Count if number[i] > average
              {
                   countAboveAverage++;
              }
         }
         System.out.println("Average is " + average);
         System.out.println("Number of elements above the average is "
             + countAboveAverage);
   }// end of main method
    
    
}// end of driver class
