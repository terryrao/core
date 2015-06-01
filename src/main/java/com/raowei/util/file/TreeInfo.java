package com.raowei.util.file;

import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by terryrao on 5/27/2015.
 */
public class TreeInfo implements Iterable<File> {
    List<File> dir = new ArrayList<>();
    List<File> files = new ArrayList<>();

    /**
     * 遍历所有的文件
     *
     * @return
     */
    @Override
    public Iterator<File> iterator() {
        return files.iterator();
    }

    public void addAll(TreeInfo other) {
        dir.addAll(other.dir);
        files.addAll(other.files);
    }



    /**
     * 遍历目录树
     *
     * @param startDir 选定的目录
     * @param regex    匹配的文件名称正则表达式
     * @return
     */
    public static TreeInfo recrurseDirs(File startDir, String regex) {
        TreeInfo treeInfo = new TreeInfo();
        for (File item : startDir.listFiles()) {
            if (item.isDirectory()) {
                treeInfo.dir.add(item);
                treeInfo.addAll(recrurseDirs(item, regex));
            } else {
                if (item.getName().matches(regex)) {
                    treeInfo.files.add(item);
                }
            }
        }
        return treeInfo;
    }
    /**
     * 遍历目录树
     *
     * @param startDir 选定的目录
     * @param regex    匹配的文件名称正则表达式
     * @return
     */
    public static TreeInfo walk(String startDir,String regex) {
        return recrurseDirs(new File(startDir),regex);
    }


    /**
     * 遍历目录树
     *
     * @param startDir 选定的目录
     * @param regex    匹配的文件名称正则表达式
     * @return
     */
    public static TreeInfo walk(File startDir,String regex) {
        return recrurseDirs(startDir,regex);
    }

    /**
     * 遍历目录树
     *
     * @param startDir 选定的目录
     * @return
     */
    public static TreeInfo walk(File startDir) {
        return recrurseDirs(startDir, ".*");
    }

    /**
     * 遍历目录树
     *
     * @param startDir 选定的目录
     * @return
     */
    public static TreeInfo walk(String startDir) {
        return recrurseDirs(new File(startDir),".*");
    }


    public static void main(String[] args) {
        TreeInfo treeInfo = walk("D:\\Java\\jdk1.8.0");
        for (File file : treeInfo.dir) {
            System.out.println("目录 -->" + file);
        }
        for (File file : treeInfo.files) {
            System.out.println("文件 -->" + file);
        }
        System.out.println("共 -->" + treeInfo.dir.size() + "个文件夹");
        System.out.println("共 -->" + treeInfo.files.size() + "个文件");

    }
}
