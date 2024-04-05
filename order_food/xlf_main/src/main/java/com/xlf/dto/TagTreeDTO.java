package com.xlf.dto;

import com.xlf.vo.TagTreeVO;
import lombok.Data;

import java.util.List;

@Data
public class TagTreeDTO  {

    private List<TagTreeVO> tagTree;

    private Long foodId;
}
