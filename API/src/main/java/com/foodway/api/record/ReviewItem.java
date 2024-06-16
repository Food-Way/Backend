package com.foodway.api.record;

public class ReviewItem {
  private Long count;
  private String sentiment;

  public ReviewItem( String sentiment,Long count) {
    this.count = count;
    this.sentiment = sentiment;
  }

  public Long getCount() {
    return count;
  }
  public void setCount(Long count) {
    this.count = count;
  }
  public String getSentiment() {
    return sentiment;
  }
  public void setSentiment(String sentiment) {
    this.sentiment = sentiment;
  }

}
