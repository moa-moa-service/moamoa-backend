package site.moamoa.backend.web.dto.response;

import java.util.List;
import site.moamoa.backend.web.dto.base.KeywordDTO;

public class KeywordResponseDTO {

    public static class GetKeywords {
        List<KeywordDTO> keywords;
    }
}
