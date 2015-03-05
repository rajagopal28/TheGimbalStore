package com.avnet.gears.codes.gimbal.store.client;

/**
 * Created by 914889 on 2/24/15.
 */

import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_HEADER_VALUES;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_METHODS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

public class HttpClient {

    private static final int BYTE_ARRAY_LIMIT_1K = 1024;



    private static final String DELIMITER = "--";
    private static final String BOUNDARY =  "SwA"
                                    + Long.toString(System.currentTimeMillis())
                                    +"SwA";
    private static final String HEADER_DELIMITER_VALUE = "\r\n";
    private static final String DELIMITER_COLAN = ":";
    private static final String DELIMITER_QUESTION = "?";
    private static final String DELIMITER_SEMICOLAN = ";";
    private static final String DELIMITER_AMPERSAND = "&";
    private static final String DELIMITER_EQUAL = "=";

    private static int MAX_READ_TIMEOUT = 100000;
    protected HttpClient() {
        // treating it as a static class
    }
    private static HttpURLConnection getPlainHttConnection(String url, HTTP_METHODS httpMethod, boolean doInput,
                                                    boolean doOutput, boolean isMultiPart, boolean setKeepAlive,
                                                    boolean useCaches,boolean allowUserInteraction, int timeout) throws MalformedURLException, IOException {
        HttpURLConnection con = (HttpURLConnection) ( new URL(url)).openConnection();
        con.setRequestMethod(httpMethod.toString());
        con.setDoInput(doInput);
        con.setDoOutput(doOutput);
        if(httpMethod == HTTP_METHODS.GET) {
            con.setRequestProperty(GimbalStoreConstants.HTTP_HEADER_PROPERTIES.CONTENT_LENGTH.getValue(), "0");
            con.setUseCaches(useCaches);
            con.setAllowUserInteraction(allowUserInteraction);
            con.setConnectTimeout(timeout);
            con.setReadTimeout(timeout);
        }
        if( setKeepAlive ) {
            con.setRequestProperty(GimbalStoreConstants.HTTP_HEADER_PROPERTIES.CONNECTION.getValue(),
                    HTTP_HEADER_VALUES.CONNECTION_KEEP_ALIVE.getValue());
        }
        if( isMultiPart ) {
          con.setRequestProperty(GimbalStoreConstants.HTTP_HEADER_PROPERTIES.CONTENT_TYPE.getValue(),
                  MessageFormat.format(HTTP_HEADER_VALUES.CONTENT_TYPE_MULTIPART.getValue(),
                          BOUNDARY)
                  );
        }
        con.connect();
        return con;
    }

    public static HttpURLConnection getHttPOSTConnection(String url) throws MalformedURLException, IOException {
        return getPlainHttConnection(url, HTTP_METHODS.POST, true,
                            true, false, false,
                            false,false,MAX_READ_TIMEOUT);
    }
    public static HttpURLConnection getHttpGetConnection(String url, Map<String,String> paramsMap) throws  MalformedURLException, IOException {
        url += DELIMITER_QUESTION + getStringParameters(paramsMap);
        return getPlainHttConnection(url, HTTP_METHODS.GET, true,
                            true, false, false,
                            true, true, MAX_READ_TIMEOUT);
    }

    public static HttpURLConnection getMultiPartHttpPOSTConnection(String url) throws MalformedURLException, IOException {
        return getPlainHttConnection(url, HTTP_METHODS.POST, true,
                            true, true, true,
                            false,false,MAX_READ_TIMEOUT);
    }

    private static String getStringParameters(Map<String,String> parametersMap) {
        Set<String> keySet = parametersMap.keySet();
        String outputParametersString = "";
        for(String key : keySet) {
            if(!outputParametersString.isEmpty()) {
                outputParametersString += DELIMITER_AMPERSAND;
            }
            outputParametersString += key + DELIMITER_EQUAL + parametersMap.get(key);
        }
        return outputParametersString;
    }

