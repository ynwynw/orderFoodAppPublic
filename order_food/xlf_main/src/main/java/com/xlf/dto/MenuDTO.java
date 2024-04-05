package com.xlf.dto;

import com.xlf.domain.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuDTO extends Menu {

    private List<MenuDTO> children;
}
