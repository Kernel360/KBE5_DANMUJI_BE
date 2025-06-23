package com.back2basics.mention;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MentionUtils {

    // 정규식 : @로 시작 + 한글/영문/숫자/밑줄로 구성된 2~20자의 사용자명 추출
    private static final Pattern MENTION_PATTERN = Pattern.compile("@([가-힣a-zA-Z0-9_]{2,20})");


    //주어진 텍스트에서 멘션된 username만 추출
    public static List<String> extractMentionUsernames(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }

        Matcher matcher = MENTION_PATTERN.matcher(text);
        Set<String> mentions = new HashSet<>();

        while (matcher.find()) {
            mentions.add(matcher.group(1)); // group(1) -> '@' 제외된 username
        }

        return mentions.stream().collect(Collectors.toList());
    }
}