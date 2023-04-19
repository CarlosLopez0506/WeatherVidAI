import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        int fileCounter = 0;
        Multimedia multimediaArray[];
        Multimedia myFiles[] = new Multimedia[FileFinder.main("/home/clopez543/Downloads").size()];
//        myFiles[0].setDate("123","12");
        for (String fileName : FileFinder.main("/home/clopez543/Downloads")) {
            myFiles[fileCounter] = new Multimedia(fileName);
            ProcessBuilder processBuilder = new ProcessBuilder("./exiftool/exiftool", "-createDate", fileName);
            try {
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                List<String> files = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    // extract filename from path
//                String[] folderSegments = line.split("/");
//                files.add(folderSegments[folderSegments.length-1]);
                    String date = line.split(":",2)[1];
                    String fullDate[] = date.substring(1).split("[^\\d]+");
                    int[] parsedDate = new int[6];
                    for (int i = 0; i<6; i++){
                        parsedDate[i] = Integer.parseInt(fullDate[i]);
                    }
//                    "Based on code by ChatGPT in a Stack Overflow answer."
                    long seconds = ((parsedDate[0] * 365L + parsedDate[0] / 4L) * 24L * 60L * 60L) // Year
                            + ((parsedDate[1] - 1L) * 31L * 24L * 60L * 60L) // Month
                            + ((parsedDate[2] - 1L) * 24L * 60L * 60L) // Day
                            + (parsedDate[3] * 60L * 60L) // Hour
                            + (parsedDate[4] * 60L) // Minute
                            + parsedDate[5]; // Seconds
//                    "Based on code by ChatGPT in a Stack Overflow answer."

                    myFiles[fileCounter].setDate(seconds);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileCounter +=1;
        }
    }
}