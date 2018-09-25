package mandj.appbuildin.codingcoach.innovo.Learn;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by joey on 4/26/15.
 */
public class TextBoxListener extends InputStream {


    final LearnRunner reader = new LearnRunner();
    private TextView tv;
    private String str = null;
    private int pos = 0;

    public TextBoxListener(TextView jtf) {
        tv = jtf;
    }

    public static void checkOffsetAndCount(int arrayLength, int offset, int count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void inputData(String data) {
        Log.d("textBoxListner", "INput DAta");

        str = data + "\n";
        pos = 0;
    }

    //gets triggered everytime that "Enter" is pressed on the textfield
    public void InputField(EditText txt) {
        str = txt.getText() + "\n";
        pos = 0;
        synchronized (this) {
            //maybe this should only notify() as multiple threads may
            //be waiting for input and they would now race for input
            this.notifyAll();
        }
    }

    @Override
    public int read() {

        //reader.openText(true);
        //test if the available input has reached its end
        //and the EOS should be returned
        if (str != null && pos == str.length()) {
            str = null;
            //this is supposed to return -1 on "end of stream"
            //but I'm having a hard time locating the constant
            return java.io.StreamTokenizer.TT_EOF;
        }
        //no input available, block until more is available because that's
        //the behavior specified in the Javadocs
        while (str == null || pos >= str.length()) {
            try {
                //according to the docs read() should block until new input is available
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        //read an additional character, return it and increment the index
        return str.charAt(pos++);
    }

    @Override
    public int read(byte[] buffer, int byteOffset, int byteCount) {

        Log.d("textBoxListner", "CALLED IN : 1");

        new Thread() {
            @Override
            public void run() {
                reader.openText();
            }
        }.start();


        checkOffsetAndCount(buffer.length, byteOffset, byteCount);
        for (int i = 0; i < byteCount; ++i) {
            int c;
            if ((c = read()) == -1) {
                return i == 0 ? -1 : i;
            }
            buffer[byteOffset + i] = (byte) c;
        }
        return byteCount;
    }
}