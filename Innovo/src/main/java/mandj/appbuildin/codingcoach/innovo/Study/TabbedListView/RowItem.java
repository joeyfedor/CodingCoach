package mandj.appbuildin.codingcoach.innovo.Study.TabbedListView;

/**
 * Created by Monica on 8/17/2014.
 */

public class RowItem {
    private int imageId;
    private String title;
    private String desc;
    private int done;

    public RowItem(int imageId, String title, String desc, int done) {
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
        this.done = done;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return title + "\n" + desc;
    }
}
