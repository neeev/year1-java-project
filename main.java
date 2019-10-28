
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


/*

COM 102 ASSIGNMENT 2 - STUDENT MODULE MARKS SYSTEM

            COMPLETED ON 07/04/2019

Authors: Niamh Lindsay & Dillan McIlvenna
          (B00677159)      (B00751106)

 */
public class MarkManagement {
   // Sets up 'quit' variable, used through out code. By default the
   // value is false, anywhere it is true the program will end.
   private boolean quit_;
   // Stores the hex representation of the hash codes of each 'Student' object.
   private List<Student> records_ = new ArrayList<Student>();

// initiates the core methods
   public static void main(String[] args)
      {
      // Instantiates program, calls it session.
      MarkManagement session = new MarkManagement();

      session.header();
      session.readFile();
      session.start();
      }

   // Text header for program for viewability purposes, only called on once.
   private void header()
      {
      System.out.println("||=============================================||");
      System.out.println("||                                             ||");
      System.out.println("||           Assignment 2 for COM102           ||");
      System.out.println("||                                             ||");
      System.out.println("||    Authors: Dillan McIlvenna (B00751106)    ||");
      System.out.println("||         & Niamh Lindsay (B00677259)         ||");
      System.out.println("||                                             ||");
      System.out.println("||     Submission Deadline: 8th April 2019     ||");
      System.out.println("||                                             ||");
      System.out.println("||=============================================||");
      System.out.println("||                                             ||");
      }

   // Every method ends with this for the sake of readability purposes.
   // Stored in its own method to reduce redundancy.
   private void footer()
      {
      System.out.println("||=============================================||");
      System.out.println("||                                             ||");
      }

// Responsible for reading from the text file provided and for storing...
// those records in the program.   
   private void readFile()
      {
      // Program will halt if it can't read the file.    
      try
         {
         // Allows user to enter name of the file they want to read from.
         // Instantiates scanner to do so.    
         String fileName = "data.txt";
         File file = new File(fileName);

         Scanner reader = new Scanner(file);

         // Scanner reads through file line by line until it discovers...
         // the end of the file.
         while (reader.hasNext())
            {
            // Contains entire line read by scanner.
            String record = reader.next();

            // Separates line into bCode and Mark values.
            // Mark is converted into an integer here.
            String bCode = record.substring(0, 6);
            int mark = Integer.parseInt(record.substring(7));

            // Creates a new instance of the object student.
            // Passes our separated value into it.
            Student student = new Student(bCode, mark);

            // Hexadecimal reference of the objects is added to the list.
            records_.add(student);
            }
         } catch (FileNotFoundException e)
         {
         System.out.println("||  File not found.                            ||");
         }
      }

// Provides text output for the menu for readability/user purposes.
   private void displayMenu()
      {
      System.out.println("||=============================================||");
      System.out.println("||                    MENU                     ||");
      System.out.println("||=============================================||");
      System.out.println("||                                             ||");
      System.out.println("||           1) View all records.              ||");
      System.out.println("||           2) Add new record.                ||");
      System.out.println("||           3) Edit existing record.          ||");
      System.out.println("||           4) Delete existing record.        ||");
      System.out.println("||           5) View file statistics.          ||");
      System.out.println("||           9) Save & Quit.                   ||");
      System.out.println("||           0) Display menu.                  ||");
      System.out.println("||                                             ||");
      System.out.println("||=============================================||");
      System.out.println("||                                             ||");
      }

// This handles user menu input.
   private void start()
      {
      displayMenu();

      // Once quit is set to true, program terminates.
      while (quit_ != true)
         {
         // Ensures user input is an integer and a valid choice from the menu.
         try
            {
            try
               {
               System.out.println("||  Enter an option from the menu              ||");
               System.out.println("||  (enter '0' to view the menu)               ||");
               System.out.println("||                                             ||");

               Scanner scan = new Scanner(System.in);
               System.out.print("||  >>> ");

               int choice = scan.nextInt();
               if ((choice >= 0 && choice < 6) || (choice == 9))
                  {
                  menu(choice);
                  } else
                  {
                  throw new MenuException();
                  }

               } catch (MenuException e)
               {
               System.err.println(e.getMessage());
               }
            } catch (InputMismatchException e)
            {
            System.err.println("||  Whole numbers only please.                 ||");
            }
         }
      }

