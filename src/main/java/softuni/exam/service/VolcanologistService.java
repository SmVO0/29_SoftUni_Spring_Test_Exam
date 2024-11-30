package softuni.exam.service;

import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public interface VolcanologistService {

    boolean areImported();

    String readVolcanologistsFromFile() throws IOException;

	String importVolcanologists() throws IOException, JAXBException;

}
