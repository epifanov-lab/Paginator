package com.example.paginationpolygon;

import com.example.paginationpolygon.pagination.Item;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author Konstantin Epifanov
 * @since 24.06.2019
 */
public class DataService {

  private final List<String> mPaginationList;

  public DataService() {
    mPaginationList = new ArrayList<>();
    for (int i = 0; i < Constants.ITEMS_TOTAL_SIZE; i++) {
      mPaginationList.add(String.valueOf(i));
    }
  }

  public Mono<Item[]> load(int offset, int size) {
    int limit = Math.min(mPaginationList.size(), offset + size);

    return Mono.fromCallable(() -> {
      Thread.sleep(Constants.API_DELAY);

      final List<String> list = mPaginationList.subList(offset, limit);

      Item[] result = new Item[list.size()];
      for (int i = 0; i < list.size(); i++) {
        int position = offset + i;
        String s = mPaginationList.get(position);
        result[i] = new Item(s, position);
      }

      return result;
    })
      .subscribeOn(Schedulers.elastic())
      .publishOn(Schedulers.elastic());
  }

  public void addItem() {
    mPaginationList.add(0, String.valueOf(mPaginationList.size() + 1));
  }

  public void deleteItem() {
    mPaginationList.remove("2");
  }

  public void changeItem() {
    mPaginationList.set(2, "CHANGED");
  }

}
