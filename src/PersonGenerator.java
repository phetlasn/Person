import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonGenerator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        ArrayList<Person> persons = new ArrayList<>();

        String number = "";
        String fName = "";
        String lName = "";
        String title = "";
        String record = "";
        String fileName = "";
        int year = 0;
        Person per;

        boolean done = false;

        do {
            number = SafeInput.getNonZeroLenString(in, "Please enter your person's ID [6 DIGITS]");
            fName = SafeInput.getNonZeroLenString(in, "Please enter your person's First Name");
            lName = SafeInput.getNonZeroLenString(in, "Please enter your person's Last Name");
            title = SafeInput.getNonZeroLenString(in, "Please enter your person's Title");
            year = SafeInput.getRangedInt(in, "Enter your person's Year of Birth", 1940, 2000);

            per = new Person(number, fName, lName, title, year);
            persons.add(per);

            done = SafeInput.getYNConfirm(in, "Are there any more records you would like to add?");
        }while (done);

        fileName = SafeInput.getNonZeroLenString(in, "Name your file please");
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "//src//" + fileName + ".txt");

        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            String p;

            for (Person rec: persons){
                p = rec.toCSVDataRecord();
                System.out.println(p);
                writer.write(p, 0, p.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("Data been successfully written");
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }
}
