package org.ccit.com;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MenuTree {

    /**
     * 获取菜单树
     *
     * @param menuList 获取到的全量菜单列表
     * @return
     */
    public static List<Menu> getMenuTree(List<Menu> menuList) {
        List<Menu> result = new ArrayList<>();

        //获取根节点
        menuList.forEach(menu -> {
            if (menu.getParentId() == null) {
                result.add(menu);
            }
        });

        result.forEach(menu -> {
            recursiveTree(menu, menuList);
        });

        return result;
    }

    private static void recursiveTree(Menu parentMenu, List<Menu> menuList) {
        menuList.forEach(menu -> {
            if (parentMenu.getId().equals(menu.getParentId())) {
                recursiveTree(menu, menuList);
                parentMenu.getChildren().add(menu);
            }
        });
    }

    public static void main(String[] args) {
        Menu menu1 = new Menu(1L, "主目录1", "", null);
        Menu menu11 = new Menu(11L, "目录11", "", 1l);
        Menu menu111 = new Menu(111L, "目录111", "", 11l);
        Menu menu112 = new Menu(112L, "目录111", "", 11L);
        Menu menu12 = new Menu(12L, "目录12", "", 1L);
        Menu menu13 = new Menu(13L, "目录13", "", 1L);
        Menu menu14 = new Menu(14L, "目录14", "", 1L);

        Menu menu2 = new Menu(2l, "主目录2", "", null);
        Menu menu21 = new Menu(21l, "目录21", "", 2l);
        Menu menu22 = new Menu(22l, "目录22", "", 2l);
        Menu menu23 = new Menu(23l, "目录23", "", 2l);

        Menu menu3 = new Menu(3l, "主目录3", "", null);
        Menu menu31 = new Menu(31l, "目录31", "", 3l);

        Menu menu4 = new Menu(4l, "主目录4", "", null);
        Menu menu41 = new Menu(41l, "目录41", "", 4l);
        Menu menu42 = new Menu(42l, "目录42", "", 4l);

        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu1);
        menuList.add(menu11);
        menuList.add(menu111);
        menuList.add(menu112);
        menuList.add(menu12);
        menuList.add(menu13);
        menuList.add(menu14);

        menuList.add(menu2);
        menuList.add(menu21);
        menuList.add(menu22);
        menuList.add(menu23);

        menuList.add(menu3);
        menuList.add(menu31);
        menuList.add(menu4);
        menuList.add(menu41);
        menuList.add(menu42);

        List<Menu> result = getMenuTree(menuList);
        JSONArray jsonArray = new JSONArray(result);
        System.out.println(jsonArray);
    }

}
