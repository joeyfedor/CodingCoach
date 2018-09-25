package mandj.appbuildin.codingcoach.innovo.Study.Runner;

import java.util.Arrays;

/**
 * Created by joey on 2/21/15.
 */
public class StringManipulation {

    public static String firstWord(String input, String color) {
        return ("<font color=#" + color + ">" +
                input.substring(0, input.indexOf(" ")) + "</font> " +
                input.substring(input.indexOf(" ") + 1));
    }

    public static String strbuilder(Object obj, boolean correct) {
        if (obj instanceof String[]) {
            if (correct) {
                return "<font color=#409c00>" + Arrays.toString((String[]) obj) + "</font><br><br>";
            } else {
                return "<font color=#dc4229>" + Arrays.toString((String[]) obj) + "</font><br><br>";
            }
        } else {
            if (correct) {
                //Green
                return "<font color=#409c00>" + obj + "</font><br><br>";
            } else
                //Red
                return "<font color=#dc4229>" + obj + "</font><br><br>";
        }
    }

    public static String strbuilder(Object[] obj, boolean correct) {
        String output = Arrays.toString(obj);
        output = output.replace("[", "");
        output = output.replace("]", "");

        if (correct) {
            //Green
            return "<font color=#409c00>" + output + "</font><br><br>";
        } else
            //Red
            return "<font color=#dc4229>" + output + "</font><br><br>";
    }


    public static String unEscape(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
            switch (str.charAt(i)) {
                case '\n':
                    sb.append("\\n");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                // ... rest of escape characters
                default:
                    sb.append(str.charAt(i));
            }
        return sb.toString();
    }

    public static String repair(String inputString) {
        String[] lines;
        lines = inputString.split("\n");
        boolean containsElse = false;
        String afterelse = "";
        for (String str : lines) {
            if (containsElse) {
                str = str + "}";
                afterelse += str + "\n";
                containsElse = false;
            } else if (str.contains("else if")) {
                System.out.println("Entered Loop");
                containsElse = true;
                int loc = str.indexOf("else");
                str = str.substring(0, loc) + "}" + str.substring(loc, str.length());
                str = str.substring(0, loc + 8) + "{" + str.substring(loc + 8, str.length());
                afterelse += str + "\n";
            } else if (str.contains("if") && (!str.contains("{"))) {
                int loc = str.lastIndexOf(")") + 1;
                str = str.substring(0, loc) + "{" + str.substring(loc, str.length());
                afterelse += str + "\n";
            } else if (str.contains("else") && (!str.contains("{"))) {
                containsElse = true;
                int loc = str.indexOf("else");
                str = str.substring(0, loc) + "}" + str.substring(loc, str.length());
                str = str.substring(0, loc + 5) + "{" + str.substring(loc + 5, str.length());
                afterelse += str + "\n";
            } else {
                afterelse += str + "\n";
            }

            //
            if (str.contains("//")) {
                int loc = afterelse.indexOf("//");
                int locAfter = afterelse.lastIndexOf("\n");
                afterelse = afterelse.substring(0, loc) + afterelse.substring(locAfter, afterelse.length());
            }
        }
        return afterelse;
    }


}
