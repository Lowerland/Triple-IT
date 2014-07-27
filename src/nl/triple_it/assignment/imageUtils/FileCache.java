package nl.triple_it.assignment.imageUtils;

import java.io.File;
import android.content.Context;

public class FileCache {

	private File cacheDir;

	public FileCache(Context context) {
		// Find the cache dir to save cached images
		cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, but good for
		// enough for now.
		String filename = String.valueOf(url.hashCode());
		// Another possible solution (thanks to grantland)
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}
