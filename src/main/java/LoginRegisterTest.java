import controllers.Regex;
import controllers.menues.LoginMenu;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static controllers.menues.LoginMenu.createNewUser;

public class LoginRegisterTest {
    @Test
    public void userRegisterTest() throws IOException {
        final String output = "src/main/java/UnitTests/RegisterAndLogin/RegisterTestOutput.txt";
        final String input = "src/main/java/UnitTests/RegisterAndLogin/RegisterTest.txt";
        final String expectedOutput = "src/main/java/UnitTests/RegisterAndLogin/RegisterExpected.txt";
        writeOutputsToTXT(output);
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                createNewUser(Regex.getMatcher(line,Regex.userCreate),false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        compareTXT(output, expectedOutput);
    }

    public void writeOutputsToTXT(String output) throws FileNotFoundException {
        PrintStream fileStream = new PrintStream(output);
        System.setOut(fileStream);
    }

    public static void compareTXT(String output, String expectedOutput) throws IOException {
        List<String> file1 = Files.readAllLines(Paths.get(output));
        List<String> file2 = Files.readAllLines(Paths.get(expectedOutput));

        assertEquals(file1.size(), file2.size());

        for(int i = 0; i < file1.size(); i++) {
            assertEquals(file1.get(i), file2.get(i));
        }
    }

    @Test
    public void userLoginTest() throws IOException {
        final String output = "src/main/java/UnitTests/RegisterAndLogin/LoginTestOutput.txt";
        final String input = "src/main/java/UnitTests/RegisterAndLogin/LoginTest.txt";
        final String expectedOutput = "src/main/java/UnitTests/RegisterAndLogin/LoginExpected.txt";
        writeOutputsToTXT(output);
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                LoginMenu.loginUser(line,false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        compareTXT(output, expectedOutput);
    }
}
