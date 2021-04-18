package lcaparros.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static String getFilePath(String fileName) {
        String filePath = "";
        try {
            URL fileUrl = DataProvider.class.getClassLoader().getResource(fileName);
            if (fileUrl != null) {
                File file = Paths.get(fileUrl.toURI()).toFile();
                filePath = file.getAbsolutePath();
            } else {
                logger.error("Target file does not exists");
                throw new AssertionError();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static List<String> diffLists(List<String> completeList, List<String> excludeList) {
        List<String> resultList = new LinkedList<>(completeList);
        resultList.removeAll(excludeList);
        return resultList;
    }
}
