package sanchez.sanchez.sergio.rest.response;

public enum AlertResponseCode implements IResponseCodeTypes {

    ALL_ALERTS(700L), NO_ALERTS_FOUND(701L), ALL_INFO_ALERTS(702L),
    ALL_WARNING_ALERTS(703L), ALL_DANGER_ALERTS(704L), ALL_SUCCESS_ALERTS(705L);

    private Long code;

    private AlertResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
