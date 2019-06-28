package com.example.paginationpolygon;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

/**
 * @author Konstantin Epifanov
 * @since 24.06.2019
 */
public class Utils {

  public static Flux<View> toFlux(View view) {
    return Flux.create(sink -> {
      view.setOnClickListener(sink::next);

      sink.onDispose(new Disposable() {
        @Override
        public void dispose() {
          view.setOnClickListener(null);
        }

        @Override
        public boolean isDisposed() {
          return !view.hasOnClickListeners();
        }
      });
    });
  }

  public static int getRandomColor() {
    return Color.rgb(
      (int) (Math.random() * 255),
      (int) (Math.random() * 255),
      (int) (Math.random() * 255)
    );
  }

  @SuppressWarnings("SameParameterValue")
  public static <T> PagedListAdapter<T, RecyclerView.ViewHolder>
  getPagedAdapter(@NonNull LayoutInflater inflater, @LayoutRes int layout) {
    return new PagedListAdapter<T, RecyclerView.ViewHolder>(
      new AsyncDifferConfig.Builder<>
        (new DiffUtil.ItemCallback<T>() {
          @SuppressWarnings("NullableProblems")
          @Override
          public final boolean
          areItemsTheSame(T oldItem, T newItem) {
            //System.out.println("areItemsTheSame: " + "oldItem = [" + oldItem + "], newItem = [" + newItem + "]");
            boolean result = oldItem.hashCode() == newItem.hashCode();
            return result;
          }

          @SuppressWarnings("NullableProblems")
          @Override
          public final boolean
          areContentsTheSame(T oldItem, T newItem) {
            //System.out.println("areContentsTheSame: " + "oldItem = [" + oldItem + "], newItem = [" + newItem + "]");
            boolean result = Objects.equals(oldItem, newItem);
            return result;
          }

          @Nullable
          @Override
          public Object getChangePayload(@NonNull T oldItem, @NonNull T newItem) {
            //System.out.println("getChangePayload: oldItem = [" + oldItem + "], newItem = [" + newItem + "]");
            return newItem;
          }
        }).setBackgroundThreadExecutor(Runnable::run)
        .build()
    ) {
      {
        setHasStableIds(false);
      }

      @SuppressWarnings("NullableProblems")
      @Override
      public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new RecyclerView.ViewHolder(inflater.inflate(type, parent, false)) {
        };
      }

      @Override
      public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) onBindViewHolder(holder, position);
        else {
          final Consumer<T> view = ((Consumer<T>) holder.itemView);
          payloads.forEach(item -> view.accept((T) item));
        }

        //((Consumer<T>) holder.itemView).accept(getItem(position));
      }

      @SuppressWarnings({"unchecked", "NullableProblems"})
      @Override
      public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Consumer<T>) holder.itemView).accept(getItem(position));
      }

      @Override
      public final int getItemViewType(int position) {
        return layout;
      }

      @Nullable
      @Override
      protected T getItem(int position) {
        return super.getItem(position);
      }

      @Override
      public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        ((Runnable) holder.itemView).run();
        super.onViewRecycled(holder);
      }
    };
  }

}
