package com.example.paginationpolygon;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.example.paginationpolygon.Utils.getRandomColor;

/**
 * @author Konstantin Epifanov
 * @since 24.06.2019
 */
public class DataService {

  private final List<Content> mList;

  public DataService() {
    mList = new ArrayList<>();
    for (int i = 0; i < Constants.ITEMS_TOTAL_SIZE; i++) {
      Content item = new Content(i);
      mList.add(item);
    }
  }

  public Mono<Content[]> load(int offset, int size) {
    int limit = Math.min(mList.size(), offset + size);

    return Mono.fromCallable(() -> {
      Thread.sleep(Constants.API_DELAY);
      Content[] contents = mList.subList(offset, limit).toArray(new Content[0]);
      System.out.println("LOAD: " + " offset:" + offset + ", limit:" + limit + " , result:" + contents.length);
      return contents;
    })
      //.filter(contents -> contents.length > 0)
      .subscribeOn(Schedulers.elastic())
      .publishOn(Schedulers.elastic());
  }

  /**
   * @author Konstantin Epifanov
   * @since 24.06.2019
   */
  public class Content {

    private int position;
    private String text;
    private int color;

    public Content(int position) {
      this.text = String.valueOf(position);
      this.position = position;
      color = getRandomColor();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Content content = (Content) o;
      return getPosition() == content.getPosition();
    }

    @Override
    public int hashCode() {
      return position;
    }

    public String getText() {
      return text;
    }

    public int getColor() {
      return color;
    }

    int getPosition() {
      return position;
    }

    @Override
    public String toString() {
      return "Content{" +
        "position=" + position +
        ", text='" + text + '\'' +
        ", color=" + color +
        '}';
    }
  }
}
