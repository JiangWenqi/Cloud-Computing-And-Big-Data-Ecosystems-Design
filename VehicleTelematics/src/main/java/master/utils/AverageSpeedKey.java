package master.utils;

import java.util.Objects;

/** @author Wenqi */
public class AverageSpeedKey {
  private int vId;
  private int xWay;
  private int dir;

  public AverageSpeedKey(int vId, int xWay, int dir) {
    this.vId = vId;
    this.xWay = xWay;
    this.dir = dir;
  }

  public int getVId() {
    return vId;
  }

  public void setVId(int vId) {
    this.vId = vId;
  }

  public int getXWay() {
    return xWay;
  }

  public void setXWay(int xWay) {
    this.xWay = xWay;
  }

  public int getDir() {
    return dir;
  }

  public void setDir(int dir) {
    this.dir = dir;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AverageSpeedKey that = (AverageSpeedKey) o;
    return getVId() == that.getVId() && getXWay() == that.getXWay() && getDir() == that.getDir();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getVId(), getXWay(), getDir());
  }
}
