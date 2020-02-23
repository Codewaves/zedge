package com.codewaves.zedge.demo.itunes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Album {

  @JsonProperty(access = Access.WRITE_ONLY)
  private String wrapperType;
  @JsonProperty(access = Access.WRITE_ONLY)
  private String collectionType;
  private Integer collectionId;
  private String collectionName;
  private String artistName;
  private String collectionCensoredName;
  private String artistViewUrl;
  private String collectionViewUrl;
  private String artworkUrl60;
  private String artworkUrl100;
  private Double collectionPrice;
  private String collectionExplicitness;
  private Integer trackCount;
  private String copyright;
  private String country;
  private String currency;
  private String releaseDate;
  private String primaryGenreName;

  @JsonCreator
  public Album(@JsonProperty(value = "wrapperType", required = true) String wrapperType,
      @JsonProperty(value = "collectionType") String collectionType,
      @JsonProperty(value = "collectionId") Integer collectionId,
      @JsonProperty(value = "collectionName") String collectionName,
      @JsonProperty(value = "artistName") String artistName,
      @JsonProperty(value = "collectionCensoredName") String collectionCensoredName,
      @JsonProperty(value = "artistViewUrl") String artistViewUrl,
      @JsonProperty(value = "collectionViewUrl") String collectionViewUrl,
      @JsonProperty(value = "artworkUrl60") String artworkUrl60,
      @JsonProperty(value = "artworkUrl100") String artworkUrl100,
      @JsonProperty(value = "collectionPrice") Double collectionPrice,
      @JsonProperty(value = "collectionExplicitness") String collectionExplicitness,
      @JsonProperty(value = "trackCount") Integer trackCount,
      @JsonProperty(value = "copyright") String copyright,
      @JsonProperty(value = "country") String country,
      @JsonProperty(value = "currency") String currency,
      @JsonProperty(value = "releaseDate") String releaseDate,
      @JsonProperty(value = "primaryGenreName") String primaryGenreName) {
    this.wrapperType = wrapperType;
    this.collectionType = collectionType;
    this.collectionId = collectionId;
    this.collectionName = collectionName;
    this.artistName = artistName;
    this.collectionCensoredName = collectionCensoredName;
    this.artistViewUrl = artistViewUrl;
    this.collectionViewUrl = collectionViewUrl;
    this.artworkUrl60 = artworkUrl60;
    this.artworkUrl100 = artworkUrl100;
    this.collectionPrice = collectionPrice;
    this.collectionExplicitness = collectionExplicitness;
    this.trackCount = trackCount;
    this.copyright = copyright;
    this.country = country;
    this.currency = currency;
    this.releaseDate = releaseDate;
    this.primaryGenreName = primaryGenreName;
  }

  @JsonIgnore
  public String getWrapperType() {
    return wrapperType;
  }

  public void setWrapperType(String wrapperType) {
    this.wrapperType = wrapperType;
  }

  @JsonIgnore
  public String getCollectionType() {
    return collectionType;
  }

  public void setCollectionType(String collectionType) {
    this.collectionType = collectionType;
  }

  public Integer getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(Integer collectionId) {
    this.collectionId = collectionId;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

  public String getArtistName() {
    return artistName;
  }

  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }

  public String getCollectionCensoredName() {
    return collectionCensoredName;
  }

  public void setCollectionCensoredName(String collectionCensoredName) {
    this.collectionCensoredName = collectionCensoredName;
  }

  public String getArtistViewUrl() {
    return artistViewUrl;
  }

  public void setArtistViewUrl(String artistViewUrl) {
    this.artistViewUrl = artistViewUrl;
  }

  public String getCollectionViewUrl() {
    return collectionViewUrl;
  }

  public void setCollectionViewUrl(String collectionViewUrl) {
    this.collectionViewUrl = collectionViewUrl;
  }

  public String getArtworkUrl60() {
    return artworkUrl60;
  }

  public void setArtworkUrl60(String artworkUrl60) {
    this.artworkUrl60 = artworkUrl60;
  }

  public String getArtworkUrl100() {
    return artworkUrl100;
  }

  public void setArtworkUrl100(String artworkUrl100) {
    this.artworkUrl100 = artworkUrl100;
  }

  public Double getCollectionPrice() {
    return collectionPrice;
  }

  public void setCollectionPrice(Double collectionPrice) {
    this.collectionPrice = collectionPrice;
  }

  public String getCollectionExplicitness() {
    return collectionExplicitness;
  }

  public void setCollectionExplicitness(String collectionExplicitness) {
    this.collectionExplicitness = collectionExplicitness;
  }

  public Integer getTrackCount() {
    return trackCount;
  }

  public void setTrackCount(Integer trackCount) {
    this.trackCount = trackCount;
  }

  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getPrimaryGenreName() {
    return primaryGenreName;
  }

  public void setPrimaryGenreName(String primaryGenreName) {
    this.primaryGenreName = primaryGenreName;
  }
}
