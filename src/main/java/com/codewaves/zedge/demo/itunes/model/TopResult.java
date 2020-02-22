package com.codewaves.zedge.demo.itunes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TopResult {

  @JsonProperty(required = true)
  private int resultCount;
  @JsonProperty(required = true)
  private List<Album> results;

  @JsonCreator
  public TopResult(@JsonProperty(value = "resultCount", required = true) int resultCount,
      @JsonProperty(value = "results", required = true) List<Album> results) {
    this.resultCount = resultCount;
    this.results = results;
  }

  public int getResultCount() {
    return resultCount;
  }

  public void setResultCount(int resultCount) {
    this.resultCount = resultCount;
  }

  public List<Album> getResults() {
    return results;
  }

  public void setResults(List<Album> results) {
    this.results = results;
  }
}
