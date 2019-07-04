package com.example.paginationpolygon.pagination;

import android.graphics.Color;

import java.util.Objects;

/**
 * @author Konstantin Epifanov
 * @since 24.06.2019
 */
public class Item implements Cloneable {

  private String id;
  private int hash;

  private int position;
  private String text;
  private int color;

  public Item(String id, int position) {
    this.id = id;
    this.hash = id.hashCode();
    this.position = position;

    this.text = "id:" + id + " - pos:" + position;
    color = Color.GRAY;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return getPosition() == item.getPosition() &&
      getColor() == item.getColor() &&
      Objects.equals(getText(), item.getText());
  }

  @Override
  public int hashCode() {
    return hash;
  }

  public String getText() {
    return text;
  }

  void setText(String text) {
    this.text = text;
  }

  String getId() {
    return id;
  }

  int getHash() {
    return hash;
  }

  public int getColor() {
    return color;
  }

  void setColor(int color) {
    this.color = color;
  }

  int getPosition() {
    return position;
  }

  @Override
  public String toString() {
    return "Item{" +
      "id='" + id + '\'' +
      ", hash=" + hash +
      ", position=" + position +
      ", text='" + text + '\'' +
      ", color=" + color +
      '}';
  }
}
