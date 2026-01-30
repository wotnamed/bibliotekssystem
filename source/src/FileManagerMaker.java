import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;


public abstract class FileManagerMaker {
    public void clearFile(String path) throws IOException {
        new FileWriter(path, false).close();
    }
}

