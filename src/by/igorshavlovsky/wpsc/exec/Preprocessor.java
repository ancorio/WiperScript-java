package by.igorshavlovsky.wpsc.exec;

import java.util.HashSet;
import java.util.Set;

public class Preprocessor {

	public static Script prepare(String s, boolean isScript) {
		Set<Integer> strings = new HashSet<>(128);
		StringBuilder result = new StringBuilder(s.length());
		boolean inString = false;
		boolean escape = false;
		int l = s.length();
		for (int i = 0; i < l; i++) {
			char c = s.charAt(i);
			if (inString) {
				switch (c) {
				case '"':
					if (escape) {
						escape = false;
					} else {
						inString = false;
						
					}
					break;
				case '\\':
					escape = !escape;
					break;
				case 't':
				case 'n':
				case 'r':
					if (escape) {
						escape = false;
					}
					break;
				default:
					if (escape) {
						throw new PreprocessorException("Unknown escape character: " + c);
					}
					break;
				}
				strings.add(Integer.valueOf(result.length()));
				result.append(c);
			} else {
				boolean shouldAppend;
				switch (c) {
				case '"':
					inString = true;
					shouldAppend = true;
					strings.add(Integer.valueOf(result.length()));
					break;
				case '\t':
				case '\n':
				case '\r':
				case ' ':
					shouldAppend = false;
					break;
				default:
					shouldAppend = true;
					break;
				}
				if (shouldAppend) {
					result.append(c);
				}
			}
		}
		if (inString) {
			throw new PreprocessorException("String constant is not finished");
		}
		if (isScript && (result.length() == 0 || (result.charAt(result.length() - 1) != ';'))) {
			result.append(';');
		}
		return new Script(result.toString(), strings);
	}

}