   // Menu Switchboard. Links to different methods.
   private void menu(int choice)
      {
      switch (choice)
         {
         case 0:
            displayMenu();
            break;
         case 1:
            viewAll();
            break;
         case 2:
            addNew();
            break;
         case 3:
            edit();
            break;
         case 4:
            delete();
            break;
         case 5:
            viewStatistics();
            break;
         case 9:
            saveAndQuit();
            break;
         }
      }

// Method responsible for displaying all records.
   private void viewAll()
      {
      System.out.println("||=============================================||");
      System.out.println("||            1.ALL STUDENT RECORDS            ||");
      System.out.println("||=============================================||");
      System.out.println("||              Rcd.| B-Code | Mark            ||");
      System.out.println("||             ---------------------           ||");

      int i = 0;
      while (i < Student.getTotal())
         {
         // Purpose of if statement is purely for text display formatting.
         // If a record number is less than 10, it reads it as '001'.
         // If a record is less than 100, it displays it as '010' etc.
         if (i < 9)
            {
            System.out.print("||              00");
            } else if (i > 8 && i < 99)
            {
            System.out.print("||              0");
            } else if (i > 99)
            {
            System.out.print("||              ");
            }

         // Every iteration of while loop prints out current number of the...
         // record, the bCode and the mark.
         System.out.println((i + 1) + " | " + records_.get(i).getBcode()
               + " | " + records_.get(i).getMark() + "              ||");

         i++;
         }

      footer();
      }

// Method responsible for adding new records.
   private void addNew()
      {

      System.out.println("||=============================================||");
      System.out.println("||             2. ADD NEW RECORD               ||");
      System.out.println("||=============================================||");

      Scanner scan = new Scanner(System.in);
      boolean success = false;

      // This while loop ensures that user is kept in the adding new record
      // segment until the program has completed what it's trying to do.
      while (!success)
         {
         System.out.print("||  B-Code: ");
         String bCode = scan.next().toUpperCase();

         // Checks for duplicate B-Code and denies user the ability to enter
         // in duplicate values.
         boolean duplicate = false;
         int i;

         for (i = 0; i < Student.getTotal(); i++)
            {
            if (duplicate = bCode.equals(records_.get(i).getBcode()))
               {
               break;
               }
            }

         // Ensures that B-Code is 6 characters long and that Mark is...
         // an integer in the range of 0-100.
         try
            {
            if (!duplicate)
               {
               if (bCode.length() == 6)
                  {
                  System.out.print("||  Mark: ");
                  int mark = scan.nextInt();

                  if (mark >= 0 && mark <= 100)
                     {
                     Student student = new Student(bCode, mark);
                     records_.add(student);
                     success = true;

                     System.out.println("||                                             ||");
                     System.out.println("||  Record "
                           + records_.get(Student.getTotal() - 1).getBcode()
                           + " added successfully.          ||");
                     } else
                     {
                     throw new RangeException();
                     }
                  } else
                  {
                  throw new LengthException();
                  }
               } else
               {
               throw new DuplicateException();
               }
            } catch (RangeException | LengthException | DuplicateException e)
            {
            System.err.println(e.getMessage());
            }
         }

      footer();
      }

// Method responsible for editing records.
   private void edit()
      {

      System.out.println("||=============================================||");
      System.out.println("||           3. EDIT EXISTNG RECORD            ||");
      System.out.println("||=============================================||");
      System.out.println("||  Search for the record by B-Code.           ||");

      boolean success = false;

      while (!success)
         {
         Scanner scan = new Scanner(System.in);

         System.out.print("||  B-Code: ");
         String bCode = scan.next().toUpperCase();

         // Ensures that user entry is 6 characters long before searching...
         // the records list for a matching value.
         try
            {
            if (bCode.length() == 6)
               {
               boolean exists = false;
               int i;

               for (i = 0; i < Student.getTotal(); i++)
                  {
                  if (exists = bCode.equals(records_.get(i).getBcode()))
                     {
                     System.out.println("||                                             ||");
                     System.out.println("||  Record "
                           + records_.get(i).getBcode()
                           + " found!                       ||");
                     System.out.println("||  Current Mark: "
                           + records_.get(i).getMark()
                           + "                           ||");

                     break;
                     }
                  }

               // Only allows mark value to be edited if B-Code is found to...
               // already exist. Ensures mark value is an integer between...
               // 0 and 100.
               if (exists)
                  {
                  System.out.println("||                                             ||");
                  System.out.print("||  New Mark: ");
                  int mark = scan.nextInt();

                  if (mark >= 0 && mark <= 100)
                     {
                     records_.get(Student.getTotal() - 1).setMark(mark);

                     success = true;

                     System.out.println("||                                             ||");
                     System.out.println("||  Record "
                           + records_.get(Student.getTotal() - 1).getBcode()
                           + " edited successfuly.          ||");

                     } else
                     {
                     throw new RangeException();
                     }
                  } else
                  {
                  throw new NotFoundException();
                  }
               } else
               {
               throw new LengthException();
               }

            } catch (RangeException | LengthException | NotFoundException e)
            {
            System.err.println(e.getMessage());
            }
         }

      footer();
      }

// Method responsible for deleting record.
   private void delete()
      {

      System.out.println("||=============================================||");
      System.out.println("||               4. DELETE RECORD              ||");
      System.out.println("||=============================================||");
      System.out.println("||  Search for the record by B-Code.           ||");

      boolean bCheck = false;

      while (bCheck != true)
         {
         Scanner scan = new Scanner(System.in);
         System.out.print("||  B-Code: ");
         String bCode = scan.next().toUpperCase();

         // Performs a search for the B-Code entered and once found, provides...
         // user with the option to delete the record from the list.
         // Also controls user inputs.
         try
            {
            if (bCode.length() == 6)
               {
               boolean exists = false;
               int i;

               for (i = 0; i < Student.getTotal(); i++)
                  {
                  if (exists = bCode.equals(records_.get(i).getBcode()))
                     {
                     System.out.println("||                                             ||");
                     System.out.println("||  Record "
                           + records_.get(i).getBcode()
                           + " found!                       ||");
                     System.out.println("||  Current Mark: "
                           + records_.get(i).getMark()
                           + "                           ||");

                     break;
                     }
                  }

               if (exists)
                  {
                  System.out.println("||                                             ||");
                  System.out.print("||  Delete this record? [Y/N]: ");

                  scan = new Scanner(System.in);
                  char response = scan.next().charAt(0);

                  if (response == 'Y' || response == 'y')
                     {
                     records_.remove(i);
                     Student.deleteStudent();
                     bCheck = true;

                     System.out.println("||                                             ||");
                     System.out.println("||  Record "
                           + bCode
                           + " successfully deleted.        ||");
                     } else if (response == 'N' || response == 'n')
                     {
                     bCheck = true;
                     } else
                     {
                     throw new InvalidResponseException();
                     }
                  } else
                  {
                  throw new NotFoundException();
                  }
               } else
               {
               throw new LengthException();
               }

            } catch (LengthException | NotFoundException | InvalidResponseException e)
            {
            System.err.println(e.getMessage());
            }

         // Provides user the option to return to the menu if they change...
         // their mind about deleting or if they wish to continue deleting...
         // records after a single delete has already occurred.
         try
            {
            System.out.println("||                                             ||");
            System.out.print("||  Search another B-Code? [Y/N]: ");

            scan = new Scanner(System.in);
            char response = scan.next().charAt(0);

            if (response == 'Y' || response == 'y')
               {
               } else if (response == 'N' || response == 'n')
               {
               bCheck = true;
               } else
               {
               throw new InvalidResponseException();
               }

            } catch (InvalidResponseException e)
            {
            System.err.println(e.getMessage());
            }
         }

      footer();
      }

// Method responsible for generating statistics about the student record...
// 'marks' value. All statistics are calculated in separate methods.   
   private void viewStatistics()
      {
      System.out.println("||=============================================||");
      System.out.println("||             5. FILE STATISTICS              ||");
      System.out.println("||=============================================||");

      System.out.println("||  There is a total of "
            + Student.getTotal() + " student records in  ||"
            + "\n||  the system currently.                      ||");

      System.out.println("||                                             ||");
      System.out.println("||                MODULE MARKS                 ||");
      System.out.println("||                                             ||");

      System.out.println("||  Average Mark: " + getAverage()
            + "                         ||");

      System.out.println("||  Lowest Mark: " + getLowest()
            + "                            ||");

      System.out.println("||  Highest Mark: " + getHighest()
            + "                           ||");

      footer();
      }

// Method responsible for generating average. Achieves this by...
// procedurally adding all marks together, then divides by total.
   private double getAverage()
      {
      int i;
      int cMark = 0;

      for (i = 0; i < Student.getTotal(); i++)
         {
         cMark += records_.get(i).getMark();
         }

      return (cMark / Student.getTotal());
      }

/* Method responsible for obtaining lowest mark. Does this by setting a...
variable 'lMark' to first instance of a mark, then procedurally compares...
all other marks against variable, replacing it if it is lower. */
   private int getLowest()
      {
      int i;
      int lMark = records_.get(0).getMark();

      for (i = 1; i < Student.getTotal(); i++)
         {
         if (records_.get(i).getMark() < lMark)
            {
            lMark = records_.get(i).getMark();
            }
         }

      return lMark;
      }

/* Method responsible for obtaining highest mark. Does this by setting a...
variable 'hMark' to first instance of a mark, then procedurally compares...
all other marks against variable, replacing it if it is higher. */
   private int getHighest()
      {
      int i;
      int hMark = records_.get(0).getMark();

      for (i = 1; i < Student.getTotal(); i++)
         {
         if (records_.get(i).getMark() > hMark)
            {
            hMark = records_.get(i).getMark();
            }
         }

      return hMark;
      }

