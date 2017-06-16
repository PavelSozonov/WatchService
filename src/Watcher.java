import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Created by pavel on 16.06.17.
 */
public class Watcher {
    public static void main(String[] args) {
        Watcher watcher = new Watcher();
        watcher.watchFile("/home/pavel/IdeaProjects/FileNio/", "important_log.txt");
    }

    private void watchFile(String dir, String fileName) {
        Path path = Paths.get(dir);
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey watchKey;
            while ((watchKey = watchService.take()) != null) {
                for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
                    if (watchEvent.context().toString().equals(fileName)) {
                        List<String> content = Files.readAllLines(path.resolve(fileName));
                        for (String line : content) {
                            System.out.println(line);
                        }
                    }

                }
                watchKey.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
