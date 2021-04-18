import controllers.Constants.Initialize;
import controllers.ProgramController;

public class Main {
    public static void main(String[] args) {
        Initialize.init();
        new ProgramController().run();
    }
}