   // Method responsible for committing changes to text file before exiting
   // the program. Provides option to not save if user so desires.
   private void saveAndQuit()
      {
      System.out.println("||=============================================||");
      System.out.println("||               9. SAVE & QUIT                ||");
      System.out.println("||=============================================||");
      System.out.print("||  Are you sure you want to quit? [Y/N]: ");

      Scanner scan = new Scanner(System.in);
      char responseQuit = scan.next().charAt(0);

      try
         {
         if (responseQuit == 'Y' || responseQuit == 'y')
            {
            System.out.println("||                                             ||");
            System.out.println("||  You are about to close the program.        ||"
                  + "\n||  Any unsaved changes will be lost.          ||");

            System.out.print("||  Do you want to save? [Y/N]: ");
            char responseSave = scan.next().charAt(0);
            System.out.println("||                                             ||");

            if (responseSave == 'Y' || responseSave == 'y')
               {
               PrintWriter recordOutput = null;
               recordOutput = new PrintWriter("MarksFileForDemo.txt");
               int i;

               for (i = 0; i < Student.getTotal(); i++)
                  {
                  produceReport(recordOutput, i);
                  }

               recordOutput.close();

               System.out.println("||                                             ||");
               System.out.println("||  Save successful!                           ||");

               } else if (responseSave == 'N' || responseSave == 'n')
               {
               System.out.println("||                                             ||");
               System.out.println("||  Thank you for using the program!           ||");
               System.out.println("||  Quitting . . .                             ||");
               System.out.println("||=============================================||");
               quit_ = true;
               } else
               {
               throw new InvalidResponseException();
               }

            } else if (responseQuit == 'N' || responseQuit == 'n')
            {
            start();
            } else
            {
            throw new InvalidResponseException();
            }

         } catch (IOException | InvalidResponseException e)
         {
         System.err.println(e.getMessage());
         }
      }

