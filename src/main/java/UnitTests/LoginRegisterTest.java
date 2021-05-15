package UnitTests;

import controllers.Regex;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

import static controllers.menues.LoginMenu.createNewUser;

public class LoginRegisterTest {
    @Test
    public void userRegisterTest() throws IOException {
        final String output = "src/main/java/UnitTests/RegisterTestOutput.txt";
        final String input = "src/main/java/UnitTests/RegisterTest.txt";
        final String expectedOutput = "src/main/java/UnitTests/RegisterExpected.txt";
        compareTXTFiles(output, input, expectedOutput);
    }
    @Test
    public void userLoginTest() throws IOException {
        final String output = "src/main/java/UnitTests/LoginTestOutput.txt";
        final String input = "src/main/java/UnitTests/LoginTest.txt";
        final String expectedOutput = "src/main/java/UnitTests/LoginExpected.txt";
        compareTXTFiles(output, input, expectedOutput);
    }
    private void compareTXTFiles(String output, String input, String expectedOutput) throws IOException {
        PrintStream fileStream = new PrintStream(output);
        System.setOut(fileStream);
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                createNewUser(Regex.getMatcher(line,Regex.userCreate),false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> file1 = Files.readAllLines(Paths.get(output));
        List<String> file2 = Files.readAllLines(Paths.get(expectedOutput));

        assertEquals(file1.size(), file2.size());

        for(int i = 0; i < file1.size(); i++) {
            assertEquals(file1.get(i), file2.get(i));
        }
    }
}
