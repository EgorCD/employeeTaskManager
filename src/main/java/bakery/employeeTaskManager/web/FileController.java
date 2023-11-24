package bakery.employeeTaskManager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

@Service
public class FileController {

	// A Path variable to store the location where files will be saved
    private final Path fileStorageLocation;

    @Autowired
    public FileController() {
    	// Setting the file storage location to 'src/main/resources/static/uploads'
        this.fileStorageLocation = Paths.get("src/main/resources/static/uploads").toAbsolutePath().normalize();

        try {
        	// Creating the directory for file storage, if it doesn't already exist
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
        	// Throwing a runtime exception if the directory cannot be created
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    public String storeFile(MultipartFile file) {
    	// Cleaning up the file name and ensuring it does not have any path sequences that can be exploited
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
        	// Resolving the file path where the file will be stored
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // Copying the file to the target location, replacing any existing file with the same name
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
        	// Throwing a runtime exception if the file cannot be stored
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
