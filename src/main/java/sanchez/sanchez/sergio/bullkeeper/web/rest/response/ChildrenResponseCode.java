package sanchez.sanchez.sergio.bullkeeper.web.rest.response;

/**
 * Children Response Code
 * @author sergiosanchezsanchez
 *
 */
public enum ChildrenResponseCode implements IResponseCodeTypes {

    ALL_USERS(100L), 
    SINGLE_USER(101L),
    USER_CREATED(102L), 
    USER_NOT_FOUND(103L), 
    NO_CHILDREN_FOUND(104L), 
    PROFILE_IMAGE_UPLOAD_SUCCESSFULLY(105L), 
    ALERTS_BY_KID(106L), 
    NO_ALERTS_BY_KID_FOUNDED(107L),
    CHILD_ALERTS_CLEANED(108L), 
    GET_ALERT_BY_ID(109L), 
    ALERT_NOT_FOUND(110L),
    ALERT_BY_ID_DELETED(111L),
    CHILD_DELETED_SUCCESSFULLY(112L), 
    SOCIAL_MEDIA_ACTIVITY_STATISTICS(113L), 
    SOCIAL_MEDIA_ACTIVITY_STATISTICS_NOT_FOUND(114L),
    SENTIMENT_ANALYSIS_STATISTICS(115L), 
    COMMUNITIES_STATISTICS(116L), 
    FOUR_DIMENSIONS_STATISTICS(117L),
    NO_SENTIMENT_ANALYSIS_STATISTICS_FOR_THIS_PERIOD(118L), 
    NO_COMMUNITY_STATISTICS_FOR_THIS_PERIOD(119L),
    NO_DIMENSIONS_STATISTICS_FOR_THIS_PERIOD(120L), 
    SUCCESS_ALERTS_BY_KID(121L),
    WARNING_ALERTS_BY_KID(122L),
    INFO_ALERTS_BY_KID(123L), 
    DANGER_ALERTS_BY_KID(124L), 
    CHILD_WARNING_ALERTS_CLEANED(125L),
    CHILD_INFO_ALERTS_CLEANED(126L), 
    CHILD_SUCCESS_ALERTS_CLEANED(127L), 
    CHILD_DANGER_ALERTS_CLEANED(128L),
    ALL_TERMINALS(129L), 
    NO_TERMINALS_FOUND(130L), 
    TERMINAL_SAVED(131L), 
    NO_TERMINAL_FOUND_EXCEPTION(132L),
    TERMINAL_BY_ID_DELETED(133L),
    TERMINAL_DETAIL(134L),
    ALL_SCHEDULED_BLOCKS(143L), 
    NO_SCHEDULED_BLOCKS_FOUND(144L), 
    SCHEDULED_BLOCK_SAVED(145L),
    ALL_SCHEDULED_BLOCK_DELETED(146L),
    SCHEDULED_BLOCK_DELETED(147L), 
    SCHEDULED_BLOCK_DETAIL(148L),
    SCHEDULED_BLOCK_STATUS_SAVED(149L),
    SCHEDULED_BLOCK_IMAGE_UPLOADED(150L),
    CHILD_GUARDIANS_SAVED(151L),
    CHILD_GUARDIANS(152L),
    CURRENT_LOCATION_OF_KID(153L),
    NO_CURRENT_LOCATION_FOUND(154L),
    CURRENT_LOCATION_UPDATED(155L),
    PHONE_NUMBER_BLOCKED_LIST(156L),
    NO_PHONE_NUMBER_BLOCKED_FOUND(157L),
    ALL_PHONE_NUMBERS_UNBLOCKED_SUCCESSFULLY(158L),
    PHONE_NUMBER_UNBLOCKED_SUCCESSFULLY(159L),
    PHONE_NUMBER_BLOCKED_ADDED(160L),
    TERMINAL_HEARTBEAT_NOTIFIED_SUCCESSFULLY(161L),
    SCHEDULED_BLOCK_NOT_VALID(162L),
    PHONE_NUMBER_ALREADY_BLOCKED(163L),
    FUN_TIME_SCHEDULED(164L),
    FUN_TIME_SCHEDULED_SAVED(165L),
    FUN_TIME_NOT_FOUND(166L),
    FUN_TIME_DAY_SCHEDULED(167L),
    FUN_TIME_DAY_SCHEDULED_NOT_FOUND(168L),
    FUN_TIME_DAY_SCHEDULED_SAVED(169L),
    BED_TIME_ENABLED_SUCCESSFULLY(170L),
    BED_TIME_DISABLED_SUCCESSFULLY(171L),
    LOCK_SCREEN_ENABLED_SUCCESSFULLY(172L),
    LOCK_SCREEN_DISABLED_SUCCESSFULLY(173L),
    LOCK_CAMERA_ENABLED_SUCCESSFULLY(174L),
    LOCK_CAMERA_DISABLED_SUCCESSFULLY(175L),
    KID_REQUEST_SAVED(176L),
    ALL_KID_REQUEST(177L),
    ALL_KID_REQUEST_DELETED(178L),
    KID_REQUEST_DELETED(179l),
    KID_REQUEST_DETAIL(180l),
    NO_KID_REQUEST_FOUND(181l),
    KID_REQUEST_NOT_FOUND(182l),
    SINGLE_KID_REQUEST_DELETED(182l),
    PREVIOUS_REQUEST_HAS_NOT_EXPIRED(183L),
    SETTINGS_ENABLE_SUCCESSFULLY(184L),
    SETTINGS_DISABLE_SUCCESSFULLY(185L);

    private Long code;

    private ChildrenResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
