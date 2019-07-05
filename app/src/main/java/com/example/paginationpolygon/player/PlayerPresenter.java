package com.example.paginationpolygon.player;

import androidx.annotation.NonNull;

import com.example.paginationpolygon.DataService;

import java.util.Arrays;
import java.util.List;

import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.Flux;

/**
 * @author Konstantin Epifanov
 * @since 25.06.2019
 */
public class PlayerPresenter implements Disposable {

  /** Disposable. */
  private final Disposable mDisposable;

  /** Data Service. */
  private final DataService mDataService;

  private List<String> urls = Arrays.asList(
    "https://media.webka.com/hls/live/master/1562079961.m3u8",
    "https://media.webka.com/hls/vod/40.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/39.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/38.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/37.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/36.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/35.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/34.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/33.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/32.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/31.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/30.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/29.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/28.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/27.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/26.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/25.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/24.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/23.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/22.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/21.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/20.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/19.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/18.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/17.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/16.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/15.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/14.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/13.mp4/index.m3u8",
    "https://media.webka.com/hls/vod/12.mp4/index.m3u8"
  );

  private boolean bigger = true;

  /** Default constructor. */
  PlayerPresenter(@NonNull PlayerFragment view) {

    mDataService = new DataService();

    mDisposable = Disposables.composite(

      Flux.just(urls)
        .subscribe(view::submitUrls),

      view.mUrlClicks
        .subscribe(i -> view.onClickUrl(urls.get(i))),

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

      view .mFluxSwap
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
