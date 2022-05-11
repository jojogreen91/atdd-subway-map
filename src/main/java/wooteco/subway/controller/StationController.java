package wooteco.subway.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.subway.domain.Station;
import wooteco.subway.dto.request.StationRequestDto;
import wooteco.subway.dto.response.StationResponseDto;
import wooteco.subway.service.StationService;

@RestController
@RequestMapping("/stations")
public class StationController {

    private final StationService stationService;

    public StationController(final StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<StationResponseDto> createStation(@RequestBody final StationRequestDto stationRequestDto) {
        final Station newStation = stationService.register(stationRequestDto);
        final StationResponseDto stationResponseDto = new StationResponseDto(newStation.getId(), newStation.getName());
        return ResponseEntity.created(URI.create("/stations/" + newStation.getId())).body(stationResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<StationResponseDto>> showStations() {
        final List<Station> stations = stationService.searchAll();
        final List<StationResponseDto> stationResponseDtos = stations.stream()
                .map(station -> new StationResponseDto(station.getId(), station.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(stationResponseDtos);
    }

    @DeleteMapping("/{stationId}")
    public ResponseEntity<Integer> removeStation(@PathVariable Long stationId) {
        stationService.remove(stationId);
        return ResponseEntity.noContent().build();
    }
}
