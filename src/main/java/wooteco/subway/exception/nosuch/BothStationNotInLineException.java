package wooteco.subway.exception.nosuch;

public class BothStationNotInLineException extends NoSuchException {
    private static final String MESSAGE = "두 역이 모두 노선에 존재하지 않습니다.";

    public BothStationNotInLineException() {
        super(MESSAGE);
    }
}