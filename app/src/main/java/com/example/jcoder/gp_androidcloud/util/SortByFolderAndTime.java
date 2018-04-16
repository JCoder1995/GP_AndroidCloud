package com.example.jcoder.gp_androidcloud.util;


import android.annotation.TargetApi;
import android.os.Build;

import java.io.File;
import java.util.Comparator;

public class SortByFolderAndTime implements Comparator<File> {

	boolean first;
	boolean second;

	public SortByFolderAndTime(boolean firstSequence,
			boolean secondSequence) {
		this.first = firstSequence;
		this.second = secondSequence;
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public int compare(File lhs, File rhs) {
		if (first) {
			if (!lhs.isFile() && rhs.isFile()) {
				return -1;
			}
			if (lhs.isFile() && !rhs.isFile()) {
				return 1;
			}
		} else {
			if (!lhs.isFile() && rhs.isFile()) {
				return 1;
			}
			if (lhs.isFile() && !rhs.isFile()) {
				return -1;
			}
		}

		if (second) {
			if (!(lhs.isFile() ^ rhs.isFile())) {
				return -Long.compare(lhs.lastModified(), rhs.lastModified());
			}
		} else {
			if (!(lhs.isFile() ^ rhs.isFile())) {
				return +Long.compare(lhs.lastModified(), rhs.lastModified());
			}
		}

		return 0;
	}
}
