package com.qsl.seckill;

/**
 * @author DanielQSL
 * @date 2018/12/3
 */
public enum Season {
    Spring(1, "春天"),
    Summer(2, "夏天"),
    Autumn(3, "秋天"),
    Winter(4, "冬天");

    private int code;
    private String name;

    Season(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
