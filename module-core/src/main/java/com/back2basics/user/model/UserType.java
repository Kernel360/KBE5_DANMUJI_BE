package com.back2basics.user.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserType {
    MANAGER("MANAGER"),
    MEMBER("MEMBER");


    private final String key;

    @JsonValue
    public String getKey() {
        return key;
    }
}
/* todo: key 로는 사용해본적이 없어서 우선 제 방식대로 사용하고
 *   추후에 공부하고 key로 적용할 수 있으면 적용하겠습니다.
 *    key 사용: getKey()와 key 미사용: getName() 비슷한걸로만 대충 알고있습니다. */
