package org.sunbird.contentvalidation.util;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.sunbird.contentvalidation.model.ProfanityLevelClassification;

@Service
public class CommonUtils {

	private static final char UNIX_SEPARATOR = '/';

	private static final int NOT_FOUND = -1;

	/**
	 * The Windows separator character.
	 */
	private static final char WINDOWS_SEPARATOR = '\\';

	/**
	 * Gets the name minus the path from a full filename.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The text
	 * after the last forward or backslash is returned.
	 * 
	 * <pre>
	 * a/b/c.txt --&gt; c.txt
	 * a.txt     --&gt; a.txt
	 * a/b/c     --&gt; c
	 * a/b/c/    --&gt; ""
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is
	 * running on.
	 *
	 * @param filename the filename to query, null returns null
	 * @return the name of the file without the path, or an empty string if none
	 *         exists. Null bytes inside string will be removed
	 */
	public String getFileName(final String filename) {
		if (filename == null) {
			return null;
		}
		failIfNullBytePresent(filename);
		final int index = indexOfLastSeparator(filename);
		return filename.substring(index + 1);
	}

	/**
	 * Returns the index of the last directory separator character.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The position
	 * of the last forward or backslash is returned.
	 * <p>
	 * The output will be the same irrespective of the machine that the code is
	 * running on.
	 *
	 * @param filename the filename to find the last path separator in, null returns
	 *                 -1
	 * @return the index of the last separator character, or -1 if there is no such
	 *         character
	 */
	public int indexOfLastSeparator(final String filename) {
		if (filename == null) {
			return NOT_FOUND;
		}
		final int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
		final int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
		return Math.max(lastUnixPos, lastWindowsPos);
	}

	/**
	 * Check the input for null bytes, a sign of unsanitized data being passed to to
	 * file level functions.
	 * <p>
	 * This may be used for poison byte attacks.
	 *
	 * @param path the path to check
	 */
	private void failIfNullBytePresent(String path) {
		int len = path.length();
		for (int i = 0; i < len; i++) {
			if (path.charAt(i) == 0) {
				throw new IllegalArgumentException("Null byte present in file/path name. There are no "
						+ "known legitimate use cases for such data, but several injection attacks may use it");
			}
		}
	}

	public String getProfanityClassification(String currentValue, String newValue) {
		ProfanityLevelClassification newProfanityLevel = ProfanityLevelClassification.getLevelByName(newValue);
		ProfanityLevelClassification currentProfanityLevel = ProfanityLevelClassification.getLevelByName(currentValue);

		int compareValue = currentProfanityLevel.compareTo(newProfanityLevel);
		if (compareValue <= 0) {
			return newProfanityLevel.getLevelName();
		} else {
			return currentProfanityLevel.getLevelName();
		}
	}

	public boolean emptyCheck(String value) {
		String str = value.replaceAll("(\r\n|\n|\r)", "").replaceAll("\\s+","");
		return StringUtils.isEmpty(str);
	}
}
