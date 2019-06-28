package com.example.paginationpolygon;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author Konstantin Epifanov
 * @since 24.06.2019
 */
public class DataService {

  private final List<String> mList;

  public DataService() {
    mList = new ArrayList<>();
    for (int i = 0; i < Constants.ITEMS_TOTAL_SIZE; i++) {
      mList.add(String.valueOf(i));
    }
  }

  public Mono<Item[]> load(int offset, int size) {
    int limit = Math.min(mList.size(), offset + size);

    return Mono.fromCallable(() -> {
      Thread.sleep(Constants.API_DELAY);

      final List<String> list = mList.subList(offset, limit);

      Item[] result = new Item[list.size()];
      for (int i = 0; i < list.size(); i++) {
        int position = offset + i;
        String s = mList.get(position);
        result[i] = new Item(s, position);
      }

      return result;
    })
      .subscribeOn(Schedulers.elastic())
      .publishOn(Schedulers.elastic());
  }

  public void addItem() {
    mList.add(0, String.valueOf(mList.size() + 1));
  }

  public void deleteItem() {
    mList.remove("2");
  }

  public void changeItem() {
    mList.set(2, "CHANGED");
  }

}
