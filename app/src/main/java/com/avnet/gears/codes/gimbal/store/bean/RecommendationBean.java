package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/21/15.
 */
public class RecommendationBean extends BaseServerDataBean {

    private String recommendationId;
    private String recommendationType;
    private String recommendationResponseType;
    private String recommendationStatus;
    private String identifierValue;
    private String friendUserId;

    public String getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(String recommendationId) {
        this.recommendationId = recommendationId;
    }

    public String getRecommendationType() {
        return recommendationType;
    }

    public void setRecommendationType(String recommendationType) {
        this.recommendationType = recommendationType;
    }

    public String getRecommendationResponseType() {
        return recommendationResponseType;
    }

    public void setRecommendationResponseType(String recommendationResponseType) {
        this.recommendationResponseType = recommendationResponseType;
    }

    public String getRecommendationStatus() {
        return recommendationStatus;
    }

    public void setRecommendationStatus(String recommendationStatus) {
        this.recommendationStatus = recommendationStatus;
    }

    public String getIdentifierValue() {
        return identifierValue;
    }

    public void setIdentifierValue(String identifierValue) {
        this.identifierValue = identifierValue;
    }

    public String getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(String friendUserId) {
        this.friendUserId = friendUserId;
    }

    @Override
    public String toString() {
        return super.toString() +
                "RecommendationBean{" +
                "recommendationId='" + recommendationId + '\'' +
                ", recommendationType='" + recommendationType + '\'' +
                ", recommendationResponseType='" + recommendationResponseType + '\'' +
                ", recommendationStatus='" + recommendationStatus + '\'' +
                ", identifierValue='" + identifierValue + '\'' +
                ", friendUserId='" + friendUserId + '\'' +
                '}';
    }
}
