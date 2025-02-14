package kr.co.digitalship.msarouter.subscribe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BackupData {
    private int boardId;
    private String content;
    private String rgstDttm;
}
