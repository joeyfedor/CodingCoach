package mandj.appbuildin.codingcoach.innovo.Study.Runner.Examples;


/**
 * Created by joey on 12/26/14.
 */
public class ItemData {

    String input;
    String output;
    String desc;

    public ItemData(String input, String output, String desc) {
        this.input = input;
        this.output = output;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
