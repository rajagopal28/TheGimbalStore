package com.avnet.gears.codes.gimbal.store.bean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/16/15.
 */
public class ReviewBean extends BaseServerDataBean {
    private String TotalResults;
    private String Offset;
    private String HasErrors;
    private ReviewResult[] Results;
    private Object Includes;
    private String[] errors;

    public String getTotalResults() {
        return TotalResults;
    }

    public void setTotalResults(String totalResults) {
        TotalResults = totalResults;
    }

    public String getOffset() {
        return Offset;
    }

    public void setOffset(String offset) {
        Offset = offset;
    }

    public String getHasErrors() {
        return HasErrors;
    }

    public void setHasErrors(String hasErrors) {
        HasErrors = hasErrors;
    }

    public ReviewResult[] getResults() {
        return Results;
    }

    public void setResults(ReviewResult[] results) {
        Results = results;
    }

    public Object getIncludes() {
        return Includes;
    }

    public void setIncludes(Object includes) {
        Includes = includes;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return super.toString() +
                "ReviewBean{" +
                "TotalResults='" + TotalResults + '\'' +
                ", Offset='" + Offset + '\'' +
                ", HasErrors='" + HasErrors + '\'' +
                ", Results=" + Arrays.toString(Results) +
                ", Includes='" + Includes + '\'' +
                ", errors=" + Arrays.toString(errors) +
                '}';
    }
}
