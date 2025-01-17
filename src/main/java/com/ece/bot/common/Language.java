package com.ece.bot.common;

public enum Language {
    RU, EN, CHINA;

    public static Language getLanguageByCode(String code) {
        switch (code) {
            case "ru":
                return RU;
            case "en":
                return EN;
            case "china":
                return CHINA;
            default:
                return EN;
        }
    }
}
