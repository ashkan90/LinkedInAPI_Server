package com.linkedin.ugc;

import java.util.HashMap;
import java.util.Map;

public class ShareContent {
    public Map<String, String> shareCommentary;
    public String shareMediaCategory = "NONE";

    public ShareContent(String text) {
        this.shareCommentary = new HashMap<>();
        this.shareCommentary.put("text", text);
    }
}
