import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;


public abstract class FileManagerMaker {
    private String path;
    public FileManagerMaker(String path){
        this.path = Paths.get("").toAbsolutePath() + File.separator + "resources" + File.separator + path;
    }
    public void clearFile(String path) throws IOException {
        new FileWriter(path, false).close();
    }
}

