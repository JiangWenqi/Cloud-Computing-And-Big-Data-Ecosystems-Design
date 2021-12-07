package master.events;

/**
 * @author Wenqi Jiang & Zhou
 */
public class SpeedFine {
    private long time;
    private int vId;
    private int xWay;
    private int seg;
    private int dir;
    private int spd;

    public SpeedFine(long time, int vId, int xWay, int seg, int dir, int spd) {
        this.time = time;
        this.vId = vId;
        this.xWay = xWay;
        this.seg = seg;
        this.dir = dir;
        this.spd = spd;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    @Override
    public String toString() {
        return time + ", " + vId + ", " + xWay + ", " + seg + ", " + dir + ", " + spd;
    }
}
