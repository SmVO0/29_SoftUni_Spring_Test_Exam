package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoImportDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.enums.VolcanoType;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VolcanoServiceImpl implements VolcanoService {


    private final VolcanoRepository volcanoRepository;
    private final CountryRepository countryRepository;
    private final String VOLCANO_FILE_PATH = "src/main/resources/files/json/volcanoes.json";

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    VolcanoServiceImpl(VolcanoRepository volcanoRepository, CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.volcanoRepository = volcanoRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {

        return Files.readString(Path.of(VOLCANO_FILE_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        StringBuilder sb = new StringBuilder();
        VolcanoImportDto[] volcanoImportDtos = this.gson.fromJson(readVolcanoesFileContent(), VolcanoImportDto[].class);
        for (VolcanoImportDto volcanoImportDto : volcanoImportDtos) {
            if (this.volcanoRepository.findByName(volcanoImportDto.getName()).isPresent() ||
                    !this.validationUtil.isValid(volcanoImportDto)) {
                sb.append("Invalid volcano").append(System.lineSeparator());
                continue;
            }
            Volcano volcano = this.modelMapper.map(volcanoImportDto, Volcano.class);
            volcano.setVolcanoType(VolcanoType.valueOf(volcanoImportDto.getVolcanoType()));
            volcano.setCountry(this.countryRepository.findById((long) volcanoImportDto.getCountry()).get());
            this.volcanoRepository.save(volcano);
            sb.append(String.format("Successfully imported volcano %s of type %s",
                    volcanoImportDto.getName(), volcanoImportDto.getVolcanoType().toString())).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public String exportVolcanoes() {
        StringBuilder sb = new StringBuilder();
        this.volcanoRepository.findByElevationAfterAndIsActiveAndLastEruptionIsNotNullOrderByElevationDesc(3000, true)
                .forEach(volcano -> {
                    sb.append(String.format("Volcano: %s\n" +
                            "   *Located in: %s\n" +
                            "   **Elevation: %d\n" +
                            "   ***Last eruption on: %s", volcano.getName(),
                            volcano.getCountry().getName(),
                            volcano.getElevation(),
                            volcano.getLastEruption()))
                            .append(System.lineSeparator());
                });
        return sb.toString();
    }
}