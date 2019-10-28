/*

COM 102 ASSIGNMENT 2 - STUDENT MODULE MARKS SYSTEM

            COMPLETED ON 07/04/2019

Authors: Niamh Lindsay & Dillan McIlvenna
          (B00677159)      (B00751106)

 */

// Responsible for storing Student details.
public class Student {

   // instantiates variables.
   private String bCode;
   private int mark;
   private static int nRecords = 0;

   // Constructor.
   public Student(String inBcode, int inMark)
      {
      this.bCode = inBcode;
      this.mark = inMark;
      nRecords++;
      }

   // Subtracts 1 from total number of student records when called upon.
   public static void deleteStudent()
      {
      nRecords -= 1;
      }

   // Getters and setters.
   public void setBcode(String inBcode)
      {

      this.bCode = inBcode;
      }

   public String getBcode()
      {
      return this.bCode;
      }

   public void setMark(int inMark)
      {
      this.mark = inMark;
      }

   public int getMark()
      {
      return this.mark;
      }

   // Provides total number of student records when called upon.
   public static int getTotal()
      {
      return Student.nRecords;
      }
}