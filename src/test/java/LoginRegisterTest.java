import controllers.Regex;
import controllers.menues.LoginMenu;
import models.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

import static controllers.menues.LoginMenu.createNewUser;

public class LoginRegisterTest implements BackupDatabase{
    @Test
    public void userRegisterTest() throws IOException {
        moveDatabase();
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
        backDatabase();
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
        moveDatabase();
        new User("pouria1","123","Empire1");
        new User("pouria2","123","Empire2");
        new User("pouria3","123","Empire3");
        new User("pouria4","123","Empire4");
        new User("pouria5","123","Empire5");
        new User("pouria6","123","Empire6");
        new User("pouria7","123","Empire7");
        String userInputs = "user login -u pouria1 -p 123" + System.getProperty("line.separator")
                + "user login -u pouria1 -p 123" + System.getProperty("line.separator")
                + "user login -p 123 -u pouria2" + System.getProperty("line.separator")
                + "user login --username pouria3 --password 123" + System.getProperty("line.separator")
                + "user login --password 123 -u pouria4" + System.getProperty("line.separator")
                + "user login --username pouria5 -p 123" + System.getProperty("line.separator")
                + "user login -u pouria6 --password 123" + System.getProperty("line.separator")
                + "user login -p 123 -u pouria7" + System.getProperty("line.separator");
        new Regex();
        System.setIn(new ByteArrayInputStream(userInputs.getBytes()));
        Assert.assertTrue(Objects.requireNonNull(User.getUserByUsername("pouria1")).isLoggedIn());
        Assert.assertTrue(Objects.requireNonNull(User.getUserByUsername("pouria2")).isLoggedIn());
        Assert.assertTrue(Objects.requireNonNull(User.getUserByUsername("pouria3")).isLoggedIn());
        Assert.assertTrue(Objects.requireNonNull(User.getUserByUsername("pouria4")).isLoggedIn());
        Assert.assertTrue(Objects.requireNonNull(User.getUserByUsername("pouria5")).isLoggedIn());
        Assert.assertTrue(Objects.requireNonNull(User.getUserByUsername("pouria6")).isLoggedIn());
        Assert.assertTrue(Objects.requireNonNull(User.getUserByUsername("pouria7")).isLoggedIn());
        backDatabase();
    }
}
