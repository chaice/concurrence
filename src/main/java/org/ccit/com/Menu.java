package org.ccit.com;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private Long id;

    //菜单标题
    private String title;

    //路径
    private String path;

    //父菜单Id
    private Long parentId;

    private List<Menu> children = new ArrayList<>();

    public Menu(Long id, String title, String path, Long parentId) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
