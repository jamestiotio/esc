import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class XSSFixed {
    public static void main(String args[]) {
        // Assume "s" is the input that may be susceptible to XSS attacks
        // String s = "<script>";
        // String s = "<script> alert('hey there, you have been hacked') </script> ";
        String s = "\uFE64" + "script" + "\uFE65";
        // Deletes all non-valid characters (which might be the unicode equivalent of <script>)
        s = s.replaceAll("[^\\p{ASCII}]", "");
        // Remove all white spaces
        s = s.replaceAll("\\s+", "");
        // Normalize string before
        s = Normalizer.normalize(s, Form.NFKC);
        Pattern pattern = Pattern.compile("<script>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            System.out.println("blacklisted tag");
        } else {
            // ...
        }
        // Do not modify input after blacklisting!
        System.out.println("Modified string: " + s);
    }
}
