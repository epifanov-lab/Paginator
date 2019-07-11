package com.example.paginationpolygon.pagination;

/**
 * @author Konstantin Epifanov
 * @since 10.07.2019
 */
public class UrlHolder {
  private String videoUrl;
  private String screenshotUrl;

  public UrlHolder(String videoUrl, String screenshotUrl) {
    this.videoUrl = videoUrl;
    this.screenshotUrl = screenshotUrl;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public String getScreenshotUrl() {
    return screenshotUrl;
  }
}
