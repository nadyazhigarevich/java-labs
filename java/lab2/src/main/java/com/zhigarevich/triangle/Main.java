import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.executor.TriangleExecutor;
import com.zhigarevich.triangle.factory.TriangleFactory;
import com.zhigarevich.triangle.parser.TriangleParser;
import com.zhigarevich.triangle.reader.TriangleReader;
import com.zhigarevich.triangle.service.TriangleService;
import com.zhigarevich.triangle.service.TriangleServiceImpl;
import com.zhigarevich.triangle.validator.TriangleValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String filePath = "triangles.txt"; // Replace with your file path

        TriangleValidator validator = new TriangleValidator();
        TriangleService triangleService = new TriangleServiceImpl(validator);
        TriangleExecutor triangleExecutor = new TriangleExecutor(triangleService);

        try {
            TriangleReader triangleReader = new TriangleReader(filePath);
            List<String> triangleData = triangleReader.readTriangleData();

            TriangleParser triangleParser = new TriangleParser();
            List<Triangle> triangles = triangleParser.parseTriangles(triangleData);

            triangleExecutor.executeAll(triangles);
        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", filePath);
        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage());
        } catch (TriangleException e) {
            logger.error("Error processing triangles: {}", e.getMessage());
        }
    }
}