import java.io.File;
import java.io.IOException;

public interface BackupDatabase {
    default void moveDatabase() throws IOException {
        // File (or directory) with old name
        File file = new File("savedList.list");

        File file2 = new File("savedList_2.list");

        File file3 = new File("Deck.list");

        File file4 = new File("Deck_2.list");
        if (file2.exists())
            file2.delete();

        if (file4.exists())
            file4.delete();

        // Rename file (or directory)
        boolean success1 = file.renameTo(file2);
        if(success1)
            System.out.println("user database renamed successfully");
        boolean success2 = file3.renameTo(file4);
        if(success2)
            System.out.println("Deck database renamed successfully");
    }
    default void backDatabase(){
        // File (or directory) with old name
        File file = new File("savedList_2.list");

        File file2 = new File("savedList.list");

        File file3 = new File("Deck_2.list");

        File file4 = new File("Deck.list");
        if (file2.exists())
            file2.delete();

        if (file4.exists())
            file4.delete();
        boolean success1 = file.renameTo(file2);
        if(success1)
            System.out.println("user database renamed successfully");
        boolean success2 = file3.renameTo(file4);
        if(success2)
            System.out.println("Deck database renamed successfully");
    }
}
