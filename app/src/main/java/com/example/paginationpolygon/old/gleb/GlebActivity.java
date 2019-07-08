package com.example.paginationpolygon.old.gleb;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paginationpolygon.R;

import java.time.Duration;
import java.util.function.Consumer;

import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Schedulers;


public class GlebActivity extends AppCompatActivity {

  private Disposable mDisposable = null;
  private Disposable.Swap swap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.tab_pagination);

    final TextView textview = findViewById(R.id.text);
    /*final Flux<View> f = toFlux(textview);

    mDisposable =
      f.switchMap(view -> concatStrMono("str", String.valueOf(System.currentTimeMillis())))
        .publishOn(Schedulers.fromExecutor(textview::post))
        .subscribe(textview::setText);*/


    Flux flux = Flux.just(new Object());


    Flux f2 = flux.publish().refCount(1);

    f2.subscribe();
    f2.subscribe();
    f2.subscribe();
    f2.subscribe();


    swap = Disposables.swap();

    textview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        swap.update(
          concatStrMono("str", String.valueOf(System.currentTimeMillis()))
            .publishOn(Schedulers.fromExecutor(textview::post))
            .delayElement(Duration.ofSeconds(3))
            .subscribe(textview::setText)
        );


      }
    });
  }

  @Override
  protected void onDestroy() {
    //mDisposable.dispose();
    swap.dispose();
    super.onDestroy();
  }

  private Flux<View> toFlux(View view) {
    return Flux.create(sink -> {
      sink.onDispose(clickListener(view));
      view.setOnClickListener(sink::next);
    });
  }

  private Disposable clickListener(View view) {
    return new Disposable() {
      @Override
      public void dispose() {
        view.setOnClickListener(null);
      }

      @Override
      public boolean isDisposed() {
        return !view.hasOnClickListeners();
      }
    };
  }

  private String concatStr(String a, String b) {

    System.out.println("before");

    try {
      Thread.sleep(1000);
    } catch (InterruptedException exception) {
      exception.printStackTrace();
      return null;
    }

    try {
      return a + b;
    } finally {
      System.out.println("after");
    }
  }

  private Thread concatStringAsync(String a, String b, Consumer<String> onReady) {
    final Thread t = new Thread(() -> {
      final String result = concatStr(a, b);
      onReady.accept(result);
    });
    t.start();
    return t;
  }

  private Mono<String> concatStrMono(String a, String b) {

    /*return Mono.fromCallable(() -> concatStr(a, b))
      .subscribeOn(Schedulers.elastic());*/

    return Mono.create(new Consumer<MonoSink<String>>() {
      @Override
      public void accept(MonoSink<String> sink) {
        final Thread t = concatStringAsync(a, b, sink::success);
        sink.onDispose(t::interrupt);

      }
    });
  }


  @Override
  protected void finalize() throws Throwable {
    System.out.println("MainActivity.finalize");
    super.finalize();
  }

}
