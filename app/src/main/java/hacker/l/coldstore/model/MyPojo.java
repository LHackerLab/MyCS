package hacker.l.coldstore.model;

/**
 * Created by lalitsingh on 09/03/18.
 */

public class MyPojo {
    private Result[] result;

    public Result[] getResult() {
        return result;
    }

    public void setResult(Result[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ClassPojo [result = " + result + "]";
    }
}
