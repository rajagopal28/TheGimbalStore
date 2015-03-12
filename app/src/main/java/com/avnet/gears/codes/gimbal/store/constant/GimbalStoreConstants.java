package com.avnet.gears.codes.gimbal.store.constant;

/**
 * Created by 914889 on 2/25/15.
 */
public class GimbalStoreConstants {

    public static String SUCCESS_STRING = "DONE";

    public static String DEFAULT_SPINNER_TITLE = "Loading...";
    public static String DEFAULT_SPINNER_INFO_TEXT = "Please wait for a while!!";

    public static String SUB_CATEGORY_VIEW_HEADING = "Showing List of Items of {0}";
    public static String START_COMMENT_STRING = "/*";
    public static String END_COMMENT_STRING = "*/";

    public static String PREF_IS_FIRST_TIME_OPEN = "PREF_IS_FIRST_TIME_OPEN";

    public static int ACTIVITY_REQUEST_SIGNUP = 11;

    public static String APP_ACCOUNT_TYPE_STRING = "com.avnet.gimbal.store.mobile.user.account.type";

    public static String DEFAULT_STORE_NOTIFICATION_TITLE = "You have a Store Notification!!!";

    public static enum NOTIFICATION_TYPE {
        NOTIFY_RECOMMENDATION("recommendation"),
        NOTIFY_FEED("feed");
        private String stringType;

        NOTIFICATION_TYPE(String stringType) {
            this.stringType = stringType;
        }

        public static NOTIFICATION_TYPE getNotificationType(String stringType) {
            NOTIFICATION_TYPE returnType = NOTIFICATION_TYPE.NOTIFY_FEED;
            for (NOTIFICATION_TYPE notificationType : NOTIFICATION_TYPE.values()) {
                if (notificationType.stringType.equalsIgnoreCase(stringType)) {
                    returnType = notificationType;
                    break;
                }
            }
            ;
            return returnType;
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
        SELECTED_REVIEW_ID;
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
        productId// TODO change
    }

    // network data param keys
    public static enum StoreParameterValues {
        top,
        category,
        signup,
        authentication,
        signin
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
        notificationTime,
        notificationSender,
        collapse_key,
        message
    }

}
