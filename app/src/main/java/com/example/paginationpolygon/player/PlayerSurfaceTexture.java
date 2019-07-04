package com.example.paginationpolygon.player;

import android.graphics.SurfaceTexture;

/**
 * @author Konstantin Epifanov
 * @since 04.07.2019
 */
public class PlayerSurfaceTexture extends SurfaceTexture {

  public PlayerSurfaceTexture(int texName) {
    super(texName);
  }

  public PlayerSurfaceTexture(int texName, boolean singleBufferMode) {
    super(texName, singleBufferMode);
  }

  public PlayerSurfaceTexture(boolean singleBufferMode) {
    super(singleBufferMode);
  }

}
