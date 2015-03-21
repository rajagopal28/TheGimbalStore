package com.avnet.gears.codes.gimbal.store.constant;

/**
 * Created by 914889 on 2/25/15.
 */
public class GimbalStoreConstants {

    public static final String DELIMITER_COLAN = ":";
    public static final String DELIMITER_COMMA = ",";
    public static final String DELIMITER_QUESTION = "?";
    public static final String DELIMITER_SEMICOLAN = ";";
    public static final String DELIMITER_AMPERSAND = "&";
    public static final String DELIMITER_EQUAL = "=";

    public static final String DELIMITER_OPEN_PARENTHESIS = "(";
    public static final String DELIMITER_CLOSE_PARENTHESIS = ")";
    public static final String DELIMITER_SPACE = " ";
    public static final String DELIMITER_HYPHEN = "-";


    public static String ENCODING_UTF_8 = "UTF-8";

    public static String SUCCESS_STRING = "DONE";

    public static String SECURE_URL_PROTOCOL_PREFIX = "https";

    public static String DEFAULT_SPINNER_TITLE = "Loading...";
    public static String DEFAULT_SPINNER_INFO_TEXT = "Please wait for a while!!";

    public static String SYNC_CONTACTS_SPINNER_TITLE = "Syncing Contacts..";
    public static String SYNC_CONTACTS_SPINNER_INFO_TEXT = "Please wait!! This may take a while!!!";

    public static String MESSAGE_NO_REVIEWS = "No reviews for this product yet! Be the first to review!";
    public static String MESSAGE_PRODUCT_GOT_REVIEWS = "This product got {0} user reviews";

    public static String LABEL_FRIEND_RECOMMENDED = "Your Friends {0} have recommended this product for you!!";
    public static String LABEL_NO_FRIEND_RECOMMENDED = "No Friend has recommended yet!!";
    public static String LABEL_OVERALL_RATING = "Overall rating :";
    public static String LABEL_NO_RATING = "No ratings Yet!!";

    public static int CONTACTS_UPLOAD_CHUNK_LIMIT = 25;

    public static String TAG_SHOW_FEED_ITEM = "FeedItemDialogFragment";
    public static String TAG_SHOW_FRIENDS_LIST = "FriendsListDialogFragment";

    public static String SUB_CATEGORY_VIEW_HEADING = "Showing List of Items of {0}";

    public static String START_COMMENT_STRING = "/*";
    public static String END_COMMENT_STRING = "*/";

    public static String PREF_IS_FIRST_TIME_OPEN = "PREF_IS_FIRST_TIME_OPEN";
    public static int ACTIVITY_REQUEST_SIGNUP = 11;
    public static int ACTIVITY_RESULT_LOGIN_SUCCESS = 22;

    public static String APP_ACCOUNT_TYPE_STRING = "com.avnet.gimbal.store.mobile.user.account.type";
    public static String DEFAULT_STORE_NOTIFICATION_TITLE = "You have a Store Notification!!!";
    public static String PROMOTIONS_STORE_NOTIFICATION_DESC = "We have exclusive promotions for you in our nearer store!!";

    public static String COOKIES_RESPONSE_HEADER = "Set-Cookie";
    public static String COOKIES_REQUEST_HEADER = "Cookie";

    public static String PREF_GCM_DEVICE_ID = "PREF_GCM_DEVICE_ID";
    public static String PREF_SESSION_COOKIE_PARAM_KEY = "PREF_SESSION_COOKIE_PARAM_KEY";


    public static enum RECOMMENDATION_TYPE {
        ASK_REC_PROD,
        ASK_REC_CAT,
        TYPE_REVIEW_CATEGORY,
        ASK_REVIEW,
        RECOMMENDED_BY,
        NOTIFY_REVIEW,
        ASKED_TO_REC_PROD,
        ASKED_TO_REC_CAT
    }

    public static enum NOTIFICATION_TYPE {
        ASKED_TO_REC_PROD,
        ASKED_TO_REC_CAT,
        ASKED_TO_REVIEW,
        REVIEWED,
        RECOMMENDED,
        PRODUCT_PROMOTION
    }

    public static enum FEED_ITEM_TYPE {
        FRIEND_RECOMMENDED("Recommended By Friends"),
        FRIEND_REVIEWED("Reviewed By Friends"),
        SUGGESTED_PRODUCTS("Products You may like");

        private String itemTypeLabel;

        FEED_ITEM_TYPE(String label) {
            this.itemTypeLabel = label;
        }

        public String getItemTypeLabel() {
            return this.itemTypeLabel;
        }
    }

    // inter activity interaction data key values
    public static enum INTENT_EXTRA_ATTR_KEY {
        SELECTED_CATEGORY_ID,
        SELECTED_SUB_CATEGORY_ID,
        SELECTED_PRODUCT_ID,
        SELECTED_FEED_ID,
        SELECTED_NOTIFICATION_ID,
        GIVEN_GCM_DEVICE_ID,
        SELECTED_RECOMMENDATION_ID,
        FEED_ITEM_TEXT,
        SELECTED_REVIEW_ID,
        SELECTED_FRIEND_LIST_RESPONSE,
        SELECTED_NETWORK_RESPONSE_TYPE,
        SELECTED_ITEM_THUMBNAIL,
        SELECTED_BEACONS_ID;
    }

