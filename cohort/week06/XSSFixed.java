import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class XSSFixed {

	public static void main(String args[]) {
			// assume "s" is the input that may be susceptible to XSS attacks
			//String s = "<script>";
			String s = "<script> alert('hey there, you have been hacked') </script> ";
			//String s = "\uFE64" + "script" + "\uFE65";
			s = Normalizer.normalize(s, Form.NFKC);
			Pattern pattern = Pattern.compile("<script>");
			Matcher matcher = pattern.matcher(s);
			if (matcher.find()) {
				System.out.println("blacklisted tag");
			} else {
				// . . .
			}
			// Deletes all non-valid characters
			s = s.replaceAll("[^\\p{ASCII}]", "");
	}
}
