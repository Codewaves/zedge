package com.codewaves.zedge.demo.itunes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResult {

  private int resultCount;
  private List<Artist> results;

  @JsonCreator
  public SearchResult(@JsonProperty(value = "resultCount", required = true) int resultCount,
      @JsonProperty(value = "results", required = true) List<Artist> results) {
    this.resultCount = resultCount;
    this.results = results;
  }

  public int getResultCount() {
    return resultCount;
  }

  public void setResultCount(int resultCount) {
    this.resultCount = resultCount;
  }

  public List<Artist> getResults() {
    return results;
  }

  public void setResults(List<Artist> results) {
    this.results = results;
  }
}
