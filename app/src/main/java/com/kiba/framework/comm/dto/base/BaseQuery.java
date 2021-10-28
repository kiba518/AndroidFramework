package com.kiba.framework.comm.dto.base;

public class BaseQuery {
    public boolean HasPage;
    public int CurrentPage;
    public int SkipNumber;

    public boolean isHasPage() {
        return HasPage;
    }

    public void setHasPage(boolean hasPage) {
        HasPage = hasPage;
    }

    public int getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        CurrentPage = currentPage;
    }

    public int getSkipNumber() {
        return SkipNumber;
    }

    public void setSkipNumber(int skipNumber) {
        SkipNumber = skipNumber;
    }
}
