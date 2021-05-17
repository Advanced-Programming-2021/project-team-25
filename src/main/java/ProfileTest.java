
import controllers.Regex;
import controllers.menues.ProfileMenu;
import models.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;

import static controllers.menues.LoginMenu.createNewUser;

public class ProfileTest {
    @BeforeEach
    public void RegisterSumUsers(){
        String command1 = "user create --username asqar --nickname king --password 123";
        createNewUser(Regex.getMatcher(command1,Regex.userCreate),false);
        String command2 = "user create --username asqar --nickname king --password 123";
        createNewUser(Regex.getMatcher(command2,Regex.userCreate),false);
    }
    @Test
    public void profileTest() throws IOException {
        final String output = "src/main/java/UnitTests/profile/profileOutput.txt";
        final String input = "src/main/java/UnitTests/profile/profileTest.txt";
        final String expectedOutput = "src/main/java/UnitTests/profile/profileExpected.txt";
        writeOutputsToTXT(output);
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("profile change --nickname"))
                    new ProfileMenu(User.getUserByUsername("asqar")).changeNickname(Regex.getMatcher(line,Regex.changeNickname));
                else
                    new ProfileMenu(User.getUserByUsername("asqar")).changeNickname(Regex.getMatcher(line,Regex.changePassword));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       LoginRegisterTest.compareTXT(output, expectedOutput);
    }
    public void writeOutputsToTXT(String output) throws FileNotFoundException {
        PrintStream fileStream = new PrintStream(output);
        System.setOut(fileStream);
    }
}
