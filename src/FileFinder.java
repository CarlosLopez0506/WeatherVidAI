import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {
    public static List<String> main(String folderPath) {
        try {
            // create process builder for find command
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "find " + folderPath + " \\( -name '*.heif' -o -name '*.JPG' -o -name '*.mp4' \\)");

            // start process and read output
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            List<String> files = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                // extract filename from path
//                String[] folderSegments = line.split("/");
//                files.add(folderSegments[folderSegments.length-1]);
                files.add(line);
            }
            return files;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

