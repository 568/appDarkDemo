package com.haste.lv.faith.uiviews.recyclerview.section;

/**
 * Created by lv on 18-12-6.
 * 这个类是用来记录分组列表中组的结构的</p>
 * 通过GroupStructure记录每个组是否有头部，是否有尾部和子项的数量。从而能方便的计算<br>
 * 列表的长度和每个组的组头、组尾和子项在列表中的位置<br>
 */

public class SectionStructure {
    private boolean hasHeader;
    private boolean hasFooter;
    private int childrenCount;

    public SectionStructure(boolean hasHeader, boolean hasFooter, int childrenCount) {
        this.hasHeader = hasHeader;
        this.hasFooter = hasFooter;
        this.childrenCount = childrenCount;
    }

    public boolean hasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public boolean hasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }
}
