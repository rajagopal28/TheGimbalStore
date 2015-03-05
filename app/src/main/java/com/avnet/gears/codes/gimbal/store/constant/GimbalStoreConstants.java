package com.avnet.gears.codes.gimbal.store.constant;

import java.util.List;

/**
 * Created by 914889 on 2/25/15.
 */
public class GimbalStoreConstants {

    public static String SUCCESS_STRING = "DONE";

    // inter activity interaction data key values
    public static enum INTENT_EXTRA_ATTR_KEY {
        SELECTED_CATEGORY_ID,
        SELECTED_SUB_CATEGORY_ID,
        SELECTED_PRODUCT_ID,
        SELECTED_FEED_ID,
        SELECTED_NOTIFICATION_ID,
        SELECTED_REVIEW_ID;
    }
    public static String START_COMMENT_STRING = "/*";
    public static String END_COMMENT_STRING = "*/";
    public static enum StoreParameterKeys {
        langId,
        storeId,
        type,
        identifier,
        catalogId
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
        HTTP_RESPONSE_CODES(int code) {
            this.codeValue = code;
        }
        private int codeValue;

        public static HTTP_RESPONSE_CODES getCode(int value) {
            HTTP_RESPONSE_CODES returnValue = HTTP_RESPONSE_CODES.NOT_FOUND;
            for(HTTP_RESPONSE_CODES code : HTTP_RESPONSE_CODES.values()) {
                if(code.codeValue == value) {
                    returnValue = code;
                    break;
                }
            }
            return returnValue;
        }

    }
    public static enum HTTP_REQUEST_HEADER_PROPERTIES {
        CONNECTION("Connection"),
        CONTENT_TYPE("Content-Type"),
        CONTENT_DISPOSITION("Content-Disposition"),
        CONTENT_TRANSFER_ENCODING("Content-Transfer-Encoding"),
        CONTENT_LENGTH("Content-length");

        HTTP_REQUEST_HEADER_PROPERTIES(String value) {
            this.value = value;
        }
        private String value;

        public String getValue() {
            return this.value;
        }
    }

    public static enum HTTP_HEADER_VALUES {
        CONNECTION_KEEP_ALIVE("Keep-Alive"),
        CONTENT_TYPE_TEXT("text/plain"),
        CONTENT_DISPOSITION_FORM_DATA("form-data; name=\"{0}\""),
        CONTENT_DISPOSITION_FILE_NAME("file-name=\"{0}\""),
        CONTENT_TYPE_OCTET(" application/octet-stream"),
        CONTENT_TYPE_MULTIPART("multipart/form-data; boundary={0}"),
        CONTENT_TRANSFER_ENCODING_BINARY("binary");

        HTTP_HEADER_VALUES(String value) {
            this.value = value;
        }
        private String value;
        public String getValue(){
            return this.value;
        }
    }

}