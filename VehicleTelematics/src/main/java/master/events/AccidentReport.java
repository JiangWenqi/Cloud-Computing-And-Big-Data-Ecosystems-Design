package master.events;

/** @author Wenqi Jiang & Zhuo Cheng*/
public class AccidentReport {
  /** Time1 is the time of the first event the car stops */
  private long time1;
  /** Time2 is the time of the fourth event the car reports to be stopped */
  private long time2;

  private int vId;
  private int xWay;
  private int seg;
  private int dir;
  private int pos;

  public AccidentReport(long time1, long time2, int vId, int xWay, int seg, int dir, int pos) {
    this.time1 = time1;
    this.time2 = time2;
    this.vId = vId;
    this.xWay = xWay;
    this.seg = seg;
    this.dir = dir;
    this.pos = pos;
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

  public int getvId() {
    return vId;
  }

  public void setvId(int vId) {
    this.vId = vId;
  }

  public int getxWay() {
    return xWay;
  }

  public void setxWay(int xWay) {
    this.xWay = xWay;
  }

  public int getSeg() {
    return seg;
  }

  public void setSeg(int seg) {
    this.seg = seg;
  }

  public int getDir() {
    return dir;
  }

  public void setDir(int dir) {
    this.dir = dir;
  }

  public int getPos() {
    return pos;
  }

  public void setPos(int pos) {
    this.pos = pos;
  }

  @Override
  public String toString() {
    return time1 + ", " + time2 + ", " + vId + ", " + xWay + ", " + seg + ", " + dir + ", " + pos;
  }
}
