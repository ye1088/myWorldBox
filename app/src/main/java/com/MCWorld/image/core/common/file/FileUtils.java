package com.MCWorld.image.core.common.file;

import com.MCWorld.framework.base.utils.Preconditions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtils {

    public static class CreateDirectoryException extends IOException {
        public CreateDirectoryException(String message) {
            super(message);
        }

        public CreateDirectoryException(String message, Throwable innerException) {
            super(message);
            initCause(innerException);
        }
    }

    public static class FileDeleteException extends IOException {
        public FileDeleteException(String message) {
            super(message);
        }
    }

    public static class ParentDirNotFoundException extends FileNotFoundException {
        public ParentDirNotFoundException(String message) {
            super(message);
        }
    }

    public static class RenameException extends IOException {
        public RenameException(String message) {
            super(message);
        }

        public RenameException(String message, Throwable innerException) {
            super(message);
            initCause(innerException);
        }
    }

    public static void u(File directory) throws CreateDirectoryException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                if (!directory.delete()) {
                    throw new CreateDirectoryException(directory.getAbsolutePath(), new FileDeleteException(directory.getAbsolutePath()));
                }
            }
            return;
        }
        if (!directory.mkdirs() && !directory.isDirectory()) {
            throw new CreateDirectoryException(directory.getAbsolutePath());
        }
    }

    public static void rename(File source, File target) throws RenameException {
        Preconditions.checkNotNull(source);
        Preconditions.checkNotNull(target);
        target.delete();
        if (!source.renameTo(target)) {
            Throwable innerException = null;
            if (target.exists()) {
                innerException = new FileDeleteException(target.getAbsolutePath());
            } else if (!source.getParentFile().exists()) {
                innerException = new ParentDirNotFoundException(source.getAbsolutePath());
            } else if (!source.exists()) {
                innerException = new FileNotFoundException(source.getAbsolutePath());
            }
            throw new RenameException("Unknown error renaming " + source.getAbsolutePath() + " to " + target.getAbsolutePath(), innerException);
        }
    }
}
