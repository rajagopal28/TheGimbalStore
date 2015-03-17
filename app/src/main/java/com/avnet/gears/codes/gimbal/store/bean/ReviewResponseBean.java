package com.avnet.gears.codes.gimbal.store.bean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/17/15.
 */
public class ReviewResponseBean extends BaseServerDataBean {
    private String HasErrors;
    private String AuthorSubmissionToken;
    private String TypicalHoursToPost;
    private String SubmissionId;
    private Object[] Errors;

    public String getHasErrors() {
        return HasErrors;
    }

    public void setHasErrors(String hasErrors) {
        HasErrors = hasErrors;
    }

    public String getAuthorSubmissionToken() {
        return AuthorSubmissionToken;
    }

    public void setAuthorSubmissionToken(String authorSubmissionToken) {
        AuthorSubmissionToken = authorSubmissionToken;
    }

    public String getTypicalHoursToPost() {
        return TypicalHoursToPost;
    }

    public void setTypicalHoursToPost(String typicalHoursToPost) {
        TypicalHoursToPost = typicalHoursToPost;
    }

    public String getSubmissionId() {
        return SubmissionId;
    }

    public void setSubmissionId(String submissionId) {
        SubmissionId = submissionId;
    }

    public Object[] getErrors() {
        return Errors;
    }

    public void setErrors(Object[] errors) {
        Errors = errors;
    }

    @Override
    public String toString() {
        return super.toString() +
                "ReviewResponseBean{" +
                "HasErrors='" + HasErrors + '\'' +
                ", AuthorSubmissionToken='" + AuthorSubmissionToken + '\'' +
                ", TypicalHoursToPost='" + TypicalHoursToPost + '\'' +
                ", SubmissionId='" + SubmissionId + '\'' +
                ", Errors=" + Arrays.toString(Errors) +
                '}';
    }
}
