package com.raowei.util.file;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by terryrao on 5/27/2015.
 */
public class FileUtils {
    /**
     * 获取目录下特定文件或文件夹名称的集合
     *
     * @param dir   要遍历的文件夹
     * @param regex 文件名匹配的正则表达式
     * @return File[] 文件集合
     */
    public static File[] local(File dir, final String regex) {
        return dir.listFiles(new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);

            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(new File(name).getName()).matches();
            }
        });
    }

    /**
     * 获取特定路径下特定文件或文件夹名称的集合
     *
     * @param path   要遍历的文件夹
     * @param regex 文件名匹配的正则表达式
     * @return File[] 文件集合
     */
    public static File[] local(String path,final String regex) {
        return local(new File(path), regex);
    }

    /**
     * 创建文件
     * @param path
     * @return
     */
    public static Path createFile(String path) {
        Path target = Paths.get(path);
        try {
            return Files.createFile(target);
        } catch (IOException e) {
            throw new RuntimeException("can not creat specific file target[" + path + "]");
        }
    }

    public static Path createFile(@NotNull String path, String perm) {
        Path target = Paths.get(path);
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString(perm);
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
        try {
            return Files.createFile(target,attr);
        } catch (IOException e) {
            throw new RuntimeException("can not creat specific file target[" + path + "] \n perm=[" + perm + "]");
        }

    }

}