    // network data param keys
    public static enum StoreParameterKeys {
        langId,
        storeId,
        type,
        identifier,
        catalogId,
        parentCategoryId,
        logonId,
        logonPassword,
        gcmDeviceId,
        securityToken,
        recommendationId,
        friends,
        productId,
        categoryId,
        reviewText,
        rating,
        rectype,
        beaconId,
        uniqueId
    }

    // network data param keys
    public static enum StoreParameterValues {
        top,
        category,
        product,
        fetchFeeds,
        fetchNotifications,
        signup,
        authentication,
        signin,
        postReview,
        createNetwork,
        fetchContacts,
        askRecommendation,
        askToReview,
        postRecommendation,
        marketing,
        getRecommendation
    }

    public static enum HTTP_METHODS {
        GET, POST
    }

    public static enum HTTP_RESPONSE_CODES {
        OK(200),
        CREATED(201),
        ACCEPTED(202),
        MULTIPLE_CHOICES(300),
        MOVED_PERMANENTLY(301),
        BAD_REQUEST(400),
        UNAUTHORIZED(401),
        FORBIDDEN(403),
        NOT_FOUND(404),
        REQUEST_TIMEOUT(408);
        private int codeValue;

        HTTP_RESPONSE_CODES(int code) {
            this.codeValue = code;
        }

        public static HTTP_RESPONSE_CODES getCode(int value) {
            HTTP_RESPONSE_CODES returnValue = HTTP_RESPONSE_CODES.NOT_FOUND;
            for (HTTP_RESPONSE_CODES code : HTTP_RESPONSE_CODES.values()) {
                if (code.codeValue == value) {
                    returnValue = code;
                    break;
                }
            }
            return returnValue;
        }

    }

    public static enum HTTP_HEADER_PROPERTIES {
        CONNECTION("Connection"),
        CONTENT_TYPE("Content-Type"),
        USER_AGENT("User-Agent"),
        CONTENT_DISPOSITION("Content-Disposition"),
        CONTENT_TRANSFER_ENCODING("Content-Transfer-Encoding"),
        CONTENT_LENGTH("Content-length");
        private String value;

        HTTP_HEADER_PROPERTIES(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static enum SupportedImageFormats {
        png(HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_PNG),
        jpeg(HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_JPEG),
        jpg(HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_JPEG),
        gif(HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_GIF);
        private HTTP_HEADER_VALUES responseType;

        SupportedImageFormats(HTTP_HEADER_VALUES responseType) {
            this.responseType = responseType;
        }

        public HTTP_HEADER_VALUES getResponseType() {
            return this.responseType;
        }
    }

    public static enum HTTP_HEADER_VALUES {
        CONNECTION_KEEP_ALIVE("Keep-Alive"),
        CONTENT_TYPE_TEXT("text/plain"),
        CONTENT_DISPOSITION_FORM_DATA("form-data; name=\"{0}\""),
        CONTENT_DISPOSITION_FILE_NAME("file-name=\"{0}\""),
        CONTENT_TYPE_OCTET("application/octet-stream"),
        CONTENT_TYPE_IMAGE_PNG("image/png"),
        CONTENT_TYPE_IMAGE_JPEG("image/jpeg"),
        CONTENT_TYPE_IMAGE_GIF("image/gif"),
        CONTENT_TYPE_MULTIPART("multipart/form-data; boundary={0}"),
        USER_AGENT_FIRE_FOX_MAC("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"),
        CONTENT_TRANSFER_ENCODING_BINARY("binary");
        private String value;

        HTTP_HEADER_VALUES(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static enum AUTHENTICATION_INTENT_ARGS {
        ARG_USER_ACCOUNT_NAME,
        ARG_ACCOUNT_TYPE,
        ARG_USER_ACCOUNT_PASSWORD,
        AUTH_TOKEN_TYPE,
        ARG_IS_NEW_ACCOUNT,
        ARG_KEY_ERROR_MSG,
        ARG_USER_PASS
    }

    public static enum AUTH_TOKEN_TYPE {

        STORE_ACCESS_FULL("Full Access to store features"),
        STORE_ACCESS_LIMITED("Limited access to store features");

        private String tokenLabel;

        AUTH_TOKEN_TYPE(String tokenLabel) {
            this.tokenLabel = tokenLabel;
        }

        public String getTokenLabel() {
            return this.tokenLabel;
        }
    }

    public static enum notificationDataKey {
        notificationType,
        notificationText,
        notificationId,
        notificationTime,
        notificationSender,
        recommendationId,
        feedItemId,
        productId,
        collapse_key,
        message
    }

    public static enum PHONE_NUMBER_TYPE {
        WORK(3),
        MOBILE(2),
        OTHER(7),
        HOME(1);

        private int numberType;

        PHONE_NUMBER_TYPE(int numType) {
            this.numberType = numType;
        }

        public static PHONE_NUMBER_TYPE getNumberType(int phoneType) {
            PHONE_NUMBER_TYPE returnType = OTHER;
            for (PHONE_NUMBER_TYPE type : PHONE_NUMBER_TYPE.values()) {
                if (type.numberType == phoneType) {
                    returnType = type;
                    break;
                }
            }
            return returnType;
        }
    }

}
