package wooteco.subway.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import wooteco.subway.domain.Line;
import wooteco.subway.dto.request.LineRequestDto;
import wooteco.subway.dto.request.SectionRequestDto;
import wooteco.subway.exception.DuplicateLineNameException;
import wooteco.subway.exception.NoSuchLineException;
import wooteco.subway.repository.dao.LineDao;
import wooteco.subway.repository.entity.LineEntity;

@Service
public class LineService {

    private final LineDao lineDao;

    private final SectionService sectionService;

    public LineService(final LineDao lineDao, final SectionService sectionService) {
        this.lineDao = lineDao;
        this.sectionService = sectionService;
    }

    public Line register(final LineRequestDto lineRequestDto) {
        final Line line = new Line(lineRequestDto.getName(), lineRequestDto.getColor());
        try {
            final LineEntity savedLineEntity = lineDao.save(new LineEntity(line));
            sectionService.registerWhenRegisterLine(line, new SectionRequestDto(lineRequestDto));
            return savedLineEntity.generateLine();
        } catch (DuplicateKeyException exception) {
            throw new DuplicateLineNameException();
        }
    }

    public Line searchById(final Long id) {
        return lineDao.findById(id)
                .orElseThrow(() -> new NoSuchLineException())
                .generateLine();
    }

    public List<Line> searchAll() {
        return lineDao.findAll()
                .stream()
                .map(lineEntity -> new Line(lineEntity.getId(), lineEntity.getName(), lineEntity.getColor()))
                .collect(Collectors.toList());
    }

    public void modify(final Long id, final LineRequestDto lineRequestDto) {
        lineDao.update(new LineEntity(id, lineRequestDto.getName(), lineRequestDto.getColor()));
    }

    public void remove(final Long id) {
        lineDao.deleteById(id);
    }
}
