package master.utils;

import java.util.Objects;

/**
 * @author Wenqi
 */
public class AccidentKey {
    private int vId;
    private int xWay;
    private int dir;
    private int seg;
    private int pos;

    public AccidentKey(int vId, int xWay, int dir, int seg, int pos) {
        this.vId = vId;
        this.xWay = xWay;
        this.dir = dir;
        this.seg = seg;
        this.pos = pos;
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

    public int getSeg() {
        return seg;
    }

    public void setSeg(int seg) {
        this.seg = seg;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccidentKey that = (AccidentKey) o;
        return vId == that.vId && xWay == that.xWay && getDir() == that.getDir() && getSeg() == that.getSeg() && getPos() == that.getPos();
    }

    @Override
    public int hashCode() {
        return Objects.hash(vId, xWay, getDir(), getSeg(), getPos());
    }
}
