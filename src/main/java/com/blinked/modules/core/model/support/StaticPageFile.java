package com.blinked.modules.core.model.support;

import lombok.Data;

import java.util.Comparator;
import java.util.List;

/**
 * Static page dto.
 *
 * @author ssatwa
 * @date 2019-12-26
 */
@Data
public class StaticPageFile implements Comparator<StaticPageFile> {

    private String id;

    private String name;

    private Boolean isFile;

    private List<StaticPageFile> children;

    @Override
    public int compare(StaticPageFile leftFile, StaticPageFile rightFile) {
        if (leftFile.isFile && !rightFile.isFile) {
            return 1;
        }

        if (!leftFile.isFile && rightFile.isFile) {
            return -1;
        }

        return leftFile.getName().compareTo(rightFile.getName());
    }
}