    public static void writePlainParameters(OutputStream outputStream, Map<String,String> parametersMap) throws IOException {
        String outputParametersString = getStringParameters(parametersMap);
        // Log.d("HTTP DEBUG", outputParametersString);
        outputStream.write(outputParametersString.getBytes());
    }
    public static byte[] downloadBytesData(HttpURLConnection con) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream is = con.getInputStream();
            byte[] b = new byte[BYTE_ARRAY_LIMIT_1K];

            while ( is.read(b) != -1)
                byteArrayOutputStream.write(b);

            con.disconnect();

        return byteArrayOutputStream.toByteArray();
    }

    public static void writeMultiPartFileData(OutputStream os, String paramName, String fileName, byte[] data) throws Exception {
        os.write( (DELIMITER + BOUNDARY + "\r\n").getBytes());
        os.write( (GimbalStoreConstants.HTTP_HEADER_PROPERTIES.CONTENT_DISPOSITION.getValue()
                + DELIMITER_COLAN + " "
                + MessageFormat.format(HTTP_HEADER_VALUES.CONTENT_DISPOSITION_FORM_DATA.getValue(),
                                paramName)
                + DELIMITER_SEMICOLAN
                + MessageFormat.format(HTTP_HEADER_VALUES.CONTENT_DISPOSITION_FILE_NAME.getValue(),
                    fileName)
                + HEADER_DELIMITER_VALUE).getBytes());
        os.write( (GimbalStoreConstants.HTTP_HEADER_PROPERTIES.CONTENT_TYPE.getValue()
                + DELIMITER_COLAN + " "
                + HTTP_HEADER_VALUES.CONTENT_TYPE_OCTET.getValue()
                + HEADER_DELIMITER_VALUE ).getBytes());
        os.write( (GimbalStoreConstants.HTTP_HEADER_PROPERTIES.CONTENT_TRANSFER_ENCODING.getValue()
                + DELIMITER_COLAN + " "
                + HTTP_HEADER_VALUES.CONTENT_TRANSFER_ENCODING_BINARY.getValue()
                + HEADER_DELIMITER_VALUE ).getBytes());
        os.write("\r\n".getBytes());

        os.write(data);

        os.write(HEADER_DELIMITER_VALUE.getBytes());
    }

    public static void finishMultipart(OutputStream os) throws Exception {
        os.write( (DELIMITER + BOUNDARY + DELIMITER + HEADER_DELIMITER_VALUE).getBytes());
    }


    public static String getResponseString(HttpURLConnection con) throws Exception {
        int responseCode = con.getResponseCode();

        StringBuffer buffer = new StringBuffer();
        if(GimbalStoreConstants.HTTP_RESPONSE_CODES.getCode(responseCode) == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK
                || GimbalStoreConstants.HTTP_RESPONSE_CODES.getCode(responseCode) == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
            InputStream is = con.getInputStream();
            int len = 0;


            byte[] data1 = new byte[BYTE_ARRAY_LIMIT_1K];
            while ( -1 != (len = is.read(data1)) )
                buffer.append(new String(data1, 0, len));

        }
        con.disconnect();

        return buffer.toString();
    }



    private static void writeMultiPartParamData(OutputStream os, Map<String,String> parametersMap) throws Exception {
        Set<String> keySet = parametersMap.keySet();

        for(String key : keySet) {
            os.write( (DELIMITER + BOUNDARY + HEADER_DELIMITER_VALUE).getBytes());
            os.write((GimbalStoreConstants.HTTP_HEADER_PROPERTIES.CONTENT_TYPE.getValue()
                    + DELIMITER_COLAN + " "
                    + HTTP_HEADER_VALUES.CONTENT_TYPE_TEXT.getValue()
                    + HEADER_DELIMITER_VALUE).getBytes());


            os.write(  (GimbalStoreConstants.HTTP_HEADER_PROPERTIES.CONTENT_DISPOSITION.getValue()
                    + DELIMITER_COLAN + " "
                    + MessageFormat.format(HTTP_HEADER_VALUES.CONTENT_DISPOSITION_FORM_DATA.getValue(),
                            key)
                    + HEADER_DELIMITER_VALUE).getBytes());
            os.write( (HEADER_DELIMITER_VALUE
                    + parametersMap.get(key)
                    + HEADER_DELIMITER_VALUE).getBytes());

        }

    }
}