   // saves changes made to the file
   private void produceReport(PrintWriter recordOutput, int nRecord)
      {
      String record = records_.get(nRecord).getBcode()
            + ","
            + records_.get(nRecord).getMark();

      recordOutput.println(record);
      }

   /* Below are all the classes responsible for catching exceptions.
   Each one provides user feedback to the error made, allowing them to
   avoid making the same error twice.
   */
   private static class RangeException extends Exception {

      public RangeException()
         {
         super("||  Mark should be in the range 0 - 100.       ||");
         }

      public String getMessage()
         {
         return super.getMessage();
         }
   }

   private static class LengthException extends Exception {

      public LengthException()
         {
         super("||  B-Code must be 6 characters long.          ||");
         }

      public String getMessage()
         {
         return super.getMessage();
         }
   }

   private static class DuplicateException extends Exception {

      public DuplicateException()
         {
         super("||  A record already exists with that B-Code.  ||");
         }

      public String getMessage()
         {
         return super.getMessage();
         }
   }

   private static class NotFoundException extends Exception {

      public NotFoundException()
         {
         super("||  Record not found.                          ||");
         }

      public String getMessage()
         {
         return super.getMessage();
         }
   }

   private static class InvalidResponseException extends Exception {

      public InvalidResponseException()
         {
         super("||  Please enter Y or N.                       ||");
         }

      public String getMessage()
         {
         return super.getMessage();
         }
   }

   private static class MenuException extends Exception {

      public MenuException()
         {
         super("||  Please enter a number from the menu.       ||");
         }

      public String getMessage()
         {
         return super.getMessage();
         }
   }
}
