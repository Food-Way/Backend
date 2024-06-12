package com.foodway.api.record;

public class ReviewItem {
  private int count;
  private String sentiment;

  public ReviewItem( String sentiment,int count) {
    this.count = count;
    this.sentiment = sentiment;
  }

  public int getCount() {
    return count;
  }
  public void setCount(int count) {
    this.count = count;
  }
  public String getSentiment() {
    return sentiment;
  }
  public void setSentiment(String sentiment) {
    this.sentiment = sentiment;
  }

}
