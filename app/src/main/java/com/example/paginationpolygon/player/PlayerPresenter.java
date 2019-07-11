package com.example.paginationpolygon.player;

import androidx.annotation.NonNull;

import com.example.paginationpolygon.DataService;
import com.example.paginationpolygon.pagination.UrlHolder;

import java.util.ArrayList;

import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.Flux;

import static com.example.paginationpolygon.DataService.urls;

/**
 * @author Konstantin Epifanov
 * @since 25.06.2019
 */
public class PlayerPresenter implements Disposable {

  /** Disposable. */
  private final Disposable mDisposable;

  /** Data Service. */
  private final DataService mDataService;

  private boolean bigger = true;

  /** Default constructor. */
  PlayerPresenter(@NonNull PlayersTabView view) {

    ArrayList<String> urlList = new ArrayList<>();
    urls.stream()
      .map(UrlHolder::getVideoUrl)
      .forEach(urlList::add);

    mDataService = new DataService();

    mDisposable = Disposables.composite(

      Flux.just(urlList)
        .subscribe(view::submitUrls),

      view.mUrlClicks
        .subscribe(i -> view.onClickUrl(urls.get(i).getVideoUrl())),

      view.mFluxRestart
        .doOnNext(o -> view.restart())
        .subscribe(),

      view.mFluxStart
        .doOnNext(o -> view.onClickStart())
        .subscribe(),

      view.mFluxStop
        .doOnNext(o -> view.onClickStop())
        .subscribe(),


      view.mFluxBigger
        .doOnNext(o -> view.onClickBigger())
        .doOnNext(o -> bigger = true)
        .subscribe(),

      view.mFluxSmaller
        .doOnNext(o -> view.onClickSmaller())
        .doOnNext(o -> bigger = false)
        .subscribe(),

      view.mFluxScale
        .doOnNext(o -> view.onClickScale(bigger))
        .subscribe(),

      view.mFluxSwap
        .doOnNext(o -> view.onClickSwap())
        .subscribe()

    );
  }

  /** {@inheritDoc} */
  @Override
  public final void dispose() {
    mDisposable.dispose();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isDisposed() {
    return mDisposable.isDisposed();
  }

}
