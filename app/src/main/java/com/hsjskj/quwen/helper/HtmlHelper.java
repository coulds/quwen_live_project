package com.hsjskj.quwen.helper;

/**
 * @author : Jun
 * time          : 2020年12月30日 10:03
 * description   : quwen_live
 */
public class HtmlHelper {

    public static String addHtml(String content) {
        String head = "<head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\" />" +
                "<style>* {font-size:15px;padding:0;} img{max-width: 100%; width:auto; height: auto;} video{max-width: 100%; width:100%; height: auto;}</style></head>";
        return "<html>" + head + "<body style=\"word-wrap:break-word;\">" + content + "</body></html>";
    }
}
