package master.events;

public class AvgSpeedFine {
  /** Time1 is the time of the first event of the segment */
  private long time1;

  /** Time2 is the time of the last event of the segment */
  private long time2;

  private int vId;
  private int xWay;
  private int dir;
  private int avgSpd;

  public AvgSpeedFine(long time1, long time2, int vId, int xWay, int dir, int avgSpd) {
    this.time1 = time1;
    this.time2 = time2;
    this.vId = vId;
    this.xWay = xWay;
    this.dir = dir;
    this.avgSpd = avgSpd;
  }

  public long getTime1() {
    return time1;
  }

  public void setTime1(long time1) {
    this.time1 = time1;
  }

  public long getTime2() {
    return time2;
  }

  public void setTime2(long time2) {
    this.time2 = time2;
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

  public int getAvgSpd() {
    return avgSpd;
  }

  public void setAvgSpd(int avgSpd) {
    this.avgSpd = avgSpd;
  }
}
