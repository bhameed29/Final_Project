import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Teacher {
    String studentName;
    boolean isPresent;
    int totalPresents;
    int totalAbsents;
    String reason;

    Teacher() {
        List<String> note=new ArrayList<String>();
        note.add("Attandance");
        note.add("Management");
        note.add("System");
        note.add("Welcome");
        note.forEach(
                (n)->System.out.println(n)
        );
        totalAbsents = 0;
        totalPresents = 0;
        isPresent = false;
    }

    class TotalStudent {
        int totalstuds = 10;
    }

    void addStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Student Name: ");
        studentName = sc.nextLine();
        totalPresents = 0;
        totalAbsents = 0;
        TotalStudent ts = new TotalStudent();
        ts.totalstuds++;
        System.out.println("Total Students now in class: " + ts.totalstuds);
    }

    void showStudentData() {
        try {
            Scanner na = new Scanner(System.in);
            System.out.println("Enter Student Name: ");
            String sname = na.nextLine();
            boolean isin = true;
            File my_file = new File("C:\\Users\\basee\\Documents\\NEIU\\Masters\\Spring 2022\\CS420\\Final Project\\Baseer\\src\\studentsData.txt");
            Scanner file = new Scanner(my_file);
            while (file.hasNextLine()) {
                String data = file.nextLine();
                String[] datasplits = data.split(" "); // split line on string separated by " "
                if (datasplits[0].equals(sname)) {
                    System.out.println("Name-Total Presents-Total Absents-Reason of Absent");
                    System.out.println(data);
                    return;
                }
            }
            if (isin) {
                System.out.println("Student not in database");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    void showStudentsData() {
        try {

            File my_file = new File("C:\\Users\\basee\\Documents\\NEIU\\Masters\\Spring 2022\\CS420\\Final Project\\Baseer\\src\\studentsData.txt");
            Scanner file = new Scanner(my_file);
            while (file.hasNextLine()) {
                String data = file.nextLine();
                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    boolean markAttendance() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Is he present(1) or absent(0): ");
        int status = sc.nextInt();
        if (status < 0 && status > 1) {
            do {
                System.out.println("Wrong Status, Please select the right status\nIs he present(1) or absent(0): ");
                status = sc.nextInt();
            } while (status == 0 || status == 1);
        }
        if (status == 0) {
            totalAbsents++;
            return true;
        }
        totalPresents++;
        return false;
    }

    void reasons() {
        System.out.println("Please Enter your reason for you absent: ");
        Scanner sc = new Scanner(System.in);
        this.reason = sc.nextLine();
    }

    void addDataInFile(BufferedWriter fileWrite) throws IOException {
        try {
            String toWrite = "\n" + studentName + " " + totalPresents + " " + totalAbsents + " " + reason;
            fileWrite.write(toWrite);
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }

    void updateDataInFile() {
        try {
            String fpath = "C:\\Users\\basee\\Documents\\NEIU\\Masters\\Spring 2022\\CS420\\Final Project\\Baseer\\src\\studentsData.txt";
            File inFile = new File(fpath);
            if (!inFile.isFile()) {
                System.out.println("File not found");
                return;
            }
            String line = null, data = null, rs = null;
            int pr = 0, ab = 0;
            Scanner na = new Scanner(System.in);
            System.out.println("Enter Student Name: ");
            String sname = na.nextLine();
            boolean isin = true;
            File my_file = new File(fpath);
            Scanner file = new Scanner(my_file);
            while (file.hasNextLine()) {
                data = file.nextLine();
                String[] datasplits = data.split(" "); // split line on string separated by " "
                if (datasplits[0].equals(sname)) {
                    pr = parseInt(datasplits[1]);
                    ab = parseInt(datasplits[2]);
                    rs = datasplits[3];
                    isin = false;
                }
            }
            if (isin) {
                System.out.println("Student not in database");
                return;
            }
            System.out.print("What do you want to increase Presents(1) or Absents(0): ");
            int choice = na.nextInt();
            if (choice == 1) {
                line = sname + " " + Integer.toString(++pr) + " " + Integer.toString(ab) + rs;
            } else
                line = sname + " " + Integer.toString(pr) + " " + Integer.toString(++ab) + rs;
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(fpath));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals(data)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, FileNotFoundException {
        File filetoWrite = new File("C:\\Users\\basee\\Documents\\NEIU\\Masters\\Spring 2022\\CS420\\Final Project\\Baseer\\src\\studentsData.txt");
        FileWriter fr = new FileWriter(filetoWrite, true);
        BufferedWriter br = new BufferedWriter(fr);
        Teacher t = new Teacher();
        Scanner sc = new Scanner(System.in);
        int ch = 1;
        String sname = "";
        while (ch != 0) {
            System.out.println("1. Add new Student Data");
            System.out.println("2. Show a Student Data");
            System.out.println("3. Show All Students Data");
            System.out.println("4. Update Attendace of a Student");
            System.out.println("0. Exit Program");
            System.out.print("Enter your choice: ");
            ch = sc.nextInt();
            switch (ch) {
                case 0:
                    System.out.println("PROGRAM ENDED");
                    break;
                case 1:
                    t.addStudent();
                    if (t.markAttendance()) {
                        t.reasons();
                        t.addDataInFile(br);
                    } else
                        t.addDataInFile(br);
                    br.close();
                    fr.close();
                    break;
                case 2:
                    t.showStudentData();
                    break;
                case 3:
                    t.showStudentsData();
                    break;
                case 4:
                    t.updateDataInFile();
            }
        }
    }
}
