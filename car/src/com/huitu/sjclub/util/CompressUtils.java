/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.util;

import org.apache.commons.compress.archivers.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
public final class CompressUtils {

	private CompressUtils() {
	}

	public static void archive(File[] srcFiles, File destFile, String archiverName) {
		Assert.notNull(destFile);
		Assert.state(!destFile.exists() || destFile.isFile());
		Assert.hasText(archiverName);

		File parentFile = destFile.getParentFile();
		if (parentFile != null) {
			parentFile.mkdirs();
		}
		ArchiveOutputStream archiveOutputStream = null;
		try {
			archiveOutputStream = new ArchiveStreamFactory().createArchiveOutputStream(archiverName, new BufferedOutputStream(new FileOutputStream(destFile)));
			if (ArrayUtils.isNotEmpty(srcFiles)) {
				for (File srcFile : srcFiles) {
					if (srcFile == null || !srcFile.exists()) {
						continue;
					}
					Set<File> sjclubs = new HashSet<File>();
					if (srcFile.isFile()) {
						sjclubs.add(srcFile);
					}
					if (srcFile.isDirectory()) {
						sjclubs.addAll(FileUtils.listFilesAndDirs(srcFile, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE));
					}
					String basePath = FilenameUtils.getFullPath(srcFile.getCanonicalPath());
					for (File sjclub : sjclubs) {
						try {
							String entryName = FilenameUtils.separatorsToUnix(StringUtils.substring(sjclub.getCanonicalPath(), basePath.length()));
							ArchiveEntry archiveEntry = archiveOutputStream.createArchiveEntry(sjclub, entryName);
							archiveOutputStream.putArchiveEntry(archiveEntry);
							if (sjclub.isFile()) {
								InputStream inputStream = null;
								try {
									inputStream = new BufferedInputStream(new FileInputStream(sjclub));
									IOUtils.copy(inputStream, archiveOutputStream);
								} catch (FileNotFoundException e) {
									throw new RuntimeException(e.getMessage(), e);
								} catch (IOException e) {
									throw new RuntimeException(e.getMessage(), e);
								} finally {
									IOUtils.closeQuietly(inputStream);
								}
							}
						} catch (IOException e) {
							throw new RuntimeException(e.getMessage(), e);
						} finally {
							archiveOutputStream.closeArchiveEntry();
						}
					}
				}
			}
		} catch (ArchiveException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(archiveOutputStream);
		}
	}

	public static void archive(File srcFile, File destFile, String archiverName) {
		archive(new File[] { srcFile }, destFile, archiverName);
	}

	public static void extract(File srcFile, File destDir) {
		Assert.notNull(srcFile);
		Assert.state(srcFile.exists());
		Assert.state(srcFile.isFile());
		Assert.notNull(destDir);
		Assert.state(!destDir.exists() || destDir.isDirectory());

		destDir.mkdirs();
		ArchiveInputStream archiveInputStream = null;
		try {
			archiveInputStream = new ArchiveStreamFactory().createArchiveInputStream(new BufferedInputStream(new FileInputStream(srcFile)));
			ArchiveEntry archiveEntry;
			while ((archiveEntry = archiveInputStream.getNextEntry()) != null) {
				if (archiveEntry.isDirectory()) {
					new File(destDir, archiveEntry.getName()).mkdirs();
				} else {
					OutputStream outputStream = null;
					try {
						outputStream = new BufferedOutputStream(new FileOutputStream(new File(destDir, archiveEntry.getName())));
						IOUtils.copy(archiveInputStream, outputStream);
					} catch (FileNotFoundException e) {
						throw new RuntimeException(e.getMessage(), e);
					} catch (IOException e) {
						throw new RuntimeException(e.getMessage(), e);
					} finally {
						IOUtils.closeQuietly(outputStream);
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ArchiveException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(archiveInputStream);
		}
	}

}